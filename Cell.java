import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cell extends Actor {
    private Candy candy;
    private int size;
    private GreenfootImage img;
    private final static Color selected = new Color(250,250,250), outline = Color.BLACK, normal = new Color(180,180,180);
    public Cell(){
        size = FINAL.CELL_SIZE;
        img = new GreenfootImage(size, size);
        draw(normal);
    }
    
    public void draw(Color c) {
        img.setColor(c);
        img.fill();
        img.setColor(outline);
        img.drawRect(0,0,size,size);
        setImage(img);
    }
    
    public void act() {
        if (Candy.getSelected() != null && Candy.getSelected().equals(candy))
            draw(selected);
        else draw(normal);
    }
    
    public void setCandy(Candy c){
        candy = c;
        candy.setLocation(getX(), getY());
        candy.setOrigin(new Pair(getX(), getY()));
    }
    
    public Candy getCandy(){
        return candy;
    }
    
    public boolean comp(Cell c) {
        return candy.comp(c.getCandy());
    }
}
