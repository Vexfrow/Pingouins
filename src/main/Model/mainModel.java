package main.Model;


import java.util.Arrays;



public class mainModel {
    
    public static void main(String[] args) {
		
        //test pour la fonction clonner terrain

        //System.out.println("");

        Jeu j = new Jeu(8,8, 1, 1);
        System.out.println(j);


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
