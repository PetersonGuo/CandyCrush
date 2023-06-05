import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Special here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Special extends Candy
{
    public Special(int colour){
        super(colour);
    }

    protected abstract void useAbility();

    public void act()
    {
        // Add your action code here.
    }
}