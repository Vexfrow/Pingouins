package main.Model;

public class Pingouin {
    
    private int numero;
    private Joueur joueur;

    private int x;
    private int y;


    //constructeur
    public Pingouin(int numero, Joueur joueur, int x, int y) {
        this.numero = numero;
        this.joueur = joueur;
        this.x = x;
        this.y = y;
    }

        //getters

    public int getNumero() {
        return numero;
    }


    public Joueur getJoueur() {
        return joueur;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }



}
