import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cell extends Actor
{
    private Candy candy;
    private int size;
    private GreenfootImage img;
    public Cell(){
        size = FINAL.CELL_SIZE;
        img = new GreenfootImage(size, size);
        img.setColor(new Color(150,150,150));
        img.fill();
        img.setTransparency(100);
        img.setColor(new Color(20,20,20));
        img.drawRect(0,0,size,size);
        setImage(img);
    }
    
    public void setCandy(Candy c){
        candy = c;
        candy.setLocation(getX(), getY());
    }
    
    public Candy getCandy(){
        return candy;
    }
    
    public boolean comp(Cell c) {
        return candy.comp(c.getCandy());
    }
}
