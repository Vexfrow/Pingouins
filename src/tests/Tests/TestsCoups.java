package tests.Tests;

import Model.JeuAvance;
import Model.Coup;
import Model.Pingouin;
import Model.Joueur;

/* Programme test pour tout ce qui a trait aux coups */

public class TestsCoups {
    
    public static void main(String[] args){
        // Initialisation du jeu pour tester les coups
        JeuAvance jeu = new JeuAvance("src/tests/Terrains/terrainFixe.txt");

        // Placement des pingouins (cf MyTestsPingouins.java)
        System.out.println("Placement des pingouins\n");
        jeu.placePingouin(0, 0); //1: rond
        jeu.placePingouin(7, 7); //2: carre
        jeu.placePingouin(3, 0); //3: triangle
        jeu.placePingouin(4, 2); //4: croix
        jeu.placePingouin(5, 0); //1
        jeu.placePingouin(6, 6); //2
        jeu.placePingouin(0, 6); //3
        //System.out.println(jeu.getListeJoueur() + "\n");



        // On doit d'abord placer tous les pingouins avant de pouvoir jouer
        System.out.println("On doit d'abord placer tous les pingouins avant de pouvoir jouer");
        Pingouin ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne(), 
                                     jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne());
        Coup cp = new Coup(4,3,ping,false);
        jeu.joue(cp);
        //System.out.println(ping);
        assert ping.getLigne() == 4 && ping.getColonne() == 2: "Les pingouins doivent d'abord etre places";
        assert jeu.getJoueurCourant() == 4: "C'est toujours au joueur 4 de jouer";

        // On place donc le dernier pingouin
        jeu.placePingouin(2, 5); //4
        //System.out.println("\n" + jeu.getListeJoueur() + "\n");



