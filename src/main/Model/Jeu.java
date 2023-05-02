package main.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;

public class Jeu{

    private Cases [][] terrainInitiale;
    private Cases [][] terrainCourant;




    Jeu(String name){

    }

    public Jeu(){


    }

    Jeu(int nbLignes, int nbColonnes, int nbJoueurs, int PingParJoueur){

    }

    public void terrainAleatoire(){

    }

    public Cases [][] getTerrain(){
        return terrainCourant;
    }

    public Cases getCase(int ligne, int colonne){
        return null;
    }

    public void setCase(Cases c){

    }


    public void joue(Coup c){

    }

    public void joue(int numEq, int numPing, int ligne, int colonne){

    }
    
    public boolean peutAnnule(){
        return false;
    }

    public void annule(){

    }
    
    public boolean peutRefaire(){
        return false;
    }

    public void refaire(){

    }

    public void sauvegarder(String name){

    }

    public String toString(){
        return null;
    }

    
}