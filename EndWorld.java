import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

/**
 * Write a description of class EndWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * 
 * Background: Same creator as previosly mentioned in StartWorld, edited by Jett M.
 */
public class EndWorld extends Worlds {
    GreenfootImage background;
    Text endResult, score, hScore1, hScore2, hScore3;
    
    //used to display score
    FileWriter userScore;
    PrintWriter output;
    Scanner scan;
    private int lines;
    private boolean linesLeft;
    private ArrayList<Integer> scoreboard;
    /**
     * Constructor for objects of class EndWorld.
     * 
     */
    
    public EndWorld() {
        background = new GreenfootImage("CandyEndScreen.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        
        //text visuals
        if(MainWorld.isObjectiveCompleted()) endResult = new Text("Mission Completed!", Color.WHITE, 50);
        else endResult = new Text("Mission Failed...", Color.WHITE, 50);
        addObject(endResult, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 8);    
        
        score = new Text("Score: ", Color.BLUE, 30);
        addObject(score, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT/ 4);
        
        scoreboard = new ArrayList<Integer>();
        Text topScores = new Text("Top Scores:", Color.BLUE, 30);
        addObject(topScores, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 3 + 30);
        updateScore();
        displayScore();
        
        Text play = new Text("Press Space to Play Again", Color.RED, 30);
        addObject(play, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT - 100);
    }
    
    public void act() {
        if(Greenfoot.isKeyDown("space")) {
            Greenfoot.setWorld(new StartWorld());
        }
    }
    
    public void updateScore() {
        try{
            userScore = new FileWriter("score.txt", true);
            output = new PrintWriter(userScore);
            output.println(MainWorld.getPoints());
            output.close();
        }
        catch(IOException e){
            System.out.println("No File Found...Cannot update scoreboard");
        }
    }
    public void displayScore() {
        try {
            scan = new Scanner(new File("score.txt"));
        } catch(FileNotFoundException e){
        }
        while(linesLeft){
            try {
                scoreboard.add(Integer.parseInt(scan.nextLine()));
            }
            catch(NoSuchElementException e){
                linesLeft = false;
            }
        }
        //sorting method called from Collections
        Collections.sort(scoreboard);
        Collections.reverse(scoreboard);
        
        //print score to screen
        try{
            hScore1 = new Text("Score: " + Integer.toString(scoreboard.get(0)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore1 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        try{
            hScore2 = new Text("Score: " + Integer.toString(scoreboard.get(0)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore2 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        try{
            hScore3 = new Text("Score: " + Integer.toString(scoreboard.get(0)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore3 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        
        addObject(hScore1, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2);
        addObject(hScore2, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2 + 40);
        addObject(hScore3, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2 + 80);
    }
}
