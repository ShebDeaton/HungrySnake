import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.lang.*;
import java.util.Random;

public class Snake extends JPanel{
    private static int imageWidth = 700;
    private static int imageHeight = 700;
    private BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    private Timer snakeAnimation;
    private Random rand = new Random();
    //Tail Stuff
    private int tailDuration = 0;
    private static int tailMax = 5;
    private int[] segmentX = new int[tailMax+1];
    private int[] segmentY = new int[tailMax+1];

    
    //speed of the snake
    private int snakeDelay = 500;
    
    //Starting coordinates of the snake
    int startX;
    int startY;
    
    //radius of body of snake
    int size = 10;
    
    public Snake()
    {  
        /*
        this button would be like the add button i just needed
        it to test if this works for now
        */
        JButton addSnake = new JButton("Add");
        addSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tailDuration = 0;
                drawSnake();
            }
        });
        this.add(addSnake);
        
        /*
        this is the background for the snakes to be drawn on. It has to be a 
        buffered image to show it as animating.
        */
        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }
    
    private void drawSnake() {
        /*
        Pauses the drawing of the previous snake when u click add again, I dont
        know how to keep the previous one going but we'll figure it out.
        */
        if (snakeAnimation != null && snakeAnimation.isRunning()){
            snakeAnimation.stop();
        }
        
        startX = rand.nextInt(700);
        startY = rand.nextInt(700);
        
        ActionListener timerDraw = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics2D g2 = img.createGraphics();
                //changes the positioning of the snake, basically
                //it's next moves
                int newX = startX + (int) ((Math.round(Math.random())*2-1) * 5);
                int newY = startY + (int) ((Math.round(Math.random())*2-1) * 5);

                //Keeps track of Tail Segments.
                if(tailDuration == 0) {
                    segmentX[tailDuration] = startX;
                    segmentY[tailDuration] = startY;
                    tailDuration++;
                }
                else if(tailDuration < tailMax) {
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
    
    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        
        //im ngl i have no idea what this does but seems important
        if (img != null)
            g.drawImage(img, 0, 0, this);
    } 
}
