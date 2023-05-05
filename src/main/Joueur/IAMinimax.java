package Joueur;

import java.util.Random;
import Model.Coup;
import Model.Jeu;
import Model.JeuAvance;
import Model.Position;
import Model.Joueur;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;



public class IAMinimax extends IAJoueur{

    IAMinimax(JeuAvance j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        
        return null;
    }
    
    @Override
    public Coup elaboreCoup(){
        return null;
    }


}