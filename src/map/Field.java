package map;

public abstract class Field {

    private int strength;
    private String name;

    public Field(int strength, String name) {
        this.strength = strength;
        this.name = name;
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
}