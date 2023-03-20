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

    Color[] possibleColors = {Color.blue, Color.red, Color.black, Color.green, Color.yellow, Color.green, Color.MAGENTA};
    String[] possibleCStrings = {"Blue    ", "Red     ", "black   ", "green   ", "yellow  ", "green   ", "magenta"};

    int size = 15;
    int speed = 100;

    String[] snakearr = new String[3];
    DefaultListModel model = new DefaultListModel();
    JList snakeBox = new JList(model);

    JScrollPane snakePane = new JScrollPane(snakeBox);

    GridBagLayout overall = new GridBagLayout();
    GridBagLayout options = new GridBagLayout();

    


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

        JCheckBox speedBox = new JCheckBox("Speed");
        speedBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               speed = rand.nextInt(0,3) * 50;
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
                String snakeString = String.format("%-24s%-24s%-24d%-24d", possibleCStrings[headNum], possibleCStrings[tailNum], size, speed);
                //model.addElement(possibleCStrings[headNum] + space + possibleCStrings[tailNum] + space + size + space + speed);
                model.addElement(snakeString);
                trialSnake.drawSnakes();
                //back to defaults
                colorBox.setSelected(false);
                headNum = 2;
                tailNum = 2;
                sizeBox.setSelected(false);
                size = 15;
                speedBox.setSelected(false);
                speed = 100;

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

        String directions = "<html>Directions: <br>";
        directions += "The default snake is all black and a medium size.<br>";
        directions += "To generate a completely random snake of a different color or size.<br>";
        directions += "Check the size, color, or both checkboxes and click generate to see your snake!<br>";
        directions += "To remove a snake, click on it, and click remove<br>";
        directions += "<br>";
        directions += "Click anywhere in the snake habitat to drop some food and watch what happens!<br>";
        directions += "<br><br><br><html>";

        JLabel directionLabel = new JLabel(directions);
        directionLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        eastPan.add(directionLabel, gbc);

        String labelString = String.format("%-22s%-22s%-22s%-22s", "Head Color", "Tail Color", "Size", "Speed");
        JLabel listLabel = new JLabel(labelString);

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(mainLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eastPan.add(speedBox, gbc);

        gbc.gridy = 3;
        eastPan.add(sizeBox, gbc);

        gbc.gridy = 4;
        eastPan.add(colorBox, gbc);

        gbc.gridy = 5;
        eastPan.add(genSnake, gbc);

        gbc.gridy = 6;
        eastPan.add(listLabel, gbc);
       
        gbc.gridy = 7;
        eastPan.add(snakePane, gbc);

        gbc.gridy = 8;
        eastPan.add(remSnake, gbc);

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
