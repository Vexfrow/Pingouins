package Interface;

import javax.swing.*;
import java.awt.*;

public class Fenetre implements Runnable {
    Menu m;
    private JFrame jf;
    private JPanel menu;
    private SpringLayout l;
    public Fenetre(){
        menu = new JPanel();
    }
    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jf.setMinimumSize(new Dimension(600, 800));
        menu.add(new Menu());
        jf.add(menu);


        jf.setVisible(true);
    }



}