/**
 * A set of enumerations that categorize colours
 * 
 * @author Peterson Guo
 * @version June 15, 2023
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
    
    /**
     * A constructor method that takes a colour as a string and sets
     * its own colour as it.
     * 
     * @param colour   A string of the colour
     */
    private Colour(String colour) {
        this.colour = colour;
    }
    
    /**
     * A method that randomly chooses one of the enums listed and returns
     * it.
     * 
     * @return Colour   A random colour
     */
    public static Colour random() {
        return values[(int)(Math.random() * values.length)];
    }
    
    /**
     * An overridden method that converts this object into a string that
     * is this object's colour
     * 
     * @return String   The colour that this is defined as
     */
    @Override
    public String toString(){
        return colour;
    }
}