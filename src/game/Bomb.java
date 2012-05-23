package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

import draw.Drawable;

import map.Map;

public class Bomb extends Drawable implements ActionListener {
	
	private int radius;
    // inherits x/y from drawable
	//private int x;
	//private int y;
	private Map map;
	private int strength;
	private int delay;
	private Timer timer;
	private long startTime;
	private LinkedList<Drawable> drawables;
	
	public Bomb(int x, int y, Map map, LinkedList<Drawable> drawables){
        super(x, y);
		this.radius = 1;
		this.strength = 1;
		this.delay = 300; //300 milliseconds is too short
		this.startTime = -1;
		this.map = map;
		this.drawables = drawables;
	}
	
	public void setXY(int x, int y, Map map){
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public void explode(){
		map.destroy(x, y, strength);
		int dx[] = {1, 0, -1, 0};
		int dy[] = {0, 1, 0, -1};
        drawables.add(new Explosion(x, y));
		for (int j=0; j<4; j++) {
		    int x2 = x;
		    int y2 = y;
		    for (int i=1; i<=radius; i++) {
                x2 += dx[j];
                y2 += dy[j];
                map.destroy(x2, y2, strength);
                if (map.isBlocked(x2, y2)) break;
                drawables.add(new Explosion(x2, y2));
		    }
		}
		/*
        boolean proceedLeft = true;
        boolean proceedRight = true;
        boolean proceedUp = true;
        boolean proceedDown = true;
		for (int i=1; i<=this.radius; i++){
			if (proceedRight){
				this.map.destroy(this.x + i,this.y, this.strength);
				proceedRight = !this.map.isBlocked(this.x + i,this.y);
			}
			if (proceedLeft){
				this.map.destroy(this.x - i,this.y, this.strength);
				proceedLeft = !this.map.isBlocked(this.x-i,this.y);
			}
			if (proceedUp){
				this.map.destroy(this.x,this.y + i,this.strength);
				proceedUp = !this.map.isBlocked(this.x,this.y+i);
			}
			if (proceedDown){
				this.map.destroy(this.x,this.y - i,this.strength);
				proceedDown = !this.map.isBlocked(this.x,this.y-i);
			}
		}*/
	}
	
	public void startTimer(){
		timer = new Timer(this.delay,this);
		timer.setRepeats(false);
		timer.start();
		this.startTime = System.currentTimeMillis();
	}
	
	public void destroy(){
		this.explode();
	}
	
	public long getTimeUntilExplosion(){
		return (long)this.delay - (System.currentTimeMillis() - this.startTime);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		this.explode();
	}

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getRadius() {
        return radius;
    }
	
    public boolean isExpired() {
        return !timer.isRunning();
    }
}
