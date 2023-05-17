package tests.Tests;

import Model.Jeu;
import Model.Cases;

/* Programme test pour tout ce qui a trait au terrain */

public class TestsTerrains {

    public static void main(String[] args){

        // Test terrain correct
        Jeu jeu = new Jeu(4);
        assert jeu.getNbLigne() == 8: "Nombre de lignes different de 8";
        assert jeu.getNbColonne() == 15: "Nombre de colonnes different de 15";
        assert jeu.getNbCases() == 60: "Nombre de cases different de 60";
        assert jeu.case1poisson().size() == 30: "Nombre de cases a 1 poisson different de 30";

        // Test cloner terrain
        Cases[][] jeuClone = jeu.clonerTerrain(jeu.getTerrain());
        for(int i = 0; i < jeuClone.length; i++){
            for (int j = 0; j < jeuClone[0].length; j++){
                if (jeuClone[i][j] != null){
                    assert jeuClone[i][j].getNbPoissons() == jeu.getTerrain()[i][j].getNbPoissons()
                        : "Clonage non reussi";
                    assert (jeuClone[i][j].estMange() == jeu.getTerrain()[i][j].estMange())
                        : "Clonage non reussi";
                    assert (jeuClone[i][j].pingouinPresent() == jeu.getTerrain()[i][j].pingouinPresent())
                        : "Clonage non reussi";
                }
            }
        }

        // Test set et get
        Cases c = new Cases(4);
        jeu.setCase(c, 0, 0);
        assert (c == jeu.getCase(0, 0));
        jeu.setCase(c, 0, 7);
        assert jeu.getCase(0,7) == null: "Case (0,7) en dehors du terrain";
        assert jeu.getCase(0, 0).getNbPoissons() == 4: "La case (0,0) a 4 poissons";
        assert jeu.getCase(0, 0).estMange() == false: "La case (0,0) n'est pas mangee";
        assert jeu.getCase(0, 0).pingouinPresent() == 0: "Aucun pingouin n'est sur la case (0,0)";


        // Test sauvegarde
        jeu.sauvegarder("src/tests/Terrains/terrainNonFixe.txt");
        Jeu jeuSauve = new Jeu("src/tests/Terrains/terrainNonFixe.txt");

        for(int i = 0; i < jeu.getTerrain().length; i++){
            for (int j = 0; j < jeu.getTerrain()[0].length/2; j++){
                if (jeu.getCase(i,j) != null){
                    assert (jeu.getCase(i,j).getNbPoissons() == jeuSauve.getCase(i,j).getNbPoissons());
                    assert (jeu.getCase(i,j).estMange() == jeuSauve.getCase(i,j).estMange());
                    assert (jeu.getCase(i,j).pingouinPresent() == jeuSauve.getCase(i,j).pingouinPresent());
                }
            }
        }        
    }
}