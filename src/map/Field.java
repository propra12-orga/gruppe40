package map;

import game.GameData;
import draw.Drawable;

public abstract class Field extends Drawable {
    private static final long serialVersionUID = GameData.version;

    private int strength;

    public Field(int x, int y, int strength) {
        super(x, y, true);
        this.strength = strength;
    }

    /**
     * @return strength of the field
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @param newStrength
     *            - the new strength
     */
    public void setStrength(int newStrength) {
        this.strength = newStrength;
    }

    /**
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     */
    public void getItem(int x, int y) {
//         if (Math.random() < 0.2) new Item(x, y, true);
    }
}