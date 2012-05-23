package map;

import java.awt.Image;

public class DestructibleWall extends Field {
    
    private boolean exit = false;
    
    public void setExit(boolean hasExit) {
        this.exit = hasExit;
    }
    
    public boolean isExit() {
        return exit;
    }
    
    public DestructibleWall(int strength, Image img) {
        super(strength, img);
    }
}
