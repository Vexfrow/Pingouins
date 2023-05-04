package Model;

public class Coup {

    public boolean place; // 1 = on place le pingouin, sinon on joue.
    private int x; // On veut aller,
    private int y;
    private int ancienX;
    private int ancienY;
    private Pingouin pingouin;  // non remplis si place = 1.


    public Coup(int x, int y, Pingouin pingouin, boolean place) {
        this.x = x;
        this.y = y;
        this.pingouin = pingouin;
        this.place = place;
    }


    /* 
    public Coup(int x, int y, int ancienX, int ancienY, Pingouin pingouin, boolean place) {
        this.x = x;
        this.y = y;
        this.ancienX = ancienX;
        this.ancienY = ancienY;
        this.pingouin = pingouin;
        this.place = place;
    }
    */

        // Getters
    
    public int getLigne(){
        return this.x;
    }


    public int getColonne(){
        return this.y;
    }


    public int getAncienX() {
        return this.ancienX;
    }


    public int getAncienY() {
        return this.ancienY;
    }


    public Pingouin getPingouin(){
        return this.pingouin;
    }


    public boolean estPlace(){
        return this.place;
    }


        // Setters 

    public void setLigne(int ligne){
        this.x = ligne;
    }


    public void setAncienX(int ancienX) {
        this.ancienX = ancienX;
    }


    public void setAncienY(int ancienY) {
        this.ancienY = ancienY;
    }


    public void setColonne(int colonne){
        this.y = colonne;
    }


    public void setPingouin(Pingouin pingouin){
        this.pingouin = pingouin;
    }


    // Renvoie une copie du coup
    public Coup cloner(){
        Coup cp = new Coup(this.x,this.y, this.pingouin, this.place);
        return cp;
    }

    // Renvoie une copie du coup
    /*
    public Coup cloner2(){
        Coup cp = new Coup(this.x,this.y, this.ancienX, this.ancienY, this.pingouin, this.place);
        return cp;
    }
    */
    
    //afficher un coup
    @Override
    public String toString() {
        String string = "x=" + x + " y=" + y + " " + pingouin+ " place="+ place;
        return string;
    }


}
