package game;

import images.ImageLoader;

import java.awt.Image;

import draw.Drawable;

public class Explosion extends Drawable {
    
    private long t0 = System.currentTimeMillis();
    private final long duration = 18 * 50; //milliseconds of bomb explosion animation duration
    
    //TODO make expire

    public Explosion(int x, int y) {
        super(x, y);
        image = ImageLoader.getImage(this).getScaledInstance(-1, -1, Image.SCALE_REPLICATE);
    }

}
