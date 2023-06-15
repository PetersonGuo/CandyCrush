import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A superclass called Objectives that contains all the different types
 * of objectives in the game. Objective help add to your score and can 
 * be either counting the type of candy destroyed or dropping ingredients.
 * 
 * @author Kevin Luo
 * @version  June 15, 2023
 */
public abstract class Objectives extends Actor
{
    protected static boolean objComplete;
    /**
     * An abstract class that determines what the objective of the main
     * world should be.
     */
    protected abstract boolean checkObj();
}
