package Controleur;

import Interface.Fenetre;
import Model.Coup;
import Model.Jeu;
import Model.Pingouin;
import Vue.AdaptateurSourisPlateau;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import java.awt.Shape;

public class Controleur implements CollecteurEvenements {

    private Fenetre window;
    private BanquiseGraphique plateauJeu;

    private Jeu jeu;

    int phaseJeu;

    boolean selection;
    int selectionPX;
    int selectionPY;

    int tourJoueur;

    public Controleur(){

    }

    public void toggleHelp(){
        //this.window.workingPane.toggleBackingPane();

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

        }
        int k = 0;
        int l = 0;
        for(int i = 0 ; i < 60 ; i++){
            Shape cell = plateauJeu.getPlateauJeu().get(i);
            if(cell.contains(coupX, coupY)){
                if(phaseJeu == 1){
                    joueCoupPhase1(k,l);
                }else{
                    joueCoupPhase2(k,l);
                }
                plateauJeu.misAJour(jeu);
                break;
            }

            l++;

            if (k % 2 == 0) {
                if (l >= 7) {
                    k = k + 1;
                    l = 0;
                }
            } else {
                if (l >= 8) {
                    k = k + 1;
                    l = 0;
                }
            }
        }
    }


    public void setPlateauJeu(BanquiseGraphique bq){
        plateauJeu = bq;
    }

    public void setJeu(Jeu j){
        jeu = j;
    }

    public void setInterface(Fenetre window){
        this.window = window;
    }

    private void joueCoupPhase1(int i, int j){
        jeu.placePingouin(i, j);
    }


    private void joueCoupPhase2(int i, int j) {
        if(!selection){
            if(jeu.pingouinPresent(i,j) && jeu.getCase(i,j).pingouinPresent() == tourJoueur){
                selection = true;
                selectionPX = i;
                selectionPY = j;
                System.out.println("X =" +i +"; Y =" + j + " ; selectionné");
            }
        }else{
            Pingouin p = new Pingouin(selectionPX,selectionPY);
            Coup c = new Coup(i,j,p,false);
            if(jeu.peutJouer(c)){
                jeu.joue(c);
                tourJoueur = jeu.getJoueur();
            }else{
                System.out.println("Coup impossible");
            }
            selection = false;

        }

    }

    public void startGame(){
        phaseJeu = 1;
        plateauJeu.addMouseListener(new AdaptateurSourisPlateau(plateauJeu, this));
//        while(!jeu.pingouinTousPlace()){
//            plateauJeu.addMouseListener(new AdaptateurSourisPlateau(plateauJeu, this));
//        }
//        phaseJeu = 2;
//        while(true){
//
//        }

    }
}
