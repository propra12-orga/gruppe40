package ai;

import game.GameData;
import game.Bomb;
import game.Player;

public class AI {

    private Player player;
    
    public AI(Player player) {
        this.player = player;
    }

    public void nextStep() {
        //Dumb AI        
        int w = GameData.map.getWidth();
        int h = GameData.map.getHeight();
        boolean dangerous[][] = new boolean[w][h];
        // All directions (left, up, right, down)
        int dx[] = {-1, 0, 1,  0};
        int dy[] = { 0, 1, 0, -1};
        // For all bombs set fields as dangerous or not
        for (Bomb bomb : GameData.bombs) {
            int x = bomb.getX();
            int y = bomb.getY();
            dangerous[x][y] = true;
            for (int i=0; i<4; i++) {
                int x2 = bomb.getX();
                int y2 = bomb.getY();
                for (int r=1; r<=bomb.getRadius(); r++) {
                    x2 += dx[i];
                    y2 += dy[i];
                    if (GameData.map.contains(x2, y2)) dangerous[x2][y2] = true;
                }
            }
        }
        int x = player.getX();
        int y = player.getY();
        int possibleDirections[] = new int[4];
        int c = 0;
        for (int i=0; i<4; i++) {
            int x2 = player.getX() + dx[i];
            int y2 = player.getY() + dy[i];
            if (GameData.map.contains(x2, y2) && !GameData.map.isBlocked(x2, y2) && !dangerous[x2][y2]) possibleDirections[c++] = i;
        }
        // Walk towards random direction
        if (c > 0) {
            int i = possibleDirections[(int)(Math.random()*c)];
            player.move(x+dx[i], y+dy[i]);
        }
        if (player.hasBomb()) player.putBomb();
        
        // Unbeatable AI: like chess-AIs
        
        // Less dumb AI:
                
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
