import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * 
 * 
 * Credit for background: https://www.deviantart.com/mikavklover/art/Candy-Shop-Background-Layout-200909563
 * 
 */
import greenfoot.Font;
public class StartWorld extends Worlds
{
    GreenfootImage background;
    Text title, start, instruction;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public StartWorld()
    {    
        background = new GreenfootImage("StartBackground2.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        drawScreen();
    }
    
    public void act() {
        if(Greenfoot.isKeyDown("i")) { //opens up instructions
            Greenfoot.setWorld(new InstructionWorld());
        }
        if(Greenfoot.isKeyDown("enter")) { //checks to see if user starts the game
            Greenfoot.setWorld(new SelectScreen());
        }
    }
    
    public void drawScreen() {
        title = new Text("Candy Crush Mania", Color.GREEN, 55);
        start = new Text("Press Enter to Begin", Color.BLUE, 40);
        instruction = new Text("Hold I to Find How to Play", Color.BLUE, 40);
        addObject(title, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 8);
        addObject(instruction, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2);
        addObject(start, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT - 100);
        
        
    }
    
}
