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

    public Coup cloner(){
        Coup cp = new Coup(this.x,this.y, this.pingouin);
        return cp;
    }


    @Override
    public String toString() {
        return "PingouinJoueur = " + pingouin.getJoueur()+ ", numéro pingouin : " + pingouin.getNumero() + ", x = "+ x + ", y = " + y;
    }


}
