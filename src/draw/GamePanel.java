package Draw;

import map.Map;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
Map map;

public GamePanel(Map map){
	
	this.map = map;
    setBounds(0, 0, 700, 700);
     
}

public void drawMap(Graphics g0){
	super.paintComponent(g0);
	Graphics2D g= (Graphics2D) g0;
	
	for (int y=0; y<map.getHeight(); y++){
		 for(int x=0; x<map.getWidth(); x++){
			 int x1 = (int) (x * (700 / (double) map.getWidth()));
			 int y1 = (int) (y * (700 / (double) map.getHeight()));
			// g.drawImage(img, x1, y1,(int) ((700 / (double) map.getWidth())+1), (int) ((700 / (double) map.getHeight())), this);
			 //g.drawImage(map.getField(x, y).getImage(), x1, y1,(int) ((700 / (double) map.getWidth())+1), (int) ((700 / (double) map.getHeight())), this);
			 
		 }	
}
}
}








/*public void drawTile(Graphics2D g){
	
	 for (int y=0; y<map.getHeight(); y++){
		 for(int x=0; x<map.getWidth(); x++){
			 int x1 = (int) (x* (700 / (double) map.getWidth()));
			 int y1 = (int) (y*(700 / (double) map.getHeight()));
			 g.drawImage(map.getField(x, y).getImage(),x, y, x1 + 1, y1 + 1, this);
			 
			 
		 }
	 }

	
	
*/


