package Model;

/* Tests sur le jeu (a mettre a jour au fur et a mesure) */

public class MainTest {
    
    public static void main(String[] args){
        // Creation du jeu de bas


        //save

        //Jeu j = new Jeu(null);
        //System.out.println(j.toString());


        ///*
        
        
        // Initialisation du jeu
        Jeu j = new Jeu(2);


        // Placement des pingouins 
        System.out.println("Au tour de " + j.getJoueur() + "\nPlacement pingouin en (0,0)");
        j.placePingouin(0, 0);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\n Au tour de " + j.getJoueur() + "\nPlacement pingouin en (1,0)");
        j.placePingouin(1, 0);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());


        // Coups
        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Coup (0,4)");
        Coup cp = new Coup(0,4,new Pingouin(0,0), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("Au tour de " + j.getJoueur());
        System.out.println("Coup (3,1)");
        cp = new Coup(3,1,new Pingouin(1,0), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("Au tour de " + j.getJoueur());
        System.out.println("Coup (3,1)");
        cp = new Coup(3,1,new Pingouin(0,4), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());
        
        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.getJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        //j.sauvegarder(null);
        
    }
}
