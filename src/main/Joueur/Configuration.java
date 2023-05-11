package Joueur;

import Model.*;
import java.util.ArrayList;


public class Configuration{

    public Jeu jeu;
    public Coup coup;
    public Position position;


    Configuration(Jeu jeu, Coup coup){
        this.jeu = jeu;
        this.coup = coup;
    }

    Configuration(Jeu jeu, Position position){
        this.jeu = jeu;
        this.position = position;
    }

    Configuration(Jeu jeu){
        this.jeu = jeu;
    }

    public Configuration cloner(){
        return (new Configuration(jeu.cloner()));
    }




    public static ArrayList<Configuration> coupFilsPhase1(Configuration config){
        ArrayList<Position> listePos = config.jeu.case1poisson();

        Position pos;
        Configuration prochainConfig; 
        ArrayList<Configuration> configList = new ArrayList<Configuration>();
        if(!config.jeu.pingouinTousPlace()){
            for(int i = 0; i < listePos.size(); i++){
                    prochainConfig = config.cloner();
                    pos = listePos.get(i);
                    prochainConfig.jeu.placePingouin(pos.x,pos.y);
                    prochainConfig.position = pos;
                    configList.add(prochainConfig);
            }
        }
        return configList; 
    }



    public static ArrayList<Configuration> coupFilsPhase2(Configuration config){
        //System.out.println("\non entre dans Coupfils");
        Configuration neo = config.cloner();
        Configuration prochainConfig; 
        ArrayList<Pingouin> alp = neo.jeu.getListeJoueur().get(neo.jeu.getJoueurCourant()-1).getListePingouin();
        ArrayList<Position> positionList;
        ArrayList<Configuration> configList = new ArrayList<Configuration>();
        
        for(int i = 0; i < alp.size(); i++){
            positionList = neo.jeu.getCaseAccessible(alp.get(i).getLigne(), alp.get(i).getColonne());
            for(int j =0; j < positionList.size(); j ++){
                prochainConfig = config.cloner();
                Coup cp = new Coup(positionList.get(j).x, positionList.get(j).y, alp.get(i), false);
                prochainConfig.jeu.joue(cp);
                prochainConfig.coup = cp;
                configList.add(prochainConfig);
            }

        }
        return configList;
        
    }

}
