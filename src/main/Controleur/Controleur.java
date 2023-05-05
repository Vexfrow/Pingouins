package Controleur;

import Interface.Fenetre;
import Model.Coup;
import Model.Jeu;
import Model.Pingouin;
import Vue.AdaptateurSourisPlateau;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import java.awt.*;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private BanquiseGraphique plateauJeu;

    private Jeu jeu;

    int phaseJeu;

    boolean selection;
    Point selectionP;

    public Controleur(){
        phaseJeu = 1;
        selection = false;
        selectionP = null;
        jeu = null;
        window = null;
        plateauJeu = null;

    }

    public void toggleHelp(){
        this.window.workingPane.toggleBackingPane();

    }

    public void switchSel(){window.switchPanel(2);}

    public void switchMenu(){
        window.switchPanel(1);
    }

    public void switchGameBoard(){
        window.switchPanel(3);
    }



    @Override
    public void clicSourisPlateau(int coupX, int coupY) {


        if(jeu.estTermine()){
            System.out.println("Test");
        }else{
            for(int i = 0; i < plateauJeu.getPlateauJeu().size();i++) {
                Shape cell = plateauJeu.getPlateauJeu().get(i);

                if (cell.contains(coupX, coupY)) {
                    if (phaseJeu == 1) {
                        joueCoupPhase1(plateauJeu.getCoordFromNumber(i));
                    } else {
                        joueCoupPhase2(plateauJeu.getCoordFromNumber(i));
                    }
                    plateauJeu.misAJour(jeu);
                    break;
                }
            }

        }
    }


    public void setPlateauJeu(BanquiseGraphique bq){
        plateauJeu = bq;
        plateauJeu.addMouseListener(new AdaptateurSourisPlateau(plateauJeu, this));
    }

    public void setJeu(Jeu j){
        jeu = j;
    }

    public void setInterface(Fenetre window){
        this.window = window;
    }

    private void joueCoupPhase1(Point p){
        jeu.placePingouin(p.x, p.y);

        if(jeu.pingouinTousPlace()){
            phaseJeu = 2;
        }
    }


    private void joueCoupPhase2(Point p) {
        if(!selection){
            if(jeu.pingouinPresent(p.x, p.y) && jeu.getCase(p.x, p.y).pingouinPresent() == jeu.getJoueur()){
                selection = true;
                selectionP = p;

                System.out.println("X =" +p.x +"; Y =" + p.y + " ; selectionné");
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
        }

    }

}
