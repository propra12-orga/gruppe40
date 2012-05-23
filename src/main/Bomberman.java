package main;

import images.ImageLoader;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import draw.Drawable;
import draw.GamePanel;
import map.Exit;
import map.Field;
import map.Map;
import game.Player;

public class Bomberman {

    JFrame               menuFrame;
    JFrame               frame     = new JFrame();
    Container            pane      = frame.getContentPane();
    Map                  map       = new Map(11, 11);
    LinkedList<Drawable> drawables = new LinkedList<Drawable>();
    GamePanel            gamePanel = new GamePanel(map, drawables);
    Player               player1   = new Player(1, 1, 2, map, ImageLoader.getPlayerImage(), drawables);

    public Bomberman(JFrame menuFrame, int width, int height, boolean fullscreen) {
        this.menuFrame = menuFrame;

        // TODO ask first
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (fullscreen) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setAlwaysOnTop(true);
        }

        // Remove layout so things can be placed manually
        pane.setLayout(null);
        // Add GamePanel
        pane.add(gamePanel);
        // Set background color to black
        pane.setBackground(Color.BLACK);
        // add Player 1
        drawables.add(player1);

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
                // TODO decide how to design server/client stuff
                // there shouldn't be a direct connection between classes here
                switch (e.getKeyChar()) {
                    case 'E':
                    case 'e':
                        if (player1.hasBomb()) player1.putBomb();
                    break;

                    case 'W':
                    case 'w':
                        player1.move(0, -1);
                    break;

                    case 'A':
                    case 'a':
                        player1.move(-1, 0);
                    break;

                    case 'S':
                    case 's':
                        player1.move(0, 1);
                    break;

                    case 'D':
                    case 'd':
                        player1.move(1, 0);
                    break;

                    case KeyEvent.VK_ESCAPE:
                        exit();
                    break;

                    default:
                    break;
                }
                int x = player1.getX();
                int y = player1.getY();
                Field field = map.getField(x, y);
                if (field instanceof Exit) {
                    JOptionPane.showMessageDialog(frame, "You won!");
                    exit();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        };
        frame.addKeyListener(keyListener);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    private void exit() {
        // TODO ask
        menuFrame.setVisible(true);
        frame.dispose();
    }
    
    /**
     * Resizes the game panel so it stays square
     */
    public void resizeGamePanel() {
        int width = pane.getWidth();
        int height = pane.getHeight();
        if (width > height) {
            int blackBarWidth = (width - height) / 2;
            gamePanel.setBounds(blackBarWidth, 0, height, height);
        } else {
            int blackBarWidth = (height - width) / 2;
            gamePanel.setBounds(0, blackBarWidth, width, width);
        }
    }

}
