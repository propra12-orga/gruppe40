package game;

import draw.Drawable;

import map.Map;

public class Bomb extends Drawable {
    private static final long serialVersionUID = GameData.version;
	
	private int radius;
	private int strength;
	private boolean hasExploded;
	private Player owner; 
	public int ticksUntilExplosion;
	
	public Bomb(int x, int y, Player owner){
        super(x, y, false);
		this.radius = 1;
		this.strength = 1;
		this.ticksUntilExplosion = GameData.fps;
		this.owner = owner;
        GameData.map.setBlocked(x, y, true);
		synchronized (GameData.bombs) {
	        GameData.bombs.add(this);
		}
	}
	
	public void setXY(int x, int y, Map map){
		this.x = x;
		this.y = y;
	}
	
	private boolean explodeAt(int x2, int y2) {
        //Chain reaction
        for (Bomb b : GameData.bombs) {
            if (b != this && b.getX() == x2 && b.getY() == y2) b.explode();
        }
	    // destroy field
        if (GameData.map.destroy(x2, y2, strength)) {
            new Explosion(x2, y2);
            return false;
        }
        // Stop if blocked
        if (GameData.map.isBlocked(x2, y2)) return false;
        synchronized (GameData.drawables) {
            new Explosion(x2, y2);
        }
        //Kill players
        for (Player p : GameData.players) {
            if (p.getX() == x2 && p.getY() == y2) p.setAlive(false);
        }
        return true;
	}
	
	/**
	 * Makes the bomb explode.
	 */
	public void explode(){
	    //Prevent infinite loop from two bombs triggering each other
	    if (hasExploded) return;
		this.owner.increaseBombCounter();
		hasExploded = true;
		GameData.map.setBlocked(this.x, this.y, false);
        GameData.map.destroy(x, y, strength);
		// All directions (right, up, left, down)
		int dx[] = {1, 0, -1, 0};
		int dy[] = {0, 1, 0, -1};
		// Add new explosion at (x, y)
		explodeAt(x, y);
        // For all directions
		for (int j=0; j<4; j++) {
		    int x2 = x;
		    int y2 = y;
		    // For radius
		    for (int i=1; i<=radius; i++) {
		        // Walk towards direction
                x2 += dx[j];
                y2 += dy[j];
                if (!explodeAt(x2, y2)) break;
		    }
		}
		hasExploded = true;
	}
	
	public void destroy(){
		this.explode();
	}

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getRadius() {
        return radius;
    }
	
    public boolean isExpired() {
        return hasExploded;
    }

    @Override
    public String getPath() {
        return "Bomb.gif";
    }
    
    @Override
    public boolean shouldScale() {
        return false;
    }
}
