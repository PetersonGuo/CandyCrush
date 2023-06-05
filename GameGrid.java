import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameGrid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameGrid extends Actor
{
    private Cell[][] grid;
    public GameGrid(int width, int height){
        grid = new Cell[height][width];
        GreenfootImage img = new GreenfootImage(1,1);
        img.setTransparency(0);
        setImage(img);
    }
    
    public void addedToWorld(World w){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Cell(null);
                int shiftAmountX = (getWorld().getWidth())/2 - (grid[i].length * Cell.getSize())/2 + Cell.getSize()/2; 
                int shiftAmountY = (getWorld().getHeight())/2 - (grid.length * Cell.getSize())/2 + Cell.getSize()/2; 
                getWorld().addObject(grid[i][j], Cell.getSize()*i + shiftAmountX, Cell.getSize()*j + shiftAmountY);
            }
        }
    }

    
    /**
     * Act - do whatever the GameGrid wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    public void addCandies(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                Candy c = new Regular((int)Math.random()*4);
                grid[i][j].setCandy(c);
                getWorld().addObject(c, grid[i][j].getX(), grid[i][j].getY());
            }
        }
    }
}
