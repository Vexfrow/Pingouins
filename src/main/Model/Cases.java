package main.Model;

public class Cases {

    private boolean mange; //true/1 = mangé
    private int nbPoissons;
    private boolean pingouin;


    public Cases(boolean mange, int nbPoissons, boolean pingouin) {
        this.mange = mange;
        this.nbPoissons = nbPoissons;
        this.pingouin = pingouin;
    }

    public Cases(int nbPoissons) {
        this.nbPoissons = nbPoissons;
        this.mange = false;
        this.pingouin=false;
    }
        //getters

    public boolean estMange() {
        return mange;
    }

    public int getNbPoissons() {
        return nbPoissons;
    }


    public boolean pingouinPresent() {
        return pingouin;
    }

        //setters
    
    public void setMange(boolean mange) {
        this.mange = mange;
    }
    
    public void setNbPoissons(int nbPoissons) {
        this.nbPoissons = nbPoissons;
    }
    
    public void setPingouin(boolean pingouin) {
        this.pingouin = pingouin;
    }

    
}
