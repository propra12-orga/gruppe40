package game;

import map.*;

public class Player extends Drawable {
    private static final long serialVersionUID = GameData.version;

    private Map               map;
    private int               direction;
    private boolean           alive;
    private String            name;
    private int[]             itemCounter;
    public int                radius;
    private int               tickMax;
    public int                ticks;
    public int                bombTickMax;
	private boolean		      putBox;
	public int 				  ticksUntilNormalSpeed;
	private int				  tilesPerSec;

    public Player(String name, int x, int y) {
        super(x, y, false);
        this.name = name;
        this.map = GameData.map;
        this.direction = Direction.DOWN;
        this.alive = true;
		this.itemCounter = new int[]{2,0,0,0}; //has 2 bombs, 0 superbomb, 0 speedups, 0 boxes
        this.radius = 1;
        this.bombTickMax = GameData.fps;
		this.ticksUntilNormalSpeed = 0;
        this.tilesPerSec = 8;
        this.tickMax = GameData.fps / tilesPerSec;
        this.ticks = GameData.fps / tilesPerSec;
		this.putBox = false;
    }

    public int getSpeed() {
        return this.tickMax;
    }

    public void setSpeed(int speed) {
        this.tickMax = speed;
    }
	
	/**
	 * sets speed to twice the normal speed
	 * increases timeUntilNormalSpeed
	*/
	public void speedUp(){
		if(this.ticksUntilNormalSpeed <0){
			this.ticksUntilNormalSpeed = 0;
		}
		this.ticksUntilNormalSpeed+=(GameData.fps*2);
		this.setSpeed((GameData.fps / this.tilesPerSec)/2);
	}
	
	public void speedDown(){
		this.setSpeed(GameData.fps / this.tilesPerSec);
	}
	
	

    public int getX() {
        return this.x;
    }

    /**
     * @return A value in range [0, 1) which indicates how much of the walking
     *         process is done already.
     */
    public double getFlow() {
        // The player moves instantly on key press, animation follows
        // Not sure if this is the way to go
        double t = ticks / (double) tickMax;
        return t < 1 ? t - 1 : 0;// Subtract 1 to make up for player position
    }

    /**
     * @return Movement interpolation value in range (-1, 1).
     */
    public double getFlowX() {
        return Direction.x[direction] * getFlow();
    }

    /**
     * @return Movement interpolation value in range (-1, 1).
     */
    public double getFlowY() {
        return Direction.y[direction] * getFlow();
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMoving() {
        return ticks < tickMax;
    }

    private boolean canMove() {
        return alive && !isMoving();
    }

    /**
     * Move towards direction.
     * 
     * @param direction Direction towards to move.
     * @return If the move was successful.
     */
    public boolean move(int direction) {
        return move(Direction.x[direction], Direction.y[direction]);
    }

    /**
     * Moves towards an absolute point.
     * 
     * @param x X-Coordinate of point towards which to move.
     * @param y Y-Coordinate of point towards which to move.
     * @return If the move was successful.
     */
    public boolean moveTo(int x, int y) {
        return move(x - this.x, y - this.y);
    }

    /**
     * Move by x/y-offset.
     * 
     * @param dx X-offset by which to move.
     * @param dy Y-offset by which to move.
     * @return If the move was successful.
     */
    public boolean move(int dx, int dy) {
        if (!isMoving()) direction = Direction.get(dx, dy);
        if (canMove()) {
            int x2 = x + dx;
            int y2 = y + dy;
            if (map.contains(x2, y2) && !map.isBlocked(x2, y2)) {
                ticks = 0;
				if(this.putBox){ //leaves box on field if box is collected before
					this.putBox = false;
					map.setField(new DestructibleWall(this.x,this.y,1));
					this.itemCounter[3]--;
				}
                this.x = x2;
                this.y = y2;
				Field field = map.getField(this.x,this.y);
				if(field instanceof Item){
					Item item = (Item)field;
					map.setField(new EmptyField(this.x,this.y));
					if(item.getType() ==2){
						this.speedUp();
					}
					else{
						this.itemCounter[item.getType()]++;
					}
				}
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean hasItem(int type) {
        return alive && this.itemCounter[type] > 0;
    }
	
	public int getItemCount(int type){
		return this.itemCounter[type];
	}

    /**
     * If there are bombs left, one is placed at the player's position.
     * 
     * @return If the bomb could be planted successfully.
     */
    public boolean putItem(int type) {
        // Do not place bombs while moving between tiles and do not place bombs
        // on bombs
        if (!hasItem(type) || map.isBlocked(x, y) || isMoving()) return false;
		switch(type){
			case 0: 
				new Bomb(this);
				this.itemCounter[type]--;
				break;
			case 1:
				new Superbomb(this);
				this.itemCounter[type]--;
				break;
			case 3:
				this.putBox = true;
				break;
			
		
		}
        return true;
    }

    public void increaseItemCounter(int type) {
        this.itemCounter[type]++;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        this.setVisible(false);
    }

    public boolean isAlive() {
        return alive;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return "Player.gif";
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public boolean shouldScale() {
        return false;
    }

}
