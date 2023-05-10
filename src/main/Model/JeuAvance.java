package Model;

import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class JeuAvance extends Jeu{

    protected Cases [][] terrainInitiale;
    protected ArrayList<Coup> coupJoue;
    protected ArrayList<Coup> coupAnnule;
    public int [] scoreSave;
    
    //attribut pour la reflexion de l'IA

    JeuAvance(){

    }


    public JeuAvance(int nbJoueur){
        this(nbJoueur,8,8);
    }


    //Constructeur pour la méthode annule
    JeuAvance(Cases [][]terrainInitiale, int nbJoueur, int nbLigneTab, int nbColTab){
        this.terrainInitiale = clonerTerrain(terrainInitiale);
        this.terrainCourant = clonerTerrain(terrainInitiale);

        initArrays();

        initNbJoueur(nbJoueur);

        //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbColonnes = nbColTab;
        this.nbLignes = nbLigneTab;

    }


    public JeuAvance(int nbJoueur, int nbLignes, int nbColonnes){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];

        initArrays();

        initNbJoueur(nbJoueur);

        //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
        initNbPingouins(nbJoueur);

        this.nbColonnes = nbColonnes*2-1;
        this.nbLignes = nbLignes;
        terrainAleatoire(nbLignes, nbColonnes);
    }

    public JeuAvance(Cases[][] terrain, ArrayList<Joueur> ar, int l, int c, int j, int pj, int pp, int jc){
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
    
    //init les joeurs et remplis la liste des joueurs
    public void initNbJoueur(int nbJoueur){
        int i = 1;
        Joueur player;

        while(i <= nbJoueur){
            player = new Joueur(i,0,0);
            listeJoueur.add(player);
            i++;
        }
        this.nbJoueur = nbJoueur;
    }

    //init les arrayLists de la classe
    public void initArrays(){
        this.coupAnnule = new ArrayList<Coup>();
        this.coupJoue = new ArrayList<Coup>();
        this.listeJoueur = new ArrayList<Joueur>();
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

    
    //bool IA vrai que pour les instances de l'IA : toujours false sinon
    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueur();
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

    //bool IA a false pour chaque coup sauf pour IA
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
        }
        else {
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
            System.out.println("Impossible de jouer\n");
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
            coupAnnule.add(coupJoue.get(coupJoue.size()-1));
            coupJoue.remove(coupJoue.size()-1);
            int i = 0;
            this.nbPingouinPlace =nbPingouinJoueur;

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

            //stocker tout le terrain
            String result = "";
            String sep = "";
            String tmp = "";

            for (int i=0; i<terrainCourant.length; i++) {
                tmp = Arrays.toString(terrainCourant[i]);
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
}