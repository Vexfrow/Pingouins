package Joueur;

import Model.*;


public class CoupPondere implements Comparable<CoupPondere>{
    public Coup cp;
    public double valeur;


    public CoupPondere(Coup cp, double valeur){
        this.cp=cp;
        this.valeur=valeur;
    }

    @Override
    public int compareTo(CoupPondere cpP){
        return (int)cpP.valeur-(int)this.valeur;
    }

    @Override
    public String toString() {
        return ""+this.valeur;
    }




}