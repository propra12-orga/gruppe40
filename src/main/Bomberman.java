package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;

import network.KeyInput;
import network.NetworkData;

import draw.Drawable;
import draw.GamePanel;
import map.Exit;
import map.Map;
import game.Bomb;
import game.GameData;
import game.Player;

public class Bomberman {

    private Container pane;

    public Bomberman(JFrame menuFrame, boolean fullscreen, String mapName) {
        GameData.playerCount = 1;
        GameData.frame = new JFrame();
        GameData.menuFrame = menuFrame;
        GameData.bomberman = this;
        if (GameData.server != null) {
            GameData.playerCount = GameData.server.getPlayerCount();
            GameData.drawables = new LinkedList<Drawable>();
            GameData.bombs = new LinkedList<Bomb>();
            GameData.players = new Vector<Player>();

            if (mapName.equals("Random")) {
                GameData.map = new Map(13, 13, GameData.playerCount == 1);
            } else {
                GameData.map = new Map(mapName);
            }
            // TODO non-hardcoded spawn locations
            int x = GameData.map.getWidth() - 2;
            int y = GameData.map.getHeight() - 2;
            if (GameData.playerCount > 0) GameData.players.add(new Player("Player 1", 1, 1, 200));
            if (GameData.playerCount > 1) GameData.players.add(new Player("Player 2", x, y, 200));
            if (GameData.playerCount > 2) GameData.players.add(new Player("Player 3", 1, y, 200));
            if (GameData.playerCount > 3) GameData.players.add(new Player("Player 4", x, 1, 200));
            // TODO more players?
        }

        GameData.gamePanel = new GamePanel();
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
            // This is just for milestone 2, will be changed later

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // TODO ask if user really wanted to press escape
                    stop();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (GameData.client == null) {
                    System.exit(1);
                }
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
                    NetworkData networkData = new NetworkData(GameData.drawables, GameData.map);
                    if (GameData.server != null) GameData.server.send(networkData);
                    GameData.frame.repaint();
                    checkEndConditions();
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
        gameLoop.start();
    }

    private int hash = 0;

    /**
     * Resize the GamePanel so it always has maximum size while still preserving
     * aspect ratio.
     */
    public void resizeGamePanel() {
        if (GameData.networkData == null) {
            System.out.println("Waiting for server");
            return;
        }
        Map map = GameData.networkData.map;
        int mx = map.getWidth();
        int my = map.getHeight();
        int width = pane.getWidth();
        int height = pane.getHeight();

        //Do not resize if nothing has changed
        int newHash = (mx << 25) | (my << 20) | (width << 10) | height;
        if (hash != newHash) hash = newHash;
        else return;

        // Calculate width required to display the image at full height while
        // preserving aspect ratio
        int maxWidth = height * mx / my;
        // If it fits on screen draw it
        if (maxWidth <= width) {
            int blackBarWidth = (width - maxWidth) / 2;
            GameData.gamePanel.setBounds(blackBarWidth, 0, maxWidth, height);
        } else {
            // If it does not fit calculate height which preserves aspect ratio
            // and use that instead
            int maxHeight = width * my / mx;
            int blackBarHeight = (height - maxHeight) / 2;
            GameData.gamePanel.setBounds(0, blackBarHeight, width, maxHeight);
        }
    }

    private void checkEndConditions() {
        int playerCount = GameData.playerCount;
        if (playerCount == 1) {
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

    public void stop() {
        if (GameData.server != null) GameData.server.stop();
        if (GameData.client != null) GameData.client.stop();
        if (GameData.frame != null) GameData.frame.dispose();
        GameData.menuFrame.setVisible(true);
    }
}
