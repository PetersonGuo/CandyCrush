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
    private Map<Integer, Triple<Integer, Integer, Boolean>> matchedCandies;
    private Set<Triple<Candy, Integer, Integer>> shift;
    private enum Specials {
        Wrapped, Striped, ColourBomb;
    }
    
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;
        candies = new Candy[height][width];
        cells = new Cell[height][width];
        matchedCandies = new HashMap<>();
        shift = new HashSet<>();
        GreenfootImage img = new GreenfootImage(1,1);
        img.setTransparency(0);
        setImage(img);
    }
    
    public void addedToWorld(World w) {
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++) {
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
    public void act() {
        // Add your action code here.
    }
    
    private void addCandy(int i, int j) { //basic candy
        candies[i][j] = new Regular(Colour.random());
        cells[i][j].setCandy(candies[i][j]);
        getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());
    }
    
    private void addCandy(int i, int j, Specials type, Colour c, boolean vertical) {
        switch (type) {
            case ColourBomb:
                candies[i][j] = new ColourBomb();
                break;
            case Striped:
                candies[i][j] = new Striped(c, vertical);
                break;
            case Wrapped:
                candies[i][j] = new Wrapped(c);
                break;
            default:
                candies[i][j] = new Striped(c, vertical);
                break;
        }
        cells[i][j].setCandy(candies[i][j]);
        getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());        
    }
    
    public void addCandies() {
        for(int i = 0; i < candies.length; i++)
            for(int j = 0; j < candies[i].length; j++)
                addCandy(i, j);
        removeMatching();
    }
    
    public void removeMatching() {
        while(checkMatching()) {
            for(Map.Entry entry : matchedCandies.entrySet()) {
                Triple<Integer, Integer, Boolean> t = (Triple) entry.getValue();
                Colour col = null;
                boolean wrapper = false;
                Pair<Integer, Integer> wrappedLocation = new Pair(-1,-1);
                if(t.z){ //vertical
                    int x = (Integer)entry.getKey();
                    int y1 = t.x;
                    int y2 = t.y;
                    
                    for(int i = y1; i <= y2; i++){
                        //System.out.println(i + "," + x);
                        //System.out.println("Horizontal length: " + match(i, x, t.z));
                        if(match(i, x, t.z) > 2){
                            col = candies[i][x].getColour();
                            wrappedLocation = new Pair(i,x);
                            wrapper = true;
                        }
                    }
                } else { //horizontal
                    int y = (Integer)entry.getKey();
                    int x1 = t.x;
                    int x2 = t.y;

                    for(int i = x1; i <= x2; i++){
                        //System.out.println(y + "," + i);
                        //System.out.println("Vertical length: " + match(y, i, t.z));
                        if(match(y, i, t.z) > 2){
                            col = candies[y][i].getColour();
                            wrappedLocation = new Pair(y,i);
                            wrapper = true;
                        }
                    }
                }
                //System.out.println(wrapper);
                
                for (int i = t.x; i <= t.y; i++) {
                    if (t.z)  {
                        if (candies[i][(Integer)entry.getKey()] != null) {
                            col = candies[i][(Integer)entry.getKey()].getColour();
                            candies[i][(Integer)entry.getKey()].useAbility();
                            getWorld().removeObject(candies[i][(Integer)entry.getKey()]);
                            candies[i][(Integer)entry.getKey()] = null;
                        }
                    } else {
                        if (candies[(Integer)entry.getKey()][i] != null) {
                            col = candies[(Integer)entry.getKey()][i].getColour();
                            candies[(Integer)entry.getKey()][i].useAbility();
                            getWorld().removeObject(candies[(Integer)entry.getKey()][i]);
                            candies[(Integer)entry.getKey()][i] = null;
                        }
                    }
                }
                if (t.y-t.x >= 4)
                    if (t.z) addCandy(t.x, (Integer) entry.getKey(), Specials.ColourBomb, col, false);
                    else addCandy((Integer) entry.getKey(), t.x, Specials.ColourBomb, col, false);
                else if(wrapper)
                    addCandy((int)(wrappedLocation.x), (int)(wrappedLocation.y), Specials.Wrapped, col, false);
                else if(t.y-t.x >= 3) 
                    if (t.z) addCandy(t.x, (Integer) entry.getKey(), Specials.Striped, col, !t.z);
                    else addCandy((Integer) entry.getKey(), t.x, Specials.Striped, col, !t.z);
                drop();
            }
            matchedCandies.clear();
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
                    if (validCoor(i+dir[k], j) && candies[i][j].comp(candies[i+dir[k]][j]))
                        if ((validCoor(i+dir[1-k], j-1) && candies[i][j].comp(candies[i+dir[1-k]][j-1])) ||
                            (validCoor(i+dir[1-k], j+1) && candies[i][j].comp(candies[i+dir[1-k]][j+1]))) return true;
                    if (validCoor(i, j+dir[k]) && candies[i][j].comp(candies[i][j+dir[k]]))
                        if ((validCoor(i-1, j+dir[1-k]) && candies[i][j].comp(candies[i-1][j+dir[1-k]])) ||
                            (validCoor(i+1, j+dir[1-k]) && candies[i][j].comp(candies[i+1][j+dir[1-k]]))) return true;
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
                if (match(i, j, false) > 2)
                    matched = true;
                if (match(i, j, true) > 2)
                    matched = true;
            }
        }
        return matched;
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
        Pair<Integer, Integer> p = new Pair(-1, -1);
        int c = 0;
        if(candies[i][j] != null){
            if (dir) {
                int y = j+1;
                while (validCoor(i, y) && candies[i][j].comp(candies[i][y]))
                    y++;
                p.y = y-1;
                y = j-1;
                while (validCoor(i, y) && candies[i][j].comp(candies[i][y]))
                    y--;
                p.x = y+1;
                if (p.y-p.x >= 2) matchedCandies.put(i, new Triple(p.x, p.y, false));
            } else {
                int x = i+1;
                while (validCoor(x, j) && candies[i][j].comp(candies[x][j]))
                    x++;
                p.y = x-1;
                x = i-1;
                while (validCoor(x, j) && candies[i][j].comp(candies[x][j]))
                    x--;
                p.x = x+1;
                if (p.y-p.x >= 2) matchedCandies.put(j, new Triple(p.x, p.y, true));
            }
        }
        return p.y-p.x+1;
    }
    
    /**
     * Check if given x and y coordinates are within bounds of the candies
     */
    private boolean validCoor(int i, int j) {return i >= 0 && i < candies.length && j >= 0 && j < candies[i].length;}
    
    public void printArray() {
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++){
                //System.out.print(i + "," + j);
                System.out.print(candies[i][j] + "\t");}
            System.out.println();
        }
        System.out.println("---------------------------------------------------");
    }
    
    /**
     * Check if user made a valid swap and swap back if not valid
     */
    public boolean validSwap(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        swap(a, b);
        boolean hasColourBomb;
        Pair colourBomb = null;
        Pair candy = null;
        if(getCandy(a) instanceof ColourBomb){
            hasColourBomb = true;
            colourBomb = a;
            candy = b;
        } else if (getCandy(b) instanceof ColourBomb){
            hasColourBomb = true;
            colourBomb = b;
            candy = a;
        } else { 
            hasColourBomb = false;
        } 
        if (checkMatching(Math.min(a.x, b.x), Math.max(a.x, b.y), Math.max(a.y, b.y)) || hasColourBomb) {
            swapGraphics(a,b);
            if(hasColourBomb){
                ((ColourBomb)getCandy(colourBomb)).usePower(getCandy(candy).getColour());
                getWorld().removeObject(getCandy(colourBomb));
                getWorld().removeObject(getCandy(candy));
                candies[(int)colourBomb.x][(int)colourBomb.y] = null;
                candies[(int)candy.x][(int)candy.y] = null;
                drop();
            }
            removeMatching();
            return true;
        }
        swap(a, b);
        return false;
    }
    
    public void drop() {
        Pair<Integer, Integer> candyNullCoor = checkNullCandy();
        while(candyNullCoor != null) {
            moveDown(candyNullCoor.x, candyNullCoor.y);
            candyNullCoor = checkNullCandy();
        }
    }
    
    private Pair<Integer, Integer> checkNullCandy() {
        for(int i = 0; i < candies.length; i++)
            for(int j = 0; j < candies[i].length; j++)
                if(candies[i][j] == null)
                    return new Pair(i,j);
        return null;
    }
    
    private void moveDown(int row, int col) {
        if(row == 0)
            addCandy(row, col);
        else {
            swap(new Pair(row, col), new Pair(row-1, col));
            swapGraphics(new Pair(row, col), new Pair(row-1, col));
        }
    }
    
    /**
     * Helper function
     */
    private void swap(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        Candy temp = candies[a.x][a.y];
        candies[a.x][a.y] = candies[b.x][b.y];
        candies[b.x][b.y] = temp;
    }
    
    private void swapGraphics(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        if(candies[a.x][a.y] != null)
            cells[a.x][a.y].setCandy(candies[a.x][a.y]);
        if(candies[b.x][b.y] != null)
            cells[b.x][b.y].setCandy(candies[b.x][b.y]);
    }

    //getters
    public void clearRow(Candy c) {
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c)) {
                    clearRow(i);
                    return;
                }
        }
    }
    
    public void clearRow(int row) {
        for (int i = 0; i < candies[row].length; i++) {
            getWorld().removeObject(candies[row][i]);
            candies[row][i] = null;
            drop();
        }
        removeMatching();
    }
    
    public void clearCol(Candy c) {
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c)) {
                    clearCol(j);
                    return;
                }
        }
    }
    
    public void clearCol(int col) {
        for (int i = 0; i < candies.length; i++) {
            getWorld().removeObject(candies[i][col]);
            candies[i][col] = null;
            drop();
        }
        removeMatching();
    }
    
    public void clearColour(Colour c){
        ArrayList<Candy> colouredCandies = new ArrayList<Candy>();
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++) {
                if (candies[i][j] != null && candies[i][j].getColour() == c) {
                    colouredCandies.add(candies[i][j]);
                    
                    getWorld().removeObject(candies[i][j]);
                    candies[i][j] = null;
                    drop();
                }
            }
        }
        removeMatching();
    }
    
    public Candy[] getRow(int row) {return candies[row];}
    
    public Candy[] getExploGrid(Candy c) {
        Pair<Integer, Integer> p = getGridCoor(c);
        int x = p.x, y = p.y;
        // for(int i = 0; i < candies.length; i++) {
            // for(int j = 0; j < candies[i].length; j++) {
                // if(candies[i][j].equals(c)) {
                    // x = i;
                    // y = j;
                // }
            // }
        // }   
        Candy[] arr = new Candy[9];
        int arrIndex = 0;
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if (validCoor(i, j)) {
                    arr[arrIndex] = candies[i][j];
                    arrIndex++;
                }
            }
        }  
        return arr;
    }
    
    public Candy[] getCandies() {
        Candy[] arr = new Candy[width * height];
        int index = 0;
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++) {
                arr[index] = candies[i][j];
                index++;
            }
        }
        return arr;
    }        
    
    public Pair<Integer, Integer> getGridCoor(Candy c) {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j].equals(c))
                    return new Pair(i, j);
        return new Pair(-1,-1);
    }
    
    public Candy getCandy(Pair p){
        return candies[(int)p.x][(int)p.y];
    }
}
