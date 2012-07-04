package main;

import images.PlayerColor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ai.AI;

import network.KeyInput;
import network.NetworkData;

import map.Exit;
import map.Item;
import map.Map;
import game.Bomb;
import game.Direction;
import game.Drawable;
import game.GameData;
import game.Player;

public class Bomberman {

    private Container pane;
    private boolean shouldHaveExit;
    
    /**
     * Creates a new instance of a Bomberman game.
     * @param menuFrame Menu frame.
     * @param fullscreen If the window should fill the screen.
     * @param mapName Name of the map to load.
     * @param useAI If remaining slots should be filled up with AI players.
     */
    public Bomberman(JFrame menuFrame, boolean fullscreen, String mapName, boolean useAI) {
        GameData.playerCount = 1;
        GameData.frame = new JFrame();
        GameData.menuFrame = menuFrame;
        GameData.bomberman = this;
        GameData.networkData = new NetworkData(null, null, System.currentTimeMillis());
        if (GameData.server != null) {
            shouldHaveExit = GameData.server.getPlayerCount() == 1;
            if (useAI) {
                GameData.playerCount = 4;
            }else{
                GameData.playerCount = GameData.server.getPlayerCount();
            }
            
            GameData.drawables = new LinkedList<Drawable>();
            GameData.bombs = new LinkedList<Bomb>();
            GameData.items = new LinkedList<Item>();
            GameData.players = new Vector<Player>();
            GameData.keys = new Vector<LinkedList<Character>>();
            GameData.ais = new LinkedList<AI>();
            for (int i=0; i<GameData.playerCount; i++) GameData.keys.add(new LinkedList<Character>());

            if (mapName.equals("Random")) {
                GameData.map = new Map(Map.SIZE_DEFAULT_X, Map.SIZE_DEFAULT_Y, shouldHaveExit);
            } else {
                GameData.map = new Map(mapName);
            }
            // TODO non-hardcoded spawn locations
            int x = GameData.map.getWidth() - 2;
            int y = GameData.map.getHeight() - 2;
            if (GameData.playerCount > 0) GameData.players.add(new Player("Player 1", 1, 1, PlayerColor.RED));
            if (GameData.playerCount > 1) GameData.players.add(new Player("Player 2", x, y, PlayerColor.GREEN));
            if (GameData.playerCount > 2) GameData.players.add(new Player("Player 3", 1, y, PlayerColor.BLUE));
            if (GameData.playerCount > 3) GameData.players.add(new Player("Player 4", x, 1, PlayerColor.YELLOW));

            if (useAI) {
                for (int i=GameData.server.getPlayerCount(); i<4; i++) {
                    GameData.ais.add(new AI(GameData.players.get(i)));
                }
            }
        }

        GameData.gamePanel = new MapPanel();
        pane = GameData.frame.getContentPane();
        GameData.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (fullscreen) {
            // Remove title bar etc.
            GameData.frame.setUndecorated(true);
            // Set size to fill the whole screen
            GameData.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // Make fullscreen window stay on top of other windows
            GameData.frame.setAlwaysOnTop(true);
        }

        // Remove layout so things can be placed manually
        pane.setLayout(null);
        // Add GamePanel
        pane.add(GameData.gamePanel);
        // Set background color to black
        pane.setBackground(Color.BLACK);
        // add Player 1

        // Register resize/show events
        ComponentListener componentListener = new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                resizeGamePanel();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resizeGamePanel();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

        };
        pane.addComponentListener(componentListener);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    int option = JOptionPane.showConfirmDialog(GameData.frame, "Do you really want to exit?","Exit",JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.OK_OPTION) {
                        stop();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                GameData.client.send(new KeyInput(e, false));
            }

