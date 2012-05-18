package map;

import java.awt.Image;

public abstract class Field {

    private int strength;
    private String name;
    private Image img;

    public Field(int strength, String name, Image img) {
        this.strength = strength;
        this.name = name;
        this.img = img;
    }

    /**
     * @return strength of the field
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @return name of the field
     */
    public String getName() {
        return name;
    }

    /**
     * @param newStrength
     *            - the new strength
     */
    public void setStrength(int newStrength) {
        this.strength = newStrength;
    }

    /**
     * @return the image of the field
     */
    public Image getImage() {
        return img;
    }
}