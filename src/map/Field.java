package map;

import images.ImageLoader;

import java.awt.Image;

public abstract class Field {

    private int strength;
    private Image img;

    public Field(int strength) {
        this.strength = strength;
        this.img = ImageLoader.getImage(this);
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
     * @return the image of the field
     */
    public Image getImage() {
        return img;
    }
}