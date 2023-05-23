package Interface;

import java.io.*;
import Joueur.*;
import Model.*;

import java.util.ArrayList;

public class Settings {
    File quickPlay;
    public int nbJoueur;
    public int[] typeJoueur;

    public Settings(){
        quickPlay = new File(GameConstants.DOSSIER_SETTINGS + "quickPlay");

    }

    public void writeSettings(int nbJ, int[] types){
        try {
            File f = new File( GameConstants.DOSSIER_SETTINGS);
            f.mkdir();

            FileWriter fw = new FileWriter(quickPlay);
            fw.write(nbJ+"\n");
            for(int i =0; i < nbJ; i++){
                fw.write(types[i] + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void readSettings(){
        try {
            FileReader fr = new FileReader(quickPlay);
            BufferedReader br = new BufferedReader(fr);
            nbJoueur = Integer.parseInt(br.readLine());
            typeJoueur = new int[nbJoueur];
            for(int i = 0; i < nbJoueur; i++){
                typeJoueur[i] = Integer.parseInt(br.readLine());
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<IAJoueur> getTypes(Jeu j){
        readSettings();
        ArrayList<IAJoueur> ar = new ArrayList<IAJoueur>();
        for(int i =0; i < nbJoueur; i++){
            switch(typeJoueur[i]){
                case 0:
                    ar.add(null);
                    break;
                case 1:
                    ar.add(new IAFacile(j));
                    break;
                case 2:
                    ar.add(new IAMoyen(j));
                    break;
                case 3:
                    ar.add(new IADifficile(j));
                    break;
                case 4:
                    ar.add(new IAExpert(j));
                    break;

            }
        }
        return ar;
    }


    public ArrayList<Joueur> getJoueur(){
        readSettings();
        ArrayList<Joueur> ar = new ArrayList<Joueur>();
        for(int i =0; i < nbJoueur; i++){
            ar.add(new Joueur(i+1, 0, 0, typeJoueur[i]));
        }
        return ar;
    }
}
