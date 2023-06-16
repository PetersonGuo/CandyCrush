import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Candy and superclass of different types of special candies
 * called Special. Types include ColourBomb, Striped, and Wrapper candies 
 * that each have special abilities when they are destroyed.
 * 
 * @author Kelby To, Isaac Chan
 * @version June 15, 2023
 */
public abstract class Special extends Candy {
    protected GameGrid grid;
    
    /**
     * A constructor method that takes a colour and finds its location
     * in the game grid.
     */
    public Special(Colour colour){
        super(colour);
        grid = MainWorld.getGrid();
    }
    
    /**
     * Act - do whatever the Special wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
    }
}