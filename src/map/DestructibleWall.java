package map;

public class DestructibleWall extends Field {
    
    private boolean exit = false;
    
    public void setExit(boolean hasExit) {
        this.exit = hasExit;
    }
    
    public boolean isExit() {
        return exit;
    }
    
    public DestructibleWall(int strength) {
        super(strength);
    }
}
