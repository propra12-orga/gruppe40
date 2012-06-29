package network;

import game.GameData;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyInput implements Serializable {
    private static final long serialVersionUID = GameData.version;
    
    public KeyEvent e;
    public boolean keyDown;
    
    public KeyInput(KeyEvent e, boolean keyDown) {
        this.e = e;
        this.keyDown = keyDown;
    }

}
