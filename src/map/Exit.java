package map;

public class Exit extends Field {

    public Exit() {
        super(0);
        this.duration = 180;
    }
    
    @Override
    public int getFrame() {
    	double u = (System.currentTimeMillis() - t)%duration;
        int nx = getFrameCountX();
        int ny = getFrameCountY();
        return (int)(nx*ny*u);
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
