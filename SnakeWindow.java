import javax.swing.*;
import java.awt.event.*;

public class SnakeWindow extends JFrame{
    private int SnakeWindowWidth = 1000;
    private int SnakeWindowHeight = 1000;

    public SnakeWindow() {
        Snake trialSnake = new Snake();
        this.setTitle("Hungry Snake Project");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(trialSnake);
        this.setSize(SnakeWindowWidth,SnakeWindowHeight);
        windowMenus();
        this.setVisible(true);
    }
    
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

    public static void main(String[] args) {
        new SnakeWindow();
    }
}
