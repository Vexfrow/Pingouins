package main.Model;

public class Pingouin {
    
    private int numero;
    private Joueur joueur;

    private int ligne; // coord du terrain Hexagonal
    private int colonne;


    //constructeur
    public Pingouin(int numero, Joueur joueur, int x, int y) {
        this.numero = numero;
        this.joueur = joueur;
        this.ligne = x;
        this.colonne = y;
    }

     
     
    public Pingouin cloner(){
        Pingouin ping = new Pingouin(this.numero, this.joueur,this.x,this.y);
    }
        //getters

    public int getNumero() {
        return numero;
    }


    public Joueur getJoueur() {
        return joueur;
    }


    public int getLigne() {
        return ligne;
    }


    public int getColonne() {
        return colonne;
    }

    /* retourner un couple de coordonnées
    public int getCoordonnees(){
        return;
    }
    */


        //setters


    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setLigne(int x) {
        this.ligne = x;
    }

    public void setColonne(int y) {
        this.colonne = y;
    }



}
