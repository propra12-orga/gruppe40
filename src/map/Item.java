package map;

import game.GameData;

public class Item extends Field{
	private static final long serialVersionUID = GameData.version;
	
	private int type;

	public Item(int x, int y, int type){
		super(x, y, 0);
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	
	  @Override
    public String getPath() {
		switch(type){
			case 1: 
			default: 
				return "ItemSuperbomb.jpg";
			case 2:
				return "ItemSpeed.jpg";
			case 3:
				return "ItemBox.jpg";
		}
    }
}