        // Un pingouin ne peut pas se deplacer sur une case ou il y a deja un pingouin
        System.out.println("\nUn pingouin ne peut pas se deplacer sur une case ou il y a deja un pingouin");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne(),
                             jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne());
        cp = new Coup(0,6,ping,false);
        jeu.joue(cp);
        // On verifie donc que tout soit en ordre
        assert ping.getLigne() == 0 && ping.getColonne() == 0: "Le pingouin n'est pas deplacable en (0,6)";
        assert jeu.getJoueurCourant() == 1: "C'est toujours au joueur 1 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 0: "Le score doit etre a 0";
        assert jeu.getCase(0, 0).getNbPoissons() == 1: "Le nombre de poissons en (0,0) doit etre a 1";
        assert jeu.getCase(0, 0).estMange() == false: "La case (0,0) n'a pas encore ete mange";
        assert jeu.getCase(0, 0).pingouinPresent() == 1: "Le pingouin n'est pas encore parti de la case (0,0)";
        assert jeu.getCase(0, 6).pingouinPresent() == 3: "Aucun pingouin ne doit etre sur la case (0,6)";
        assert jeu.getCase(0, 6).estMange() == false: "La case (0,6) a ete mange";
        assert jeu.getCase(0, 6).getNbPoissons() == 1: "La case (0,6) doit avoir 1 poisson";



        // On ne peut pas deplacer un pingouin adverse
        System.out.println("\nOn ne peut pas deplacer un pingouin adverse");
        ping = new Pingouin(jeu.getListeJoueur().get(3).getListePingouin().get(0).getLigne(),
                            jeu.getListeJoueur().get(3).getListePingouin().get(0).getColonne()) ;
        cp = new Coup(4,0,ping,false);
        jeu.joue(cp);
        assert jeu.getJoueurCourant() == 1: "C'est toujours au joueur 1 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 0: "Le score doit etre a 0";
        assert jeu.getCase(0, 0).getNbPoissons() == 1: "Le nombre de poissons en (0,0) doit etre a 1";
        assert jeu.getCase(0, 0).estMange() == false: "La case (0,0) n'a pas encore ete mange";
        assert jeu.getCase(0, 0).pingouinPresent() == 1: "Le pingouin n'est pas encore parti de la case (0,0)";
        assert jeu.getCase(4, 0).pingouinPresent() == 0: "Aucun pingouin ne doit etre sur la case (4,0)";
        assert jeu.getCase(4, 0).estMange() == false: "La case (4,0) a ete mange";
        assert jeu.getCase(4, 0).getNbPoissons() == 3: "La case (4,0) doit avoir 1 poisson";



        // Bon deplacement d'un pingouin
        System.out.println("\nBon placement d'un pingouin");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(5,6,ping,false);
        jeu.joue(cp);
        assert jeu.getJoueurCourant() == 2: "C'est au joueur de 2 de jouer";
        assert (jeu.getListeJoueur().get(jeu.getJoueurCourant() - 2).getListePingouin().get(1).getLigne() == 5 && 
                jeu.getListeJoueur().get(jeu.getJoueurCourant() - 2).getListePingouin().get(1).getColonne() == 6): "Le pingouin devrait etre en (5,6)";
        assert jeu.getScore(jeu.getJoueurCourant() - 1) == 1: "Le score doit etre a 1 pour le joueur 1";
        assert jeu.getCase(5, 0).getNbPoissons() == 0: "Le nombre de poissons en (5,0) doit etre a 0";
        assert jeu.getCase(5, 0).estMange() == true: "La case (5,0) doit avoir ete mange";
        assert jeu.getCase(5, 0).pingouinPresent() == 0: "Le pingouin doit etre parti de la case (5,0)";
        assert jeu.getCase(5, 6).pingouinPresent() == 1: "Le pingouin doit etre sur la case (5,6)";
        assert jeu.getCase(5, 6).estMange() == false: "La case (5,6) n'a pas encore ete mange";
        assert jeu.getCase(5, 6).getNbPoissons() == 2: "La case (5,6) n'a pas encore ete mange";



        // Un pingouin ne peut pas ignorer un pingouin sur son chemin
        System.out.println("\nUn pingouin ne peut pas ignorer un pingouin sur son chemin");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(4,5,ping,false);
        jeu.joue(cp);
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne() == 6 && 
               jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne() == 6: "Le pingouin n'est pas deplacable en (4,5)";
        assert jeu.getJoueurCourant() == 2: "C'est toujours au joueur 2 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 0: "Le score doit etre a 0";
        assert jeu.getCase(6, 6).getNbPoissons() == 1: "Le nombre de poissons en (6,6) doit etre a 1";
        assert jeu.getCase(6, 6).estMange() == false: "La case (6,6) n'a pas encore ete mange";
        assert jeu.getCase(6, 6).pingouinPresent() == 2: "Le pingouin n'est pas encore parti de la case (6,6)";
        assert jeu.getCase(4, 5).pingouinPresent() == 0: "Aucun pingouin ne doit etre sur la case (4,5)";
        assert jeu.getCase(4, 5).estMange() == false: "La case (4,5) a ete mange";
        assert jeu.getCase(4, 5).getNbPoissons() == 1: "La case (4,5) doit avoir 1 poisson";



        // On verifie qu'un pingouin ne sorte pas du terrain quand bien meme c'est dans l'une de ses dix directions possibles
        System.out.println("\nOn verifie qu'un pingouin ne sorte pas du terrain quand bien meme c'est dans l'une de ses dix directions possibles");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(6,7,ping,false);
        jeu.joue(cp);
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne() == 6 && 
               jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne() == 6: "Le pingouin n'est pas deplacable en (6,7)";
        assert jeu.getJoueurCourant() == 2: "C'est toujours au joueur 2 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 0: "Le score doit etre a 0";
        assert jeu.getCase(6, 6).getNbPoissons() == 1: "Le nombre de poissons en (6,6) doit etre a 1";
        assert jeu.getCase(6, 6).estMange() == false: "La case (6,6) n'a pas encore ete mange";
        assert jeu.getCase(6, 6).pingouinPresent() == 2: "Le pingouin n'est pas encore parti de la case (6,6)";
        assert jeu.getCase(6, 7) == null: "La case (6,7) est en dehors du terrain";



        // On verifie qu'un pingouin ne puisse pas aller en dehors de ses cases accessibles
        System.out.println("\nOn verifie qu'un pingouin ne puisse pas aller en dehors de ses cases accessibles");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(5,5,ping,false);
        jeu.joue(cp);
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne() == 6 && 
               jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne() == 6: "Le pingouin n'est pas deplacable en (5,5)";
        assert jeu.getJoueurCourant() == 2: "C'est toujours au joueur 2 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 0: "Le score doit etre a 0";
        assert jeu.getCase(6, 6).getNbPoissons() == 1: "Le nombre de poissons en (6,6) doit etre a 1";
        assert jeu.getCase(6, 6).estMange() == false: "La case (6,6) n'a pas encore ete mange";
        assert jeu.getCase(6, 6).pingouinPresent() == 2: "Le pingouin n'est pas encore parti de la case (6,6)";
        assert jeu.getCase(5, 5).pingouinPresent() == 0: "Aucun pingouin ne doit etre sur la case (5,5)";
        assert jeu.getCase(5, 5).estMange() == false: "La case (5,5) a ete mange";
        assert jeu.getCase(5, 5).getNbPoissons() == 2: "La case (5,5) doit avoir 2 poissons";



        // Suite de coups acceptables
        System.out.println("\n\nSuite de coups acceptables");
        // 2
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(6,3,ping,false);
        jeu.joue(cp);
        // 3
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne());
        cp = new Coup(3,5,ping,false);
        jeu.joue(cp);
        // 4
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne());
        cp = new Coup(0,4,ping,false);
        jeu.joue(cp);
        // 1
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(4,6,ping,false);
        jeu.joue(cp);
        // 2
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(1).getColonne());
        cp = new Coup(6,2,ping,false);
        jeu.joue(cp);

        // On verifie quand meme que tout soit en ordre
        assert jeu.getScore(1) == 3: "Le joueur 1 doit avoir un score de 3";
        assert jeu.getScore(2) == 3: "Le joueur 2 doit avoir un score de 3";
        assert jeu.getScore(3) == 1: "Le joueur 3 doit avoir un score de 1";
        assert jeu.getScore(4) == 1: "Le joueur 4 doit avoir un score de 1";
            // 2
        assert jeu.getCase(6, 3).pingouinPresent() == 0: "Le pingouin doit etre sur la case (6,3)";
        assert jeu.getCase(6, 3).estMange() == true: "La case (6,3) n'a pas encore ete mange";
        assert jeu.getCase(6, 3).getNbPoissons() == 0: "La case (6,3) n'a pas encore ete mange";       
            // 3
        assert jeu.getCase(3, 5).pingouinPresent() == 3: "Le pingouin doit etre sur la case (3,5)";
        assert jeu.getCase(3, 5).estMange() == false: "La case (3,5) n'a pas encore ete mange";
        assert jeu.getCase(3, 5).getNbPoissons() == 1: "La case (3,5) n'a pas encore ete mange";    
            // 4
        assert jeu.getCase(0, 4).pingouinPresent() == 4: "Le pingouin doit etre sur la case (0,4)";
        assert jeu.getCase(0, 4).estMange() == false: "La case (0,4) n'a pas encore ete mange";
        assert jeu.getCase(0, 4).getNbPoissons() == 1: "La case (0,4) n'a pas encore ete mange";    
            // 1
        assert jeu.getCase(4, 6).pingouinPresent() == 1: "Le pingouin doit etre sur la case (4,6)";
        assert jeu.getCase(4, 6).estMange() == false: "La case (4,6) n'a pas encore ete mange";
        assert jeu.getCase(4, 6).getNbPoissons() == 1: "La case (4,6) n'a pas encore ete mange";    
            // 2
        assert jeu.getCase(6, 2).pingouinPresent() == 2: "Le pingouin doit etre sur la case (6,2)";
        assert jeu.getCase(6, 2).estMange() == false: "La case (6,2) n'a pas encore ete mange";
        assert jeu.getCase(6, 2).getNbPoissons() == 1: "La case (6,2) n'a pas encore ete mange"; 



        // Un pingouin ne peut pas passer par-dessus une case mangee
        System.out.println("\nUn pingouin ne peut pas passer par-dessus une case mangee");
        ping = new Pingouin(jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne(),
                            jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne());
        cp = new Coup(7,3,ping,false);
        jeu.joue(cp);
        assert jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getLigne() == 3 && 
               jeu.getListeJoueur().get(jeu.getJoueurCourant() - 1).getListePingouin().get(0).getColonne() == 5: "Le pingouin n'est pas deplacable en (3,5)";
        assert jeu.getJoueurCourant() == 3: "C'est toujours au joueur 2 de jouer";
        assert jeu.getScore(jeu.getJoueurCourant()) == 1: "Le score doit etre a 1";
        assert jeu.getCase(3, 5).getNbPoissons() == 1: "Le nombre de poissons en (3,5) doit etre a 1";
        assert jeu.getCase(3, 5).estMange() == false: "La case (3,5) n'a pas encore ete mange";
        assert jeu.getCase(3, 5).pingouinPresent() == 3: "Le pingouin n'est pas encore parti de la case (3,5)";
        assert jeu.getCase(7, 3).pingouinPresent() == 0: "Aucun pingouin ne doit etre sur la case (7,3)";
        assert jeu.getCase(7, 3).estMange() == false: "La case (7,3) a ete mange";
        assert jeu.getCase(7, 3).getNbPoissons() == 1: "La case (7,3) doit avoir 1 poisson";


        
        // On sauvegarde puis on recupere dans un autre jeuAvance pour verifier qu'on a bien la meme chose
        System.out.println("\nOn sauvegarde puis on recupere dans un autre jeuAvance pour verifier qu'on a bien la meme chose");
        jeu.sauvegarder("src/tests/Terrains/terrainFixeCoup.txt");

        JeuAvance jeuSauve = new JeuAvance("src/tests/Terrains/terrainFixeCoup.txt");

        Joueur jeuJ1 = jeu.getListeJoueur().get(0);
        Joueur jeuJ2 = jeu.getListeJoueur().get(1);
        Joueur jeuJ3 = jeu.getListeJoueur().get(2);
        Joueur jeuJ4 = jeu.getListeJoueur().get(3);

        Joueur jeuSauveJ1 = jeuSauve.getListeJoueur().get(0);
        Joueur jeuSauveJ2 = jeuSauve.getListeJoueur().get(1);
        Joueur jeuSauveJ3 = jeuSauve.getListeJoueur().get(2);
        Joueur jeuSauveJ4 = jeuSauve.getListeJoueur().get(3);

        assert jeuJ1.getNumeroJoueur() == jeuSauveJ1.getNumeroJoueur();
        assert jeuJ2.getNumeroJoueur() == jeuSauveJ2.getNumeroJoueur();
        assert jeuJ3.getNumeroJoueur() == jeuSauveJ3.getNumeroJoueur();
        assert jeuJ4.getNumeroJoueur() == jeuSauveJ4.getNumeroJoueur();
        
        assert jeuJ1.getScore() == jeuSauveJ1.getScore(): "Les scores pour le joueur 1 ne sont pas les memes";
        assert jeuJ2.getScore() == jeuSauveJ2.getScore(): "Les scores pour le joueur 2 ne sont pas les memes";
        assert jeuJ3.getScore() == jeuSauveJ3.getScore(): "Les scores pour le joueur 3 ne sont pas les memes";
        assert jeuJ4.getScore() == jeuSauveJ4.getScore(): "Les scores pour le joueur 4 ne sont pas les memes";

        assert jeuJ1.getNbCasesMange() == jeuSauveJ1.getNbCasesMange(): "Les nombres de cases mangees pour le joueur 1 ne sont pas les memes";
        assert jeuJ2.getNbCasesMange() == jeuSauveJ2.getNbCasesMange(): "Les nombres de cases mangees pour le joueur 2 ne sont pas les memes";
        assert jeuJ3.getNbCasesMange() == jeuSauveJ3.getNbCasesMange(): "Les nombres de cases mangees pour le joueur 3 ne sont pas les memes";
        assert jeuJ4.getNbCasesMange() == jeuSauveJ4.getNbCasesMange(): "Les nombres de cases mangees pour le joueur 4 ne sont pas les memes";

     
 
    }
}
