package test.Tests;

/* Tests sur un terrain fixe */

import Model.Jeu;
import Model.Cases;
import Model.JeuAvance;
import Model.Pingouin;
import Model.Position;

import java.util.Arrays;
import java.util.ArrayList;

public class MyTest {

    public static void main(String[] args){
        /*
        JeuAvance game = new JeuAvance(4);
        System.out.println(game.toString() + "\n");
        //game.sauvegarder(null);

        assert game.getNbCases() == 60 : "Nombre de cases different de 60";

        Cases [][] gameClone = game.clonerTerrain(game.getTerrain());
        assert (gameClone.length == game.getTerrain().length) && (gameClone[0].length == game.getTerrain()[0].length)
                : "Le terrain clone n'a pas les memes dimensions que le terrain initial";

        for(int i = 0; i < gameClone.length; i++){
            for (int j = 0; j < gameClone[0].length; j++){
                if (gameClone[i][j] != null){
                    assert gameClone[i][j].getNbPoissons() == game.getTerrain()[i][j].getNbPoissons();
                    assert (gameClone[i][j].estMange()==false) && (gameClone[i][j].estMange()==game.getTerrain()[i][j].estMange());
                    assert (gameClone[i][j].pingouinPresent()==0) && (game.getTerrain()[i][j].pingouinPresent()==0);
                }
            }
        }

        assert game.getListeJoueur().size() == 4 : "La liste des joueurs ne contient pas le bon nombre de joueurs";



        // Placement des pingouins
        assert game.getJoueur() != 2 : "Ce n'est pas au joueur 2 de jouer !!";
        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (4,2)");
        game.placePingouin(4, 2);
        System.out.println("\nPlacement pingouin en (4,3)\n");
        game.placePingouin(4, 3);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (1,0)\n");
        game.placePingouin(1, 0);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (0,6)\n");
        game.placePingouin(0, 6);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (7,0)\n");
        game.placePingouin(7, 0);

        // "Deuxieme tour"
        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (6,5)\n");
        game.placePingouin(6, 5);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (2,4)\n");
        game.placePingouin(2, 4);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (3,1)\n");
        game.placePingouin(3, 1);

        System.out.println("Au tour de " + game.getJoueur() + "\nPlacement pingouin en (3,1)");
        game.placePingouin(3,1);        // Placement sur une case ou il y a deja un pingouin qui n'est pas a nous
        System.out.println("\nAu tour de " + game.getJoueur() + "\nPlacement pingouin en (7,0)");
        game.placePingouin(7,0);        // Placement sur une case ou il y a deja un pingouin qui est a nous
        System.out.println("\nAu tour de " + game.getJoueur() + "\nPlacement pingouin en (4,6)");
        game.placePingouin(4, 6);
        System.out.println();

        */
        JeuAvance jeu = new JeuAvance("src/test/Terrains/terrainFixe.txt");
        jeu.placePingouin(7, 7);
        System.out.println(jeu.getListeJoueur());

        // Verification placement pingouins


        // Plus de pingouins ?
        //game.placePingouin(7, 4);
        //assert game.getListeJoueur().get(game.getJoueur()).getListePingouin().size() == 2;



        /*
        System.out.println("Sauvegarde du jeu\n");
        String filename = "src/test/Terrains/terrainFixe.txt";
        jeu.sauvegarder(filename);
        JeuAvance jeuSauve = new JeuAvance("test.txt");
        System.out.println("jeuSauve:\n" + jeuSauve.toString());
        */

        //jeu.sauvegarder("src/test/Terrains/terrainNonFixe.txt");

        /*
        Cases c = new Cases(4);
        jeu.setCase(c, 0, 0);
        System.out.println(jeu.toString());

        ArrayList<Position> ListecasesAccess = jeu.getCaseAccessible(new Pingouin(0, 0));
        System.out.println(ListecasesAccess.toString());

        if (ListecasesAccess.contains(new Position(0, 1))){
            System.out.println("Hello");
        }
        */

    }
}
