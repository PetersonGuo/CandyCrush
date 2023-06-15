import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Candy called Regular that is a type of candy
 * that has no special features. It only appears as a candy 
 * with its specified colour and no special ability.
 * 
 * @author Kelby To 
 * @version June 15, 2023
 */
public class Regular extends Candy
{
    /**
     * A constructor method that takes a colour and sets its
     * image based on the defined colour of this candy, creating
     * an instance of this candy.
     * 
     * @param colour    The colour of this candy
     */
    public Regular(Colour colour){
        super(colour);
        image = new GreenfootImage(colour + "_Candy.png");
        setCandyImage();
        type = Specials.Normal;
    }
    
    /**
     * A method that does nothing for regular candies since 
     * they dont have any special abilities.
     */
    public void useAbility() {
        
    }
}
