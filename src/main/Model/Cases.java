package Model;


public class Cases {

    private boolean mange; //true = mangé
    private int nbPoissons;
    private int pingouin;


    public Cases(boolean mange, int nbPoissons, int pingouin) {
        this.mange = mange;
        this.nbPoissons = nbPoissons;
        this.pingouin = pingouin;
    }

    public Cases(int nbPoissons) {
        this.nbPoissons = nbPoissons;
        this.mange = false;
        this.pingouin=0;
    }

        //getters

    // Renvoie true si une case a ete mangee
    public boolean estMange() {
        return mange;
    }

    // Renvoie le nombre de poissons sur une case
    public int getNbPoissons() {
        return nbPoissons;
    }

    // Si un pingouin se trouve sur la case, renvoie le numero 
    // du joueur auquel il appartient
    public int pingouinPresent() {
        return pingouin;
    }

        //setters
    
    public void setMange(boolean mange) {
        this.mange = mange;
    }
    
    public void setNbPoissons(int nbPoissons) {
        this.nbPoissons = nbPoissons;
    }
    
    public void setPingouin(int pingouin) {
        this.pingouin = pingouin;
    }

    //afficher une case
    @Override
    public String toString() {
        //ce retrurn est pour la sauvegarde
        return  "" +getNbPoissons();
        //return /*estMange() +*/ "/" + getNbPoissons() + "/" + pingouinPresent() ;
    }

}
