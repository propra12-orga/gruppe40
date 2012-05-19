package draw;

import java.awt.Image;

/**
 * Every tile-sized object which is not the map should extend this to be drawn
 */
public class Drawable {
    private int   x   = 0;
    private int   y   = 0;

    private Image img = null;

    public Drawable(Image img, int x, int y) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public Image getImage() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
