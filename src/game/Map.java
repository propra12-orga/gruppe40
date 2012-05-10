package game;

public class Map {

    int w, h;
    int WALL = -1;
    int DWALL = 1;

    int[][] m;

    public Map(int w, int h) {
		this.w = w;
		this.h = h;
		m = new int[w][h];

		for (int x = 3; x<w-3; x++) {
		    for (int y = 1; y<(h-1); y++) {
		        if (Math.random()<0.5) m[x][y] = DWALL;
		    }
		}
        for (int y = 3; y<h-3; y++) {
            for (int x = 1; x<(w-1); x++) {
                if (Math.random()<0.5) m[x][y] = DWALL;
            }
        }
		
		for (int x = 0; x < w; x++) {
			m[x][0] = WALL;
			m[x][h - 1] = WALL;
		}
		
		for (int y = 0; y < h; y++) {
			m[0][y] = WALL;
			m[w-1][y] = WALL;
		}

		for (int x = 2; x<w-2; x += 2) {
			for(int y = 2; y<h-2; y += 2) {
				m[x][y] = WALL;
			}
		}

	}

    public boolean isBlocked(int x, int y) {
        return m[x][y] == 0;
    }

    public boolean onMap(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h;
    }
    
    public int getWidth() {
        return w;
    }
    
    public int getHeight() {
        return h;
    }
}