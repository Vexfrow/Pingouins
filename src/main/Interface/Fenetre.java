package Interface;

import Vue.BanquiseGraphique;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Model.Joueur;
import Model.Jeu;

public class Fenetre implements Runnable {
    Menu m;
    private JFrame jf;
    private JPanel menu;

    private SpringLayout l;
    Jeu j;
    public Fenetre(){
        menu = new JPanel();
        j = new Jeu(2);
    }
    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jf.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jf.setMinimumSize(new Dimension(600, 800));
        //menu.add(new BanquiseGraphique(j));
        BanquiseGraphique bq = new BanquiseGraphique(j);
        bq.setVisible(true);
        jf.add(bq);

        jf.getContentPane().setBackground(Color.GRAY);



        jf.setVisible(true);
    }



}