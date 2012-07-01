package game;


public class Explosion extends Drawable {
    private static final long serialVersionUID = GameData.version;

    public Explosion(int x, int y) {
        super(x, y, false);
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
        return "RedExplosion.png";
    }

    @Override
    public boolean shouldScale() {
        return false;
    }
}
