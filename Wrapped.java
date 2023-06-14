import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Wrapper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wrapped extends Special {
    public Wrapped(Colour colour) {
        super(colour);
        image = new GreenfootImage(colour + "_Wrapper.png");
        setCandyImage();
    }
    
    public void useAbility(){ // explodes in a 3 x 3 square about the origin
        Candy[] explodeCandies = grid.getExploGrid(this);
    }
}
