package Model;

import Model.Pingouin;
import Model.Joueur;

public class Coup {
    
    private int x;
    private int y;
    private Joueur joueur;
    private Pingouin pingouin;


    public Coup(int x, int y, Joueur joueur, Pingouin pingouin) {
        this.x = x;
        this.y = y;
        this.joueur = joueur;
        this.pingouin = pingouin;
    }

    
    public int getLigne(){
        return this.x;
    }

    public int getColonne(){
        return this.y;
    }

    public Joueur getJoueur(){
        return this.joueur;
    }

    public Pingouin getPingouin(){
        return this.pingouin;
    }

/* Setters */

    public void setLigne(int ligne){
        this.x = ligne;
    }

    public void setColonne(int colonne){
        this.y = colonne;
    }

    public void setJoueur(Joueur joueur){
        this.joueur = joueur;
    }

    public void setPingouin(Pingouin pingouin){
        this.pingouin = pingouin;
    }



    public Coup cloner(){
        Coup cp = new Coup(this.x,this.y,this.joueur, this.pingouin);
        return cp;
    }

    //afficher un coup
    @Override
    public String toString() {
        String string = "x= " + x + "y = " + y;
        return string;
    }


}
