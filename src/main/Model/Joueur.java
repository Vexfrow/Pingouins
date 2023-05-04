package Model;

import java.util.ArrayList;

public class Joueur {

    private int numeroJoueur;
    private int score;

    //liste des pingouins pour le joueur
    public ArrayList<Pingouin> listePingouin;

    // Constructeur de joueur
    // Pour initialiser un joueur, on appelle avec score = 0
    Joueur(int numeroJoueur, int score){

        this.numeroJoueur = numeroJoueur;
        this.score = score;
        listePingouin = new ArrayList<Pingouin>();//init la liste des pingouins
    }


    // Renvoie 1 si tous les pingouins pour un joueur sont tous places sur le plateau

        // Getters

    public int getNumeroJoueur() {
        return numeroJoueur;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Pingouin> getListePingouin() {
        return listePingouin;
    }

    public Pingouin getPingouin(Pingouin ping){
        int i =0;
        while( i< listePingouin.size() && (ping.getLigne() != listePingouin.get(i).getLigne() ||  ping.getColonne() != listePingouin.get(i).getColonne()) ){
            i++;
        }
        if(i == listePingouin.size()){
            return null;
        }else{
            return listePingouin.get(i);
        }
    }


        // Setters

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
