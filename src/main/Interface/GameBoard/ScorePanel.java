package Interface.GameBoard;

import Model.Jeu;
import Model.Joueur;

import javax.swing.*;

public class ScorePanel extends JPanel {

    Jeu jeu;


    public ScorePanel(Jeu j){
        jeu = j;
    }

    public ScorePanel(){
    }

    public void misAJour(Jeu j, Joueur je) {
        jeu = j;
    }

}
