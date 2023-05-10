package Joueur;

import java.util.*;
import java.lang.Math;
import Model.*;

public class Heuristique{

    //Aléatoire
    public static int Hrandom(Configuration config){
        Random r = new Random();
        int value = r.nextInt(50);
        return value;
    }

    // Ce truc marche moin bien que l'aléatoire
    //Nombre de coup possible
    public static int HcoupPossible(Configuration config, int joueuria){
        ArrayList<Pingouin> listePingouin = config.jeu.getListeJoueur().get(joueuria-1).getListePingouin();
        ArrayList<Position> listePos;
        ArrayList<Position> listePosTotal = new ArrayList<Position>();
        int i = 0;
        int j = 0;
        while( i < listePingouin.size()){
            listePos = null;
            listePos = config.jeu.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                listePosTotal.add(listePos.get(j));
                j++;
            }
            i++;
        }
            return listePosTotal.size();
        }



    // renvoie la différence de score (2 joueurs SEULEMENT)
    public static int Hdiffscore(Configuration config, int joueuria){
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;       
        Joueur joueur2= config.jeu.getListeJoueur().get(joueurCourant-1);
        int scores = joueur1.getScore()-joueur2.getScore();
        return 50;
    }


    public int Hilot(Configuration config, int joueuria){
        Hashtable<Integer, Position> vu= new Hashtable<Integer, Position>();
        Position pos;

        ArrayList<Position> listePos = new ArrayList<Position>();
        boolean changer = true;
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        ArrayList<Pingouin> listePingouin1 = config.jeu.getListeJoueur().get(joueuria-1).getListePingouin();

        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;  
        Joueur joueur2=config.jeu.getListeJoueur().get(joueurCourant-1);
        ArrayList<Pingouin> listePingouin2 = config.jeu.getListeJoueur().get(joueurCourant-1).getListePingouin();

        
        while(changer){
            
        }

        return 0;
    }



}