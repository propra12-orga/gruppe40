package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import javax.swing.JFrame;

import draw.Drawable;

import map.Map;
import map.Field;

public class Player extends Drawable implements ActionListener {

    //inherits x/y from Drawable
	//private int x; //player's x-coordinate
	//private int y;// player's y-coordinate
	private Map map; // player knows which field he is playing on
	private int direction; // players moving direction 0:no movement, 1:up, 2:right, 3:down, 4:left
	private Timer timer;
	private int speed;
	private LinkedList<Drawable> drawables;
	private long startTime;
	private JFrame frame;
	private int progress;
	private int delay;
	
    // Became obsolete?
    private Bomb bomb;
	
	public Player(int x, int y, int speed, Map map, LinkedList<Drawable> drawables, JFrame frame){
		super(x, y);
		this.map = map;
		this.bomb = new Bomb(-1, -1, map, drawables);
		this.direction = 0;
		this.speed = speed;
		this.delay = 50;
		this.timer = new Timer(this.delay,this);
		this.drawables = drawables;
		this.frame = frame;
		this.progress = 0;
		
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

/** provides flowing movement (X) , used for drawing only */	
	public double getFlowX(){
		switch(this.direction){
			case 1:
			case 3:
				return this.x;
				break;
			case 2:
				return this.x + ((this.progress % this.speed) / this.delay);
				break;
			case 4:
				return this.x - ((this.progress % this.speed) / this.delay);
				break;
		}
	}
	
/** provides flowing movement (Y) , used for drawing only */
	public double getFlowY(){
		switch(this.direction){
			case 2:
			case 4:
				return this.y;
				break;
			case 1:
				return this.y - ((this.progress % this.speed) / this.delay);
				break;
			case 3:
				return this.y + ((this.progress % this.speed) / this.delay);
				break;
		}
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
			case 1: move(0, -1); //1:up
					break;
			case 2: move(1,0); //2:right
					break;
			case 3: move(0,1); //3:down
					break;
			case 4: move(-1,0); //4:left
					break;
			default:  
			        break;
		}
		this.timer.start();
		this.startTime = System.currentTimeMillis();
	}
	
	public void stopMove(){
		this.progress = 0;
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
		System.out.println("newMOVE("+dx+","+dy+")");
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
		return (this.bomb instanceof Bomb); //has to be changed, when bomb explodes, array, when more bombs are available
	}
	
	public void putBomb(){
	    Bomb bomb = new Bomb(x, y, map, drawables);
	    bomb.startTimer();
	    synchronized (drawables) {
	        drawables.add(bomb);
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
	    this.progress = this.progress + this.delay;
		if (this.progress % this.speed == 0){
			switch (this.direction){
				case 1: this.move(0,-1); //1:up
				break;
				case 2: this.move(1,0);  //2:right
				break;
				case 3: this.move(0,1);  //3:down
				break;
				case 4: this.move(-1,0); //4:left
				break;
			}
		}
	
		// Get field the player is standing on
		Field field = this.map.getField(this.getX(), this.getY());
		
		/*
		// If player stands on exit
		if (field instanceof Exit) {
			// Show win dialog
			JOptionPane.showMessageDialog(frame, "You won!");
			exit();
		}
		*/
		
		// Force repaint to make sure everything is drawn
		// If there are no animated gifs visible nothing will be updated automatically
		this.frame.repaint();
	}
	

}
