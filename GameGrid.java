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
    private ArrayList<Pair> matchedCandies;
    public GameGrid(int width, int height){
        this.width = width;
        this.height = height;
        candies = new Candy[height][width];
        cells = new Cell[height][width];
        matchedCandies = new ArrayList<Pair>();
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
                w.addObject(cells[i][j], FINAL.CELL_SIZE*j + shiftAmountX, FINAL.CELL_SIZE*i + shiftAmountY);
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
    
    private void addCandy(int i, int j){
        candies[i][j] = new Regular((int)(Math.random()*6));
        cells[i][j].setCandy(candies[i][j]);
        getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());        
    }
    
    public void addCandies(){
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                addCandy(i,j);
            }
        }
        printArray();
        
        removeMatching();
    }
    
    public void removeMatching(){
        while(checkMatching()){
            for(Pair p : matchedCandies){
                getWorld().removeObject(candies[p.x][p.y]);
                candies[p.x][p.y] = null;
            }
            matchedCandies.clear();
            drop();
            printArray();
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
    private boolean checkMatching() {return checkMatching(0, candies[0].length-1, candies.length-1);}
    
    /**
     * Check through the candies to destroy any currently matching candies
     * Smart algorithm to only destroy all matching within a bound
     */
    private boolean checkMatching(int lowX, int highX, int y) {
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
            int y = j+1;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) {
                c++;
                y++;
            }
            y = j-1;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) {
                c++;
                y--;
            }            
        } else {
            int x = i+1;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) {
                c++;
                x++;
            }
            x = i-1;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) {
                c++;
                x--;
            }            
        }
        if(c > 2) {
            Pair p = getGridCoor(candies[i][j]);
            matchedCandies.add(p);
        }
        return c;
    }
    
    /**
     * Check if given x and y coordinates are within bounds of the candies
     */
    private boolean validCoor(int i, int j) {
        return i >= 0 && i < candies.length && j >= 0 && j < candies[i].length;
    }
    
    public void printArray(){
        for(int i = 0; i < candies.length; i++){
            for(int j = 0; j < candies[i].length; j++){
                System.out.print(candies[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------");
    }
    
    /**
     * Check if user made a valid swap and swap back if not valid
     */
    public void validSwap(Pair a, Pair b) {
        swap(a, b);
        if (checkMatching(Math.min(a.x, b.x), Math.max(a.x, b.y), Math.max(a.y, b.y))){
            swapGraphics(a,b);
            removeMatching();
            return;
        }
        swap(a, b);
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
            addCandy(row, col);
        }else{
            swap(new Pair(row, col), new Pair(row-1, col));
            swapGraphics(new Pair(row, col), new Pair(row-1, col));
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
    
    private void swapGraphics(Pair a, Pair b){
        if(candies[a.x][a.y] != null){
            cells[a.x][a.y].setCandy(candies[a.x][a.y]);
        }
        if(candies[b.x][b.y] != null){
            cells[b.x][b.y].setCandy(candies[b.x][b.y]);
            //candies[b.x][b.y].setLocation(cells[b.x][b.y].getX(), cells[b.x][b.y].getY());
        }
    }

    //getters
    public Candy[] getRow(int row){
        return candies[row];
    }
    
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
    
    public Pair getGridCoor(Candy c) {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j].equals(c))
                    return new Pair(i, j);
        return new Pair(-1,-1);
    }
}