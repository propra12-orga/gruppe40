import javax.swing.Timer;

public class Player implements ActionListener{

	private int x; //player's x-coordinate
	private int y;// player's y-coordinate
	private Map map; // player knows which field he is playing on
	private Bomb mybomb;
	private int direction; // players moving direction 0:no movement, 1:up, 2:right, 3:down, 4:left
	private Timer timer;
	
	public Player(int x, int y, Map map){
		this.x = x;
		this.y = y;
		this.map = map;
		this.mybomb = new Bomb();
		this.direction = 0;
		this.timer = new Timer(100,this);
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
	
	public boolean startMove(int direction){
	    this.direction = direction;
		this.move(direction);
		this.timer.start();
	}
	
	public void stopMove(){
		this.direction = 0;
		this.timer.stop();
	}
	
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
		}
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
	
	public void actionPerformed(ActionEvent e){
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
	}
	

}
