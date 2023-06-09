package Joueur;

import java.util.Random;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;




public class IAAleatoire extends IAJoueur{
    Random r;


    public IAAleatoire(Jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        Random r = new Random();
        Cases[][] terrainCourant = this.j.getTerrain();
        ArrayList<Position> posPossible = new ArrayList<Position>();
        Cases caseCourant;
        Position posCourant;

        char nbc;
        char l = 0;
        char c = 0;
        Position pos = null;
        while( l < terrainCourant.length){
            c = 0;
            if( l%2 == 1){// si ligne impaire
                nbc = 8;
            }else{
                nbc = 7;
            }

            //boucle sur toutes les colonnes
            while( c < (nbc)){
                caseCourant = j.getCase(l,c);
                if(caseCourant.getNbPoissons()==1 && caseCourant.pingouinPresent() == 0){
                    posCourant = new Position(l,c);
                    posPossible.add(posCourant);
                }
                c++;
            }
            l++;
        }

        pos = posPossible.get(r.nextInt(posPossible.size()));

        return pos;
    }
    


    @Override
    public Coup elaboreCoup(){
        Random r = new Random();
        ArrayList<Coup> coupPossible = new ArrayList<Coup>();
        int joueurCourant = this.j.getJoueurCourant()-1;
        ArrayList<Pingouin> listePingouin = this.j.getListeJoueur().get(joueurCourant).getListePingouin();
        ArrayList<Position> listePos;
        Coup cp;
        int i = 0;
        int j = 0;

        Coup coupChoix = null;

        while( i < listePingouin.size()){
            listePos = null;
            listePos = this.j.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
            j=0;
            while(j < listePos.size()){
                cp = new Coup(listePos.get(j).x , listePos.get(j).y , listePingouin.get(i).cloner(), false) ;
                coupPossible.add(cp);
                j++;
            }
            i++;

        }


        if(coupPossible.size()==0){
            coupChoix = null;
        }else{
            coupChoix = coupPossible.get(r.nextInt(coupPossible.size()));
        }
  
        return coupChoix;
    }

}