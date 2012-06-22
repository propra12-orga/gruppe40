package images;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;

import draw.Drawable;

public class ImageLoader {

    private static HashMap<String, Image> images = new HashMap<String, Image>();
    private ImageObserver observer;

    public ImageLoader(ImageObserver observer) {
        this.observer = observer;
    }

    public void loadImage(Drawable drawable) {
        ClassLoader classLoader = ImageLoader.class.getClassLoader();
        String path = drawable.getFrameName();
        URL url = classLoader.getResource("images/" + path);
        if (url == null) {
            System.err.println("failed to locate image: " + path);
        } else {
            Image image = Toolkit.getDefaultToolkit().createImage(url);

            if (image == null) {
                System.err.println("failed to create image: " + path);
                System.exit(1);
            }

            while (image.getWidth(observer) < 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int width = image.getWidth(observer);
            if (width < 0) System.err.println("Failed to load image for some unknown java-reason");
            
            images.put(path, image);
        }
    }

    public Image getImage(Drawable drawable) {
        String frame = drawable.getFrameName();
        if (!images.containsKey(frame)) loadImage(drawable);
        return images.get(frame);
    }
}
