package game;

public class Superbomb extends Bomb{


public Superbomb(int x, int y, Player owner){
        super(x, y, 1, owner);
		this.setRadius(2);
		
	}
}