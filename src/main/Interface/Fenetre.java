package Interface;

import javax.swing.*;

import Controleur.Controleur;
import Interface.MenuP;
import Model.Jeu;
import Model.JeuAvance;
import Vue.AdaptateurSourisPlateau;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Fenetre implements Runnable {
    private CollecteurEvenements c;
    private MenuP menu;
    private Selection selection;
    private GameBoard gameBoard;
    public JFrame jf;

    public WorkingPane workingPane;

    JeuAvance jeu;


    public static void demarrer(Controleur ctrl){
            SwingUtilities.invokeLater(new Fenetre(ctrl));
    }


    public Fenetre(Controleur ctrl){
        this.c = ctrl;
        c.setInterface(this);

        this.menu = new MenuP(this.c);
        this.selection = new Selection(this.c);

        jeu = new JeuAvance(2);
        this.gameBoard = new GameBoard(jeu, c);


    }

    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setMinimumSize(new Dimension(800, 600));

        workingPane = new WorkingPane(this.menu);
        jf.add(workingPane);
        jf.setVisible(true);
    }


    public void switchPanel(int ecran){
        switch(ecran){
            case 1:
                this.workingPane.changePanel(this.menu);
                break;
            case 2:
                this.workingPane.changePanel(this.selection);
                this.selection.changeIcon();
                break;
            case 3:
                this.workingPane.changePanel(this.gameBoard);

            default:
                System.err.println("Erreur dans l'affichage choisi");
                break;
        }

    }



}