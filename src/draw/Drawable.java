package draw;

import java.awt.Image;

public class Drawable {
	public int x = 0;
	public int y = 0;

	public Image img = null;

	public Drawable(Image img, int x, int y){
		this.x = x;
		this.y = y;
		this.img = img;
	}
	
public Image getImage(){
	return img;
}

public int getX(){
	return x;
}

public int getY(){
	return y;
}

}
