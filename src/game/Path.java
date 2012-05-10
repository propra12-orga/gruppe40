package game;

public class Path {

    /**
     * Just for debugging
     * 
     * @param args
     */
    public static void main(String[] args) {
        //TODO find out why this does not work for other maps
        //Try a few times until the map has a path
        while (!Path.find(new Map(10, 10), 1, 1, 7, 8));
    }

    /**
     * Finds a non-blocked path on a map from a tile to another.
     * 
     * @param map Map to search path on
     * @param x0 start x-coordinate
     * @param y0 start y-coordinate
     * @param xEnd end x-coordinate
     * @param yEnd end y-coordinate
     * @return
     */
    public static boolean find(Map map, int x0, int y0, int xEnd, int yEnd) {
        int w = map.getWidth();
        int h = map.getHeight();
        boolean[][] visited = new boolean[w][h];
        int[][] prev = new int[w][h];
        int[] stackX = new int[w * h];
        int[] stackY = new int[w * h];

        boolean pathFound = false;

        stackX[0] = x0;
        stackY[0] = y0;
        int stackPos = 0;
        int stackSize = 1;

        //Left, right, up, down
        int dx[] = { -1, 1, 0, 0 };
        int dy[] = { 0, 0, 1, -1 };

        // While there are tiles to visit
        while (stackSize > stackPos) {
            int x = stackX[stackPos];
            int y = stackY[stackPos];
            // For all 4 sides
            for (int i = 0; i < 4; i++) {
                int x2 = x + dx[i];
                int y2 = y + dy[i];

                // If coordinate is not on map, visited or blocked, skip this
                // and check other tiles
                if (!map.contains(x2, y2) || visited[x2][y2] || map.isBlocked(x2, y2)) continue;
                
                // Mark previous tile for tracing back later
                prev[x2][y2] = stackPos;
                
                // Mark tile as visited
                visited[x2][y2] = true;
                
                //Push new tile on stack
                stackX[stackSize] = x2;
                stackY[stackSize] = y2;
                stackSize++;
                
                if (x2 == xEnd && y2 == yEnd) {
                    pathFound = true;
                    System.out.println("found path");
                    break;
                }
            }
            stackPos++;
        }

        if (!pathFound) return false;

        // Iterating through path (testing)
        int i = prev[xEnd][yEnd];
        while (i != 0) {
            int x = stackX[i];
            int y = stackY[i];
            i = prev[x][y];
            // Setting
            map.m[x][y] = 3;
            System.out.println(i + ": " + x + "/" + y);
        }

        // Print ASCII map with path (testing)
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int j = map.m[x][y];
                if (j < 0) j = 2;
                if (j > 9) j = 9;
                if (j == 3) {
                    System.out.print("#");
                } else {
                    System.out.print(j);
                }
            }
            System.out.println();
        }
        return true;
    }
}
