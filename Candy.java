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
    protected Colour colour;
    private int x, y;
    private boolean dir;
    public Candy(Colour colour) {
        this.colour = colour;
    }
    
    public void addedToWorld(World w) {
        x = getX();
        y = getY();
    }
    
    /**
     * Act - do whatever the Candy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (Greenfoot.mouseDragged(this)) {
            MainWorld.setClicked(this);
            MouseInfo m = Greenfoot.getMouseInfo();
            int offX = Math.abs(m.getX() - x), offY = Math.abs(m.getY() - y);
            if (offX > FINAL.CELL_SIZE || offY > FINAL.CELL_SIZE)
                setLocation(x, y);
            else if (offX >= FINAL.CELL_SIZE / 2)
                setLocation(m.getX(), y);
            else if (offY >= FINAL.CELL_SIZE / 2)
                setLocation(x, m.getY());
            else
                setLocation(x, y);
        } else if (Greenfoot.mouseDragEnded(this)) {
            Candy overlap = (Candy) getOneIntersectingObject(Candy.class);
            if (overlap != null) {
                if (MainWorld.setClicked(overlap)) setLocation(x, y);
            } else
                setLocation(x, y);
        }
    }
    
    protected void setCandyImage() {
        image.scale(FINAL.CELL_SIZE, FINAL.CELL_SIZE);
        setImage(image);        
    }
    
    public Cell getCell() {
        return (Cell)getOneIntersectingObject(Cell.class);
    }
    
    public Colour getColour() {
        return colour;
    }
    
    public abstract void destroy();
    
    public boolean comp(Candy c) {
        return this.colour == c.getColour();
    }
    
    public String toString() {
        return colour.toString();
    }
    
    public void setOrigin(Pair p) {
        x = p.x;
        y = p.y;
    }
    
    public Pair getOrigin() {
        return new Pair(x, y);
    }
}
