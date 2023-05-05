package Vue;

import Interface.Fenetre;
import Model.Jeu;

public interface CollecteurEvenements {

    void clicSourisPlateau(int coupX, int coupY);

    void setPlateauJeu(BanquiseGraphique bq);

    void setInterface(Fenetre window);

    void setJeu(Jeu jeu);

    void switchSel();

    void switchMenu();

    void switchGameBoard();

    void toggleHelp();


}
