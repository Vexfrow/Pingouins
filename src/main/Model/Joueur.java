package main.Model;

import java.util.ArrayList;

public class Joueur {

    private int numeroJoueur;
    private int score;

    //liste des pingouins pour le joueur
    private ArrayList<Pingouin> listePingouin;

    //constructeur de joueur
    Joueur(int numero, int nbPingouin){
        numeroJoueur = numero;
        score = 0;

        //init la liste des pingouins


    }


        //getters

    public int getNumeroJoueur() {
        return numeroJoueur;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Pingouin> getListePingouin() {
        return listePingouin;
    }


        //setters

    public void setNumeroJoueur(int numeroJoueur) {
        this.numeroJoueur = numeroJoueur;
    }


    public void setScore(int score) {
        this.score = score;
    }

    
}
