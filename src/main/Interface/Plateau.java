package Interface;

import Model.Jeu;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.swing.*;

public class Plateau implements Runnable {

    CollecteurEvenements controlleur;
    BanquiseGraphique banquise;
    Jeu jeu;


    Plateau(Jeu j, CollecteurEvenements ce){
        controlleur = ce;
        jeu = j;
    }


    public static void demarrer(Jeu j, CollecteurEvenements cEvenements) {
        SwingUtilities.invokeLater(new Plateau(j, cEvenements));
    }


    @Override
    public void run() {
        banquise = new BanquiseGraphique(jeu);
        




    }
}
