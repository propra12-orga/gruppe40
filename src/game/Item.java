package game;

public class Item {
	private boolean explodable;
	
	public Item(boolean explodable){
		this.explodable = explodable;
	}
	
	public boolean isExplodable(){
		return this.explodable;
	}
	
	public void setExplodable(boolean explodable){
		this.explodable = explodable;
	}
}
