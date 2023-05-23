package Interface.GameBoard;

import Interface.GameConstants;
import Model.Jeu;
import Model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScorePlacementPanel extends ScorePanel{


    Joueur joueur;

    JLabel textJoueur;
    JLabel textNbPingouin;
    ImagePanel imagePanel;

    public ScorePlacementPanel(Jeu j) {
        super(j);

        joueur = null;
        textJoueur = new JLabel();
        textNbPingouin = new JLabel();
        imagePanel = new ImagePanel(getImage());
    }


    public ScorePlacementPanel(Jeu j, Joueur player) {
        super(j);

        joueur = player;
        textJoueur = new JLabel();
        textNbPingouin = new JLabel();
        imagePanel = new ImagePanel(getImage());
        setPanel();
    }

    public void setPanel(){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(200,200,200));

        textJoueur.setText("Joueur " + joueur.getNumeroJoueur() + " - " + joueur.getName());
        textJoueur.setFont(new Font("Serif", Font.PLAIN, 19));
        this.add(textJoueur);


        JPanel panelPingouin = new JPanel();
        panelPingouin.setLayout(new BoxLayout(panelPingouin, BoxLayout.X_AXIS));
        panelPingouin.setBackground(new Color(200,200,200));

        imagePanel.setBackground(new Color(200,200,200));
        panelPingouin.add(Box.createRigidArea(new Dimension(40, 0)));
        panelPingouin.add(imagePanel);


        textNbPingouin.setText("X" + (jeu.getNbPingouinJoueur() - joueur.getListePingouin().size()));
        textNbPingouin.setFont(new Font("Serif", Font.PLAIN, 30));
        panelPingouin.add(textNbPingouin);
        panelPingouin.add(Box.createRigidArea(new Dimension(40, 0)));
        this.add(panelPingouin);
    }




    private BufferedImage getImage(){
        if(joueur.getNumeroJoueur() == 1)
            return GameConstants.pingouinBleu;
        else if(joueur.getNumeroJoueur() == 2)
            return GameConstants.pingouinRouge;
        else if(joueur.getNumeroJoueur() == 3)
            return GameConstants.pingouinVert;
        else
            return GameConstants.pingouinJaune;

    }


    public void misAJour(Jeu j, Joueur je){

        joueur = je;
        textNbPingouin.setText("X" + (jeu.getNbPingouinJoueur() - joueur.getListePingouin().size()));


        if(joueur.getNumeroJoueur() == jeu.getJoueurCourant()){
            switch(joueur.getNumeroJoueur()){
                case 1 : this.setBackground(GameConstants.BLEU); textJoueur.setForeground(Color.WHITE);break;
                case 2 : this.setBackground(GameConstants.ROUGE); break;
                case 3 : this.setBackground(GameConstants.VERT); break;
                case 4 : this.setBackground(GameConstants.JAUNE);break;
            }
        }else{
            this.setBackground(new Color(200,200,200));textJoueur.setForeground(Color.BLACK);;
        }

     //   revalidate();
    }

}
