package images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

import draw.Drawable;

public class ImageLoader {

    private static HashMap<String, Image> images = new HashMap<String, Image>();

    public static void loadImage(Drawable drawable) {
        String path = drawable.getFrameName();
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

    public static Image getImage(Drawable drawable) {
        String frame = drawable.getFrameName();
        if (!images.containsKey(frame)) loadImage(drawable);
        return images.get(frame);
    }
}
