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
    //Food Stuff
    public ArrayList<Integer> foodX = new ArrayList<Integer>();
    public ArrayList<Integer> foodY = new ArrayList<Integer>();
    public ArrayList<SnakeThing> snakeList = new ArrayList<SnakeThing>();
    private boolean resetSnakes = false;
    private boolean flushSnakes = false;
    
    
    
    //Starting coordinates of the snake
    int startX;
    int startY;

    /*
     * Changeable Attributes of the Snake
     */
        //radius of body of snake
        int speedSize = 15;

        //speed of the snake
        private int snakeDelay = 500;

        //Color of the snake head
        Color headColor = Color.red;

        //Color of the snake body
        Color tailColor = Color.blue;


    
    public Snake()
    {  
        addMouseListener(this);
        /*
        this button would be like the add button i just needed
        it to test if this works for now
        */

        //
        //COMMENTED OUT: I turned the add button into the generate button at the end of
        //the customizations section
        //
        /* JButton addSnake = new JButton("Add");
        addSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tailDuration = 0;
                drawSnake();
            }
        });
        this.add(addSnake); */
        //drawSnake(headColor,tailColor,speedSize,snakeDelay);

        /*
        this is the background for the snakes to be drawn on. It has to be a 
        buffered image to show it as animating.
        */
        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void drawSnake2(Color head, Color tail, int size, int snakeDelay) {
        //Make a snake.
        SnakeThing snake = new SnakeThing(head, tail, size, snakeDelay);
        //Add it to a list of snakes
        snakeList.add(snake);
    }

    public void drawSnakes() {
        if (snakeAnimation != null && snakeAnimation.isRunning()){
            snakeAnimation.stop();
        }
        
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
                            g2.setColor(Color.yellow);
                            g2.fillRect(foodX.get(0),foodY.get(0),10,10);
                            foodX.remove(0);
                            foodY.remove(0);
                        }
                        //Check if the food list is empty again
                        if (foodX.size() == 0) {
                            //Change the direciton randomly if so.
                            int randDir = rand.nextInt(8);
                            curSnake.changeDirection(randDir);
                            resetSnakes = true;
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
                    if (curSnake.getX() > imageWidth-curSnake.getSize()) {
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
                    if (curSnake.getY() > imageHeight-curSnake.getSize()) {
                        //Set direction to north(0), north west(7), or north east(1)
                        directionOptions[0] = 0;
                        directionOptions[1] = 7;
                        directionOptions[2] = 1;
                        curSnake.changeDirection(directionOptions[choice]);
                    }

                    //Check if the snake is full
                    if(curSnake.isFull()==true){
                        g2.setColor(Color.white);
                        g2.fillOval(curSnake.getSegmentX()[0], curSnake.getSegmentY()[0], curSnake.getSize(), curSnake.getSize());
                    }
                    //Paint the snake.
                    for(int j=1;j<curSnake.getSegmentX().length;j++){
                        //Different color for the tail to see
                        g2.setColor(curSnake.getTailColor());
                        g2.fillOval(curSnake.getSegmentX()[j], curSnake.getSegmentY()[j], curSnake.getSize(), curSnake.getSize());

                        //temporary color of this snake
                        g2.setColor(curSnake.getHeadColor());
                        //the base shape of the snake
                        g2.fillOval(curSnake.getX(), curSnake.getY(), curSnake.getSize(), curSnake.getSize());
                    }

                    curSnake.incrementSnake();
                    repaint();
                }
            }
        };
        //Check every second...
        snakeAnimation = new Timer(0, drawSnakes);
        snakeAnimation.start();
    }
