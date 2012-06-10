package main;

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

    private JFrame               menuFrame;
    private JFrame               frame     = new JFrame();
    private Container            pane      = frame.getContentPane();
    private Map                  map       = new Map(11, 11, true);
    private LinkedList<Drawable> drawables = new LinkedList<Drawable>();
    private GamePanel            gamePanel = new GamePanel(map, drawables);
    private Player               player1   = new Player(1, 1, 200, map, drawables, frame);

    public Bomberman(JFrame menuFrame, int width, int height, boolean fullscreen) {
        this.menuFrame = menuFrame;

        // TODO ask first
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (fullscreen) {
            // Remove title bar etc.
            frame.setUndecorated(true);
            // Set size to fill the whole screen
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // Make fullscreen window stay on top of other windows
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
			System.out.println("keyPressed: "+e.getKeyChar());
                // TODO decide how to design server/client stuff
                // there shouldn't be a direct connection between classes here
                switch (e.getKeyChar()) {
                    case 'E':
                    case 'e':
                        if (player1.hasBomb()) player1.putBomb();
                    break;

                    case KeyEvent.VK_ESCAPE:
                        exit();
                    break;

                    default:
                    break;
                }
                // Get field the player is standing on
                Field field = map.getField(player1.getX(), player1.getY());
                // If player stands on exit
                if (field instanceof Exit) {
                    // Show win dialog
                    JOptionPane.showMessageDialog(frame, "You won!");
                    exit();
                }
                // Force repaint to make sure everything is drawn
                // If there are no animated gifs visible nothing will be updated automatically
                frame.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
			System.out.println("keyReleased: "+e.getKeyChar());
			    switch (e.getKeyChar()) {
                    
                    case 'W':
                    case 'w':
                    case 'A':
                    case 'a':
                    case 'S':
                    case 's':
                    case 'D':
                    case 'd':
                        player1.stopMove();
                    break;

                    default:
                    break;
                }
				
			    // Get field the player is standing on
                Field field = map.getField(player1.getX(), player1.getY());
                // If player stands on exit
                if (field instanceof Exit) {
					// Show win dialog
                    JOptionPane.showMessageDialog(frame, "You won!");
                    exit();
                }
                // Force repaint to make sure everything is drawn
                // If there are no animated gifs visible nothing will be updated automatically
                frame.repaint();
			}

            @Override
            public void keyTyped(KeyEvent e) {               
    			System.out.println("keyTyped: "+e.getKeyChar());
       			switch (e.getKeyChar()) {
                    
                    case 'W':
                    case 'w':
                        player1.startMove(1);
                    break;
    
                    case 'A':
                    case 'a':
                        player1.startMove(4);
                    break;
    
                    case 'S':
                    case 's':
                        player1.startMove(3);
                    break;
    
                    case 'D':
                    case 'd':
                        player1.startMove(2);
                    break;
    
                    default:
                    break;
                }
                // Get field the player is standing on
                Field field = map.getField(player1.getX(), player1.getY());
                // If player stands on exit
                if (field instanceof Exit) {
                    // Show win dialog
                    JOptionPane.showMessageDialog(frame, "You won!");
                    exit();
                }
                // Force repaint to make sure everything is drawn
                // If there are no animated gifs visible nothing will be updated automatically
                frame.repaint();
			}
        };
        frame.addKeyListener(keyListener);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    private void exit() {
        player1.stopMove();
        menuFrame.setVisible(true);
        frame.dispose();
    }

    /**
     * Resizes the game panel so it stays square
     * TODO preserver other aspect ratios if map is not square
     */
    public void resizeGamePanel() {
        int mx = map.getWidth();
        int my = map.getHeight();
        int width = pane.getWidth();
        int height = pane.getHeight();
        //Calculate width required to display the image at full height while preserving aspect ratio
        int maxWidth = height*mx/my;
        //If it fits on screen draw it
        if (maxWidth <= width) {
            int blackBarWidth = (width - maxWidth)/2;
            gamePanel.setBounds(blackBarWidth, 0, maxWidth, height);
        }else {
            //If it does not fit calculate height which preserves aspect ratio and use that instead
            int maxHeight = width*my/mx;
            int blackBarHeight = (height - maxHeight)/2;
            gamePanel.setBounds(0, blackBarHeight, width, maxHeight);
        }
    }

}
