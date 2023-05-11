package Joueur;

import java.util.Random;
import java.lang.Math;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Joueur;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;
import java.util.Hashtable;
import Joueur.Heuristique;



public class IAMinimax extends IAJoueur{
    int iajoueur;


    public IAMinimax(Jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        
        this.iajoueur = this.j.getJoueurCourant();
        Configuration conf = new Configuration(this.j.cloner());

        ArrayList<Configuration> fils = Configuration.coupFilsPhase1(conf);
        int max = 0;
        int temp = 0;
        Position posmax = null;
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase1(fils.get(i), 0 , false)) > max ){
                max =temp;
                posmax = fils.get(i).position;
            }
            
        }
        return posmax;
    }
    
    @Override
    public Coup elaboreCoup(){
        this.iajoueur = this.j.getJoueurCourant();
        Configuration conf = new Configuration(this.j.cloner());

        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(conf);
        int max = 0;
        int temp = 0;
        Coup coupMax = null;
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase2(fils.get(i), 0, false)) > max ){
                max =temp;
                coupMax = fils.get(i).coup;
            }
            
        }
        return coupMax;
    }


    public int minimaxPhase1(Configuration config, int depth, boolean max){
        int value =0;
        int profo = depth-1;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase1(config);
        if(depth <= 0 || config.jeu.pingouinTousPlace() ){
            return Heuristique.HcoupPossible(config,this.iajoueur)+100;

        }if(max){
            value = Integer.MIN_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.max(value, minimaxPhase1(fils.get(i), profo, false ));
            } 
        }else{
            value = Integer.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.min(value, minimaxPhase1(fils.get(i),profo, true ));
            }

        }
        return value;
    }

    public int minimaxPhase2(Configuration config, int depth, boolean max){
        int value =0;
        int profo = depth-1;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(config);
        if(depth <= 0 || config.jeu.jeuTermine()){
            value = Heuristique.Hilot(config,this.iajoueur)+100;
            //System.out.println(config.jeu);
            //System.out.println(value + "\n");
            return value;

        }if(fils.size()==0){
            return minimaxPhase2(config,profo,!max);

        }
        if(max){
            value = Integer.MIN_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.max(value, minimaxPhase2(fils.get(i), profo, false ));
            } 
        }else{
            value = Integer.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                value = Math.min(value, minimaxPhase2(fils.get(i),profo, true ));
            }

        }
        return value;
    }

}