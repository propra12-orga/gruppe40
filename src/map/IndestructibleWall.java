package map;

import game.GameData;

public class IndestructibleWall extends Field {
    private static final long serialVersionUID = GameData.version;
    
    public IndestructibleWall(int x, int y) {
        super(x, y, -1);
    }

    @Override
    public String getPath() {
        return "IndestructibleWall.jpg";
    }
}
