package draw;

import images.ImageLoader;

import java.awt.Image;

/**
 * Every tile-sized object which is not the map should extend this to be drawn
 */
public abstract class Drawable {
    protected int        x        = 0;
    protected int        y        = 0;
    protected boolean    visible  = true;
    protected long       duration = -1;
    protected long       t        = System.currentTimeMillis();
    
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
        }else {
            double u = (System.currentTimeMillis() - t)/(double)duration;
            int nx = getFrameCountX();
            int ny = getFrameCountY();
            return (int)(nx*ny*u);
        }
    }

    public String getFrameName() {
        return getPath();
    }

    public Image getImage(ImageLoader imageLoader) {
        return imageLoader.getImage(this);
    }

    public Drawable(int x, int y) {
        this.x = x;
        this.y = y;
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

}
