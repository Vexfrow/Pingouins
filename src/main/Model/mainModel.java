package Model;

/* Tests sur les methodes de Model */

import java.util.ArrayList;
import java.util.Arrays;

public class mainModel {
    
    public static void main(String[] args) {


        // Initialisation du jeu
        Jeu j = new Jeu(2);

        System.out.println(j.toString());


        System.out.println(j);










        // Test pour la methode clonerTerrain

        /*
        System.out.println("\nClonage du terrain:");
        Cases [][] c = j.clonerTerrain(j.getTerrain());


        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<c.length; i++) {
			result += sep + Arrays.toString(c[i]);
			sep = "\n ";
		}

        System.out.println(result);

        */

	}
}
