package Interface;

import Model.Jeu;
import Model.Joueur;
import Vue.Hexagone;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

public class ScorePanel extends JPanel {

    public JPanel panelScores;

    public JLabel scorePoisson;
    public JLabel scoreHexagone;
    public JLabel numJoueur;

    public JPanel panelScorePoisson;
    public JPanel panelScoreHexagone;

    private Jeu jeu;

    private final BufferedImage poisson;
    private final BufferedImage hexagone;


    int sizeImage;

    public ScorePanel(Jeu j,Joueur joueur) {
        scorePoisson = new JLabel();
        scoreHexagone = new JLabel();
        panelScorePoisson = new JPanel();
        panelScoreHexagone = new JPanel();
        panelScores = new JPanel();
        numJoueur = new JLabel();
        jeu = j;

        poisson = chargeImage("poisson");
        hexagone = chargeImage("hexagone");

        sizeImage = 0;

        createPanel(joueur);
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

    public void createPanel(Joueur joueur){

        ImageIcon iiP = new ImageIcon(poisson);
        Image image = iiP.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        iiP = new ImageIcon(newimg);

        ImageIcon iiH = new ImageIcon(hexagone);
        image = iiH.getImage();
        newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        iiH = new ImageIcon(newimg);

        //Panel de base pour chaque score
        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.BLACK));
        GridBagConstraints c2 = new GridBagConstraints();


        //Panel pour les deux scores
        panelScores.setLayout(new BoxLayout(panelScores, BoxLayout.X_AXIS));

        //-----------------------------------

        //Panel pour le score poisson
        panelScorePoisson.setLayout(new BoxLayout(panelScorePoisson, BoxLayout.Y_AXIS));

        scorePoisson = new JLabel(String.valueOf(joueur.getScore()));
        panelScorePoisson.add(scorePoisson);

        JLabel jlP = new JLabel(iiP);
        panelScorePoisson.add(jlP);

        panelScores.add(panelScorePoisson);
        //-----------------------------------

        //Panel pour le score hexagone
        panelScoreHexagone.setLayout(new BoxLayout(panelScoreHexagone, BoxLayout.Y_AXIS));

        scoreHexagone = new JLabel(String.valueOf(joueur.getNbCasesMange()));
        panelScoreHexagone.add(scoreHexagone);

        JLabel jlH = new JLabel(iiH);
        panelScoreHexagone.add(jlH);

        panelScores.add(panelScoreHexagone);
        //-----------------------------------

        //Texte pour le numéro du joueur
        numJoueur.setText("Joueur "+ joueur.getNumeroJoueur());
        numJoueur.setForeground(Color.BLACK);

        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 2;
        c2.weighty = 1;
        this.add(numJoueur, c2);

        c2.gridx = 0;
        c2.gridy = 1;
        c2.gridwidth = 2;
        c2.weighty = 5;
        this.add(panelScores, c2);

    }



    public void misAJour(Jeu j, Joueur joueur){
        jeu = j;
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
    }



    @Override
    public void invalidate() {
        super.invalidate();
        majSize();
    }



    private void majSize() {
        float largeur = panelScorePoisson.getSize().width;
        float hauteur = panelScorePoisson.getSize().height;

        sizeImage = (int) Math.min(largeur, hauteur);
    }


//    public void paintComponent(Graphics g) {
//        majSize();
//        panelScorePoisson.getGraphics().drawImage(poisson, 0,0, null);
//        panelScoreHexagone.getGraphics().drawImage(hexagone, 0,0, null);
//    }

}
