package Joueur;

import Model.Coup;
import Model.Jeu;
import Model.Position;
import java.util.ArrayList;


public class MainTestIA{
    private static int nbP = 100;
    private static int nbJ = 2;

    public static void main(String[] args){
        testerIA(nbP);
    }

    public static void testerIA(int nbPartie){
        int i = 0;
        int k = 0;
        int tab[] = new int[4];
        IAJoueur ia1;
        IAJoueur ia2;
        Coup cp;
        Position pos;

        while ( i < nbPartie){
            Jeu j = new Jeu((char)nbJ);
            ArrayList<IAJoueur> ialist = new ArrayList<IAJoueur>();


            ialist.add(new IAMinimax(j));
            ialist.add(new IAMoyen(j));

            while(!j.pingouinTousPlace()){
                k=0;
                while(k < ialist.size()){
                    pos= ialist.get(k).elaborePlacement();
                    j.placePingouin(pos.x, pos.y);
                    k++;
                }
            }
            while(!j.jeuTermine()){
                k=0;
                while(k < ialist.size()){
                    cp= ialist.get(k).elaboreCoup();
                    if(cp != null){
                        j.joue(cp);
                        //System.out.println("Joueur " +(k+1) + " joue");
                    }
                    k++;
                }
            }
            i++;
            tab[j.gagnant().get(0)-1]++;
            System.out.println("j"+j.gagnant()+" gagne");

        }
        String line = "";
        k=0;
        while(k< nbJ){
            line=line + "j"+(k+1)+" gagne "+tab[k]+ " fois " + (tab[k]*100/nbP) + "%\n";
            k++;
        }
        System.out.println(line);
    }
}