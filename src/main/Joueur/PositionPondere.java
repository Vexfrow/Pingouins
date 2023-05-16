package Joueur;

import Model.*;


public class PositionPondere implements Comparable<PositionPondere>{
    public Position pos;
    public double valeur;


    public PositionPondere(Position pos, double valeur){
        this.pos=pos;
        this.valeur=valeur;
    }

    @Override
    public int compareTo(PositionPondere cpP){
        return (int)cpP.valeur-(int)this.valeur;
    }

    @Override
    public String toString() {
        return ""+this.valeur;
    }

}