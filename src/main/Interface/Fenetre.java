package Interface;

import javax.swing.*;

import Controleur.Controleur;
import Interface.MenuP;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Fenetre implements Runnable {
    private Controleur c;
    private Menu m;
    private JFrame jf;
    private JPanel menu;
    private SpringLayout l;

    public static void demarrer(Controleur ctrl){
        try {
            SwingUtilities.invokeAndWait(new Fenetre(ctrl));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }


    public Fenetre(Controleur ctrl){
        ctrl.ajouteInterface(this);
        this.c = ctrl;
        menu = new JPanel();
    }
    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jf.setMinimumSize(new Dimension(600, 800));
        menu.add(new MenuP(this.c));
        jf.add(menu);


        jf.setVisible(true);
    }



}