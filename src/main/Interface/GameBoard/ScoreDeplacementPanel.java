package Interface.GameBoard;

import Interface.GameConstants;
import Model.Jeu;
import Model.Joueur;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ScoreDeplacementPanel extends ScorePanel {

    public JPanel panelScores;

    public JLabel scorePoisson;
    public JLabel scoreHexagone;
    public JLabel numJoueur;

    public JPanel panelScorePoisson;
    public JPanel panelScoreHexagone;

    private Jeu jeu;

    int sizeImage;

    public ScoreDeplacementPanel(Jeu j, Joueur joueur) {
        super();
        scorePoisson = new JLabel();
        scoreHexagone = new JLabel();
        panelScorePoisson = new JPanel();
        panelScoreHexagone = new JPanel();
        panelScores = new JPanel();
        numJoueur = new JLabel();
        jeu = j;

        sizeImage = 0;

        createPanel(joueur);
    }

    public void createPanel(Joueur joueur){

        //Panel de base pour chaque score
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBorder(new LineBorder(Color.BLACK));

        //Panel pour les deux scores
        panelScores.setLayout(new BoxLayout(panelScores, BoxLayout.X_AXIS));

        //-----------------------------------

        //Panel pour le score poisson
        panelScorePoisson.setLayout(new BoxLayout(panelScorePoisson, BoxLayout.Y_AXIS));

        ImagePanel jlP = new ImagePanel(GameConstants.poisson);
        panelScorePoisson.add(jlP);

        scorePoisson = new JLabel(String.valueOf(joueur.getScore()));
        scorePoisson.setFont(new Font("Serif", Font.PLAIN, 30));
        panelScorePoisson.add(scorePoisson);

        panelScores.add(panelScorePoisson);
        //-----------------------------------

        //Panel pour le score hexagone
        panelScoreHexagone.setLayout(new BoxLayout(panelScoreHexagone, BoxLayout.Y_AXIS));

        ImagePanel jlH = new ImagePanel(GameConstants.hexagone);
        panelScoreHexagone.add(jlH);

        scoreHexagone = new JLabel(String.valueOf(joueur.getNbCasesMange()));
        scoreHexagone.setFont(new Font("Serif", Font.PLAIN, 30));
        panelScoreHexagone.add(scoreHexagone);

        panelScores.add(panelScoreHexagone);
        //-----------------------------------

        //Texte pour le numéro du joueur
        numJoueur.setText("Joueur "+ joueur.getNumeroJoueur() + " - " + joueur.getName());
        numJoueur.setFont(new Font("Serif", Font.PLAIN, 20));
        numJoueur.setForeground(Color.BLACK);


        this.add(numJoueur);
        this.add(panelScores);
        this.add(Box.createRigidArea(new Dimension(0,40 - (10* jeu.getListeJoueur().size()))));

    }



    public void misAJour(Jeu j, Joueur joueur){
        scoreHexagone.setText(String.valueOf(joueur.getNbCasesMange()));
        scorePoisson.setText(String.valueOf(joueur.getScore()));

        if(joueur.getNumeroJoueur() == jeu.getJoueurCourant()){
            switch(joueur.getNumeroJoueur()){
                case 1 : panelScoreHexagone.setBackground(GameConstants.BLEU);panelScorePoisson.setBackground(GameConstants.BLEU);panelScores.setBackground(GameConstants.BLEU);this.setBackground(GameConstants.BLEU); break;
                case 2 : panelScoreHexagone.setBackground(GameConstants.ROUGE); panelScorePoisson.setBackground(GameConstants.ROUGE); panelScores.setBackground(GameConstants.ROUGE);this.setBackground(GameConstants.ROUGE); break;
                case 3 : panelScoreHexagone.setBackground(GameConstants.VERT); panelScorePoisson.setBackground(GameConstants.VERT); panelScores.setBackground(GameConstants.VERT);this.setBackground(GameConstants.VERT);break;
                case 4 : panelScoreHexagone.setBackground(GameConstants.JAUNE); panelScorePoisson.setBackground(GameConstants.JAUNE); panelScores.setBackground(GameConstants.JAUNE);this.setBackground(GameConstants.JAUNE);break;
            }
        }else{
            panelScoreHexagone.setBackground(new Color(200,200,200)); panelScorePoisson.setBackground(new Color(200,200,200)); panelScores.setBackground(new Color(200,200,200));this.setBackground(new Color(200,200,200));
        }

        switch(joueur.getNumeroJoueur()){
            case 1 : numJoueur.setBackground(GameConstants.BLEU);break;
            case 2 : numJoueur.setBackground(GameConstants.ROUGE); break;
            case 3 : numJoueur.setBackground(GameConstants.VERT);break;
            case 4 : numJoueur.setBackground(GameConstants.JAUNE);break;
        }

    }

}
