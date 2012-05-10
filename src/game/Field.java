package game;

public class Field {

    private int strength;

    public Field(int strength) {
        this.strength = strength;
    }

    /**
     * @return if the field is an exit
     */
    public boolean isExit() {
        return strength < -1;
    }

    /**
     * @return if the field is destructible
     */
    public boolean isDestructible() {
        return strength > 0;
    }

    /**
     * @return strength of the field
     */
    public int getStrength() {
        return strength;
    }
}
