package Joueur;

import Model.Coup;
import Model.Jeu;
import Model.JeuAvance;
import Model.Position;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;
import Joueur.IAAleatoire;
import Joueur.IAJoueur;


public class MainTestIA{


    public static void main(String[] args){
        testerIA(1);
    }

    public static void testerIA(int nbPartie){
        int i = 0;
        int winj1 =0;
        int winj2 =0;
        IAJoueur ia1;
        IAJoueur ia2;
        Coup cp;
        Position pos;


        while ( i < nbPartie){
            JeuAvance j = new JeuAvance(2);

            ia1 = new IAAleatoire(j);
            ia2 = new IAMinimax(j);

            while(!j.pingouinTousPlace()){

                pos = ia1.elaborePlacement();
                j.placePingouin(pos.x, pos.y);
                
                pos = ia2.elaborePlacement();
                j.placePingouin(pos.x,pos.y);

            }
            while(!j.jeuTermine()){
                cp = ia1.elaboreCoup();

                if(cp == null){
                   j.switchJoueur();
                }else{
                    j.joue(cp);
                }



                cp= ia2.elaboreCoup();
                if(cp == null){
                   j.switchJoueur();
                }else{
                    j.joue(cp);
                }


            }

            if(j.getScore(1) > j.getScore(2)){
                winj1++;
            }else if(j.getScore(1) < j.getScore(2)){
                winj2++;
            }
            i++;
        }
        System.out.println( "j1 gagne: "+ winj1+ "   j2 gagne: "+ winj2);

    }

    public static void test(){
        JeuAvance j = new JeuAvance(2);
        int i =0;
        
        IAJoueur ia = new IAAleatoire(j);
        IAJoueur ia2 = new IAMinimax(j);
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