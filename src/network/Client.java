package network;

import game.GameData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Client implements Runnable {
    
    private InetAddress addr;
    private int port;
    private boolean authenticated = false;
    private boolean running = true;
    private DatagramSocket socket;
    
    public Client(String ip, int port) {
        try {
            addr = InetAddress.getByName(ip);
            this.port = port;
            this.socket = new DatagramSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    public void send(Object object) {
        send(ObjectConverter.toByteArray(object));
    }

    public void send(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        final int BUFFER_SIZE = 1024*64;
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                byte data[] = Arrays.copyOf(packet.getData(), packet.getLength());
                Object object = ObjectConverter.getFromByteArray(data);
                if (object instanceof String) {
                    String s = (String)object;
                    System.out.println("Client received: " + s);
                    if (s.equals("AUTHENTICATION")) {
                        authenticated = true;
                    }else {
                        int spacePos = s.indexOf(' ');
                        String pre = s.substring(0, spacePos);
                        String post = s.substring(spacePos+1, s.length());
                        if (pre.equals("END")) {
                            GameData.running = false;
                            JOptionPane.showMessageDialog(GameData.frame, post);
                            GameData.frame.dispose();
                            GameData.menuFrame.setVisible(true);
                            GameData.client = null;
                            if (GameData.server != null) {
                                GameData.server.stop();
                                GameData.server = null;
                            }
                            return;
                        }
                    }
                }
                if (object instanceof NetworkData) {
                    GameData.networkData = (NetworkData)object;
                    if (GameData.frame != null) {
                        GameData.bomberman.resizeGamePanel();
                        GameData.frame.repaint();
                    }
                }
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }else {
                    System.out.println("Client socket closed successfully");
                    return;
                }
            }
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean connect(long timeout) {
        long t = System.currentTimeMillis();
        do {
            send("AUTHENTICATION_REQUEST");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (System.currentTimeMillis() - t > timeout) return false;
        } while (!isAuthenticated());
        return true;
    }

    public void stop() {
        running = false;
        socket.close();
    }

}
