package map;

import draw.Drawable;

public abstract class Field extends Drawable {

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
}