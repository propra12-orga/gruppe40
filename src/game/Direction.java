package game;

public class Direction {

    public static final int RIGHT = 0;
    public static final int UP    = 1;
    public static final int LEFT  = 2;
    public static final int DOWN  = 3;
    public static int       x[]   = { 1, 0, -1, 0 };
    public static int       y[]   = { 0, -1, 0, 1 };

    /**
     * Get direction from x/y-offset. Only works for one field offsets.
     * 
     * @param dx X-translation
     * @param dy Y-translation
     * @return Integer indicating a direction which is RIGHT/UP/LEFT/DOWN from 0
     *         to 3.
     */
    public static int get(int dx, int dy) {
        for (int i = 0; i < 4; i++)
            if (dx == Direction.x[i] && dy == Direction.y[i]) return i;
        return 0;
    }
}
