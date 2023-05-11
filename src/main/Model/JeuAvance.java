package Model;

import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class JeuAvance extends Jeu{

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


    //tableau pour récupere si les joeurs sont des IA ou non
    public boolean [] IATab;


    private Position selectionP;
    private boolean selection;
    int etat;

    //attribut pour la reflexion de l'IA
    private boolean IA;

    public JeuAvance(){}

    public JeuAvance(String name){
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


            //récup le type des joeurs (IA ou non)
            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                IATab[i] = Boolean.parseBoolean(line);
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
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));

                if(Boolean.parseBoolean(parts[4])){
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }else{
                    joue(cp);
                }
            }

            //recuprere les coups annulés
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));
                coupAnnule.add(cp);
            }

            reader.close();
        } catch (IOException e) {
            System.out.print("Erreur : " + e);
        }
    }



    public JeuAvance(int nbJoueur){
        this(nbJoueur,8,8);
    }


    //Constructeur pour la méthode annule
    JeuAvance(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab){
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);

        initArrays(nbJoueur);

        initNbJoueur(nbJoueur);

        //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;

    }


    public JeuAvance(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(nbJoueur);

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

    public JeuAvance(ArrayList<Joueur> ar){
        this(ar, 8,8);
    }
    //constructeru avec une liste de joueur
    public JeuAvance(ArrayList<Joueur> ar, int nbLignes, int nbColonnes){

        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays(ar.size());

        nbJoueur = ar.size();


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

    public JeuAvance(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc){
        terrainCourant = clonerTerrain(terrain);

        initArrays(ar.size());

        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());
        }

        this.nbLignes = l; // taille du tableau
        this.nbColonnes = c; // taille du tableau
        this.nbJoueur = j;
        this.nbPingouinJoueur = pj;
        this.nbPingouinPlace = pp;
        this.joueurCourant = jc;

        selection = false;
        selectionP = null;
        etat = ETAT_INITIAL;
    }
    
    //init les joeurs et remplis la liste des joueurs
    public void initNbJoueur(int nbJoueur){
        int i = 1;
        Joueur player;

        while(i <= nbJoueur){
            player = new Joueur(i,0,0, false);
            listeJoueur.add(player);
            //de base tous les joeurs ne sont pas des IAs
            IATab[i-1] =false;
            i++;
        }

        this.nbJoueur = nbJoueur;
    }

    //init les arrayLists de la classe
    public void initArrays(int nbJoueur){
        this.coupAnnule = new ArrayList<Coup>();
        this.coupJoue = new ArrayList<Coup>();
        this.listeJoueur = new ArrayList<Joueur>();

        this.IATab = new boolean[nbJoueur];
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


    public void joue(Coup cp){
        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller
        int joueurCourant = getJoueurCourant();

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

            switchJoueur();
            
            if(jeuTermine()){
                etat = ETAT_FINAL;
            }

        }
        else {
            System.out.println("JeuA: joue() - Impossible de jouer");
        }
    }


    public void joueAnnuler(Coup cp){

        int l = cp.getLigne();   //Coord ou le pingouin doit aller
        int c = cp.getColonne(); //Coord ou le pingouin doit aller

        int joueurCourant = getJoueurCourant();

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
            JeuAvance j = new JeuAvance(terrainInit, this.nbJoueur, this.nbLignes, this.nbColonnes);
            Coup cp;
            if (coupJoue.get(coupJoue.size() - 1).estPlace() == true)
                this.nbPingouinPlace++;
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


    public JeuAvance toJeu(){
        return (new JeuAvance(this.terrainCourant, this.listeJoueur, this.nbLignes, this.nbColonnes, this.nbJoueur,
         this.nbPingouinJoueur, this.nbPingouinPlace, this.joueurCourant));
        
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

    public JeuAvance cloner(){
        JeuAvance j = new JeuAvance(this.terrainCourant, this.listeJoueur, this.nbLignes, this.nbColonnes, this.nbJoueur,
         this.nbPingouinJoueur, this.nbPingouinPlace, this.joueurCourant);
        return j;
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
}