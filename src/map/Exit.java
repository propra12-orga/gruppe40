package map;

import images.ImageLoader;

public class Exit extends Field {

    public Exit() {
        super(-2, "Exit", ImageLoader.getExitImage());
    }
}
