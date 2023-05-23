package Joueur;

import java.util.*;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import java.util.ArrayList;


public class IAExpert extends IAJoueur{
    int iajoueur;
    private long start;



    public IAExpert(Jeu j){
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
        double max = -Double.MAX_VALUE;
        double temp = 0;
        Position posmax = null;
        for(int i = 0; i < fils.size(); i++){
            if((temp = Heuristique.HnbCaseAccessible(fils.get(i),this.iajoueur)) > max ){
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

        ArrayList<CoupPondere> listCp = new ArrayList<CoupPondere>();

        for(int i = 0; i < fils.size(); i++){
             CoupPondere cpP = new CoupPondere(fils.get(i).coup,Heuristique.montecarlo(fils.get(i),this.iajoueur,200));
             listCp.add(cpP);
        }  
        Collections.sort(listCp);
        if(listCp.size()>0){
            return listCp.get(0).cp;
        }
        return null;
    }

}