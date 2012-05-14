
public class Bomb extends Item{
	
	private int radius;
	private int x;
	private int y;
	private Map map;
	
	public Bomb(){
		this.radius = 1;
		super(true);
		
	}
	
	public void setXY(int x, int y, Map map){
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public void explode(){
		//TODO: start timer
		boolean proceedLeft = true;
		boolean proceedRight = true;
		boolean proceedUp = true;
		boolean proceedDown = true;
		this.map.destroyContent(this.x,this.y); 
		//TODO: write method destroyContent in class map, suggestion: each object has got method destroy.
		//destroyContent calls each destroy-method
		for (int i=1; i<=this.radius; i++){
			if (proceedRight){
				this.map.destroyContent(this.x + i,this.y);
				proceedRight = !this.map.isBlocked(this.x + i,this.y);
			}
			if (proceedLeft){
				this.map.destroyContent(this.x - i,this.y);
				proceedLeft = !this.map.isBlocked(this.x-i,this.y);
			}
			if (proceedUp){
				this.map.destroyContent(this.x,this.y + i);
				proceedUp = !this.map.isBlocked(this.x,this.y+i)
			}
			if (proceedDown){
				this.map.destroyContent(this.x,this.y - i);
				proceedDown = !this.map.isBlocked(this.x,this.y-i);
			}
		}
	}
	
	public void destroy(){
		this.explode();
	}
	
	
}
