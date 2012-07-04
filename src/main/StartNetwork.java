package main;

import game.GameData;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import network.Client;
import network.Server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StartNetwork {

    public final JFrame             networkWindow = new JFrame("Network settings");
    public static final JEditorPane log           = new JEditorPane();
    final JScrollPane               scrollPane    = new JScrollPane(log);
    public final File               logfile       = new File("log_test.txt");

    boolean                         AI            = false;
	
	public StartNetwork() {


            


       
	    
		//panels etc...
		JPanel base = new JPanel();
		JPanel buttonsStart = new JPanel();
		//final JCheckBox checkAI = new JCheckBox("AI? (host only)");
		final JButton buttonConnectGame = new JButton("Connect to server");
		final JButton buttonStartServer = new JButton("Start server");
		
		final JTextPane paneIP = new JTextPane();
		
		base.setLayout(new FlowLayout());
		buttonsStart.setLayout(new FlowLayout());
		
		buttonsStart.add(buttonConnectGame);
		buttonsStart.add(buttonStartServer);
		
		base.add(buttonsStart);
		base.add(scrollPane);
		base.add(paneIP);

		//editing JTextPane
		StyleContext.NamedStyle styleCenter = StyleContext.getDefaultStyleContext().new NamedStyle();
		StyleConstants.setAlignment(styleCenter, StyleConstants.ALIGN_CENTER);
		paneIP.setText("127.0.0.1");
		paneIP.setEditable(true);
		paneIP.setBorder(BorderFactory.createLineBorder(Color.black));
		paneIP.setLogicalStyle(styleCenter);
		
		log.setText("Server-Log:\n");
		log.setEditable(false);
				
		//start server
		ActionListener alServer = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
			    int port = 12345;
			    GameData.server = new Server(port);
			    new Thread(GameData.server).start();
			}
		};
		
        // connect to game
        ActionListener alConnect = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = 12345;
                String ip = paneIP.getText();
                GameData.client = new Client(ip, port);
                new Thread(GameData.client).start();
                if (!GameData.client.connect(1000)) {
                    System.out.println("Authentication failed");
                } else {
                    System.out.println("Successfully connected to server");
                }

                if (logfile.exists()) {
                    log.setText("");
                    BufferedReader reader;
                    try {
                        reader = new BufferedReader(new FileReader(logfile));
                        String line;
                        line = "";
                        while ((line = reader.readLine()) != null) {
                            log.setText(log.getText() + line + "\n");
                        }
                        reader.close();
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

            }
        };		
		//adding actionlisteners
		//buttonEditIP.addActionListener(alChange);
		buttonStartServer.addActionListener(alServer);
		buttonConnectGame.addActionListener(alConnect);
		//checkAI.addItemListener(ilAI);
		
		
		//setting up sizes to match everything
		buttonConnectGame.setPreferredSize(new Dimension(180, 70));
		buttonStartServer.setPreferredSize(new Dimension(180, 70));
		paneIP.setPreferredSize(new Dimension(350, 25));
		log.setPreferredSize(new Dimension(440, 200));
		networkWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		networkWindow.setSize(450, 400);
		networkWindow.setResizable(false);
		buttonsStart.setPreferredSize(new Dimension(450, 110));
		
		//gets dimensions from screen and calculates center
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - networkWindow.getWidth()/2, (int)screenSize.getHeight()/2 - networkWindow.getHeight()/2);
		
		networkWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
		networkWindow.add(base);
		networkWindow.setVisible(true);
	}

}
