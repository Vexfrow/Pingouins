package main.Model;

public class Coup {
    
    private int x;
    private int y;
    private Pingouin pingouin;



    public Coup(int x, int y, Pingouin pingouin) {
        this.x = x;
        this.y = y;
        this.pingouin = pingouin;
    }

    
    public int getLigne(){
        return this.x;
    }

    public int getColonne(){
        return this.y;
    }

    public Pingouin getPingouin(){
        return this.pingouin
    }

    public void setLigne(int ligne){
        this.x = ligne;
    }

    public void setColonne(int colonne){
        this.y = colonne;
    }

    public void setPingouin(Pingouin pingouin){
        this.pingouin = pingouin;
    }

    public Coup cloner(){
        Coup cp = new Coup(this.x,this.y, this.pingouin);
        return cp;
    }


    @Override
    public String toString() {
        return "PingouinJoueur = " + pingouin.getJoueur()+ ", numéro pingouin : " + pingouin.getNumero() + ", x = "+ x + ", y = " + y;
    }


}
