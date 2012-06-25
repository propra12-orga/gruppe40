package map;

public class EmptyField extends Field{
    
    public EmptyField(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public String getPath() {
        return "EmptyField.jpg";
    }
}
