package draw;

import draw.Drawable;
import game.Player;

import javax.swing.JPanel;

import map.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {

    private double              tileWidth, tileHeight;
    private Graphics2D          g;
    public LinkedList<Drawable> drawables;
    public Map                  map;

    public void paintComponent(Graphics g0) {
        if (drawables == null) return;

        g = (Graphics2D) g0;

        int width = this.getWidth();
        int height = this.getHeight();
        // Clear background (just in case)
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);

        tileWidth = width / (double) map.getWidth();
        tileHeight = height / (double) map.getHeight();

        for (Drawable drawable : drawables)
            drawDrawable(drawable);
    }

    private void drawDrawable(Drawable drawable) {
        if (!drawable.isVisible()) return;
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
        // Calculate frame position in 2D
        int x2 = n % nw;
        int y2 = n / nw;
        // Frame edges in screen-space
        double destX0 = x * tileWidth;
        double destY0 = y * tileHeight;
        double destX1 = x * tileWidth + tileWidth + 1;
        double destY1 = y * tileHeight + tileHeight + 1;
        if (drawable instanceof Player) {
            Player player = (Player) drawable;
            double tx = tileWidth * player.getFlowX();
            double ty = tileHeight * player.getFlowY();
            destX0 += tx;
            destX1 += tx;
            destY0 += ty;
            destY1 += ty;
        }
        // Draw frame
        g.drawImage(image, (int) destX0, (int) destY0, (int) destX1, (int) destY1, x2 * dx, y2 * dy, x2 * dx + dx, y2 * dy + dy, this);
    }

    /**
     * Used to determine if game panel should be resized.
     * This is required because resizing appears to be a quite costly operation
     * on linux.
     */
    private int hash = 0;

    /**
     * Get bounds for panel so it has maximum size but still preserves map aspect ratio.
     */
    public Rectangle getOptimalSize(int width, int height) {
        int mx = map.getWidth();
        int my = map.getHeight();

        // Do not resize if nothing has changed
        int newHash = (mx << 25) | (my << 20) | (width << 10) | height;
        if (hash != newHash) hash = newHash;
        else return null;

        // Calculate width required to display the image at full height while
        // preserving aspect ratio
        int maxWidth = height * mx / my;
        // If it fits on screen draw it
        if (maxWidth <= width) {
            int blackBarWidth = (width - maxWidth) / 2;
            return new Rectangle(blackBarWidth, 0, maxWidth, height);
            //GameData.gamePanel.setBounds(blackBarWidth, 0, maxWidth, height);
        } else {
            // If it does not fit calculate height which preserves aspect ratio
            // and use that instead
            int maxHeight = width * my / mx;
            int blackBarHeight = (height - maxHeight) / 2;
            return new Rectangle(0, blackBarHeight, width, maxHeight);
            //GameData.gamePanel.setBounds(0, blackBarHeight, width, maxHeight);
        }
    }
}
