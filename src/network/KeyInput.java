package network;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyInput implements Serializable {
    
    public KeyEvent e;
    public boolean keyDown;
    
    public KeyInput(KeyEvent e, boolean keyDown) {
        this.e = e;
        this.keyDown = keyDown;
    }

}
