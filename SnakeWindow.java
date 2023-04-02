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
    int headNum = 2;
    int tailNum = 2;

    //Color[] possibleColors = {Color.blue, Color.red, Color.black, Color.green, Color.yellow, Color.green, Color.MAGENTA};
    int[] possibleRedColors = {0     ,      255,          0,            0,          255,        34          , 201};
    int[] possibleGreenColors = {   0  ,    0,            0,          255,          255,        135        , 12};
    int[] possibleBlueColors = {255       ,  0,            0,           0,           0,         14          , 201};
    String[] possibleCStrings = {"Blue    ", "Red     ", "black   ", "green   ", "yellow  ", "green   ", "magenta", "gradient"};

    int size = 15;
    int speed = 100;
    int maxLength = 15;

    String[] snakearr = new String[3];
    DefaultListModel model = new DefaultListModel();
    JList snakeBox = new JList(model);

    JScrollPane snakePane = new JScrollPane(snakeBox);

    GridBagLayout overall = new GridBagLayout();
    GridBagLayout options = new GridBagLayout();

    Boolean gradientFlag = false;
    int gradRed;
    int gradGreen;
    int gradBlue;

    ButtonGroup colorButtons = new ButtonGroup();



    public SnakeWindow() {
        trialSnake = new Snake();
        this.setTitle("Hungry Snake Project");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //start of testing GridBag Layout
        main = new JPanel();
        main.setLayout(overall);
        c = new GridBagConstraints();
        c.insets = new Insets(1,1,1,1);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 700;
        c.ipady = 700;
        main.add(trialSnake, c);
        //this.setLayout(new GridLayout(1,2));
        //this.add(trialSnake);
        
        //Trying to create a gap between the customizations and the snake panel
        JPanel gap = new JPanel();
        c.gridy = 1;
        c.gridx = 1;
        c.ipadx = 70;
        main.add(gap, c);

        initDirections();
        initOptions();

        this.add(main);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        //initSnake();
        this.setVisible(true);
    }

    public void initDirections() {
        String title = "HUNGRY SNAKE";


        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        main.add(titleLabel, c);

    }

    public void initOptions() {
        eastPan = new JPanel();
        eastPan.setLayout(options);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel mainLabel = new JLabel("CUSTOMIZATIONS:");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 19));

        JRadioButton rainbowButton = new JRadioButton("Rainbow Effect");
        rainbowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gradRed = (rand.nextInt(14) + 3) * 10;
                gradGreen = (rand.nextInt(14) + 3) * 10;
                gradBlue = (rand.nextInt(14) + 3) * 10;

                if (gradRed == gradGreen && gradRed == gradBlue && gradGreen == gradBlue)
                {
                    while (gradRed == gradGreen)
                        gradRed = (rand.nextInt(17) + 3) * 10;
                }
                gradientFlag = true;
            }
        });

        JRadioButton colorButton = new JRadioButton("Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headNum = rand.nextInt(7);
                tailNum = rand.nextInt(7);

                while (tailNum == headNum){
                    tailNum = rand.nextInt(7);
                }
            }
        });

        JRadioButton invisibleButton = new JRadioButton();
        invisibleButton.setSelected(true);

        colorButtons.add(rainbowButton);
        colorButtons.add(colorButton);
        colorButtons.add(invisibleButton);
        

        JCheckBox sizeBox = new JCheckBox("Size");
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size = rand.nextInt(22)+9;
            }
        });

        JCheckBox lengthBox = new JCheckBox("Length");
        lengthBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxLength = rand.nextInt(11)+10;
            }
        });

        JButton genSnake = new JButton("Generate");
        genSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //trialSnake.drawSnake2(possibleColors[headNum], possibleColors[tailNum], size, speed);

                if (gradientFlag) {
                    trialSnake.drawSnake2(gradRed, gradGreen, gradBlue, gradRed, gradGreen, gradBlue, size, speed, gradientFlag, maxLength, headNum, tailNum);
                    String snakeString = String.format("%-24s%-24s%-24d%-24d%-24d", possibleCStrings[7], possibleCStrings[7], size, speed,maxLength);
                    model.addElement(snakeString);
                }
                else {
                    trialSnake.drawSnake2(possibleRedColors[headNum], possibleGreenColors[headNum], possibleBlueColors[headNum], 
                            possibleRedColors[tailNum], possibleGreenColors[tailNum], possibleBlueColors[tailNum], size, speed, gradientFlag, maxLength, headNum, tailNum);
                    String snakeString = String.format("%-24s%-24s%-24d%-24d%-24d", possibleCStrings[headNum], possibleCStrings[tailNum], size, speed,maxLength);
                    model.addElement(snakeString);
                }
                
                trialSnake.drawSnakes();
                //back to defaults
                invisibleButton.setSelected(true);
                //colorBox.setSelected(false);
                headNum = 2;
                tailNum = 2;
                sizeBox.setSelected(false);
                lengthBox.setSelected(false);
                size = 15;
                maxLength = 15;
                //rainbowBox.setSelected(false);
                speed = 100;
                gradientFlag = false;

            }
        });

        JButton remSnake = new JButton("Remove");
        remSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int byeSnake = snakeBox.getSelectedIndex();
                model.removeElementAt(byeSnake);
                trialSnake.snakeList.get(byeSnake).killSnake();
                trialSnake.cleanSnake(byeSnake);
                trialSnake.snakeList.remove(byeSnake);
            }
        });

        JButton killSnake = new JButton("Stop");
        killSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int byeSnake = snakeBox.getSelectedIndex();
                trialSnake.snakeList.get(byeSnake).killSnake();
            }
        });

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int refreshSnakeChoice = snakeBox.getSelectedIndex();
                int length = trialSnake.snakeList.get(refreshSnakeChoice).getLength();
                int size = trialSnake.snakeList.get(refreshSnakeChoice).getSize();
                int head = trialSnake.snakeList.get(refreshSnakeChoice).getHeadNum();
                int tail = trialSnake.snakeList.get(refreshSnakeChoice).getTailNum();
                String refreshString = String.format("%-24s%-24s%-24d%-24d%-24d", possibleCStrings[head], possibleCStrings[tail], size, speed,length);
                model.setElementAt(refreshString,refreshSnakeChoice);
            }
        });


        String directions = "<html>Directions: <br>";
        directions += "The default snake is all black and a medium size.<br>";
        directions += "To generate a completely random snake of a different color or size.<br>";
        directions += "Check the size, color, or rainbow buttons and click generate to see your snake!<br>";
        directions += "To remove a snake, click on it, and click remove<br>";
        directions += "<br>";
        directions += "Click anywhere in the snake habitat to drop some food and watch what happens!<br>";
        directions += "<br><br><br><html>";

        JLabel directionLabel = new JLabel(directions);
        directionLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        eastPan.add(directionLabel, gbc);

        String labelString = String.format("%-22s%-22s%-22s%-22s%-22s", "Head Color", "Tail Color", "Size", "Speed","Length");
        JLabel listLabel = new JLabel(labelString);

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(mainLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(rainbowButton, gbc);
        //eastPan.add(rainbowBox, gbc);

        gbc.gridy = 3;
        eastPan.add(colorButton, gbc);
       // eastPan.add(colorBox, gbc);

        gbc.gridy = 4;
        eastPan.add(sizeBox, gbc);

        gbc.gridy = 5;
        eastPan.add(lengthBox, gbc);

        gbc.gridy = 6;
        eastPan.add(genSnake, gbc);

        gbc.gridy = 7;
        eastPan.add(listLabel, gbc);
       
        gbc.gridy = 8;
        eastPan.add(snakePane, gbc);

        gbc.gridy = 9;
        eastPan.add(remSnake, gbc);

        gbc.gridy = 10;
        eastPan.add(killSnake, gbc);

        gbc.gridy = 11;
        eastPan.add(refresh,gbc);

        c.gridy = 1;
        c.gridx = 2;
        c.ipadx = 80;
        c.ipady = 50;
        main.add(eastPan, c);
    }

    public static void main(String[] args) {
        new SnakeWindow();
    }
}
