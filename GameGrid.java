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
    private boolean init;
    private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> swap;
    private Set<Triple<Integer, Integer, Colour>> wraps;
    private Set<Triple<Integer, Integer, Integer>> horizontal, vertical;
    private Sound match, colourBomb, stripe, wrapper, background;
    
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;
        candies = new Candy[height][width];
        cells = new Cell[height][width];
        init = true;
        horizontal = new HashSet<>();
        vertical = new HashSet<>();
        wraps = new HashSet<>();
        GreenfootImage img = new GreenfootImage(1,1);
        img.setTransparency(0);
        setImage(img);
    }
    
    public void addedToWorld(World w) {
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++) {
                cells[i][j] = new Cell();
                int shiftAmountX = (w.getWidth())/2 - (candies[i].length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                int shiftAmountY = (w.getHeight())/2 - (candies.length * FINAL.CELL_SIZE)/2 + FINAL.CELL_SIZE/2; 
                w.addObject(cells[i][j], FINAL.CELL_SIZE*j + shiftAmountX, FINAL.CELL_SIZE*i + shiftAmountY);
            }
        }
        background = new Sound("background.mp3");
        background.loop();
    }

    public void stopped(){
        background.stop();
    }
    
    /**
     * Act - do whatever the Gamecandies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (!checkCandies())
            reshuffle();
        //getWorld().removeObjects(getWorld().getObjects(Candy.class));
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if(candies[i][j].getIntersectingCandy() != null && candies[i][j].atOrigin() && candies[i][j].getIntersectingCandy().atOrigin()) getWorld().removeObject(candies[i][j].getIntersectingCandy());
                //getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());
    }
    
    private void addCandy(int i, int j) { //basic candy
        candies[i][j] = new Regular(Colour.random());
        if(!init) getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY()-50);
        cells[i][j].setCandy(candies[i][j]);
    }
    
    private void addCandy(int i, int j, Specials type, Colour c, boolean vertical) {
        switch (type) {
            case ColourBomb:
                colourBomb = new Sound("colourbomb.mp3");
                if(!init) colourBomb.play();
                candies[i][j] = new ColourBomb();
                break;
            case Striped:
                stripe = new Sound("stripe.mp3");
                if(!init) stripe.play();
                candies[i][j] = new Striped(c, vertical);
                break;
            case Wrapped:
                wrapper = new Sound("wrapper.mp3");
                if(!init) wrapper.play();
                candies[i][j] = new Wrapped(c);
                break;
            default:
                stripe = new Sound("stripe.mp3");
                if(!init) stripe.play();
                candies[i][j] = new Striped(c, vertical);
                break;
        }
        if(!init) getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY()-50);        
        cells[i][j].setCandy(candies[i][j]);
    }
    
    public void addCandies() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                addCandy(i, j);
        removeMatching();
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++){
                if(candies[i][j] instanceof Special && !FINAL.CHEAT){
                    if(candies[i][j].getColour() != null){
                        candies[i][j] = new Regular(candies[i][j].getColour());
                        cells[i][j].setCandy(candies[i][j]);
                    }else{
                        ArrayList<Colour> colours = new ArrayList<Colour>();
                        for(Colour c : Colour.getColours())
                            colours.add(c);
                        if(validCoor(i,j+1)) colours.remove(candies[i][j+1].getColour());
                        if(validCoor(i,j-1)) colours.remove(candies[i][j-1].getColour());
                        if(validCoor(i+1,j)) colours.remove(candies[i+1][j].getColour());
                        if(validCoor(i-1,j)) colours.remove(candies[i-1][j].getColour());
                        candies[i][j] = new Regular(colours.get((int)(Math.random() * colours.size())));
                        cells[i][j].setCandy(candies[i][j]);
                    }
                }
                getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY());
            }
        init = false;
    }
    
    public void removeMatching() {
        while (checkMatching()) {
            for (Triple<Integer, Integer, Integer> entry : horizontal) {
                Colour col = null;
                for (int i = entry.y; i <= entry.z; i++) {
                    if (candies[entry.x][i] != null) {
                        col = candies[entry.x][i].getColour();
                        candies[entry.x][i].useAbility();
                        removeFromWorld(new Pair(entry.x, i));
                    }
                }
                if (swap != null && swap.y.x == entry.x && entry.y <= swap.y.y && swap.y.y <= entry.z) {
                    if (entry.z-entry.y >= 4)
                        addCandy(swap.y.x, swap.y.y, Specials.ColourBomb, col, false);
                    else if (entry.z-entry.y >= 3)
                        addCandy(swap.y.x, swap.y.y, Specials.Striped, col, true);
                    swap = null;
                } else if (swap != null && swap.x.x == entry.x && entry.y <= swap.x.y && swap.x.y <= entry.z) {
                    if (entry.z-entry.y >= 4)
                        addCandy(swap.x.x, swap.x.y, Specials.ColourBomb, col, false);
                    else if (entry.z-entry.y >= 3)
                        addCandy(swap.x.x, swap.x.y, Specials.Striped, col, true);
                    swap = null;
                } else if (entry.z-entry.y >= 4)
                    addCandy(entry.x, entry.y, Specials.ColourBomb, col, false);
                else if (entry.z-entry.y >= 3)
                    addCandy(entry.x, entry.y, Specials.Striped, col, true);
                match = new Sound("match" + (int)(Math.random() * 4 + 1) + ".mp3");
                if(!init) match.play();
            }
            horizontal.clear();
            for (Triple<Integer, Integer, Integer> entry : vertical) {
                Colour col = null;
                for (int i = entry.y; i <= entry.z; i++) {
                    if (candies[i][entry.x] != null) {
                        col = candies[i][entry.x].getColour();
                        candies[i][entry.x].useAbility();
                        removeFromWorld(new Pair(i, entry.x));
                    }
                }
                if (swap != null && entry.y <= swap.y.x && swap.y.x <= entry.z && swap.y.y == entry.x) {
                    if (entry.z-entry.y >= 4)
                        addCandy(swap.y.x, swap.y.y, Specials.ColourBomb, col, false);
                    else if (entry.z-entry.y >= 3)
                        addCandy(swap.y.x, swap.y.y, Specials.Striped, col, true);
                    swap = null;
                } else if (swap != null && entry.y <= swap.x.x && swap.x.x <= entry.z && swap.x.y == entry.x) {
                    if (entry.z-entry.y >= 4)
                        addCandy(swap.y.x, swap.y.y, Specials.ColourBomb, col, false);
                    else if (entry.z-entry.y >= 3)
                        addCandy(swap.y.x, swap.y.y, Specials.Striped, col, true);
                    swap = null;
                } else if (entry.z-entry.y >= 4)
                    addCandy(entry.y, entry.x, Specials.ColourBomb, col, false);
                else if (entry.z-entry.y >= 3)
                    addCandy(entry.y, entry.x, Specials.Striped, col, true);
                match = new Sound("match" + (int)(Math.random() * 4 + 1) + ".mp3");
                if(!init) match.play();
            }
            vertical.clear();
            for (Triple<Integer, Integer, Colour> t : wraps)
                addCandy(t.x, t.y, Specials.Wrapped, t.z, false);
            wraps.clear();
            drop();
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
    
    private void reshuffle() {
        List<Candy> list = new ArrayList<>(Arrays.asList(getCandies()));
        Collections.shuffle(list);
        int counter = 0;
        for (Candy i : list)
            candies[counter/candies.length][counter%candies.length] = i;
        removeMatching();
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
        boolean ver = false, hor = false;
        for (int j = y; j >= 0; j--) {
            for (int i = lowX; i <= highX; i++) {
                Triple<Integer, Integer, Integer> vertLen = match(i, j, false), horLen = match(i, j, true);
                if (horLen.z - horLen.y >= 4) {
                    horizontal.add(horLen);
                    hor = true;
                } else if (vertLen.z - vertLen.y >= 4) {
                    vertical.add(vertLen);
                    ver = true;
                } else if (horLen.z - horLen.y >= 2 && vertLen.z - vertLen.y >= 2) wraps.add(new Triple(i, j, candies[i][j].getColour()));
                else if (horLen.z - horLen.y >= 2) {
                    horizontal.add(horLen);
                    hor = true;
                } else if (vertLen.z - vertLen.y >= 2) {
                    vertical.add(vertLen);
                    ver = true;
                }
            }
        }
        return ver || hor;
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
    private Triple<Integer, Integer, Integer> match(int i, int j, boolean dir) {
        Triple<Integer, Integer, Integer> t = new Triple(-1, -1, -1);
        if (dir) {
            int y = j+1;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) y++;
            t.z = y-1;
            y = j-1;
            while (validCoor(i, y) && candies[i][j].comp(candies[i][y])) y--;
            t.y = y+1;
            t.x = i;
        } else {
            int x = i+1;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) x++;
            t.z = x-1;
            x = i-1;
            while (validCoor(x, j) && candies[i][j].comp(candies[x][j])) x--;
            t.y = x+1;
            t.x = j;
        }
        return t;
    }
    
    /**
     * Check if given x and y coordinates are within bounds of the candies
     */
    private boolean validCoor(int i, int j) {return i >= 0 && i < candies.length && j >= 0 && j < candies[i].length;}
    
    /**
     * Check if user made a valid swap and swap back if not valid
     */
    public boolean validSwap(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        swap = new Pair(a, b);
        swap(a, b);
        if (candies[a.x][a.y].getType() == Specials.ColourBomb && candies[b.x][b.y].getType() == Specials.ColourBomb) {
            clearAll();
            removeMatching();
            return true;
        } else if (candies[a.x][a.y].getType() == Specials.ColourBomb) {
            clearColour(candies[b.x][b.y].getColour());
            removeFromWorld(a);
            drop();
            removeMatching();
            return true;
        } else if (candies[b.x][b.y].getType() == Specials.ColourBomb) {
            clearColour(candies[a.x][a.y].getColour());
            removeFromWorld(b);
            drop();
            removeMatching();
            return true;
        } else if (checkMatching(Math.min(a.x, b.x), Math.max(a.x, b.y), Math.max(a.y, b.y))) {
            swapGraphics(a,b);
            removeMatching();
            return true;
        }
        swap(a, b);
        return false;
    }
    
    private void removeFromWorld(Pair<Integer, Integer> p) {
        getWorld().removeObject(candies[p.x][p.y]);
        if(!init)MainWorld.addPoints(100);
        candies[p.x][p.y] = null;
    }
    
    public void drop() {
        Pair<Integer, Integer> nullCoor = checkNullCandy();
        while (nullCoor != null) {
            if(nullCoor.x == 0)
                addCandy(nullCoor.x, nullCoor.y);
            else {
                swap(new Pair(nullCoor.x, nullCoor.y), new Pair(nullCoor.x-1, nullCoor.y));
                swapGraphics(new Pair(nullCoor.x, nullCoor.y), new Pair(nullCoor.x-1, nullCoor.y));
            }
            nullCoor = checkNullCandy();
        }
    }
    
    private Pair<Integer, Integer> checkNullCandy() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if(candies[i][j] == null)
                    return new Pair(i,j);
        return null;
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
    
    private void clearAll() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                removeFromWorld(new Pair(i, j));
        drop();
    }

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
            removeFromWorld(new Pair(row, i));
            drop();
        }
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
            removeFromWorld(new Pair(i, col));
            drop();
        }
    }
    
    public void clearColour(Colour c){
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++) {
                if (candies[i][j] != null && candies[i][j].getColour() == c) {
                    removeFromWorld(new Pair(i, j));
                    drop();
                }
            }
        }
    }
    
    public Candy[] getRow(int row) {
        return candies[row];
    }
    
    public void getExploGrid(Candy c) {
        Pair p = getGridCoor(c);   
        for (int i = (int)p.x - 1; i <= (int)p.x + 1; i++) {
            for (int j = (int)p.y - 1; j <= (int)p.y + 1; j++) {
                if (validCoor(i, j)) {
                    getWorld().removeObject(candies[i][j]);
                    candies[i][j] = null;
                    drop();
                }
            }
        }
    }
    
    public Candy[] getCandies() {
        Candy[] arr = new Candy[width * height];
        int index = 0;
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++) {
                arr[index] = candies[i][j];
                index++;
            }
        }
        return arr;
    }        
    
    public Pair<Integer, Integer> getGridCoor(Candy c) {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c))
                    return new Pair(i, j);
        return null;
    }
}
