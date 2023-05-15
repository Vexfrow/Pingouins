package Controleur;

import Interface.GameConstants;
import Interface.MenuP;
import Interface.Fenetre;
import Interface.GameBoard;
import Joueur.IAJoueur;
import Joueur.IATroisPoissons;
import Model.*;
import Vue.AdaptateurSourisPlateau;
import Vue.CollecteurEvenements;

import java.awt.Shape;
import java.util.ArrayList;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private GameBoard plateauJeu;
    private Jeu jeu;

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
            this.window.getGameBoard().toggleButton();

        }

    }


    public void toggleVictoire(boolean change){
        this.window.workingPane.switchBackPane(5);
        if(change){
            this.window.workingPane.toggleBackingPane();
            this.window.getGameBoard().toggleButton();

        }

    }

    public void toggleSave(){
        this.window.workingPane.switchBackPane(3);
    }

    public void toggelCharge(boolean change){
        if(window.workingPane.actuel instanceof MenuP){
            window.getMenu().activateButton();
        }
        this.window.workingPane.switchBackPane(4);
        if(change){
            this.window.workingPane.toggleBackingPane();
            this.plateauJeu.activateButton();
        }
    }

    public void switchSel(){window.switchPanel(2);}

    public void switchMenu(){
        window.getMenu().activateButton();
        window.workingPane.resetBackPane();
        window.switchPanel(1);
    }

    public void switchGameBoard(){
        System.out.println("Second");
        window.switchPanel(3);
        System.out.println("Second");
    }

    @Override
    public void clicSourisPlateau(int coupX, int coupY) {
        if(jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() ==0){
            System.out.println("Au clic " + jeu.getEtat());
            for(int i = 0; i < plateauJeu.getBq().getPlateauJeu().size();i++) {
                Shape cell = plateauJeu.getBq().getPlateauJeu().get(i);
                if (cell.contains(coupX, coupY)) {
                    if (jeu.getEtat() == Jeu.ETAT_PLACEMENTP) {

                        joueCoupPhase1(plateauJeu.getBq().getPosFromNumber(i));
                    } else if(jeu.getEtat() == Jeu.ETAT_SELECTIONP || jeu.getEtat() == Jeu.ETAT_CHOIXC){
                        joueCoupPhase2(plateauJeu.getBq().getPosFromNumber(i));
                    }
                    plateauJeu.misAJour(jeu);
                    break;
                }
            }
        }
    }


    public void setPlateauJeu(GameBoard gb){
        plateauJeu = gb;
        plateauJeu.getBq().addMouseListener(new AdaptateurSourisPlateau(plateauJeu.getBq(), this));
    }

    public void setJeu(Jeu j){
        jeu = j;
        listeIA.add(new IATroisPoissons(j));
        listeIA.add(new IATroisPoissons(j));
    }

    public void setJeu(Jeu j, ArrayList<IAJoueur> ar){
        this.jeu = j;
        //setPlateauJeu(new GameBoard(this.jeu, this));
        plateauJeu.misAJour(jeu);
        listeIA = ar;
        this.window.setGameBoard(plateauJeu);
        System.out.println("first");
        joueCoup();


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

            if(jeu.getEtat() != Jeu.ETAT_FINAL)
                jeu.unsetSelectionP();

        }
        joueCoup();

    }

    public int getEtatBackPane(){
        return this.window.workingPane.getEtatBackPane();
    }

    public void newGame(Jeu j, ArrayList<IAJoueur> liste, ArrayList<Joueur> arJ) {
        jeu = j;
        plateauJeu = new GameBoard(jeu, this);
        listeIA = liste;
        this.window.setGameBoard(plateauJeu);
    }

    public void startGame(){
        jeu.startGame();
        plateauJeu.getBq().misAJour(jeu);
        joueCoup();
    }

    private void joueCoup(){

        if(jeu.getEtat()!=Jeu.ETAT_FINAL && jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() !=0) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (jeu.getEtat() != Jeu.ETAT_FINAL && jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).estIA() !=0) {
                        IAJoueur jia = listeIA.get(jeu.getJoueurCourant() - 1);
                        System.out.println("Etat lors du coup :" + jeu.getEtat());
                        System.out.println("Nombre d'element dans la liste = "+ listeIA.size());
                        if (jeu.getEtat() == Jeu.ETAT_PLACEMENTP) {
                            Position p = jia.elaborePlacement();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ignored) {

                            }
                            jeu.placePingouin(p.x, p.y);
                        } else {
                            Coup c = jia.elaboreCoup();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ignored) {

                            }
                            if (c != null)
                                jeu.joue(c);
                        }
                        plateauJeu.misAJour(jeu);
                        run();
                    }
                }
            });
            t.start();
        }
    }

    public void save(String s){
        plateauJeu.getBq().sauvegardeBanquise(s);
        jeu.sauvegarder(GameConstants.DOSSIER_SAVE + s + ".txt");
    }

    public Jeu getJeu(){
        return jeu;
    }
}
