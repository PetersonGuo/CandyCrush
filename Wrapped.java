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
        if(colour == 0){//red
            image = new GreenfootImage("Red_Wrapper.png");
        } else if (colour == 1) {//yellow
            image = new GreenfootImage("Yellow_Wrapper.png");
        } else if (colour == 2) {//green
            image = new GreenfootImage("Green_Wrapper.png");
        } else if (colour == 3) {//blue
            image = new GreenfootImage("Blue_Wrapper.png");
        }
        setCandyImage();
    }
    /**
     * Act - do whatever the Wrapper wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public void useAbility(){ //explodes in a 3 x 3 square about the origin
         for(Candy c: getObjectsInRange(FINAL.CELL_SIZE * 3 / 2, Candy.class)){
             c.destroy();
         }
    }
}
