package map;

import images.ImageLoader;

public class EmptyField extends Field{

    public EmptyField() {
        super(0, ImageLoader.getNormalFieldImage());
    }
}
