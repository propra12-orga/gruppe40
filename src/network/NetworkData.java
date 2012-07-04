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
    public long time;
    
    /**
     * Data to be sent over the network from the server to all clients.
     * @param drawables List of objects which should be drawn on the client.
     * @param map Map which is used to calculate the aspect ratio of the client screens.
     * @param time Time to be used for explosions and similar things.
     */
    public NetworkData(LinkedList<Drawable> drawables, Map map, long time) {
        this.drawables = drawables;
        this.map = map;
        this.time = time;
    }
    
}
