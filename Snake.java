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
    //Tail Stuff
    private int tailDuration = 0;
    private static int tailMax = 10;
    private int[] segmentX = new int[tailMax+1];
    private int[] segmentY = new int[tailMax+1];
    //Food Stuff
    private ArrayList<Integer> foodX = new ArrayList<Integer>();
    private ArrayList<Integer> foodY = new ArrayList<Integer>();
    
    //speed of the snake
    private int snakeDelay = 500;
    
    //Starting coordinates of the snake
    int startX;
    int startY;
    
    //radius of body of snake
    int size = 15;
    
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
        drawSnake();
        /*
        this is the background for the snakes to be drawn on. It has to be a 
        buffered image to show it as animating.
        */
        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void drawSnake() {
        /*
        Pauses the drawing of the previous snake when u click add again, I dont
        know how to keep the previous one going but we'll figure it out.
        */
        if (snakeAnimation != null && snakeAnimation.isRunning()){
            snakeAnimation.stop();
        }
        
        startX = rand.nextInt(imageWidth);
        startY = rand.nextInt(imageHeight);
        //Random number generated initially for direction later
        //8 for cardinal directions
        int direction = rand.nextInt(8);
        int newX;
        int newY;
        
        ActionListener timerDraw = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics2D g2 = img.createGraphics();

                if(firstSpawn == 0) {
                    startX = 350;
                    startY = 350;
                    firstSpawn++;
                } 
                int newX;
                int newY;
                //changes the positioning of the snake, basically
                //it's next moves
                //Use a switch to keep direction consistent while still being random.
                switch (direction) {
                    case 0:
                        //north
                        newX = startX + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        newY = startY + (int) ((-1)*size/2 * Math.random()); 
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
                    default: 
                        newX = startX + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());
                        newY = startY + (int) ((Math.round(Math.random())*2-1) * size/2 * Math.random());  
                        break;
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
                g2.setColor(Color.green);
                g2.fillOval(startX, startY, size, size);

                //temporary color of this snake
                g2.setColor(Color.blue);
                //the base shape of the snake
                g2.fillOval(newX, newY, size, size);

                //New starting point based on current position.
                startX = newX;
                startY = newY;
                
                repaint();
            }
        };
        
        snakeAnimation = new Timer(snakeDelay, timerDraw);
        snakeAnimation.start();
    }

    public void drawBorder(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,imageWidth, 5);
        g.fillRect(0,imageHeight,imageWidth, 5);
        g.fillRect(0,0,5, imageHeight);
        g.fillRect(imageWidth,0,5, imageHeight);
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

    public void drawFood(Graphics g) {
        g.setColor(Color.RED);
        for (int i=0;i<foodX.size();i++){
            g.fillRect(foodX.get(i),foodY.get(i),10,10);
        }
    }
    
    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        super.paintComponent(g);
        drawBorder(g);
        drawFood(g);
        //im ngl i have no idea what this does but seems important
        if (img != null)
            g.drawImage(img, 0, 0, this);
    } 
}
