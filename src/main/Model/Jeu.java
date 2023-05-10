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
    protected boolean IA;

    
    //constructeur vide pour la classe fille
    public Jeu(){

    }

    public Jeu(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc){
        terrainCourant = clonerTerrain(terrain);
        listeJoueur = new ArrayList<Joueur>();
        for(int i =0; i < ar.size(); i++){
            listeJoueur.add(ar.get(i).cloner());
        }

        this.nbLignes = l; // taille du tableau
        this.nbColonnes = c; // taille du tableau
        this.nbJoueur = j;
        this.nbPingouinJoueur = pj;
        this.nbPingouinPlace = pp;
        this.joueurCourant = jc;
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
        this.joueurCourant = jeu.getJoueur();
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

    public boolean peutPlacer(int i, int j){
        return(getCase(i,j).pingouinPresent() == 0 && getCase(i,j).getNbPoissons() == 1);
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

        while(k < p.size() && !p.get(k).equals(cp.getPingouin())){
            k++;
        }

        boolean bonPinguoin = false;

        if(k<p.size() && p.get(k).equals(cp.getPingouin())){
            bonPinguoin = true;
        } else {
            System.out.println("Le pingouin choisit n'est pas déplacable pour le moment");
        }

        return (index != casesAccessible.size() && bonPinguoin && nbPingouinPlace == 0);
    }

    public boolean coordValideTab(int x,int y){
        return (x < this.nbLignes && x >= 0 && y < this.nbColonnes && y >= 0);
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

    public int getJoueur(){
        return joueurCourant;
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
        boolean passeTour = true;

        while(i< pingJoueur.size() && passeTour){
            passeTour = estPingouinBloque(pingJoueur.get(i));
        }
        
        if(passeTour && jeuTermine()){
            if(!IA){
                System.out.println("Switch joueur bloqué");
            }
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
            c = getDecalage(l);
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
