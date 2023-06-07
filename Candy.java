import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Candy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Candy extends Actor
{
    protected GreenfootImage image;
    protected int colour;
    public Candy(int colour){
        this.colour = colour;
    }
    
    /**
     * Act - do whatever the Candy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    protected void setCandyImage(){
        image.scale(FINAL.CELL_SIZE, FINAL.CELL_SIZE);
        setImage(image);        
    }
    
    public Cell getCell(){
        return (Cell)getOneIntersectingObject(Cell.class);
    }
    
    public int getColour(){
        return colour;
    }
    
    public void destroy(){
        
    }
}
