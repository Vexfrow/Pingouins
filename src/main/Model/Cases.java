package main.Model;

public class Cases {

    private boolean mange; //true/1 = mangé
    private int nbPoissons;
    private Pingouin pingouin;


    public Cases(boolean mange, int nbPoissons, Pingouin pingouin) {
        this.mange = mange;
        this.nbPoissons = nbPoissons;
        this.pingouin = pingouin;
    }

    public Cases(int nbPoissons) {
        this.nbPoissons = nbPoissons;
        this.mange = false;
    }

    public Cases cloneCases(boolean mange, int nbPoissons, Pingouin ping){
        if (ping == null) {
            return new Cases(mange, nbPoissons, null);
        } else {
            return new Cases(mange, nbPoissons, new Pingouin(ping.getNumero(), ping.getJoueur()));
        }
    }


        //getters

    public boolean estMange() {
        return mange;
    }

    public int getNbPoissons() {
        return nbPoissons;
    }


    public Pingouin getPingouin() {
        return this.pingouin;
    }

        //setters
    
    public void setMange(boolean mange) {
        this.mange = mange;
    }
    
    public void setNbPoissons(int nbPoissons) {
        this.nbPoissons = nbPoissons;
    }
    
    public void setPingouin(Pingouin pingouin) {
        this.pingouin = pingouin;
    }

    //afficher une case
    @Override
    public String toString() {

        return  "" +getNbPoissons();
        //return "Case: est mangé =  " + estMange() + " nombre de poisson =  " + getNbPoissons() + " getPingouin" + getPingouin() ;
    }

}
