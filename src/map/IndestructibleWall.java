package map;

import images.ImageLoader;

public class IndestructibleWall extends Field {

    public IndestructibleWall() {
        super(-1, ImageLoader.getIndestructibleWallImage());
    }
}
