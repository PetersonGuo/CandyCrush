import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InstructionWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstructionWorld extends Worlds
{
    /**
     * Constructor for objects of class InstructionWorld.
     * 
     */
    public InstructionWorld()
    {    
        GreenfootImage background = new GreenfootImage("CandyEndScreen.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        
        
        Text htp = new Text("How to Play", Color.WHITE, 50);
        Text match = new Text("Match 3, 4 or 5 Candies in a row", Color.BLUE, 30);
        Text click = new Text("Click and Drag candies to match",Color.BLUE, 30);
        Text click2 = new Text("in a pair of the same colour",Color.BLUE, 30);
        Text objective = new Text("Follow the Objective to win the game!",Color.BLUE, 30);
        Text special = new Text("Special Candy:",Color.BLUE, 30);
        Text wrapped = new Text("Wrapped:",Color.BLUE, 20);
        Text wrapped2 = new Text("Explode Adjacent Candy",Color.BLUE, 10);
        Text striped = new Text("Striped:",Color.BLUE, 20);
        Text striped2 = new Text("Destroy Candies on Line",Color.BLUE, 10);
        Text bomb = new Text("Bomb:",Color.BLUE, 20);
        Text bomb2 = new Text("Destroy Same Colour Candy", Color.BLUE, 10);
        
        addObject(htp, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 12);
        addObject(click, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 6);
        addObject(click2, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 6 + 20);
        addObject(match, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 3 + 20);
        addObject(special, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 60);
        addObject(wrapped, FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(wrapped2, FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(striped, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(striped2, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(bomb, FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 80);
        addObject(bomb2, FINAL.WORLD_WIDTH - FINAL.WORLD_WIDTH /3, FINAL.WORLD_HEIGHT/2 + 100);
        addObject(objective, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT - 20);
    }
    
    
    public void act() {
        if(!Greenfoot.isKeyDown("i")) Greenfoot.setWorld(new StartWorld());
    }
}
