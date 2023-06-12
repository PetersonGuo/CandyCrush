import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DropIngredients here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DropIngredients extends Objectives
{
    private static int totalIngredients;
    
    public DropIngredients(int totalIngredients){
        this.totalIngredients = totalIngredients;
    }
    
    /**
     * Act - do whatever the DropIngredients wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(checkObj()){
            MainWorld.objectiveCompleted();
        }
    }
    
    public boolean checkObj(){
        if (totalIngredients == 0){
            objComplete = true;
        }else{
            objComplete = false;
        }
        return objComplete;
    }
    
    public void decreaseIngredients(){
        totalIngredients--;
    }
}
