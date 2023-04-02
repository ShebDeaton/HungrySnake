import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.lang.*;
import java.util.Random;
import java.util.ArrayList;

public class SnakeThing{
    private Timer timer;
    private Random rand = new Random();
    private int snakeSpeed;
    private int direction;
    private int tailDuration;
    private int tailMax;
    private int snakeSize;
    //private int[] segmentX;
    //private int[] segmentY;
    private ArrayList<Integer> segmentX = new ArrayList<Integer>();
    private ArrayList<Integer> segmentY = new ArrayList<Integer>();
    private int newX;
    private int newY;
    private int startX;
    private int startY;
    private int age = 0;
    private int lifeSpan = 500;
    private boolean alive = true;
    private Color headColor;
    private Color tailColor;
    private ArrayList<Integer> foodX = new ArrayList<Integer>();
    private ArrayList<Integer> foodY = new ArrayList<Integer>();
    private boolean isFull = false;
    private int headColorNum;
    private int tailColorNum;

    //PRETTY COLORS STUFF
    private int headRed;
    private int headGreen;
    private int headBlue;
    private int tailRed;
    private int tailGreen;
    private int tailBlue;
    private Boolean gradientFlag;

    private int snakeName;


    public SnakeThing (int hRed, int hGreen, int hBlue, int tRed, int tGreen, int tBlue, int size, int speed, Boolean gFlag, int length, int name, int headNum, int tailNum)
    {
        headRed = hRed;
        headGreen = hGreen;
        headBlue = hBlue;
        tailRed = tRed;
        tailGreen = tGreen;
        tailBlue = tBlue;

        if(gFlag == true){
            headColorNum = 7;
            tailColorNum = 7;
        }
        else{
            headColorNum = headNum;
            tailColorNum = tailNum;
        }
        snakeSize = size;
        snakeSpeed = speed;

        gradientFlag = gFlag;
        tailMax = length;
        snakeName = name;

        // segmentX = new int[tailMax+1];
        // segmentY = new int[tailMax+1];
        spawnSnake();
    }

    private void spawnSnake() {
        //Decide where the snake will start.
        startX = rand.nextInt(700);
        startY = rand.nextInt(700);
        //If the food list isn't empty
        if (foodX.size() > 0) {
            //Head to food
            direction = 8;
        }
        else {
            //Move in a random direction
            direction = rand.nextInt(8);
        }
        int spawnCount = tailDuration;
        while(spawnCount<tailMax+1){
            //If the snake is dead, don't continue.
            if(alive == false) {
                break;
            }
            else{
                incrementSnake();
                spawnCount++;
            }
        }
    
    }

