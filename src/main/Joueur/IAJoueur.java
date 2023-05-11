package Joueur;

import Model.Jeu;
import Model.Position;
import Model.Coup;


public abstract class IAJoueur {
    Jeu j;
    public String name;

    IAJoueur(Jeu j){
        this.j = j;
    }


    public Coup elaboreCoup(){
        return null;
    }
    
    public Position elaborePlacement(){
        return null;
    }

    public void setName(String s){}

}