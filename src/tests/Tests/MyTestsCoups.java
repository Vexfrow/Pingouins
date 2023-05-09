package test.Tests;

import Model.JeuAvance;
import Model.Coup;
import Model.Pingouin;
import Model.Position;

import java.util.ArrayList;

public class MyTestsCoups {
    
    public static void main(String[] args){
        // Initialisation du jeu pour tester les coups
        JeuAvance jeu = new JeuAvance("src/tests/Terrains/terrainFixe.txt");

        // Placement des pingouins (cf MyTestsPingouins.java)
        jeu.placePingouin(0, 0); //1
        jeu.placePingouin(7, 7); //2
        jeu.placePingouin(3, 0); //3
        jeu.placePingouin(4, 2); //4
        jeu.placePingouin(5, 0); //1
        jeu.placePingouin(6, 6); //2
        jeu.placePingouin(0, 6); //3
        jeu.placePingouin(2, 5); //4

        jeu.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");


        Pingouin ping = jeu.getListeJoueur().get(1).getListePingouin().get(0);
        Coup cp = new Coup(7,6,ping,false);
        jeu.joue(cp);

        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne() == 0;
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne() == 0;
        assert jeu.getScore(1) == 0;
        

        // Tests cases accessibles
/*
        Pingouin ping = jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0);
        
        //System.out.println(ping);
        //System.out.println(jeu.getCaseAccessible(0, 0));
        Coup cp = new Coup(-1, 0, ping, false);
        
        ArrayList<Position> listeCasesAccessibles = jeu.getCaseAccessible(ping.getLigne(), ping.getColonne());
        ArrayList<Position> listeCasesAccessiblesAVerifier = new ArrayList<Position>();

        listeCasesAccessiblesAVerifier.add(new Position(0,1));
        listeCasesAccessiblesAVerifier.add(new Position(0,2));
        listeCasesAccessiblesAVerifier.add(new Position(0,3));
        listeCasesAccessiblesAVerifier.add(new Position(0,4));
        listeCasesAccessiblesAVerifier.add(new Position(0,5));
        listeCasesAccessiblesAVerifier.add(new Position(1,1));
        listeCasesAccessiblesAVerifier.add(new Position(1,1));
        listeCasesAccessiblesAVerifier.add(new Position(2,1));
        listeCasesAccessiblesAVerifier.add(new Position(3,3));

        assert listeCasesAccessibles.size() == listeCasesAccessiblesAVerifier.size(): "Pas le meme nombre de cases accessibles";

        boolean caseNonPresent = false;
        String message = "Couple manquant: ";

*/
    }
}
