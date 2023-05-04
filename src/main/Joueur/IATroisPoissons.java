package Joueur;


import java.util.Random;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;

public class IATroisPoissons extends IAJoueur{

    IATroisPoissons(Jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        return null;
    }

    public Coup elaboreCoup(){
        return null;
    }

}