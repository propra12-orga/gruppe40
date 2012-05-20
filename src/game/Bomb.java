package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import map.Map;

public class Bomb extends Item implements ActionListener{
	
	private int radius;
	private int x;
	private int y;
	private Map map;
	private int strength;
	private int delay;
	private Timer timer;
	
	public Bomb(){
        super(true);
		this.radius = 1;
		this.strength = 1;
		this.delay = 300; //milliseconds
		
	}
	
	public void setXY(int x, int y, Map map){
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public void explode(){
		boolean proceedLeft = true;
		boolean proceedRight = true;
		boolean proceedUp = true;
		boolean proceedDown = true;
		this.map.destroy(this.x,this.y,this.strength); 
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
		}
	}
	
	public void startTimer(){
		timer = new Timer(this.delay,this);
		timer.setRepeats(false);
		timer.start();
	}
	
	public void destroy(){
		this.explode();
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
	
}
