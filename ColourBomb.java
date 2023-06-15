import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Special called ColourBomb that has the ability to 
 * break all candies of a certain colour that are currently on the 
 * grid if matched with the specified colour. These candies are 
 * created when a match of 5 or greater is made.
 * 
 * @author Kelby To, Isaac Chan
 * @version June 15, 2023
 */
public class ColourBomb extends Special {
    /**
     * A constructor method that creates a colour bomb and sets its image.
     */
    public ColourBomb() {
        super(null);
        image = new GreenfootImage("Colour_Bomb.png");
        setCandyImage();
    }
    
    /**
     * A method that allows this candy to break all candies of a 
     * random colour.
     */
    public void useAbility() {
        grid.clearColour(Colour.random());
    }
}