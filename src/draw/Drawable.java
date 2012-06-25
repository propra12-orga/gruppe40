package draw;

import game.GameData;
import images.ImageLoader;

import java.awt.Image;

/**
 * Every tile-sized object which is not the map should extend this to be drawn
 */
public abstract class Drawable {
    public int     x          = 0;
    public int     y          = 0;
    public boolean visible    = true;
    public long    duration   = -1;
    public long    t          = System.currentTimeMillis();
    public boolean isField;

    public Drawable(int x, int y, boolean isField) {
        this.x = x;
        this.y = y;
        this.isField = isField;
        if (isField) GameData.drawables.addFirst(this);
        else GameData.drawables.addLast(this);
    }

    public abstract String getPath();

    public int getFrameCountX() {
        return 1;
    }

    public int getFrameCountY() {
        return 1;
    }

    public int getFrame() {
        if (duration <= 0) {
            return 0;
        } else {
            double u = (System.currentTimeMillis() - t) / (double) duration;
            int nx = getFrameCountX();
            int ny = getFrameCountY();
            return (int) (nx * ny * u);
        }
    }

    public String getFrameName() {
        return getPath();
    }

    public Image getImage() {
        return ImageLoader.getImage(this);
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

    /**
     * Check if object is expired.
     * Expired Objects will be removed every draw call.
     * 
     * @return if this object is expired
     */
    public boolean isExpired() {
        return false;
    }

    /**
     * Sets objects visible or not
     * 
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

    public boolean shouldScale() {
        return true;
    }

}
