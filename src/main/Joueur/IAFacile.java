package Joueur;

import java.util.*;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import java.util.ArrayList;


public class IAFacile extends IAJoueur{
    int iajoueur;
    private long start;

    private static final int Time_out_ms = 2000;


    public IAFacile(Jeu j){
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
             CoupPondere cpP = new CoupPondere(fils.get(i).coup,Heuristique.montecarlo(fils.get(i),this.iajoueur,100));
             listCp.add(cpP);
        }  
        Collections.sort(listCp);
        Random r = new Random();
        if(listCp.size()>0){
            int taille = (int)((double)listCp.size()/1.5);
            return (listCp.get(r.nextInt(taille+1)).cp);
        }
        return null;
    }

}