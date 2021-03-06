package map;

import game.GameData;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import ai.Pathfinding;

public class Map implements Serializable {
    public static final int   SIZE_MIN_X       = 5;
    public static final int   SIZE_MIN_Y       = 5;
    public static final int   SIZE_MAX_X       = 21;
    public static final int   SIZE_MAX_Y       = 21;
    public static final int   SIZE_DEFAULT_X   = 13;
    public static final int   SIZE_DEFAULT_Y   = 13;

    private static final long serialVersionUID = GameData.version;

    private int               width, height;

    private Field[][]         m;
    private ArrayList<String> MapArray         = null;

    /**
     * @param xml
     *            - path of the xml-file
     */
    public Map(String xml) {
        MapParser testing = new MapParser(xml);
        testing.parse();
        this.width = testing.get_width();
        this.height = testing.get_height();
        this.MapArray = testing.getMapElements();
        m = new Field[this.width][this.height]; /* sets mapsize */

        for (int y = 0, i = 0; y < height; y++) {
            for (int x = 0; x < width; x++, i++) {
                if (this.MapArray.get(i).equals("Exit")) {
                    NormalWall exit = new NormalWall(x, y);
                    exit.setExit(true);
                    m[x][y] = exit;
                } else try {
                    // Get class for field
                    Class<?> whichClass = Class.forName("map." + this.MapArray.get(i));
                    // Create a field using a constructor with parameters x
                    // and y
                    m[x][y] = (Field) whichClass.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(x, y);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map(int width, int height, boolean singleplayer) {
        this.width = width;
        this.height = height;
        m = new Field[width][height]; /* sets mapsize */

        /* sets normal Fields everywhere */
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                m[x][y] = new EmptyField(x, y);
            }
        }

        /* spawns destructible walls */
        /* no walls at the spawnpoint */
        for (int x = 3; x < width - 3; x++) {
            for (int y = 1; y < (height - 1); y++) {
                if (Math.random() < 0.9) m[x][y] = new NormalWall(x, y);
            }
        }

        NormalWall lastWall = null;
        for (int y = 3; y < height - 3; y++) {
            for (int x = 1; x < (width - 1); x++) {
                if (Math.random() < 0.9) {
                    /* If it is a singleplayer-game spawn the exit */
                    if (singleplayer) {
                        lastWall = new NormalWall(x, y);
                        m[x][y] = lastWall;
                    } else m[x][y] = new NormalWall(x, y);
                }
            }
        }

        if (lastWall != null) lastWall.setExit(true);

        /* spawns walls as frame */
        for (int x = 0; x < width; x++) {
            m[x][0] = new IndestructibleWall(x, 0);
            m[x][height - 1] = new IndestructibleWall(x, height - 1);
        }

        for (int y = 0; y < height; y++) {
            m[0][y] = new IndestructibleWall(0, y);
            m[width - 1][y] = new IndestructibleWall(width - 1, y);
        }

        /* spawns the 'standard' walls */
        for (int x = 2; x < width - 2; x += 2) {
            for (int y = 2; y < height - 2; y += 2) {
                m[x][y] = new IndestructibleWall(x, y);
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

    public void setBlocked(int x, int y, boolean block) {
        if (block) {
            m[x][y].setStrength(10);
        } else {
            m[x][y].setStrength(0);
        }
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
     * @return if the field was destroyed
     */
    public boolean destroy(int x, int y, int damage) {
        Field field = m[x][y];
        if (field instanceof DestructibleWall) { /* NormalWall etc. */
            DestructibleWall wall = (DestructibleWall) field;
            if (wall.isExit()) {
                m[x][y] = new Exit(x, y);
            } else {
                if (m[x][y].getStrength() - damage <= 0) {
                    m[x][y].onDestroy();
                    m[x][y] = new EmptyField(x, y);
                } else {
                    m[x][y].setStrength(m[x][y].getStrength() - damage);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @param x
     *            - horizontal axis
     * @param y
     *            - vertical axis
     */
    public void setExit(int x, int y) {
        NormalWall exitWall = new NormalWall(x, y);
        exitWall.setExit(true);
    }

    public void setField(Field field) {
        m[field.x][field.y] = field;
    }

    /**
     * Checks if the map is mirrored vertically and horizontally.
     * 
     * @return If the map is symmetric.
     */
    public boolean isSymmetric() {
        for (int x = 0; x < width / 2; x++) {
            for (int y = 0; y < height / 2; y++) {
                int x2 = width - x - 1;
                int y2 = height - y - 1;
                Field f[] = new Field[] { getField(x, y), getField(x2, y), getField(x2, y2), getField(x, y2) };
                for (int i = 1; i < 4; i++)
                    if (f[0].getClass() != f[i].getClass()) return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        int spawnPositionX[] = { 1, width - 2, width - 2, 1 };
        int spawnPositionY[] = { 1, 1, height - 2, height - 2 };
        
        Pathfinding pathfinding = new Pathfinding(GameData.map, null, null, 1);
        pathfinding.disregardDestroyableWalls = true;
        pathfinding.start(spawnPositionX[0], spawnPositionY[0]);
        for (int j = 0; j < 4; j++) {
            if (pathfinding.isReachable(spawnPositionX[j], spawnPositionY[j]) == false) {
                return false;
            }
        }
        return true;
    }
}
