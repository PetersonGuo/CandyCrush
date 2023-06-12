import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ingredients here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ingredients extends Objectives
{
    private int totalIngredients;
    public Ingredients(int totalIngredients){
        objComplete = false;
        this.totalIngredients = totalIngredients;
    }
    
    public void act()
    {
        for (int i = 1; i < 9; i++){
            /*
            if (getWorld().getObjectsAt(GameGrid.getCellX(i, 8), GameGrid.getCellY(i, 8), Actor.class) instanceof Candy){
                totalIngredients--;
            }
            */
            for (Object obj : getWorld().getObjectsAt(GameGrid.getCellX(i, 8), GameGrid.getCellY(i, 8), Actor.class))
            {
                if (obj instanceof Candy)
                {
                    totalIngredients--;
                }
            }
        }
        if (checkObj() == true){
            //getWorld().nextWorld(WinScreen);
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
}