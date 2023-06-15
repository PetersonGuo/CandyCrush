import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartWorld here.
 * 
 * @author Peterson Guo, Isaac Chan
 * @version June 15, 2023
 * 
 * 
 * Credit for background: https://www.deviantart.com/mikavklover/art/Candy-Shop-Background-Layout-200909563
 * 
 */
public class StartWorld extends Worlds {
    private Text candy, ingredient;
    private int screen;
    /**
     * Constructor for objects of class StartWorld.
     */
    public StartWorld() {
        this(0);
    }
    
    public StartWorld(int screen) {
        GreenfootImage background = new GreenfootImage("StartBackground2.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        if (screen == 0) drawScreen();
        else if (screen == 1) drawSelection();
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("i")) //opens up instructions
            drawInstructions();        
        else if (Greenfoot.isKeyDown("enter")) //checks to see if user starts the game
            screen = 1;
        else if (Greenfoot.mouseClicked(candy))
            Greenfoot.setWorld(new MainWorld(true));
        else if (Greenfoot.mouseClicked(ingredient))
            Greenfoot.setWorld(new MainWorld(false));
        else if (screen == 0) drawScreen();
        else if (screen == 1) drawSelection();
    }
    
    private void drawScreen() {
        removeObjects(getObjects(Actor.class));
        addObject(new Text("Candy Crush Mania", Color.GREEN, 55), FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 8);
        addObject(new Text("Hold I to Find How to Play", Color.BLUE, 40), FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2);
        addObject(new Text("Press Enter to Begin", Color.BLUE, 40), FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT - 100);
    }
    
    private void drawInstructions() {
        removeObjects(getObjects(Actor.class));
        addObject(new Text("How to Play", Color.WHITE, 50), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 12);
        addObject(new Text("Click and Drag candies to match",Color.BLUE, 30), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 6);
        addObject(new Text("in a pair of the same colour",Color.BLUE, 30), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 6 + 20);
        addObject(new Text("Match 3, 4 or 5 Candies in a row", Color.BLUE, 30), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 3 + 20);
        addObject(new Text("Special Candy:",Color.BLUE, 30), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 60);
        addObject(new Text("Wrapped:",Color.BLUE, 20), FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(new Text("Explode Adjacent Candy",Color.BLUE, 10), FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(new Text("Striped:",Color.BLUE, 20), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(new Text("Destroy Candies on Line",Color.BLUE, 10), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(new Text("Bomb:",Color.BLUE, 20), FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(new Text("Destroy Same Colour Candy", Color.BLUE, 10), FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(new Text("Follow the Objective to win the game!",Color.BLUE, 30), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT - 20);
    }
    
    private void drawSelection() {
        removeObjects(getObjects(Actor.class));
        addObject(new Text("Choose your Objective", Color.WHITE, 50), FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 8);
        addObject((candy = new Text("Candy Count:", Color.BLUE, 30)), FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 3 + 25);
        addObject(new Text("Match a certain", Color.PINK, 30), FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 2 + 10);
        addObject(new Text("# of candies", Color.PINK, 30), FINAL.WORLD_WIDTH /3 + 20, FINAL.WORLD_HEIGHT/ 2 + 35);
        addObject((ingredient = new Text("Ingredient Drop:",Color.BLUE, 30)), FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 20, FINAL.WORLD_HEIGHT/ 3 + 25);
        addObject(new Text("Drop Ingredients",Color.PINK, 30), FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 20, FINAL.WORLD_HEIGHT/ 2 + 10);
        addObject(new Text("at bottom of puzzle",Color.PINK, 30), FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH / 3 - 25, FINAL.WORLD_HEIGHT/ 2 + 35);
    }
}
