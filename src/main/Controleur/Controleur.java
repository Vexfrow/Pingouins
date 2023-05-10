package Controleur;

import Interface.MenuP;
import Interface.Fenetre;
import Interface.GameBoard;
import Joueur.IAJoueur;
import Joueur.IAAleatoire;
import Joueur.IATroisPoissons;
import Model.*;
import Vue.AdaptateurSourisPlateau;
import Vue.CollecteurEvenements;

import java.awt.Shape;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private GameBoard plateauJeu;
    private JeuAvance jeu;

    private ArrayList<IAJoueur> listeIA;

    public Controleur(){
        jeu = null;
        window = null;
        plateauJeu = null;
        listeIA = new ArrayList<>();

    }

    //change est a true si il faut toggle la backingPane
    public void toggleHelp(boolean change){
        if(window.workingPane.actuel instanceof MenuP){
            window.getMenu().activateButton();
        }
        this.window.workingPane.switchBackPane(1);
        if(change){
            this.window.workingPane.toggleBackingPane();
        }


    }

    //change est a true si il faut toggle la backingPane
    public void togglePause(boolean change){
        this.window.workingPane.switchBackPane(2);
        if(change){
            this.window.workingPane.toggleBackingPane();
        }

    }

    public void switchSel(){window.switchPanel(2);}

    public void switchMenu(){
        window.getMenu().activateButton();
        window.workingPane.resetBackPane();
        window.switchPanel(1);
    }

    public void switchGameBoard(){
        window.switchPanel(3);
    }



    @Override
    public void clicSourisPlateau(int coupX, int coupY) {
        for(int i = 0; i < plateauJeu.getBq().getPlateauJeu().size();i++) {
            Shape cell = plateauJeu.getBq().getPlateauJeu().get(i);

            if (cell.contains(coupX, coupY)) {
                if (jeu.getEtat() == JeuAvance.ETAT_PLACEMENTP) {
                    joueCoupPhase1(plateauJeu.getBq().getPosFromNumber(i));
                } else if(jeu.getEtat() == JeuAvance.ETAT_SELECTIONP || jeu.getEtat() == JeuAvance.ETAT_CHOIXC){
                    joueCoupPhase2(plateauJeu.getBq().getPosFromNumber(i));

                }else if (jeu.getEtat() == JeuAvance.ETAT_FINAL){
                    System.out.println("Test état final");
                }

                plateauJeu.misAJour(jeu);
                break;
            }
        }

    }


    public void setPlateauJeu(GameBoard gb){
        plateauJeu = gb;
        plateauJeu.getBq().addMouseListener(new AdaptateurSourisPlateau(plateauJeu.getBq(), this));
    }

    public void setJeu(JeuAvance j){
        jeu = j;
        listeIA.add(new IATroisPoissons(j));
        listeIA.add(new IAAleatoire(j));
    }

    public void setInterface(Fenetre window){
        this.window = window;
    }

    private void joueCoupPhase1(Position p){
        if(jeu.peutPlacer(p.x, p.y))
            jeu.placePingouin(p.x, p.y);
        else
            System.out.println("Peut pas placer ici");
        joueCoup();
    }


    private void joueCoupPhase2(Position p) {
        if(!jeu.getSelection()){
            if(jeu.pingouinPresent(p.x, p.y) && jeu.getCase(p.x, p.y).pingouinPresent() == jeu.getJoueurCourant()){
                jeu.setSelectionP(p);
            }
        }else{
            Pingouin pingouin = new Pingouin(jeu.getSelectionP().x, jeu.getSelectionP().y);
            Coup c = new Coup(p.x, p.y,pingouin,false);
            if(jeu.peutJouer(c)){
                jeu.joue(c);
            }else{
                //Remplacer par pp
                System.out.println("Coup impossible");
            }
            jeu.unsetSelectionP();
        }
        joueCoup();

    }

    public int getEtatBackPane(){
        return this.window.workingPane.getEtatBackPane();
    }

    public void toggleBackingPane(){
        this.window.workingPane.toggleBackingPane();
    }

    public void startGame(){
        jeu.startGame();
        plateauJeu.getBq().misAJour(jeu);
        joueCoup();
    }


    private void joueCoup(){
        if(jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA()){
            IAJoueur jia = listeIA.get(jeu.getJoueurCourant()-1);
            System.out.println(jeu.getEtat());
            if(jeu.getEtat() == JeuAvance.ETAT_PLACEMENTP){
                System.out.println("Tdzdzest");
                Position p = jia.elaborePlacement();
                jeu.placePingouin(p.x,p.y);
            }else{
                Coup c = jia.elaboreCoup();
                System.out.println("Coup = " + c + " ; plateau = " + jeu);
                jeu.joue(c);
            }
            plateauJeu.misAJour(jeu);
//            try {
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//
//            }
            //joueCoup();
        }

    }

}
