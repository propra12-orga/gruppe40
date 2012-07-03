package game;


import map.Map;

public class Bomb extends Drawable {
    private static final long serialVersionUID = GameData.version;
	
	private int radius;
	private int strength;
	private boolean hasExploded;
	private Player owner; 
	public int ticksUntilExplosion;
	public int bombTickMax;
	
	public Bomb(Player owner){
        super(owner.x, owner.y, false);
		this.radius = owner.radius;
		this.strength = 1;
		this.ticksUntilExplosion = owner.bombTickMax;
		this.bombTickMax = owner.bombTickMax;
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
		if(!(this instanceof Superbomb)){
			this.owner.increaseItemCounter(0);
		}
		hasExploded = true;
		GameData.map.setBlocked(this.x, this.y, false);
        GameData.map.destroy(x, y, strength);
		// Add new explosion at (x, y)
		explodeAt(x, y);
        // For all directions
		for (int j=0; j<4; j++) {
		    int x2 = x;
		    int y2 = y;
		    // For radius
		    for (int i=1; i<=radius; i++) {
		        // Walk towards direction
                x2 += Direction.x[j];
                y2 += Direction.y[j];
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
	
	public void setRadius(int radius){
		this.radius = radius;
	}
	
    public boolean isExpired() {
        return hasExploded;
    }

    @Override
    public String getPath() {
        return "Bomb.png";
    }
    
    @Override
    public int getFrameCountX() {
        return 3;
    }
    
    @Override
    public int getFrameCountY() {
        return 1;
    }
    
    @Override
    public int getFrame() {
        int frameCount = getFrameCountX()*getFrameCountY();
        return (int)(frameCount - frameCount*ticksUntilExplosion/(double)bombTickMax);
    }
    
    @Override
    public boolean shouldScale() {
        return false;
    }
}
