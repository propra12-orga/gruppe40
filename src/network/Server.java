package network;

import game.GameData;
import game.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedList;

public class Server implements Runnable {

    private boolean                   running = true;
    private DatagramSocket            socket  = null;
    private LinkedList<SocketAddress> clients = new LinkedList<SocketAddress>();
    
    public void stop() {
        running = false;
        socket.close();
    }

    public Server(String ip, int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerCount() {
        return clients.size();
    }

    public void send(byte[] data) {
        if (!running) return;
        for (SocketAddress addr : clients) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length, addr);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Object object) {
        send(ObjectConverter.toByteArray(object));
    }

    public void handleKeyInput(KeyInput e, int playerNumber) {
        Player player = GameData.players.get(playerNumber);
        char key = e.e.getKeyChar();
        if (e.keyDown) {
            switch (key) {
                case 'W':
                case 'w':
                    player.startMove(1);
                break;

                case 'A':
                case 'a':
                    player.startMove(4);
                break;

                case 'S':
                case 's':
                    player.startMove(3);
                break;

                case 'D':
                case 'd':
                    player.startMove(2);
                break;

                case 'E':
                case 'e':
                    if (player.hasBomb()) player.putBomb();
                break;
            }
        } else {
            if (key != 'e' && key != 'E') player.stopMove();
        }
    }

    public void run() {
        final int BUFFER_SIZE = 1024 * 64;
        byte[] buffer = new byte[BUFFER_SIZE];
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                byte data[] = Arrays.copyOf(packet.getData(), packet.getLength());
                SocketAddress addr = packet.getSocketAddress();
                // If the same client gets added multiple times the reason for
                // this can be found here
                if (!clients.contains(addr)) {
                    clients.add(addr);
                    System.out.println("Got new client: " + addr);
                }
                Object object = ObjectConverter.getFromByteArray(data);
                if (object instanceof String) {
                    String s = (String) object;
                    System.out.println("Server received: " + s);
                    if (s.equals("AUTHENTICATION_REQUEST")) send("AUTHENTICATION");
                }
                if (object instanceof KeyInput) {
                    int playerNumber = clients.indexOf(addr);
                    handleKeyInput((KeyInput) object, playerNumber);
                }
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }else {
                    System.out.println("Server socket closed successfully");
                    return;
                }
            }
        }
    }
}
