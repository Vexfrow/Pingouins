package Vue;

import Interface.Fenetre;
import Interface.GameBoard;
import Model.Jeu;
import Model.JeuAvance;

public interface CollecteurEvenements {

    void clicSourisPlateau(int coupX, int coupY);

    void setPlateauJeu(GameBoard bq);

    void setInterface(Fenetre window);

    void setJeu(JeuAvance jeu);

    void switchSel();

    void switchMenu();

    void switchGameBoard();

    void toggleHelp(boolean b);

    void togglePause(boolean b);

    int getEtatBackPane();

    void newGame(int j);


}
