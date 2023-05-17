package Joueur;

import java.util.*;
import java.lang.Math;
import Model.*;

public class Heuristique{
    public static Hashtable<Integer, Position> vuPos;

    //Aléatoire
    public static int Hrandom(Configuration config){
        Random r = new Random();
        int value = r.nextInt(50);
        return value;
    }


    public static double HcoupPossibleAdv(Configuration config, int joueuria){
        int nbjoueur = config.jeu.getNbJoueur();
        ArrayList<Pingouin> listePingouinTotal = new ArrayList<Pingouin>();
        ArrayList<Pingouin> listePingouin = new ArrayList<Pingouin>();
        int k = 0;
        int i = 0;
        while(k < nbjoueur){
            i=0;
            if(k != joueuria-1){
                listePingouin = config.jeu.getListeJoueur().get(k).getListePingouin();
                while(i<listePingouin.size()){
                    listePingouinTotal.add(listePingouin.get(i));
                    i++;
                }
            }
            k++;
        }
        ArrayList<Position> listePos;
        ArrayList<Position> listePosTotal = new ArrayList<Position>();
        i = 0;
        int j = 0;
        while( i < listePingouinTotal.size()){
            listePos = null;
            listePos = config.jeu.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                listePosTotal.add(listePos.get(j));
                j++;
            }
            i++;
        }
        return ((double)listePosTotal.size()/40.0);
    }

    // Ce truc marche moin bien que l'aléatoire
    //Nombre de coup possible
    public static double HcoupPossible(Configuration config, int joueuria){
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
        return ((double)listePosTotal.size()/20.0);
        }

    public static double HnbCaseAccessible(Configuration config, int joueuria){
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        ArrayList<Pingouin> listePingouin = joueur1.getListePingouin();
        ArrayList<Position> listePos;

        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;  
        Joueur joueur2=config.jeu.getListeJoueur().get(joueurCourant-1);
        ArrayList<Pingouin> listePingouin2 = config.jeu.getListeJoueur().get(joueurCourant-1).getListePingouin();


        Hashtable<Integer, Position> vuPos= new Hashtable<Integer, Position>();
        double score=0;
        int j = 0;
        int i = 0;
        while( i < listePingouin.size()){
            listePos = config.jeu.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                if(!vuPos.containsKey(listePos.get(j))){
                    vuPos.put(listePos.get(j).hash(),listePos.get(j));
                    score++;
                }
                j++;
            }
            i++;
        }
        return (score/15);
    }

    public static double HnbCaseAccessibleAdv(Configuration config, int joueuria){
        int nbjoueur = config.jeu.getNbJoueur();
        ArrayList<Pingouin> listePingouinTotal = new ArrayList<Pingouin>();
        ArrayList<Pingouin> listePingouin = new ArrayList<Pingouin>();
        int k = 0;
        int i = 0;
        while(k < nbjoueur){
            i=0;
            if(k != joueuria-1){
                listePingouin = config.jeu.getListeJoueur().get(k).getListePingouin();
                while(i<listePingouin.size()){
                    listePingouinTotal.add(listePingouin.get(i));
                    i++;
                }
            }
            k++;
        }
        Hashtable<Integer, Position> vuPos= new Hashtable<Integer, Position>();
        ArrayList<Position> listePos;
        double score=0;
        int j = 0;
        i = 0;
        while( i < listePingouinTotal.size()){
            listePos = config.jeu.getCaseAccessible(listePingouinTotal.get(i).getLigne(),listePingouinTotal.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                if(!vuPos.containsKey(listePos.get(j).hash())){
                    vuPos.put(listePos.get(j).hash(),listePos.get(j));
                    score++;
                }
                j++;
            }
            i++;
        }
        return (score/20);
    }


    // renvoie la différence de score (2 joueurs SEULEMENT)
    public static double Hdiffscore(Configuration config, int joueuria){
        Joueur joueur1=config.jeu.getListeJoueur().get(joueuria-1);
        int joueurCourant = (joueuria % config.jeu.getNbJoueur()) + 1;       
        Joueur joueur2= config.jeu.getListeJoueur().get(joueurCourant-1);
        int scores = joueur1.getScore()-joueur2.getScore()+ (int)Hilot(config,joueuria);
        if(joueur1.getScore()>50){
            return 9999999;
        }else if(joueur2.getScore()>50){
            return -999999;
        }
        return scores;
    }


    public static double montecarlo(Configuration config, int joueuria,int nbPartie){
        int nbJoueur = config.jeu.getNbJoueur();
        int tab[] = new int[nbJoueur];
        int i = 0;
        IAJoueur ia1;
        Coup cp;
        Position pos;
        int k =0;


        while ( i < nbPartie){
            Jeu j = config.jeu.cloner();
            IAJoueur ia = new IAAleatoire(j);

            while(!j.pingouinTousPlace()){
                pos = ia.elaborePlacement();
                j.placePingouin(pos.x, pos.y);

            }
            while(!j.jeuTermine()){
                cp = ia.elaboreCoup();
                j.joue(cp);
                }
            i++;
            tab[j.gagnant()-1]++;
        }
        return (double)tab[joueuria-1];
    }


    public static double Hilot(Configuration config, int joueuria){
        vuPos= new Hashtable<Integer, Position>();
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
            if((tempo =estIlot(ping, config.jeu, joueur2, true) )!= 0){
                score+=tempo;
            }
            i++;
        }
        i=0;
        while(i < listePingouin2.size()){
            ping = listePingouin2.get(i);
            if((tempo =estIlot(ping, config.jeu, joueur1, true) )!= 0){
                score-=tempo;
            }
            i++;
        }
        return (double)score;
    }



    public static int estIlot(Pingouin ping, Jeu jeu, Joueur joueur, boolean bool){
        if(!bool){
            vuPos = new Hashtable<Integer, Position>();
        }
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
                if(!vuPos.containsKey(pos.hash())){
                    score+=jeu.getCase(pos.x,pos.y).getNbPoissons();
                    vuPos.put(pos.hash(),pos);
                }

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