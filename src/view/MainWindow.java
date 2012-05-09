package view;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.JFrame;

import data.Data;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements KeyListener {

    public MainWindow() {
        //TODO implement optional FULLSCREEN
        //setExtendedState(Frame.MAXIMIZED_BOTH);
        //setUndecorated(true);  
        
        Data.mainWindow = this;
        setFocusable(true);
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Data.init();
        setSize(Data.width, Data.height);
        setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < Data.imagePaths.length; i++) {
            URL url = getClass().getClassLoader().getResource(Data.imagePaths[i]);
            if (url == null) {
                // TODO create error window
                System.err.println("failed to load image: " + Data.imagePaths[i]);
                System.exit(1);
            }
            Data.images[i] = toolkit.createImage(url);
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            long t = System.nanoTime();
            Data.shutdownEverything();
            while (!Data.everythingShutDown()) {
                try {
                    Thread.sleep(Data.UI_DELAY);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (System.nanoTime() - t > Data.SHUTDOWN_DELAY*1000*1000) {
                    System.err.println("Failed to close something");
                    System.exit(1);
                }
            }
            System.exit(0);
        }else {
            Data.client.sendKey(e.getKeyCode());
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}