package Joueur;

import Model.Jeu;
import Model.Position;
import Model.Coup;
import Model.JeuAvance;


public abstract class IAJoueur {
    JeuAvance j;
    public String name;

    IAJoueur(JeuAvance j){
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