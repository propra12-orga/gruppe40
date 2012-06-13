package game;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;

import main.Bomberman;
import map.Map;
import draw.Drawable;
import draw.GamePanel;

public class GameData {
    public JFrame               frame;
    public LinkedList<Drawable> drawables;
    public LinkedList<Bomb>     bombs;
    public GamePanel            gamePanel;
    public Vector<Player>       players;
    public Map                  map;
    public Bomberman            bomberman;
    public boolean              gameOver;
}
