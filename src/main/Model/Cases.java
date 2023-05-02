package Model;


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

    // Renvoie 1 si une case a ete mangee
    public boolean estMange() {
        return mange;
    }

    // Renvoie le nombre de poissons sur une case
    public int getNbPoissons() {
        return nbPoissons;
    }

    // Renvoie 1 si un pingouin se trouve sur la case
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

    //afficher une case
    @Override
    public String toString() {

        return  "" +getNbPoissons();
        //return "Case: est mangé =  " + estMange() + " nombre de poisson =  " + getNbPoissons() + " getPingouin" + getPingouin() ;
    }

}