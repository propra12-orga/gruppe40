package map;

public class Exit extends Field {

    public Exit(int x, int y) {
        super(x, y, 0);
        this.duration = 180;
    }
    
    @Override
    public int getFrame() {
        double u = (System.currentTimeMillis() - t)/(double)duration;
        int nx = getFrameCountX();
        int ny = getFrameCountY();
        return ((int)(nx*ny*u))%(nx*ny);
    }
    
    @Override
    public boolean shouldScale() {
        return false;
    }
    
    @Override
    public boolean isExpired() {
        return false;
    }
    
    @Override
    public int getFrameCountX() {
        return 9;
    }

    @Override
    public int getFrameCountY() {
        return 2;
    }

    @Override
    public String getPath() {
        return "Exit.png";
    }
}
