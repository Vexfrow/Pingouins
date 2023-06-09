package Interface;

import javax.swing.*;

import Controleur.Controleur;
import Interface.GameBoard.GameBoard;
import Model.Jeu;
import Model.Joueur;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

public class Fenetre implements Runnable {
    private CollecteurEvenements c;
    private MenuP menu;
    private Selection selection;
    private GameBoard gameBoard;
    public JFrame jf;

    public WorkingPane workingPane;

    Jeu jeu;


    public Fenetre(Controleur ctrl){
        this.c = ctrl;
        c.setInterface(this);

        this.menu = new MenuP(this.c);
        this.selection = new Selection(this.c);

        ArrayList<Joueur> ar = new ArrayList<>();
        ar.add(new Joueur(1,0,0,0));
        ar.add(new Joueur(2,0,0,3));
        jeu = new Jeu(ar);
        //jeu = new JeuAvance(2);
        this.gameBoard = new GameBoard(jeu, c);



    }

    public void run(){
        jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setMinimumSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.7), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.8)));
        workingPane = new WorkingPane(this.menu, this.c);
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

                break;
            default:
                System.err.println("Erreur dans l'affichage choisi");
                break;
        }

    }

    public MenuP getMenu(){
        return this.menu;
    }

    public GameBoard getGameBoard(){
        return this.gameBoard;
    }

    public void setGameBoard(GameBoard gb){
        gameBoard = gb;
    }

    public void close(){
        jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
    }



}