package main.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.ArraysList;
import java.util.Random;

public class Jeu{

    private Cases [][] terrainInitiale;
    private Cases [][] terrainCourant;
    private ArraysList<Coup> coupJoue;
    private ArraysList<Coup> coupAnnule;
    private ArrayList<Pingouin> listePingouin;




    Jeu(String name){

    }

    Jeu(){
        i

    }

    Jeu(int nbLignes, int nbColonnes, int nbJoueurs, int PingParJoueur){
        terrainInitiale = new Cases[nbLignes][nbColonnes*2-1];
        terrainCourant = new Cases[nbLignes][nbColonnes*2-1];
        int l = 0;
        int c, r;

        
        if( l%2 ==1 ){// si ligne impaire
            c = 0
        }else{ // ligne paire
            c = 1;
        }
        while( l < nbLignes ){

            if( l%2 ==1 ){// si ligne impaire
                c = 0
            }else{ // ligne paire
                c = 1;
            }

            while( c < nbColonnes*2-1){
                r = r.nextInt(3)+1;
                terrainInitiale[l][c]= new Cases(r);
                terrainCourant[l][c]= new Cases(r);
                c+=2;
            }
            l++;
        }

    }

    public void terrainAleatoire(){


    }

    public Cases [][] getTerrain(){
        return terrainCourant;
    }


    // Ligne compris entre [0, nbLigne[, ligne est la position sur le terrain hexagonal
    // Colonne compris entre [0, nbColonne[, colonne est la position sur le terrain hexagonal
    public Cases getCase(int ligne, int colonne){
            if( ligne%2 ==1 ){// si ligne impaire
                return terrainCourant[ligne][colonne*2+1];
            }else{ // ligne paire
                return terrainCourant[ligne][colonne*2];
            }
    }

    public void setCase(Cases cases, int ligne, int colonne){

            if( ligne%2 ==1 ){// si ligne impaire
                terrainCourant[ligne][colonne*2+1] = cases;
            }else{ // ligne paire
                terrainCourant[ligne][colonne*2] = cases;
            }
    }


    public void joue(Coup){

    }

    public void joue(int numEq, int numPing, int ligne, int colonne){

    }
    
    public boolean peutAnnule(){

    }

    public void annule(){

    }
    
    public boolean peutRefaire(){

    }

    public void refaire(){

    }

    public void sauvegarder(String name){

    }

    public String toString(){
        return null;
    }

    
}