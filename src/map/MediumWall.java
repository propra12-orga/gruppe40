package map;

public class MediumWall extends DestructibleWall {

    public MediumWall(int x, int y) {
        super(x, y, 2);
    }
    
    @Override
    public String getPath() {
        if (super.getStrength() == 1) return "MediumWallCracked.jpg";
        else return "MediumWall.jpg";
    }

}
