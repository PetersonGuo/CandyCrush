import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CandyCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CandyCount extends Objectives
{
    private int counter;
    private int totalCandy;
    private int colour;
    public CandyCount(int colour){
        objComplete = false;
        this.colour = colour;
    }
    
    public void act()
    {
        if(checkObj()){
            MainWorld.objectiveCompleted();
        }
    }
    
    public boolean checkObj(){
        if (counter >= totalCandy){
            objComplete = true;
        }
        return objComplete;
    }
    
    public void increaseCandyCounter(int add){
        counter += add;
    }
}
