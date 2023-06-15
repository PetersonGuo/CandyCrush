/**
 * Write a description of class CANDY_COLOUR here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public enum Colour  {
    RED("Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue"),
    PURPLE("Purple");
    private static final Colour[] values = values();
    private String colour;
    private Colour(String colour) {
        this.colour = colour;
    }
    
    public static Colour random() {
        return values[(int)(Math.random() * values.length)];
    }
    
    public static Colour[] getColours(){
        return values;
    }
    
    @Override
    public String toString(){
        return colour;
    }
}