            @Override
            public void keyTyped(KeyEvent e) {
                GameData.client.send(new KeyInput(e, true));
            }
        };
        GameData.frame.addKeyListener(keyListener);
        GameData.frame.setSize(600, 600);
        GameData.frame.setVisible(true);

        Thread gameLoop = new Thread() {
            public void run() {
                while (GameData.frame.isVisible()) {
                    
                    if (GameData.server != null) {
                        updateGameState();
                    }
                    
                    GameData.frame.repaint();
                    try {
                        Thread.sleep(1000 / GameData.fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        gameLoop.start();
    }
    
    /**
     * Updates the game (one frame).
     */
    private void updateGameState() {
        for (AI ai : GameData.ais) ai.nextStep();
        
        //Use items
        synchronized (GameData.items) {
            ListIterator<Item> it = GameData.items.listIterator();
            while (it.hasNext()) {
                Item item = it.next();
                for (Player player : GameData.players) {
                    if (item.x == player.x && item.y == player.y) {
                        item.setUsed(true);
                        if (item.getType() == Item.SPEED) {
                            player.speedUp();
                        }else {
                            player.increaseItemCounter(item.getType());
                        }
                        it.remove();
                        break;
                    }
                }
            }
        }
        
        //Update bombs
        synchronized (GameData.bombs) {
            ListIterator<Bomb> it = GameData.bombs.listIterator();
            while (it.hasNext()) {
                Bomb bomb = it.next();
                if (bomb.isExpired()) {
                    it.remove();
                }else {
                    if (--bomb.ticksUntilExplosion < 0) {
                        bomb.explode();
                    }
                }
            }
        }
        
        //Update players
        for (Player player : GameData.players) {
            player.ticks++;
            if(--player.ticksUntilNormalSpeed <0){
                player.speedDown();
            }
        }
        
        //Handle key input
        synchronized (GameData.keys) {
            players: for (int playerNumber = 0; playerNumber < GameData.playerCount; playerNumber++) {
                Player player = GameData.players.get(playerNumber);
                //Iterate through keys until one works
                for (Character key : GameData.keys.get(playerNumber)) {                            
                    switch (key) {
                        case 'W':
                        case 'w':
                            if (player.move(Direction.UP)) continue players;
                        break;

                        case 'A':
                        case 'a':
                            if (player.move(Direction.LEFT)) continue players;
                        break;

                        case 'S':
                        case 's':
                            if (player.move(Direction.DOWN)) continue players;
                        break;

                        case 'D':
                        case 'd':
                            if (player.move(Direction.RIGHT)) continue players;
                        break;

                        case 'E':
                        case 'e':
                            player.putItem(Item.BOMB); //puts bomb
                        break;
                        
                        case 'R':
                        case 'r':
                            player.putItem(Item.SUPERBOMB); //puts superbomb
                        break;
                        
                        case 'Q':
                        case 'q':
                            player.putItem(Item.BOX); //puts box
                        break;
                    }
                }
            }
        }
        
        //Update drawables
        synchronized (GameData.drawables) {
            GameData.removeDeadDrawables();
            //Send everything to clients
            NetworkData networkData = new NetworkData(GameData.drawables, GameData.map, System.currentTimeMillis());
            GameData.server.send(networkData);
        }
        checkEndConditions();
    }

    /**
     * Resize the GamePanel so it always has maximum size while still preserving
     * aspect ratio of map.
     */
    public void resizeGamePanel() {
        if (GameData.networkData == null) {
            System.out.println("Waiting for server");
            return;
        }
        Rectangle rect = GameData.gamePanel.getOptimalSize(pane.getWidth(), pane.getHeight());
        if (rect != null) {
            GameData.gamePanel.setBounds(rect);
        }
    }

    /**
     * Check if the game is over.
     */
    private void checkEndConditions() {
        if (shouldHaveExit) {
            // Single player
            Player player = GameData.players.firstElement();
            if (!player.isAlive()) {
                GameData.server.send("END You died.");
            } else {
                if (GameData.map.getField(player.x, player.y) instanceof Exit) {
                    GameData.server.send("END You won the game!");
                }
            }
        } else {
            //Many players
            LinkedList<Player> livingPlayers = new LinkedList<Player>();
            for (Player player : GameData.players)
                if (player.isAlive()) livingPlayers.add(player);
            if (livingPlayers.size() == 1) {
                Player player = livingPlayers.getFirst();
                GameData.server.send("END " + player.getName() + " won!");
            } else {
                if (livingPlayers.size() == 0) {
                    GameData.server.send("END Tie!");
                }
            }
        }
    }

    /**
     * Stops the game and cleans up.
     */
    public void stop() {
        if (GameData.server != null) GameData.server.stop();
        if (GameData.client != null) GameData.client.stop();
        if (GameData.frame != null) GameData.frame.dispose();
        GameData.server = null;
        GameData.client = null;
        GameData.menuFrame.setVisible(true);
        GameData.menuFrame.requestFocus();
    }
}
