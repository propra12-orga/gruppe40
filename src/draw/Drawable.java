package draw;

import game.GameData;
import images.ImageLoader;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

/**
 * Every object which is drawn should extend this
 */
public abstract class Drawable implements Serializable {
    private static final long serialVersionUID = GameData.version;
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
        synchronized (GameData.drawables) {
            if (isField) GameData.drawables.addFirst(this);
            else GameData.drawables.addLast(this);
        }
    }

    /**
     * @return Image path
     */
    public abstract String getPath();

    /**
     * @return Number of images on the x-axis
     */
    public int getFrameCountX() {
        return 1;
    }

    /**
     * @return Number of images on the y-axis
     */
    public int getFrameCountY() {
        return 1;
    }

    /**
     * @return Frame to be displayed (e.g. 3 if you want to have the blue part of the windows logo)
     */
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

    /**
     * @return Image of this Drawable
     */
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
     * @return If this object is visible
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
    
    public Point getPoint() {
        return new Point(x, y);
    }

    /**
     * Animated Gifs should not be scaled because java will break the animation.
     * Sprite images should not be scaled if they become too small.
     * @return If the Image of this object should be scaled to 50x50
     */
    public boolean shouldScale() {
        return true;
    }

}
