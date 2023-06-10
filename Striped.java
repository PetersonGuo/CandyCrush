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
            image = new GreenfootImage(FINAL.CANDY_COLOUR[colour] + "_VStripe.png");
        }else{
            image = new GreenfootImage(FINAL.CANDY_COLOUR[colour] + "_HStripe.png");
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
        if(vertical){
            Candy[] column = grid.getColumn(this);
            for(Candy c: column){
                c.destroy();
            }
        } else {
            Candy[] row = grid.getRow(this);
            for(Candy c: row){
                c.destroy();
            }
        }
        destroyed = true;        
    }
    
    public void destroy(){
        
    }
}
