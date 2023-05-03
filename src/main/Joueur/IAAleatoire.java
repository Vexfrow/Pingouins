package Joueur;

import java.util.Random;
import Model.coup;
import Model.jeu;
import Model.Position;
import Model.cases;
import java.util.ArrayList;




public class IAAleatoire extends Joueur{
    Random r;


    IAAleatoire(jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    public Position elaborePlacement(){
        Cases[][] terrainCourant = this.j.clonerTerrain(this.j.terrainCourant);
        ArrayList<Position> positionPossible = new ArrayList<Position>();
        
    }


















}