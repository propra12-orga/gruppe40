package main;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNetwork {
	
	public StartNetwork() {
		JFrame networkWindow = new JFrame("Netzwerkeinstellungen");
		JPanel base = new JPanel();
		JPanel buttonsStart = new JPanel();
		JPanel ipChange = new JPanel();
		final JButton startGame = new JButton("Verbinde zum Spiel");
		final JButton startServer = new JButton("Server starten");
		final JButton editIP = new JButton("IP aendern");
		final JTextArea ipText = new JTextArea();
		String iP;
		
		base.setLayout(new GridLayout(2,1));
		buttonsStart.setLayout(new FlowLayout());
		ipChange.setLayout(new FlowLayout());
		Dimension defaultSize = new Dimension(150,70);
		ipText.setPreferredSize(defaultSize);
		startGame.setPreferredSize(defaultSize);
		startServer.setPreferredSize(defaultSize);
		editIP.setPreferredSize(defaultSize);
		
		buttonsStart.add(startGame);
		buttonsStart.add(startServer);
		
		ipChange.add(ipText);
		ipChange.add(editIP);
		
		base.add(buttonsStart);
		base.add(ipChange);

		//editing JTextArea
		ipText.setText("127.0.0.1");
		ipText.setEditable(true);
		ipText.setFont(new Font("Arial", Font.PLAIN, 24));
		
		ActionListener alChange = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				iP = ipText.getText();
			}
		};
		
		editIP.addActionListener(alChange);
		
		//setting popup size to match JTextArea
		networkWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		networkWindow.setSize(400, 200);
		networkWindow.setVisible(true);
		networkWindow.setResizable(false);
		ipChange.setAlignmentX(0);
		ipChange.setAlignmentY(0);
		buttonsStart.setSize(200, 200);
		
		//gets dimensions from JTextArea (calculation based on font-size etc.)
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - networkWindow.getWidth()/2, (int)screenSize.getHeight()/2 - networkWindow.getHeight()/2);
		
		networkWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
		
		//class popup is protected, so we use a factory to obtain instances
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup tutorial = factory.getPopup(networkWindow, base, (int)center.getWidth()+10, (int)center.getHeight()+30);
		tutorial.show();
	}

}
