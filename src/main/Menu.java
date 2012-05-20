package main;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;

public class Menu {
	
	static int x, y;
	
	public static void main(String[] args) {
		
		/******************************************************
		 * Initialize frame, labels, buttons and set settings *
		 ******************************************************/
		
		final CardLayout cards = new CardLayout();
		
		final JFrame base = new JFrame("Willkommen in der Bomberman-Beta");
		JPanel bomberman = new BombermanPanel();
		JPanel menu = new JPanel();
		JPanel buttons = new JPanel();
		JPanel buttonsSize = new JPanel();
		JLabel title = new JLabel("Bomberman");
		JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");
		
		final Dimension buttonSize = new Dimension(190,60);
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		final JButton buttonSP = new JButton("Starte Singleplayer");
		final JButton buttonMP = new JButton("Starte Multiplayer");
		final JButton buttonTutorial = new JButton("Steuerung ansehen");
		
		JRadioButton small = new JRadioButton("800 x 600", true);
		JRadioButton medium = new JRadioButton("1024 x 768");
		JRadioButton large = new JRadioButton("1280 x 1024");
		
		ButtonGroup gameSizes = new ButtonGroup();
		gameSizes.add(small);
		buttonsSize.add(small);
		gameSizes.add(medium);
		buttonsSize.add(medium);
		gameSizes.add(large);
		buttonsSize.add(large);
		
		base.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		base.addWindowListener(new MenuListener());
		//setting up layout
		base.setSize(600, 300);
		base.setLayout(cards);
		menu.setLayout(new GridLayout(4, 1));
		base.setLocation((int)screenSize.getWidth()/2 - base.getWidth()/2, (int)screenSize.getHeight()/2 - base.getHeight()/2);
		base.setResizable(false);
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
		menu.add(buttonsSize);
		
		base.add(menu, "menue");
		base.add(bomberman, "bomberman");
		menu.add(creators);
		
		//menu.pack();
		base.setVisible(true);
		cards.show(base.getContentPane(), "menue");
		
		/*************************************
		 * Actions taken when Button clicked *
		 *************************************/
		
		//Singleplayer
		ActionListener sp = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    base.setResizable(true);
				cards.show(base.getContentPane(), "bomberman");
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
		
		ActionListener butSmall = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				x = 800;
				y = 600;
			}
		};
		
		ActionListener butMedium = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				x = 1024;
				y = 768;
			}
		};
		
		ActionListener butLarge = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				x = 1280;
				y = 1024;
			}
		};
		
		//adding listeners
		buttonSP.addActionListener(sp);
		buttonMP.addActionListener(mp);
		buttonTutorial.addActionListener(tut);
		small.addActionListener(butSmall);
		medium.addActionListener(butMedium);
		large.addActionListener(butLarge);
		
	}
	
}
