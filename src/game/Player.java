package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import draw.Drawable;

import map.Map;

public class Player extends Drawable implements ActionListener {

    private Map      map;      // player knows which field he is playing on
    private int      direction; // players moving direction 0:no movement, 1:up,
                                // 2:right, 3:down, 4:left
    private Timer    timer;
    private int      speed;
    private int      progress;
    private int      delay;
    private boolean  alive;
    private GameData data;
    private String name;

    public Player(String name, int x, int y, int speed, GameData data) {
        super(x, y, data);
        this.name = name;
        this.map = data.map;
        this.direction = 0;
        this.speed = speed;
        this.delay = 50;
        this.timer = new Timer(this.delay, this);
        this.progress = 0;
        this.data = data;
        this.alive = true;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return this.x;
    }

    /** provides flowing movement (X) , used for drawing only */
    public double getFlowX() {
        switch (this.direction) {
            case 2:
                return this.x + ((this.progress % this.speed) / this.delay);

            case 4:
                return this.x - ((this.progress % this.speed) / this.delay);

            default:
                return this.x;
        }// Fixed missing default case, removed unreachable code (Please do not
         // push code with errors)
    }

    /** provides flowing movement (Y) , used for drawing only */
    public double getFlowY() {
        switch (this.direction) {
            case 1:
                return this.y - ((this.progress % this.speed) / this.delay);

            case 3:
                return this.y + ((this.progress % this.speed) / this.delay);

            default:
                return this.y;
        }// Fixed missing default case, removed unreachable code (Please do not
         // push code with errors)
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Fixed move, boolean move() didnt work this way - S.B.
    public void startMove(int direction) {
        this.direction = direction;
        switch (direction) {
            case 1:
                move(0, -1); // 1:up
            break;
            case 2:
                move(1, 0); // 2:right
            break;
            case 3:
                move(0, 1); // 3:down
            break;
            case 4:
                move(-1, 0); // 4:left
            break;
            default:
            break;
        }
        this.timer.start();
    }

    public void stopMove() {
        this.progress = 0;
        this.direction = 0;
        this.timer.stop();
    }

    public void move(int dx, int dy) {
        if (!alive) return;
        System.out.println("newMOVE(" + dx + "," + dy + ")");
        int x2 = x + dx;
        int y2 = y + dy;
        if (map.contains(x2, y2) && !map.isBlocked(x2, y2)) {
            // TODO set facing direction
            this.x = x2;
            this.y = y2;
        }
    }

    public boolean teleport(int x, int y) {// to teleport player
        if (this.map.contains(x, y)) {
            if (!this.map.isBlocked(x, y)) {
                this.x = x;
                this.y = y;
                return true;
            }
        }
        return false;
    }

    public boolean hasBomb() {
        return true; // TODO has to be changed, when bomb explodes, array, when
                     // more bombs are available
    }

    public void putBomb() {
        if (!alive) return;
        Bomb bomb = new Bomb(x, y, data);
        bomb.startTimer();
        synchronized (data.drawables) {
            data.drawables.add(bomb);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.progress = this.progress + this.delay;
        if (this.progress % this.speed == 0) {
            switch (this.direction) {
                case 1:
                    this.move(0, -1); // 1:up
                break;
                case 2:
                    this.move(1, 0); // 2:right
                break;
                case 3:
                    this.move(0, 1); // 3:down
                break;
                case 4:
                    this.move(-1, 0); // 4:left
                break;
            }
        }

        // Force repaint to make sure everything is drawn
        // If there are no animated gifs visible nothing will be updated
        // automatically
        data.frame.repaint();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        this.setVisible(false);
    }

    public boolean isAlive() {
        return alive;
    }

    public String getName() {
        return name;
    }

}
