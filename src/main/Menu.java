package main;

import game.GameData;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import map.MapEditor;
import network.Client;
import network.Server;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Menu {

    static boolean fullscreen = false, mapLoaded = false;
	static String mapName = "Random";
	static File mapFile;
	static Tutorial tut = null;
	static StartNetwork startNet = null;
	static MapEditor mapEdit = null;
	public final static File logfile = new File("log_test.txt");

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /******************************************************
         * Initialize frame, labels, buttons and set settings *
         ******************************************************/

        final CardLayout cards = new CardLayout();

        final JFrame base = new JFrame("Welcome to the Bomberman-Beta");
        JPanel menu = new JPanel();
        JPanel buttons = new JPanel();
        JPanel buttonsSize = new JPanel();
        JPanel network = new JPanel();
        JPanel chooseMap = new JPanel();
        JLabel title = new JLabel("Bomberman");
        JLabel mapNames = new JLabel("Map:");
        JLabel mapOr = new JLabel("  or  ");
        JLabel creators = new JLabel("Dominik Mehren, Lisa Rey, Philipp Kochanski, Sebastian Brink, Thomas Germer");

        final JCheckBox checkAI = new JCheckBox("AI", true);
        final Dimension dimButtonSize = new Dimension(190, 60);
        final Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final JButton buttonSP = new JButton("Start Singleplayer");
        // Redundant
        // final JButton buttonMP = new JButton("Start Multiplayer");
        final JButton buttonTutorial = new JButton("Controls");
        final JButton buttonNetwork = new JButton("Start Network Game");
        final JButton buttonEditor = new JButton("Open Map-Editor");
        final JButton buttonLoadMap = new JButton("Load Map");

        final JFileChooser fc = new JFileChooser();

        JRadioButton rbWindow = new JRadioButton("Windowed", true);
        JRadioButton rbFull = new JRadioButton("Fullscreen");

        // drop-down menu
        String[] mapList = { "Random", "Map1", "Map2", "Map3", "Map4" };
        JComboBox cbMapChoice = new JComboBox(mapList);

        // screen solution buttons
        ButtonGroup gameSizes = new ButtonGroup();
        gameSizes.add(rbWindow);
        buttonsSize.add(rbWindow);
        gameSizes.add(rbFull);
        buttonsSize.add(rbFull);

        buttonsSize.add(checkAI);

        // close window - adding options
        base.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        base.addWindowListener(new MenuListener());

        // setting up layout
        base.setSize(600, 450);
        base.setLayout(cards);
        menu.setLayout(new GridLayout(6, 1));
        base.setLocation((int) dimScreenSize.getWidth() / 2 - base.getWidth() / 2, (int) dimScreenSize.getHeight() / 2 - base.getHeight() / 2);
        base.setResizable(false);
        buttons.setLayout(new FlowLayout());
        title.setHorizontalAlignment(0);
        creators.setHorizontalAlignment(0);

        // setting up button size
        buttonSP.setPreferredSize(dimButtonSize);
        // Redundant
        // buttonMP.setPreferredSize(dimButtonSize);
        buttonTutorial.setPreferredSize(dimButtonSize);
        buttonNetwork.setPreferredSize(dimButtonSize);
        buttonEditor.setPreferredSize(dimButtonSize);
        buttonLoadMap.setPreferredSize(new Dimension(175, 25));

        title.setFont(new Font("Arial", Font.PLAIN, 72));

        // adding buttons to panel
        buttons.add(buttonSP);
        // Redundant
        // buttons.add(buttonMP);
        buttons.add(buttonTutorial);

        // adding drop-down menu to panel
        chooseMap.add(mapNames);
        chooseMap.add(cbMapChoice);
        chooseMap.add(mapOr);
        chooseMap.add(buttonLoadMap);

        // adding button to networkpanel
        network.add(buttonNetwork);
        network.add(buttonEditor);

        // adding everything to Frame
        menu.add(title);
        menu.add(buttons);
        menu.add(buttonsSize);
        menu.add(chooseMap);
        menu.add(network);
        menu.add(creators);

        base.add(menu, "menu");

        base.setVisible(true);
        cards.show(base.getContentPane(), "menu");

        try {
            System.setOut(new PrintStream(new FileOutputStream("log_test.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*****************************************************
         * Actionlistner - Actions taken when Button clicked *
         *****************************************************/

        // singleplayer
        ActionListener alSP = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameData.client == null) {
                    // If there is no network connection host a local server
                    int port = 12345;
                    String ip = "127.0.0.1";
                    GameData.server = new Server(port);
                    GameData.client = new Client(ip, port);
                    new Thread(GameData.server).start();
                    new Thread(GameData.client).start();
                    if (!GameData.client.connect(1000)) {
                        System.err.println("Authentication for localhost failed (no idea how this could happen)");
                        return;
                    }
                }
                base.setVisible(false);
                new Bomberman(base, fullscreen, mapName, useAI);
            }
        };
			    
				if(logfile.exists()){
	                 
                     BufferedReader reader;
                     try {
                         reader = new BufferedReader(new FileReader(logfile));
                         String line;
                         line = "";
                         while((line = reader.readLine( )) != null){
                             StartNetwork.log.setText(StartNetwork.log.getText()+ line+"\n");
                         }
                         reader.close( );
                     } catch (FileNotFoundException e1) {
                         // TODO Auto-generated catch block
                         e1.printStackTrace();
                     }
             catch (IOException e1) {
                 // TODO Auto-generated catch block
                 e1.printStackTrace();
             }

             }
			}
		};
        // multiplayer
        // ActionListener alMP = new ActionListener() {
        // @Override public void actionPerformed(ActionEvent e) {
        // base.setVisible(false);
        // new Bomberman(base, fullscreen, mapName);
        // }
        // };

        // tutorial
        ActionListener alTut = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tut == null) {
                    tut = new Tutorial("tutorialtext.txt");
                    tut.tutWindow.setVisible(true);
                } else if (!tut.tutWindow.isVisible()) {
                    tut.tutWindow.setVisible(true);
                }
            }
        };

        // network
        ActionListener alNetwork = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startNet == null) {
                    startNet = new StartNetwork();
                    startNet.networkWindow.setVisible(true);
                } else if (!startNet.networkWindow.isVisible()) {
                    startNet.networkWindow.setVisible(true);
                }
            }
        };

        // mapeditor
        ActionListener alEditor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mapEdit == null) {
                    mapEdit = new MapEditor();
                    mapEdit.frame.setVisible(true);
                } else if (!mapEdit.frame.isVisible()) {
                    mapEdit.frame.setVisible(true);
                }
            }
        };

        // window mode
        ActionListener alWindow = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullscreen = false;
                base.requestFocus();
            }
        };

        // fullscreen
        ActionListener alFull = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullscreen = true;
                base.requestFocus();
            }
        };

        // choose map
        ActionListener alMapChoice = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                mapName = (String) comboBox.getSelectedItem();
                base.requestFocus();
            }
        };

        // load custom map
        ActionListener alLoadMap = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // map already loaded -> "erase" it
                if (mapLoaded == true) {
                    mapLoaded = false;
                    buttonLoadMap.setText("rejected - load new map?");
                }
                // no map loaded yet
                else {
                    int returnVal = fc.showOpenDialog(base);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        // make sure user read a .xml file
                        if (fc.getSelectedFile().getName().toLowerCase().endsWith(".xml")) {
                            mapFile = fc.getSelectedFile();
                            buttonLoadMap.setText("success - reject?");
                            mapLoaded = true;
                        }
                        // file isn't a .xml file
                        else {
                            buttonLoadMap.setText("Wrong File Format");
                        }
                    }
                }
                base.requestFocus();
            }
        };

        ItemListener ilAI = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItemSelectable() == checkAI) {
                    useAI = true;
                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    useAI = false;
                }
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    UIManager.put("OptionPane.cancelButtonText", "Cancel");
                    UIManager.put("OptionPane.noButtonText", "No");
                    UIManager.put("OptionPane.yesButtonText", "Yes");
                    UIManager.put("OptionPane.Title", "Please select");

                    int option = JOptionPane.showConfirmDialog(base, "Close window?", "Exit", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.OK_OPTION) System.exit(0);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
            
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        base.setFocusable(true);
        base.addKeyListener(keyListener);

        WindowStateListener returnListener = new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                base.requestFocus();
            }
        };
        base.addWindowStateListener(returnListener);

        // adding listeners
        buttonSP.addActionListener(alSP);
        // Redundant
        // buttonMP.addActionListener(alMP);
        buttonTutorial.addActionListener(alTut);
        buttonNetwork.addActionListener(alNetwork);
        buttonEditor.addActionListener(alEditor);
        rbWindow.addActionListener(alWindow);
        rbFull.addActionListener(alFull);
        cbMapChoice.addActionListener(alMapChoice);
        buttonLoadMap.addActionListener(alLoadMap);
        checkAI.addItemListener(ilAI);

    }

}
