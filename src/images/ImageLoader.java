package images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;


public class ImageLoader {

    /**
     * Paths for images.
     * NOTE THAT PATHS ARE CASE SENSITIVE!
     */
    private static String imagePaths[] = { 	"DestructibleWall.jpg",  	    //[0] 
    									 	"IndestructibleWall.jpg", 		//[1] 
    									 	"EmptyField.jpg", 				//[2]
    									 	"Exit.jpg",						//[3]
    									 	"Bomb.gif",						//[4]
    									 	"Player.gif",					//[5]
    									 	"Explosion.gif"};				//[6]

    private static HashMap<String, Image> images = new HashMap<String, Image>();
    
    static {
        // Loads images
        // This will be executed before first access to this class
        ClassLoader classLoader = ImageLoader.class.getClassLoader();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < imagePaths.length; i++) {
            String path = imagePaths[i];
            URL url = classLoader.getResource("images/" + path);
            if (url == null) {
                System.err.println("failed to locate image: " + path);
            } else {
                Image image = toolkit.createImage(url);
                if (image == null) {
                    System.err.println("failed to create image: " + path);
                } else {
                    path = path.substring(0, path.lastIndexOf('.'));
                    images.put(path, image);
                }
            }
        }
    }
    
    /**
     * Loads image by object name.
     * Note that the image path is case sensitive.
     * File extensions have to be omitted, e.g. "Image" instead of "Image.jpg".
     * @param object Object whose name is used to load the image
     * @return an image
     */
    public static Image getImage(Object object) {
        return getImage(object.getClass().getSimpleName());
    }

    /**
     * Loads image by name.
     * Note that the image path is case sensitive.
     * File extension has to be omitted, e.g. "Image" instead of "Image.jpg".
     * @param name String which is used to load the image
     * @return an image
     */
    public static Image getImage(String name) {
        return images.get(name);
    }
/*
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
    }*/
}