/* 
    public void drawSnake(Color head, Color tail, int size, int snakeDelay) {
        Graphics2D g2 = img.createGraphics();
        //Paint the snake.
        g2.setColor(Color.red);
        //the base shape of the snake
        g2.fillOval(350, 350, 50, 50);
        repaint();
       //Pauses the drawing of the previous snake when u click add again, I dont
       // know how to keep the previous one going but we'll figure it out.
        
        if (snakeAnimation != null && snakeAnimation.isRunning()){
            snakeAnimation.stop();
        }
        
        startX = rand.nextInt(imageWidth);
        startY = rand.nextInt(imageHeight);
        //Check if there is food already.
        if (foodX.size() > 0) {
            // Head to the food.
            direction = 8;
        }
        else {
            //Random number generated initially for direction later
            //8 for cardinal directions
            direction = rand.nextInt(8);
        }

        ActionListener timerDraw = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics2D g2 = img.createGraphics();

                int newX;
                int newY;
                //changes the positioning of the snake, basically
                //it's next moves
                //Use a switch to keep direction consistent while still being random.
                switch (direction) {
                    case 0:
                        //north
                        newX = startX + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        newY = startY + (int) ((-1)* size/2 * Math.random()); 
                        break;
                    case 1:
                        //north-east
                        newX = startX + (int) (size/2 * Math.random());
                        newY = startY + (int) ((-1)*size/2 * Math.random());
                        break;
                    case 2:
                        //east
                        newX = startX + (int) (size/2 * Math.random());
                        newY = startY + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        break;
                    case 3:
                        //south-east
                        newX = startX + (int) (size/2 * Math.random());
                        newY = startY + (int) (size/2 * Math.random());
                        break;
                    case 4:
                        //south
                        newX = startX + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        newY = startY + (int) (size/2 * Math.random()); 
                        break;
                    case 5:
                        //south-west
                        newX = startX + (int) ((-1)*size/2 * Math.random());
                        newY = startY + (int) (size/2 * Math.random());
                       break;
                    case 6:
                        //west
                        newX = startX + (int) ((-1)*size/2 * Math.random());
                        newY = startY + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        break;
                    case 7:
                        //north-west
                        newX = startX + (int) ((-1)*size/2 * Math.random());
                        newY = startY + (int) ((-1)*size/2 * Math.random());
                        break;
                    case 8:
                        //to the oldest food
                        int focusFoodX = foodX.get(0);
                        int focusFoodY = foodY.get(0);
                        //Compare startX to focusFoodX
                        if (startX > focusFoodX) {
                            //If right of food, go left.
                            newX = startX + (int) ((-1)*size/2 * Math.random());                         
                        }
                        else {
                            //If left of food, go right.
                            newX = startX + (int) (size/2 * Math.random());
                        }
                        //Compare startY to focusFoodY
                        if (startY > focusFoodY) {
                            //If below food, go up
                            newY = startY + (int) ((-1)* size/2 * Math.random()); 
                        }
                        else {
                            //If above the food, go down.
                            newY = startY + (int) (size/2 * Math.random()); 
                        }                       
                        //Check if the food has been touched
                        if ((Math.abs(newX - focusFoodX) <= size) && (Math.abs(newY - focusFoodY) <= size)) {
                            //Delete Food
                            g2.fillRect(foodX.get(0),foodY.get(0),10,10);
                            foodX.remove(0);
                            foodY.remove(0);
                            //Check if the food list is empty
                            if (foodX.size() == 0) {
                                //Change the direciton randomly if so.
                                direction = rand.nextInt(8);
                            }
                        }
                        break;
                    default: 
                        newX = startX + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        newY = startY + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());  
                        break;
                }   
                
                int[] directionOptions = new int[3];
                int choice = rand.nextInt(3);
                //Check the left border
                if (newX < 0+size) {
                    //Set direction to east(2), north east(1), or south east(3)
                    directionOptions[0] = 2;
                    directionOptions[1] = 1;
                    directionOptions[2] = 3;
                    direction = directionOptions[choice];
                }
                //Check the right border
                if (newX > imageWidth-size) {
                    //Set direction to west(6), north west(7), or south west(5)
                    directionOptions[0] = 6;
                    directionOptions[1] = 7;
                    directionOptions[2] = 5;
                    direction = directionOptions[choice];
                }
                //Check the top border
                if (newY < 0+size) {
                    //Set direction to south(4), south west(5), or south east(3)
                    directionOptions[0] = 4;
                    directionOptions[1] = 5;
                    directionOptions[2] = 3;
                    direction = directionOptions[choice];
                }
                //Check the bottom border
                if (newY > imageHeight-size) {
                    //Set direction to north(0), north west(7), or north east(1)
                    directionOptions[0] = 0;
                    directionOptions[1] = 7;
                    directionOptions[2] = 1;
                    direction = directionOptions[choice];
                }

                //Keeps track of Tail Segments.
                if(tailDuration < tailMax) {
                    segmentX[tailDuration] = startX;
                    segmentY[tailDuration] = startY;
                    tailDuration++;
                }
                else if(tailDuration == tailMax) {
                    //Get rid of last tail segment.
                    segmentX[tailDuration] = startX;
                    segmentY[tailDuration] = startY;
                    g2.setColor(Color.white);
                    g2.fillOval(segmentX[0], segmentY[0], size, size);
                    //Shifts all segments down.
                    for(int i=0;i<tailMax;i++)
                    {
                        segmentX[i] = segmentX[i+1];
                        segmentY[i] = segmentY[i+1];
                        //Might have to use something similar to color the tail consistently?
                    }
                }

                //Different color for the tail to see
                g2.setColor(tail);
                g2.fillOval(startX, startY, size, size);

                //temporary color of this snake
                g2.setColor(head);
                //the base shape of the snake
                g2.fillOval(newX, newY, size, size);

                //New starting point based on current position.
                startX = newX;
                startY = newY;
                
                repaint();
            }
        };
        //Turn this comment off and he'll go SUPER fast.
        //snakeDelay = 0;
        snakeAnimation = new Timer(snakeDelay, timerDraw);
        snakeAnimation.start();
    } 
*/
    public void drawBorder(Graphics g)
    {
        g.setColor(Color.BLACK);
        int borderThickness = 5;
        int horziontalBorderWidth = imageWidth;
        int verticalBorderHeight = imageHeight;
        g.fillRect(0,0,horziontalBorderWidth, borderThickness);
        g.fillRect(0,imageHeight,horziontalBorderWidth, borderThickness);
        g.fillRect(0,0,borderThickness, verticalBorderHeight);
        g.fillRect(imageWidth,0,borderThickness, verticalBorderHeight);
    }

    public void mouseClicked(MouseEvent e) {
        Graphics g = getGraphics();
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (mouseX<imageWidth-10 && mouseY<imageHeight-10)  {
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
        g.setColor(Color.RED);
        for (int i=0;i<foodX.size();i++){
            g.fillRect(foodX.get(i),foodY.get(i),10,10);
        }
    }
    
    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        super.paintComponent(g);
        drawBorder(g);
        drawFood();
        //im ngl i have no idea what this does but seems important
        if (img != null)
            g.drawImage(img, 0, 0, this);
    } 
}
