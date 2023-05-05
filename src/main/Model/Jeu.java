package Model;


import java.util.ArrayList;

public class Jeu{

    protected Cases [][] terrainInitiale;
    protected Cases [][] terrainCourant;
    protected ArrayList<Joueur> listeJoueur;

    protected int nbLignes; // taille du tableau
    protected int nbColonnes; // taille du tableau

    protected int nbJoueur;
    protected int nbPingouin;
    protected int nbPingouinPlace;

    protected int joueurCourant = 1;  // En supposant que c'est le joueur 1 qui commence compris entre 1 et nbJoueur-1 inclus


    /*
     * Met à jour la variable Jeutermine si les pingouins ne peuvent plus bouger
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

        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
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
        ArrayList<Position> casesAccessible = getCaseAccessible(cp.getPingouin().getLigne(), cp.getPingouin().getColonne());
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
        }
        else {
            System.out.println("Impossible de jouer");
            
        }

    }


    public boolean peutPlacer(int i, int j){
        return(getCase(i,j).pingouinPresent() == 0 && getCase(i,j).getNbPoissons() == 1);
    }

    
}
