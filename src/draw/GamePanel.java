package draw;

import map.ImageLoader;
import map.Map;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    
    Map map;

    public GamePanel(Map map) {
        this.map = map;
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        
        int w = this.getWidth();
        int h = this.getHeight();
        
        // Example usage
        // Drawing a tile filling a quarter of the screen at the center of the screen
        g.drawImage(ImageLoader.getNormalWallImage(), w/4, h/4, w/2, h/2, this);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                int x1 = (int) (x * (700 / (double) map.getWidth()));
                int y1 = (int) (y * (700 / (double) map.getHeight()));
                // g.drawImage(img, x1, y1,(int) ((700 / (double)
                // map.getWidth())+1), (int) ((700 / (double) map.getHeight())),
                // this);
                // g.drawImage(map.getField(x, y).getImage(), x1, y1,(int) ((700
                // / (double) map.getWidth())+1), (int) ((700 / (double)
                // map.getHeight())), this);

            }
        }
    }
}
