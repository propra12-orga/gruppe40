package view;

import game.Map;
import game.Thing;

import java.awt.Graphics;

import javax.swing.JPanel;

import data.Data;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    public GamePanel() {
        setBounds(0, 0, Data.width, Data.height);
    }

    public void drawTile(int x, int y, int imageId) {
        x = (int) (x * Data.scaleX);
        y = (int) (y * Data.scaleY);
        // +1 to width and height to remove gaps between tiles.
        // Gaps probably occur because java uses integers.
        Data.g.drawImage(Data.images[imageId], x, y, (int) Data.scaleX + 1, (int) Data.scaleY + 1, this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawString("Waiting for data from server...", 100, 100);
        
        Data.g = g;
        Map map = Data.map;
        Thing[] things = Data.things;
        if (map == null) return;
        if (things == null) return;
        
        map.draw();
        for (Thing thing : things) thing.draw();
    }
}