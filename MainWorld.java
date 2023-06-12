import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainWorld extends World
{
    private GameGrid grid;
    //Candy Count variables
    private int counter;
    private int totalCandy;
    private int colour;
    
    private static boolean objComplete;
    
    //Ingredients variables
    private int totalIngredients;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MainWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 
        grid = new GameGrid(10,10);
        addObject(grid, 400, 400);
        grid.addCandies();
        
        objComplete = false;
        
        double doubleRandomNumber = Math.random() * 4;
        int randomNumber = (int)doubleRandomNumber;
        if (randomNumber == 0){
            colour = 0;
        }else if (randomNumber == 1){
            colour = 1;
        }else if (randomNumber == 2){
            colour = 2;
        }else if (randomNumber == 3){
            colour = 3;
        }
        
        totalIngredients = 2;
    }
    
    public void act(){
        if (true){ //if the objective is candy count
            if (true){ //candy of that number is destroyed
                counter++;
            }
        }
        if (true){ //if the objective is getting ingredients
            for (int i = 1; i < 9; i++){
                for (Object obj : getObjectsAt(GameGrid.getCellX(i, 8), GameGrid.getCellY(i, 8), Actor.class))
                {
                    if (obj instanceof Candy)
                    {
                        totalIngredients--;
                    }
                }
            }
            if (checkObj2() == true){
                //getWorld().nextWorld(WinScreen);
            }
        }
    }
    
    public boolean checkObj1(){
        if (counter == totalCandy){
            objComplete = true;
        }
        return objComplete;
    }
    
    public boolean checkObj2(){
        if (totalIngredients == 0){
            objComplete = true;
        }else{
            objComplete = false;
        }
        return objComplete;
    }
}
