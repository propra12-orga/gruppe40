package network;

import java.util.LinkedList;

import map.Map;

import draw.Drawable;

public class NetworkData  {

    public LinkedList<Drawable> drawables;
    public Map map;
    
    public NetworkData(LinkedList<Drawable> drawables, Map map) {
        this.drawables = drawables;
        this.map = map;
    }
    
}
