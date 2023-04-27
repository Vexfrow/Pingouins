package main.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class Jeu{

    private Cases [][] terrainInitiale;
    private Cases [][] terrainCourant;
    private ArrayList<Coup> coupJoue;
    private ArrayList<Coup> coupAnnule;
    private ArrayList<Joueur> listeJoueur;

    private int nbLignes;
    private int nbColonnes;
    private int nbJoueur;

    

    /**
     * Init du jeu depuis une sauvegarde
     */
    Jeu(String name){

        try {
    		
    		//init des arrays
    		coupAnnule = new ArrayList<Coup>();
        	coupJoue = new ArrayList<Coup>();


        	//ouverture fichier
    		FileReader reader = new FileReader(name);
    		BufferedReader bufferedReader = new BufferedReader(reader);

    		String line;

    		//recuperer le nombre de ligne
    		line = bufferedReader.readLine();
    		nbLignes = Integer.parseInt(line);

    		//recuperer le nombre de collone
    		line = bufferedReader.readLine();
    		nbColonnes = Integer.parseInt(line);


            //creation terrain

    		//terrainCourant = new int[nbligne][nbcolonne];






            //recuperer le terrain






            //save le terrrain
            //terrainInitiale = 


    		//recuprer tous les coups à jouer
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

    				//split la ligne
    				String[] parts = line.split(" ");

    				//creer un nouveau coup
    				//Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

    				//jouer le coup
    				//joue(cp);

    	    }

    		//recuprere les dernieres lignes du fichier
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

    			//split les lignes
				String[] parts = line.split(" ");
				//definir un nouveau  coup
				//Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

				//ajoute le coup à l'arraylist annule
				//coupAnnule.add(cp);

	    }

    		//fermer le fichier
    		reader.close();
    		
		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}

    }


    /**
     * Init du jeu de base
     */
    Jeu(){

        

    }

    /**
     * Init jeu avec un terrain
     */
    Jeu(Cases [][]terrainInitiale, int nbLigneTab, int nbColTab){
        this.terrainInitiale = terrainInitiale;
        //this.terrainCourant = cloner(terrainInitiale);
    }


    /**
     * Init du jeu avec des parametres
     */
    Jeu(int nbLignes, int nbColonnes, int nbJoueurs, int PingParJoueur){

        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];
        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;

        //init des arrays
    	coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();

        int l = 0;

        ArrayList<Integer> listeNombre = new ArrayList<Integer>();

        for(int i = 1; i<=60; i++){
            if(i<= 30){
                listeNombre.add(1);
            }else if(i <=50){
                listeNombre.add(2);
            }else{
                listeNombre.add(3);
            }
        }


        Random r = new Random();

        int c, randomNumber;

        
        if( l%2 ==1 ){// si ligne impaire
            c = 0;
        }else{ // ligne paire
            c = 1;
        }

        while( l < nbLignes ){

            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire
                c = 1;
            }

            while( c < nbColonnes*2-1){
                randomNumber = r.nextInt(3)+1;
                terrainInitiale[l][c]= new Cases(randomNumber);
                terrainCourant[l][c]= new Cases(randomNumber);
                c+=2;
            }
            l++;
        }

    }



    /**
     * Creation du clone du terrain donné
     */
    public Cases [][] clonerTerrain(Cases [][] terrainInitiale){

        Cases [][] terrainClone;
        Cases caseCourante;
        Cases cases;
        Pingouin ping;

        //creation de la nouvelle matrice
        terrainClone = new Cases[this.nbLignes][this.nbColonnes];
        int c;
        int l=0;
        
        //boucle sur toutes les lignes
        while( l < nbLignes){

            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire
                c = 1;
            }

            //boucle sur toutes les colonnes
            while( c < (nbColonnes)){
                caseCourante = terrainInitiale[l][c];

                ping = caseCourante.getPingouin();

                //test pingouin est present sur la case ou non
                if(ping != null){
                    cases = new Cases(caseCourante.estMange(), caseCourante.getNbPoissons(),ping.cloner());
                } else {
                    cases = new Cases(caseCourante.estMange(), caseCourante.getNbPoissons(),null);
                }

                terrainClone[l][c] = cases;
                c+=2;
            }
            l++;
        }

        return terrainClone;
    }


    /**
     * Creation du terrain aléatoirement
     */
    public void terrainAleatoire(){


    }

    /**
     * Donne le terrain courant
     */
    public Cases [][] getTerrain(){
        return terrainCourant;
    }


    // Ligne compris entre [0, nbLigne[, ligne est la position sur le terrain hexagonal
    // Colonne compris entre [0, nbColonne[, colonne est la position sur le terrain hexagonal
    public Cases getCase(int ligne, int colonne){
            if( ligne%2 ==1 ){// si ligne impaire
                return terrainCourant[ligne][colonne*2+1];
            }else{ // ligne paire
                return terrainCourant[ligne][colonne*2];
            }
    }

    /**
     * Attribut une case à une case du terrain
     */
    public void setCase(Cases cases, int ligne, int colonne){

            if( ligne%2 ==1 ){// si ligne impaire
                terrainCourant[ligne][colonne*2+1] = cases;
            }else{ // ligne paire
                terrainCourant[ligne][colonne*2] = cases;
            }
    }


    /**
     * Annonce si on peut jouer
     */
    public boolean peutJoue(Coup cp){

        return true;
    }


    /**
     * Joue un coup
     */
    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        if (peutJoue(cp)){
            Cases caseArrive = getCase(l,c);

            Pingouin ping = cp.getPingouin();
            Joueur joueur = ping.getJoueur();
            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseArrive.getNbPoissons());

            caseDep.setPingouin(null); // A verifier !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);
            caseArrive.setNbPoissons(0);
            caseArrive.setPingouin(ping);

            
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();
        }
    }


    /**
     * Joue un coup sans objet coup
     */
    public void joue(int numEq, int numPing, int ligne, int colonne){

    }
    

    /**
     * annonce s'il est possible d'annuler
     */
    public boolean peutAnnule(){
        return (!(coupJoue.size() < 1));
    }


    /**
     * Annule un coup
     */
    public void annule(){

        /*
        if(peutAnnule()){
            Jeu jeu = new Jeu(nbligne,nbcolonne);
            int i = 0;
            coupAnnule.add(coupJoue.get(coupJoue.size()-1));
            coupJoue.remove(coupJoue.size()-1);

            while( i < coupJoue.size()){
                jeu.joue(coupJoue.get(i));
                i++;
            }
            this.terrain = jeu.terrain;

        }
        */

    }

    
    /**
     * Annonce s'il est possible de refaire un coup annulé
     */
    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }


    /**
     * Refait un coup annulé
     */
    public void refaire(){
        if(peutRefaire()){

        }
    }


    /**
     * Sauvegarde la partie dans un fichier de nom name
     */
    public void sauvegarder(String name){

        try {

			FileWriter w = new FileWriter(name);
			
            //stockage des diffferentes valeurs
            
			w.write(nbLignes + "\n");
			w.write(nbColonnes + "\n");




            //stocker le terrain de base

            String result = "Plateau:\n[";
		    String sep = "";

            for (int i=0; i<terrainCourant.length; i++) {
                result += sep + Arrays.toString(terrainCourant[i]);
                sep = "\n ";
            }

            w.write(result + "\n");







			//stocker tous les coups joués

			int tailleList = coupJoue.size();

			//stock tous les coups
			for(int i = 0; i< tailleList; i++) {
				//w.write(coupJoue.get(i).getLigne() + " "+ coupJoue.get(i).getColonne() + " " + coupJoue.get(i).getPingouin() + "\n");
			}


			//marque pour indiquer que la suite sont des coups annules
			w.write("b\n");




			//stocker tous les coups annules

			int tailleLista = coupAnnule.size();
			for(int i = 0; i< tailleLista; i++) {
				//w.write(coupAnnule.get(i).getLigne() + " "+ coupAnnule.get(i).getColonne()+ " " + coupAnnule.get(i).getPingouin() +"\n");
			}


			//fermer le fichier
			w.close();

		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}

    }

    /**
     * affichage terrain et informations courantes
     */
    public String toString(){
        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<terrainCourant.length; i++) {
			result += sep + Arrays.toString(terrainCourant[i]);
			sep = "\n ";
		}
		result += 	"]\nEtat:" +
                //"\n- Jeu en cours ? : " + jeuEnCours +
                //"\n- Joueur courant : " + joueurCourant +
				"\n- peut annuler : " + peutAnnule() +
				"\n- peut refaire : " + peutRefaire();
		return result;
    }
    
}