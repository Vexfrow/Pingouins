package Interface;

import Model.JeuAvance;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    CollecteurEvenements collecteur;

    JeuAvance jeu;

    private ArrayList<JTextArea> listScore;


    GameBoard(JeuAvance j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();

        listScore = new ArrayList<>();

        jeu = j;

        collecteur = c;
        collecteur.setPlateauJeu(this);
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

        menuGame.add(boutonPanel);

        for(int i = 0; i < jeu.getListeJoueur().size();i++){

            JTextArea jta = new JTextArea("Score Joueur "+(i+1)+" : \n\t"+ jeu.getListeJoueur().get(i).getScore());

            switch (i) {
                case 0 -> jta.setBackground(Color.red);
                case 1 -> jta.setBackground(Color.blue);
                case 2 -> jta.setBackground(Color.green);
                case 3 -> jta.setBackground(Color.yellow);
            }
            jta.setWrapStyleWord(true);
            listScore.add(jta);
            menuGame.add(jta);
        }

        menuGame.setBackground(Color.blue);
    }


    private void setGamePanel(){
        this.add(bq, BorderLayout.CENTER);
        this.add(menuGame, BorderLayout.EAST);
        this.setBackground(Color.CYAN);
    }


    public void misAJour(JeuAvance j, int etat, int hexagone){
        jeu = j;
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            System.out.println(i);
            listScore.get(i).setText("Score Joueur "+(i+1)+" : \n\t"+ jeu.getListeJoueur().get(i).getScore());
        }
        bq.misAJour(j,etat, hexagone);
    }


    public BanquiseGraphique getBq(){
        return bq;
    }


}
