package main;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

import java.awt.Dimension;
import java.io.File;

public class Menu {
	
	static boolean fullscreen = false, mapLoaded = false;
	static String mapName = "Zufall";
	static File mapFile;
	
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
		JLabel mapOr = new JLabel("  oder  ");
		JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");
		
		final Dimension dimButtonSize = new Dimension(190,60);
		final Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		final JButton buttonSP = new JButton("Starte Singleplayer");
		final JButton buttonMP = new JButton("Starte Multiplayer");
		final JButton buttonTutorial = new JButton("Steuerung ansehen");
		final JButton buttonNetwork = new JButton("Netzwerkspiel starten");
		final JButton buttonEditor = new JButton("Karteneditor �ffnen");
		final JButton buttonLoadMap = new JButton("Karte laden");
		
		final JFileChooser fc = new JFileChooser();
		
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
		buttonEditor.setPreferredSize(dimButtonSize);
		buttonLoadMap.setPreferredSize(new Dimension(175,25));
		
		title.setFont(new Font("Arial", Font.PLAIN, 72));
		
		//adding buttons to panel
		buttons.add(buttonSP);
		buttons.add(buttonMP);
		buttons.add(buttonTutorial);
		
		//adding drop-down menu to panel
		chooseMap.add(mapNames);
		chooseMap.add(cbMapChoice);
		chooseMap.add(mapOr);
		chooseMap.add(buttonLoadMap);
		
		//adding button to networkpanel
		network.add(buttonNetwork);
		network.add(buttonEditor);
		
		//adding everything to Frame
		menu.add(title);
		menu.add(buttons);
		menu.add(buttonsSize);
		menu.add(chooseMap);
		menu.add(network);
		menu.add(creators);
		
		base.add(menu, "menue");
		
		base.setVisible(true);
		cards.show(base.getContentPane(), "menue");
		
		
		/*****************************************************
		 * Actionlistner - Actions taken when Button clicked *
		 *****************************************************/
		
		//singleplayer
		ActionListener alSP = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    base.setVisible(false);
			    boolean singlePlayer = true;
			    new Bomberman(base, fullscreen, singlePlayer, mapName);
			}
		};
		
		//multiplayer
		ActionListener alMP = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    base.setVisible(false);
                boolean singlePlayer = false;
                new Bomberman(base, fullscreen, singlePlayer, mapName);
			}
		};
		
		//tutorial
		ActionListener alTut = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new Tutorial("tutorialtext.txt");
			}
		};
		
		//network
		ActionListener alNetwork = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new StartNetwork();
			}
		};
		
		//mapeditor
		ActionListener alEditor = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new MapEditor();
			}
		};
		
		//window mode
		ActionListener alWindow = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				fullscreen = false;
			}
		};
		
		//fullscreen
		ActionListener alFull = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				fullscreen = true;
			}
		};
		
		//choose map
		ActionListener alMapChoice = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox)e.getSource();
				mapName = (String)comboBox.getSelectedItem();
			}
		};
		
		//load custom map
		ActionListener alLoadMap = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {		
				//map already loaded -> "erase" it
				if (mapLoaded == true) {
					mapLoaded = false;
					buttonLoadMap.setText("verworfen - neu laden?");
				}
				//no map loaded yet
				else {
					int returnVal = fc.showOpenDialog(base);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						//make sure user read a .xml file
						if(fc.getSelectedFile().getName().toLowerCase().endsWith(".xml")) {
							mapFile = fc.getSelectedFile();
							buttonLoadMap.setText("geladen - verwerfen?");
							mapLoaded = true;
						}
						//file isn't a .xml file
						else {
							buttonLoadMap.setText("falsches Dateiformat");
						}
					}
				}
			}
		};
		
		//adding listeners
		buttonSP.addActionListener(alSP);
		buttonMP.addActionListener(alMP);
		buttonTutorial.addActionListener(alTut);
		buttonNetwork.addActionListener(alNetwork);
		buttonEditor.addActionListener(alEditor);
		rbWindow.addActionListener(alWindow);
		rbFull.addActionListener(alFull);
		cbMapChoice.addActionListener(alMapChoice);
		buttonLoadMap.addActionListener(alLoadMap);
		
	}
	
}
