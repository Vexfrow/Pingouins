package Joueur;


import java.util.Random;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Cases;
import Model.Pingouin;
import java.util.ArrayList;

public class IATroisPoissons extends IAJoueur{

    public IATroisPoissons(Jeu j){
        super(j);
    }

    public void setName(String s){
        this.name = s;
    }

    @Override
    public Position elaborePlacement(){
        Random r = new Random();
        Cases[][] terrainCourant = this.j.clonerTerrain(this.j.getTerrain());
        ArrayList<Position> posPossible = new ArrayList<Position>();
        Cases caseCourant;
        Position posCourant;

        int nbc;
        int l = 0;
        int c = 0;
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

        return posPossible.get(r.nextInt(posPossible.size()));
    }   



    public Coup elaboreCoup(){
        Random r = new Random();
        ArrayList<Coup> coupPossible = new ArrayList<Coup>();
        int joueurCourant = this.j.getJoueurCourant()-1;

        ArrayList<Pingouin> listePingouin = this.j.getListeJoueur().get(joueurCourant).getListePingouin();
        ArrayList<Position> listePos = new ArrayList<Position>();

        Coup cp;
        Cases cases;

        int i = 0;
        int k = 0;
        int nbpoisson=3;

        while(nbpoisson > 0 && (coupPossible.size()==0)){
            i = 0;
            while( i < listePingouin.size()){
                listePos = this.j.getCaseAccessible(listePingouin.get(i).getLigne(),listePingouin.get(i).getColonne());
                k=0;
                while(k < listePos.size()){
                    cases = j.getCase(listePos.get(k).x , listePos.get(k).y);
                    if(cases.getNbPoissons() == nbpoisson){
                        cp = new Coup(listePos.get(k).x , listePos.get(k).y , listePingouin.get(i).cloner(), false) ;
                        coupPossible.add(cp);
                    }
                    k++;
                }
                i++;
            }
            nbpoisson--;
        }
        if(coupPossible.size()==0){
            return null;
        }else{
            return coupPossible.get(r.nextInt(coupPossible.size()));
        }
    }

}