package draw;

import draw.Drawable;
import map.Map;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private Map                  map;
    private LinkedList<Drawable> drawables;

    public GamePanel(Map map, LinkedList<Drawable> drawables) {
        this.map = map;
        this.drawables = drawables;
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;

        int tileWidth = this.getWidth() / map.getWidth();
        int tileHeight = this.getHeight() / map.getHeight();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                g.drawImage(map.getField(x, y).getImage(), tileWidth * x, tileHeight * y, tileWidth, tileHeight, this);
            }
        }
        synchronized (drawables) {
            for (Drawable drawable : drawables) {
                g.drawImage(drawable.getImage(), drawable.getX() * tileWidth, drawable.getY() * tileHeight, tileWidth, tileHeight, this);
            }
        }
    }
}
