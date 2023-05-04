package Joueur;

import java.util.Random;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Model.Joueur;
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
    


    @Override
    public Coup elaboreCoup(){
        Random r = new Random();
        ArrayList<Coup> coupPossible = new ArrayList<Coup>();
        int joueurCourant = this.j.quelJoueur()-1;
        ArrayList<Joueur> listeJoueur =this.j.getListeJoueur();
        ArrayList<Pingouin> listePingouin = listeJoueur.get(joueurCourant).getListePingouin();
        ArrayList<Position> listePos;
        Coup cp;
        int i = 0;
        int j = 0;

        while( i < listePingouin.size()){
            listePos = null;
            listePos = this.j.getCaseAccessible(listePingouin.get(i));
            j=0;
            while(j < listePos.size()){
                cp = new Coup(listePos.get(j).x , listePos.get(j).y , listePingouin.get(i), false) ;
                coupPossible.add(cp);
                j++;
            }
            i++;

        }
        System.out.println(coupPossible.size());
        if(coupPossible.size()==0){
            return null;
        }else{
            return coupPossible.get(r.nextInt(coupPossible.size()));
        }
    }

}