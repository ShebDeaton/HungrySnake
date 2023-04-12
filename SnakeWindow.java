import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class SnakeWindow extends JFrame{
    //variables for how big the window will be and setup
    private int SnakeWindowWidth = 1500;
    private int SnakeWindowHeight = 1000;
    JPanel eastPan, snakePan;

    //the snake itself from the Snake class
    Snake trialSnake;

    Random rand = new Random();
    
    //Color[] possibleColors = {Color.blue, Color.red, Color.black, Color.green, Color.yellow, Color.green, Color.MAGENTA};
    int[] possibleRedColors = {0     ,      255,          0,            0,          255,        34          , 201};
    int[] possibleGreenColors = {   0  ,    0,            0,          255,          255,        135        , 12};
    int[] possibleBlueColors = {255       ,  0,            0,           0,           0,         14          , 201};
    String[] possibleCStrings = {"Blue    ", "Red     ", "black   ", "green   ", "yellow  ", "green   ", "magenta", "gradient"};

    //some default parameters for the snake
    int size = 15;
    int maxLength = 15;
    int headNum = 2;
    int tailNum = 2;
    String snakePulse = "Moving";

    String[] snakearr = new String[3];

    //how the snakes' attributes are displayed
    DefaultListModel model = new DefaultListModel();
    JList snakeBox = new JList(model);
    JScrollPane snakePane = new JScrollPane(snakeBox);

    //layout of the whole joptionpane
    JPanel main;
    GridBagConstraints c;
    GridBagLayout overall = new GridBagLayout();
    GridBagLayout options = new GridBagLayout();
    ButtonGroup colorButtons = new ButtonGroup();

    //variables to make the rainbow snake
    Boolean gradientFlag = false;
    int gradRed;
    int gradGreen;
    int gradBlue;

    //just a list of possible hardcoded snake names
    String[] snakeNames = {"Snakespeare",
        "Hisslehoff",
        "Snakers",
        "Hissy",
        "Slithers",
        "Scaley",
        "Cathiss",
        "Mr. Balboa",
        "Squeezer",
        "Hisstopher",
        "Parhiss",
        "Hissinger",
        "Hisschel",
        "Fangis",
        "Sir Pent",
        "Strangles",
        "Noodles",
        "Biscuit",
        "Bob the Hisser"};


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
        
        //Trying to create a gap between the customizations and the snake panel
        JPanel gap = new JPanel();
        c.gridy = 1;
        c.gridx = 1;
        c.ipadx = 70;
        main.add(gap, c);

        //initializes the directions and buttons part of the pane
        initDirections();
        initOptions();

        this.add(main);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        this.setVisible(true);
    }

    //The main title of the game displayed on top
    public void initDirections() {
        String title = "HUNGRY SNAKE";

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        //positioning using GridBagLayout
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        main.add(titleLabel, c);

    }

    /*
     * Big function that creates all the buttons and directions that are on the 
     * right side of the screen. Each button has an actionlistener associated with it
     * in order for it to make changes in the game itself
     */
    public void initOptions() {
        eastPan = new JPanel();
        eastPan.setLayout(options);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel mainLabel = new JLabel("CUSTOMIZATIONS:");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 19));

        //The button to allow the snake to enter "rainbow" mode which just means
        // the color changes around
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

        // The button that will randomly generate a color for the head and tail
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

        //this button is so that neither rainbow or color is selected by default
        JRadioButton invisibleButton = new JRadioButton();
        invisibleButton.setSelected(true);

        colorButtons.add(rainbowButton);
        colorButtons.add(colorButton);
        colorButtons.add(invisibleButton);
        
        //This button is to randomly generate the size of the snake from 9-31
        JCheckBox sizeBox = new JCheckBox("Size");
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size = rand.nextInt(22)+9;
            }
        });

        //This will randomly generate the starting length of the snake from 10 to 21
        JCheckBox lengthBox = new JCheckBox("Length");
        lengthBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxLength = rand.nextInt(11)+10;
            }
        });

        //this button generates the snake using the parameters that were specified with the other buttons
        JButton genSnake = new JButton("Generate");
        genSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if statement checks if it is possible to generate more snakes, if so it will assign it a name
                if (trialSnake.snakeList.size() < snakeNames.length) {

                    int nameNum = rand.nextInt(snakeNames.length);
                    for (int i = 0; i < trialSnake.snakeList.size(); i ++){
                        if (nameNum == trialSnake.snakeList.get(i).getNameNum()) {
                            nameNum = rand.nextInt(snakeNames.length);
                            i = 0;
                        }
                    }

                    //this if statement checks if it will be a rainbow snake or not which will depend on what color values it receives
                    if (gradientFlag) {
                        trialSnake.drawSnake2(gradRed, gradGreen, gradBlue, gradRed, gradGreen, gradBlue, size, gradientFlag, maxLength, nameNum, headNum, tailNum);
                        String snakeString = String.format("%-26s%-26s%-26s%-26d%-26d%-26s", snakeNames[nameNum], possibleCStrings[7], possibleCStrings[7], size, maxLength, snakePulse);
                        model.addElement(snakeString);
                    }
                    else {
                        trialSnake.drawSnake2(possibleRedColors[headNum], possibleGreenColors[headNum], possibleBlueColors[headNum], 
                                possibleRedColors[tailNum], possibleGreenColors[tailNum], possibleBlueColors[tailNum], size, gradientFlag, maxLength, nameNum, headNum, tailNum);
                        String snakeString = String.format("%-26s%-26s%-26s%-26d%-26d%-26s", snakeNames[nameNum], possibleCStrings[headNum], possibleCStrings[tailNum], size,maxLength, snakePulse);
                        model.addElement(snakeString);
                    }
                }

                //error message if the user tries to generate more snakes than there are names
                else {
                    JOptionPane.showMessageDialog(null, "Maximum of " + snakeNames.length + " Snakes to be generated at once. Please remove one in order to generate another",
                                                                    "Error: Too Many Snakes", 1);
                }
                //makes the snake 
                trialSnake.drawSnakes();

                //back to default parameters
                invisibleButton.setSelected(true);
                headNum = 2;
                tailNum = 2;
                sizeBox.setSelected(false);
                lengthBox.setSelected(false);
                size = 15;
                maxLength = 15;
                gradientFlag = false;

            }
        });

        //This button will remove and erase the snake from the window
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

        //this button will simply stop the snake in its track, it can be revived with the food option
        JButton killSnake = new JButton("Stop");
        killSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int byeSnake = snakeBox.getSelectedIndex();
                trialSnake.snakeList.get(byeSnake).killSnake();
            }
        });

        //the refresh button refreshes the entry in the JListBox if changes are made such as to length
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int refreshSnakeChoice = snakeBox.getSelectedIndex();
                int length = trialSnake.snakeList.get(refreshSnakeChoice).getLength();
                int size = trialSnake.snakeList.get(refreshSnakeChoice).getSize();
                int head = trialSnake.snakeList.get(refreshSnakeChoice).getHeadNum();
                int tail = trialSnake.snakeList.get(refreshSnakeChoice).getTailNum();
                int nameNum = trialSnake.snakeList.get(refreshSnakeChoice).getNameNum();
                boolean pulse = trialSnake.snakeList.get(refreshSnakeChoice).checkPulse();
                if (pulse == true){
                    snakePulse = "Moving";
                }
                else {
                    snakePulse = "Stopped";
                }
                String refreshString = String.format("%-26s%-26s%-26s%-26d%-26d%-26s", snakeNames[nameNum], possibleCStrings[head], possibleCStrings[tail], size,length,snakePulse);
                model.setElementAt(refreshString,refreshSnakeChoice);
            }
        });

        //Directions displayed on top for user 
        String directions = "<html>Directions: <br>";
        directions += "The default snake is all black and a medium size.<br>";
        directions += "To generate a completely random snake of a different color or size.<br>";
        directions += "Check the size, color, or rainbow buttons and click generate to see your snake!<br>";
        directions += "To remove a snake, click on it, and click remove<br>";
        directions += "<br>";
        directions += "Click anywhere in the snake habitat to drop some food and watch what happens!<br>";
        directions += "<br><br><br><html>";

        //adds direction label to joption 
        JLabel directionLabel = new JLabel(directions);
        directionLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        eastPan.add(directionLabel, gbc);

        //adds the CUSTOMIZATIONS label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(mainLabel, gbc);

        //adds the Rainbow Button
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(rainbowButton, gbc);

        //adds the color button
        gbc.gridy = 3;
        eastPan.add(colorButton, gbc);

        //adds the size checkbox
        gbc.gridy = 4;
        eastPan.add(sizeBox, gbc);

        //adds the length checkbox
        gbc.gridy = 5;
        eastPan.add(lengthBox, gbc);

        //adds the generate button
        gbc.gridy = 6;
        eastPan.add(genSnake, gbc);

        //adds the label for the top of the JListBox
        String labelString = String.format("%-26s%-22s%-22s%-22s%-22s%-26s", "Name", "Head Color", "Tail Color", "Size","Length","Status");
        JLabel listLabel = new JLabel(labelString);
        gbc.gridy = 7;
        eastPan.add(listLabel, gbc);
       
        //adds the JListBox
        gbc.gridy = 8;
        eastPan.add(snakePane, gbc);

        //adds the remove button
        gbc.gridy = 9;
        eastPan.add(remSnake, gbc);

        //adds the kill button
        gbc.gridy = 10;
        eastPan.add(killSnake, gbc);

        //adds the refresh button
        gbc.gridy = 11;
        eastPan.add(refresh,gbc);

        //adds the eastpan to the main panel
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
