package map;

import game.Drawable;
import game.GameData;

public class Item extends Drawable {
    private static final long serialVersionUID = GameData.version;

    public static final int   BOMB             = 0;
    public static final int   SUPERBOMB        = 1;
    public static final int   SPEED            = 2;
    public static final int   BOX              = 3;

    private int               type;
    private boolean           wasUsed          = false;

    public Item(int x, int y, int type) {
        super(x, y, false);
        this.type = type;
        synchronized (GameData.items) {
            GameData.items.add(this);
        }
    }

    public int getType() {
        return this.type;
    }

    @Override
    public int getFrameCountX() {
        return 4;
    }

    @Override
    public int getFrameCountY() {
        return 1;
    }

    @Override
    public int getFrame() {
        int t2 = (int) (System.currentTimeMillis() - t);
        t2 %= 1000;
        if (t2 > 500) t2 = 1000 - t2;
        int frameCount = getFrameCountX() * getFrameCountY();
        return t2 * frameCount / 500;
    }

    @Override
    public boolean shouldScale() {
        return false;
    }
    
    public void setUsed(boolean b) {
        wasUsed = b;
    }

    @Override
    public boolean isExpired() {
        return wasUsed;
    }

    @Override
    public String getPath() {
        switch (type) {
            case SUPERBOMB:
            default:
                return "ItemSuperbomb.png";
            case SPEED:
                return "ItemSpeed.png";
            case BOX:
                return "ItemBox.png";
        }
    }
}