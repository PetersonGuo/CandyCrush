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
                int shiftAmountX = (w.getWidth())/2 - (grid[i].length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                int shiftAmountY = (w.getHeight())/2 - (grid.length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                getWorld().addObject(grid[i][j], FINAL.CELL_SIZE*i + shiftAmountX, FINAL.CELL_SIZE*j + shiftAmountY);
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
                Candy c = new Regular((int)(Math.random()*6));
                grid[i][j].setCandy(c);
                getWorld().addObject(c, grid[i][j].getX(), grid[i][j].getY());
            }
        }
    }
    
    public Cell[] getRow(Cell c){
        int rowNum = -1;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j].equals(c)) rowNum = i;
            }
        }
        return grid[rowNum];
    }
    
    public Cell[] getColumn(Cell c){
        int columnNum = -1;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j].equals(c)) columnNum = j;
            }
        }
        Cell[] arr = new Cell[grid.length];
        for(int i = 0; i < grid.length; i++){
            arr[i] = grid[i][columnNum];
        }
        return arr;
    }
    
    public Cell[] getExploGrid(Cell c){
        int x = -1, y = -1;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j].equals(c)){
                    x = i;
                    y = j;
                }
            }
        }   
        Cell[] arr = new Cell[9];
        int arrIndex = 0;
        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j <= y+1; j++){
                try{
                    arr[arrIndex] = grid[i][j];
                    arrIndex++;
                }catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }
        }  
        return arr;
    }
}
