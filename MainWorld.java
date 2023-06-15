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
    //Candy Count variables
    private static Counter score, moves;
    private static int counter, totalCandy, colour, objective;
    private static boolean objComplete;
    private Objectives obj;
    
    //Ingredients variables
    //private int totalIngredients;
    /**
     * Constructor for objects of class MyWorld.
     * 
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
    
    public static boolean setClicked(Candy c) {
        boolean valid = false;
        if (clicked == null || c == null || clicked.equals(c)) clicked = c;
        else {
            if ((Math.abs(c.getX() - clicked.getX()) <= FINAL.CELL_SIZE && c.getX() != clicked.getX() && c.getY() == clicked.getY()) || 
                (Math.abs(c.getY() - clicked.getY()) <= FINAL.CELL_SIZE && c.getY() != clicked.getY() && c.getX() == clicked.getX())) {
                valid = grid.validSwap(grid.getGridCoor(c), grid.getGridCoor(clicked));
                clicked = null;
            } else clicked = c;
        }
        if(valid) decreaseMoves();
        return valid;
    }
    
    public static void addPoints(int pts){
        score.changeValue(pts);
    }
    
    public static void decreaseMoves(){
        moves.changeValue(-1);
    }
    
    public static void objectiveCompleted(){
        objComplete = true;
    }
    
    public static GameGrid getGrid() {
        return grid;
    }
    
    public static Candy getClicked() {
        return clicked;
    }
}
