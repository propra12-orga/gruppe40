package view;

import game.Game;
import game.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JPanel;

import network.Client;
import network.Server;
import data.Data;
import data.Udp;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

    public MenuPanel() {
        setBounds(0, 0, Data.width, Data.height);

        final JButton clientButton = new JButton("Connect Client");
        final JButton serverButton = new JButton("Start Server");
        add(clientButton);
        add(serverButton);

        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // TODO move this to a new thread to keep UI responsive
                    clientButton.setText("Connecting...");
                    Data.mainWindow.repaint();
                    Data.menuPanel.repaint();
                    Data.client = new Client("255.255.255.255", Udp.AUTHENTICATION_PORT);
                    Data.client.start();

                    long t = System.nanoTime();
                    while (!Data.client.isAuthenticated()) {
                        try {
                            Thread.sleep(Data.UI_DELAY);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        if (System.nanoTime() - t > Udp.AUTHENTICATION_TIMEOUT * 1000000) {
                            clientButton.setText("Could not connect to server. Try again?");
                            return;
                        }
                    }

                    Data.layout.show(Data.content, "game");
                    Data.mainWindow.repaint();
                    Data.gamePanel.repaint();
                    Data.gamePanel.revalidate();
                } catch (UnknownHostException e1) {
                    clientButton.setText("Unknown Host. Try again?");
                    e1.printStackTrace();
                } catch (SocketException e1) {
                    clientButton.setText("Failed. Try again?");
                    e1.printStackTrace();
                }
            }
        });

        serverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Data.server == null) {
                    // If there is no server yet
                    try {
                        Data.server = new Server(Udp.AUTHENTICATION_PORT);
                        Data.server.start();

                        int w = Data.w;
                        int h = Data.h;
                        // Generate Map data
                        int[][] data = new int[w][h];
                        for (int x = 0; x < w; x++) {
                            for (int y = 0; y < h; y++) {
                                boolean wall = (x & 1) == 1 && (y & 1) == 1;
                                data[x][y] = wall ? Map.WALL1 : Map.EMPTY;
                                if (!wall) {
                                    if (Math.random() < 0.25 && x > 0 && y > 0 && x < w - 1 && y < h - 1) {
                                        data[x][y] = Map.WALL2;
                                    }
                                }
                            }
                        }

                        Data.game = new Game(new Map(w, h, data), Data.frameRate, 4);
                        Data.game.start();
                        serverButton.setText("Stop Server");
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    // Stop server and game
                    serverButton.setVisible(false);
                    Data.server.shutdown();
                    Data.game.shutdown();
                    // Hide button for safety
                    // TODO timeout for termination
                    while (Data.server.isAlive() || Data.game.isAlive()) {
                        try {
                            Thread.sleep(Data.UI_DELAY);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    Data.server = null;
                    Data.game = null;
                    serverButton.setVisible(true);
                    serverButton.setText("Start Server");
                }
            }
        });
    }

}
