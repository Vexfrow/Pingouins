package Model;

/* Tests sur les methodes de Model */

import java.util.ArrayList;
import java.util.Arrays;

public class mainModel {
    
    public static void main(String[] args) {

        // Test sur contains
        ArrayList<Position> p = new ArrayList<Position>();

        Position pa = new Position(10, 666);
        p.add(pa);

        Position paa = new Position(10, 666);

        System.out.println(p.contains(pa));
        System.out.println(p.contains(paa) + "\n");

        // Initialisation du jeu
        Jeu j = new Jeu(2);

        System.out.println(j.toString());
        System.out.println("\nNombre de cases: " + j.getNbCases() + "\n");


        // Test pour la methode clonerTerrain
        System.out.println("\nClonage du terrain:");
        Cases [][] c = j.clonerTerrain(j.getTerrain());

        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<c.length; i++) {
			result += sep + Arrays.toString(c[i]);
			sep = "\n ";
		}

        System.out.println(result);

	}
}
