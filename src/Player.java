
public class Player {

	private int x; //player's x-coordinate
	private int y;// player's y-coordinate
	private Map map; // player knows which field he is playing on
	private Bomb mybomb;
	
	public Player(int x, int y, Map map){
		this.x = x;
		this.y = y;
		this.map = map;
		this.mybomb = new Bomb();
		
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
	
	public boolean moveLeft(){//moves left
		if (this.map.contains(this.x - 1,this.y)){
			if (!this.map.isBlocked(this.x-1,this.y)){
				this.x--;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveRight(){//moves right
		if(this.map.contains(this.x+1,this.y)){
			if(!this.map.isBlocked(this.x+1,this.y)){
				this.x++;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveUp(){//moves up
		if(this.map.contains(this.x, this.y+1)){
			if(!this.map.isBlocked(this.x,this.y+1)){
				this.y++;
				return true;
			}
		}
		return false;
	}
	
	public boolean moveDown(){//moves down
		if(this.map.contains(this.x,this.y-1)){
			if(!this.map.isBlocked(this.x, this.y-1)){
				this.y--;
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBomb(){
		
	}
	
	public void putBomb(){//puts bomb on his position
		this.mybomb.setXY(this.x,this.y,this.map);
		//TODO: send bomb to field
		this.mybomb.explode();
	}
	

}
