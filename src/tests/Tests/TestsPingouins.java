package tests.Tests;

import Model.*;

/* Programme test pour le placement des pingouins */

public class TestsPingouins {
    
    public static void main(String[] args){
        // Test placement pingouins
        Jeu jeu = new Jeu("src/tests/Terrains/terrainFixe.txt");
        System.out.println(jeu.toString());

        // On verifie qu'on ne peut pas placer de pingouins n'importe ou
        System.out.println("========== On verifie qu'on ne peut pas placer de pingouins n'importe ou ==========");
        assert jeu.getListeJoueur().size() == 4: "Nombre de joueurs differents de 4";
        assert jeu.placePingouin(0, 7) == false: "Placement en (0,7) impossible";
        assert jeu.placePingouin(0, -1) == false: "Placement en (0,7) impossible";
        assert jeu.placePingouin(7, 0) == false: "Placement en (0,7) impossible";
        assert jeu.placePingouin(1, 0) == false: "Placement en (0,7) impossible";
        System.out.println("========== On verifie qu'on ne peut pas placer de pingouins n'importe ou -- OK ==========\n");

        // On verifie qu'on peut bien placer des pingouins sur une case
        System.out.println("========== On verifie qu'on peut bien placer des pingouins sur une case ==========");
        assert jeu.placePingouin(0, 0) == true: "Placement en (0,0)"; //1
        assert jeu.placePingouin(7, 7) == true: "Placement en (7,7)"; //2
        assert jeu.placePingouin(3, 0) == true: "Placement en (3,0)"; //3
        assert jeu.placePingouin(4, 2) == true: "Placement en (4,2)"; //4
        assert jeu.placePingouin(7, 7) == false: "Pingouin adverse deja en (7,7)";
        assert jeu.placePingouin(5, 0) == true: "Placement en (5,0)"; //1
        assert jeu.placePingouin(7, 7) == false: "Pingouin meme equipe deja en (7,7)";
        assert jeu.placePingouin(6, 6) == true: "Placement en (6,6)"; //2
        assert jeu.placePingouin(0, 6) == true: "Placement en (0,6)"; //3
        assert jeu.placePingouin(2, 5) == true: "Placement en (2,5)"; //4
        System.out.println("========== On verifie qu'on peut bien placer des pingouins sur une case -- OK ==========\n");


        // On verifie qu'on ne peut pas rajouter de pingouins
        System.out.println("========== On verifie qu'on ne peut pas rajouter de pingouins ==========");
        assert jeu.placePingouin(7, 7) == false: "Placement en (7,2)";
        assert jeu.placePingouin(3, 4) == false: "Placement en (3,4)";
        System.out.println("========== On verifie qu'on ne peut pas rajouter de pingouins -- OK ==========\n");


        // On verifie que tous les pingouins ont ete places
        System.out.println("========== On verifie que tous les pingouins ont ete places ==========");
        assert jeu.pingouinTousPlace() == true: "Pingouins tous places";
        assert jeu.placePingouin(7, 2) == false: "Impossible de placer plus de pingouins";
        jeu.placePingouin(3, 4);
        System.out.println("========== On verifie que tous les pingouins ont ete places -- OK ==========\n");


        System.out.println("\n"+jeu.getListeJoueur());
        
    }
}
