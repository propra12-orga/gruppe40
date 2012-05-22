package game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import draw.Drawable;

import map.Map;

public class Player extends Drawable implements ActionListener {

	private int x; //player's x-coordinate
	private int y;// player's y-coordinate
	private Map map; // player knows which field he is playing on
	private Bomb mybomb;
	private int direction; // players moving direction 0:no movement, 1:up, 2:right, 3:down, 4:left
	private Timer timer;
	private int speed;
	
	public Player(int x, int y, int speed, Map map, Image img){
		super(img, x, y);
		this.map = map;
		this.mybomb = new Bomb();
		this.direction = 0;
		this.speed = speed;
		this.timer = new Timer(this.speed,this);
	}
	
	public int getSpeed(){
		return this.speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setXY(int x, int y){
		this.x =x;
		this.y = y;
	}
	
	// Fixed move, boolean move() didnt work this way - S.B.
	public void startMove(int direction){
	    this.direction = direction;
		switch(direction){ 
			case 1: move(this.x, this.y + 1); //1:up
					break;
			case 2: move(this.x + 1, this.y); //2:right
					break;
			case 3: move(this.x, this.y - 1); //3:down
					break;
			case 4: move(this.x - 1, this.y); //4:left
					break;
			default: move(this.x, this.y); 
			        break;
		}
		this.timer.start();
	}
	
	public void stopMove(){
		this.direction = 0;
		this.timer.stop();
	}
	
/* shouldn't be needed
 	public boolean move(int direction){ 
		int x,y;
		switch(direction){ //1:up
			case 1: x = this.x;
					y = this.y +1;
					break;
			case 2: x = this.x + 1; //2:right
					y = this.y;
					break;
			case 3: x = this.x; //3:down
					y = this.y - 1;
					break;
			case 4: x = this.x -1; //4:left
					y = this.y;
					break;
			default: x = 0; y = 0; break;
		}
		return this.move(x,y);
		
	}
*/
	
	public void move(int dx, int dy) {
	    int x2 = x + dx;
	    int y2 = y + dy;
        if (map.contains(x2, y2) && !map.isBlocked(x2, y2)) {
            //TODO set facing direction
            this.x = x2;
            this.y = y2;
        }
    }
	
	public boolean teleport(int x, int y){// to teleport player
		if (this.map.contains(x,y)){
			if (!this.map.isBlocked(x,y)){
				this.x = x;
				this.y = y;
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBomb(){
		return (this.mybomb instanceof Bomb); //has to be changed, when bomb explodes, array, when more bombs are available
	}
	
	public void putBomb(){//puts bomb on his position
		this.mybomb.setXY(this.x,this.y,this.map);
		//TODO: send bomb to field
		this.mybomb.startTimer();
		//this.mybomb = null; //bomb doesn't exist any longer
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
	    /*
		switch (this.direction){
		case 1: this.moveUp();
				break;
		case 2: this.moveRight();
				break;
		case 3: this.moveDown();
				break;
		case 4: this.moveLeft();
				break;
		}
		*/
	}
	

}
