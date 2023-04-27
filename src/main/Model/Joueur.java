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

        listePingouin = new ArrayList<Pingouin>();//init la liste des pingouins


        for(int i =0; i< nbPingouin; i++){ //         //ajout a la liste des pingouins du joueur les pingouins
            Pingouin ping = new Pingouin();
            listePingouin.add(ping);
        }

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

    //afficher un joueur
    @Override
    public String toString() {
        return "Joueur = " + getNumeroJoueur() + ", score = " + getScore() + ", Pingouins  = " + getListePingouin().toString() ;
    }

    
}
