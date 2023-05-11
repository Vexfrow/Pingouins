package Vue;

import Interface.Fenetre;
import Interface.GameBoard;
import Model.Jeu;
import Model.Joueur;

import java.util.ArrayList;

public interface CollecteurEvenements {

    void clicSourisPlateau(int coupX, int coupY);

    void setPlateauJeu(GameBoard bq);

    void setInterface(Fenetre window);

    void setJeu(Jeu jeu);

    void switchSel();

    void switchMenu();

    void switchGameBoard();

    void toggleHelp(boolean b);

    void togglePause(boolean b);

    void toggleSave();

    int getEtatBackPane();

    void newGame(int j);

    void newGame(ArrayList<Joueur> j);

    void startGame();

    void save(String s);



}
