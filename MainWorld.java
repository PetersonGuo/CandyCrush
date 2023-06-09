import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainWorld extends World {
    private static GameGrid grid;
    private static Candy clicked;
    /**
     * Constructor for objects of class MyWorld.
     */
    public MainWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 
        grid = new GameGrid(10,10);
        addObject(grid, 400, 400);
        grid.addCandies();
        
        clicked = null;
    }
    
    public void act() {
        
    }
    
    public static void setClicked(Candy c) {
        if (clicked != null) {
            if ((Math.abs(c.getX() - clicked.getX()) <= FINAL.CELL_SIZE && c.getX() != clicked.getX()) || (Math.abs(c.getY() - clicked.getY()) <= FINAL.CELL_SIZE && c.getY() != clicked.getY())) {
                grid.validSwap(grid.getGridCoor(c), grid.getGridCoor(clicked));
                clicked = null;
            } else clicked = c;
        } else clicked = c;
    }
}
