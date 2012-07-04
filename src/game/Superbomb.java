package game;

public class Superbomb extends Bomb {

    private static final long serialVersionUID = -4118186866125080195L;

    public Superbomb(Player owner) {
        super(owner);
        this.setRadius(3);
    }
   
    @Override
    public String getPath() {
        return "SuperBomb.png";
    }    
}