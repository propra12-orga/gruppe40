package game;

import draw.Drawable;

public class Explosion extends Drawable {

    public Explosion(int x, int y, GameData data) {
        super(x, y);
        data.drawables.add(this);
        this.duration = 500;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() - t > duration;
    }

    @Override
    public int getFrameCountX() {
        return 4;
    }

    @Override
    public int getFrameCountY() {
        return 2;
    }

    @Override
    public String getPath() {
        return "EmptyField.jpg";
    }

}
