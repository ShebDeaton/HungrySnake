import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SnakeWindow extends JFrame{
    private int SnakeWindowWidth = 1000;
    private int SnakeWindowHeight = 1000;
    JPanel eastPan, snakePan;

    public SnakeWindow() {
        Snake trialSnake = new Snake();
        this.setTitle("Hungry Snake Project");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));
        this.add(trialSnake);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        windowMenus();
        initOptions();
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
        eastPan.setBackground(Color.blue);
        this.add(eastPan);

        JCheckBox speedBox = new JCheckBox("Speed");

        eastPan.add(speedBox);
    }

    public static void main(String[] args) {
        new SnakeWindow();
    }
}
