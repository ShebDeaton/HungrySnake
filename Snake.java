import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.lang.*;
import java.util.Random;
import java.util.ArrayList;

public class Snake extends JPanel implements MouseListener{
    private static int imageWidth = 700;
    private static int imageHeight = 700;
    private BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    private Timer snakeAnimation;
    private Random rand = new Random();
    private int firstSpawn = 0;
    public int direction;
    //Tail Stuff
    private int tailDuration = 0;
    private static int tailMax = 15;
    private int[] segmentX = new int[tailMax+1];
    private int[] segmentY = new int[tailMax+1];
    private boolean recentlyEaten;
    //Food Stuff
    public ArrayList<Integer> foodX = new ArrayList<Integer>();
    public ArrayList<Integer> foodY = new ArrayList<Integer>();
    public ArrayList<SnakeThing> snakeList = new ArrayList<SnakeThing>();
    private boolean resetSnakes = false;
    private boolean flushSnakes = false;
    private int borderThickness;
    
    private int indSnakeSpeed = 100 ;
    
    
    //Starting coordinates of the snake
    int startX;
    int startY;

    int change;

    /*
     * Changeable Attributes of the Snake
     */
    //radius of body of snake
    int speedSize = 15;

    //speed of the snake
    //Not able to be used for now.
    //private int snakeDelay = 500;

    //Color of the snake head
    Color headColor = Color.black;

    //Color of the snake body
    Color tailColor = Color.black;


    
    public Snake()
    {  
        addMouseListener(this);
        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }


    public void drawSnake2(int headRed, int headGreen, int headBlue, int tailRed, int tailGreen, int tailBlue, int size, int snakeDelay, Boolean flag, int length, int name)
    {

        //Make a Snake
        SnakeThing snake = new SnakeThing(headRed, headGreen, headBlue, tailRed, tailGreen, tailBlue, size, snakeDelay, flag, length, name);
        //Add it to a list of snakes.
        snakeList.add(snake);
    }

