// package Model;

// import java.util.ArrayList;

// /* Tests sur le jeu (a mettre a jour au fur et a mesure) */

// public class MainTest {
    
//     public static void main(String[] args){
//         // Creation du jeu de base

//         // Initialisation des joueurs
//         Joueur j1 = new Joueur(1, 0, 2);
//         Joueur j2 = new Joueur(2, 0, 2);
//         ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
//         listeJoueur.add(j1);
//         listeJoueur.add(j2);

//         // Initialisation du jeu
//         Jeu j = new Jeu(listeJoueur);
//         System.out.println(j.toString());

//         // Placement des pingouins pour le joueur 1
//         j.placePingouin(0, 1, j1);
//         j.placePingouin(0, 5, j1);
//         j.placePingouin(1, 1, j1);  // pour voir si on peut mettre plus de pingouins
//         System.out.println("\n" + j1.toString() + "\n");

//         // Placement des pingouins pour le joueur 2
//         j.placePingouin(1, 1, j2);
//         j.placePingouin(0, 1, j2);  // pour voir si on peut placer sur une case deja prise
//         j.placePingouin(1, 7, j2);
//         j.placePingouin(1, 2, j2);  // pour voir si on peut mettre plus de pingouins
//         System.out.println("\n" + j2.toString() + "\n");

//         System.out.println("Plateau apres placement des pingouins:\n" + j.toString());

//         // Coup valide pour j1
//         Coup cp = new Coup(0, 2, j1, j1.getListePingouin().get(0));
//         System.out.println("\nCoup (0, 2) pour pingouin 1 de j1:");
//         j.joue(cp, j1.getNumeroJoueur());
//         System.out.println(j.toString());
//         System.out.println("\nScore joueur 1: " + j1.getScore());
//         System.out.println("Score joueur 2: " + j2.getScore());

//         // Coup valide mais c'est au tour du joueur 2
//         cp = new Coup(0, 6, j1, j1.getListePingouin().get(1));
//         System.out.println("\nCoup (0, 6) pour pingouin 2 de j1:");
//         j.joue(cp, j1.getNumeroJoueur());
//         System.out.println(j.toString());
//         System.out.println("\nScore joueur 1: " + j1.getScore());
//         System.out.println("Score joueur 2: " + j2.getScore()); 

//         // Coup valide pour le joueur 2
//         cp = new Coup(1, 6, j2, j2.getListePingouin().get(1));
//         System.out.println("\nCoup (1, 6) pour pingouin 2 de j1:");
//         j.joue(cp, j2.getNumeroJoueur());
//         System.out.println(j.toString());
//         System.out.println("\nScore joueur 1: " + j1.getScore());
//         System.out.println("Score joueur 2: " + j2.getScore());     
        
//         // Coup invalide pour j1
//         //   -> Deplacement sur une case qu'on a mange
//         cp = new Coup(0, 1, j1, j1.getListePingouin().get(0));
//         System.out.println("\nCoup (0, 1) pour pingouin 1 de j1:");
//         j.joue(cp, j1.getNumeroJoueur());
//         System.out.println(j.toString());
//         System.out.println("\nScore joueur 1: " + j1.getScore());
//         System.out.println("Score joueur 2: " + j2.getScore()); 
        

//         /*
//         // CREATION JEU AVEC PARAMETRES
//         Jeu j = new Jeu(7, 9, 2, 4);
//         System.out.println("Chargement d'un jeu\n" + j.toString());

//         // !!! FAIRE UN TEST QUAND ON PLACE LES PINGOUINS !!!
//         // !!! + METTRE A 0 LES CASES OU LES PINGOUINS SONT PLACES !!!
//         Pingouin p1 = new Pingouin(1, 0, 0);
//         System.out.println(p1.toString());

//         // Coup valide
//             // On se deplace de 1 vers la droite
//         Coup cp = new Coup(0, 1, p1);
//         System.out.println("\nNombre de poissons en (0, 1): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("coup: (0, 1) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());

//             // On se deplace jusqu'a la fin de la ligne
//         cp = new Coup(0, 7, p1);
//         System.out.println("\nNombre de poissons en (0, 7): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("coup: (0, 7) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());

//             // On se deplace en vertical
//         cp = new Coup(1, 8, p1);
//         System.out.println("\nNombre de poissons en (1, 8): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("coup: (1, 8) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());

//             // Toujours en vertical mais jusqu'au bord
//         cp = new Coup(6, 5, p1);
//         System.out.println("\nNombre de poissons en (6, 5): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("coup: (6, 5) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());


//         // Coup invalide
//             // On va sur une case sur laquelle on est deja alle
//         cp = new Coup(1, 8, p1);
//         System.out.println("\nNombre de poissons en (1, 8): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("\ncoup: (1, 8) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());

//             // On se deplace sur une case hors des cases ou on peut aller
//         cp = new Coup(1, 0, p1);
//         System.out.println("\nNombre de poissons en (1, 0): " + 
//                             j.getCase(cp.getLigne(), cp.getColonne()).getNbPoissons());
//         System.out.println("\ncoup: (1, 0) pour le pingouin 1");
//         j.joue(cp);
//         System.out.println(j.toString());
//         System.out.println("Position du pingouin: " + p1.toString());
//         */
//     }
// }
