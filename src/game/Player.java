package game;

import draw.Drawable;

import map.Map;

public class Player extends Drawable {
    private static final long serialVersionUID = GameData.version;

    private Map               map;
    private int               direction;

    private boolean           alive;
    private String            name;
    private int               bombCounter;
    private long              walkStart;
    private int               walkDistance;
    public int                radius;

    public Player(String name, int x, int y, int speed) {
        super(x, y, false);
        this.name = name;
        this.map = GameData.map;
        this.direction = 0;
        this.walkDistance = speed;
        this.alive = true;
        this.bombCounter = 2;
        this.radius = 1;
    }

    public int getSpeed() {
        return this.walkDistance;
    }

    public void setSpeed(int speed) {
        this.walkDistance = speed;
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
        double t = (System.currentTimeMillis() - walkStart) / (double) walkDistance;
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
        return System.currentTimeMillis() - walkStart < walkDistance;
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
     * Get direction from x/y-offset. Only works for one field offsets.
     * 
     * @param dx X-translation
     * @param dy Y-translation
     * @return Integer indicating a direction which is RIGHT/UP/LEFT/DOWN from 0
     *         to 3.
     */
    public int getDirection(int dx, int dy) {
        for (int i = 0; i < 4; i++)
            if (dx == Direction.x[i] && dy == Direction.y[i]) return i;
        return 0;
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
        if (!isMoving()) direction = getDirection(dx, dy);
        if (canMove()) {
            int x2 = x + dx;
            int y2 = y + dy;
            if (map.contains(x2, y2) && !map.isBlocked(x2, y2)) {
                walkStart = System.currentTimeMillis();
                this.x = x2;
                this.y = y2;
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean hasBomb() {
        return alive && bombCounter > 0;
    }

    /**
     * If there are bombs left, one is placed at the player's position.
     * 
     * @return If the bomb could be planted successfully.
     */
    public boolean putBomb() {
        // Do not place bombs while moving between tiles and do not place bombs
        // on bombs
        if (!hasBomb() || map.isBlocked(x, y) || isMoving()) return false;
        this.bombCounter--;
        new Bomb(x, y, this);
        return true;
    }

    public void increaseBombCounter() {
        this.bombCounter++;
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
