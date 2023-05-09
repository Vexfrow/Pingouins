package Controleur;
import Interface.MenuP;
import Interface.Fenetre;
import Interface.GameBoard;
import Model.Coup;
import Model.Jeu;
import Model.Pingouin;
import Model.Position;
import Vue.AdaptateurSourisPlateau;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import java.awt.*;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private GameBoard plateauJeu;
    private Jeu jeu;

    int phaseJeu;

    boolean selection;
    Position selectionP;

    int info;

    int etat;

    public Controleur(){
        phaseJeu = 1;
        selection = false;
        selectionP = null;
        jeu = null;
        window = null;
        plateauJeu = null;
        etat = BanquiseGraphique.ETAT_PLACEMENTP;
        info = 0;

    }

    public void toggleHelp(){
        if(window.workingPane.actuel instanceof MenuP){
            window.getMenu().activateButton();
        }
        this.window.workingPane.switchBackPane(1);
        this.window.workingPane.toggleBackingPane(1);



    }

    public void togglePause(){
        this.window.workingPane.toggleBackingPane(2);
        this.window.workingPane.switchBackPane(2);

    }

    public void switchSel(){window.switchPanel(2);}

    public void switchMenu(){
        window.getMenu().activateButton();
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
                    if (phaseJeu == 1) {
                        joueCoupPhase1(plateauJeu.getBq().getCoordFromNumber(i));
                    } else {
                        joueCoupPhase2(plateauJeu.getBq().getCoordFromNumber(i));
                        if(selection)
                            info = i;
                        else
                            info = jeu.getJoueur();

                    }

                    plateauJeu.misAJour(jeu, etat, info);
                    break;
                }
            }

        }


    public void setPlateauJeu(GameBoard gb){
        plateauJeu = gb;
        plateauJeu.getBq().addMouseListener(new AdaptateurSourisPlateau(plateauJeu.getBq(), this));
    }

    public void setJeu(Jeu j){
        jeu = j;
    }

    public void setInterface(Fenetre window){
        this.window = window;
    }

    private void joueCoupPhase1(Position p){
        if(jeu.peutPlacer(p.x, p.y))
            jeu.placePingouin(p.x, p.y);
        else
            System.out.println("Peut pas placer ici");

        if(jeu.pingouinTousPlace()){
            phaseJeu = 2;
            etat = BanquiseGraphique.ETAT_SELECTIONP;
        }
    }


    private void joueCoupPhase2(Position p) {
        if(!selection){
            if(jeu.pingouinPresent(p.x, p.y) && jeu.getCase(p.x, p.y).pingouinPresent() == jeu.getJoueur()){
                selection = true;
                selectionP = p;
                etat = BanquiseGraphique.ETAT_CHOIXC;

            }
        }else{
            Pingouin pingouin = new Pingouin(selectionP.x, selectionP.y);
            Coup c = new Coup(p.x, p.y,pingouin,false);
            if(jeu.peutJouer(c)){
                jeu.joue(c);
            }else{
                System.out.println("Coup impossible");
            }
            selection = false;
            etat = BanquiseGraphique.ETAT_SELECTIONP;

            if(jeu.jeuTermine()){
                phaseJeu = 3;
            }
        }

    }

    public int getEtatBackPane(){
        return this.window.workingPane.getEtatofBackPane();
    }


}
