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
        return scores;
    }


    public static int montecarlo(Configuration config, int joueuria){
        int i = 0;
        int winj1 =0;
        int winj2 =0;
        IAJoueur ia1;
        IAJoueur ia2;
        Coup cp;
        Position pos;


        while ( i < 5){
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
        if(joueuria == 1){
            return winj1;  
        }else{
            return winj2;
        }

    }


    public static int Hilot(Configuration config, int joueuria){
        Hashtable<Integer, Position> vu= new Hashtable<Integer, Position>();
        Position pos;
        Pingouin ping;
        int score =0;;

        ArrayList<Position> listePos = new ArrayList<Position>();
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        ArrayList<Pingouin> listePingouin1 = joueur1.getListePingouin();

        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;  
        Joueur joueur2=config.jeu.getListeJoueur().get(joueurCourant-1);
        ArrayList<Pingouin> listePingouin2 = config.jeu.getListeJoueur().get(joueurCourant-1).getListePingouin();

        int i = 0;
        int tempo;
        while(i < listePingouin1.size()){
            ping = listePingouin1.get(i);
            //System.out.println(" avec le pingouin"+ ping);
            if((tempo =estIlot(ping, config.jeu, joueur2) )!= 0){
                //System.out.println("abc "+tempo+" avec le pingouin"+ ping+"\n\n");
                score+=tempo;
                //System.out.println("def score vaut "+ score);
            }
            i++;
        }
        i=0;
        while(i < listePingouin2.size()){
            ping = listePingouin2.get(i);
            //System.out.println(" avec le pingouin"+ ping);
            if((tempo =estIlot(ping, config.jeu, joueur1) )!= 0){
                //System.out.println("tempo vaut "+ tempo +" avec le pingouin"+ ping+"\n\n");
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
        //System.out.println("\ncaseAccTotal vaut" + caseAccTotal + "pour le joueur" + ping);
        Position posC;
        Stack<Position> Avisit = new Stack<Position>();
        caseAcc=jeu.getCaseAccessible(ping.getLigne(),ping.getColonne());
        //System.out.println("Les cases accessible = " + caseAcc);
        i =0;
        while(i< caseAcc.size()){
            Avisit.push(caseAcc.get(i));
            vuPingouin.put(caseAcc.get(i).hash(),caseAcc.get(i));
            i++;
        }
        i =0;
        //System.out.println("Debut de boucle file vide ? " + Avisit.empty());
        while(!Avisit.empty()){
            pos = Avisit.pop();
            if(vuJoueur.containsKey(pos.hash())){
                //System.out.println("ON RENVOIE 0 :" + pos);
                return 0;
            }else{
                score+=jeu.getCase(pos.x,pos.y).getNbPoissons();
                //System.out.println("on rajoute "+ jeu.getCase(pos.x,pos.y).getNbPoissons()+" avec pos"+ pos +"calcul du tempo ping:" +ping);
                caseAcc=jeu.getCaseAccessible(pos.x,pos.y);
                i = 0;
                while(i< caseAcc.size()){
                    posC = caseAcc.get(i);
                    if(!vuPingouin.containsKey(posC.hash())){
                        //System.out.println("Case ajouté : " + posC);
                        Avisit.push(caseAcc.get(i));
                        vuPingouin.put(posC.hash(),posC);
                    }
                    i++;
                }
            }
        }
        return score;
    }
}