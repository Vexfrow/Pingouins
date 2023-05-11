package Interface;

import Model.Jeu;
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

    Label messageTour;

    Jeu jeu;

    private JButton bPause;
    private JButton bSuggestion;

    private ArrayList<JTextArea> listScore;


    public GameBoard(Jeu j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();
        messageTour = new Label();

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
        bPause = new JButton("Pause");
        bSuggestion = new JButton("Suggestion");
        boutonPanel.add(bPause);
        bPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.togglePause(true);
                //toggleButton();
            }
        });
        boutonPanel.add(bSuggestion);

        menuGame.add(boutonPanel);

        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());

        menuGame.add(messageTour);


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
                misAJour(jeu);
            }
        });

        JButton bRefaire = new JButton("Refaire");

        bRefaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.refaire();
                misAJour(jeu);
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


    public void misAJour(Jeu j){
        jeu = j;
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            listScore.get(i).setText("Joueur "+(i+1)+" : \nNombre de poissons : "+ jeu.getListeJoueur().get(i).getScore() + "\nNombre de cases : " +jeu.getListeJoueur().get(i).getNbCasesMange());
        }
        bq.misAJour(jeu);
    }


    public BanquiseGraphique getBq(){
        return bq;
    }

    public void toggleButton(){
        bSuggestion.setEnabled(!bSuggestion.isEnabled());
        bPause.setEnabled(!bPause.isEnabled());
    }



}
