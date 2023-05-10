package Model;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class JeuSauvegarde extends JeuAvance{
    

    //Construction du jeu depuis une sauvegarde
    JeuSauvegarde(String name){
        try {
            coupAnnule = new ArrayList<Coup>();
            coupJoue = new ArrayList<Coup>();
            listeJoueur = new ArrayList<Joueur>();

            FileReader reader = new FileReader(name);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            line = bufferedReader.readLine();
            this.nbJoueur = Integer.parseInt(line);

            line = bufferedReader.readLine();
            this.nbLignes = Integer.parseInt(line);
            
            line = bufferedReader.readLine();
            this.nbColonnes = Integer.parseInt(line);
            this.nbColonnes = nbColonnes*2+1; 

            //récup le score des joueurs
            scoreSave = new int [nbJoueur];

            for(int i=0; i<nbJoueur; i++){
                line = bufferedReader.readLine();
                scoreSave[i] = Integer.parseInt(line);
            }

            Joueur player;
            int i = 1;

            while(i <= nbJoueur){
                player = new Joueur(i,0,0);
                listeJoueur.add(player);
                i++;
            }

            //intit le nombre de pingouin a placer et le nombre de pingouin par joueur
            initNbPingouins(nbJoueur);

            terrainInitiale = new Cases[nbLignes][nbColonnes];
            terrainCourant = new Cases[nbLignes][nbColonnes];

            int l =0;
            int c =0;

            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                String[] parts = line.split(", ");
                c=0;

                for(int m =0; m < parts.length; m++){
                    if(!parts[m].equals("null")){
                        Cases cases = new Cases(false, Integer.parseInt(parts[m]), 0);
                        setCase(cases, l, c);
                        c++;
                    }
                }
                l++;
            }

            terrainInitiale = clonerTerrain(terrainCourant);
            
            //recuprer tous les coups à jouer
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                //split la ligne
                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));

                if(Boolean.parseBoolean(parts[4])){
                    placePingouin(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }else{
                    joue(cp);
                }
            }

            //recuprere les coups annulés
            while ((line = bufferedReader.readLine()) != null && (!line.equals("b"))) {

                String[] parts = line.split(" ");

                Pingouin ping = new Pingouin(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                Coup cp = new Coup(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), ping , Boolean.parseBoolean(parts[4]));
                coupAnnule.add(cp);
            }

            reader.close();
        } catch (IOException e) {
            System.out.print("Erreur : " + e);
        }
    }

}
