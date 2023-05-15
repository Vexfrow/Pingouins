package tests.Tests;

/* Programme simple pour tester/deboguer les differents bugs rencontres lors du jeu pour differentes configurations */

import Model.Jeu;
import Model.Pingouin;
import Model.Coup;

public class MyTest {

    public static void main(String[] args){
        /*
        Jeu game = new Jeu(3);
        game.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");
        */

        //------------------------------------------------------------
        
        System.out.println("==========TEST 1==========\n");
        Jeu jeu = new Jeu("src/tests/Terrains/terrainTest1.txt");
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
        System.out.println(jeu.estPingouinBloque(new Pingouin(6, 5)));
        System.out.println("\n"+jeu.getCase(6, 5).getNbPoissons());
        System.out.println(jeu.getCase(6, 5).estMange());
        System.out.println(jeu.getCase(6, 5).pingouinPresent() + "\n");


        //------------------------------------------------------------
        
        System.out.println("==========TEST 2==========\n");
        jeu = new Jeu("src/tests/Terrains/terrainTest2.txt");
        jeu.placePingouin(0, 6); //1
        jeu.placePingouin(0, 5); //2
        jeu.placePingouin(1, 6); //3
        jeu.placePingouin(1, 7); //1
        jeu.placePingouin(2, 6); //2
        jeu.placePingouin(3, 6); //3
        jeu.placePingouin(4, 6); //1
        jeu.placePingouin(4, 5); //2
        jeu.placePingouin(6, 4); //3

        assert jeu.getNbPingouinPlace() == 0: "Test2 - Il ne reste aucun pingouin a placer";

        System.out.println(jeu.toString());
        System.out.println(jeu.getListeJoueur());
        System.out.println("\n"+jeu.getListeCoupsJoues());
        System.out.println(jeu.getJoueurCourant());
        
        Pingouin ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(2).getLigne(), 
                                     jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(2).getColonne());
        System.out.println(ping);
        Coup cp = new Coup(3, 7, ping, false);
        jeu.joue(cp);
        System.out.println();


        //------------------------------------------------------------
        
        System.out.println("==========TEST 3==========\n");
        jeu = new Jeu("src/tests/Terrains/terrainTest3.txt");
        System.out.println(jeu.toString());
        System.out.println(jeu.getListeJoueur());

        jeu.placePingouin(7,4); //1
        jeu.placePingouin(7,3); //2
        jeu.placePingouin(6,3); //3
        jeu.placePingouin(6,4); //4
        jeu.placePingouin(7,0); //1
        jeu.placePingouin(7,5); //2
        jeu.placePingouin(6,0); //3
        jeu.placePingouin(7,1); //4

        System.out.println("\n"+jeu.getListeJoueur());
        assert jeu.getNbPingouinPlace() == 0: "Test3 - Il ne reste aucun pingouin a placer";
        System.out.println("\n"+jeu.getJoueurCourant());


        ping = new Pingouin(7, 4);
        System.out.println(ping + " Hello "+jeu.estPingouinBloque(ping) + " " + jeu.getCase(7, 4).estMange());
        ping = new Pingouin(7, 0);
        System.out.println(ping + " Hello "+jeu.estPingouinBloque(ping) + " " + jeu.getCase(7, 0).estMange());

        System.out.println("\n"+jeu.getListeJoueur());

        jeu.annule();
        jeu.refaire();
        
        System.out.println("\n"+jeu.getJoueurCourant());

        ping = new Pingouin(7, 4);
        System.out.println(ping + " Hello "+jeu.estPingouinBloque(ping) + " " + jeu.getCase(7, 4).estMange());
        ping = new Pingouin(7, 0);
        System.out.println(ping + " Hello "+jeu.estPingouinBloque(ping) + " " + jeu.getCase(7, 0).estMange());

    }
}