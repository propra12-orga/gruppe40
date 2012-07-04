package map;

import game.Drawable;
import game.GameData;

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

    public void onDestroy() {
        int itemnumber = 3; //increased, if more than 3 items
        int random = (int)(Math.floor(Math.random()*100));
        if (random < 20){ //80% field remains empty
            new Item(this.x, this.y, (random % itemnumber) + 1);
        }
    }

    public boolean isDestroyable() {
        return strength != -1;
    }
}