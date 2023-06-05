import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Regular here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Regular extends Candy
{
    public Regular(int colour){
        super(colour);
        if(colour == 0){
            image = new GreenfootImage("");
        } else if (colour == 1) {
            image = new GreenfootImage("");
        } else if (colour == 2) {
            image = new GreenfootImage("");
        } else if (colour == 3) {
            image = new GreenfootImage("");
        }
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
