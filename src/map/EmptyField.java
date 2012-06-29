package map;

import game.GameData;

public class EmptyField extends Field{
    private static final long serialVersionUID = GameData.version;
    
    public EmptyField(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public String getPath() {
        return "EmptyField.jpg";
    }
}
