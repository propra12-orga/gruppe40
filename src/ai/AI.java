package ai;

import java.awt.Point;
import java.util.LinkedList;

import map.Map;
import map.Path;

public class AI {

    private Player           player;
    private Map              map;
    private LinkedList<Bomb> bombs;
    private Player[]         players;

    public AI(Player player, Map map, LinkedList<Bomb> bombs, Player[] players) {
        this.player = player;
        this.map = map;
    }
    
    private Point findClosestSavePoint(boolean dangerous[][]) {
        Point safePoint = null;
        int shortestDistance = Integer.MAX_VALUE;
        //TODO search smarter (path finding with multiple end points)
        //Find closest safe point
        int w = map.getWidth();
        int h = map.getHeight();
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                if (!dangerous[x][y]) {
                    int distance = Path.find(map, player.getX(), player.getY(), x, y);
                    if (distance != -1 && distance < shortestDistance) {
                        shortestDistance = distance;
                        safePoint = new Point(x, y);
                    }
                }
            }
        }
        return safePoint;
    }

    public void nextStep() {
        int w = map.getWidth();
        int h = map.getHeight();
        //TODO make AI smarter by considering duration until bomb explodes
        boolean dangerous[][] = new boolean[w][h];
        int dx[] = {-1, 0, 1,  0};
        int dy[] = { 0, 1, 0, -1};
        for (Bomb bomb : bombs) {
            int x = bomb.getX();
            int y = bomb.getY();
            dangerous[x][y] = true;
            for (int r=1; r<=bomb.getRadius(); r++) {
                for (int i=0; i<4; i++) dangerous[x+dx[i]*r][y+dy[i]*r] = true;//Replace with duration until detonation
            }
        }
        if (dangerous[player.getX()][player.getY()]) {
            Point safePoint = findClosestSavePoint(dangerous);
            if (safePoint != null) {
                
            }
        }
        
        // if threatened by bomb
        //     find safe place
        //     go there
        // else
        //     if an enemy is close
        //         if there is a safe path to run away
        //             drop bomb and run
        //         else
        //             find safe place to run away from and go there
        //         endif
        //     else
        //         find free paths to enemies
        //         if there are such paths
        //             choose shortest path and go closer
        //         else
        //             find shortest path to enemies through destructible objects
        //             if first block can be exploded safely do this
        //             else safely explode random block in range
        //         endif
        //     endif
        //endif
    }

}
