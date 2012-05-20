package main;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import draw.Drawable;
import draw.GamePanel;
import map.Map;

@SuppressWarnings("serial")
public class BombermanPanel extends JPanel implements KeyListener, ComponentListener {

    Map                  map       = new Map(11, 11, true);
    LinkedList<Drawable> drawables = new LinkedList<Drawable>();
    GamePanel            gamePanel = new GamePanel(map, drawables);

    public BombermanPanel() {
        // Register resize/show events
        addComponentListener(this);
        // Remove layout so things can be placed manually
        setLayout(null);
        // Add GamePanel
        add(gamePanel);
        // Set background color to black
        setBackground(Color.BLACK);
    }

    /**
     * Resizes the game panel so it stays square
     */
    public void resizeGamePanel() {
        int width = getWidth();
        int height = getHeight();
        if (width > height) {
            int blackBarWidth = (width - height) / 2;
            gamePanel.setBounds(blackBarWidth, 0, height, height);
        } else {
            int blackBarWidth = (height - width) / 2;
            gamePanel.setBounds(0, blackBarWidth, width, width);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resizeGamePanel();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        resizeGamePanel();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent arg0) {}

}
