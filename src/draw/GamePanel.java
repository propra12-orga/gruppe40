package draw;

import draw.Drawable;
import game.GameData;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ListIterator;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;

        int width = this.getWidth();
        int height = this.getHeight();
        // Clear background (just in case)
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);

        double tileWidth = width / (double) GameData.map.getWidth();
        double tileHeight = height / (double) GameData.map.getHeight();

        synchronized (GameData.drawables) {
            ListIterator<Drawable> it = GameData.drawables.listIterator();
            while (it.hasNext()) {
                Drawable drawable = it.next();
                boolean isUnusedField = drawable.isField && GameData.map.getField(drawable.x, drawable.y) != drawable;
                if (drawable.isExpired() || isUnusedField) {
                    it.remove();
                } else {
                    if (drawable.isVisible()) {
                        drawDrawable(g, drawable, tileWidth, tileHeight);
                    }
                }
            }
        }
    }

    private void drawDrawable(Graphics2D g, Drawable drawable, double width, double height) {
        int x = drawable.getX();
        int y = drawable.getY();
        Image image = drawable.getImage();
        int nw = drawable.getFrameCountX();
        int nh = drawable.getFrameCountY();
        int dx = image.getWidth(this) / nw;
        int dy = image.getHeight(this) / nh;
        int n = drawable.getFrame();
        int x2 = n % nw;
        int y2 = n / nw;
        int destX0 = (int) (x * width);
        int destY0 = (int) (y * height);
        int destX1 = (int) (x * width + width + 1);
        int destY1 = (int) (y * height + height + 1);
        g.drawImage(image, destX0, destY0, destX1, destY1, x2 * dx, y2 * dy, x2 * dx + dx, y2 * dy + dy, this);
    }
}
