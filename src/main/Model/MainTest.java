package Model;

/* Tests sur le jeu (a mettre a jour au fur et a mesure) */

public class MainTest {
    
    public static void main(String[] args){

        // Initialisation du jeu
        JeuAvance j = new JeuAvance(2);

        
        // Placement des pingouins 
        System.out.println("Au tour de " + j.quelJoueur() + "\nPlacement pingouin en (0,0)");
        j.placePingouin(0, 0);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        // Annule refait
        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\n Au tour de " + j.quelJoueur() + "\nPlacement pingouin en (1,0)");
        j.placePingouin(1, 0);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());


        // Coups
        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Coup (0,4)");
        Coup cp = new Coup(0,4,new Pingouin(0,0), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("Au tour de " + j.quelJoueur());
        System.out.println("Coup (3,1)");
        cp = new Coup(3,1,new Pingouin(1,0), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("Au tour de " + j.quelJoueur());
        System.out.println("Coup (3,1)");
        cp = new Coup(3,1,new Pingouin(0,4), false);
        j.joue(cp);
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());
    
        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());
        
        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Annule coup");
        j.annule();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        System.out.println("\nAu tour de " + j.quelJoueur());
        System.out.println("Refais coup");
        j.refaire();
        System.out.println("\n" + j.toString() + "\n");
        System.out.println(j.getListeJoueur());
        System.out.println("Liste coupJoue: " + j.getListeCoupsJoues());

        //pour la sauvegarde


        /* 
        //sauvegarder

        /!\ bien verifier le toString de la méthode Cases : besoin du return pour

        j.sauvegarder("test.txt");
        
        System.out.println("\n\n");

        //refaire un jeu avec sauvegarde
        Jeu j2 = new Jeu("test.txt");
        System.out.println(j2.toString());

        */

    }

}
