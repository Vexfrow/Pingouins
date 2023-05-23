package Controleur;

import Interface.GameConstants;
import Interface.MenuP;
import Interface.Fenetre;
import Interface.GameBoard.GameBoard;
import Joueur.*;
import Model.*;
import Vue.AdaptateurSourisPlateau;
import Vue.CollecteurEvenements;

import java.awt.Shape;
import java.util.ArrayList;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private GameBoard plateauJeu;
    private Jeu jeu;

    private AdaptateurSourisPlateau cliqueBq;

    private volatile boolean threadActif;

    private ArrayList<IAJoueur> listeIA;

    public Controleur(){
        jeu = null;
        window = null;
        plateauJeu = null;
        listeIA = new ArrayList<>();
        threadActif = true;

        cliqueBq = null;

    }

    //change est a true si il faut toggle la backingPane
    public void toggleHelp(boolean change){
        if(window.workingPane.actuel instanceof MenuP){
            window.getMenu().activateButton();
        }
        this.window.workingPane.switchBackPane(1);
        if(change){
            this.window.getMenu().setOpaque(!this.window.getMenu().isOpaque());
            this.window.workingPane.toggleBackingPane();
        }


    }

    //change est a true si il faut toggle la backingPane
    public void togglePause(boolean change){
        this.window.workingPane.switchBackPane(2);
        if(change){
            if(threadActif)
                threadActif = false;
            else{
                threadActif = true;
                joueCoup();
            }
            this.window.workingPane.toggleBackingPane();
            this.window.getGameBoard().toggleButton();
            toggleClique();
        }

    }


    public void toggleVictoire(boolean change){
        this.window.workingPane.switchBackPane(5);
        if(change){
            this.window.workingPane.toggleBackingPane();
            this.window.getGameBoard().toggleButton();
            toggleClique();

        }

    }

    public void toggleSave(){
        this.window.workingPane.switchBackPane(3);
    }

    public void toggelCharge(boolean change){
        if(window.workingPane.actuel instanceof MenuP){
            window.getMenu().activateButton();
            this.window.getMenu().setOpaque(!this.window.getMenu().isOpaque());
        }
        this.window.workingPane.switchBackPane(4);
        if(change){
            this.window.workingPane.toggleBackingPane();
            this.plateauJeu.activateButton();
            toggleClique();
        }
    }

    public void switchSel(){window.switchPanel(2);}

    public void switchMenu(){
        window.getMenu().activateButton();
        window.workingPane.resetBackPane();
        window.switchPanel(1);
    }

    public void switchGameBoard(){  window.switchPanel(3); }

    @Override
    public void clicSourisPlateau(int coupX, int coupY) {
        System.out.println("Clique !");
        if(jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() == 0){
            for(int i = 0; i < plateauJeu.getBq().getPlateauJeu().size();i++) {
                Shape cell = plateauJeu.getBq().getPlateauJeu().get(i);
                if (cell.contains(coupX, coupY)) {
                    if (jeu.getEtat() == Jeu.ETAT_PLACEMENTP)
                        joueCoupPhase1(plateauJeu.getBq().getPosFromNumber(i));
                    else if(jeu.getEtat() == Jeu.ETAT_SELECTIONP || jeu.getEtat() == Jeu.ETAT_CHOIXC)
                        joueCoupPhase2(plateauJeu.getBq().getPosFromNumber(i));
                    plateauJeu.misAJour(jeu);
                    break;
                }
            }
        }
    }

    public void setPlateauJeu(GameBoard gb){
        plateauJeu = gb;
        cliqueBq = new AdaptateurSourisPlateau(plateauJeu.getBq(), this);
        toggleClique();
    }

    private void toggleClique(){
        if(plateauJeu.getBq().getMouseListeners().length == 0) {
            plateauJeu.getBq().addMouseListener(cliqueBq);
            //plateauJeu.getBq().addMouseMotionListener(cliqueBq);
        }
        else {
            plateauJeu.getBq().removeMouseListener(cliqueBq);
            //plateauJeu.getBq().removeMouseMotionListener(cliqueBq);
        }
    }

    public void activateGameBoard(){
        if(plateauJeu.getBq().getMouseListeners().length == 0)
            plateauJeu.getBq().addMouseListener(cliqueBq);
    }


    public void setJeu(Jeu j, ArrayList<IAJoueur> ar){
        this.jeu = j;
        plateauJeu = new GameBoard(jeu, this);
        listeIA = ar;
        threadActif = true;
        this.window.setGameBoard(plateauJeu);
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
        threadActif = true;
        this.window.setGameBoard(plateauJeu);
    }

    public void startGame(){
        jeu.startGame();
        plateauJeu.getBq().misAJour(jeu);
      // ?????  threadActif = true;
        joueCoup();
    }

    private void joueCoup(){

        if(jeu.getEtat()!=Jeu.ETAT_FINAL && jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() !=0) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (jeu.getEtat() != Jeu.ETAT_FINAL && jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).estIA() !=0 && threadActif) {
                        IAJoueur jia = listeIA.get(jeu.getJoueurCourant() - 1);
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

    public void replay(){

        ArrayList<Joueur> arJV = jeu.getListeJoueur();
        ArrayList<Joueur> arJ = new ArrayList<>();
        for(int i = 0; i < arJV.size(); i++){
            arJ.add(new Joueur(i+1, 0 ,0, arJV.get(i).estIA()));
        }
        jeu = new Jeu(arJ);
        ArrayList<IAJoueur> arIAV = listeIA;
        ArrayList<IAJoueur> arIA = new ArrayList<>();
        for(int i = 0; i< arIAV.size(); i++){
            IAJoueur ia = null;
            if(arIAV.get(i) instanceof IAFacile){
                ia = new IAFacile(jeu);
            }else if(arIAV.get(i) instanceof IAMoyen){
                ia = new IAMoyen(jeu);
            }else if(arIAV.get(i) instanceof IADifficile){
                ia = new IADifficile(jeu);
            }else if(arIAV.get(i) instanceof IAExpert){
                ia = new IAExpert(jeu);
            }
            arIA.add(ia);
        }
        plateauJeu = new GameBoard(jeu, this);
        listeIA = arIA;

        this.window.setGameBoard(plateauJeu);
        System.out.println(arJ);
        switchGameBoard();
    }

    public void close(){
        this.window.close();
    }
}
