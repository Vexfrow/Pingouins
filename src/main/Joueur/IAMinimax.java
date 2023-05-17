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
    private double coefIlot = 0;
    private double coefNBCoup = 0;
    private double coefDiffScore =4;
    private double coefNBCaseAcc = 2;
    private double coefMonteCarlo = 0;
    private double coefCaseAccAdv = 4;
    private double coefNBCoupAdv = 0;


    private static final int Time_out_ms = 5000;


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
        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase1(fils.get(i), 2 , conf.jeu.getNbJoueur()-1)) > max ){
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
        int nbcase = 0;
        int bonus;
        for(int i = 0; i< listeJoueur.size(); i++){
            nbcase+= listeJoueur.get(i).getNbCasesMange();
        }
        if(nbcase <=10){
            nbcase = 10;
        }
        bonus = (int)Math.cbrt((double)nbcase-9);
        Configuration conf = new Configuration(this.j.cloner());
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(conf);
        double max = -Double.MAX_VALUE;
        double temp = 0;
        Coup coupMax = null;

        for(int i = 0; i < fils.size(); i++){
            if((temp = minimaxPhase2(fils.get(i), 1+bonus, conf.jeu.getNbJoueur()-1)) > max ){
                max = temp;
                coupMax = fils.get(i).coup;
            }
            
        }
        return coupMax;
    }


    public double minimaxPhase1(Configuration config, int depth, int max){
        double value;
        int profo = depth-1;
        double tempo;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase1(config);
        if((System.currentTimeMillis() - start > Time_out_ms) || depth <= 0 || config.jeu.pingouinTousPlace() ){
            return Heuristique.HnbCaseAccessible(config,this.iajoueur);

        }if(max <= 0){
            value = -Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase1(fils.get(i), profo, config.jeu.getNbJoueur()-1 );
                if(value < tempo){
                    value = tempo;
                }
            } 
        }else{
            value = Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase1(fils.get(i), profo, max-1 );
                if(value > tempo){
                    value = tempo;
                }
            }
        }
        return value;
    }

    public double minimaxPhase2(Configuration config, int depth, int max){
        double value =0;
        int profo = depth-1;
        double tempo;
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(config);
        if((System.currentTimeMillis() - start > Time_out_ms) || depth <= 0 || config.jeu.jeuTermine()){
            value = /*Heuristique.Hilot(config,this.iajoueur)*coefIlot*/ + Heuristique.HnbCaseAccessible(config,this.iajoueur)*coefNBCaseAcc
            + Heuristique.Hdiffscore(config,this.iajoueur)*coefDiffScore +Heuristique.HcoupPossible(config,this.iajoueur)*coefNBCoup
            + /*Heuristique.montecarlo(config,this.iajoueur,2)*coefMonteCarlo*/ -Heuristique.HcoupPossibleAdv(config,this.iajoueur)*coefNBCoupAdv
           -Heuristique.HnbCaseAccessibleAdv(config,this.iajoueur)*coefCaseAccAdv;
            return value;

        }if(fils.size()==0){
            return minimaxPhase2(config,profo,max-1);

        }
        if(max <=0){
            value = -Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase2(fils.get(i), profo, config.jeu.getNbJoueur()-1);
                if(value < tempo){
                    value = tempo;
                }
            } 
        }else{
            value = Double.MAX_VALUE;
            for(int i =0; i < fils.size(); i++){
                tempo = minimaxPhase2(fils.get(i), profo, max-1);
                if(value > tempo){
                    value = tempo;
                }
            }

        }
        return value;
    }

}