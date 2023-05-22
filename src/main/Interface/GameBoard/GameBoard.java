package Interface.GameBoard;

import Interface.GameConstants;
import Joueur.IAExpert;
import Model.Coup;
import Model.Jeu;
import Model.Position;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    int etat;
    CollecteurEvenements collecteur;

    Label messageTour;

    public Jeu jeu;

    private final JButton bPause;
    private final JButton bRefaire;
    private final JButton bSuggestion;
    private final JButton bAnnuler;
    private final JButton bOk;
    private final JButton bStart;

    private final JButton bRegenereP;

    private final ArrayList<ScorePanel> listeScorePanel;

    public GameBoard(Jeu j, CollecteurEvenements c){

        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();
        messageTour = new Label();

        listeScorePanel = new ArrayList<>();

        jeu = j;
        etat = jeu.getEtat();

        collecteur = c;
        collecteur.setPlateauJeu(this);

        bPause = new JButton(new ImageIcon(GameConstants.pause));
        bRefaire = new JButton(new ImageIcon(GameConstants.refaire));
        bSuggestion = new JButton(new ImageIcon(GameConstants.suggestion));
        bAnnuler = new JButton(new ImageIcon(GameConstants.annuler));
        bOk= new JButton(new ImageIcon(GameConstants.boutonOk));
        bStart = new JButton("Commencer Partie");
        bRegenereP= new JButton(new ImageIcon(GameConstants.regenerePlateau));

        setMenuGame();
        setGamePanel();
    }


    private void setPause(){
        bPause.setBorderPainted(false);
        bPause.setContentAreaFilled(false);
        bPause.addActionListener(e -> collecteur.togglePause(true));
    }

    private void setOk(){
        bOk.setBorderPainted(false);
        bOk.setContentAreaFilled(false);
        bOk.addActionListener(e -> collecteur.togglePause(true));
    }

    private void setSuggestion(){
        bSuggestion.setBorderPainted(false);
        bSuggestion.setContentAreaFilled(false);
        bSuggestion.addActionListener(e -> {
            if (jeu.getEtat() == Jeu.ETAT_PLACEMENTP){
                bq.misAJourSuggestionPlacement(jeu,suggestionPlacement());
            }else {
                bq.misAJourSuggestionCoup(jeu, suggestionCoup());
            }
        });
    }

    private void setAnnuler(){
        bAnnuler.setBorderPainted(false);
        bAnnuler.setContentAreaFilled(false);

        bAnnuler.addActionListener(e -> {
            jeu.annule();
            misAJour(jeu);
        });
    }


    private void setRefaire(){
        bRefaire.setBorderPainted(false);
        bRefaire.setContentAreaFilled(false);

        bRefaire.addActionListener(e -> {
            jeu.refaire();
            misAJour(jeu);
        });
    }


    private void setRegenereP(){
        bRegenereP.setBorderPainted(false);
        bRegenereP.setContentAreaFilled(false);

        bRegenereP.addActionListener(e -> {
            jeu.terrainAleatoire(8, 8);
            bq.misAJour(jeu);
        });
    }


    private void setCommencerPartie(){
        bStart.setBorderPainted(false);
        bStart.setContentAreaFilled(false);

        bStart.addActionListener(e -> {
            collecteur.startGame();
            misAJour();
        });
    }


    private void setMenuGame(){
        if(jeu.getEtat() == Jeu.ETAT_INITIAL){
            setMenuGameInitial();
        }else if(jeu.getEtat() == Jeu.ETAT_PLACEMENTP){
            setMenuGameJeuPlacement();
        }else{
            setMenuGameJeuDeplacement();
        }
    }


    private void setMenuGameJeuPlacement() {

        menuGame.setLayout(new GridBagLayout());
        menuGame.setBorder(new LineBorder(Color.BLACK));
        menuGame.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        //----------------Boutons Pause et suggestion-------------
        setPause();
        setSuggestion();

        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(boutonPanel, c);


        //----------------Message-------------
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant() + " de placer un pingouin");
        messageTour.setFont(new Font("Serif", Font.PLAIN, 20));

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(messageTour, c);


        //----------Score--------------
        setScorePlacement();



        //----------Boutons annuler et refaire --------------
        setAnnuler();
        setOk();
        setRefaire();

        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bOk);
        boutonPanel2.add(bRefaire);


        c.gridx = 0;
        c.weighty = 10;
        c.gridy = 2+jeu.getListeJoueur().size();
        menuGame.add(boutonPanel2, c);

        misAJour();
    }


    private void setMenuGameJeuDeplacement(){
        menuGame.setLayout(new GridBagLayout());
        menuGame.setBackground(Color.ORANGE);
        menuGame.setBorder(new LineBorder(Color.BLACK));
        GridBagConstraints c = new GridBagConstraints();

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        //----------------Boutons Pause et suggestion-------------
        setPause();
        setSuggestion();

        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(boutonPanel, c);


        //----------------Message-------------
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant() + " de deplacer un pingouin");
        messageTour.setFont(new Font("Serif", Font.PLAIN, 20));

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(messageTour, c);


        //----------Score--------------
        setScoreDeplacement();



        //----------Boutons annuler et refaire --------------
        setAnnuler();
        setOk();
        setRefaire();

        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bOk);
        boutonPanel2.add(bRefaire);


        c.gridx = 0;
        c.weighty = 10;
        c.gridy = 2+jeu.getListeJoueur().size();
        menuGame.add(boutonPanel2, c);

        misAJour();
    }



    private void setMenuGameInitial() {

        menuGame.setLayout(new GridBagLayout());
        menuGame.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();


        setPause();

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(bPause, c);



        //----------Commencer partie-------------
        setCommencerPartie();
        c.gridx = 0;
        c.weighty = 15;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        menuGame.add(bStart,c);


        //----------Regenere Plateau-------------
        setRegenereP();
        c.gridx = 0;
        c.weighty = 15;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        menuGame.add(bRegenereP,c);

        menuGame.setBorder(new LineBorder(Color.BLACK));


        misAJour();

    }




    private void setScoreDeplacement(){

        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            ScoreDeplacementPanel s = new ScoreDeplacementPanel(jeu,jeu.getListeJoueur().get(i));
            s.setBorder(new LineBorder(Color.BLACK));
            listeScorePanel.add(s);

            c.gridx = 0;
            c.gridy = 2+i;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weighty = 75;
            menuGame.add(s, c);
        }
    }


    private void setScorePlacement(){

        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            ScorePlacementPanel s = new ScorePlacementPanel(jeu,jeu.getListeJoueur().get(i));
            s.setBorder(new LineBorder(Color.BLACK));
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
        if((etat == Jeu.ETAT_INITIAL && jeu.getEtat() == Jeu.ETAT_PLACEMENTP) || (etat == Jeu.ETAT_PLACEMENTP && jeu.getEtat() == Jeu.ETAT_SELECTIONP) || (etat == Jeu.ETAT_SELECTIONP && jeu.getEtat() == Jeu.ETAT_PLACEMENTP)){
            etat = jeu.getEtat();
            menuGame.removeAll();
            listeScorePanel.clear();
            setMenuGame();
            revalidate();
        }

        if(etat != Jeu.ETAT_INITIAL){
            messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
            for(int i = 0; i < jeu.getListeJoueur().size();i++){
                System.out.println("Test");
                listeScorePanel.get(i).misAJour(jeu, jeu.getListeJoueur().get(i));
            }
        }
        bq.misAJour(jeu);

        if(jeu.getEtat() == Jeu.ETAT_FINAL) {
            collecteur.toggleVictoire(true);
        }
    }





    private Coup suggestionCoup(){
        IAExpert sugg = new IAExpert(jeu);
        return sugg.elaboreCoup();
    }


    private Position suggestionPlacement(){
        IAExpert sugg = new IAExpert(jeu);
        return sugg.elaborePlacement();
    }

    public BanquiseGraphique getBq(){
        return bq;
    }





    public void toggleButton(){
        bPause.setEnabled(!bPause.isEnabled());
        bSuggestion.setEnabled(!bSuggestion.isEnabled());
        bRefaire.setEnabled(!bRefaire.isEnabled());
        bAnnuler.setEnabled(!bAnnuler.isEnabled());
        bOk.setEnabled(!bOk.isEnabled());
    }



    public void activateButton(){
        bPause.setEnabled(true);
        bSuggestion.setEnabled(true);
        bRefaire.setEnabled(true);
        bAnnuler.setEnabled(true);
        bOk.setEnabled(true);
    }

}
