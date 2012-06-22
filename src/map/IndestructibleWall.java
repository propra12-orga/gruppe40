package map;

public class IndestructibleWall extends Field {
    
    public IndestructibleWall() {
        super(-1);
    }

    @Override
    public String getPath() {
        return "IndestructibleWall.jpg";
    }
}
