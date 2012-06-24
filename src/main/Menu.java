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
	
	static int x = 650, y = 650;
	// TODO button to (de)select fullscreen
	static boolean fullscreen = false;
	static String mapName = "Zufall";
	
	public static void main(String[] args) {
		
		/******************************************************
		 * Initialize frame, labels, buttons and set settings *
		 ******************************************************/
		
		final CardLayout cards = new CardLayout();
		
		final JFrame base = new JFrame("Willkommen in der Bomberman-Beta");
		JPanel menu = new JPanel();
		JPanel buttons = new JPanel();
		JPanel buttonsSize = new JPanel();
		JPanel network = new JPanel();
		JPanel chooseMap = new JPanel();
		JLabel title = new JLabel("Bomberman");
		JLabel mapNames = new JLabel("Karte:");
		JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");
		
		final Dimension dimButtonSize = new Dimension(190,60);
		final Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		final JButton buttonSP = new JButton("Starte Singleplayer");
		final JButton buttonMP = new JButton("Starte Multiplayer");
		final JButton buttonTutorial = new JButton("Steuerung ansehen");
		final JButton buttonNetwork = new JButton("Netzwerkspiel starten");
		
		JRadioButton rbWindow = new JRadioButton("Fenstermodus", true);
		JRadioButton rbFull = new JRadioButton("Vollbild");
		
		//drop-down menu
		String[] mapList = {"Zufall", "Karte1", "Karte2", "Karte3", "Karte4"};
		JComboBox cbMapChoice = new JComboBox(mapList);
		
		//screen solution buttons
		ButtonGroup gameSizes = new ButtonGroup();
		gameSizes.add(rbWindow);
		buttonsSize.add(rbWindow);
		gameSizes.add(rbFull);
		buttonsSize.add(rbFull);
		
		//close window - adding options
		base.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		base.addWindowListener(new MenuListener());
		
		//setting up layout
		base.setSize(600, 450);
		base.setLayout(cards);
		menu.setLayout(new GridLayout(6, 1));
		base.setLocation((int)dimScreenSize.getWidth()/2 - base.getWidth()/2, (int)dimScreenSize.getHeight()/2 - base.getHeight()/2);
		base.setResizable(false);
		buttons.setLayout(new FlowLayout());
		title.setHorizontalAlignment(0);
		creators.setHorizontalAlignment(0);
		
        //setting up button size
		buttonSP.setPreferredSize(dimButtonSize);
		buttonMP.setPreferredSize(dimButtonSize);
		buttonTutorial.setPreferredSize(dimButtonSize);
		buttonNetwork.setPreferredSize(dimButtonSize);
		
		title.setFont(new Font("Arial", Font.PLAIN, 72));
		
		//adding buttons to panel
		buttons.add(buttonSP);
		buttons.add(buttonMP);
		buttons.add(buttonTutorial);
		
		//adding drop-down menu to panel
		chooseMap.add(mapNames);
		chooseMap.add(cbMapChoice);
		
		//adding button to networkpanel
		network.add(buttonNetwork);
		
		//adding everything to Frame
		menu.add(title);
		menu.add(buttons);
		menu.add(buttonsSize);
		menu.add(chooseMap);
		menu.add(network);
		menu.add(creators);
		
		base.add(menu, "menue");
		
		//menu.pack();
		base.setVisible(true);
		cards.show(base.getContentPane(), "menue");
		
		/*****************************************************
		 * Actionlistner - Actions taken when Button clicked *
		 *****************************************************/
		
		//Singleplayer
		ActionListener alSP = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    base.setVisible(false);
			    boolean singlePlayer = true;
			    new Bomberman(base, x, y, fullscreen, singlePlayer, mapName);
			}
		};
		
		//Multiplayer
		ActionListener alMP = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    base.setVisible(false);
                boolean singlePlayer = false;
                new Bomberman(base, x, y, fullscreen, singlePlayer, mapName);
			}
		};
		
		//Tutorial
		ActionListener alTut = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new Tutorial("tutorialtext.txt");
			}
		};
		
		//Netzwerk
		ActionListener alNetwork = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new StartNetwork();
			}
		};
		
		ActionListener alWindow = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				fullscreen = false;
			}
		};
		
		ActionListener alFull = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				fullscreen = true;
			}
		};
		
		ActionListener alMapChoice = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox)e.getSource();
				mapName = (String)comboBox.getSelectedItem();
			}
		};
		
		//adding listeners
		buttonSP.addActionListener(alSP);
		buttonMP.addActionListener(alMP);
		buttonTutorial.addActionListener(alTut);
		buttonNetwork.addActionListener(alNetwork);
		rbWindow.addActionListener(alWindow);
		rbFull.addActionListener(alFull);
		cbMapChoice.addActionListener(alMapChoice);
		
	}
	
}
