package main.Model;
import main.Model.Pingouin;

import java.util.ArrayList;

public class Joueur {

    private int numeroJoueur;
    private int score;
    private int nbPingouin;

    //liste des pingouins pour le joueur
    public ArrayList<Pingouin> listePingouin;

    //constructeur de joueur
    Joueur(int numeroJoueur, int nbPingouin, int nbpingouin){

        this.numeroJoueur = numeroJoueur;
        this.score = 0;

        listePingouin = new ArrayList<Pingouin>();//init la liste des pingouins
    }


    public boolean tousPingouinPlace(){
        return (listePingouin.size() == nbPingouin);
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

    public Joueur cloner(){
        Joueur joueuse = new Joueur(this.numeroJoueur, this.score, this.nbPingouin);
        int i = 0;
        while( i < this.listePingouin.size()){
            joueuse.listePingouin.add(this.listePingouin.get(i));
        }
        return joueuse;
    }

    
}
