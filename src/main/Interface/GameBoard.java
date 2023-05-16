package Interface;

import Model.Jeu;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    CollecteurEvenements collecteur;

    Label messageTour;

    public Jeu jeu;

    private JButton bPause, bRefaire, bSuggestion, bAnnuler, bHistorique;
    BufferedImage poisson, hexagone, annuler, refaire, pause, suggestion;

    private final ArrayList<ScorePanel> listeScorePanel;

    public GameBoard(Jeu j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();
        messageTour = new Label();

        listeScorePanel = new ArrayList<>();

        jeu = j;

        collecteur = c;
        collecteur.setPlateauJeu(this);
        //collecteur.setJeu(j);

        poisson = chargeImage("poisson");
        hexagone = chargeImage("hexagone");
        suggestion = chargeImage("boutonAstuce");
        pause = chargeImage("boutonPause");
        annuler = chargeImage("boutonAnnuler");
        refaire = chargeImage("boutonRefaire");


        setMenuGame();
        setGamePanel();
    }



    private BufferedImage chargeImage(String nom){
        try {
            InputStream in = new FileInputStream("resources/assets/jeu/" + nom + ".png");
            return ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("Fichier \"" + nom + "\" introuvable");
        }
        return null;
    }


    private void setMenuGame(){

        menuGame.setLayout(new GridBagLayout());
        menuGame.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        //----------------Boutons Pause et suggestion-------------
        bPause = new JButton(new ImageIcon(pause));
        bPause.setBorderPainted(false);
        bPause.setContentAreaFilled(false);
        bPause.addActionListener(e -> collecteur.togglePause(true));

        bSuggestion = new JButton(new ImageIcon(suggestion));
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

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        menuGame.add(messageTour, c);


        //----------Score--------------
        setScore();

        //----------Historique-------------
        bHistorique = new JButton("Historique");

        c.gridx = 0;
        c.weighty = 15;
        c.gridy = 3+jeu.getListeJoueur().size();
        c.fill = GridBagConstraints.BOTH;
        menuGame.add(bHistorique,c);


        //----------Boutons annuler et refaire --------------
        bAnnuler = new JButton(new ImageIcon(annuler));
        bAnnuler.setBorderPainted(false);
        bAnnuler.setContentAreaFilled(false);

        bAnnuler.addActionListener(e -> {
            jeu.annule();
            misAJour(jeu);
        });

        bRefaire = new JButton(new ImageIcon(refaire));
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

    private void setScore(){


        ImageIcon iiP = new ImageIcon(poisson);
        ImageIcon iiH = new ImageIcon(hexagone);



        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            ScorePanel s = new ScorePanel(jeu);
            listeScorePanel.add(s);

            //Panel de base pour chaque score
            JPanel mainP = new JPanel();
            mainP.setLayout(new GridBagLayout());
            mainP.setBorder(new LineBorder(Color.BLACK));
            GridBagConstraints c2 = new GridBagConstraints();

            s.panelScore = mainP;

            //Panel pour les deux scores
            JPanel imageP = new JPanel();
            imageP.setLayout(new BoxLayout(imageP, BoxLayout.X_AXIS));

            //-----------------------------------

            //Panel pour le score poisson
            JPanel poissonP = new JPanel();
            poissonP.setLayout(new BoxLayout(poissonP, BoxLayout.Y_AXIS));
            s.panelScorePoisson = poissonP;

            JLabel scoreP = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getScore()));
            poissonP.add(scoreP);
            s.scorePoisson = scoreP;

            JLabel jlP = new JLabel(iiP);
            poissonP.add(jlP);

            imageP.add(poissonP);
            //-----------------------------------

            //Panel pour le score hexagone
            JPanel hexaP = new JPanel();
            hexaP.setLayout(new BoxLayout(hexaP, BoxLayout.Y_AXIS));
            s.panelScoreHexagone = hexaP;

            JLabel scoreH = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
            hexaP.add(scoreH);
            s.scoreHexagone = scoreH;

            JLabel jlH = new JLabel(iiH);
            hexaP.add(jlH);

            imageP.add(hexaP);
            //-----------------------------------

            //Texte pour le numéro du joueur
            JLabel numJoueur = new JLabel("Joueur "+(i+1));
            numJoueur.setForeground(Color.BLACK);

            c2.gridx = 0;
            c2.gridy = 0;
            c2.gridwidth = 2;
            c2.weighty = 1;
            mainP.add(numJoueur, c2);

            c2.gridx = 0;
            c2.gridy = 1;
            c2.gridwidth = 2;
            c2.weighty = 5;
            mainP.add(imageP, c2);



            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2+i;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weighty = 75;
            menuGame.add(mainP, c);
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
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
            ScorePanel s = listeScorePanel.get(i);
            s.scoreHexagone.setText(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
            s.scorePoisson.setText(String.valueOf(jeu.getListeJoueur().get(i).getScore()));

            if(i+1 == jeu.getJoueurCourant()){
                switch(i+1){
                    case 1 : s.panelScoreHexagone.setBackground(GameConstants.BLEU); s.panelScorePoisson.setBackground(GameConstants.BLEU); s.panelScoreHexagone.setBackground(GameConstants.BLEU); break;
                    case 2 : s.panelScoreHexagone.setBackground(GameConstants.ROUGE); s.panelScorePoisson.setBackground(GameConstants.ROUGE); s.panelScoreHexagone.setBackground(GameConstants.ROUGE); break;
                    case 3 : s.panelScoreHexagone.setBackground(GameConstants.VERT); s.panelScorePoisson.setBackground(GameConstants.VERT); s.panelScoreHexagone.setBackground(GameConstants.VERT);break;
                    case 4 : s.panelScoreHexagone.setBackground(GameConstants.JAUNE); s.panelScorePoisson.setBackground(GameConstants.JAUNE); s.panelScoreHexagone.setBackground(GameConstants.JAUNE);break;
                }
            }else{
                s.panelScoreHexagone.setBackground(new Color(200,200,200)); s.panelScorePoisson.setBackground(new Color(200,200,200)); s.panelScoreHexagone.setBackground(new Color(200,200,200));
            }
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
