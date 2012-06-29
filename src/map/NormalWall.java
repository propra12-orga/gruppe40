package map;

import game.GameData;

public class NormalWall extends DestructibleWall {
    private static final long serialVersionUID = GameData.version;

    public NormalWall(int x, int y) {
        super(x, y, 1);
    }
}
