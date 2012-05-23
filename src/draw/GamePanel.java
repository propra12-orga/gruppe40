package draw;

import draw.Drawable;
import map.Map;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import java.util.ListIterator;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private Map                  map;
    private LinkedList<Drawable> drawables;

    public GamePanel(Map map, LinkedList<Drawable> drawables) {
        this.map = map;
        this.drawables = drawables;
    }

    private void drawTile(Graphics g, Image img, int x, int y, double width, double height) {
        // Dirty +1 hack to stop flickering
        g.drawImage(img, (int) (x * width), (int) (y * height), (int) width + 1, (int) height + 1, this);
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;

        int width = this.getWidth();
        int height = this.getHeight();
        // Clear background (just in case)
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);

        double tileWidth = width / (double) map.getWidth();
        double tileHeight = height / (double) map.getHeight();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                drawTile(g, map.getField(x, y).getImage(), x, y, tileWidth, tileHeight);
            }
        }
        synchronized (drawables) {
            ListIterator<Drawable> it = drawables.listIterator();
            while (it.hasNext()) {
                Drawable drawable = it.next();
                if (drawable.isExpired()) {
                    it.remove();
                }else {
                    drawTile(g, drawable.getImage(), drawable.getX(), drawable.getY(), tileWidth, tileHeight);
                }
            }
        }
    }
}
