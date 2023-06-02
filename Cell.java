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
    private static int size;
    private GreenfootImage img;
    public Cell(Candy c){
        candy = c;
        size = 30;
        img = new GreenfootImage(size, size);
        img.setColor(new Color(100,100,100));
        img.fill();
        img.setTransparency(100);
        img.setColor(Color.BLACK);
        img.drawRect(0,0,size,size);
        setImage(img);
    }
    
    /**
     * Act - do whatever the Cell wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    public static int getSize(){
        return size;
    }
    
    public void setCandy(Candy c){
        candy = c;
    }
}
