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
        image = new GreenfootImage(FINAL.CANDY_COLOUR[colour] + "_Candy.png");
        setCandyImage();
    }
    
    public void destroy(){
        
    }
}
