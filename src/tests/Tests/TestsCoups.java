package tests.Tests;

import Model.JeuAvance;
import Model.JeuSauvegarde;
import Model.Coup;
import Model.Pingouin;
import Model.Position;

import java.util.ArrayList;

public class TestsCoups {
    
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
        System.out.println(jeu.getListeJoueur() + "\n");

        // On doit d'abord placer tous les pingouins avant de pouvoir jouer
        Pingouin ping = jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0);
        Coup cp = new Coup(4,3,ping,false);
        jeu.joue(cp);
        assert ping.getLigne() == 4 && ping.getColonne() == 2: "Les pingouins doivent d'abord etre places";
        assert jeu.getJoueurCourant() == 4: "C'est toujours au joueur 4 de jouer";

        // On place donc le dernier pingouin
        jeu.placePingouin(2, 5); //4
        System.out.println("\n" + jeu.getListeJoueur());

        


        /*
        jeu.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");
        System.out.println(jeu.toString() + "\n" + jeu.getListeJoueur() + "\n");

        //System.out.println(jeu.getJoueurCourant());
        jeu.joue(cp);
        jeu.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");*/
        

        /*
        JeuAvance game = new JeuAvance("src/tests/Terrains/terrainNonFixe.txt");
        //System.out.println(game.toString() + "\n" + game.getListeJoueur());

        Pingouin ping = game.getListeJoueur().get(game.getJoueurCourant() - 1).getListePingouin().get(0);
        System.out.println("\n" + ping);
        Coup cp = new Coup(0,3,ping,false);

        System.out.println(game.getJoueurCourant());
        game.joue(cp);
        game.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");*/

        /*
        Pingouin ping = jeu.getListeJoueur().get(1).getListePingouin().get(0);
        Coup cp = new Coup(7,6,ping,false);
        jeu.joue(cp);

        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne() == 0;
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne() == 0;
        assert jeu.getScore(1) == 0;*/
        

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
