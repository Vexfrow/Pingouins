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
    protected int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence comprit entre 1 et nbJoueur inclus

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

    //tableau pour recuperer si les joueurs sont des IA ou non
    public int [] IATab;

    private Position selectionP;
    private boolean selection;
    int etat;

    //attribut pour la reflexion de l'IA
    public boolean IA;



    // Constructeur qui clone le jeu entier
    public Jeu(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc, boolean ia, int [] IATab){
        terrainCourant = clonerTerrain(terrain);
        listeJoueur = new ArrayList<>();
        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());
        }
        this.nbLignes = l; // taille du tableau
        this.nbColonnes = c; // taille du tableau
        this.nbJoueur = j;
        this.nbPingouinJoueur = pj;
        this.nbPingouinPlace = pp;
        this.joueurCourant = jc;
        this.IA = ia;
        this.IATab = IATab;
    }


    // Constructeur pour creer un jeu depuis un fichier
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

            //récup le nombre de cases mangees par un joueur lors de la partie
            nbCasesMangeSave = new int [nbJoueur];
            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                nbCasesMangeSave[i] = Integer.parseInt(line);
            }

            //récup le type des joueurs (IA ou non)
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
                    if (!parts[m].equals("null")) {
                        Cases cases = new Cases(false, Integer.parseInt(parts[m]), 0);
                        setCase(cases, l, c);
                        c++;
                    }
                }
                l++;
            }

            terrainInitiale = clonerTerrain(terrainCourant);
            
            // recupere tous les coups à jouer
            System.out.println("Liste Coup");
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));
                System.out.println("Erreur de placement ? Etat = "  + getEtat());
                if (Boolean.parseBoolean(parts[4])) {
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                } else {
                    joue(cp);
                }
            }
            System.out.println("Au final = " + getEtat());

            // recupere les coups annules
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


    // Constructeur qui cree un jeu avec nbJoueur joueurs
    public Jeu(int nbJoueur){
        this(nbJoueur,8,8);
    }


    // Constructeur qui cree un jeu apres appel a la methode annule
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

        //Init le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbJoueur = nbJoueur;
        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;
    }


    // Constructeur qui cree un jeu avec nbJoueur joueurs de taille nbLignes x nbColonnes
    public Jeu(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(nbJoueur);
        initNbJoueur(nbJoueur);
        initNbPingouins(nbJoueur);

        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;
        terrainAleatoire(nbLignes, nbColonnes);

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;
    }


    // Constructeur pour creer un jeu depuis une liste de joueurs
    public Jeu(ArrayList<Joueur> ar){
        this(ar, 8,8);
    }


    // Constructeur avec une liste de joueur de terrain nbLignes x nbColonnes
    public Jeu(ArrayList<Joueur> ar, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(ar.size());
        nbJoueur = ar.size();

        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());

            //stocke si le joeur est une IA ou non
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


    // Initialise les joueurs et remplit la liste des joueurs
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


    // Initialise les ArrayLists de la classe
    public void initArrays(int nbJoueur){
        this.coupAnnule = new ArrayList<Coup>();
        this.coupJoue = new ArrayList<Coup>();
        this.listeJoueur = new ArrayList<Joueur>();
        this.IATab = new int[nbJoueur];
    }


    // Initialise les variables nbPingouinJoueur et nbPingouinPlace
    public void initNbPingouins(int nbJoueurs){
        if (nbJoueurs == 2) {
            this.nbPingouinJoueur =4;
            this.nbPingouinPlace = nbPingouinJoueur*2;
        } else if (nbJoueurs == 3) {
            this.nbPingouinJoueur =3;
            this.nbPingouinPlace = nbPingouinJoueur*3;
        } else {
            this.nbPingouinJoueur =2;
            this.nbPingouinPlace = nbPingouinJoueur*4;
        }
    }


    // Cree un terrain avec nombre de poissons aleatoire
    public void terrainAleatoire(int nbLignes, int nbColonnes){
        int l = 0;
        int c,r;
        ArrayList<Integer> listeNombre = new ArrayList<Integer>();
    
        for(int i = 1; i<=60; i++){
            if (i<= 30) {
                listeNombre.add(1);
            } else if (i <=50) {
                listeNombre.add(2);
            } else {
                listeNombre.add(3);
            }
        }
        
        Random rand = new Random();
        while(l < nbLignes){
            c = getDecalage(l);
            while(c < nbColonnes*2-1){
                r = rand.nextInt(listeNombre.size());
                int valeur = listeNombre.remove(r);
                terrainInitiale[l][c]= new Cases(valeur);
                terrainCourant[l][c]= new Cases(valeur);
                c+=2;
            }
            l++;
        }
    }


    // Renvoie la liste des coups annules
    public ArrayList<Coup> getListeCoupsAnnules(){
        return this.coupAnnule;
    }


    // Renvoie la liste des coups joues
    public ArrayList<Coup> getListeCoupsJoues(){
        return this.coupJoue;
    }


    // Set la case (ligne, colonne) du terrain a cases
    public void setCase(Cases cases, int ligne, int colonne){
        int val = getDecalage(ligne);
        if (coordValideTab(ligne, colonne*2+val)) {
            terrainCourant[ligne][colonne*2+val] = cases;
            terrainInitiale[ligne][colonne*2+val] = cases;
        } else {
            System.out.println("impossible de mettre à jour la case ligne: " + ligne +", colonne:" + colonne );
        }
    }


    // Renvoie true si le placement d'un pingouin en (l, c) a marche, false sinon
    // bool IA vrai que pour les instances de l'IA, toujours false sinon
    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueurCourant();
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        if ((joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()) {
            etat = ETAT_PLACEMENTP;
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);

            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);

            switchJoueur();

            //l'IA min/max n'enregistre pas de coup lors de sa reflexion
            if(!IA){
                Coup cp = new Coup(l,c,ping,true);
                coupJoue.add(cp);
                coupAnnule = new ArrayList<Coup>();
            }

            nbPingouinPlace--;

            if (nbPingouinPlace == 0) {
                etat = ETAT_SELECTIONP;

                boolean passeTour = true;
                int i =0;
                joueur = listeJoueur.get(getJoueurCourant()-1);
                while(i< joueur.listePingouin.size() && passeTour){
                    Pingouin p = joueur.listePingouin.get(i);
                    passeTour = estPingouinBloque(p);
                    i++;
                }

                if (passeTour) {
                    switchJoueur();
                }
            }

            return true;
        } else {
            System.out.println("Impossible de placer le pingouin la");
            return false;
        }
    }


    // Renvoie true si le placement d'un pingouin selon un coup cp apres appel a la 
    // methode annule a marche, false sinon
    private boolean placePingouinAnnuler(Coup cp){
        int joueurCourant = getJoueurCourant();
        Joueur joueur = listeJoueur.get(joueurCourant-1);
        int l = cp.getLigne();
        int c = cp.getColonne();

        if ((joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()) {
            etat = ETAT_PLACEMENTP;
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();

            Coup coup = new Coup(l,c,ping,true);
            coupJoue.add(coup);
            nbPingouinPlace--;

            if (nbPingouinPlace == 0) {
                etat = ETAT_SELECTIONP;

                boolean passeTour = true;
                int i =0;
                joueur = listeJoueur.get(getJoueurCourant()-1);
                while(i< joueur.listePingouin.size() && passeTour){
                    Pingouin p = joueur.listePingouin.get(i);
                    passeTour = estPingouinBloque(p);
                    i++;
                }
                
                if (passeTour) {
                    switchJoueur();
                } 
            }

            return true;
        } else {
            System.out.println("Impossible de placer le pingouin ici ");
            return false;
        }
    }


    // Joue un coup cp sur le terrain
    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        int joueurCourant = getJoueurCourant();

        etat = ETAT_SELECTIONP;
        retirePingouin();

        if (peutJouer(cp)) {
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

            //pour optimiser la réflexion de l'ia min max on ne stocke pas les coups
            if (!IA) {
                coupJoue.add(cp);
                coupAnnule = new ArrayList<Coup>();
            }

            //on enlève un pingouin dés qu'il est bloqué
            retirePingouin();
            switchJoueur();
            
            if (jeuTermine()) {
                etat = ETAT_FINAL;
            }

        } else {
            System.out.println("JeuA: joue() - Impossible de jouer en :" + cp);
            retirePingouin();
        }
    }


    // Enleve les pingouins bloques du terrain
    public void retirePingouin(){
        for(int i =0; i< listeJoueur.size(); i++){
            Joueur joueur = listeJoueur.get(i);
            for(int k = 0; k<joueur.listePingouin.size(); k++){
                Pingouin ping = joueur.listePingouin.get(k);
                Cases casesCourante = getCase(ping.getLigne(), ping.getColonne());
                if (estPingouinBloque(ping) && !casesCourante.estMange()) {
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


    // Joue un coup cp sur le terrain apres appel a la methode annule
    public void joueAnnuler(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = getJoueurCourant();
        etat = ETAT_SELECTIONP;

        retirePingouin();

        if (peutJouer(cp)) {
            Cases caseArrive = getCase(l,c);
            Joueur joueur = listeJoueur.get(joueurCourant-1);

            Pingouin ping = cp.getPingouin();
            ping = joueur.getPingouin(ping);

            //conservation d'ou vient le pingouin
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
        } else {
            System.out.println("JeuA: joueAnnuler() - Impossible de jouer\n");
        }
    }


    // Renvoie true si on peut annuler un coup, false sinon
    public boolean peutAnnuler(){
        return (!(coupJoue.size() < 1));
    }


    // Annule le dernier coup joue
    public void annule(){   
        if (peutAnnuler()) {
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
                if (cp.place == true) {
                    j.placePingouin(cp.getLigne(),cp.getColonne());
                    if (nbPingouinPlace == 0) {
                        etat = ETAT_SELECTIONP;
                    }
                } else { 
                    etat = ETAT_SELECTIONP;
                    j.joue(cp);
                }
                i++;
            }
            this.terrainCourant = j.getTerrain();
            this.listeJoueur = j.getListeJoueur();
            this.joueurCourant = j.getJoueurCourant();
        } else {
            System.out.println("JeuA: annule() - Impossible d'annuler\n");
        }
    }


    // Renvoie true si on peut refaire un coup qu'on a annule, false sinon
    public boolean peutRefaire(){
        return (!(coupAnnule.size() < 1));
    }


    // Refait un coup annule
    public void refaire(){
        if (peutRefaire()) {
            Coup cp = coupAnnule.get(coupAnnule.size()-1);
            if (cp.place == true) {
                placePingouinAnnuler(coupAnnule.get(coupAnnule.size()-1));
            } else {
                joueAnnuler(coupAnnule.get(coupAnnule.size()-1));
                coupJoue.add(cp);
            }
            coupAnnule.remove(coupAnnule.size()-1);
        } else {
            System.out.println("JeuA: refaire() - Impossible de refaire\n");
        }
    }


    // Sauvegarde du jeu avec un nom de fichier
    public void sauvegarder(String name){
        try {

            FileWriter w = new FileWriter(name);
            
            //stocke les infos de bases
            w.write(nbJoueur + "\n");
            w.write(nbLignes + "\n");
            w.write(((nbColonnes-1)/2) + "\n");

            //stocke le score des joueurs
            for(int i=0; i<nbJoueur; i++){
                w.write(listeJoueur.get(i).getScore() + "\n");
            }

            //stocke le nombre de cases mangees par les joueurs
            for(int i=0; i<nbJoueur; i++){
                w.write(listeJoueur.get(i).getNbCasesMange() + "\n");
            }

            //stocke le type des joueurs (IA ou non)
            for(int i=0; i<nbJoueur; i++){
                w.write(IATab[i]+ "\n");
            }

            //stocke tout le terrain
            String result = "";
            String sep = "";
            String tmp = "";
            for (int i=0; i<terrainInitiale.length; i++) {
                tmp = Arrays.toString(terrainInitiale[i]);
                result += sep + tmp.substring(1, tmp.length() -1);
                sep = "\n";
            }
            w.write(result + "\n");

            //marque pour indiquer que la suite sont des coups joues
            w.write("b\n");

            //enregistrer tous les coups joues
            int tailleList = coupJoue.size();
            for(int i = 0; i< tailleList; i++) {
                w.write(coupJoue.get(i).getLigne() + " "+ coupJoue.get(i).getColonne()  + " " + coupJoue.get(i).getPingouin().getLigne()+ " " + coupJoue.get(i).getPingouin().getColonne() + " " + coupJoue.get(i).estPlace() + "\n");
            }
            w.write("b\n");

            //enregistrer tous les coups annules
            int tailleLista = coupAnnule.size();
            for(int i = 0; i< tailleLista; i++) {
                w.write(coupAnnule.get(i).getLigne() + " "+ coupAnnule.get(i).getColonne() + " " + coupAnnule.get(i).getPingouin().getColonne()+ " " + coupAnnule.get(i).getPingouin().getLigne() + " " + coupJoue.get(i).estPlace() +"\n");
            }

            w.close();
        } catch (IOException e) {
            System.out.print("Erreur : " + e);
        }
    }


    // Affiche le jeu a l'ecran
    public String toString(){
        String result ="Plateau:\n[";
        String line;
        Cases caseCourant;
        int l =0;
        int c =0;
        int nbc;
        while( l < terrainCourant.length){
            c = 0;
            if (l%2 == 1) { // si ligne impaire
                line= "";
                nbc = 8;
            } else {
                nbc = 7;
                line = "   ";
            }

            //boucle sur toutes les colonnes
            while(c < (nbc)){
                line = line + "   ";
                caseCourant = getCase(l,c);
                if (caseCourant.estMange()) {
                    line= line + "    ";
                } else {
                    line= line +caseCourant + "";
                }
                c++;
            }
            result+=line + "\n";
            l++;
        }
        return result;
    }


    // Set l'etat du jeu a ETAT_CHOIXC
    public void setSelectionP(Position p) {
        selection = true;
        selectionP = p;
        etat = ETAT_CHOIXC;
    }

    
    // Unset l'etat du jeu et le set a ETAT_SELECTIONP
    public void unsetSelectionP() {
        selection = false;
        selectionP = null;
        etat = ETAT_SELECTIONP;
    }


    // Renvoie true true si un pingouin est seletionne, false sinon
    public boolean getSelection() {
        return selection;
    }


    // Renvoie la position du pingouin selectionne
    public Position getSelectionP() {
        return selectionP;
    }


    // Renvoie l'etat du jeu
    public int getEtat() {
        return etat;
    }


    // Debut du jeu en mettant son etat a ETAT_PLACEMENTP
    public void startGame() {
        etat = ETAT_PLACEMENTP;
    }


    // Renvoie true si le jeu est termine, false sinon
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

        if (termine) {
            //on retire les dernier pingouins si ils sont bloques
            retirePingouin();
        }

        return termine;
    }


    // Renvoie true si le pingouin ping est bloque, false sinon
    public boolean estPingouinBloque(Pingouin ping){
        ArrayList<Position> casesAccesible = getCaseAccessible(ping.getLigne(), ping.getColonne());
        return (casesAccesible.size() == 0);
    }


    // Renvoie true si tous les pingouins ont ete place, false sinon
    public boolean pingouinTousPlace(){
        return (nbPingouinPlace == 0);
    }


    // Renvoie true si un pingouin se trouve sur la case (l, c), false sinon
    public boolean pingouinPresent(int l, int c){
        return (getCase(l,c).pingouinPresent()!= 0);
    }


    // Renvoie true si on peut placer un pingouin sur la case (i, j), false sinon
    public boolean peutPlacer(int i, int j){
        return(getCase(i,j).pingouinPresent() == 0 && getCase(i,j).getNbPoissons() == 1);
    }


    // Renvoie true si on peut jouer le coup cp sur le terrain, false sinon
    public boolean peutJouer(Coup cp){
        int ligne = cp.getLigne();
        int colonne = cp.getColonne();

        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin().getLigne(), cp.getPingouin().getColonne());
        int index = 0;
        while(index < casesAccessible.size() && (casesAccessible.get(index).x !=ligne || casesAccessible.get(index).y != colonne)){
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

        if (k<p.size()) {
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }
        return (index != casesAccessible.size() && bonPinguoin && nbPingouinPlace == 0);
    }


    // Renvoie true si (x, y) est dans le terrain, false sinon
    public boolean coordValideTab(int x, int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }


    // Renvoie la liste des positions des cases contenant un poisson
    public ArrayList<Position> case1poisson(){
        ArrayList<Position> posPossible = new ArrayList<Position>();
        Cases caseCourant;
        Position posCourant;
        int l =0;
        int c =0;

        while( l < terrainCourant.length){
            c = 0;

            //boucle sur toutes les colonnes
            while( c < (8 - getDecalage(l))){
                caseCourant = getCase(l,c);
                if (caseCourant.getNbPoissons()==1 && caseCourant.pingouinPresent() == 0) {
                    posCourant = new Position(l,c);
                    posPossible.add(posCourant);
                }
                c++;
            }
            l++;
        }
        return posPossible;
    }


    // Renvoie le score d'un joueur
    public int getScore(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getScore();
    }


    // Renvoie le nombre de cases mangees d'un joueur
    public int getcasesMange(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getNbCasesMange();
    }


    // Renvoie le nombre de cases du terrain
    public int getNbCases(){
        int nbCases = 0;
        int l =0;
        int c =0;

        while(l < this.nbLignes){
            c = getDecalage(l);
            while(c < this.nbColonnes){
                nbCases++;
                c+=2;
            }
            l++;
        }
        return nbCases;
    }


    // Renvoie 1 si la ligne l est paire, 0 sinon
    public int getDecalage(int l){
        if (l%2 ==1) {
            return 0;
        } else { 
            return 1;
        }
    }


    // Renvoie le nombre de ligne du terrain
    public int getNbLigne(){
        return this.nbLignes;
    }


    // Renvoie le nombre de colonne du terrain
    public int getNbColonne(){
        return this.nbColonnes;
    }


    // Renvoie le nombre de joueur de la partie
    public int getNbJoueur(){
        return this.nbJoueur;
    }


    // Renvoie le nombre de pingouins par joueur
    public int getNbPingouinJoueur(){
        return this.nbPingouinJoueur;
    }


    // Renvoie le nombre de pingouins qu'il reste a placer
    public int getNbPingouinPlace(){
        return this.nbPingouinPlace;
    }


    // Renvoie le numero du joueur courant
    public int getJoueurCourant(){
        return joueurCourant;
    }


    // Renvoie le terrain
    public Cases [][] getTerrain(){
        return this.terrainCourant;
    }


    // Renvoie la liste des joueurs
    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }


    // Renvoie la case (ligne, colonne)
    public Cases getCase(int ligne, int colonne){
        int val = getDecalage(ligne);
        if (coordValideTab(ligne, colonne*2+val)) {
            return terrainCourant[ligne][colonne*2+val];
        } else {
            System.out.println("impossible de récuperer la case ligne: "+ligne+ ", colonne:"+colonne + "");
            return null;
        }
    }


    // Renvoie la liste des cases accessibles depuis la case (i, j)
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

        int yInit = j * 2 + getDecalage(i);
        int xInit = i;

        // recherche dans toutes les directions
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


    // Change le numero du joueur courant a celui du joueur suivant si le premier ne peut pas jouer
    public void switchJoueur(){
        joueurCourant = (joueurCourant % nbJoueur) + 1;

        ArrayList<Pingouin> pingJoueur = new ArrayList<>();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        //init liste des pingouisn avec la liste des pingouins
        pingJoueur = joueur.getListePingouin();

        int i =0;
        boolean passeTour = (listeJoueur.get(joueurCourant-1).getListePingouin().size()!=0);
        if (pingJoueur.size() ==0 && etat ==ETAT_SELECTIONP && !jeuTermine()) {
            switchJoueur();
            retirePingouin();
        } else {
            if (etat != ETAT_PLACEMENTP) {
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
        
        if (passeTour && !jeuTermine()) {
            switchJoueur();
        }
    }


    // Clone le terrain
    public Cases [][] clonerTerrain(Cases [][] terrainInitiale){
        Cases [][] terrainClone;
        Cases caseCourante;
        Cases cases;

        int nbl = terrainInitiale.length;
        int nbc = terrainInitiale[0].length;
        terrainClone = new Cases[nbl][nbc];

        int c;
        int l=0;
        while(l < nbl){
            c= getDecalage(l);
            while(c < (nbc)){
                caseCourante = terrainInitiale[l][c];
                cases = new Cases(caseCourante.estMange(), caseCourante.getNbPoissons(), caseCourante.pingouinPresent());
                terrainClone[l][c] = cases;
                c+=2;
            }
            l++;
        }
        return terrainClone;
    }


    // Clone le jeu
    public Jeu cloner(){
        Jeu j = new Jeu(this.terrainCourant, this.listeJoueur, this.nbLignes, this.nbColonnes, this.nbJoueur,
         this.nbPingouinJoueur, this.nbPingouinPlace, this.joueurCourant, this.IA, this.IATab);
        return j;
    }


    // Renvoie le numero du joueur gagnant
    public int gagnant(){
        int [] score = new int[nbJoueur];
        for(int i=0; i<nbJoueur; i++){
            score[i] = listeJoueur.get(i).getScore(); 
        }

        int max =0;
        int joueurGagnant = 0;
        for (int i =0; i< nbJoueur; i++){
            if (max < score[i]) {
                max = score[i];
                joueurGagnant = i;
            } else if (max == score[i]) {
                int nbCasesMangeA = listeJoueur.get(joueurGagnant).getNbCasesMange();
                int nbCasesMangeB = listeJoueur.get(i).getNbCasesMange();

                if (nbCasesMangeA < nbCasesMangeB) {
                    joueurGagnant = i;
                }
            }
        }
        return joueurGagnant +1;
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
