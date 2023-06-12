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
    public CandyCount(){
        objComplete = false;
        double doubleRandomNumber = Math.random() * 4;
        int randomNumber = (int)doubleRandomNumber;
        if (randomNumber == 0){
            colour = 0;
        }else if (randomNumber == 1){
            colour = 1;
        }else if (randomNumber == 2){
            colour = 2;
        }else if (randomNumber == 3){
            colour = 3;
        }
    }
    
    public void act()
    {
        if (true){ //candy of that number is destroyed
            counter++;
        }
    }
    
    public boolean checkObj(){
        if (counter == totalCandy){
            objComplete = true;
        }
        return objComplete;
    }
}
