import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass called CandyCount that is an objective that requires
 * the player to break a certain number of candies of a given colour. 
 * If the player manages to complete the objective they win.
 * 
 * @author Kevin Luo, Kelby To, Peterson Guo
 * @version June 15, 2023
 */
public class CandyCount extends Objectives {
    private int counter, totalCandy;
    private Colour colour;
    /**
     * A constructor method that takes the colour of the required colour
     * that must be destroyed to complete the objective.
     * 
     * @param colour    The required colour that is destroyed for the 
     *                  objective
     */
    public CandyCount(Colour colour){
        objComplete = false;
        this.colour = colour;
    }
    
    /**
     * Act - do whatever the CandyCount wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if(checkObj())
            MainWorld.objectiveCompleted();
    }
    
    /**
     * A method that checks if the objective is completed or not.
     *
     * @return objComplete  A boolean that is true if the objective is completed
     */
    public boolean checkObj(){
        if (counter >= totalCandy)
            objComplete = true;
        return objComplete;
    }
    
    /**
     * A method that adds to a counter that keeps track of the number of
     * candies destroyed.
     * 
     * @param add   The number of candies destroyed
     */
    public void increaseCandyCounter(int add){
        counter += add;
    }
}
