import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stripe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Striped extends Special
{
    private boolean vertical;
    
    public Striped(int colour, boolean vertical){
        super(colour);
        this.vertical = vertical;
        if(vertical){
            if(colour == 0){//red
                image = new GreenfootImage("Red_VStripe.png");
            } else if (colour == 1) {//yellow
                image = new GreenfootImage("Yellow_VStripe.png");
            } else if (colour == 2) {//green
                image = new GreenfootImage("Green_VStripe.png");
            } else if (colour == 3) {//blue
                image = new GreenfootImage("Blue_VStripe.png");
            }
        }else{
            if(colour == 0){//red
                image = new GreenfootImage("Red_HStripe.png");
            } else if (colour == 1) {//yellow
                image = new GreenfootImage("Yellow_HStripe.png");
            } else if (colour == 2) {//green
                image = new GreenfootImage("Green_HStripe.png");
            } else if (colour == 3) {//blue
                image = new GreenfootImage("Blue_HStripe.png");
            }            
        }
        setCandyImage();
    }
    /**
     * Act - do whatever the Stripe wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public void useAbility(){
        
    }
}