    //Increment the snake
    public void incrementSnake(){
        if(checkPulse()==true) {
            //Prepare the snake to move forward.
            //Move the snake.
            switch(direction) {
                case 0:
                    //The North Direction
                    newX = startX + (int) ((Math.round(Math.random())*2-1) * snakeSize/2 * Math.random());
                    newY = startY + (int) ((-1)* snakeSize/2 * Math.random()); 
                    break;
                case 1:
                    //The North-East Direction
                    newX = startX + (int) (snakeSize/2 * Math.random());
                    newY = startY + (int) ((-1)*snakeSize/2 * Math.random());
                    break;
                case 2:
                    //The East Direction
                    newX = startX + (int) (snakeSize/2 * Math.random());
                    newY = startY + (int) ((Math.round(Math.random())*2-1) * snakeSize/2 * Math.random());
                    break;
                case 3:
                    //The South-East Direction
                    newX = startX + (int) (snakeSize/2 * Math.random());
                    newY = startY + (int) (snakeSize/2 * Math.random());
                    break;
                case 4:
                    //The South Direction
                    newX = startX + (int) ((Math.round(Math.random())*2-1) * snakeSize/2 * Math.random());
                    newY = startY + (int) (snakeSize/2 * Math.random()); 
                    break;
                case 5:
                    //The South-West Direction
                    newX = startX + (int) ((-1)*snakeSize/2 * Math.random());
                    newY = startY + (int) (snakeSize/2 * Math.random());
                    break;
                case 6:
                    //The West Direction
                    newX = startX + (int) ((-1)*snakeSize/2 * Math.random());
                    newY = startY + (int) ((Math.round(Math.random())*2-1) * snakeSize/2 * Math.random());
                    break;
                case 7:
                    //The North-West Direction
                    newX = startX + (int) ((-1)*snakeSize/2 * Math.random());
                    newY = startY + (int) ((-1)*snakeSize/2 * Math.random());
                    break;
                case 8:
                    //The Food Direction.
                    //Get the oldest food
                    int focusFoodX = foodX.get(0);
                    int focusFoodY = foodY.get(0);
                        //Compare startX to focusFoodX
                    if (startX > focusFoodX) {
                        //If right of food, go left.
                        newX = startX + (int) ((-1)*snakeSize/2 * Math.random());                         
                    }
                    else {
                        //If left of food, go right.
                        newX = startX + (int) (snakeSize/2 * Math.random());
                    }
                    //Compare startY to focusFoodY
                    if (startY > focusFoodY) {
                        //If below food, go up
                        newY = startY + (int) ((-1)* snakeSize/2 * Math.random()); 
                    }
                    else {
                        //If above the food, go down.
                        newY = startY + (int) (snakeSize/2 * Math.random()); 
                    }     
                    break;
            }
            //Keep track of tail segments
            if(tailDuration < tailMax) {
                isFull = false;
                //If the snake is not at full size
                //Keep track of each segment and increase the size.
                segmentX.add(startX);
                segmentY.add(startY);
                tailDuration++;
                if(tailDuration==tailMax){
                    isFull = true;
                }
            }
            if (tailDuration == tailMax) {
                //If the snake is at full size
                //Keep track of each segment
                segmentX.set(tailDuration-1,startX);
                segmentY.set(tailDuration-1, startY);
                //Mark the isFull flag true
                isFull = true;
                //Shift all segments down
                for(int i=0;i<tailMax-1;i++)
                {
                    segmentX.set(i, segmentX.get(i+1));
                    segmentY.set(i, segmentY.get(i+1));
                }

            }
            startX = newX;
            startY = newY;
            //The snake grows up
            birthday();
        }
    }
    private void birthday(){
        age++;
        if (age>=lifeSpan){
            killSnake();
        }
    }


    public void reviveSnake(){
        alive = true;
    }

    public void healSnake(){
        age = age - 100;
    }

    //Kill the snake.
    public void killSnake(){
        alive = false;
    }

    //Check if the snake is alive or dead.
    public boolean checkPulse(){
        return alive;
    }

    //Change the snake's direction.
    public void changeDirection(int dir){
        direction = dir;
    }

    //Get the snake's head color.
    public Color getHeadColor(){
        return headColor;
    }

    //Get the snake's tail color.
    public Color getTailColor(){
        return tailColor;
    }

    //Get the snake's latest X coordinate
    public int getX(){
        return newX;
    }

    //Get the snake's latest Y coordinate
    public int getY(){
        return newY;
    }

    //Get the isFull boolean
    public boolean isFull() {
        return isFull;
    }

    //Get the snake's X-Segment List.
    public ArrayList<Integer> getSegmentX(){
        return segmentX;
    }

    //Get the snake's Y-Segment List.
    public ArrayList<Integer> getSegmentY(){
        return segmentY;
    }

    //Get the snake's size.
    public int getSize(){
        return snakeSize;
    }

    public int getLength(){
        return tailMax;
    }

    public void incrementTail(){
        tailMax += 3;
    }

    public int getSpeed() {
        return snakeSpeed;
    }

    //Get the latest list of food locations.
    public void refreshFood(ArrayList<Integer> curFoodX, ArrayList<Integer>curFoodY){
        foodX = curFoodX;
        foodY = curFoodY;
    }

    public int getHeadNum(){
        return headColorNum;
    }

    public int getTailNum(){
        return tailColorNum;
    }

    public int getHeadRed() {
        return headRed;
    }

    public int getHeadGreen() {
        return headGreen;
    }

    public int getHeadBlue() {
        return headBlue;
    }

    public int getTailRed() {
        return tailRed;
    }

    public int getTailGreen() {
        return tailGreen;
    }

    public int getTailBlue() {
        return tailBlue;
    }

    public Boolean isGradient() {
        return gradientFlag;
    }

    public void incrementRed(int num) {
        headRed += num;
        tailRed += num;
    }

    public void incrementGreen(int num) {
        headGreen += num;
        tailGreen += num;
    }

    public void incrementBlue(int num) {
        headBlue += num;
        tailBlue += num;
    }

    public int getNameNum(){
        return snakeName;
    }
}