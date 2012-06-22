package map;

public class DestructibleWall extends Field {
    
    private boolean exit = false;
    
    /**
     * Set if exit should appear after wall is destroyed
     * @param hasExit exit yes/no?
     */
    public void setExit(boolean hasExit) {
        this.exit = hasExit;
    }
    
    /**
     * Should an exit appear after this wall is destroyed?
     * @return if an exit should appear
     */
    public boolean isExit() {
        return exit;
    }
    
    public DestructibleWall(int strength) {
        super(strength);
    }

    @Override
    public String getPath() {
        return "DestructibleWall.jpg";
    }
}
