package Interface;

import javax.swing.*;

import Controleur.Controleur;
import Interface.MenuP;
import Model.Jeu;
import Vue.AdaptateurSourisPlateau;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Fenetre implements Runnable {
    private CollecteurEvenements c;
    private MenuP menu;
    private Selection selection;
    public JFrame jf;

    public WorkingPane workingPane;

    BanquiseGraphique bq;


    public static void demarrer(Controleur ctrl){
            SwingUtilities.invokeLater(new Fenetre(ctrl));
    }


    public Fenetre(Controleur ctrl){
        this.c = ctrl;
        c.setInterface(this);

        Jeu j = new Jeu(2);
        bq = new BanquiseGraphique(j);
        bq.addMouseListener(new AdaptateurSourisPlateau(bq, c));
        c.setPlateauJeu(bq);
        c.setJeu(j);
    }

    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setMinimumSize(new Dimension(800, 600));
        this.menu = new MenuP(this.c);
        this.selection = new Selection(this.c);
        //workingPane = new WorkingPane(this.menu);

        jf.add(bq);
        jf.setVisible(true);
        jf.getContentPane().setBackground(Color.CYAN);
    }


    public void switchPanel(int ecran){
        switch(ecran){
            case 1:
                this.workingPane.changePanel(this.menu);
                break;
            case 2:
                this.workingPane.changePanel(this.selection);
                break;
            default:
                System.err.println("Erreur dans l'affichage choisi");
                break;
        }

    }



}