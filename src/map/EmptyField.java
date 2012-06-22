package map;

public class EmptyField extends Field{
    
    public EmptyField() {
        super(0);
    }

    @Override
    public String getPath() {
        return "EmptyField.jpg";
    }
}
