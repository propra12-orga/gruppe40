package images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

public class ImageLoader {

    /**
     * Paths for images. NOTE THAT PATHS ARE CASE SENSITIVE!
     */
    private static String imagePaths[] = { "DestructibleWall.jpg",
            "NormalWall.jpg", "MediumWall.jpg", "MediumWallCracked.jpg", "IndestructibleWall.jpg",
            "EmptyField.jpg", "Exit.jpg", "Bomb.gif", "Player.gif",
            "Explosion.gif" };

    /**
     * HashMap for storing images associated with their names
     */
    private static HashMap<String, Image> images = new HashMap<String, Image>();

    // Loads images
    static {
        // This will be executed before first access to this class
        // Class loader to have path to start searching at
        ClassLoader classLoader = ImageLoader.class.getClassLoader();
        // Toolkit for loading images
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // For all image paths load images
        for (int i = 0; i < imagePaths.length; i++) {
            String path = imagePaths[i];
            // Add image folder path to image and find internal path
            URL url = classLoader.getResource("images/" + path);
            if (url == null) {
                System.err.println("failed to locate image: " + path);
            } else {
                // Create image from internal path
                Image image = toolkit.createImage(url);
                // Get file extension
                String ext = path.substring(path.lastIndexOf('.') + 1,
                        path.length());
                // If extension is not gif scale the image smoothly
                if (!ext.equals("gif")) {
                    // All scalings except smooth scaling appear to break
                    // animations
                    image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                }
                if (image == null) {
                    System.err.println("failed to create image: " + path);
                } else {
                    // Remove file extension for saving
                    path = path.substring(0, path.lastIndexOf('.'));
                    // Save image in HashMap
                    images.put(path, image);
                }
            }
        }
    }

    /**
     * Loads image by object name. Note that the image path is case sensitive.
     * File extensions have to be omitted, e.g. "Image" instead of "Image.jpg".
     * 
     * @param object
     *            Object whose name is used to load the image
     * @return an image
     */
    public static Image getImage(Object object) {
        return getImage(object.getClass().getSimpleName());
    }

    /**
     * Loads image by name. Note that the image path is case sensitive. File
     * extension has to be omitted, e.g. "Image" instead of "Image.jpg".
     * 
     * @param name
     *            String which is used to load the image
     * @return an image
     */
    public static Image getImage(String name) {
        return images.get(name);
    }
}
