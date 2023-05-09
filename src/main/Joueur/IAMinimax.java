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



public class IAMinimax extends IAJoueur{


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
        Configuration conf = new Configuration(this.j.toJeu());

        ArrayList<Configuration> fils = Configuration.coupFils(conf);
        int max = 0;
        int temp = 0;
        Coup coupMax = null;
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimax(fils.get(i), 1, true)) > max ){
                max =temp;
                coupMax = fils.get(i).coup;
            }
            
        }
        
        return coupMax;
    }

    public int minimax(Configuration config, int depth, boolean max){
        int value =0;
        ArrayList<Configuration> fils = Configuration.coupFils(config);
        if(depth ==0 || config.jeu.jeuTermine()){
            return H(config);

        }if(fils.size()==0){
            return minimax(config,depth-1,!max);

        }
        if(max){
            value = Integer.MIN_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.max(value, minimax(fils.get(i),depth-1, false ));
            }
        }else{
            value = Integer.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.min(value, minimax(fils.get(i),depth-1, true ));
            }

        }
        return value;
    }


    public int H(Configuration config){
        Random r = new Random();
        int value = r.nextInt(50);
        return value;
    }
}