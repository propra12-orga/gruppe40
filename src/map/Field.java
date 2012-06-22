package map;

import draw.Drawable;

public abstract class Field extends Drawable {

    private int strength;

    public Field(int strength) {
        super(-1, -1);
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