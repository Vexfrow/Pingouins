package tests.Tests;

/* Programme simple sur differentes configurations testees en jeu sur lesquelles un bug a ete
 * trouve et qui ont donc ete corrige/deboguer
*/

import Model.Jeu;
import Model.Pingouin;
import Model.Coup;
import Joueur.IAAleatoire;

public class MyTest {

    public static void main(String[] args){
        /*
        Jeu game = new Jeu(3);
        game.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");
        */

        //------------------------------------------------------------
        // --> BUG: Case mangee toujours consideree comme case avec pingouin present (et 
        //          donc toujours cliquable)
        
        System.out.println("===============TEST 1===============\n");
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
        // --> TEST: Un seul pingouin bloque de j1 
        
        System.out.println("===============TEST 2===============\n");
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
        // --> BUG: Tous les pingouins de j1 bloques mais cas non gere
        
        System.out.println("===============TEST 3===============\n");
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
        System.out.println();


        //------------------------------------------------------------
        // --> BUG: Annuler/Refaire non fonctionnel pour IA aleatoire et IA 3 poissons
        
        System.out.println("===============TEST 4===============\n");
        jeu = new Jeu("src/tests/Terrains/terrainTest4.txt");

        jeu.placePingouin(0, 6); //1
        jeu.placePingouin(0, 5); //2
        jeu.placePingouin(1, 6); //1
        jeu.placePingouin(1, 7); //2
        jeu.placePingouin(2, 6); //1
        jeu.placePingouin(3, 6); //2
        jeu.placePingouin(4, 6); //1
        jeu.placePingouin(4, 5); //2

        assert jeu.getNbPingouinPlace() == 0: "Test4 - Il ne reste aucun pingouin a placer";

        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(2).getLigne(), 
                                     jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(2).getColonne());
        System.out.println(ping);
        cp = new Coup(3, 7, ping, false);
        jeu.joue(cp);

        System.out.println(jeu.getListeJoueur());
        System.out.println(jeu.getJoueurCourant());

        IAAleatoire ia = new IAAleatoire(jeu);
        cp = ia.elaboreCoup();
        //cp = new Coup (6,6,new Pingouin(4,5), false);
        System.out.println(cp);
        jeu.joue(cp);
        System.out.println("\n==========C'est ici que ca nous interresse==========\n");

        System.out.println(jeu.getListeJoueur());
        System.out.println(jeu.getJoueurCourant());
        System.out.println(jeu.getListeCoupsJoues());
        System.out.println(jeu.getListeCoupsAnnules());

        System.out.println("Annule");
        jeu.annule();
        System.out.println(jeu.getListeJoueur());
        System.out.println(jeu.getJoueurCourant());
        System.out.println(jeu.getListeCoupsJoues());
        System.out.println(jeu.getListeCoupsAnnules());

        System.out.println("Refaire");
        jeu.refaire();

    }
}
