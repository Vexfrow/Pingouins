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
    private long start;

    private static final int Time_out_ms = 2000;


    public IAMinimax(Jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        
        this.start = System.currentTimeMillis();
        
        this.iajoueur = this.j.getJoueurCourant();
        Configuration conf = new Configuration(this.j.cloner());

        ArrayList<Configuration> fils = Configuration.coupFilsPhase1(conf);
        double max = -Double.MAX_VALUE;
        double temp = 0;
        Position posmax = null;
        System.out.println(Heuristique.HnbCaseAccessible(conf,this.iajoueur));
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase1(fils.get(i), 2 , false)) > max ){
                max =temp;
                posmax = fils.get(i).position;
            }
            
        }
        return posmax;
    }
    
    @Override
    public Coup elaboreCoup(){

        this.start = System.currentTimeMillis();
        this.iajoueur = this.j.getJoueurCourant();
        ArrayList<Joueur> listeJoueur = this.j.getListeJoueur();
        int nbcase= 0;
        int bonus;
        for(int i = 0; i< listeJoueur.size(); i++){
            nbcase+= listeJoueur.get(i).getNbCasesMange();
        }
        if(nbcase <=4){
            nbcase = 5;
        }
        bonus = (int)Math.cbrt((double)nbcase-4);




        Configuration conf = new Configuration(this.j.cloner());
        System.out.println(Heuristique.HnbCaseAccessible(conf,this.iajoueur)+ " est la valeur de l'heuristique nbCaseAccessible");
        System.out.println(Heuristique.Hilot(conf,this.iajoueur)*4 + "Valeur Ilot");
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(conf);
        double max = -Double.MAX_VALUE;
        double temp = 0;
        Coup coupMax = null;

        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase2(fils.get(i), 1+bonus, false)) > max ){
                max = temp;
                coupMax = fils.get(i).coup;
            }
            
        }
        return coupMax;
    }


    public double minimaxPhase1(Configuration config, int depth, boolean max){
        double value;
        int profo = depth-1;
        double tempo;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase1(config);
        if((System.currentTimeMillis() - start > Time_out_ms) || depth <= 0 || config.jeu.pingouinTousPlace() ){
            return Heuristique.HnbCaseAccessible(config,this.iajoueur);

        }if(max){
            value = -Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase1(fils.get(i), profo, false );
                if(value < tempo){
                    value = tempo;
                }
            } 
        }else{
            value = Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase1(fils.get(i), profo, true );
                if(value > tempo){
                    value = tempo;
                }
            }

        }
        return value;
    }

    public double minimaxPhase2(Configuration config, int depth, boolean max){
        double value =0;
        int profo = depth-1;
        double tempo;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(config);
        if((System.currentTimeMillis() - start > Time_out_ms) || depth <= 0 || config.jeu.jeuTermine()){
            value = Heuristique.Hilot(config,this.iajoueur)*4 + Heuristique.HnbCaseAccessible(config,this.iajoueur);
            //System.out.println(config.jeu);
            //System.out.println(value + "\n");
            return value;

        }if(fils.size()==0){
            return minimaxPhase2(config,profo,!max);

        }
        if(max){
            value = -Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase2(fils.get(i), profo, false );
                if(value < tempo){
                    value = tempo;
                }
            } 
        }else{
            value = Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase2(fils.get(i), profo, true);
                if(value > tempo){
                    value = tempo;
                }
            }

        }
        return value;
    }

}