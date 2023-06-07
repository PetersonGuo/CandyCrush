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
        if(colour == 0){//red
            image = new GreenfootImage("Red_Candy.png");
        }else if (colour == 1){//orange
            image = new GreenfootImage("Orange_Candy.png");
        }else if (colour == 2){//yellow
            image = new GreenfootImage("Yellow_Candy.png");
        }else if (colour == 3){//green
            image = new GreenfootImage("Green_Candy.png");
        }else if (colour == 4){//blue
            image = new GreenfootImage("Blue_Candy.png");
        }else if (colour == 5){///purple
            image = new GreenfootImage("Purple_Candy.png");            
        }
        setCandyImage();
    }

    public void act()
    {
        // Add your action code here.
    }
}
