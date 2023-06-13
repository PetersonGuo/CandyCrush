import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Choco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColourBomb extends Special
{
    public ColourBomb() {
        super(null);
        image = new GreenfootImage("Colour_Bomb.png");
        setCandyImage();
    }
    
    public void useAbility(){
        //somehow detect what candy the colour bomb interacts with
        //colour variable represents the colour that the bomb swaps with
        for(Candy c: grid.getCandies()) {
            if(colour == c.getColour()) {
                c.destroy();
            }
        }
        destroyed = true;        
    }
    
    public void destroy() {
        
    }
}
