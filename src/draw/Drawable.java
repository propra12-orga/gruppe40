package draw;

import game.GameData;
import images.ImageLoader;

import java.awt.Image;

/**
 * Every tile-sized object which is not the map should extend this to be drawn
 */
public abstract class Drawable {
    protected int     x       = 0;
    protected int     y       = 0;
    protected boolean visible = true;

    protected Image   image   = null;

    public Drawable(int x, int y, GameData data) {
        this.x = x;
        this.y = y;
        // Load image with which should be used for drawing this object
        this.image = ImageLoader.getImage(this);
        data.drawables.add(this);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Check if object is expired.
     * Expired Objects will be removed every draw call.
     * @return if this object is expired
     */
    public boolean isExpired() {
        return false;
    }

    /**
     * Sets objects visible or not
     * @param visible visible yes/no?
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return if this object is visible
     */
    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
