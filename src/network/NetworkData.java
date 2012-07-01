package network;

import game.Drawable;
import game.GameData;

import java.io.Serializable;
import java.util.LinkedList;

import map.Map;


public class NetworkData implements Serializable {
    private static final long serialVersionUID = GameData.version;

    public LinkedList<Drawable> drawables;
    public Map map;
    
    public NetworkData(LinkedList<Drawable> drawables, Map map) {
        this.drawables = drawables;
        this.map = map;
    }
    
}
