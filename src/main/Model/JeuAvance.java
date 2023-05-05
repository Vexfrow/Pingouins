package Model;


import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class JeuAvance extends Jeu{

    private Cases [][] terrainInitiale;
    private ArrayList<Coup> coupJoue;
    private ArrayList<Coup> coupAnnule;



    JeuAvance(String name){
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
                this.nbPingouin =4;
                this.nbPingouinPlace = this.nbPingouin*2;
            }else if (nbJoueur == 3){
                this.nbPingouin =3;
                this.nbPingouinPlace = this.nbPingouin*3;
            } else {
                this.nbPingouin =2;
                this.nbPingouinPlace = this.nbPingouin*4;
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

    public JeuAvance(int nbJoueur, int nbLignes, int nbColonnes){
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


    public boolean placePingouin(int l, int c){
        int joueurCourant = getJoueur();
        Joueur joueur = listeJoueur.get(joueurCourant-1);

        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
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

        if( (joueur.listePingouin.size() < nbPingouin) && getCase(l, c) != null && !pingouinPresent(l, c) && getCase(l,c).getNbPoissons() == 1 && !pingouinTousPlace()){
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

    @Override
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
            this.nbPingouinPlace =nbPingouin;

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