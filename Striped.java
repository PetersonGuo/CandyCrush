import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Special called Stripe that has the ability to break 
 * all candies in its row or column depending on the type of striped 
 * candy this candy is defined by. Like other candies, this candy has 
 * a defining colour. Additionally, they can be made if the match is 4 
 * candies.
 * 
 * @author Peterson Guo, Kelby To, Isaac Chan 
 * @version June 15, 2023
 */
public class Striped extends Special {
    private boolean vertical;
    
    /**
     * A constructor method that takes a colour and direction of the
     * stripe and creates a striped candy and setting it image.
     * 
     * @param colour    The colour of this candy
     * @param vertical  If true, the stripe is vertical and if false,
     *                  the stripe is horizontal
     */
    public Striped(Colour colour, boolean vertical){
        super((colour == null) ? Colour.random() : colour);
        this.vertical = vertical;
        if(vertical)
            image = new GreenfootImage(colour + "_VStripe.png");
        else
            image = new GreenfootImage(colour + "_HStripe.png");
        setCandyImage();
        type = Specials.Striped;
    }
    
    /**
     * Act - do whatever the Striped wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
    }
    
    /**
     * A method that allows this candy to break either a row or column
     * depending on the direction of the stripe.
     */
    public void useAbility(){
        if (vertical) {
            grid.clearCol(this);
        } else {
            grid.clearRow(this);
        }
    }
}
