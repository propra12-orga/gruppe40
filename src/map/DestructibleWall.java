package map;

import game.GameData;

public class DestructibleWall extends Field {
    private static final long serialVersionUID = GameData.version;
    
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
    
    public DestructibleWall(int x, int y, int strength) {
        super(x, y, strength);
    }
    
    public DestructibleWall(int x, int y) {
        super(x, y, 1);
    }

    @Override
    public String getPath() {
        return "DestructibleWall.jpg";
    }
}
