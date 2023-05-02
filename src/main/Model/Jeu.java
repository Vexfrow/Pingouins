package main.Model;


import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import Model.Cases;
import Model.Coup;
import Model.Joueur;
import Model.Pingouin;

public class Jeu{

    private Cases[][] terrainInitiale;
    private Cases[][] terrainCourant;
    private ArrayList<Model.Coup> coupJoue;
    private ArrayList<Coup> coupAnnule;
    private ArrayList<Joueur> listeJoueur;


    private int nbLignes; // taille du tableau
    private int nbColonnes; // taille du tableau
    private int nbJoueur;

    private int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence

    // Init du jeu depuis une sauvegarde
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


    // Init du jeu de base
    public Jeu(ArrayList<Joueur> listeJoueur){
        this(listeJoueur,8,8);
        

    }


    // Constructeur pour la methode annule()
    Jeu(Cases [][]terrainInitiale, ArrayList<Joueur> listeJoueur,ArrayList<Pingouin> listePingouin, int nbLigneTab, int nbColTab){ 
        this.terrainInitiale = terrainInitiale;
        this.terrainCourant = terrainInitiale;
        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        this.nbColonnes = nbLigneTab;
        this.nbLignes = nbColTab;

    }


    // Init du jeu avec des parametres
    public Jeu(ArrayList<Joueur> listeJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];
        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        this.listeJoueur = listeJoueur;
        this.nbJoueur = listeJoueur.size();
        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;

        //init des arrays
    	coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();

        terrainAleatoire(nbLignes, nbColonnes);

    }


    // Renvoie 1 si un pingouin est present sur une case specifique
    public boolean pingouinPresent(int l,int c){
        return (getCase(l,c).pingouinPresent());
    }


    // Place un pingouin sur une case
    public void placePingouin(int l, int c, Joueur joueur){
        if(!joueur.tousPingouinsPlaces() && getCase(l, c) != null && !pingouinPresent(l, c)){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(true);
            cases.setNbPoissons(0);
        }
    }


    // Creation du terrain aleatoirement de taille du terrain hexagonale donne en parametre
    public void terrainAleatoire(int nbLignes, int nbColonnes){
        int l = 0;
        int c,r;

        //array list pour la liste des valeurs possibles pour le nb de poisson
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

        Random rand = new Random();

        while( l < nbLignes ){

            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire
                c = 1;
            }

            while( c < nbColonnes*2-1){

                r = rand.nextInt(listeNombre.size());
                int valeur = listeNombre.remove(r);

                terrainInitiale[l][c]= new Cases(valeur);
                terrainCourant[l][c]= new Cases(valeur);
                c+=2;
            }
            l++;
        }
    }


    // Creation du clone du terrain donne
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
                cases = new Cases(caseCourante.estMange(), caseCourante.getNbPoissons(), caseCourante.pingouinPresent());

                terrainClone[l][c] = cases;
                c+=2;
            }
            l++;
        }

        return terrainClone;
    }


    // Renvoie le terrain courant
    public Cases [][] getTerrain(){
        return terrainCourant;
    }


    // Ligne compris entre [0, nbLigne[, ligne est la position sur le terrain hexagonal
    // Colonne compris entre [0, nbColonne[, colonne est la position sur le terrain hexagonal
    public Cases getCase(int ligne, int colonne){
            if( ligne%2 ==1 ){// si ligne impaire
                return terrainCourant[ligne][colonne*2];
            }else{ // ligne paire
                return terrainCourant[ligne][colonne*2-1];
            }
    }

    // Attribut une case a une case du terrain
    public void setCase(Cases cases, int ligne, int colonne){

            if( ligne%2 ==1 ){// si ligne impaire
                terrainCourant[ligne][colonne*2+1] = cases;
            }else{ // ligne paire
                terrainCourant[ligne][colonne*2] = cases;
            }
    }


    // Renvoie le joueur courant
    public int quelJoueur(){
        return joueurCourant;
    }


    // Renvoie le numero du joueur suivant
    public void switchJoueur(){
        joueurCourant = (joueurCourant % nbJoueur) + 1;
    }


    // Renvoie 1 si on peut jouer le coup cp, 0 sinon.
    // (Il faut que la case soit libre (donc qu'elle existe 
    // encore et qu'aucun pingouin ne soit dessus) et qu'elle soit atteignable)
    // A TERMINER (reste les diagonales)
    public boolean peutJouer(Coup cp){
        Cases c = getCase(cp.getLigne(), cp.getColonne());

        return !c.estMange() && !pingouinPresent(cp.getLigne(), cp.getColonne());
    }


    // Joue un coup sur le terrain courant
    public void joue(Coup cp, int joueurCourant){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        if (peutJouer(cp) && (joueurCourant == quelJoueur())){
            Cases caseArrive = getCase(l,c);

            Pingouin ping = cp.getPingouin();
            Joueur joueur = cp.getJoueur();
            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseArrive.getNbPoissons());

            caseDep.setPingouin(false); // A verifier !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);
            caseArrive.setNbPoissons(0);
            caseArrive.setPingouin(true);

            
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();

            switchJoueur();

        }
        else {
            // Pour les tests
            System.out.println("Impossible de jouer\n");
        }
    }


    // Joue un coup sans objet coup
    public void joue(int numEq, int numPing, int ligne, int colonne){

    }
    

    // Renvoie 1 si on peut annuler un coup
    public boolean peutAnnuler(){
        return (!(coupJoue.size() < 1));
    }

    /**
     * Annule un coup
     */
    public void annule(){    
        if(peutAnnuler()){
        }
    

    }

    
    // Renvoie 1 s'il est possible de refaire un coup annule
    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }


    // Refait un coup annule
    public void refaire(){
        if(peutRefaire()){

        }
    }


    // Sauvegarde la partie dans un fichier de nom name
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

			//stocker tous les coups joues
			int tailleList = coupJoue.size();

			//stocke tous les coups
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

    // Affichage terrain et informations courantes
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
				"\n- peut annuler : " + peutAnnuler() +
				"\n- peut refaire : " + peutRefaire();
		return result;
    }
    
}
