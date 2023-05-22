package Joueur;

import java.util.*;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import java.util.ArrayList;


public class IAExpert extends IAJoueur{
    int iajoueur;
    private long start;

    private static final int Time_out_ms = 2000;


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

        ArrayList<PositionPondere> listCp = new ArrayList<PositionPondere>();

        for(int i = 0; i < fils.size(); i++){
            PositionPondere cpP = new PositionPondere(fils.get(i).position,Heuristique.HnbCaseAccessible(fils.get(i),this.iajoueur));
            listCp.add(cpP);
        }  
        Collections.sort(listCp);
        if(listCp.size()>0){
            return listCp.get(0).pos;
        }
        return null;
    }
    
    @Override
    public Coup elaboreCoup(){

        this.iajoueur = this.j.getJoueurCourant();
        Configuration conf = new Configuration(this.j.cloner());
        ArrayList<Configuration> fils = Configuration.coupFilsPhase2(conf);

        ArrayList<CoupPondere> listCp = new ArrayList<CoupPondere>();

        for(int i = 0; i < fils.size(); i++){
             CoupPondere cpP = new CoupPondere(fils.get(i).coup,Heuristique.montecarlo(fils.get(i),this.iajoueur,500));
             listCp.add(cpP);
        }  
        Collections.sort(listCp);
        if(listCp.size()>0){
            return listCp.get(0).cp;
        }
        return null;
    }

}