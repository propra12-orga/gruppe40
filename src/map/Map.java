package map;

public class Map {

    private int width, height;
    private boolean singleplayer;

    private Field[][] m;

    public Map(int width, int height, boolean singleplayer) {
        this.width = width;
        this.height = height;
        this.multiplayer = multiplayer;
        m = new Field[width][height]; /* sets mapsize */

        /* sets normal Fields everywhere */
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                m[x][y] = new EmptyField();
            }
        }

        /* spawns destructible walls */
        /* no walls at the spawnpoint */
        for (int x = 3; x < width - 3; x++) {
            for (int y = 1; y < (height - 1); y++) {
                if (Math.random() < 0.5)
                    m[x][y] = new DestructibleWall(1);
            }
        }

        /* If it is a singleplayer-game spawn the exit */
        if (singleplayer) {
            DestructibleWall lastWall = null;
            for (int y = 3; y < height - 3; y++) {
                for (int x = 1; x < (width - 1); x++) {
                    if (Math.random() < 0.9) {
                        lastWall = new DestructibleWall(1);
                        m[x][y] = lastWall;
                    }
                }
            }
            if (lastWall != null) lastWall.setExit(true);
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

    /**
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     * @return
     */
    public Field getField(int x, int y) {
        return m[x][y];
    }

    /**
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     * @param damage
     *            - damage suffered
     */
    public void destroy(int x, int y, int damage) {
        Field field = m[x][y];
        if (field instanceof DestructibleWall) { /* NormalWall etc. */
            DestructibleWall wall = (DestructibleWall)field;
            if (wall.isExit()) {
                m[x][y] = new Exit();
            }else{
                if (m[x][y].getStrength() - damage <= 0) {
                    m[x][y] = new EmptyField();
                } else {
                    m[x][y].setStrength(m[x][y].getStrength() - damage);
                }
            }
        }

    }

    /**
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     */
    public void setExit(int x, int y) {
        DestructibleWall exitWall = new DestructibleWall(1);
        exitWall.setExit(true);
    }
}
