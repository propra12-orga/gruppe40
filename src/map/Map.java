package map;

public class Map {

    private int width, height;
    boolean exit;

    private Field[][] m;

    public Map(int width, int height, boolean exit) {
        this.width = width;
        this.height = height;
        this.exit = exit;
        m = new Field[width][height]; /* sets mapsize */

        /* spawns destructible walls */
        /* no walls at the spawnpoint */
        for (int x = 3; x < width - 3; x++) {
            for (int y = 1; y < (height - 1); y++) {
                if (Math.random() < 0.5)
                    m[x][y] = new NormalWall();
            }
        }
        for (int y = 3; y < height - 3; y++) {
            for (int x = 1; x < (width - 1); x++) {
                if (Math.random() < 0.5)
                    m[x][y] = new NormalWall();
            }
        }

        /* spawns walls as frame */
        for (int x = 0; x < width; x++) {
            m[x][0] = new IndestructibleWall();
            m[x][height - 1] = new IndestructibleWall();
        }

        for (int y = 0; y < height; y++) {
            m[0][y] = new IndestructibleWall();
            m[width - 1][y] = new IndestructibleWall();
        }

        /* spawns the 'standard' walls */
        for (int x = 2; x < width - 2; x += 2) {
            for (int y = 2; y < height - 2; y += 2) {
                m[x][y] = new IndestructibleWall();
            }
        }

        /* spawns the exit */
        if (exit) {
            m[width - width / 2][height - height / 2] = new Exit();
        }

    }

    /**
     * checking if the field is blocked
     * 
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     * @return true if blocked
     */
    public boolean isBlocked(int x, int y) {
        return m[x][y].getStrength() != 0;
    }

    /**
     * checking if the field is part of the map
     * 
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     * @return true if part of the map
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    /**
     * @return width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return heigth of the map
     */
    public int getHeight() {
        return height;
    }
}