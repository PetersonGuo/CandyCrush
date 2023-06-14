import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Candy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Candy extends Actor {
    protected GreenfootImage image;
    protected Colour colour;
    private int x, y;
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
        if (Greenfoot.mousePressed(this)) {
            MainWorld.setClicked(this);
        } else if (Greenfoot.mouseDragged(this)) {
            MouseInfo m = Greenfoot.getMouseInfo();
            int offX = Math.abs(m.getX() - x), offY = Math.abs(m.getY() - y);
            if (offX >= offY)
                if (offX > FINAL.CELL_SIZE)
                    setLocation(m.getX() > x ? x + FINAL.CELL_SIZE : x - FINAL.CELL_SIZE, y);
                else
                    setLocation(m.getX(), y);
            else if (offY > offX)
                if (offY > FINAL.CELL_SIZE)
                    setLocation(x, m.getY() > y ? y + FINAL.CELL_SIZE : y - FINAL.CELL_SIZE);
                else
                    setLocation(x, m.getY());
            else
                setLocation(x, y);
        } else if (Greenfoot.mouseClicked(null) && this.equals(MainWorld.getClicked())) {
            Candy overlap = (Candy) getOneIntersectingObject(Candy.class);
            if (overlap == null || !MainWorld.setClicked(overlap))
                setLocation(x, y);
        } else if (Greenfoot.mouseClicked(this))
            MainWorld.setClicked(this);
    }
    
    public abstract void useAbility();
    
    protected void setCandyImage() {
        image.scale(FINAL.CELL_SIZE-5, FINAL.CELL_SIZE-5);
        setImage(image);
    }
    
    public Cell getCell() {
        return (Cell)getOneIntersectingObject(Cell.class);
    }
    
    public Colour getColour() {
        return colour;
    }
        
    public boolean comp(Candy c) {
        return this.colour == c.getColour();
    }
    
    public String toString() {
        return (this instanceof ColourBomb) ? "Bomb" : colour.toString();
    }
    
    public void setOrigin(Pair<Integer, Integer> p) {
        x = p.x;
        y = p.y;
    }
    
    public Pair<Integer, Integer> getOrigin() {
        return new Pair(x, y);
    }
}
