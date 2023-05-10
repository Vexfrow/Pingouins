package Interface;

import Model.JeuAvance;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        bPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                jeu.annule();
//                misAJour();
            }
        });
        JButton bSuggestion = new JButton("Suggestion");

        bSuggestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                jeu.refaire();
//                misAJour();
            }
        });

        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);

        menuGame.add(boutonPanel);

        for(int i = 0; i < jeu.getListeJoueur().size();i++){

            JTextArea jta = new JTextArea("Joueur "+(i+1)+" : \nNombre de poissons : "+ jeu.getListeJoueur().get(i).getScore() + "\nNombre de cases : " +jeu.getListeJoueur().get(i).getNbCasesMange());
            jta.setEditable(false);
            if(i == 3)
                jta.setForeground(Color.BLACK);
            else
                jta.setForeground(Color.WHITE);
            switch (i) {
                case 0 -> jta.setBackground(new Color(0xEC1C24));
                case 1 -> jta.setBackground(new Color(0x3F48CC));
                case 2 -> jta.setBackground(new Color(0x0ED145));
                case 3 -> jta.setBackground(new Color(0xFFF200));
            }
            jta.setWrapStyleWord(true);
            listScore.add(jta);
            menuGame.add(jta);
        }



        JButton bAnnuler = new JButton("Annuler");

        bAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.annule();
                misAJour();
            }
        });

        JButton bRefaire = new JButton("Refaire");

        bRefaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.refaire();
                misAJour();
            }
        });
        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bRefaire);

        menuGame.add(boutonPanel2);

    }


    private void setGamePanel(){
        this.add(bq, BorderLayout.CENTER);
        this.add(menuGame, BorderLayout.EAST);
        this.setBackground(Color.CYAN);
    }


    public void misAJour(JeuAvance j){
        jeu = j;

        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            listScore.get(i).setText("Joueur "+(i+1)+" : \nNombre de poissons : "+ jeu.getListeJoueur().get(i).getScore() + "\nNombre de cases : " +jeu.getListeJoueur().get(i).getNbCasesMange());
        }


        String message;
        ToastComponent tc = null;
        if(j.getEtat() == JeuAvance.ETAT_SELECTIONP) {
            message = "C'est au tour du joueur " + j.getJoueurCourant() + " de jouer";
            tc = new ToastComponent(message, getWidth() / 2 - 20, 0);
        }else if(j.getEtat() == JeuAvance.ETAT_FINAL){
            message = "Partie terminé";
            tc = new ToastComponent(message, getWidth()/2-20, 0);
        }else if(j.getEtat() == JeuAvance.ETAT_PLACEMENTP){
            message = "C'est au tour du joueur " + j.getJoueurCourant() + " de placer un pingouin";
            tc = new ToastComponent(message, getWidth()/2-20, 0);
        }

        if(tc != null)
            tc.showtoast();


        bq.misAJour(jeu);
    }

    public void misAJour(){
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            listScore.get(i).setText("Joueur "+(i+1)+" : \nNombre de poissons : "+ jeu.getListeJoueur().get(i).getScore() + "\nNombre de cases : " +jeu.getListeJoueur().get(i).getNbCasesMange());
        }
        bq.misAJour(jeu);
    }


    public BanquiseGraphique getBq(){
        return bq;
    }


}
