package Model;


import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class Jeu{

    protected Cases [][] terrainCourant;
    protected ArrayList<Joueur> listeJoueur;

    protected int nbLignes; // taille du tableau
    protected int nbColonnes; // taille du tableau

    protected int nbJoueur;
    protected int nbPingouinJoueur;
    protected int nbPingouinPlace;

    protected int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus


    public final static int ETAT_INITIAL = 0; //Etat de base
    public final static int ETAT_PLACEMENTP = 1; //Highlight sur les hexagones disponibles pour placer le pingouin
    public final static int ETAT_SELECTIONP = 2; //Highlight sur les pingouins que le joueur peut utiliser
    public final static int ETAT_CHOIXC = 3;//Highlight sur les hexagones disponibles pour déplacer le pingouin choisi
    public final static int ETAT_FINAL = 4;

    protected Cases [][] terrainInitiale;
    protected ArrayList<Coup> coupJoue;
    protected ArrayList<Coup> coupAnnule;

    //tableau pour stocker les scores de chaque joueur au chargement 
    public int [] scoreSave;
    public int [] nbCasesMangeSave;


    //tableau pour récupere si les joeurs sont des IA ou non
    public int [] IATab;


    private Position selectionP;
    private boolean selection;
    int etat;

    //attribut pour la reflexion de l'IA
    public boolean IA;


    
    //constructeur vide pour la classe fille
    public Jeu(){

    }

    public Jeu(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc, boolean ia){
        terrainCourant = clonerTerrain(terrain);
        listeJoueur = new ArrayList<>();
        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());
        }
        this.IA = ia;
        this.nbLignes = l; // taille du tableau
        this.nbColonnes = c; // taille du tableau
        this.nbJoueur = j;
        this.nbPingouinJoueur = pj;
        this.nbPingouinPlace = pp;
        this.joueurCourant = jc;
    }


    public Jeu(Jeu jeu, boolean IA ){
        this.terrainCourant = jeu.clonerTerrain(jeu.getTerrain());

        int i = 0;
        this.listeJoueur = new ArrayList<Joueur>();
        while( i < jeu.getListeJoueur().size()){
            this.listeJoueur.add(jeu.getListeJoueur().get(i).cloner());
            i++;
        }

        i = 1;
        Joueur player;

        while(i <= nbJoueur){
            player = new Joueur(i,0,0, IATab[i-1]);
            listeJoueur.add(player);
            i++;
        }

        initNbPingouins(nbJoueur);

        this.IATab = new int[nbJoueur];

        this.IA = IA;
        this.nbLignes = jeu.getNbLigne();
        this.nbColonnes = jeu.getNbColonne();
        this.nbJoueur = jeu.getNbJoueur();
        this.nbPingouinJoueur = jeu.getNbPingouinJoueur();
        this.nbPingouinPlace = jeu.getNbPingouinPlace();
        this.joueurCourant = jeu.getJoueurCourant();
    }


    public Jeu(String name){
        try {

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


            initArrays(nbJoueur);


            //récup le score des joueurs
            scoreSave = new int [nbJoueur];

            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                scoreSave[i] = Integer.parseInt(line);
            }

            //récup le nombre de cases mangé par un joueur lors de la partie

            nbCasesMangeSave = new int [nbJoueur];
            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                nbCasesMangeSave[i] = Integer.parseInt(line);
            }


            //récup le type des joeurs (IA ou non)
            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                IATab[i] = Integer.parseInt(line);
            }
            

            Joueur player;
            int i = 1;

            while(i <= nbJoueur){
                player = new Joueur(i,0,0, IATab[i-1]);
                listeJoueur.add(player);
                i++;
            }

            //init le nombre de pingouins à placer et le nombre de pingouins par joueur
            initNbPingouins(nbJoueur);

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
            System.out.println("Liste Coup");
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));
                System.out.println("Erreur de placement ? Etat = "  + getEtat());
                if(Boolean.parseBoolean(parts[4])){
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }else{
                    joue(cp);
                }
            }
            System.out.println("Au final = " + getEtat());

            //recuprere les coups annulés
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));
                coupAnnule.add(cp);
            }
            System.out.println("A la fin = " + getEtat());
            reader.close();
        } catch (IOException e) {
            System.out.print("Erreur : " + e);
        }
    }



    public Jeu(int nbJoueur){
        this(nbJoueur,8,8);
    }


    //Constructeur pour la méthode annule
    Jeu(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab, int[] IATab){
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);

        this.coupAnnule = new ArrayList<Coup>();
        this.coupJoue = new ArrayList<Coup>();
        this.listeJoueur = new ArrayList<Joueur>();

        this.IATab = IATab;

        int i = 1;
        Joueur player;

        while(i <= nbJoueur){
            player = new Joueur(i,0,0, IATab[i-1]);
            listeJoueur.add(player);
            i++;
        }


        //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbJoueur = nbJoueur;
        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;

    }


    public Jeu(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(nbJoueur);
        this.IATab = new int[nbJoueur];

        initNbJoueur(nbJoueur);

        //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;
        terrainAleatoire(nbLignes, nbColonnes);

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;

    }

    public Jeu(ArrayList<Joueur> ar){
        this(ar, 8,8);
    }

    //constructeru avec une liste de joueur
    public Jeu(ArrayList<Joueur> ar, int nbLignes, int nbColonnes){

        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(ar.size());

        nbJoueur = ar.size();

        this.IATab = new int[nbJoueur];

        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());

            //stock si le joeur est une IA ou non
            IATab[i] = ar.get(i).estIA();
        }

        initNbPingouins(ar.size());

        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;

        terrainAleatoire(nbLignes, nbColonnes);

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;
    
    }

    

    /*********************************************************************** BUG IA REFAIRE ICI ???? */

    //init les joeurs et remplis la liste des joueurs
    public void initNbJoueur(int nbJoueur){
        int i = 1;
        Joueur player;

        while(i <= nbJoueur){
            player = new Joueur(i,0,0, 0);
            listeJoueur.add(player);
            IATab[i-1] =0;
            i++;
        }

        this.nbJoueur = nbJoueur;
    }

    /*********************************************************************** BUG IA REFAIRE ICI ???? */

    //init les arrayLists de la classe
    public void initArrays(int nbJoueur){
        this.coupAnnule = new ArrayList<Coup>();
        this.coupJoue = new ArrayList<Coup>();
        this.listeJoueur = new ArrayList<Joueur>();
        this.IATab = new int[nbJoueur];
    }


    //Init les var nbPingouinJoeur et nbPingouinPlace
    public void initNbPingouins(int nbJoueurs){
        if(nbJoueurs == 2){
            this.nbPingouinJoueur =4;
            this.nbPingouinPlace = nbPingouinJoueur*2;
        }else if (nbJoueurs == 3){
            this.nbPingouinJoueur =3;
            this.nbPingouinPlace = nbPingouinJoueur*3;
        } else {
            this.nbPingouinJoueur =2;
            this.nbPingouinPlace = nbPingouinJoueur*4;
        }
    }

    //cree un terrain avec nombre de poissons aléatoire
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


    public ArrayList<Coup> getListeCoupsAnnules(){
        return this.coupAnnule;
    }

    public ArrayList<Coup> getListeCoupsJoues(){
        return this.coupJoue;
    }


    public void setCase(Cases cases, int ligne, int colonne){
        int val = 0;
        if (ligne%2==0)
            val = 1;
        if(coordValideTab(ligne, colonne*2+val)){
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

    
    //bool IA vrai que pour les instances de l'IA : toujours false sinon
    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueurCourant();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        if( (joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            etat = ETAT_PLACEMENTP;
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);

            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);

            switchJoueur();

            //l'IA n'enregistre pas de coup lors de sa reflexion
            if(!IA){
                Coup cp = new Coup(l,c,ping,true);

                coupJoue.add(cp);
                coupAnnule = new ArrayList<Coup>();
            }

            nbPingouinPlace--;

            if(nbPingouinPlace == 0){
                etat = ETAT_SELECTIONP;

                boolean passeTour = true;
                int i =0;
                joueur = listeJoueur.get(getJoueurCourant()-1);

                while(i< joueur.listePingouin.size() && passeTour){
                    Pingouin p = joueur.listePingouin.get(i);
                    passeTour = estPingouinBloque(p);
                    i++;
                }
                
                if (passeTour){
                    switchJoueur();
                }

            }

            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }
    }


    private boolean placePingouinAnnuler(Coup cp){

        int joueurCourant = getJoueurCourant();
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        int l = cp.getLigne();
        int c = cp.getColonne();

        if( (joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            etat = ETAT_PLACEMENTP;
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();

            Coup coup = new Coup(l,c,ping,true);
            coupJoue.add(coup);
            nbPingouinPlace--;

            if(nbPingouinPlace == 0){
                etat = ETAT_SELECTIONP;
            }

            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici ");
            return false;
        }
    }


    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        int joueurCourant = getJoueurCourant();

        etat = ETAT_SELECTIONP;
        retirePingouin();

        if (peutJouer(cp)){
            
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
            joueur.setNbCasesMange(joueur.getNbCasesMange()+1);
 
            caseDep.setPingouin(0);
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);

            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);

            //pour optimiser la réflexion de l'ia on ne stocke pas les coups
            if(!IA){
                coupJoue.add(cp);
                coupAnnule = new ArrayList<Coup>();
            }


            //on enlève un pingouin dés qu'il est bloqué
            retirePingouin();

            switchJoueur();
            
            if(jeuTermine()){
                etat = ETAT_FINAL;
            }

        }
        else {
            System.out.println("JeuA: joue() - Impossible de jouer en :" + cp);
            retirePingouin();
        }
    }


    public void retirePingouin(){

        for(int i =0; i< listeJoueur.size(); i++){
            Joueur joueur = listeJoueur.get(i);
            for(int k = 0; k<joueur.listePingouin.size(); k++){
                Pingouin ping = joueur.listePingouin.get(k);
                Cases casesCourante = getCase(ping.getLigne(), ping.getColonne());
                if(estPingouinBloque(ping) && !casesCourante.estMange()){
                    joueur.setScore(joueur.getScore() + casesCourante.getNbPoissons());
                    joueur.setNbCasesMange(joueur.getNbCasesMange() +1);
                    casesCourante.setMange(true);
                    casesCourante.setNbPoissons(0); 
                    casesCourante.setPingouin(0);
                    joueur.listePingouin.remove(k);
                }
            }
        }
    }


    public void joueAnnuler(Coup cp){

        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = getJoueurCourant();
        etat = ETAT_SELECTIONP;

        retirePingouin();
        if (peutJouer(cp)){

            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            //diff
            //conservaton d'ou vient le pingouin
            cp.getPingouin().setLigne(ping.getLigne());
            cp.getPingouin().setColonne(ping.getColonne());

            Cases caseDep = getCase(ping.getLigne(),ping.getColonne());
            joueur.setScore(joueur.getScore()+caseDep.getNbPoissons());
            joueur.setNbCasesMange(joueur.getNbCasesMange()+1);

            caseDep.setPingouin(0);
            caseDep.setMange(true);

            ping.setLigne(l);
            ping.setColonne(c);

            caseDep.setNbPoissons(0);
            caseArrive.setPingouin(joueurCourant);

            retirePingouin();
            switchJoueur();

        }else {
            System.out.println("JeuA: joueAnnuler() - Impossible de jouer\n");
        }
    }


    public boolean peutAnnuler(){
        return (!(coupJoue.size() < 1));
    }


    public void annule(){   

        if(peutAnnuler()){
            Cases[][] terrainInit = clonerTerrain(this.terrainInitiale);
            Jeu j = new Jeu(terrainInit, this.nbJoueur, this.nbLignes, this.nbColonnes, IATab);
            Coup cp;
            if (coupJoue.get(coupJoue.size() - 1).estPlace() == true)
                this.nbPingouinPlace++;
            coupAnnule.add(coupJoue.get(coupJoue.size()-1));
            coupJoue.remove(coupJoue.size()-1);
            int i = 0;

            etat = ETAT_PLACEMENTP;

            while(i < coupJoue.size()){
                cp = coupJoue.get(i);
                if(cp.place == true){
                    j.placePingouin(cp.getLigne(),cp.getColonne());
                    if(nbPingouinPlace == 0){
                        etat = ETAT_SELECTIONP;
                    }
                }else{ 
                    etat = ETAT_SELECTIONP;
                    j.joue(cp);
                }
                i++;
            }
            this.terrainCourant = j.getTerrain();
            this.listeJoueur = j.getListeJoueur();
            this.joueurCourant = j.getJoueurCourant();
        }
        else {
            System.out.println("JeuA: annule() - Impossible d'annuler\n");
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
        else {
            System.out.println("JeuA: refaire() - Impossible de refaire\n");
        }
    }


    //Sauvegarde du jeu avec un nom de fichier
    public void sauvegarder(String name){
        try {

            FileWriter w = new FileWriter(name);
            
            //stocker les infos de bases
            w.write(nbJoueur + "\n");
            w.write(nbLignes + "\n");
            w.write(((nbColonnes-1)/2) + "\n");

            //stock le score des joueurs
            for(int i=0; i<nbJoueur; i++){
                w.write(listeJoueur.get(i).getScore() + "\n");
            }

            //stock le nb de cases mangé par les joueurs
            for(int i=0; i<nbJoueur; i++){
                w.write(listeJoueur.get(i).getNbCasesMange() + "\n");
            }

            //stock le type des joueurs (IA ou non)
            for(int i=0; i<nbJoueur; i++){
                w.write(IATab[i]+ "\n");
            }
            

            //stocker tout le terrain
            String result = "";
            String sep = "";
            String tmp = "";

            for (int i=0; i<terrainInitiale.length; i++) {
                tmp = Arrays.toString(terrainInitiale[i]);
                result += sep + tmp.substring(1, tmp.length() -1);
                sep = "\n";
            }

            w.write(result + "\n");

            //marque pour indiquer que la suite sont des coups joués
            w.write("b\n");

            //enregistrer tous les coups joués
            int tailleList = coupJoue.size();
            for(int i = 0; i< tailleList; i++) {
                w.write(coupJoue.get(i).getLigne() + " "+ coupJoue.get(i).getColonne()  + " " + coupJoue.get(i).getPingouin().getLigne()+ " " + coupJoue.get(i).getPingouin().getColonne() + " " + coupJoue.get(i).estPlace() + "\n");
            }

            w.write("b\n");

            //enregistrer tous les coups annulés
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
        // String result = "Plateau:\n[";
		// String sep = "";
		// for (int i=0; i<terrainCourant.length; i++) {
		// 	result += sep + Arrays.toString(terrainCourant[i]);
		// 	sep = "\n ";
		// }
		// result += 	"]\nEtat:" +
		// 		"\n- peut annuler : " + peutAnnuler() +
		// 		"\n- peut refaire : " + peutRefaire();
		// return result;


        String result ="Plateau:\n[";
        String line;
        Cases caseCourant;
        int l =0;
        int c =0;
        int nbc;

        while( l < terrainCourant.length){
            c = 0;
            if( l%2 == 1){// si ligne impaire
                line= "";
                nbc = 8;
            }else{
                nbc = 7;
                line = "   ";
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                line = line + "   ";
                caseCourant = getCase(l,c);
                if(caseCourant.estMange()){
                    line= line + "    ";
                }else{
                    line= line +caseCourant + "";
                }
                c++;
            }
            result+=line + "\n";
            l++;
        }
        return result;
    }

    public void setSelectionP(Position p) {
        selection = true;
        selectionP = p;
        etat = ETAT_CHOIXC;
    }

    public void unsetSelectionP() {
        selection = false;
        selectionP = null;
        etat = ETAT_SELECTIONP;
    }

    public boolean getSelection() {
        return selection;
    }

    public Position getSelectionP() {
        return selectionP;
    }

    public int getEtat() {
        return etat;
    }

    public void startGame() {
        etat = ETAT_PLACEMENTP;
    }

    public void changePlayer(int i, Joueur j){
        if(i > this.getListeJoueur().size()){
            System.err.println("Changement non permis");
        }else{
            this.getListeJoueur().set(i, j);
        }
    }

    /*
     * True si le jeu est terminé False sinon
     */
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
            termine = estPingouinBloque(p.get(l));
            l++;
        }

        if(termine){
            //System.out.println("fin");
            //on retire les dernier pingouins si ils sont bloqués
            retirePingouin();
        }

        return termine;
    }

    /*
     * Annonce si le pinguoin est bloqué (true) ou non (false)
     */
    public boolean estPingouinBloque(Pingouin ping){
        ArrayList<Position> casesAccesible = getCaseAccessible(ping.getLigne(), ping.getColonne());
        return (casesAccesible.size() == 0);
    }

    //Annonce s'il reste des pingouin a placer
    public boolean pingouinTousPlace(){
        return (nbPingouinPlace == 0);
    }

    public boolean pingouinPresent(int l,int c){
        return (getCase(l,c).pingouinPresent()!= 0);
    }

    public boolean peutPlacer(int i, int j){
        return(getCase(i,j).pingouinPresent() == 0 && getCase(i,j).getNbPoissons() == 1);
    }

    public boolean peutJouer(Coup cp){
        int ligne = cp.getLigne();
        int colonne = cp.getColonne();

        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin().getLigne(), cp.getPingouin().getColonne());
        int index = 0;

        while( index < casesAccessible.size() && (casesAccessible.get(index).x !=ligne || casesAccessible.get(index).y != colonne)){
            index++;    
        }

        Joueur j = listeJoueur.get(joueurCourant-1);
        ArrayList<Pingouin> p = new ArrayList<>();
        p = j.listePingouin;

        int k =0;

        while(k < p.size() && !p.get(k).equals(cp.getPingouin())){
            k++;
        }

        boolean bonPinguoin = false;

        if(k<p.size() ){
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }

        return (index != casesAccessible.size() && bonPinguoin && nbPingouinPlace == 0);
    }

    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }

    public ArrayList<Position> case1poisson(){
        ArrayList<Position> posPossible = new ArrayList<Position>();
        Cases caseCourant;
        Position posCourant;
        int l =0;
        int c =0;
        int nbc;

        while( l < terrainCourant.length){
            c = 0;
            if( l%2 == 1){// si ligne impaire
                nbc = 8;
            }else{
                nbc = 7;
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                caseCourant = getCase(l,c);
                if(caseCourant.getNbPoissons()==1 && caseCourant.pingouinPresent() == 0){
                    posCourant = new Position(l,c);
                    posPossible.add(posCourant);
                }
                c++;
            }
            l++;
        }
        return posPossible;

    }


    public int getScore(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getScore();
    }

    public int getcasesMange(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getNbCasesMange();
    }

    public int getNbCases(){
        int nbCases = 0;
        int l =0;
        int c =0;

        while( l < this.nbLignes){
            c = getDecalage(l);
            while( c < this.nbColonnes){
                nbCases++;
                c+=2;
            }
            l++;
        }

        return nbCases;
    }

    public int getDecalage(int l){
        if( l%2 ==1 ){
            return 0;
        }else{ 
            return 1;
        }
    }

    public int getNbLigne(){
        return this.nbLignes;
    }

    public int getNbColonne(){
        return this.nbColonnes;
    }

    public int getNbJoueur(){
        return this.nbJoueur;
    }

    public int getNbPingouinJoueur(){
        return this.nbPingouinJoueur;
    }

    public int getNbPingouinPlace(){
        return this.nbPingouinPlace;
    }

    public int getJoueurCourant(){
        return joueurCourant;
    }

    public Cases [][] getTerrain(){
        return this.terrainCourant;
    }

    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }

    public Cases getCase(int ligne, int colonne){
        int val = 0;
        if (ligne%2==0)
            val = 1;
        if(coordValideTab(ligne, colonne*2+val)){
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

    public ArrayList<Position> getCaseAccessible(int i, int j) {
        ArrayList<Position> caseAccessible = new ArrayList<>();
        int[][] directions = {
                {0, 2},  // droite
                {0, -2}, // gauche
                {1, -1}, // bas gauche
                {1, 1},  // bas droite
                {-1, -1},// haut gauche
                {-1, 1}  // haut droite
        };

        int yInit;
        if (i % 2 == 0) {
            yInit = j * 2 + 1;
        } else {
            yInit = j * 2;
        }

        int xInit = i;

        //recherhce dans toutes les directions
        for (int[] direction : directions) {
            int x = xInit + direction[0];
            int y = yInit + direction[1];
            while (coordValideTab(x, y)) {
                Cases cases = terrainCourant[x][y];
                if (cases == null || cases.estMange() || cases.pingouinPresent() != 0) {
                    break;
                }
                caseAccessible.add(new Position(x, y / 2));
                x += direction[0];
                y += direction[1];
            }
        }

        return caseAccessible;
    }


    //switchJoueur si le joueur ne peut pas joueur
    public void switchJoueur(){

        joueurCourant = (joueurCourant % nbJoueur) + 1;

        ArrayList<Pingouin> pingJoueur = new ArrayList<>();

        Joueur joueur = listeJoueur.get(joueurCourant-1);

        //init liste des pingouisn avec la liste des pingouins
        pingJoueur = joueur.getListePingouin();

        int i =0;
        boolean passeTour = (listeJoueur.get(joueurCourant-1).getListePingouin().size()!=0);

        if(pingJoueur.size() ==0 && etat ==ETAT_SELECTIONP && !jeuTermine()){
            switchJoueur();
            retirePingouin();
        } else {
            if(etat != ETAT_PLACEMENTP){
                while(i< pingJoueur.size() && passeTour){
                    Pingouin ping = pingJoueur.get(i);
                    passeTour = estPingouinBloque(ping);
                    i++;
                }
                retirePingouin();
            } else {
                passeTour = false;
            }
        }


        
        if(passeTour && !jeuTermine()){
            switchJoueur();
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
            c= getDecalage(l);
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

    public Jeu cloner(){
        Jeu j = new Jeu(this.terrainCourant, this.listeJoueur, this.nbLignes, this.nbColonnes, this.nbJoueur,
         this.nbPingouinJoueur, this.nbPingouinPlace, this.joueurCourant, this.IA);
        return j;
    }


    /* 
    @Override
    public String toString(){
        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<terrainCourant.length; i++) {
		 	result += sep + Arrays.toString(terrainCourant[i]);
		    sep = "\n ";
		}
        return result;

    
        String result ="Plateau:\n[";
        String line;
        Cases caseCourant;
        int l =0;
        int c =0;
        int nbc;

        while( l < terrainCourant.length){
            c = 0;
            if( l%2 == 1){// si ligne impaire
                line= "";
                nbc = 8;
            }else{
                nbc = 7;
                line = "  ";
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                line = line + "   ";
                caseCourant = getCase(l,c);
                if(caseCourant.estMange()){
                    line= line + "    ";
                }else{
                    line= line +caseCourant + "";
                }
                c++;
            }
            result+=line + "\n";
            l++;
        }
        return result;

    
    }
    */
    
}
