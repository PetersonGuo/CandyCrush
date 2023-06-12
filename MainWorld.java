import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainWorld extends World
{
    private static GameGrid grid;
    private static Candy clicked;
    //Candy Count variables
    private int counter;
    private int totalCandy;
    private int colour;
    
    private int objective;
    private static boolean objComplete;
    private Objectives obj;
    
    //Ingredients variables
    //private int totalIngredients;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MainWorld(int objective)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 
        grid = new GameGrid(10,10);
        addObject(grid, 400, 400);
        grid.addCandies();
        
        clicked = null;
        objComplete = false;
        
        this.objective = objective;
        if(objective == 1){
            obj = new CandyCount((int)(Math.random()*6));
        }else if(objective == 2){
            obj = new DropIngredients(2);
        }
        //totalIngredients = 2;
    }
    
    public void act(){
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
    
    public static void setClicked(Candy c) {
        if (clicked != null) {
            if ((Math.abs(c.getX() - clicked.getX()) <= FINAL.CELL_SIZE && c.getX() != clicked.getX() && c.getY() == clicked.getY()) || 
                (Math.abs(c.getY() - clicked.getY()) <= FINAL.CELL_SIZE && c.getY() != clicked.getY() && c.getX() == clicked.getX())) {
                grid.validSwap(grid.getGridCoor(c), grid.getGridCoor(clicked));
                clicked = null;
            } else clicked = c;
        } else clicked = c;
    }
    
    public static void objectiveCompleted(){
        objComplete = true;
    }
}
