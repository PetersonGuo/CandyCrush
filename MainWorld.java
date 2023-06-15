import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The main world runs the main portion of the game. This game is an iteration
 * of the game Candy Crush where the goal is to get the highest score while 
 * completing the main objective, determined by the user. The game requires the
 * player to match candies of the same colours into groups of 3 or greater either
 * vertically or horizontally. There are special candies that can be generated 
 * if the match is greater than 3 or creates a cross-section between 2 matches.
 * Score will be calculated breaking candies and completing the objective. A
 * player has a certain number of moves when playing so players must be wise with
 * their decision-making. 
 * <p>
 * This game was created by the following individuals: 
 * Isaac Chan, Peterson Guo, Kelby To, Kevin Luo, Jett Miysaki, William Tsao. <br>
 * Credit goes to Mr.Cohen for providing the following classes: SuperStatBar <br>
 * Credit goes to Greenfoot for providing the following classes: Text, Counter
 * <p>
 * Sources: <br>
 * Candy Visuals: King (developers of Candy Crush) <br>
 * Game Sounds: 
 * 
 * @author Peterson Guo, Kevin Luo, Kelby To 
 * @version June 15, 2023
 */
public class MainWorld extends World {
    private static GameGrid grid;
    private static Candy clicked;
    //Candy Count variables
    private static Counter score, moves;
    private static int counter, totalCandy, colour, objective;
    private static boolean objComplete;
    private Objectives obj;
    
    //Ingredients variables
    //private int totalIngredients;
    /**
     * Constructor for objects of class MyWorld.
     */
    public MainWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 
        
        score = new Counter("Score: ");
        moves = new Counter("Moves: ");
        moves.setValue(25);

        addObject(score, 100, 50);
        addObject(moves, 300, 50);
        
        grid = new GameGrid(10,10);
        addObject(grid, 400, 400);
        grid.addCandies();
        
        clicked = null;
        objComplete = false;
        
        objective = (int)(Math.random() * 2);
        if(objective == 1){
            obj = new CandyCount((int)(Math.random()*6));
        }else if(objective == 2){
            obj = new DropIngredients(2);
        }
        //totalIngredients = 2;
    }
    
    /**
     * Act - do whatever the MainWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        if(moves.getValue() == 0){
            //nextWorld
        }
        if (obj instanceof CandyCount){ //if the objective is candy count
            //something
        }
        if (obj instanceof DropIngredients){ //if the objective is getting ingredients
            for(Candy c : grid.getRow(9)){
                if(c instanceof Ingredient){
                    ((Ingredient)c).destroy();
                    ((DropIngredients)obj).decreaseIngredients();
                }
            }
        }
    }
    
    /**
     * A method that returns a boolean depending on whether the selected candy
     * can swap with another selected candy.
     * 
     * @param c         The first selected candy
     * @return boolean  A boolean that determines if a swap can be made 
     */
    public static boolean setClicked(Candy c) {
        boolean valid = false;
        if (clicked == null || c == null || clicked.equals(c)) clicked = c; //first click
        else {
            //checks if the second click can be swapped with the first click
            if ((Math.abs(c.getX() - clicked.getX()) <= FINAL.CELL_SIZE && c.getX() != clicked.getX() && c.getY() == clicked.getY()) || 
                (Math.abs(c.getY() - clicked.getY()) <= FINAL.CELL_SIZE && c.getY() != clicked.getY() && c.getX() == clicked.getX())) {
                valid = grid.validSwap(grid.getGridCoor(c), grid.getGridCoor(clicked));
                clicked = null;
            } else clicked = c;
        }
        if(valid) decreaseMoves();
        return valid;
    }
    
    /**
     * A method that adds points to the score
     * 
     * @param pts   The number of points the score increases by
     */
    public static void addPoints(int pts){
        score.changeValue(pts);
    }

    /**
     * A method that decreases the number of moves the player has left
     */
    public static void decreaseMoves(){
        moves.changeValue(-1);
    }
    
    /**
     * A method that determines if the objective is completed.
     */
    public static void objectiveCompleted(){
        objComplete = true;
    }
    
    /**
     * A method that returns the world's GameGrid
     * 
     * @return GameGrid     The game grid in the world
     */
    public static GameGrid getGrid() {
        return grid;
    }
    
    /**
     * A method that returns the candy thats currently being clicked
     * 
     * @return Candy    The candy thats being clicked
     */
    public static Candy getClicked() {
        return clicked;
    }
    
}
