package Joueur;

import Model.Jeu;
import Model.Position;
import Model.Coup;


public abstract class Joueur {
    Jeu j;
    public String name;

    Joueur(Jeu j){
        this.j = j;
    }


    public Coup elaboreCoup(){
        return null;
    }
    
    public Position elaborePlacement();

    public void setName(String s){}

}