package map;

public class MediumWall extends DestructibleWall {

    public MediumWall() {
        super(2);
    }
    
    @Override
    public String getPath() {
        return "MediumWall.jpg";
    }

}
