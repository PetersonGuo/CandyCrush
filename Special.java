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
    
    public Special(int colour){
        super(colour);
        grid = (getWorld().getObjects(GameGrid.class)).get(0);
    }

    protected abstract void useAbility();
}