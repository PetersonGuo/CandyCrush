import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Candy called Ingredient that is a type of candy
 * that is dropped to the bottom of the screen to complete the ingredients
 * objective in the game. Ingredients come as either cherries or chestnuts
 * and cannot be destroyed unless they reach the bottom row of the game grid.
 * 
 * @author Kevin Luo 
 * @version June 15, 2023
 */
public class Ingredient extends Candy
{    
    /**
     * A constructor method that takes an integer of the total ingredients in 
     * the game that must be collected for the objective to be complete and
     * creates a total of that many ingredients.
     * 
     * @param totalIngredients  The number of ingredients required to complete
     *                          the game's objective
     */
    public Ingredient(int totalIngredients){
        super(null);
        
        int num = (int) (Math.random() * 2 + 1);
        if (num == 1){
            image = new GreenfootImage("Cherry.png");
        }else if (num == 2){
            image = new GreenfootImage("hazelNut.png");
        }
        setCandyImage();
    }
    
    /**
     * A method that does nothing for ingredients since they dont have 
     * any special abilities.
     */
    public void useAbility() {
        
    }
    
    /**
     * A method that allows ingredients to be destroyed from the world.
     */
    public void destroy(){
        getWorld().removeObject(this);
    }
    
    public int getXLocation(){
        return this.getX();
    }
    
    public int getYLocation(){
        return this.getY();
    }
}