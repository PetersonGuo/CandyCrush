import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * A subclass of Special that has the ability to break candies in 
 * a 3x3 grid around this candy. Like other candies, this candy has 
 * a defining colour. Additionally, they can be made if the match 
 * is 2 intersecting matches of 3 or greater.
 * 
 * @author Kelby To, Isaac Chan
 * @version June 15, 2023
 */
public class Wrapped extends Special {
    /**
     * A constructor method that takes a colour and creates a 
     * wrapper candy and sets its image.
     */
    public Wrapped(Colour colour) {
        super(colour);
        image = new GreenfootImage(colour + "_Wrapper.png");
        setCandyImage();
    }
    
    /**
     * A method that allows this candy to break a 3x3 grid about
     * the wrapped candy.
     */
    public void useAbility(){
        grid.getExploGrid(this);
    }
}
