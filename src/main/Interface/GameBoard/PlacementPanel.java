package Interface.GameBoard;

import Interface.GameBoard.ImagePanel;
import Model.Jeu;
import Model.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;



public class PlacementPanel extends JPanel {

    BufferedImage pingouinR, pingouinV, pingouinB, pingouinJ;
    Jeu jeu;

    Joueur joueur;

    JLabel textJoueur;
    JLabel textNbPingouin;

    ImagePanel imagePanel;


    public PlacementPanel(Jeu j, Joueur player){
        pingouinR = chargeImage("pingouinRouge");
        pingouinJ = chargeImage("pingouinJaune");
        pingouinB = chargeImage("pingouinBleu");
        pingouinV = chargeImage("pingouinVert");

        jeu = j;
        joueur = player;

        textJoueur = new JLabel();
        textNbPingouin = new JLabel();
        imagePanel = new ImagePanel(getImage());

        setPanel();
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




    private void setPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setBackground(new Color(200,200,200));

        textJoueur.setText("Joueur " + joueur.getNumeroJoueur());
        this.add(textJoueur);


        JPanel panelPingouin = new JPanel();
        panelPingouin.setLayout(new BoxLayout(panelPingouin, BoxLayout.X_AXIS));

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
            return pingouinB;
        else if(joueur.getNumeroJoueur() == 2)
            return pingouinR;
        else if(joueur.getNumeroJoueur() == 3)
            return pingouinV;
        else
            return pingouinJ;

    }


    public void misAJour(Jeu j, Joueur je){
        jeu = j;
        joueur = je;
        textNbPingouin.setText("X" + (jeu.getNbPingouinJoueur() - joueur.getListePingouin().size()));
    }




}
