package main.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.ArraysList;

public class Jeu{

    private Cases [][] terrainInitiale;
    private Cases [][] terrainCourant;
    private ArraysList<Coup> coupJoue;
    private ArraysList<Coup> coupAnnule;




    Jeu(String name){

    }

    Jeu(){

    }

    Jeu(int nbLignes, int nbColonnes, int nbJoueurs, int PingParJoueur){

    }
    
    public void terrainAleatoire(){
    }

    public Cases [][] getTerrain(){
        return terrainCourant;
    }

    public Cases getCase(int ligne, int colonne){
        return Cases;
    }

    public void setCase(Cases case){

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
    public void sauvegarder(){

    }

    public String toString(){
        return null;
    }

    
}