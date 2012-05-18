package map;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ImageLoader {

    private static String imagePaths[] = { "NormalWall.jpg", "IndestructibleWall.jpg", "NormalField.jpg", "Exit.jpg" };
    private static Image  images[]     = new Image[imagePaths.length];

    public static void init(ClassLoader classLoader) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < imagePaths.length; i++) {
            URL url = classLoader.getResource(imagePaths[i]);
            if (url == null) {
                System.err.println("failed to load image: " + imagePaths[i]);
            } else {
                images[i] = toolkit.createImage(url);
            }
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
