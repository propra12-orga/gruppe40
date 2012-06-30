package game;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;

import ai.AI;

import main.Bomberman;
import network.Client;
import network.NetworkData;
import network.Server;
import map.Map;
import draw.Drawable;
import draw.GamePanel;

public abstract class GameData {
    public static JFrame                        frame;
    public static LinkedList<Drawable>          drawables;
    public static LinkedList<Bomb>              bombs;
    public static GamePanel                     gamePanel;
    public static Vector<Player>                players;
    public static Map                           map;
    public static Bomberman                     bomberman;
    public static Server                        server;
    public static Client                        client;
    public static NetworkData                   networkData;
    public static int                           playerCount;
    public static JFrame                        menuFrame;
    public static boolean                       running;
    public static final long                    version = 1;
    public static Vector<LinkedList<Character>> keys;
    public static int                           fps     = 60;
    public static LinkedList<AI>                ais;
}
