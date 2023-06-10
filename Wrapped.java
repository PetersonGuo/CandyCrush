import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Wrapper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wrapped extends Special
{
    public Wrapped(int colour){
        super(colour);
        image = new GreenfootImage(FINAL.CANDY_COLOUR[colour] + "_Wrapper.png");
        setCandyImage();
    }
    public void useAbility(){ //explodes in a 3 x 3 square about the origin
        Candy[] explodeCandies = grid.getExploGrid(this);
        for(Candy c: explodeCandies){
            c.destroy();
        }
        destroyed = true;
    }
    public void destroy(){
        
    }
}
