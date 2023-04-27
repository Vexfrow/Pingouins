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
    private ArraysList<Coup> coupJoue;
    private ArraysList<Coup> coupAnnule;
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
    Jeu(Case [][]terrainInitiale, int nbLigneTab, int nbColTab){
        this.terrainInitiale = terrainInitiale;
        this.terrainCourant = cloner(terrainInitiale);
    }

    /**
     * Init du jeu avec des parametres
     */
    Jeu(int nbLignes, int nbColonnes, int nbJoueurs, int PingParJoueur){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];
        this.nbColonnes = nbColonne*2-1;
        this.nbLignes = nbLignes;
        int l = 0;
        int c, r;

        
        if( l%2 ==1 ){// si ligne impaire
            c = 0
        }else{ // ligne paire
            c = 1;
        }
        while( l < nbLignes ){

            if( l%2 ==1 ){// si ligne impaire
                c = 0
            }else{ // ligne paire
                c = 1;
            }

            while( c < nbColonnes*2-1){
                r = r.nextInt(3)+1;
                terrainInitiale[l][c]= new Cases(r);
                terrainCourant[l][c]= new Cases(r);
                c+=2;
            }
            l++;
        }

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

    public void setCase(Cases cases, int ligne, int colonne){

            if( ligne%2 ==1 ){// si ligne impaire
                terrainCourant[ligne][colonne*2+1] = cases;
            }else{ // ligne paire
                terrainCourant[ligne][colonne*2] = cases;
            }
    }

    public boolean peutJoue(Coup cp){

        return true;
    }


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
        if(peutAnnuler()){
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

        //changemetn du joueur
        if(joueurCourant == 1){
            joueurCourant = 1;
        } else {
            joueurCourant = 2;
        }

    }

    

    
    
    /**
     * Annonce s'il est possible de refaire un coup annulé
     */
    public boolean peutRefaire(){
        return true;
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
            
			//w.write(nbligne + "\n");
			//w.write(nbcolonne + "\n");


            //stocker le terrain de base




			//stocker tous les coups joués
			int tailleList = coupJoue.size();

			//stock tous les coups
			for(int i = 0; i< tailleList; i++) {
				//w.write(coupJoue.get(i).l + " "+ coupJoue.get(i).c + "\n");
			}

			//marque pour indiquer que la suite sont des coups annules
			w.write("b\n");

			//stocker tous les coups annules
			int tailleLista = coupAnnule.size();
			for(int i = 0; i< tailleLista; i++) {
				//w.write(coupAnnule.get(i).l + " "+ coupAnnule.get(i).c + "\n");
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