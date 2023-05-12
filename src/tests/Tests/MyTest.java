package tests.Tests;

/* Programme simple pour tester/deboguer les differents bugs rencontres lors du jeu pour differentes configurations */

import Model.Jeu;

public class MyTest {

    public static void main(String[] args){
        /*
        Jeu game = new Jeu(3);
        game.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");
        */

        Jeu jeu = new Jeu("src/tests/Terrains/terrainTest.txt");
        System.out.println(jeu.toString());
        System.out.println(jeu.getListeJoueur());

        jeu.placePingouin(7, 6); //1
        jeu.placePingouin(7, 5); //2
        jeu.placePingouin(6, 5); //3
        jeu.placePingouin(6, 6); //1
        System.out.println("\nIci"+jeu.getCase(6, 5).pingouinPresent());
        jeu.placePingouin(5, 6); //2
        jeu.placePingouin(5, 5); //3
        jeu.placePingouin(6, 4); //1
        jeu.placePingouin(4, 5); //2
        System.out.println("\nLa"+jeu.getCase(6, 5).pingouinPresent());

        jeu.placePingouin(4, 4); //3


        System.out.println(jeu.getListeJoueur());
        System.out.println("\n"+jeu.getListeCoupsJoues());
        System.out.println("\n"+jeu.getCase(6, 5).getNbPoissons());
        System.out.println("\n"+jeu.getCase(6, 5).estMange());
        System.out.println("\n"+jeu.getCase(6, 5).pingouinPresent());
    }
}
