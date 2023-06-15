import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a superclass called Candy that has all the candies in the game. Each
 * Candy is defined by their colour and type (Ingredient, Regular, Special). 
 * Candies also have their own defined x,y coordinates in the game grid that make
 * them easily accessible to access. Additionally, they can be moved around by the
 * player if the match can be made and are stored in individual cells.
 * 
 * @author Peterson Guo, Kelby To
 * @version June 15, 2023
 */
public abstract class Candy extends Actor {
    protected GreenfootImage image;
    protected Colour colour;
    private int x, y;
    
    /**
     * A constructor method that creates any type of candy based on its defined colour.
     * 
     * @param colour    The colour of the candy
     */
    public Candy(Colour colour) {
        this.colour = colour;
    }
    
    /**
     * A method that runs once the world that contains this candy is generated.
     * It will help find the x and y coordinates in the world.
     * 
     * @param w   The world that contains this candy
     */
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
    
    /**
     * An abstract method that all candies will have that allows them to use
     * their abilities (if they have them).
     */
    public abstract void useAbility();
    
    /**
     * A setter method that changes this candy's image.
     */
    protected void setCandyImage() {
        image.scale(FINAL.CELL_SIZE-5, FINAL.CELL_SIZE-5);
        setImage(image);
    }
    
    /**
     * A getter method that returns the cell that contains this candy.
     * 
     * @return Cell     The cell that contains this candy
     */
    public Cell getCell() {
        return (Cell)getOneIntersectingObject(Cell.class);
    }

    /**
     * A getter method that returns the colour of this candy.
     * 
     * @return Colour   The colour of this candy
     */
    public Colour getColour() {
        return colour;
    }
        
    /**
     * A method that takes a candy and returns a boolean if the candy is the
     * same colour as this candy.
     * 
     * @param c         The candy that's colour is compared to this candy
     * @return boolean  True if the colour is the same, false otherwise
     */
    public boolean comp(Candy c) {
        return this.colour == c.getColour();
    }
    
    /**
     * A method that shows this object as a String named after
     * the colour or type of this candy.
     * 
     * @return String   The colour of this candy
     */
    public String toString() {
        return (this instanceof ColourBomb) ? "Bomb" : colour.toString();
    }
    
    /**
     * A setter method that sets the coordinate of this candy to
     * another location in the game grid.
     * 
     * @param p     The coordinate pair that is being set to this
     *              candy
     */
    public void setOrigin(Pair<Integer, Integer> p) {
        x = p.x;
        y = p.y;
    }
    
    /**
     * A getter method that returns the coordinates of this candy.
     * 
     * @return Pair<Integer, Integer>   The x and y coordinates of
     *                                  the candy in the game grid
     */
    public Pair<Integer, Integer> getOrigin() {
        return new Pair(x, y);
    }
}
