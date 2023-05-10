package Joueur;

import java.util.Random;
import java.lang.Math;
import Model.Coup;
import Model.Jeu;
import Model.JeuAvance;
import Model.Position;
import Model.Joueur;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;
import java.util.Hashtable;
import Joueur.Heuristique;



public class IAMinimax extends IAJoueur{
    int iajoueur;


    IAMinimax(JeuAvance j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        Random r = new Random();
        Cases[][] terrainCourant = this.j.getTerrain();
        ArrayList<Position> posPossible = new ArrayList<Position>();
        Cases caseCourant;
        Position posCourant;

        int nbc;
        int l = 0;
        int c = 0;
        while( l < terrainCourant.length){
            c = 0;
            if( l%2 == 1){// si ligne impaire
                nbc = 8;
            }else{
                nbc = 7;
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                caseCourant = j.getCase(l,c);
                if(caseCourant.getNbPoissons()==1 && caseCourant.pingouinPresent() == 0){
                    posCourant = new Position(l,c);
                    posPossible.add(posCourant);
                }
                c++;
            }
            l++;
        }

        return posPossible.get(r.nextInt(posPossible.size()));
    }
    
    @Override
    public Coup elaboreCoup(){
        this.iajoueur = this.j.getJoueurCourant();
        Configuration conf = new Configuration(this.j.toJeu());

        ArrayList<Configuration> fils = Configuration.coupFils(conf);
        int max = 0;
        int temp = 0;
        Coup coupMax = null;
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimax(fils.get(i), 1, false)) > max ){
                max =temp;
                coupMax = fils.get(i).coup;
            }
            
        }
        return coupMax;
    }

    public int minimax(Configuration config, int depth, boolean max){
        int value =0;
        int profo = depth-1;
        ArrayList<Configuration> fils = Configuration.coupFils(config);
        if(depth <= 0 || config.jeu.jeuTermine()){
            return Heuristique.Hdiffscore(config,this.iajoueur)+100;

        }if(fils.size()==0){
            return minimax(config,profo,!max);

        }
        if(max){
            value = Integer.MIN_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.max(value, minimax(fils.get(i), profo, false ));
            } 
        }else{
            value = Integer.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.min(value, minimax(fils.get(i),profo, true ));
            }

        }
        return value;
    }

    // Random
    public int H(Configuration config){
        Random r = new Random();
        int value = r.nextInt(50);
        return value;
    }

    // Ce truc marche moin bien que l'aléatoire
    //Nombre de coup possible
    public int H1(Configuration config){
        ArrayList<Pingouin> listePingouin = config.jeu.getListeJoueur().get(iajoueur-1).getListePingouin();
        ArrayList<Position> listePos;
        ArrayList<Position> listePosTotal = new ArrayList<Position>();
        int i = 0;
        int j = 0;

        while( i < listePingouin.size()){
            listePos = null;
            listePos = config.jeu.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                listePosTotal.add(listePos.get(j));
                j++;
            }
            i++;

        }
    
            return listePosTotal.size();
        }


    public int H2(Configuration config){
        Joueur joueur1=config.jeu.getListeJoueur().get(this.iajoueur-1);
        config.jeu.switchJoueur();
        Joueur joueur2= config.jeu.getListeJoueur().get(this.iajoueur-2);
        int scores = joueur1.getScore()-joueur2.getScore();
        return scores+50;
    }


    public int Hilot(Configuration config){
        Hashtable<Integer, Position> vu= new Hashtable<Integer, Position>();
        Position pos;

        ArrayList<Position> listePos = new ArrayList<Position>();
        boolean changer = true;
        Joueur joueur1=config.jeu.getListeJoueur().get(this.iajoueur-1);
        ArrayList<Pingouin> listePingouin1 = config.jeu.getListeJoueur().get(iajoueur-1).getListePingouin();
        Joueur joueur2=config.jeu.getListeJoueur().get(this.iajoueur-2);
        ArrayList<Pingouin> listePingouin2 = config.jeu.getListeJoueur().get(iajoueur-1).getListePingouin();

        
        while(changer){
            




        }






        return 0;
    }
}