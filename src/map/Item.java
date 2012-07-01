package map;

import game.GameData;

public class Item extends Field {
    private static final long serialVersionUID = GameData.version;

    public static final int   BOMB             = 0;
    public static final int   SUPERBOMB        = 1;
    public static final int   SPEED            = 2;
    public static final int   BOX              = 3;

    private int               type;

    public Item(int x, int y, int type) {
        super(x, y, 0);
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public String getPath() {
        switch (type) {
            case SUPERBOMB:
            default:
                return "ItemSuperbomb.jpg";
            case SPEED:
                return "ItemSpeed.jpg";
            case BOX:
                return "ItemBox.jpg";
        }
    }
}
