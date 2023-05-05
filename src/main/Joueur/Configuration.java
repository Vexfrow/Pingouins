package Joueur;

import Model.Jeu;
import Model.Coup;


public class Configuration{

    public Jeu jeu;
    public Coup coup;


    Configuration(Jeu jeu, Coup coup){
        this.jeu = jeu;
        this.coup = coup;
    }

    Configuration(Jeu jeu){
        this.jeu = jeu;
    }

    public Configuration cloner(){
        return (new Configuration(jeu.cloner(),coup.cloner()));
    }
}
