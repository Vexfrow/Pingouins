package Model;


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


    private int nbLignes; // taille du tableau
    private int nbColonnes; // taille du tableau
    private int nbJoueur;
    private int nbPingouin;

    private int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus

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
    Jeu(int nbJoueur){
        this(nbJoueur,8,8);
        

    }


    // Constructeur pour la methode annule()
    Jeu(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab){ 
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);
        System.out.println(this.terrainInitiale);
        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        listeJoueur = new ArrayList<Joueur>();
        Joueur player;
        int i = 1;
        while(i <= nbJoueur){
            player = new Joueur(i,0);
            listeJoueur.add(player);
            i++;
        }

        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;
        this.nbJoueur = nbJoueur;
        this.nbPingouin =1;

    }


    // Init du jeu avec des parametres
    Jeu(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        listeJoueur = new ArrayList<Joueur>();

        Joueur player;
        int i = 1;
        while(i <= nbJoueur){
            player = new Joueur(i,0);
            listeJoueur.add(player);
            i++;
        }

        this.nbPingouin =1;
        this.nbJoueur = nbJoueur;
        this.nbColonnes = nbColonnes*2-1; // taille du tableau
        this.nbLignes = nbLignes;          // taille du tableau contenant le terrain

        terrainAleatoire(nbLignes, nbColonnes);
    }

    // Creation du terrain aleatoirement de taille du terrain hexagonale donne en parametre
    public void terrainAleatoire(int nbLignes, int nbColonnes){
        int l = 0;
        int c,r;

        //array list pour la liste des valeurs possibles pour le nb de poisson
        ArrayList<Integer> listeNombre = new ArrayList<Integer>();

        for(int i = 1; i<=60; i++){
            if(i<= 60){
                listeNombre.add(1); // ATTENTION MODIFI2!!!
            }else if(i <60){
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





    // Renvoie 1 si un pingouin est present sur une case specifique
    public boolean pingouinPresent(int l,int c){
        return (getCase(l,c).pingouinPresent()!= 0);
    }


    // Place un pingouin sur une case hexagonale ligne l, colonne c
    public void placePingouin(int l, int c){
        int joueurCourant = quelJoueur(); 
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();
            Coup cp = new Coup(l,c,ping,true);
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();
        }else{
            System.out.println("Impossible de placer le pingouin ici");
        }
    }

    private void placePingouinAnnuler(Coup cp){
        int joueurCourant = quelJoueur(); 
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        int l = cp.getLigne();
        int c = cp.getColonne();
        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();
            Coup coup = new Coup(l,c,ping,true);
            coupJoue.add(cp);
        }else{
            System.out.println("Impossible de placer le pingouin ici");
        }
    }

    

    // Creation du clone du terrain donne
    public Cases [][] clonerTerrain(Cases [][] terrainInitiale){

        Cases [][] terrainClone;
        Cases caseCourante;
        Cases cases;
        Pingouin ping;
        int nbl = terrainInitiale.length;
        int nbc = terrainInitiale[0].length;

        //creation de la nouvelle matrice
        terrainClone = new Cases[nbl][nbc];
        int c;
        int l=0;
        
        //boucle sur toutes les lignes
        while( l < nbl){

            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire
                c = 1;
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
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

    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }


    // Ligne compris entre [0, nbLigne[, ligne est la position sur le terrain hexagonal
    // Colonne compris entre [0, nbColonne[, colonne est la position sur le terrain hexagonal
    public Cases getCase(int ligne, int colonne){
        if(this.nbLignes > ligne && ligne >=0 && this.nbColonnes > colonne && colonne >= 0){
            if( ligne%2 ==1 ){// si ligne impaire
                return terrainCourant[ligne][colonne*2];
            }else{ // ligne paire
                return terrainCourant[ligne][colonne*2+1];
            }
        } else {
            System.out.println("impossible de récuperer la case ligne: "+ligne+ ", colonne:"+colonne + "");
            return null;
        }
    }

    // Attribut une case a une case du terrain
    public void setCase(Cases cases, int ligne, int colonne){
        if(this.nbLignes > ligne && ligne >=0 && this.nbColonnes > colonne && colonne >= 0){
            if( ligne%2 ==1 ){// si ligne impaire
                terrainCourant[ligne][colonne*2] = cases;
            }else{ // ligne paire
                terrainCourant[ligne][colonne*2+1] = cases;
            }
        }else{
            System.out.println("impossible de mettre à jour la case ligne: " + ligne +", colonne:" + colonne );
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
        int ligne = cp.getPingouin().getLigne();
        int colonne = cp.getPingouin().getColonne();
        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin());
        int index = 0;
        System.out.println("taille des casses accessible est de :" + casesAccessible.size());
        while( index <casesAccessible.size() && casesAccessible.get(index).x !=ligne && casesAccessible.get(index).y != colonne){
            System.out.println("\n" + casesAccessible.get(index).x + " et y: "+ casesAccessible.get(index).y );
            index++;    
        }
        return (index != casesAccessible.size());
    }


    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }

        /**
        Donne les cases accessibles a un pingoin
     */
    public ArrayList<Position> getCaseAccessible(Pingouin ping){
        int yPing;
        int xPing;
        int x;
        int y;

        Position position;
        Cases cases;

        //sauvegarder la postion du pingouin et conversion des coordonée en case du plateau
        if((ping.getLigne()%2 ==0)){
            yPing = ping.getColonne()*2+1;
        }else {
            yPing = ping.getColonne()*2;
        }

        xPing = ping.getLigne();
        x = xPing;
        y = yPing;

        ArrayList<Position> caseAccessible = new ArrayList<Position>();

        //regarder dans toutes les directions

        //gauche à droite   
        y+=2;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            //ajout de la position de la case accesible
            caseAccessible.add(position);
            y = y+2;
        }
        

        //droite à gauche
        y=yPing-2;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y = y-2;
        }
        
        
        //bas gauche
        y = yPing -1;
        x = xPing +1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y--;
            x++;
        }
        //bas droite
        y = yPing +1;
        x = xPing +1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y++;
            x++;
        }

        //haut gauche
        y = yPing -1;
        x = xPing -1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y--;
            x--;
        }
        
        //haut droite
        y = yPing +1;
        x = xPing -1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y++;
            x--;
        }
        return caseAccessible;
    }



    // Joue un coup sur le terrain courant
    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = quelJoueur();

        if (peutJouer(cp)){
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
 
            caseDep.setPingouin(0); // A verifier !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);
            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);

            
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();

            switchJoueur();

        }
        else {
            // Pour les tests
            System.out.println("Impossible de jouer\n");
        }
    }

    public void joueAnnuler(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = quelJoueur();

        if (peutJouer(cp)){
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
 
            caseDep.setPingouin(0); // A verifier !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);
            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);

            
            coupJoue.add(cp);

            switchJoueur();

        }
        else {
            // Pour les tests
            System.out.println("Impossible de jouer\n");
        }
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

            Cases[][] terrainInit = clonerTerrain(this.terrainInitiale);
            Jeu j = new Jeu(terrainInit, this.nbJoueur, this.nbLignes, this.nbColonnes);
            Coup cp;

            coupAnnule.add(coupJoue.get(coupJoue.size()-1));
            coupJoue.remove(coupJoue.size()-1);

            int i = 0;
            while(i < coupJoue.size()){
                cp = coupJoue.get(i);
                if(cp.place == true){
                    j.placePingouin(cp.getLigne(),cp.getColonne());
                }else{
                    j.joue(cp);
                }
                i++;
            }
            this.terrainCourant = j.getTerrain();
            this.listeJoueur = j.getListeJoueur();
            this.joueurCourant = j.quelJoueur();
        }
    

    }

    
    // Renvoie 1 s'il est possible de refaire un coup annule
    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }

    // Refait un coup annule
    public void refaire(){
        if(peutRefaire()){
            Coup cp = coupAnnule.get(coupAnnule.size()-1);
            if(cp.place == true){
                placePingouinAnnuler(coupAnnule.get(coupAnnule.size()-1));
            }else{
                joueAnnuler(coupAnnule.get(coupAnnule.size()-1));
            }

            coupJoue.add(cp);
            coupAnnule.remove(coupAnnule.size()-1);

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
