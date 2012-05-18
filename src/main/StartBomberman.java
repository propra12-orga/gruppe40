package main;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import draw.GamePanel;
import map.ImageLoader;
import map.Map;

/**
 * This is where it begins
 */
public class StartBomberman {

    // TODO put menu here (or somewhere else, we just need a class to start
    // things for now until someone feels responsible making something better)

    public static void main(String[] args) {
        Map map = new Map(9, 9, true);
        GamePanel gamePanel = new GamePanel(map);
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
        frame.setSize(512, 512);

        // Show window
        frame.setVisible(true);
        // Load images
        ImageLoader.loadImages();
    }

}
