import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class SnakeWindow extends JFrame{
    private int SnakeWindowWidth = 1500;
    private int SnakeWindowHeight = 1000;
    JPanel eastPan, snakePan;
    Snake trialSnake;

    JPanel main;
    GridBagConstraints c;

    Random rand = new Random();
    int headNum = 0;
    int tailNum = 1;

    Color[] possibleColors = {Color.blue, Color.red, Color.black, Color.green, Color.yellow, Color.green, Color.MAGENTA};
    String[] possibleCStrings = {"Blue", "Red", "black", "green", "yellow", "green", "magenta"};

    int size = 15;
    int speed = 100;

    String[] snakearr = new String[3];
    DefaultListModel model = new DefaultListModel();
    JList snakeBox = new JList(model);


    public SnakeWindow() {
        trialSnake = new Snake();
        this.setTitle("Hungry Snake Project");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //start of testing GridBag Layout
        main = new JPanel();
        main.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(1,1,1,1);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 700;
        c.ipady = 700;
        main.add(trialSnake, c);
        //this.setLayout(new GridLayout(1,2));
        //this.add(trialSnake);
        
        //Trying to create a gap between the customizations and the snake panel
        JPanel gap = new JPanel();
        c.gridx = 1;
        c.ipadx = 70;
        main.add(gap, c);

        initOptions();

        this.add(main);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        //initSnake();
        this.setVisible(true);
    }

    public void initOptions() {
        eastPan = new JPanel();
        eastPan.setLayout(new GridLayout(10,1));
        c.gridx = 2;
        c.ipadx = 80;
        c.ipady = 20;

        JLabel mainLabel = new JLabel("CUSTOMIZATIONS:");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 19));

        JCheckBox speedBox = new JCheckBox("Speed");
        speedBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               speed = rand.nextInt(0,5) * 100;
            }
        });

        JCheckBox sizeBox = new JCheckBox("Size");
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size = rand.nextInt(9, 30);
            }
        });

        JCheckBox colorBox = new JCheckBox("Color");
        colorBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headNum = rand.nextInt(7);
                tailNum = rand.nextInt(7);

                while (tailNum == headNum){
                    tailNum = rand.nextInt(7);
                }
            }
        });

        String space = "              ";
        JButton genSnake = new JButton("Generate");
        genSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trialSnake.drawSnake2(possibleColors[headNum], possibleColors[tailNum], size, speed);
                model.addElement(possibleCStrings[headNum] + space + possibleCStrings[tailNum] + space + size + space + speed);
                trialSnake.drawSnakes();
                //back to defaults
                colorBox.setSelected(false);
                headNum = 0;
                tailNum = 1;
                sizeBox.setSelected(false);
                size = 15;
                speedBox.setSelected(false);
                speed = 100;

            }
        });

        JLabel listLabel = new JLabel("Head Color     Tail Color     Size     Speed");

        eastPan.add(mainLabel);
        eastPan.add(Box.createHorizontalStrut(10));
        eastPan.add(speedBox);
        eastPan.add(sizeBox);
        eastPan.add(colorBox);
        eastPan.add(Box.createHorizontalStrut(5));
        eastPan.add(genSnake);
        eastPan.add(Box.createHorizontalStrut(5));
        eastPan.add(listLabel);
        eastPan.add(Box.createHorizontalStrut(5));
        eastPan.add(snakeBox);
        main.add(eastPan, c);
    }

    public static void main(String[] args) {
        new SnakeWindow();
    }
}
