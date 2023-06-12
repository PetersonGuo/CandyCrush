import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ingredients here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ingredient extends Candy
{    
    public Ingredient(int totalIngredients){
        super(-1);
    }
    
    public void act()
    {
    }
    
    
    public void destroy(){
        getWorld().removeObject(this);
    }
}