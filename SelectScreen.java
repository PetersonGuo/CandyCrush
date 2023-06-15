import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SelectScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelectScreen extends Worlds
{

    /**
     * Constructor for objects of class SelectScreen.
     * 
     */
    Text candy, ingredient;
    public SelectScreen()
    {
        GreenfootImage background = new GreenfootImage("StartBackground2.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        
        
        Text display = new Text("Choose your Objective", Color.WHITE, 50);
        Text candy = new Text("Candy Count:", Color.BLUE, 30);
        Text candy2 = new Text("Match a certain", Color.PINK, 30);
        Text candy3 = new Text("# of candies", Color.PINK, 30);
        Text ingredient = new Text("Ingredient Drop:",Color.BLUE, 30);
        Text ingredient2 = new Text("Drop Ingredients",Color.PINK, 30);
        Text ingredient3 = new Text("at bottom of puzzle",Color.PINK, 30); 
        
        addObject(display, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 8);
        addObject(candy, FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 3 + 25);
        addObject(candy2, FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 2 + 10);
        addObject(candy3, FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 2 + 35);
        addObject(ingredient, FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 20, FINAL.WORLD_HEIGHT/ 3 + 25);
        addObject(ingredient2, FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 20, FINAL.WORLD_HEIGHT/ 2 + 10);
        addObject(ingredient3, FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 25, FINAL.WORLD_HEIGHT/ 2 + 35);
    }
    
    
    public void act() {
        
        if(Greenfoot.mouseClicked(candy)) { //if selected candycount objective
            Greenfoot.setWorld(new MainWorld(true));
        }
        if(Greenfoot.mouseClicked(ingredient)) { //selected ingredient drop objective
            Greenfoot.setWorld(new MainWorld(false));
        }
        
    }
    
}
