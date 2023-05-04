package Model;

public class Pingouin {
    
    private int ligne; // coord du terrain Hexagonal
    private int colonne;

    //constructeur
    public Pingouin(int x, int y) {
        this.ligne = x;
        this.colonne = y;
    }

    // Renvoie une copie du pingouin
    public Pingouin cloner(){
        Pingouin ping = new Pingouin(this.ligne,this.colonne);
        return ping;
    }


        // Getters

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }


        // Setters

    public void setLigne(int x) {
        this.ligne = x;
    }

    public void setColonne(int y) {
        this.colonne = y;
    }


    // Affiche la position du pingouin
    public String toString(){
        String resultat = "ping: (";
        resultat += getLigne() + ", " + getColonne() + ")";
        return resultat;
    }


    public boolean equals(Pingouin p){
        return (p.colonne == this.colonne && p.ligne == this.ligne);
    }

}
