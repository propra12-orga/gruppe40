package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable {
    
    private InetAddress addr;
    private int port;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
