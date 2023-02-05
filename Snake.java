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
    
    //speed of the snake
    private int snakeDelay = 50;
    
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
                
                //temporary color of this snake
                g2.setColor(Color.blue);
                
                //changes the positioning of the snake, basically
                //it's next moves
                int newX = startX + (int) (Math.random() * 5);
                int newY = startY + (int) (Math.random() * 5);
                
                //the base shape of the snake
                g2.fillOval(newX, newY, size, size);
                
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
    
    private static void SnakeWindow() {
        Snake trialSnake = new Snake();
        
        JFrame window = new JFrame("trial");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(trialSnake);
        window.setSize(imageWidth, imageHeight);
        window.setVisible(true);
    }
    
    public static void main(String[] args) {
        SnakeWindow();
    }
    
}
