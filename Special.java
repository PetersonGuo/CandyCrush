import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Special here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Special extends Candy
{
    protected GameGrid grid;
    protected boolean destroyed = false;
    
    public Special(Colour colour){
        super(colour);
        grid = MainWorld.getGrid();
    }
    
    public void act() {
        super.act();
    }
}