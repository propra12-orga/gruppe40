package map;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ImageLoader {

    /**
     * Paths for images (make sure that the indices of all getImage-methods match their desired path, that means, images should be stored under the bin-folder)
     */
    private static String imagePaths[] = { 	"NormalWall.jpg",  				//[0] 
    									 	"IndestructibleWall.jpg", 		//[1] 
    									 	"NormalField.jpg", 				//[2]
    									 	"Exit.jpg",						//[3]
    									 	"Bomb.gif",						//[4]
    									 	"Player.gif",					//[5]
    									 	"Explosion.gif"};				//[6]
    
    private static Image  images[]     = new Image[imagePaths.length];

    /**
     * Loads images (Duh!)
     * @param classLoader
     */
    public static void loadImages() {
        ClassLoader classLoader = ImageLoader.class.getClassLoader();
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
    
    public static Image getBomb() {
        return images[4];
    }
    
    public static Image getPlayer() {
        return images[5];
    }

    public static Image getExplosion() {
        return images[6];
    }
}
