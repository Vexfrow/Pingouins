package Model;

import java.util.ArrayList;

public class Joueur {

    private int numeroJoueur;
    private int score;
    private int nbPingouin;

    //liste des pingouins pour le joueur
    public ArrayList<Pingouin> listePingouin;

    // Constructeur de joueur
    // Pour initialiser un joueur, on appelle avec score = 0
    public Joueur(int numeroJoueur, int score, int nbPingouin){

        this.numeroJoueur = numeroJoueur;
        this.score = score;
        this.nbPingouin = nbPingouin;
        listePingouin = new ArrayList<Pingouin>();//init la liste des pingouins
    }


    // Renvoie 1 si tous les pingouins pour un joueur sont tous places sur le plateau
    public boolean tousPingouinsPlaces(){
        return (listePingouin.size() == nbPingouin);
    }


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

    // Renvoie une copie d'un joueur 
    public Joueur cloner(){
        Joueur joueuse = new Joueur(this.numeroJoueur, this.score, this.nbPingouin);
        int i = 0;
        while( i < this.listePingouin.size()){
            joueuse.listePingouin.add(this.listePingouin.get(i));
        }
        return joueuse;
    }
    
}
