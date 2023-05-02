package Model;


import java.util.ArrayList;
import java.util.Arrays;
import Model.Position;


public class mainModel {
    
    public static void main(String[] args) {
		

        ArrayList<Position> p = new ArrayList<Position>();

        Position pa = new Position(10, 666);

        p.add(pa);

        Position paa = new Position(10, 666);

        System.out.println(p.contains(pa));

        System.out.println(p.contains(paa));

        /*



        //test pour la fonction cloner terrain

        System.out.println("");

        ArrayList<Joueur> joueur = new ArrayList<>();

        Jeu j = new Jeu(joueur, 8, 8);
        System.out.println(j);


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
