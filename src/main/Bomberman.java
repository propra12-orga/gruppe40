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
import javax.swing.JOptionPane;

import draw.Drawable;
import draw.GamePanel;
import map.Exit;
import map.Field;
import map.Map;
import game.Bomb;
import game.GameData;
import game.Player;

public class Bomberman {

    private Container pane;
    private JFrame menuFrame;
    private GameData data;
    // This is just for milestone 2, will be changed later
    private static boolean singlePlayer;

    public Bomberman(JFrame menuFrame, int width, int height,
            boolean fullscreen, boolean singlePlayer, String mapName) {
        Bomberman.singlePlayer = singlePlayer;
        this.menuFrame = menuFrame;

        data = new GameData();
        data.gameOver = false;
        data.frame = new JFrame();
        data.drawables = new LinkedList<Drawable>();
        data.bombs = new LinkedList<Bomb>();
        data.players = new Vector<Player>();
        data.bomberman = this;
        switch(mapName) {
        case "Zufall":
        	data.map = new Map(11, 11, singlePlayer);
        	break;
        default:
        	data.map = new Map(System.getProperty("user.dir")+"\\bin\\xml_parser\\"+mapName+".xml");
        	break;
        }
        
        data.gamePanel = new GamePanel(data);
        data.players.add(new Player("Player 1", 1, 1, 200, data));
        if (!singlePlayer)
            data.players.add(new Player("Player 2", 9, 9, 200, data));

        pane = data.frame.getContentPane();

        data.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (fullscreen) {
            // Remove title bar etc.
            data.frame.setUndecorated(true);
            // Set size to fill the whole screen
            data.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // Make fullscreen window stay on top of other windows
            data.frame.setAlwaysOnTop(true);
        }

        // Remove layout so things can be placed manually
        pane.setLayout(null);
        // Add GamePanel
        pane.add(data.gamePanel);
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
            public void componentHidden(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

        };
        pane.addComponentListener(componentListener);

        KeyListener keyListener = new KeyListener() {
            // This is just for milestone 2, will be changed later

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyChar()) {

                case 'W':
                case 'w':
                case 'A':
                case 'a':
                case 'S':
                case 's':
                case 'D':
                case 'd':
                    data.players.get(0).stopMove();
                    break;

                case 'I':
                case 'i':
                case 'J':
                case 'j':
                case 'K':
                case 'k':
                case 'L':
                case 'l':
                    if (!Bomberman.singlePlayer)
                        data.players.get(1).stopMove();
                    break;

                case KeyEvent.VK_ESCAPE:
                    exit("Game closed");
                    break;

                default:
                    break;
                }
                forceRepaint();
            }

            @Override
            public void keyTyped(KeyEvent e) {

                switch (e.getKeyChar()) {

                case 'W':
                case 'w':
                    data.players.get(0).startMove(1);
                    break;

                case 'A':
                case 'a':
                    data.players.get(0).startMove(4);
                    break;

                case 'S':
                case 's':
                    data.players.get(0).startMove(3);
                    break;

                case 'D':
                case 'd':
                    data.players.get(0).startMove(2);
                    break;

                case 'E':
                case 'e':
                    if (data.players.get(0).hasBomb())
                        data.players.get(0).putBomb();
                    break;

                // Horrible code, I know
                // Will be replaced with network at milestone 3
                case 'I':
                case 'i':
                    if (!Bomberman.singlePlayer)
                        data.players.get(1).startMove(1);
                    break;

                case 'J':
                case 'j':
                    if (!Bomberman.singlePlayer)
                        data.players.get(1).startMove(4);
                    break;

                case 'K':
                case 'k':
                    if (!Bomberman.singlePlayer)
                        data.players.get(1).startMove(3);
                    break;

                case 'L':
                case 'l':
                    if (!Bomberman.singlePlayer)
                        data.players.get(1).startMove(2);
                    break;

                case 'O':
                case 'o':
                    if (!Bomberman.singlePlayer)
                        if (data.players.get(1).hasBomb())
                            data.players.get(1).putBomb();
                    break;

                default:
                    break;
                }
                forceRepaint();
            }
        };
        data.frame.addKeyListener(keyListener);
        data.frame.setSize(width, height);
        data.frame.setVisible(true);
    }

    private void exit(String message) {
        if (data.gameOver)
            return;
        data.gameOver = true;
        // Show win dialog
        JOptionPane.showMessageDialog(data.frame, message);
        for (Player player : data.players) {
            player.stopMove();
        }
        data.frame.dispose();
        menuFrame.setVisible(true);
    }

    /**
     * Resize the GamePanel so it always has maximum size while still preserving
     * aspect ratio.
     */
    public void resizeGamePanel() {
        int mx = data.map.getWidth();
        int my = data.map.getHeight();
        int width = pane.getWidth();
        int height = pane.getHeight();
        // Calculate width required to display the image at full height while
        // preserving aspect ratio
        int maxWidth = height * mx / my;
        // If it fits on screen draw it
        if (maxWidth <= width) {
            int blackBarWidth = (width - maxWidth) / 2;
            data.gamePanel.setBounds(blackBarWidth, 0, maxWidth, height);
        } else {
            // If it does not fit calculate height which preserves aspect ratio
            // and use that instead
            int maxHeight = width * my / mx;
            int blackBarHeight = (height - maxHeight) / 2;
            data.gamePanel.setBounds(0, blackBarHeight, width, maxHeight);
        }
    }

    /**
     * Force repaint to make sure everything is drawn. If there are no animated
     * gifs visible nothing will be updated automatically.
     */
    public void forceRepaint() {
        data.frame.repaint();
    }

    /**
     * Repaints the JFrame and checks if game is over.
     */
    public void update() {
        if (data.gameOver)
            return;
        // Delay update so tie can actually happen
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinkedList<Player> alive = new LinkedList<Player>();
        for (Player player : data.players) {
            // Count living players
            if (player.isAlive())
                alive.add(player);

            if (singlePlayer) {
                // Get field the player is standing on
                Field field = data.map.getField(data.players.get(0).getX(),
                        data.players.get(0).getY());
                // If player stands on exit
                if (field instanceof Exit) {
                    // If there is an exit it should be single player
                    exit("You won!");
                }
            }
        }
        switch (alive.size()) {
        case 0:
            if (singlePlayer)
                exit("You died");
            else
                exit("Tie");
            break;

        case 1:

            if (!singlePlayer)
                exit(alive.getFirst().getName() + " won the game");
            break;

        default:
            break;
        }

    }

}
