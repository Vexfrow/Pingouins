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
import java.util.Enumeration;
import java.util.Hashtable;


public class MainTestIA{


    public static void main(String[] args){
        testerIA(200);
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
                    //System.out.println( "j1 JOUE ");
                }



                cp= ia2.elaboreCoup();
                if(cp == null){
                   j.switchJoueur();
                }else{
                    j.joue(cp);
                    //System.out.println( "j2 JOUE ");
                }


            }

            if(j.getScore(1) > j.getScore(2)){
                winj1++;
                System.out.println( "j1 gagne ");
            }else if(j.getScore(1) < j.getScore(2)){
                winj2++;
                System.out.println( "j2 gagne ");
            }
            i++;
        }
        System.out.println( "j1 gagne: "+ winj1+ "   j2 gagne: "+ winj2);
        System.out.println("j1 gagne avec " + (winj1*100)/(winj2 + winj1)+"% de chance" +"\nj2 gagne avec " + (winj2*100)/(winj2 + winj1)+"% de chance");

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