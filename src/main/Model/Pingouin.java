package main.Model;

public class Pingouin {
    
    private int ligne; // coord du terrain Hexagonal
    private int colonne;


    //constructeur
    public Pingouin(int x, int y) {
        this.ligne = x;
        this.colonne = y;
    }

     
     
    public Pingouin cloner(){
        Pingouin ping = new Pingouin(this.x,this.y);
    }
        //getters


    public int getLigne() {
        return ligne;
    }


    public int getColonne() {
        return colonne;
    }


        //setters


    public void setLigne(int x) {
        this.ligne = x;
    }

    public void setColonne(int y) {
        this.colonne = y;
    }



}
