package Model;


import java.util.ArrayList;
import java.util.Arrays;

public class Jeu{

    protected Cases [][] terrainCourant;
    protected ArrayList<Joueur> listeJoueur;

    protected int nbLignes; // taille du tableau
    protected int nbColonnes; // taille du tableau

    protected int nbJoueur;
    protected int nbPingouinJoueur;
    protected int nbPingouinPlace;

    protected int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus


    public Jeu(){

    }

    public Jeu(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc){
        terrainCourant = clonerTerrain(terrain);
        listeJoueur = new ArrayList<Joueur>();
        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());
        }

        nbLignes = l; // taille du tableau
        nbColonnes = c; // taille du tableau

        nbJoueur = j;
        nbPingouinJoueur = pj;
        nbPingouinPlace = pp;

        joueurCourant = jc;
    }


    public Jeu(Jeu jeu){
        this.terrainCourant = jeu.clonerTerrain(jeu.getTerrain());

        int i = 0;
        this.listeJoueur = new ArrayList<Joueur>();
        while( i < jeu.getListeJoueur().size()){
            this.listeJoueur.add(jeu.getListeJoueur().get(i).cloner());
            i++;
        }

        this.nbLignes = jeu.getNbLigne();
        this.nbColonnes = jeu.getNbColonne();
        this.nbJoueur = jeu.getNbJoueur();
        this.nbPingouinJoueur = jeu.getNbPingouinJoueur();
        this.nbPingouinPlace = jeu.getNbPingouinPlace();
        this.joueurCourant = jeu.getJoueurCourant();

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

        //si le jeu est terminé on récupére tous les points sous les pingouins
        if(termine){
            for(int i =0; i< listeJoueur.size(); i++){
                Joueur j = listeJoueur.get(i);

                for(int k = 0; k<j.listePingouin.size(); k++){
                    Pingouin ping = j.listePingouin.get(k);
                    Cases casesCourante = getCase(ping.getLigne(), ping.getColonne());

                    j.setScore(j.getScore() + casesCourante.getNbPoissons());
                    j.setNbCasesMange(j.getNbCasesMange() +1);

                    casesCourante.setMange(true);
                    casesCourante.setNbPoissons(0);
                }
            }
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


    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        if( (joueur.listePingouin.size() < nbPingouinJoueur) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
            Pingouin ping = new Pingouin(l,c);
            joueur.listePingouin.add(ping);
            Cases cases = getCase(l,c);
            cases.setPingouin(joueurCourant);
            switchJoueur();
            nbPingouinPlace--;
            return true;

        }else{
            System.out.println("Impossible de placer le pingouin ici");
            return false;
        }

    }


    public int getScore(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getScore();
    }

    /*
     * Donne le nombre de cases mangé par un joueur
     */
    public int getcasesMange(int joueur){
        Joueur j = listeJoueur.get(joueur-1);
        return j.getNbCasesMange();
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
        return this.terrainCourant;
    }


    public ArrayList<Joueur> getListeJoueur(){
        return this.listeJoueur;
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


    public int getJoueur(){
        return joueurCourant;
    }


    //switchJoueur si le joueur ne peut pas joueur
    public void switchJoueur(){

        joueurCourant = (joueurCourant % nbJoueur) + 1;

        ArrayList<Pingouin> pingJoueur = new ArrayList<>();

        //recup joueur courant
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        //init liste des pingouisn avec la liste des pingouins
        pingJoueur = joueur.getListePingouin();

        int i =0;
        boolean passeTour = true;

        while(i< pingJoueur.size() && passeTour){
            passeTour = estPingouinBloque(pingJoueur.get(i));
        }
        
        //Attention à la récursivité ici ??
        if(passeTour && jeuTermine()){

            System.out.println("Switch joueur bloqué");
            switchJoueur();
        }

    }


    public boolean peutJouer(Coup cp){

        int ligne = cp.getLigne();
        int colonne = cp.getColonne();

        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin().getLigne(), cp.getPingouin().getColonne());
        int index = 0;

        while( index <casesAccessible.size() && (casesAccessible.get(index).x !=ligne || casesAccessible.get(index).y != colonne)){
            index++;    
        }

        Joueur j = listeJoueur.get(joueurCourant-1);
        ArrayList<Pingouin> p = new ArrayList<>();
        p = j.listePingouin;

        int k =0;

        while(k < p.size() && p.get(k).equals(cp.getPingouin())){
            k++;
        }

        boolean bonPinguoin = false;

        if(k<p.size() && p.get(k).equals(cp.getPingouin())){
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }

        return (index != casesAccessible.size() && bonPinguoin);
    }


    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
    }


    public ArrayList<Position> getCaseAccessible(int i, int j){
        int yInit, xInit, x, y;

        Position position;
        Cases cases;
        if((i%2 ==0)){
            yInit = j*2+1;
        }else {
            yInit = j*2;
        }
        xInit = i;
        x = xInit;
        y = yInit;
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
        y=yInit-2;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y = y-2;
        }

        //bas gauche
        y = yInit -1;
        x = xInit +1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y--;
            x++;
        }

        //bas droite
        y = yInit +1;
        x = xInit +1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y++;
            x++;
        }

        //haut gauche
        y = yInit -1;
        x = xInit -1;
        while(coordValideTab(x,y) && (cases = terrainCourant[x][y]) != null && !cases.estMange() && cases.pingouinPresent()==0){
            position = new Position(x, y/2);
            caseAccessible.add(position);
            y--;
            x--;
        }

        //haut droite
        y = yInit +1;
        x = xInit -1;
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
            switchJoueur();
        } else {
            System.out.println("Impossible de jouer");
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


    public Jeu cloner(){
        Jeu j = new Jeu(this.terrainCourant, this.listeJoueur, this.nbLignes, this.nbColonnes, this.nbJoueur,
         this.nbPingouinJoueur, this.nbPingouinPlace, this.joueurCourant);
        return j;


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

    public boolean peutPlacer(int i, int j){
        return(getCase(i,j).pingouinPresent() == 0 && getCase(i,j).getNbPoissons() == 1);
    }
/*
    public String toString(){
        String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<terrainCourant.length; i++) {
			result += sep + Arrays.toString(terrainCourant[i]);
			sep = "\n ";
		}
        return result;
    }
    */

}
