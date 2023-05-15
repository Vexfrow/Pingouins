package Joueur;

import Model.Coup;
import Model.Jeu;
import Model.Jeu;
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
        testerIA(100);
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
            Jeu j = new Jeu(2);

            ia1 = new IAAleatoire(j);
            ia2 = new IAMinimax(j);

            while(!j.pingouinTousPlace()){

                pos = ia1.elaborePlacement();
                 //System.out.println(j);
                 //System.out.println("position vaut + "+ pos);                
                j.placePingouin(pos.x, pos.y);
                
                pos = ia2.elaborePlacement();
                 //System.out.println(j);
                 //System.out.println("position vaut + "+ pos);
                j.placePingouin(pos.x,pos.y);


                //System.out.println("nb pingouin reste pllacer = " + j.getNbPingouinPlace());
                

            }
            while(!j.jeuTermine()){
                cp = ia1.elaboreCoup();

                if(cp == null){
                }else{
                    j.joue(cp);
                    System.out.println( "j1 JOUE ");
                    //System.out.println(j);
                }



                cp= ia2.elaboreCoup();
                if(cp == null){
                }else{
                    j.joue(cp);
                    System.out.println( "j2 JOUE ");
                    //System.out.println(j);
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
        if((winj1 + winj2) !=0){
            System.out.println("j1 gagne avec " + (winj1*100)/(winj2 + winj1)+"% de chance" +"\nj2 gagne avec " + (winj2*100)/(winj2 + winj1)+"% de chance");
        }
    }
}