package ai;

import java.awt.Point;
import java.util.LinkedList;

import game.GameData;
import game.Bomb;
import game.Player;

public class AI {

    private int               tickCounter;
    /**
     * Could be used as a difficulty value
     */
    private final int         TICK_MAX  = 5;
    private Player            player;
    private int               w, h;
    private boolean           dangerous[][];
    private boolean           blocked[][];
    private int               distance[][];
    private Point             previous[][];
    private int               dx[]      = { 0, 1, 0, -1 };
    private int               dy[]      = { -1, 0, 1, 0 };
    private static final int  UNVISITED = -1;
    private LinkedList<Point> reachable;

    public AI(Player player) {
        this.player = player;
        w = GameData.map.getWidth();
        h = GameData.map.getHeight();
        dangerous = new boolean[w][h];
        distance = new int[w][h];
        previous = new Point[w][h];
        blocked = new boolean[w][h];
    }

    private void initPathfinding() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                dangerous[x][y] = false;
                blocked[x][y] = GameData.map.isBlocked(x, y);
                distance[x][y] = UNVISITED;
                previous[x][y] = null;
            }
        }
        reachable = new LinkedList<Point>();
        for (Bomb bomb : GameData.bombs)
            if (!bomb.isExpired()) markDangerous(bomb.x, bomb.y, bomb.getRadius());
    }

    private void explore(Point p, int direction) {
        int x = p.x + dx[direction];
        int y = p.y + dy[direction];
        int d = distance[x][y];
        if (contains(x, y) && !blocked[x][y]&& d == UNVISITED) {
            //If field is dangerous and we can not run away from it
            //if (dangerous[x][y] && d > 2) return;
            previous[x][y] = p;
            distance[x][y] = distance[p.x][p.y] + 1;
            reachable.addLast(new Point(x, y));
        }
    }

    private void pathfinding() {
        int x = player.x;
        int y = player.y;
        previous[x][y] = null;
        distance[x][y] = 0;
        reachable.push(new Point(x, y));
        while (!reachable.isEmpty()) {
            Point p = reachable.pollFirst();
            for (int i = 0; i < 4; i++)
                explore(p, i);
        }
    }

    private boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h;
    }

    private void markDangerous(int x, int y, int radius) {
        if (contains(x, y)) dangerous[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int x2 = x;
            int y2 = y;
            for (int r = 1; r <= radius; r++) {
                x2 += dx[i];
                y2 += dy[i];
                if (contains(x2, y2)) dangerous[x2][y2] = true;
            }
        }
    }

    private Point getClosestReachableNicePoint() {
        Point p = null;
        int dist = Integer.MAX_VALUE;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int d = distance[x][y];
                if (d != UNVISITED) {
                    if ((x != player.x || y != player.y) && d < dist) {
                        p = new Point(x, y);
                        dist = d;
                    }
                }
            }
        }
        return p;
    }

    private Player getClosestReachablePlayer() {
        Player closest = null;
        int dist = Integer.MAX_VALUE;
        for (Player player : GameData.players) {
            int d = distance[player.x][player.y];
            if (player.isAlive() && player != this.player && d != UNVISITED && d < dist) {
                closest = player;
                dist = d;
            }
        }
        return closest;
    }
    
    private Point getClosestBox() {
        Point p = null;
        int dist = Integer.MAX_VALUE;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (GameData.map.getField(x, y).getStrength() > 0) {
                    for (int i=0; i<4; i++) {
                        int x2 = x + dx[i];
                        int y2 = y + dy[i];
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

    private Point getPointTowards(Point target) {
        Point source = new Point(player.x, player.y);
        Point prev = source;
        while (target.x != source.x || target.y != source.y) {
            prev = target;
            target = previous[target.x][target.y];
        }
        return prev;
    }
    
    private void moveTowards(Point p, boolean safe) {
        Point towards = getPointTowards(p);
        if (safe) {
            if (!dangerous[towards.x][towards.y]) {
                player.moveTo(towards.x, towards.y);
            }
        }else {
            player.moveTo(towards.x, towards.y);
        }
    }

    public void nextStep() {
        if (tickCounter < TICK_MAX) {
            tickCounter++;
            return;
        }
        tickCounter = 0;
        
        initPathfinding();
        pathfinding();
        int px = player.x;
        int py = player.y;
        if (dangerous[px][py]) {
            //Run away if in danger
            Point p = getClosestReachableNicePoint();
            if (p != null) {
                moveTowards(p, false);
            }else {
                System.out.println("DEBUG MSG: AI has nowhere to run!");
            }
        } else {
            //Run towards player if in reach
            Player closest = getClosestReachablePlayer();
            if (closest != null) {
                // TODO replace fixed bomb radius with actual bomb radius
                if (distance[closest.x][closest.y] < 2) {
                    player.putBomb();
                } else {
                    Point p = new Point(closest.x, closest.y);
                    if (p != null) {
                        moveTowards(p, true);
                    }
                }
            }else {
                //Search for boxes
                for (int i=0; i<4; i++) {
                    int x2 = px + dx[i];
                    int y2 = py + dy[i];
                    if (contains(x2, y2) && GameData.map.getField(x2, y2).getStrength() > 0) {
                        //Check if we can get away with bombing it
                        // TODO replace fixed bomb radius with actual bomb radius
                        initPathfinding();
                        markDangerous(x2, y2, 3);
                        pathfinding();
                        for (int y=0; y<h; y++) {
                            for (int x=0; x<w; x++) {
                                if (!dangerous[x][y] && distance[x][y] != UNVISITED && distance[x][y] < 3) {
                                    player.putBomb();
                                    return;
                                }
                            }
                        }
                    }
                }
                Point p = getClosestBox();
                if (p != null) {
                    moveTowards(p, true);
                }else {
                    System.out.println("DEBUG MSG: no close box");
                }
            }
        }
    }

}
