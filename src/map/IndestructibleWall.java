package map;

public class IndestructibleWall extends Field {
    
    public IndestructibleWall(int x, int y) {
        super(x, y, -1);
    }

    @Override
    public String getPath() {
        return "IndestructibleWall.jpg";
    }
}
