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
        return scores+50;
    }

/*
    public static int montecarlo(Configuration config, int joueuria){
        int i = 0;
        int winj1 =0;
        int winj2 =0;
        IAJoueur ia1;
        IAJoueur ia2;
        Coup cp;
        Position pos;


        while ( i < 10){
            Jeu j = config.jeu.cloner(); 

            ia1 = new IAAleatoire(j);
            ia2 = new IAAleatoire(j);

            while(!j.pingouinTousPlace()){

                pos = ia1.elaborePlacement();
                j.placePingouin(pos.x, pos.y);
                
                pos = ia2.elaborePlacement();
                j.placePingouin(pos.x,pos.y);

            }
            while(!j.jeuTermine()){
                cp = ia1.elaboreCoup();

                if(cp == null){
                   j.switchJoueur();
                }else{
                    j.joue(cp);
                    //System.out.println( "j1 JOUE ");
                }



                cp= ia2.elaboreCoup();
                if(cp == null){
                   j.switchJoueur();
                }else{
                    j.joue(cp);
                    //System.out.println( "j2 JOUE ");
                }


            }

            if(j.getScore(1) > j.getScore(2)){
                winj1++;
            }else if(j.getScore(1) < j.getScore(2)){
                winj2++;
            }
            i++;
        }
        return  winj1;
    }
    */


    public static int Hilot(Configuration config, int joueuria){
        Hashtable<Integer, Position> vu= new Hashtable<Integer, Position>();
        Position pos;
        Pingouin ping;
        int score = 100;

        ArrayList<Position> listePos = new ArrayList<Position>();
        boolean changer = true;
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        ArrayList<Pingouin> listePingouin1 = joueur1.getListePingouin();

        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;  
        Joueur joueur2=config.jeu.getListeJoueur().get(joueurCourant-1);
        ArrayList<Pingouin> listePingouin2 = config.jeu.getListeJoueur().get(joueurCourant-1).getListePingouin();

        int i = 0;
        int tempo;
        while(i < listePingouin1.size()){
            ping = listePingouin1.get(i);
            if((tempo =estIlot(ping, config.jeu, joueur2) )!= 0){
                //System.out.println("abc "+tempo+" score vaut "+ score);
                score+=tempo;
                //System.out.println("def score vaut "+ score);
            }
            i++;
        }
        i=0;
        while(i < listePingouin2.size()){
            ping = listePingouin2.get(i);
            if((tempo =estIlot(ping, config.jeu, joueur1) )!= 0){
                //System.out.println("tempo vaut "+ tempo);
                score-=tempo;
            }
            i++;
        }
        //System.out.println("Au revoir l'heuristique");
        return score;
    }



    public static int estIlot(Pingouin ping, Jeu jeu, Joueur joueur){
        ArrayList<Position> caseAcc;
        ArrayList<Position> caseAccTotal = new ArrayList<Position>(); 
        Hashtable<Integer, Position> vuJoueur= new Hashtable<Integer, Position>();
        Hashtable<Integer, Position> vuPingouin= new Hashtable<Integer, Position>();
        ArrayList<Pingouin> listePingouin = joueur.getListePingouin();
        Pingouin pingouinCourant;
        Position pos;
        int score = 0;
        int j =0;
        int i = 0;
        while( i < listePingouin.size()){
            pingouinCourant = listePingouin.get(i);
            caseAcc = jeu.getCaseAccessible(pingouinCourant.getLigne(),pingouinCourant.getColonne());
            j=0;
            while(j< caseAcc.size()){
                pos = caseAcc.get(j);
                if(!vuJoueur.containsKey(pos.hash())){
                    caseAccTotal.add(pos);
                    vuJoueur.put(pos.hash(),pos);
                }
                j++;
            }
            i++;
        }
        Position posC;
        Stack<Position> Avisit = new Stack<Position>();
        caseAcc=jeu.getCaseAccessible(ping.getLigne(),ping.getColonne());
        while(i< caseAcc.size()){
            Avisit.push(caseAcc.get(i));
            i++;
        }
        i =0;
        while(!Avisit.empty()){
            pos = Avisit.pop();
            if(vuJoueur.containsKey(pos.hash())){
                return 0;
            }else{
                caseAcc=jeu.getCaseAccessible(pos.x,pos.y);
                while(i< caseAcc.size()){
                    posC = caseAcc.get(i);
                    if(!vuPingouin.containsKey(pos.hash())){
                        score+=jeu.getCase(pos.x,pos.y).getNbPoissons();
                        Avisit.push(caseAcc.get(i));
                        vuPingouin.put(pos.hash(),pos);
                    }
                    i++;
                }
            }
        }
        return score;
    }
}