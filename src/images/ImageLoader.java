package images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;


public class ImageLoader {

    /**
     * Paths for images.
     * NOTE THAT PATHS ARE CASE SENSITIVE!
     * Make sure that the indices of all getImage-methods match their desired path.
     */
    private static String imagePaths[] = { 	"NormalWall.jpg",  				//[0] 
    									 	"IndestructibleWall.jpg", 		//[1] 
    									 	"NormalField.jpg", 				//[2]
    									 	"Exit.jpg",						//[3]
    									 	"Bomb.gif",						//[4]
    									 	"Player.gif",					//[5]
    									 	"Explosion.gif"};				//[6]
    
    private static Image  images[]     = new Image[imagePaths.length];
    
    static {
        // Loads images
        // This will be executed before first access to this class
        ClassLoader classLoader = ImageLoader.class.getClassLoader();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < imagePaths.length; i++) {
            URL url = classLoader.getResource("images/" + imagePaths[i]);
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
    
    public static Image getBombImage() {
        return images[4];
    }
    
    public static Image getPlayerImage() {
        return images[5];
    }

    public static Image getExplosionImage() {
        return images[6];
    }
}
