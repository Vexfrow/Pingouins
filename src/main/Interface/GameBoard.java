package Interface;

import Model.Jeu;
import Model.Pingouin;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    CollecteurEvenements collecteur;


    GameBoard(Jeu j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();

        collecteur = c;
        collecteur.setPlateauJeu(bq);
        collecteur.setJeu(j);

        this.setLayout(new BorderLayout());

        setMenuGame();
        setGamePanel();
    }



    private void setMenuGame(){
        menuGame.setLayout(new BoxLayout(menuGame, BoxLayout.Y_AXIS));

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        JButton bPause = new JButton("Pause");
        JButton bSuggestion = new JButton("Suggestion");

        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);


        JTextArea taScore1 = new JTextArea("Score 1 : ");
        JTextArea taScore2 = new JTextArea("Score 2 : ");
        JTextArea taScore3 = new JTextArea("Score 3 : ");
        JTextArea taScore4 = new JTextArea("Score 4 : ");

        taScore1.setWrapStyleWord(true);
        taScore1.setBackground(Color.red);

        taScore2.setWrapStyleWord(true);
        taScore2.setBackground(Color.blue);

        taScore3.setWrapStyleWord(true);
        taScore3.setBackground(Color.green);

        taScore4.setWrapStyleWord(true);
        taScore4.setBackground(Color.yellow);

        menuGame.add(boutonPanel);

        menuGame.add(taScore1);
        menuGame.add(taScore2);
        menuGame.add(taScore3);
        menuGame.add(taScore4);

        menuGame.setBackground(Color.blue);
    }


    private void setGamePanel(){
        this.add(bq, BorderLayout.CENTER);
        this.add(menuGame, BorderLayout.EAST);
        this.setBackground(Color.CYAN);
    }


    public void majScore(int joueur, int addScore){

    }


}
