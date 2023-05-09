package Joueur;

import Model.*;
import java.util.ArrayList;


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
        return (new Configuration(jeu.cloner()));
    }

    public static ArrayList<Configuration> coupFils(Configuration config){
        //System.out.println("\non entre dans Coupfils");
        Configuration neo = config.cloner();
        Configuration prochainConfig; 
        ArrayList<Pingouin> alp = neo.jeu.getListeJoueur().get(neo.jeu.getJoueurCourant()-1).getListePingouin();
        ArrayList<Position> positionList;
        ArrayList<Configuration> configList = new ArrayList<Configuration>();
        for(int i = 0; i < alp.size(); i++){

            positionList = neo.jeu.getCaseAccessible(alp.get(i).getLigne(), alp.get(i).getColonne());
            //System.out.println("\nLA taille de:" + positionList.size());
            //System.out.println(positionList);
            
            //System.out.println("Le pingouin est en: " +alp.get(i).getLigne() +"   "+ alp.get(i).getColonne());
            for(int j =0; j < positionList.size(); j ++){
                //System.out.println("Addr ping " + alp.get(i));
                //System.out.println("Les positions sont: " +positionList.get(j).x + "   "+ positionList.get(j).y);
                prochainConfig = config.cloner();
                //System.out.println("prochConf  " + prochainConfig + " jeu" + prochainConfig.jeu);
                Coup cp = new Coup(positionList.get(j).x, positionList.get(j).y, alp.get(i), false);
                //System.out.println(cp);
                //System.out.println(prochainConfig.jeu);
                prochainConfig.jeu.joue(cp);
                prochainConfig.coup = cp;
                configList.add(prochainConfig);
            }

        }
        return configList;
        
    }

}