    public void drawSnakes() {
        if (snakeAnimation != null && snakeAnimation.isRunning()){
            snakeAnimation.stop();
        }
        repaint();
        Graphics2D g2 = img.createGraphics();
        ActionListener drawSnakes = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //For every snake in the list
                for(int i=0;i<snakeList.size();i++)
                {
                    //Deal with the current snake
                    SnakeThing curSnake = snakeList.get(i);

                    //Update the snake's food list.
                    curSnake.refreshFood(foodX,foodY);
                    //If the food list isn't empty
                    if(foodX.size() > 0) {
                        //Check if a snake touch's food
                        curSnake.changeDirection(8);
                        if ((Math.abs(curSnake.getX() - foodX.get(0)) <= curSnake.getSize()) && (Math.abs(curSnake.getY() - foodY.get(0))) <= curSnake.getSize()) {
                            //Delete Food
                            g2.setColor(Color.white);
                             //Top Bun
                            g2.fillRect(foodX.get(0)+1,foodY.get(0)-6,11,3);
                            g2.fillRect(foodX.get(0),foodY.get(0)-3,13,3);
                            //Tomato
                            g2.fillRect(foodX.get(0)+1,foodY.get(0),10,1);
                            //Burger
                            g2.fillRect(foodX.get(0)+1,foodY.get(0)+1,10,1);
                            //Lettuce
                            g2.fillRect(foodX.get(0)+1,foodY.get(0)+2,10,1);
                            //Bottom Bun
                            g2.fillRect(foodX.get(0),foodY.get(0)+3,13,3);
                            g2.fillRect(foodX.get(0)+1,foodY.get(0)+6,11,3);
                            foodX.remove(0);
                            foodY.remove(0);
                            //Heal the snake
                            curSnake.healSnake();
                            //If the snake is dead
                            if (curSnake.checkPulse() == false){
                                //Revive it
                                curSnake.reviveSnake();
                            }
                            //Increment snake's length
                            curSnake.incrementTail(); 

                        }
                        //Check if the food list is empty again
                        if (foodX.size() == 0) {
                            //Change the direciton randomly if so.
                            int randDir = rand.nextInt(8);
                            curSnake.changeDirection(randDir);
                            resetSnakes = true;
                            recentlyEaten = true;
                        }
                    }
                    //Check if the food list is empty
                    if(resetSnakes == true) {
                        //Change the direciton randomly if so.
                        int randDir = rand.nextInt(8);
                        curSnake.changeDirection(randDir);
                        if((i==snakeList.size()-1) && flushSnakes == true){
                            flushSnakes = false;
                            resetSnakes = false;
                        }
                        if(i==snakeList.size()-1 && resetSnakes == true){
                            flushSnakes = true;
                        }
                        continue;
                    }

                    //Check the borders
                    int[] directionOptions = new int[3];
                    int choice = rand.nextInt(3);
                    //Check the left border
                    if (curSnake.getX() < 0+curSnake.getSize()) {
                        //Set direction to east(2), north east(1), or south east(3)
                        directionOptions[0] = 2;
                        directionOptions[1] = 1;
                        directionOptions[2] = 3;
                        curSnake.changeDirection(directionOptions[choice]);
                    }
                    //Check the right border
                    if (curSnake.getX() > imageWidth-curSnake.getSize()-borderThickness) {
                        //Set direction to west(6), north west(7), or south west(5)
                        directionOptions[0] = 6;
                        directionOptions[1] = 7;
                        directionOptions[2] = 5;
                        curSnake.changeDirection(directionOptions[choice]);
                    }
                    //Check the top border
                    if (curSnake.getY() < 0+curSnake.getSize()) {
                        //Set direction to south(4), south west(5), or south east(3)
                        directionOptions[0] = 4;
                        directionOptions[1] = 5;
                        directionOptions[2] = 3;
                        curSnake.changeDirection(directionOptions[choice]);
                    }
                    //Check the bottom border
                    if (curSnake.getY() > imageHeight-curSnake.getSize()-borderThickness) {
                        //Set direction to north(0), north west(7), or north east(1)
                        directionOptions[0] = 0;
                        directionOptions[1] = 7;
                        directionOptions[2] = 1;
                        curSnake.changeDirection(directionOptions[choice]);
                    }

                    //Check if the snake is full
                    if(curSnake.isFull()==true){
                        g2.setColor(Color.white);
                        g2.fillOval(curSnake.getSegmentX().get(0), curSnake.getSegmentY().get(0), curSnake.getSize(), curSnake.getSize());
                    }
                    //Paint the snake.
                    for(int j=1;j<curSnake.getSegmentX().size();j++){
                        //Different color for the tail to see
                        //g2.setColor(curSnake.getTailColor());
                        
                        g2.setColor(new Color(curSnake.getTailRed(), curSnake.getTailGreen(), curSnake.getTailBlue()));    
                        g2.fillOval(curSnake.getSegmentX().get(j), curSnake.getSegmentY().get(j), curSnake.getSize(), curSnake.getSize());

                        //temporary color of this snake
                        //g2.setColor(curSnake.getHeadColor());
                        g2.setColor(new Color(curSnake.getHeadRed(), curSnake.getHeadGreen(), curSnake.getHeadBlue()));
                        //the base shape of the snake
                        g2.fillOval(curSnake.getX(), curSnake.getY(), curSnake.getSize(), curSnake.getSize());
                    }

                    if (curSnake.isGradient())
                    {
                        if (curSnake.getHeadRed() <= 230)
                            curSnake.incrementRed(10);
                        else if (curSnake.getHeadGreen() <= 150)
                            curSnake.incrementGreen(10);
                        else if (curSnake.getHeadBlue() <= 150)
                            curSnake.incrementBlue(10);
                        else
                        {
                            curSnake.incrementRed(-100);
                            curSnake.incrementGreen(-50);
                            curSnake.incrementBlue(-100);
                        } 
                    } 
                    


                    curSnake.incrementSnake();
                    repaint();
                }
            }
        };
        //Check every second...
        snakeAnimation = new Timer(100, drawSnakes);
        snakeAnimation.start();
    }

    public void cleanSnake(int deadSnake){
        Graphics2D g2 = img.createGraphics();
        SnakeThing snakeCorpse = snakeList.get(deadSnake);
        for(int j=0;j<snakeCorpse.getSegmentX().size();j++){
            //Set the color to white, to match background.
            g2.setColor(Color.white);
            //Cover the removed snake.
            g2.fillOval(snakeCorpse.getSegmentX().get(j), snakeCorpse.getSegmentY().get(j), snakeCorpse.getSize(), snakeCorpse.getSize());
            //the base shape of the snake
            g2.fillOval(snakeCorpse.getX(), snakeCorpse.getY(), snakeCorpse.getSize(), snakeCorpse.getSize());
            repaint();
        }
    }

    public void drawBorder()
    {
        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        borderThickness = 5;
        int horziontalBorderWidth = imageWidth;
        int verticalBorderHeight = imageHeight;
        g.fillRect(0,0,horziontalBorderWidth, borderThickness);
        g.fillRect(0,imageHeight-borderThickness,horziontalBorderWidth, borderThickness);
        g.fillRect(0,0,borderThickness, verticalBorderHeight);
        g.fillRect(imageWidth-borderThickness,0,borderThickness, verticalBorderHeight);
    }

    public void mouseClicked(MouseEvent e) {
        Graphics g = getGraphics();
        int mouseX = e.getX();
        int mouseY = e.getY();
        if ((mouseX<imageWidth-10 && mouseY<imageHeight-10) && 
            (mouseX>10 && mouseY>10))  {
            foodX.add(mouseX);
            foodY.add(mouseY);
        }
    }

    public void mouseEntered(MouseEvent e) {}  
    public void mouseExited(MouseEvent e) {}  
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {} 

    public void drawFood() {
        Graphics2D g = img.createGraphics();
        //Draw a hamburger
        for (int i=0;i<foodX.size();i++){
            //Top Bun
            g.setColor(new Color(153,102,0));
            g.fillRect(foodX.get(i)+1,foodY.get(i)-6,11,3);
            g.fillRect(foodX.get(i),foodY.get(i)-3,13,3);
            //Tomato
            g.setColor(Color.RED);
            g.fillRect(foodX.get(i)+1,foodY.get(i),10,1);
            //Burger
            g.setColor(new Color(102,51,0));
            g.fillRect(foodX.get(i)+1,foodY.get(i)+1,10,1);
            //Lettuce
            g.setColor(Color.GREEN);
            g.fillRect(foodX.get(i)+1,foodY.get(i)+2,10,1);
            //Bottom Bun
            g.setColor(new Color(153,102,0));
            g.fillRect(foodX.get(i),foodY.get(i)+3,13,3);
            g.fillRect(foodX.get(i)+1,foodY.get(i)+6,11,3);
        }
    }
    
    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        super.paintComponent(g);
        drawBorder();
        drawFood();
        //im ngl i have no idea what this does but seems important
        if (img != null)
            g.drawImage(img, 0, 0, this);
    } 
}
