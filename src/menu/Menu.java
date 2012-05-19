import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Menu {
	
	public static void main(String[] args) {
		/******************************************************
		 * Initialize frame, labels, buttons and set settings *
		 ******************************************************/
		
		JFrame menu = new JFrame("Willkommen in der Bomberman-Beta");
		JLabel title = new JLabel("Bomberman");
		JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");
	
		final JButton buttonSP = new JButton("Starte Singleplayer");
		final JButton buttonMP = new JButton("Starte Multiplayer");
		final JButton buttonTutorial = new JButton("Steuerung ansehen");
		
		menu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menu.setLayout(new GridLayout(3, 3, 5, 5));
		menu.addWindowListener(new MenuListener());
		
		menu.setSize(600, 400);
		menu.add(title);
		menu.add(buttonSP);
		menu.add(buttonMP);
		menu.add(buttonTutorial);
		menu.add(creators);
		
		//menu.pack();
		menu.setVisible(true);
		
		/*************************************
		 * Actions taken when Button clicked *
		 *************************************/
		
		//Singleplayer
		ActionListener sp = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
				new Tutorial();
			}
		};
		
		//adding listeners
		buttonSP.addActionListener(sp);
		buttonMP.addActionListener(mp);
		buttonTutorial.addActionListener(tut);
	}
	
}
