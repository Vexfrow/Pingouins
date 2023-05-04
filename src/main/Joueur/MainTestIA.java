package Joueur;

import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;
import Joueur.IAAleatoire;
import Joueur.IAJoueur;


public class MainTestIA{


    public static void main(String[] args){
        
        Jeu j = new Jeu(2);
        int i =0;
        
        IAJoueur ia = new IAAleatoire(j);
        IAJoueur ia2 = new IATroisPoissons(j);
        Position pos;
        Coup cp;

        
        System.out.println(j);

        while(i< 4){

            pos = ia.elaborePlacement();
            j.placePingouin(pos.x, pos.y);
            System.out.println("ia 1: "+ pos);
            
            pos = ia2.elaborePlacement();
            j.placePingouin(pos.x,pos.y);
            System.out.println("ia 2:" +pos);

            i++;
        }
        System.out.println(j);


         i =0;
        while(i < 10){
            cp = ia.elaboreCoup();
            if(cp == null){
                break;
            }
            j.joue(cp);
            System.out.println(cp);


            cp= ia2.elaboreCoup();
            if(cp == null){
                break;
            }
            j.joue(cp);
            System.out.println(cp);

        }

        System.out.println(j);

    }
}