import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass called DropIngredients that is an objective that requires
 * the player to drop an ingredient from the top of the screen to the bottom.
 * If the player manages to complete the objective they win the game.
 * 
 * @author Kevin Luo, Kelby To 
 * @version June 15, 2023
 */
public class DropIngredients extends Objectives {
    protected static int totalIngredients;    
    /**
     * A constructor method that takes an integer of the total ingredients 
     * required to drop to complete the objective.
     * 
     * @param totalIngredients  The total number of ingredients required to 
     *                          drop
     */
    public DropIngredients(int totalIngredients){
        DropIngredients.totalIngredients = totalIngredients;
    }
    
    /**
     * Act - do whatever the DropIngredients wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (checkObj())
            MainWorld.objectiveCompleted();
    }
    
    /**
     * A method that checks if teh objective is completed or not.
     * 
     * @return boolean A boolean that is true if the objective is completed
     */
    public boolean checkObj(){
        if (totalIngredients == 0){
            objComplete = true;
        }else{
            objComplete = false;
        }
        return objComplete;
    }
    
    /**
     * A method that counts down the number of ingredients required
     */
    public static void decreaseIngredients(){
        totalIngredients--;
    }
    
    public static int getTotalIngredients(){
        return totalIngredients;
    }
}
