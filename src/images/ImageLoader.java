package images;

import game.Drawable;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;



/**
 * Class to handle Image loading.
 */
public class ImageLoader {

    /**
     * Contains all loaded images.
     */
    public static HashMap<String, Image> images = new HashMap<String, Image>();

    private static void loadImage(Drawable drawable) {
        String path = drawable.getPath();
        URL url = ImageLoader.class.getClassLoader().getResource("images/" + path);
        if (url == null) {
            System.err.println("failed to locate image: " + path);
        } else {
            Image image = Toolkit.getDefaultToolkit().createImage(url);
            
            if (drawable.shouldScale()) {
                image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            }

            if (image == null) {
                System.err.println("failed to create image: " + path);
                images.put(path, null);
            }
            
            images.put(path, image);
        }
    }

    /**
     * The image is loaded if necessary.
     * @param drawable The Drawable of which the Image should be returned.
     * @return The Image associated with this Drawable.
     */
    public static Image getImage(Drawable drawable) {
        String frame = drawable.getPath();
        if (!images.containsKey(frame)) loadImage(drawable);
        return images.get(frame);
    }
}
