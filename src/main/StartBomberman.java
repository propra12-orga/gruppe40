package main;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartBomberman {

    // TODO put menu here (or somewhere else, we just need a class to start
    // things for now until someone feels responsible doing something better)

    public static void main(String[] args) {
        // Create window
        JFrame frame = new JFrame();
        // Set size of window (change these values to whatever you want)
        frame.setSize(512, 512);
        // Make frame close when eveything is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.add(new JPanel());

        // Show window
        frame.setVisible(true);
    }

}
