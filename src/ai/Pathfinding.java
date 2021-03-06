package ai;

import game.Bomb;
import game.Direction;
import game.Player;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Vector;

import map.Map;

public class Pathfinding {

    private int               w, h;
    private int               dangerous[][];
    private int               distance[][];
    private Point             previous[][];
    private static final int  UNVISITED     = -1;
    private static final int  NOT_DANGEROUS = -1;
    private LinkedList<Point> reachable;
    private Map               map;
    private Vector<Player>    players;
    private int               speed;
    public boolean            disregardDestroyableWalls;

    /**
     * Creates a new object to find paths.
     * 
     * @param map Map on which to find paths.
     * @param players Players which to find paths of.
     * @param bombs Bombs which are dangerous.
     * @param speed Speed of player.
     */
    public Pathfinding(Map map, Vector<Player> players, LinkedList<Bomb> bombs, int speed) {
        this.map = map;
        this.players = players;
        this.speed = speed;

        w = map.getWidth();
        h = map.getHeight();
        dangerous = new int[w][h];
        distance = new int[w][h];
        previous = new Point[w][h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                dangerous[x][y] = NOT_DANGEROUS;
                distance[x][y] = UNVISITED;
                previous[x][y] = null;
            }
        }
        reachable = new LinkedList<Point>();
        if (bombs != null) {
            for (Bomb bomb : bombs) {
                if (!bomb.isExpired()) {
                    markDangerous(bomb.x, bomb.y, bomb.getRadius(), bomb.ticksUntilExplosion);
                }
            }
        }
    }

    private void explore(Point p, int direction) {
        int x = p.x + Direction.x[direction];
        int y = p.y + Direction.y[direction];
        if (!contains(x, y)) return;
        boolean notBlocked = !map.isBlocked(x, y);
        if (disregardDestroyableWalls) notBlocked = map.getField(x, y).isDestroyable();
        if (notBlocked && distance[x][y] == UNVISITED) {
            int danger = dangerous[x][y];
            if (danger != NOT_DANGEROUS) {
                // We can not survive that
                if (distance[x][y] / speed > danger) return;
                // Else we are running through a bomb which will explode soon
                // but we are faster (or slower)
            }
            previous[x][y] = p;
            distance[x][y] = distance[p.x][p.y] + 1;
            reachable.addLast(new Point(x, y));
        }
    }

    /**
     * Start from position.
     * 
     * @param x Starting x-coordinate.
     * @param y Starting y-coordinate.
     */
    public void start(int x, int y) {
        previous[x][y] = null;
        distance[x][y] = 0;
        reachable.push(new Point(x, y));
        while (!reachable.isEmpty()) {
            Point p = reachable.pollFirst();
            for (int i = 0; i < 4; i++)
                explore(p, i);
        }
    }

    /**
     * Does the map used for this class contain the given coordinate.
     * 
     * @param x x-coordinate which should be contained.
     * @param y y-coordinate which should be contained.
     * @return If the map contains the given coordinate pair.
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h;
    }

    /**
     * Marks all fields within bomb radius as dangerous
     * 
     * @param x x-coordinate at which to start.
     * @param y y-coordinate at which to start.
     * @param radius Radius of explosion.
     * @param danger Ticks until explosion.
     */
    public void markDangerous(int x, int y, int radius, int danger) {
        if (contains(x, y)) dangerous[x][y] = danger;
        for (int i = 0; i < 4; i++) {
            int x2 = x;
            int y2 = y;
            for (int r = 1; r <= radius; r++) {
                x2 += Direction.x[i];
                y2 += Direction.y[i];
                if (contains(x2, y2)) dangerous[x2][y2] = danger;
            }
        }
    }

    /**
     * Finds the closest point where players will not die.
     * 
     * @param px x-coordinate at which to start.
     * @param py y-coordinate at which to start.
     * @return A nice point.
     */
    public Point getClosestReachableNicePoint(int px, int py) {
        Point p = null;
        int dist = Integer.MAX_VALUE;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int d = distance[x][y];
                if (d != UNVISITED && notDangerous(x, y)) {
                    if ((x != px || y != py) && d < dist) {
                        p = new Point(x, y);
                        dist = d;
                    }
                }
            }
        }
        return p;
    }

    /**
     * Finds the closest reachable player.
     * 
     * @param thisPlayer Player at which to start.
     * @return Closest reachable player.
     */
    public Player getClosestReachablePlayer(Player thisPlayer) {
        Player closest = null;
        int dist = Integer.MAX_VALUE;
        for (Player player : players) {
            int d = distance[player.x][player.y];
            if (player.isAlive() && player != thisPlayer && d != UNVISITED && d < dist) {
                closest = player;
                dist = d;
            }
        }
        return closest;
    }

    /**
     * Find closest box.
     * 
     * @return Closest box.
     */
    public Point getClosestBox() {
        Point p = null;
        int dist = Integer.MAX_VALUE;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (map.getField(x, y).getStrength() > 0) {
                    for (int i = 0; i < 4; i++) {
                        int x2 = x + Direction.x[i];
                        int y2 = y + Direction.y[i];
                        int d = distance[x2][y2];
                        if (d != UNVISITED && d < dist) {
                            p = new Point(x2, y2);
                            d = dist;
                        }
                    }
                }
            }
        }
        return p;
    }

    public boolean notDangerous(int x, int y) {
        return dangerous[x][y] == NOT_DANGEROUS;
    }

    public boolean isDangerous(int x, int y) {
        return dangerous[x][y] != NOT_DANGEROUS;
    }

    /**
     * Distance from starting position to specified coordinate pair.
     * 
     * @param x x-coordinate at which to check the distance.
     * @param y y-coordinate at which to check the distance.
     * @return Distance from starting position to specified coordinate pair.
     */
    public int getDistance(int x, int y) {
        return distance[x][y];
    }

    /**
     * Find point towards another point.
     * 
     * @param target Target point.
     * @param source Source point.
     * @return Point towards target point.
     */
    public Point getPointTowards(Point target, Point source) {
        Point prev = source;
        while (target.x != source.x || target.y != source.y) {
            prev = target;
            target = previous[target.x][target.y];
        }
        return prev;
    }

    /**
     * Find out if a point is reachable from the starting position.
     * 
     * @param x x-coordinate at which to check.
     * @param y y-coordinate at which to check.
     * @return If a point is reachable from the starting position.
     */
    public boolean isReachable(int x, int y) {
        return distance[x][y] != UNVISITED;
    }
}
