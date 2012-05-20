package main;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;

public class Menu {
	
	public static void main(String[] args) {
		/******************************************************
		 * Initialize frame, labels, buttons and set settings *
		 ******************************************************/
		
		JFrame menu = new JFrame("Willkommen in der Bomberman-Beta");
		JPanel buttons = new JPanel();
		JLabel title = new JLabel("Bomberman");
		JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");
		
		final Dimension buttonSize = new Dimension(190,60);
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		final JButton buttonSP = new JButton("Starte Singleplayer");
		final JButton buttonMP = new JButton("Starte Multiplayer");
		final JButton buttonTutorial = new JButton("Steuerung ansehen");
		
		menu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menu.addWindowListener(new MenuListener());
		//setting up layout
		menu.setSize(600, 300);
		menu.setLayout(new GridLayout(3, 1));
		menu.setLocation((int)screenSize.getWidth()/2 - menu.getWidth()/2, (int)screenSize.getHeight()/2 - menu.getHeight()/2);
		menu.setResizable(false);
		buttons.setLayout(new FlowLayout());
		title.setHorizontalAlignment(0);
		creators.setHorizontalAlignment(0);
		
		buttonSP.setPreferredSize(buttonSize);
		buttonMP.setPreferredSize(buttonSize);
		buttonTutorial.setPreferredSize(buttonSize);
		
		title.setFont(new Font("Arial", Font.PLAIN, 72));
		
		//adding Buttons to Panel
		buttons.add(buttonSP);
		buttons.add(buttonMP);
		buttons.add(buttonTutorial);
		
		//adding everything to Frame
		menu.add(title);
		menu.add(buttons);
		menu.add(creators);
		
		//menu.pack();
		menu.setVisible(true);
		
		/*************************************
		 * Actions taken when Button clicked *
		 *************************************/
		
		//Singleplayer
		ActionListener sp = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new StartBomberman();
			}
		};
		
		//Multiplayer coming soon
		ActionListener mp = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				//starte Multiplayer
			}
		};
		
		//Tutorial
		ActionListener tut = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new Tutorial("tutorialtext.txt");
			}
		};
		
		//adding listeners
		buttonSP.addActionListener(sp);
		buttonMP.addActionListener(mp);
		buttonTutorial.addActionListener(tut);
	}
	
}
