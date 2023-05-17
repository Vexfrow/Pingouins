package Interface;

import Model.Jeu;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    boolean initial;
    CollecteurEvenements collecteur;

    Label messageTour;

    public Jeu jeu;

    private JButton bPause, bRefaire, bSuggestion, bAnnuler, bHistorique;
    BufferedImage annuler, refaire, pause, suggestion;

    private final ArrayList<ScorePanel> listeScorePanel;

    public GameBoard(Jeu j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();
        messageTour = new Label();

        listeScorePanel = new ArrayList<>();

        initial = true;

        jeu = j;

        collecteur = c;
        collecteur.setPlateauJeu(this);

        suggestion = chargeImage("boutonAstuce");
        pause = chargeImage("boutonPause");
        annuler = chargeImage("boutonAnnuler");
        refaire = chargeImage("boutonRefaire");

        bPause = new JButton(new ImageIcon(pause));
        bRefaire = new JButton(new ImageIcon(refaire));
        bSuggestion = new JButton(new ImageIcon(suggestion));
        bAnnuler = new JButton(new ImageIcon(annuler));
        bHistorique= new JButton("Historique");


        setMenuGame();
        setGamePanel();
    }



    private BufferedImage chargeImage(String nom){
        try {
            InputStream in = new FileInputStream("resources/assets/jeu/menu/" + nom + ".png");
            return ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("Fichier \"" + nom + "\" introuvable");
        }
        return null;
    }


    private void setMenuGame(){

//        if(jeu.getEtat() == Jeu.ETAT_INITIAL){
//            System.out.println("TEst");
//            setMenuGameInitial();
//        }else{
            setMenuGameJeu();
//        }
    }

    private void setMenuGameJeu() {

        menuGame.setLayout(new GridBagLayout());
        menuGame.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        //----------------Boutons Pause et suggestion-------------
        bPause.setBorderPainted(false);
        bPause.setContentAreaFilled(false);
        bPause.addActionListener(e -> collecteur.togglePause(true));

        bSuggestion.setBorderPainted(false);
        bSuggestion.setContentAreaFilled(false);


        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(boutonPanel, c);


        //----------------Message-------------
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
        messageTour.setFont(new Font("Serif", Font.PLAIN, 20));

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(messageTour, c);


        //----------Score--------------
        setScore();

        //----------Historique-------------
        c.gridx = 0;
        c.weighty = 15;
        c.gridy = 3+jeu.getListeJoueur().size();
        c.fill = GridBagConstraints.BOTH;
        menuGame.add(bHistorique,c);


        //----------Boutons annuler et refaire --------------
        bAnnuler.setBorderPainted(false);
        bAnnuler.setContentAreaFilled(false);

        bAnnuler.addActionListener(e -> {
            jeu.annule();
            misAJour(jeu);
        });

        bRefaire.setBorderPainted(false);
        bRefaire.setContentAreaFilled(false);

        bRefaire.addActionListener(e -> {
            jeu.refaire();
            misAJour(jeu);
        });

        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bRefaire);


        c.gridx = 0;
        c.weighty = 10;
        c.gridy = 4+jeu.getListeJoueur().size();
        menuGame.add(boutonPanel2, c);

        misAJour();
    }

//    private void setMenuGameInitial() {
//
//        menuGame.setLayout(new GridBagLayout());
//        menuGame.setBackground(Color.ORANGE);
//        GridBagConstraints c = new GridBagConstraints();
//
//
//        bPause.setBorderPainted(false);
//        bPause.setContentAreaFilled(false);
//        bPause.addActionListener(e -> collecteur.togglePause(true));
//
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weighty = 10;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.PAGE_START;
//        menuGame.add(bPause, c);
//
//
//        //----------------Message-------------
//        messageTour = new Label("C'est au tour du joueur " + jeu.getJoueurCourant() + " de placer un pingouin");
//
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weighty = 10;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.PAGE_START;
//        menuGame.add(messageTour, c);
//
//
//        //----------Pingouin--------------
//        for(int i = 0; i < jeu.getListeJoueur().size(); i++){
//            JPanel panelPingouin = new JPanel();
//            panelPingouin.setLayout(new GridBagLayout());
//
//
//
//        }
//
//        //----------Historique-------------
//        c.gridx = 0;
//        c.weighty = 15;
//        c.gridy = 3+jeu.getListeJoueur().size();
//        c.fill = GridBagConstraints.BOTH;
//        menuGame.add(bHistorique,c);
//
//
//        //----------Boutons annuler et refaire --------------
//        bAnnuler.setBorderPainted(false);
//        bAnnuler.setContentAreaFilled(false);
//
//        bAnnuler.addActionListener(e -> {
//            jeu.annule();
//            misAJour(jeu);
//        });
//
//        bRefaire.setBorderPainted(false);
//        bRefaire.setContentAreaFilled(false);
//
//        bRefaire.addActionListener(e -> {
//            jeu.refaire();
//            misAJour(jeu);
//        });
//
//        JPanel boutonPanel2 = new JPanel();
//        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));
//
//        boutonPanel2.add(bAnnuler);
//        boutonPanel2.add(bRefaire);
//
//
//        c.gridx = 0;
//        c.weighty = 10;
//        c.gridy = 4+jeu.getListeJoueur().size();
//        menuGame.add(boutonPanel2, c);
//
//        misAJour();
//
//
//    }

    private void setScore(){

        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            ScorePanel s = new ScorePanel(jeu,jeu.getListeJoueur().get(i));
            listeScorePanel.add(s);

            c.gridx = 0;
            c.gridy = 2+i;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weighty = 75;
            menuGame.add(s, c);
        }

    }


    private void setGamePanel(){
        this.setLayout(new BorderLayout());

        this.add(bq, BorderLayout.CENTER);
        this.add(menuGame, BorderLayout.EAST);
        this.setBackground(Color.CYAN);
    }


    public void misAJour(Jeu j){
        jeu = j;
        misAJour();
    }


    private void misAJour(){
//        if(initial && jeu.getEtat() == Jeu.ETAT_SELECTIONP){
//            initial = false;
//            menuGame.removeAll();
//            setMenuGame();
//        }


        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            if(listeScorePanel.size() < jeu.getListeJoueur().size()){
                ScorePanel s = new ScorePanel(jeu, jeu.getListeJoueur().get(i));
                listeScorePanel.add(s);
            }

            listeScorePanel.get(i).misAJour(jeu, jeu.getListeJoueur().get(i));
        }
        bq.misAJour(jeu);

        if(jeu.getEtat() == Jeu.ETAT_FINAL) {
            collecteur.toggleVictoire(true);
        }
    }


    public BanquiseGraphique getBq(){
        return bq;
    }

    public void toggleButton(){
        bPause.setEnabled(!bPause.isEnabled());
        bSuggestion.setEnabled(!bSuggestion.isEnabled());
        bRefaire.setEnabled(!bRefaire.isEnabled());
        bAnnuler.setEnabled(!bAnnuler.isEnabled());
        bHistorique.setEnabled(!bHistorique.isEnabled());
    }

    public void activateButton(){
        bPause.setEnabled(true);
        bSuggestion.setEnabled(true);
        bRefaire.setEnabled(true);
        bAnnuler.setEnabled(true);
        bHistorique.setEnabled(true);
    }



}
