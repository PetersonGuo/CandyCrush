import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Gamecandies here.
 * For efficiency only check x dir and upwards y direction of box of lowest changed candy
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameGrid extends Actor {
    private Candy[][] candies;
    private Cell[][] cells;
    private int width, height;
    public GameGrid(int width, int height){
        this.width = width;
        this.height = height;
        candies = new Candy[height][width];
        cells = new Cell[height][width];
        GreenfootImage img = new GreenfootImage(1,1);
        img.setTransparency(0);
        setImage(img);
    }
    
    public void addedToWorld(World w){
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                cells[i][j] = new Cell();
                int shiftAmountX = (w.getWidth())/2 - (candies[i].length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                int shiftAmountY = (w.getHeight())/2 - (candies.length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                w.addObject(cells[i][j], FINAL.CELL_SIZE*i + shiftAmountX, FINAL.CELL_SIZE*j + shiftAmountY);
            }
        }
    }

    /**
     * Act - do whatever the Gamecandies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    public void addCandies(){
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                candies[i][j] = new Regular((int)(Math.random()*6));
                getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());
            }
        }
    }
        
    /**
     * Check the candies to see if there are any possible matches
     * Assumes all matches are already destroyed
     */
    private boolean checkCandies() {
        // For every 3x3 box, check if centre candy has another candy of the same type in the x dir or y dir then check corners of opposite side
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++) {
                final int dir[] = {-1, 1};
                for (int k = 0; k < 2; k++) {
                    if (validCoor(i+dir[k], j) && candies[i][j].comp(candies[i+dir[k]][j])) {
                        if ((validCoor(i+dir[1-k], j-1) && candies[i][j].comp(candies[i+dir[1-k]][j-1])) ||
                            (validCoor(i+dir[1-k], j+1) && candies[i][j].comp(candies[i+dir[1-k]][j+1]))) return true;
                    }
                }
                for (int k = 0; k < 2; k++) {
                    if (validCoor(i, j+dir[k]) && candies[i][j].comp(candies[i][j+dir[k]])) {
                        if ((validCoor(i-1, j+dir[1-k]) && candies[i][j].comp(candies[i-1][j+dir[1-k]])) ||
                            (validCoor(i+1, j+dir[1-k]) && candies[i][j].comp(candies[i+1][j+dir[1-k]]))) return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Check through the candies to destroy any currently matching candies
     * Dumb algorithm to iterate over everything
     */
    private boolean destroyMatching() {return destroyMatching(0, candies[0].length-1, candies.length-1);}
    
    /**
     * Check through the candies to destroy any currently matching candies
     * Smart algorithm to only destroy all matching within a bound
     */
    private boolean destroyMatching(int lowX, int highX, int y) {
        boolean matched = false;
        for (int j = y; j >= 0; j--) {
            for (int i = lowX; i <= highX; i++) {
                if (match(i, j, false) > 2) {
                    matched = true;
                    // Find first and last x index and shift everything above down
                } else if (match(i, j, true) > 2) {
                    matched = true;
                    // Find lowest and shift everything above down
                }
            }
        }
        return matched;
    }

    private int floodfill() {
        return 0;
    }
    
    /**
     * Check length of matching candies in a specified direction
     * 
     * @param i The x coordinate of the candies to start at
     * @param j The y coordinate of the candies to start at
     * @param dir The direction to check, 0 is the x axis, 1 is the y axis
     * 
     * @return int The length of the chain of matching candies
     */
    private int match(int i, int j, boolean dir) {
        int c = 1;
        if (dir) {
            int y = j;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) {
                c++;
                y++;
            }
            y = j;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) {
                c++;
                y--;
            }
        } else {
            int x = i;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) {
                c++;
                x++;
            }
            x = i;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) {
                c++;
                x--;
            }
        }
        return c;
    }
    
    /**
     * Check if given x and y coordinates are within bounds of the candies
     */
    private boolean validCoor(int i, int j) {
        return i >= 0 && i < candies.length && j >= 0 && j < candies[i].length;
    }
    
    /**
     * Check if user made a valid swap and swap back if not valid
     */
    public void validSwap(Pair a, Pair b) {
        swap(a, b);
        if (destroyMatching(Math.min(a.x, b.x), Math.max(a.x, b.y), Math.max(a.y, b.y)))
            return;
        swap(a, b);
    }
    
    public Pair getGridCoor(Candy c) {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j].equals(c))
                    return new Pair(i, j);
        return new Pair(-1,-1);
    }
    
    public void drop(){
        Pair candyNullCoor = checkNullCandy();
        while(candyNullCoor != null){
            moveDown(candyNullCoor.x, candyNullCoor.y);
            candyNullCoor = checkNullCandy();
        }
    }
    
    private Pair checkNullCandy(){
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                if(candies[i][j] == null){
                    return new Pair(i,j);
                }
            }
        }
        return null;        
    }
    
    private void moveDown(int row, int col){
        if(row == 0){
            candies[row][col] = new Regular((int)(Math.random()*6));
        }else{
            swap(new Pair(row, col), new Pair(row-1, col));
        }        
    }
    
    /**
     * Helper function
     */
    private void swap(Pair a, Pair b) {
        Candy temp = candies[a.x][a.y];
        candies[a.x][a.y] = candies[b.x][b.y];
        candies[b.x][b.y] = temp;
    }

    //getters
    public Candy[] getRow(Candy c){
        int rowNum = -1;
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                if(candies[i][j].equals(c)) rowNum = i;
            }
        }
        return candies[rowNum];
    }
    
    public Candy[] getColumn(Candy c){
        int columnNum = -1;
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                if(candies[i][j].equals(c)) columnNum = j;
            }
        }
        Candy[] arr = new Candy[candies.length];
        for(int i = 0; i < candies.length; i++){
            arr[i] = candies[i][columnNum];
        }
        return arr;
    }
    
    public Candy[] getExploGrid(Candy c){
        int x = -1, y = -1;
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                if(candies[i][j].equals(c)){
                    x = i;
                    y = j;
                }
            }
        }   
        Candy[] arr = new Candy[9];
        int arrIndex = 0;
        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j <= y+1; j++){
                if (validCoor(i, j)) {
                    arr[arrIndex] = candies[i][j];
                    arrIndex++;
                }
            }
        }  
        return arr;
    }
    
    public Candy[] getCandies(){
        Candy[] arr = new Candy[width * height];
        int index = 0;
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                arr[index] = candies[i][j];
                index++;
            }
        }
        return arr;
    }        
}