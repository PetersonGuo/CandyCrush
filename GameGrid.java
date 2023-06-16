import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class GameGrid here.
 * For efficiency only check x dir and upwards y direction of box of lowest changed candy
 * 
 * @author Peterson Guo, Kelby To 
 * @version June 15, 2023
 */
public class GameGrid extends Actor {
    private Candy[][] candies;
    private Cell[][] cells;
    private int width, height;
    private boolean init, movingGraphics;
    private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> swap;
    private Set<Triple<Integer, Integer, Colour>> wraps;
    private Set<Triple<Integer, Integer, Integer>> horizontal, vertical;
    private Sound match, colourBomb, stripe, wrapper, background;
    private static Queue<ArrayList<Candy>> changes;
    
    /**
     * A constructor for the game grid that takes in the width and height of the grid.
     * @param width The width of the grid
     * @param height The height of the grid
     */
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;
        candies = new Candy[height][width];
        cells = new Cell[height][width];
        init = true;
        movingGraphics = false;
        horizontal = new HashSet<>();
        vertical = new HashSet<>();
        wraps = new HashSet<>();
        changes = new LinkedList<ArrayList<Candy>>();
        GreenfootImage img = new GreenfootImage(1,1);
        img.setTransparency(0);
        setImage(img);
    }
    
    @Override
    /**
     * A method run when the object is added to the world.
     * @param w The world that the object is added to
     */
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

    /**
     * A method that runs when the world is stopped
     */
    public void stopped(){
        background.stop();
    }
    
    @Override
    /**
     * Act - do whatever the Gamecandies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if(!Candy.isMoving() && checkMatching()){
            removeMatching();
            horizontal.clear();
            vertical.clear();
            wraps.clear();
        }
        if (!checkCandies())
            reshuffle();
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j].getIntersectingCandy() != null && candies[i][j].atOrigin() && candies[i][j].getIntersectingCandy().atOrigin())
                    getWorld().removeObject(candies[i][j].getIntersectingCandy());
    }
    
    /**
     * A method that adds a candy to the grid
     * @param i The row of the candy
     * @param j The column of the candy
     */
    private void addCandy(int i, int j) { //basic candy
        candies[i][j] = new Regular(Colour.random());
        if(!init) getWorld().addObject(candies[i][j], cells[i][j].getX(), cells[i][j].getY()-50);
        cells[i][j].setCandy(candies[i][j]);
    }
    
    /**
     * A method that adds a special candy to the grid
     * @param i The row of the candy
     * @param j The column of the candy
     * @param type The type of special candy
     * @param c The colour of the candy
     * @param vertical Whether the candy is vertical or not
     */
    private void addCandy(int i, int j, Specials type, Colour c, boolean vertical) {
        if(c == null) c = Colour.random();
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
    
    /**
     * A method that adds candies to the grid when the world begins
     */
    public void addCandies() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                addCandy(i, j);
        while(checkMatching())removeMatching();
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
    
    /**
     * A method that removes all matching candies from the grid
     */
    public void removeMatching() {
        for (Triple<Integer, Integer, Integer> entry : horizontal) {
            Colour col = Colour.random();
            for (int i = entry.y; i <= entry.z; i++) {
                if (candies[entry.x][i] != null) {
                    col = candies[entry.x][i].getColour();
                    candies[entry.x][i].useAbility();
                    removeFromWorld(new Pair<>(entry.x, i));
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
                    removeFromWorld(new Pair<>(i, entry.x));
                }
            }
            if (swap != null && entry.y <= swap.y.x && swap.y.x <= entry.z && swap.y.y == entry.x) {
                if (entry.z-entry.y >= 4)
                    addCandy(swap.y.x, swap.y.y, Specials.ColourBomb, col, false);
                else if (entry.z-entry.y >= 3)
                    addCandy(swap.y.x, swap.y.y, Specials.Striped, col, false);
                swap = null;
            } else if (swap != null && entry.y <= swap.x.x && swap.x.x <= entry.z && swap.x.y == entry.x) {
                if (entry.z-entry.y >= 4)
                    addCandy(swap.x.x, swap.x.y, Specials.ColourBomb, col, false);
                else if (entry.z-entry.y >= 3)
                    addCandy(swap.x.x, swap.x.y, Specials.Striped, col, false);
                swap = null;
            } else if (entry.z-entry.y >= 4)
                addCandy(entry.y, entry.x, Specials.ColourBomb, col, false);
            else if (entry.z-entry.y >= 3)
                addCandy(entry.y, entry.x, Specials.Striped, col, false);
            match = new Sound("match" + (int)(Math.random() * 4 + 1) + ".mp3");
            if(!init) match.play();
        }
        vertical.clear();
        for (Triple<Integer, Integer, Colour> t : wraps)
            addCandy(t.x, t.y, Specials.Wrapped, t.z, false);
        wraps.clear();
        drop();
    }
        
    /**
     * Check the candies to see if there are any possible matches
     * Assumes all matches are already destroyed
     * @return true if there are possible matches, false otherwise
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
     * Randomly reshuffle the candies
     */
    private void reshuffle() {
        List<Candy> list = new ArrayList<>(Arrays.asList(getCandies()));
        Collections.shuffle(list);
        int counter = 0;
        for (Candy i : list)
            candies[counter/candies.length][counter%candies.length] = i;
    }
    
    /**
     * Check through the candies to destroy any currently matching candies
     * Dumb algorithm to iterate over everything
     * @return true if there are any matches, false otherwise
     */
    private boolean checkMatching() {return checkMatching(0, candies[0].length-1, candies.length-1);}
    
    /**
     * Check through the candies to destroy any currently matching candies
     * Smart algorithm to only destroy all matching within a bound
     * @param lowX the lowest x value to check
     * @param highX the highest x value to check
     * @param y the highest y value to start from
     */
    private boolean checkMatching(int lowX, int highX, int y) {
        boolean ver = false, hor = false, wrap = false;
        for (int j = y; j >= 0; j--) {
            for (int i = lowX; i <= highX; i++) {
                Triple<Integer, Integer, Integer> vertLen = match(i, j, false), horLen = match(i, j, true);
                if (horLen.z - horLen.y >= 4) {
                    horizontal.add(horLen);
                    hor = true;
                } else if (vertLen.z - vertLen.y >= 4) {
                    vertical.add(vertLen);
                    ver = true;
                } else if (horLen.z - horLen.y >= 2 && vertLen.z - vertLen.y >= 2){
                    wraps.add(new Triple<>(i, j, candies[i][j].getColour()));
                    wrap = true;
                }else if (horLen.z - horLen.y >= 2) {
                    horizontal.add(horLen);
                    hor = true;
                } else if (vertLen.z - vertLen.y >= 2) {
                    vertical.add(vertLen);
                    ver = true;
                }
            }
        }
        return ver || hor || wrap;
    }
    
    /**
     * Check length of matching candies in a specified direction
     * 
     * @param i The x coordinate of the candies to start at
     * @param j The y coordinate of the candies to start at
     * @param dir The direction to check, 0 is the x axis, 1 is the y axis
     * 
     * @return Triple<Integer, Integer, Integer> A triple containing the start and end coordinates of the matching candies
     */
    private Triple<Integer, Integer, Integer> match(int i, int j, boolean dir) {
        Triple<Integer, Integer, Integer> t = new Triple<>(-1, -1, -1);
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
     * 
     * @param i The x coordinate to check
     * @param j The y coordinate to check
     * 
     * @return true if the coordinates are valid, false otherwise
     */
    private boolean validCoor(int i, int j) {return i >= 0 && i < candies.length && j >= 0 && j < candies[i].length;}
    
    /**
     * Check if user made a valid swap and swap back if not valid
     * 
     * @param a The first candy to swap
     * @param b The second candy to swap
     * 
     * @return true if the swap was valid, false otherwise
     */
    public boolean validSwap(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        swap = new Pair<>(a, b);
        swap(a, b);
        if (candies[a.x][a.y].getType() == Specials.ColourBomb && candies[b.x][b.y].getType() == Specials.ColourBomb) {
            clearAll();
            return true;
        } else if (candies[a.x][a.y].getType() == Specials.ColourBomb) {
            clearColour(candies[b.x][b.y].getColour());
            removeFromWorld(a);
            drop();
            return true;
        } else if (candies[b.x][b.y].getType() == Specials.ColourBomb) {
            clearColour(candies[a.x][a.y].getColour());
            removeFromWorld(b);
            drop();
            return true;
        } else if (checkMatching(Math.min(a.x, b.x), Math.max(a.x, b.y), Math.max(a.y, b.y))) {
            swapGraphics(a,b);
            return true;
        }
        swap(a, b);
        return false;
    }
    
    /**
     * Remove candy at given coordinates from the world
     * 
     * @param p The coordinates of the candy to remove
     */
    private void removeFromWorld(Pair<Integer, Integer> p) {
        getWorld().removeObject(candies[p.x][p.y]);
        if(!init)MainWorld.addPoints(100);
        candies[p.x][p.y] = null;
    }
    
    /**
     * Move all candies down to fill in empty spaces
     */
    public void drop() {
        Pair<Integer, Integer> nullCoor = checkNullCandy();
        while (nullCoor != null) {
            if(nullCoor.x == 0)
                addCandy(nullCoor.x, nullCoor.y);
            else {
                swap(new Pair<>(nullCoor.x, nullCoor.y), new Pair<>(nullCoor.x-1, nullCoor.y));
                swapGraphics(new Pair<>(nullCoor.x, nullCoor.y), new Pair<>(nullCoor.x-1, nullCoor.y));
            }
            nullCoor = checkNullCandy();
        }
    }
    
    /**
     * Check if there are any null candies
     * 
     * @return Pair<Integer, Integer> The coordinates of the null candy, null if there are none
     */
    private Pair<Integer, Integer> checkNullCandy() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if(candies[i][j] == null)
                    return new Pair<>(i,j);
        return null;
    }
    
    /**
     * Swap two candies
     * 
     * @param a The first candy to swap
     * @param b The second candy to swap
     */
    private void swap(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        Candy temp = candies[a.x][a.y];
        candies[a.x][a.y] = candies[b.x][b.y];
        candies[b.x][b.y] = temp;
    }
    
    /**
     * Swap the graphics of two candies
     * 
     * @param a The first candy to swap
     * @param b The second candy to swap
     */
    private void swapGraphics(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        if(candies[a.x][a.y] != null)
            cells[a.x][a.y].setCandy(candies[a.x][a.y]);
        if(candies[b.x][b.y] != null)
            cells[b.x][b.y].setCandy(candies[b.x][b.y]);
    }
    
    /**
     * Clear all candies on board
     */
    private void clearAll() {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                removeFromWorld(new Pair<>(i, j));
        drop();
    }

    /**
     * Clear all candies in a given row
     * @param c The candy to clear the row of
     */
    public void clearRow(Candy c) {
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c)) {
                    clearRow(i);
                    return;
                }
        }
    }
    
    /**
     * Clear all candies in a given row
     * @param row The row to clear
     */
    public void clearRow(int row) {
        for (int i = 0; i < candies[row].length; i++) {
            removeFromWorld(new Pair<>(row, i));
            drop();
        }
    }
    
    /**
     * Clear all candies in a given column
     * @param c The candy to clear the column of
     */
    public void clearCol(Candy c) {
        for (int i = 0; i < candies.length; i++) {
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c)) {
                    clearCol(j);
                    return;
                }
        }
    }
    
    /**
     * Clear all candies in a given column
     * @param col The column to clear
     */
    public void clearCol(int col) {
        for (int i = 0; i < candies.length; i++) {
            removeFromWorld(new Pair<>(i, col));
            drop();
        }
    }
    
    /**
     * Clear all candies of a given colour
     * @param c The colour to clear
     */
    public void clearColour(Colour c){
        for(int i = 0; i < candies.length; i++) {
            for(int j = 0; j < candies[i].length; j++) {
                if (candies[i][j] != null && candies[i][j].getColour() == c) {
                    removeFromWorld(new Pair<>(i, j));
                    drop();
                }
            }
        }
    }
    
    /**
     * Get row of candies
     * @param row The row to get
     * @return Candy[] The row of candies
     */
    public Candy[] getRow(int row) {
        return candies[row];
    }
    
    /**
     * Get explosion grid of a candy
     * @param c The candy to get the explosion grid of
     */
    public void getExploGrid(Candy c) {
        Pair<Integer, Integer> p = getGridCoor(c);   
        for (int i = p.x - 1; i <= p.x + 1; i++) {
            for (int j = p.y - 1; j <= p.y + 1; j++) {
                if (validCoor(i, j)) {
                    getWorld().removeObject(candies[i][j]);
                    candies[i][j] = null;
                    drop();
                }
            }
        }
    }
    
    /**
     * Get candies in a 1D array
     * @return Candy[] The candies in a singular array
     */
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
    
    /**
     * Get the coordinates of a candy
     * @param c The candy to get the coordinates of
     * @return Pair<Integer, Integer> The coordinates of the candy
     */
    public Pair<Integer, Integer> getGridCoor(Candy c) {
        for (int i = 0; i < candies.length; i++)
            for (int j = 0; j < candies[i].length; j++)
                if (candies[i][j] != null && candies[i][j].equals(c))
                    return new Pair<>(i, j);
        return null;
    }
}
