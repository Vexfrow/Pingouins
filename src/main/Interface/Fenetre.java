package Interface;

import javax.swing.*;

import Controleur.Controleur;
import Interface.MenuP;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Fenetre implements Runnable {
    private Controleur c;
    private MenuP m;
    private Selection sel;
    public JFrame jf;

    public WorkingPane workingPane;


    public static void demarrer(Controleur ctrl){
            SwingUtilities.invokeLater(new Fenetre(ctrl));

    }


    public Fenetre(Controleur ctrl){
        ctrl.ajouteInterface(this);
        this.c = ctrl;

    }

    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setMinimumSize(new Dimension(600, 800));
        m = new MenuP(this.c);
        sel = new Selection();
        workingPane = new WorkingPane(m, sel);

        jf.add(workingPane);
        jf.setVisible(true);
    }



}