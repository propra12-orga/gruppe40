package draw;

import draw.Drawable;
import game.GameData;
import game.Player;

import javax.swing.JPanel;

import map.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    
    private double tileWidth, tileHeight;
    private Graphics2D g;

    public void paintComponent(Graphics g0) {
        if (GameData.networkData == null) {
            System.out.println("Waiting for server");
            return;
        }
        Map map = GameData.networkData.map;
        LinkedList<Drawable> drawables = GameData.networkData.drawables;
        g = (Graphics2D) g0;

        int width = this.getWidth();
        int height = this.getHeight();
        // Clear background (just in case)
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);

        tileWidth = width / (double) map.getWidth();
        tileHeight = height / (double) map.getHeight();

        for (Drawable drawable : drawables) drawDrawable(drawable);
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
        //Calculate frame position in 2D
        int x2 = n % nw;
        int y2 = n / nw;
        //Frame edges in screen-space
        double destX0 = x * tileWidth;
        double destY0 = y * tileHeight;
        double destX1 = x * tileWidth + tileWidth + 1;
        double destY1 = y * tileHeight + tileHeight + 1;
        if (drawable instanceof Player) {
            Player player = (Player)drawable;
            double tx = tileWidth*player.getFlowX();
            double ty = tileHeight*player.getFlowY();
            destX0 += tx;
            destX1 += tx;
            destY0 += ty;
            destY1 += ty;
        }
        //Draw frame
        g.drawImage(image, (int)destX0, (int)destY0, (int)destX1, (int)destY1, x2 * dx, y2 * dy, x2 * dx + dx, y2 * dy + dy, this);
    }
}
