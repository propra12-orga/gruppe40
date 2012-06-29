package draw;

import draw.Drawable;
import game.GameData;

import javax.swing.JPanel;

import map.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import java.util.ListIterator;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    public void paintComponent(Graphics g0) {
        if (GameData.networkData == null) {
            System.out.println("Waiting for server");
            return;
        }
        Map map = GameData.networkData.map;
        LinkedList<Drawable> drawables = GameData.networkData.drawables;
        Graphics2D g = (Graphics2D) g0;

        int width = this.getWidth();
        int height = this.getHeight();
        // Clear background (just in case)
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);

        double tileWidth = width / (double) map.getWidth();
        double tileHeight = height / (double) map.getHeight();

        ListIterator<Drawable> it = drawables.listIterator();
        while (it.hasNext()) {
            Drawable drawable = it.next();
            boolean isUnusedField = drawable.isField && map.getField(drawable.x, drawable.y) != drawable;
            if (drawable.isExpired() || isUnusedField) {
                it.remove();
            } else {
                if (drawable.isVisible()) {
                    drawDrawable(g, drawable, tileWidth, tileHeight);
                }
            }
        }
    }

    private void drawDrawable(Graphics2D g, Drawable drawable, double width, double height) {
        // x-position of frame
        int x = drawable.getX();
        // y-position of frame
        int y = drawable.getY();
        // Image to draw
        Image image = drawable.getImage();
        // Number of frames in x-direction
        int nw = drawable.getFrameCountX();
        // Number of frames in y-direction
        int nh = drawable.getFrameCountY();
        // width of a frame
        int dx = image.getWidth(this) / nw;
        // Height of a frame
        int dy = image.getHeight(this) / nh;
        // Frame index
        int n = drawable.getFrame();
        // Restrict frame indices to valid range
        if (n < 0) n = 0;
        int max = dx * dy - 1;
        if (n > max) n = max;
        //Calculate frame position in 2D
        int x2 = n % nw;
        int y2 = n / nw;
        //Frame edges in screen-space
        int destX0 = (int) (x * width);
        int destY0 = (int) (y * height);
        int destX1 = (int) (x * width + width + 1);
        int destY1 = (int) (y * height + height + 1);
        if (dx == 48)
        System.out.println(n+ " "  + nw + " " + nh + " " + dx + " " + dy);
        //Draw frame
        g.drawImage(image, destX0, destY0, destX1, destY1, x2 * dx, y2 * dy, x2 * dx + dx, y2 * dy + dy, this);
    }
}
