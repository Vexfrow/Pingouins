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
    private int nbPingouinJoueur;
    private int nbPingouinPlace;

    private int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus

    public boolean jeuTermine(){
        
        ArrayList<Pingouin> p = new ArrayList<>();

        for(int i =0; i< listeJoueur.size(); i++){
            Joueur j = listeJoueur.get(i);
            for(int k = 0; k<j.listePingouin.size(); k++){
                p.add(j.listePingouin.get(k));
            }
        }
        boolean termine = true;
        int l = 0;
        while(l < p.size() && termine){
            termine = estPinguoinBloque(p.get(l));
            l++;
        }
        return termine;
    }

    public boolean estPinguoinBloque(Pingouin ping){
        ArrayList<Position> casesAccesible = getCaseAccessible(ping);
        return (casesAccesible.size() == 0);
    }


    
     //Annonce s'il reste des pingouin a placer
    public boolean pingouinTousPlace(){
        return (nbPingouinPlace == 0);
    }


    Jeu(String name){
        try {
            coupAnnule = new ArrayList<Coup>();
            coupJoue = new ArrayList<Coup>();
            listeJoueur = new ArrayList<Joueur>();
            FileReader reader = new FileReader(name);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            line = bufferedReader.readLine();
            this.nbJoueur = Integer.parseInt(line);

            line = bufferedReader.readLine();
            this.nbLignes = Integer.parseInt(line);
            line = bufferedReader.readLine();
            this.nbColonnes = Integer.parseInt(line);
            this.nbColonnes = nbColonnes*2+1; 
    
            Joueur player;
            int i = 1;

            while(i <= nbJoueur){
                player = new Joueur(i,0);
                listeJoueur.add(player);
                i++;
            }

            if(nbJoueur == 2){
                this.nbPingouinJoueur =4;
                this.nbPingouinPlace = nbPingouinJoueur*2;
            }else if (nbJoueur == 3){
                this.nbPingouinJoueur =3;
                this.nbPingouinPlace = nbPingouinJoueur*3;
            } else {
                this.nbPingouinJoueur =2;
                this.nbPingouinPlace = nbPingouinJoueur*4;
            }
    		terrainInitiale = new Cases[nbLignes][nbColonnes];
            terrainCourant = new Cases[nbLignes][nbColonnes];
            int l =0;
            int c =0;
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                String[] parts = line.split(", ");
                c=0;
                for(int m =0; m < parts.length; m++){
                    if(!parts[m].equals("null")){
                        Cases cases = new Cases(false, Integer.parseInt(parts[m]), 0);
                        setCase(cases, l, c);
                        c++;
                    }
                }
                l++;
    	    }
            terrainInitiale = clonerTerrain(terrainCourant);
            
    		//recuprer tous les coups à jouer
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                //definir un nouveau  coup
                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));

                System.out.println(cp);

                if(Boolean.parseBoolean(parts[4])){
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }else{
                    //jouer le coup
                    joue(cp);
                }
    	    }

    		//recuprere les dernieres lignes du fichier
    		while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

				String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

				Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));

				//ajoute le coup à l'arraylist annule
				coupAnnule.add(cp);

	        }

    		reader.close();
		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}
    }


    public Jeu(int nbJoueur){
        this(nbJoueur,8,8);
    }


    //Constructeur pour la méthode annule
    Jeu(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab){
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);
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
        if(nbJoueur == 2){
            this.nbPingouinJoueur =4;
            this.nbPingouinPlace = nbPingouinJoueur*2;
        }else if (nbJoueur == 3){
            this.nbPingouinJoueur =3;
            this.nbPingouinPlace = nbPingouinJoueur*3;
        } else {
            this.nbPingouinJoueur =2;
            this.nbPingouinPlace = nbPingouinJoueur*4;
        }
        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;
        this.nbJoueur = nbJoueur;

    }

    
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
        if(nbJoueur == 2){
            this.nbPingouinJoueur =4;
            this.nbPingouinPlace = nbPingouinJoueur*2;
        }else if (nbJoueur == 3){
            this.nbPingouinJoueur =3;
            this.nbPingouinPlace = nbPingouinJoueur*3;
        } else {
            this.nbPingouinJoueur =2;
            this.nbPingouinPlace = nbPingouinJoueur*4;
        }
        this.nbJoueur = nbJoueur;
        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;
        terrainAleatoire(nbLignes, nbColonnes);
    }


    public void terrainAleatoire(int nbLignes, int nbColonnes){
        int l = 0;
        int c,r;
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
            if( l%2 ==1 ){
                c = 0;
            }else{ 
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

    public boolean pingouinPresent(int l,int c){
        return (getCase(l,c).pingouinPresent()!= 0);
    }


    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        if( (joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();

            Coup cp = new Coup(l,c,ping,true);
            coupJoue.add(cp);
            coupAnnule = new ArrayList<Coup>();
            nbPingouinPlace--;
            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }

    }


    private boolean placePingouinAnnuler(Coup cp){

        int joueurCourant = getJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        int l = cp.getLigne();
        int c = cp.getColonne();

        if( (joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            //création pingouin et ajout liste pingouin joueur
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();
            Coup coup = new Coup(l,c,ping,true);
            coupJoue.add(coup);
            nbPingouinPlace--;
            return true;
        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }
    }


    public Cases [][] clonerTerrain(Cases [][] terrainInitiale){

        Cases [][] terrainClone;
        Cases caseCourante;
        Cases cases;

        int nbl = terrainInitiale.length;
        int nbc = terrainInitiale[0].length;
        terrainClone = new Cases[nbl][nbc];

        int c;
        int l=0;
        while( l < nbl){
            if( l%2 ==1 ){
                c = 0;
            }else{ 
                c = 1;
            }
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


    public int getScore(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getScore();
    }


    public int getNbCases(){
        int nbCases = 0;
        int l =0;
        int c =0;
        while( l < this.nbLignes){
            if( l%2 ==1 ){
                c = 0;
            }else{ 
                c = 1;
            }
            while( c < this.nbColonnes){
                nbCases++;
                c+=2;
            }
            l++;
        }
        return nbCases;
    }


    public Cases [][] getTerrain(){
        return terrainCourant;
    }


    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }


    public ArrayList<Coup> getListeCoupsAnnules(){
        return this.coupAnnule;
    }

    
    public ArrayList<Coup> getListeCoupsJoues(){
        return this.coupJoue;
    }


    public Cases getCase(int ligne, int colonne){
        if(coordValideTab(ligne, colonne)){
            if( ligne%2 ==1 ){
                return terrainCourant[ligne][colonne*2];
            }else{
                return terrainCourant[ligne][colonne*2+1];
            }

        } else {

            System.out.println("impossible de récuperer la case ligne: "+ligne+ ", colonne:"+colonne + "");

            return null;
        }
    }

    public void setCase(Cases cases, int ligne, int colonne){
        if(this.nbLignes > ligne && ligne >=0 && this.nbColonnes > colonne && colonne >= 0){
            if( ligne%2 ==1 ){
                terrainCourant[ligne][colonne*2] = cases;
                terrainInitiale[ligne][colonne*2] = cases;
            }else{
                terrainCourant[ligne][colonne*2+1] = cases;
                terrainInitiale[ligne][colonne*2+1] = cases;
            }

        }else{
            System.out.println("impossible de mettre à jour la case ligne: " + ligne +", colonne:" + colonne );
        }
    }

    public int getJoueur(){
        return joueurCourant;
    }

    public void switchJoueur(){
        joueurCourant = (joueurCourant % nbJoueur) + 1;
    }

    public boolean peutJouer(Coup cp){

        int ligne = cp.getLigne();
        int colonne = cp.getColonne();

        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin());
        int index = 0;

        while( index <casesAccessible.size() && (casesAccessible.get(index).x !=ligne || casesAccessible.get(index).y != colonne)){
            index++;    
        }

        Joueur j = listeJoueur.get(joueurCourant-1);
        ArrayList<Pingouin> p = new ArrayList<>();
        p = j.listePingouin;
        int k =0;
        while(k <p.size() && p.get(k).equals(cp.getPingouin())){
            k++;
        }

        boolean bonPinguoin = false;
        
        if(k<p.size()){
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }
        
        return (index != casesAccessible.size() && bonPinguoin);
    }


    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }


    public ArrayList<Position> getCaseAccessible(Pingouin ping){
        int yPing, xPing, x, y;

        Position position;
        Cases cases;
        if((ping.getLigne()%2 ==0)){
            yPing = ping.getColonne()*2+1;
        }else {
            yPing = ping.getColonne()*2;
        }
        xPing = ping.getLigne();
        x = xPing;
        y = yPing;
        ArrayList<Position> caseAccessible = new ArrayList<Position>();

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

    
    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        int joueurCourant = getJoueur();

        if (peutJouer(cp)){
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
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
            coupAnnule = new ArrayList<Coup>();
            switchJoueur();
        } else {
            System.out.println("Impossible de jouer");
        }
    }

    public void joueAnnuler(Coup cp){

        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = getJoueur();

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
            ping.setLigne(l);
            ping.setColonne(c);
            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);
            switchJoueur();
        }else {
            System.out.println("Impossible de jouer\n");
        }
    }

    
    //Annonce s'il est possible d'annuler un coup
    public boolean peutAnnuler(){
        return (!(coupJoue.size() < 1));
    }

     //Annule un coup
    public void annule(){   

        if(peutAnnuler()){
            Cases[][] terrainInit = clonerTerrain(this.terrainInitiale);
            Jeu j = new Jeu(terrainInit, this.nbJoueur, this.nbLignes, this.nbColonnes);
            Coup cp;
            coupAnnule.add(coupJoue.get(coupJoue.size()-1));
            coupJoue.remove(coupJoue.size()-1);
            int i = 0;

            //remise à zéro du nombre de pinguoin à placer
            this.nbPingouinPlace = nbPingouinJoueur;

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
            this.joueurCourant = j.getJoueur();
        }
    }
    

    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }


    public void refaire(){
        if(peutRefaire()){
            Coup cp = coupAnnule.get(coupAnnule.size()-1);
            if(cp.place == true){
                placePingouinAnnuler(coupAnnule.get(coupAnnule.size()-1));
            }else{
                joueAnnuler(coupAnnule.get(coupAnnule.size()-1));
                coupJoue.add(cp);
            }
            coupAnnule.remove(coupAnnule.size()-1);
        }
    }


    public void sauvegarder(String name){
        try {

			FileWriter w = new FileWriter(name);
			
            w.write(nbJoueur + "\n");
			w.write(nbLignes + "\n");
			w.write(((nbColonnes-1)/2) + "\n");
            String result = "";
		    String sep = "";
            String tmp = "";

            for (int i=0; i<terrainCourant.length; i++) {
                tmp = Arrays.toString(terrainCourant[i]);
                result += sep + tmp.substring(1, tmp.length() -1);
                sep = "\n";
            }
            w.write(result + "\n");

            //marque pour indiquer que la suite sont des coups annules
			w.write("b\n");

			int tailleList = coupJoue.size();

            System.out.println("taille liste coup = " + tailleList);

			for(int i = 0; i< tailleList; i++) {
				w.write(coupJoue.get(i).getLigne() + " "+ coupJoue.get(i).getColonne()  + " " + coupJoue.get(i).getPingouin().getLigne()+ " " + coupJoue.get(i).getPingouin().getColonne() + " " + coupJoue.get(i).estPlace() + "\n");
			}

			w.write("b\n");

			int tailleLista = coupAnnule.size();
			for(int i = 0; i< tailleLista; i++) {
				w.write(coupAnnule.get(i).getLigne() + " "+ coupAnnule.get(i).getColonne() + " " + coupAnnule.get(i).getPingouin().getColonne()+ " " + coupAnnule.get(i).getPingouin().getLigne() + " " + coupJoue.get(i).estPlace() +"\n");
			}

			w.close();
		} catch (IOException e) {
			System.out.print("Erreur : " + e);
		}
    }

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
		return result;
    }
}
