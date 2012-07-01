package ai;

import java.awt.Point;

import game.Direction;
import game.GameData;
import game.Player;

public class AI {

    private int         tickCounter;
    /**
     * Could be used as a difficulty value
     */
    private final int   TICK_MAX = 5;
    private Player      player;
    private Pathfinding pathfinding;

    public AI(Player player) {
        this.player = player;
    }

    private void moveTowards(Point p, boolean safe) {
        Point towards = pathfinding.getPointTowards(p, new Point(player.getPoint()));
        if (safe) {
            if (pathfinding.dangerous[towards.x][towards.y] == Pathfinding.NOT_DANGEROUS) {
                player.moveTo(towards.x, towards.y);
            }
        } else {
            player.moveTo(towards.x, towards.y);
        }
    }

    public void nextStep() {
        if (tickCounter < TICK_MAX) {
            tickCounter++;
            return;
        }
        tickCounter = 0;

        int px = player.x;
        int py = player.y;

        pathfinding = new Pathfinding();
        pathfinding.start(px, py);
        if (pathfinding.dangerous[px][py] != Pathfinding.NOT_DANGEROUS) {
            // Run away if in danger
            Point p = pathfinding.getClosestReachableNicePoint(px, py);
            if (p != null) {
                moveTowards(p, false);
            } else {
                System.out.println("DEBUG MSG: AI has nowhere to run!");
            }
        } else {
            // Run towards player if in reach
            Player closest = pathfinding.getClosestReachablePlayer(player);
            if (closest != null) {
                if (pathfinding.distance[closest.x][closest.y] <= player.radius) {
                    player.putBomb();
                } else {
                    Point p = new Point(closest.x, closest.y);
                    if (p != null) {
                        moveTowards(p, true);
                    }
                }
            } else {
                // Search for boxes
                for (int i = 0; i < 4; i++) {
                    int x2 = px + Direction.x[i];
                    int y2 = py + Direction.y[i];
                    if (pathfinding.contains(x2, y2) && GameData.map.getField(x2, y2).getStrength() > 0) {
                        // Check if we can get away with bombing it
                        pathfinding = new Pathfinding();
                        pathfinding.markDangerous(x2, y2, player.radius, player.bombTickMax);
                        pathfinding.start(px, py);
                        for (int y = 0; y < pathfinding.h; y++) {
                            for (int x = 0; x < pathfinding.w; x++) {
                                if (pathfinding.dangerous[x][y]  == Pathfinding.NOT_DANGEROUS && pathfinding.distance[x][y] != Pathfinding.UNVISITED && pathfinding.distance[x][y] < 3) {
                                    player.putBomb();
                                    return;
                                }
                            }
                        }
                    }
                }
                Point p = pathfinding.getClosestBox();
                if (p != null) {
                    moveTowards(p, true);
                } else {
                    System.out.println("DEBUG MSG: no close box");
                }
            }
        }
    }

}
