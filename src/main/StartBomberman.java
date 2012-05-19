package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import draw.Drawable;
import draw.GamePanel;
import map.ImageLoader;
import map.Map;

/**
 * This is where it begins
 */
public class StartBomberman implements MouseInputListener, KeyListener {

	// TODO put menu here (or somewhere else, we just need a class to start
	// things for now until someone feels responsible making something better)

	public static LinkedList<Drawable> drawables = new LinkedList<Drawable>();
	// Buffer for Drawables (required since nothing can be added to a LinkedList
	// while it is being iterated through)
	public static LinkedList<Drawable> buffer = new LinkedList<Drawable>();

	public static void main(String[] args) {
		// //////////////////

		// //////////////////
		// Load images
		// IMAGES STORED IN bin folder!!!
		ImageLoader.loadImages();
		Map map = new Map(11, 11, true);
		GamePanel gamePanel = new GamePanel(map, drawables, buffer);
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
		// //_____________________________________\\\\

		/*
		 * DESTROY EXAMPLE for (int y = 0; y < map.getHeight(); y++) { for (int
		 * x = 0; x < map.getWidth(); x++) { map.destroy(x, y, 10);
		 * 
		 * 
		 * } }
		 * 
		 * for (int y = 0; y < map.getHeight(); y++) { for (int x = 0; x <
		 * map.getWidth(); x++) { System.out.println(map.getField(x,
		 * y).getName());
		 * 
		 * } }
		 */

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
