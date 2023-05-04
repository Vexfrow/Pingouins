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
    private int nbPingouinPlace;

    private boolean jeuTermine = false;


    private int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus


    /*
     * Annonce si le jeu est terminé
     */
    public boolean estTermine(){
        return this.jeuTermine;
    }

    /*
     * Met à jour la variable Jeutermine si les pingouins ne peuvent plus bouger
     */
    public boolean jeuTermine(){
        
        //creation de la liste des pingouins
        ArrayList<Pingouin> p = new ArrayList<>();

        //parcours de tous les joueurs
        for(int i =0; i< listeJoueur.size(); i++){
            
            //on recupere le joueur
            Joueur j = listeJoueur.get(i);

            //parcours de tous les pingouins d'un joueur
            for(int k = 0; k<j.listePingouin.size(); k++){
                //ajout à la liste des pingouins
                p.add(j.listePingouin.get(k));
            }

        }

        boolean termine = true;
        int l = 0;

        //parcour tous les pingouins pour savoir si ils sont bloqués
        while(l < p.size() && termine){
            //regarde si le pingouin est bloqué
            termine = estPinguoinBloque(p.get(l));
            l++;
        }

        jeuTermine = termine;

        this.jeuTermine = jeuTermine;

        return jeuTermine;

    }


    //public boolean peutJouer


    /*
     * Annonce si le pinguoin est bloqué (true) ou non (false)
     */
    public boolean estPinguoinBloque(Pingouin ping){
        ArrayList<Position> casesAccesible = getCaseAccessible(ping);
        return (casesAccesible.size() == 0);
    }


    /*
     * Annonce s'il reste des pingouin a placer
     */
    public boolean pingouinTousPlace(){
        return (nbPingouinPlace == 0);
    }







    /*
     * Construction du jeu avec une sauvegarde
     */
    Jeu(String name){

        try {

            //pour le test
            name = "test.txt";

            //init des arrays
            coupAnnule = new ArrayList<Coup>();
            coupJoue = new ArrayList<Coup>();
            listeJoueur = new ArrayList<Joueur>();


       	    //ouverture fichier
            FileReader reader = new FileReader(name);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            //recuperer le nombre de joueurs
            line = bufferedReader.readLine();
            this.nbJoueur = Integer.parseInt(line);

            //recuperer le nombre de lignes
            line = bufferedReader.readLine();
            this.nbLignes = Integer.parseInt(line);

            //recuperer le nombre de collones
            line = bufferedReader.readLine();
            this.nbColonnes = Integer.parseInt(line);

            // taille du tableau de la matrice
            this.nbColonnes = nbColonnes*2+1; 
    
            Joueur player;
            int i = 1;

            //init des joueurs
            while(i <= nbJoueur){
                player = new Joueur(i,0);
                listeJoueur.add(player);
                i++;
            }

            //nombre de pingouin en fonction du nombre de joueurs
            if(nbJoueur == 2){
                this.nbPingouin =4;
                this.nbPingouinPlace = this.nbPingouin*2;
            }else if (nbJoueur == 3){
                this.nbPingouin =3;
                this.nbPingouinPlace = this.nbPingouin*3;
            } else {
                this.nbPingouin =2;
                this.nbPingouinPlace = this.nbPingouin*4;
            }
    
                //creation terrain
    		terrainInitiale = new Cases[nbLignes][nbColonnes];
            terrainCourant = new Cases[nbLignes][nbColonnes];

                //recuperer le terrain
            int l =0;
            int c =0;

            //recuprer toutes les lignes du tableau
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(", ");

                c=0;

                //récupére toute la ligne !!!! omg c trop bien
                for(int m =0; m < parts.length; m++){
                    if(!parts[m].equals("null")){
                        Cases cases = new Cases(false, Integer.parseInt(parts[m]), 0);
                        
                        //place la case précédement crée à la bonne place
                        setCase(cases, l, c);
                        c++;
                        
                    }
                    
                }

                l++;

    	    }

            //save le terrrain initiale
            terrainInitiale = clonerTerrain(terrainCourant);


            /* 
    		//recuprer tous les coups à jouer
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));

                //definir un nouveau  coup
                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), ping , Boolean.parseBoolean(parts[6]));


                if(Boolean.parseBoolean(parts[6])){
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }else{
                    //jouer le coup

                    //bug ici
                    joue(cp);
                }

    	    }

    		//recuprere les dernieres lignes du fichier
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

    			//split la ligne
				String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));

				//definir un nouveau  coup
				Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), ping , Boolean.parseBoolean(parts[6]));

				//ajoute le coup à l'arraylist annule
				coupAnnule.add(cp);

	        }

            */

    		//fermer le fichier
    		reader.close();
    		
		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}

    }



     // Construction du jeu avec un nombre de joueur uniquement

    public Jeu(int nbJoueur){
        this(nbJoueur,8,8);
    }


    /*
     * Constructeur pour la méthode annule
     */
    Jeu(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab){
        //cloner le terrain pour pouvoir avoir le terrian de base
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);

        //init des arrays
        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        listeJoueur = new ArrayList<Joueur>();

        Joueur player;
        int i = 1;

        //creation des joueurs
        while(i <= nbJoueur){
            player = new Joueur(i,0);
            listeJoueur.add(player);
            i++;
        }

        //nombre de pingouin en fonction du nombre de joueurs
        if(nbJoueur == 2){
            this.nbPingouin =4;
            this.nbPingouinPlace = this.nbPingouin*2;
        }else if (nbJoueur == 3){
            this.nbPingouin =3;
            this.nbPingouinPlace = this.nbPingouin*3;
        } else {
            this.nbPingouin =2;
            this.nbPingouinPlace = this.nbPingouin*4;
        }

        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;
        this.nbJoueur = nbJoueur;

    }



    /*
     * Constructeur initial
     */
    Jeu(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        coupAnnule = new ArrayList<Coup>();
        coupJoue = new ArrayList<Coup>();
        listeJoueur = new ArrayList<Joueur>();

        Joueur player;
        int i = 1;

        //init des joueurs
        while(i <= nbJoueur){
            player = new Joueur(i,0);
            listeJoueur.add(player);
            i++;
        }

        //nombre de pingouin en fonction du nombre de joueurs
        if(nbJoueur == 2){
            this.nbPingouin =4;
            this.nbPingouinPlace = this.nbPingouin*2;
        }else if (nbJoueur == 3){
            this.nbPingouin =3;
            this.nbPingouinPlace = this.nbPingouin*3;
        } else {
            this.nbPingouin =2;
            this.nbPingouinPlace = this.nbPingouin*4;
        }


        this.nbJoueur = nbJoueur;
        this.nbColonnes = nbColonnes*2-1; // taille du tableau               ??????????????????????????????????????????????????????????????????????????????
        this.nbLignes = nbLignes;          // taille du tableau contenant le terrain

        //initialisation du terrain
        terrainAleatoire(nbLignes, nbColonnes);
    }


    /*
     * creation du terrain avec placement des tuiles aléatoire
     */
    public void terrainAleatoire(int nbLignes, int nbColonnes){
        int l = 0;
        int c,r;

        //array list pour la liste des valeurs possibles pour le nb de poisson
        ArrayList<Integer> listeNombre = new ArrayList<Integer>();

        //init le nombre des cases
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

        //parcours de toute la matrice pour placer les cases
        while( l < nbLignes ){

            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire
                c = 1;
            }

            while( c < nbColonnes*2-1){

                //tirage d'un indice du tableau
                r = rand.nextInt(listeNombre.size());

                //recupération de la valeur de l'indice du tableau
                int valeur = listeNombre.remove(r);

                //init la case
                terrainInitiale[l][c]= new Cases(valeur);
                terrainCourant[l][c]= new Cases(valeur);

                c+=2;
            }

            l++;
        }
    }


    /*
     * Annonce si un pingouin esr présent à la case l, c
     */
    public boolean pingouinPresent(int l,int c){
        return (getCase(l,c).pingouinPresent()!= 0);
    }


    /*
     * Place un pingouin sur la case l, c
     */
    public boolean placePingouin(int l, int c){

        //récupération du joueur courant
        int joueurCourant = quelJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        //vérfication du placment
        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);

            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);

            //changment du joueur
            switchJoueur();

            //élaboration et joue un coup
            Coup cp = new Coup(l,c,ping,true);
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();

            //System.out.println("Pingouin placé en "+ l +" "+ c);

            nbPingouinPlace--;

            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }

    }

    /*
     * Refait le dernier coup de placement de Pingouin annulé
     */
    private boolean placePingouinAnnuler(Coup cp){

        //récupération joueur
        int joueurCourant = quelJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        //récupération coordonnée coup
        int l = cp.getLigne();
        int c = cp.getColonne();


        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            //création pingouin et ajout liste pingouin joueur
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);


            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);

            //changement joueur
            switchJoueur();

            //joue un nouveau coup
            Coup coup = new Coup(l,c,ping,true);
            coupJoue.add(coup);

            nbPingouinPlace--;

            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }
    }


    /*
     * Creation du clone du terrain donne
     */
    public Cases [][] clonerTerrain(Cases [][] terrainInitiale){

        Cases [][] terrainClone;
        Cases caseCourante;
        Cases cases;

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
            }else{ // ligne paire décalage de 1 dans la matrice
                c = 1;
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                caseCourante = terrainInitiale[l][c];

                //creation d'une nouvelle case
                cases = new Cases(caseCourante.estMange(), caseCourante.getNbPoissons(), caseCourante.pingouinPresent());

                terrainClone[l][c] = cases;
                c+=2;
            }
            l++;
        }

        return terrainClone;
    }

    /*
     * Donne le score du joueur
     */
    public int getScore(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getScore();
    }

    public int getNbCases(){
        int nbCases = 0;
        int l =0;
        int c =0;

        while( l < this.nbLignes){
            if( l%2 ==1 ){// si ligne impaire
                c = 0;
            }else{ // ligne paire décalage de 1 dans la matrice
                c = 1;
            }

            //boucle sur toutes les colonnes
            while( c < this.nbColonnes){
                nbCases++;
                c+=2;
            }

            l++;
        }

        return nbCases;
    }


    /*
     * Donne le terrain courant
     */
    public Cases [][] getTerrain(){
        return terrainCourant;
    }

    /*
     * Donne la liste des joueurs
     */
    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }

    /* Deux méthodes suivantes pour les tests, les enlever
     * si necessaire
     */
    public ArrayList<Coup> getListeCoupsAnnules(){
        return this.coupAnnule;
    }

    public ArrayList<Coup> getListeCoupsJoues(){
        return this.coupJoue;
    }


    /*
     * Donne la case à la ligne, collone données
     * Ligne comprise entre [0, nbLigne[, ligne est la position sur le terrain hexagonal
     * Colonne comprise entre [0, nbColonne[, colonne est la position sur le terrain hexagonal
     */
    public Cases getCase(int ligne, int colonne){

        //this.nbLignes > ligne && ligne >=0 && this.nbColonnes > colonne && colonne >= 0

        //si les coordonées sont valides
        if(coordValideTab(ligne, colonne)){

            if( ligne%2 ==1 ){// si ligne impaire
                return terrainCourant[ligne][colonne*2];
            }else{ // ligne paire décalage de 1
                return terrainCourant[ligne][colonne*2+1];
            }

        } else {

            System.out.println("impossible de récuperer la case ligne: "+ligne+ ", colonne:"+colonne + "");

            return null;
        }
    }


    /*
     * Set une cases à une case du terrain
     */
    public void setCase(Cases cases, int ligne, int colonne){
        if(this.nbLignes > ligne && ligne >=0 && this.nbColonnes > colonne && colonne >= 0){

            if( ligne%2 ==1 ){// si ligne impaire

                //en double pour les deux terrains (le courant et la save)
                terrainCourant[ligne][colonne*2] = cases;
                terrainInitiale[ligne][colonne*2] = cases;
            }else{ // ligne paire : décalage de 1 pour la colonne
                terrainCourant[ligne][colonne*2+1] = cases;
                terrainInitiale[ligne][colonne*2+1] = cases;
            }

        }else{
            System.out.println("impossible de mettre à jour la case ligne: " + ligne +", colonne:" + colonne );
        }
    }

    /*
     * Donne le joueur courant
     */
    public int quelJoueur(){
        return joueurCourant;
    }


    /*
     * Donne le numéro du joueur suivant
     */
    public void switchJoueur(){
        joueurCourant = (joueurCourant % nbJoueur) + 1;
    }


    /*
     * Annonce s'il est possible ou non de jouer
     */
    public boolean peutJouer(Coup cp){

        int ligne = cp.getLigne();
        int colonne = cp.getColonne();

        //tableau des position des cases accessibles
        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin());

        //System.out.println("taille des casses accessible est de: " + casesAccessible.size()+ " on va en (x,y)=("+ligne+","+colonne+")");

        int index = 0;

        //parcours de tout le tableau de position et comparaison des coordonée du coup et de la case accessible
        while( index <casesAccessible.size() && (casesAccessible.get(index).x !=ligne || casesAccessible.get(index).y != colonne)){
            //System.out.println("\n" + casesAccessible.get(index).x + " et y: "+ casesAccessible.get(index).y );
            index++;    
        }

        //regarde si c'est au bon tour de jeu

        //récupére l'objet  joueur courant

        Joueur j = listeJoueur.get(joueurCourant-1);

        ArrayList<Pingouin> p = new ArrayList<>();
        p = j.listePingouin;

        //on cherche le pinguoin dans la liste des pingouins du joueur
        int k =0;
        while(p.get(k) != cp.getPingouin() && k <p.size()){
            k++;
        }

        boolean bonPinguoin = false;

        //on a trouvé le pingouin en question
        if(k<p.size()){
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }
        
        return (index != casesAccessible.size() && bonPinguoin);
    }


    /*
     * Annonce si les coordonées passées sont des coordonnées valides de la matrice
     */
    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }

    /*
     *Donne les cases accessibles a un pingoin
     */
    public ArrayList<Position> getCaseAccessible(Pingouin ping){
        int yPing;
        int xPing;
        int x;
        int y;

        Position position;
        Cases cases;

        //sauvegarde de la postion du pingouin et conversion des coordonée en case du plateau
        if((ping.getLigne()%2 ==0)){
            yPing = ping.getColonne()*2+1;
        }else {
            yPing = ping.getColonne()*2;
        }

        xPing = ping.getLigne();
        x = xPing;
        y = yPing;

        //tableau des position des cases accessibles
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
        // int j = 0;
        // while(j < caseAccessible.size()){
        //     System.out.println("x:"+caseAccessible.get(j).x +" y:"+caseAccessible.get(j).y);
        //     j++;
        // }
        return caseAccessible;
    }



    /*
     * Joue un coup sur le terrain courant
     */
    public void joue(Coup cp){

        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = quelJoueur();

        if (peutJouer(cp)){
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();

            // regarder si on a les meme adresse pour les deux lignes précédentes ......................................................
            ping = joueur.getPingouin(ping);


            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
 
            caseDep.setPingouin(0);
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);
            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);

            coupJoue.add(cp);

            //init d'un nouveau tableau de coup Annuler
            coupAnnule = new ArrayList<Coup>();

            //changement du joueur
            switchJoueur();


        }
        else {
            System.out.println("Impossible de jouer");
            
        }

    }


    /*
     * Rejoue un coup annulé
     */
    public void joueAnnuler(Coup cp){

        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = quelJoueur();

        if (peutJouer(cp)){
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            //conservaton d'ou vient le pingouin
            cp.getPingouin().setLigne(ping.getLigne());
            cp.getPingouin().setColonne(ping.getColonne());

            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
 
            caseDep.setPingouin(0);
            caseDep.setMange(true);

            

            //modification du pingouin
            ping.setLigne(l);
            ping.setColonne(c);
            
            //modification cases
            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);
            
            //coupJoue.add(cp);

            switchJoueur();

        }
        else {
            System.out.println("Impossible de jouer\n");
        }
    }


    /*
     * Annonce s'il est possible d'annuler un coup
     */
    public boolean peutAnnuler(){
        return (!(coupJoue.size() < 1));
    }


    /**
     * Annule un coup
     */
    public void annule(){   

        if(peutAnnuler()){

            //clone le terrain intiale pour rejouer tous les coups depuis ce terrain
            Cases[][] terrainInit = clonerTerrain(this.terrainInitiale);

            //creation d'un nouveau jeu pour tout refaire les coups
            Jeu j = new Jeu(terrainInit, this.nbJoueur, this.nbLignes, this.nbColonnes);
            Coup cp;

            //met le coup annuler dans le tab des coups annulés
            coupAnnule.add(coupJoue.get(coupJoue.size()-1));

            //enleve le coup annuler au tableau des coups fait
            coupJoue.remove(coupJoue.size()-1);

            int i = 0;

            //remise à zéro du nombre de pinguoin à placer
            this.nbPingouinPlace =nbPingouin;

            //on rejoue tous les coups jusqu'à l'avant dernier
            while(i < coupJoue.size()){
                cp = coupJoue.get(i);

                //si le coup est de placer un pingouin
                if(cp.place == true){
                    j.placePingouin(cp.getLigne(),cp.getColonne());
                }else{ //sinon on joue un coup classique
                    j.joue(cp);
                }

                i++;
            }

            this.terrainCourant = j.getTerrain();
            this.listeJoueur = j.getListeJoueur();
            this.joueurCourant = j.quelJoueur();
        }

    }

    
    /*
     * Annonce si il est possible de refaire un coup
     */
    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }

    /*
     * Refait le dernier coup précédement annulé
     */
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


    /*
     * Sauvegarde du jeu avec un nom de fichier
     */
    public void sauvegarder(String name){

        try {

			FileWriter w = new FileWriter("test.txt");
			
                //stockage des diffferentes informations e bases
            w.write(nbJoueur + "\n");
			w.write(nbLignes + "\n");
			w.write(((nbColonnes-1)/2) + "\n");

                //stocker le terrain de base
            String result = "";
		    String sep = "";
            String tmp = "";

            //boucle sur toutes les lignes
            for (int i=0; i<terrainCourant.length; i++) {
                tmp = Arrays.toString(terrainCourant[i]);
                result += sep + tmp.substring(1, tmp.length() -1);
                sep = "\n";
            }

            w.write(result + "\n");


            /*
        
             
            //marque pour indiquer que la suite sont des coups annules
			w.write("b\n");

			    //stocker tous les coups joues
			int tailleList = coupJoue.size();

            System.out.println("taille liste coup = " + tailleList);

			    //stocke tous les coups
			for(int i = 0; i< tailleList; i++) {
				w.write(coupJoue.get(i).getLigne() + " "+ coupJoue.get(i).getColonne() +" "+ coupJoue.get(i).getAncienX() + " " + coupJoue.get(i).getAncienY() + " " + coupJoue.get(i).getPingouin().getLigne()+ " " + coupJoue.get(i).getPingouin().getColonne() + " " + coupJoue.get(i).estPlace() + "\n");
			}

			    //marque pour indiquer que la suite sont des coups annules
			w.write("b\n");

			    //stocker tous les coups annules
			int tailleLista = coupAnnule.size();
			for(int i = 0; i< tailleLista; i++) {
				w.write(coupAnnule.get(i).getLigne() + " "+ coupAnnule.get(i).getColonne()+ " " + coupAnnule.get(i).getAncienX() + " " + coupAnnule.get(i).getAncienY() + " " + coupAnnule.get(i).getPingouin().getColonne()+ " " + coupAnnule.get(i).getPingouin().getLigne() + " " + coupJoue.get(i).estPlace() +"\n");
			}

            */

			    //fermer le fichier
			w.close();

		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}

    }

    /*
     * Affiche le terrain courant
     */
    public String toString(){
        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<terrainCourant.length; i++) {
			result += sep + Arrays.toString(terrainCourant[i]);
			sep = "\n ";
		}
        

		result += 	"]\nEtat:" +
				"\n- peut annuler : " + peutAnnuler() +
				"\n- peut refaire : " + peutRefaire();

        //terrain init
        //for (int i=0; i<terrainInitiale.length; i++) {
        //    result += sep + Arrays.toString(terrainInitiale[i]);
        //    sep = "\n ";
        //}
		return result;
    }
    
}
