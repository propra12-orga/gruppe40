package map;

import java.awt.Image;

public class ImageLoader {

    private static String imagePaths[] = { "NormalWall.jpg",
            "IndestructibleWall.jpg", "NormalField.jpg", "Exit.jpg" };
    private static Image images[] = new Image[imagePaths.length];

    public static void init() {
        for (int i = 0; i < imagePaths.length; i++) {

        }
    }

    public static Image getNormalWallImage() {
        return images[0];
    }

    public static Image getIndestructibleWallImage() {
        return images[1];
    }

    public static Image getNormalFieldImage() {
        return images[2];
    }

    public static Image getExitImage() {
        return images[3];
    }

}
