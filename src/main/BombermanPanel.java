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
import map.ImageLoader;
import map.Map;
import game.Player;

@SuppressWarnings("serial")
public class BombermanPanel extends JPanel implements ComponentListener, KeyListener {

    Map                  map       = new Map(11, 11, true);
    LinkedList<Drawable> drawables = new LinkedList<Drawable>();
    GamePanel            gamePanel = new GamePanel(map, drawables);
    Player player1 = new Player(1, 1, 1, map, ImageLoader.getPlayerImage());

    public BombermanPanel() {
        // Register resize/show events
        addComponentListener(this);
        // Remove layout so things can be placed manually
        setLayout(null);
        // Add GamePanel
        add(gamePanel);
        // Set background color to black
        setBackground(Color.BLACK);
        // add Player 1
        drawables.add(player1);
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
    public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()) {
		    case 'E':
		    case 'e':
		    	//
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
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
