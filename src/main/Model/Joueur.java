package Model;

import java.util.ArrayList;

public class Joueur {

    private int numeroJoueur;
    private int score;
    private int nbCasesMange;

    //0 pour non IA, 1 pour IA facile, 2 moyenne, 3 difficile...
    private int IA;

    public ArrayList<Pingouin> listePingouin;

    // Constructeur de joueur
    // Pour initialiser un joueur, on appelle avec score = 0
    public Joueur(int numeroJoueur, int score, int nbCasesMange, int IA){

        this.numeroJoueur = numeroJoueur;
        this.score = score;
        this.nbCasesMange = nbCasesMange;
        this.IA = IA;
        listePingouin = new ArrayList<Pingouin>();//init la liste des pingouins
    }


    public Joueur cloner(){
        Joueur joueur = new Joueur(numeroJoueur, score, nbCasesMange, IA);
        ArrayList<Pingouin> listePing = new ArrayList<Pingouin>();
        Pingouin pingouinPrefere;
        int i = 0;
        while(i < this.listePingouin.size()){
            pingouinPrefere = this.listePingouin.get(i).cloner();
            listePing.add(pingouinPrefere);
            i++;
        }
        joueur.listePingouin = listePing;
        return joueur;
    }

        // Getters


    public int getNumeroJoueur() {
        return numeroJoueur;
    }


    public int getScore() {
        return score;
    }


    public int getNbCasesMange() {
        return nbCasesMange;
    }

    public int estIA(){
        return IA;
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

    
    public void setNbCasesMange(int nbCasesMange) {
        this.nbCasesMange = nbCasesMange;
    }

    public void setIA(int IA){
        this.IA = IA;
    }



    //afficher un joueur
    @Override
    public String toString() {
        return "Joueur = " + getNumeroJoueur() + ", score = " + getScore() + ", nombre cases mangés = " + getNbCasesMange() + ", IA ="+ estIA() +", Pingouins  = " + getListePingouin().toString() ;
    }

    public String getName(){
        switch(IA){
            case 0 : return "Humain";
            case 1 : return "IA Facile";
            case 2 : return "IA Moyenne";
            case 3 : return "IA Difficile";
            default: return "IA Experte";
        }
    }
    
}
