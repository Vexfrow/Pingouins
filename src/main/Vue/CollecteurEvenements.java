package Vue;

import Interface.Fenetre;
import Interface.GameBoard.GameBoard;
import Model.Jeu;
import Model.Joueur;
import Joueur.IAJoueur;
import java.util.ArrayList;

public interface CollecteurEvenements {

    void clicSourisPlateau(int coupX, int coupY);

    void setPlateauJeu(GameBoard bq);

    void setInterface(Fenetre window);

    void setJeu(Jeu jeu, ArrayList<IAJoueur> ar);

    void switchSel();

    void switchMenu();

    void switchGameBoard();

    void toggleHelp(boolean b);

    void togglePause(boolean b);

    void toggleSave();

    void toggelCharge(boolean change);

    void toggleVictoire(boolean b);

    int getEtatBackPane();

    void newGame(Jeu j, ArrayList<IAJoueur> liste, ArrayList<Joueur> arJ);

    void startGame();

    void save(String s);

    Jeu getJeu();

}
