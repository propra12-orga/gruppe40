package draw;

import images.ImageLoader;

import java.awt.Image;

/**
 * Every tile-sized object which is not the map should extend this to be drawn
 */
public abstract class Drawable {
    protected int     x       = 0;
    protected int     y       = 0;
    protected boolean visible = true;

    private Image     img     = null;

    public Drawable(int x, int y) {
        this.x = x;
        this.y = y;
        this.img = ImageLoader.getImage(this);
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
        return img;
    }

    public boolean isExpired() {
        return false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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
