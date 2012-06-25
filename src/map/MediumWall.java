package map;

public class MediumWall extends DestructibleWall {

    public MediumWall(int x, int y) {
        super(x, y, 2);
    }
    
    @Override
    public String getPath() {
        return "MediumWall.jpg";
    }

}
