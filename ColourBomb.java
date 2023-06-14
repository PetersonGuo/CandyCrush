import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Choco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColourBomb extends Special {
    public ColourBomb() {
        super(null);
        image = new GreenfootImage("Colour_Bomb.png");
        setCandyImage();
    }
    
    public void useAbility(){
        usePower(Colour.random());
        destroyed = true;        
    }
    
    public void usePower(Colour c){
        grid.clearColour(c);
    }
    
    public void destroy() {
        
    }
}