import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SnakeWindow extends JFrame{
    private int SnakeWindowWidth = 1000;
    private int SnakeWindowHeight = 1000;
    JPanel eastPan, snakePan;
    Snake trialSnake;

    JPanel main;
    GridBagConstraints c;


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
        c.ipadx = 40;
        main.add(gap, c);

        windowMenus();
        initOptions();

        this.add(main);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        //initSnake();
        this.setVisible(true);
    }
    
    /* public void initSnake() {
        snakePan = new JPanel();
        snakePan.setSize(700,700);
        Snake trialSnake = new Snake();
        snakePan.add(trialSnake);
        this.add(snakePan, BorderLayout.WEST);
    } */

    public void windowMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu options = new JMenu("Options");
        menuBar.add(options);
        JMenuItem optionsMenu = new JMenuItem("Display Options");
        options.add(optionsMenu);
        optionsMenu.addActionListener(new ActionListener()
            {
               public void actionPerformed(ActionEvent ac)
               {
                 //Options Menu Code
               }
            });
        this.setJMenuBar(menuBar);   
    }

    public void initOptions() {
        eastPan = new JPanel();
        eastPan.setLayout(new GridLayout(7,1));
        c.gridx = 2;
        c.ipadx = 80;
        c.ipady = 20;

        JLabel mainLabel = new JLabel("CUSTOMIZATIONS:");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 19));

        JCheckBox speedBox = new JCheckBox("Speed");
        JCheckBox sizeBox = new JCheckBox("Size");
        JCheckBox colorBox = new JCheckBox("Color");

        JButton genSnake = new JButton("Generate");
        genSnake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trialSnake.drawSnake();
            }
        });


        eastPan.add(mainLabel);
        eastPan.add(Box.createHorizontalStrut(10));
        eastPan.add(speedBox);
        eastPan.add(sizeBox);
        eastPan.add(colorBox);
        eastPan.add(Box.createHorizontalStrut(5));
        eastPan.add(genSnake);
        main.add(eastPan, c);
    }

    public static void main(String[] args) {
        new SnakeWindow();
    }
}
