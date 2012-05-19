package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;

import draw.Drawable;
import draw.GamePanel;
import map.ImageLoader;
import map.Map;

/**
 * This is where it begins
 */
public class StartBomberman implements KeyListener {

	public static void main(String[] args) {
		// Load images
	    // When using eclipse, drag and drop images into the src folder to add new images
		ImageLoader.loadImages();
        Map map = new Map(11, 11, true);
		
		LinkedList<Drawable> drawables = new LinkedList<Drawable>();
		GamePanel gamePanel = new GamePanel(map, drawables);
		// Create a window
		JFrame frame = new JFrame();
		// Make window close when everything is done
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = frame.getContentPane();
		// Add a game panel (BorderLayout is standard, other layouts might be
		// more flexible for menus)
		pane.add(gamePanel, BorderLayout.CENTER);
		// Make game panel visible
		gamePanel.setVisible(true);
		frame.pack();
		// Window of this size fits on my screen, so this size is good
		// If it is higher than 768 pixels it will be bad
		frame.setSize(900, 900);

		// Show window
		frame.setVisible(true);
	}

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

}
