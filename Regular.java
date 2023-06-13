import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Regular here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Regular extends Candy
{
    public Regular(Colour colour){
        super(colour);
        image = new GreenfootImage(colour + "_Candy.png");
        setCandyImage();
    }
    
    public void destroy(){
        
    }
}
