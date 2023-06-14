import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stripe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Striped extends Special {
    private boolean vertical;
    
    public Striped(Colour colour, boolean vertical){
        super(colour);
        this.vertical = vertical;
        if(vertical)
            image = new GreenfootImage(colour + "_VStripe.png");
        else
            image = new GreenfootImage(colour + "_HStripe.png");
        setCandyImage();
    }
    
    /**
     * Act - do whatever the Stripe wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
    }
    
    public void useAbility(){
        if (vertical) {
            grid.clearCol(this);
        } else {
            grid.clearRow(this);
        }
    }
}
