package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class Server implements Runnable {
    
    private int port;
    private DatagramSocket socket = null;
    private LinkedList<InetAddress> clients = new LinkedList<InetAddress>();
    
    public Server(String ip, int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    public void send(byte[] data) {
        for (InetAddress addr : clients) {
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void send(Object object) {
        send(ObjectConverter.toByteArray(object));
    }

    public void run() {
        final int BUFFER_SIZE = 1024*64;
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                InetAddress addr = packet.getAddress();
                //If the same client gets added multiple times the reason for this can be found here
                if (!clients.contains(addr)) clients.add(addr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
