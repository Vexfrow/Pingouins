package Interface.Panes;

import Interface.GameBoard.ImagePanel;
import Interface.GameConstants;
import Model.Jeu;
import Vue.CollecteurEvenements;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Victoire extends JPanel{

    JButton relancerPartie;
    JButton retourMenu;
    JPanel panelBoutons;

    Jeu jeu;

    Label messageVictoire;

    CollecteurEvenements controlleur;

    ArrayList<Integer> gagnant;

    public Victoire(CollecteurEvenements c){
        jeu = c.getJeu();
        controlleur = c;
        this.setLayout(new BorderLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        gagnant = jeu.gagnant();

        setEcranVictoire();
    }


    private void setEcranVictoire(){


        //-------------message gagnant----------------
        messageVictoire = new Label();
        messageVictoire.setText("Victoire du joueur " + gagnant);
        messageVictoire.setFont(new Font("Serif", Font.PLAIN, 30));

        this.add(messageVictoire, BorderLayout.NORTH);



        setScore();



        panelBoutons = new JPanel();
        panelBoutons.setBackground(GameConstants.BACKGROUND_COLOR);
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.X_AXIS));
        relancerPartie = new JButton(new ImageIcon(GameConstants.relancerPartieB));
        retourMenu = new JButton(new ImageIcon(GameConstants.retourMenuB));


        relancerPartie.setBorderPainted(false);
        relancerPartie.setContentAreaFilled(false);
        relancerPartie.setBackground(Color.CYAN);
        //relancerPartie.addActionListener(e -> controlleur.togglePause(true));

        retourMenu.setBorderPainted(false);
        retourMenu.setContentAreaFilled(false);
        retourMenu.setBackground(Color.CYAN);
        retourMenu.addActionListener(e -> controlleur.switchMenu());


        panelBoutons.add(relancerPartie);
        panelBoutons.add(retourMenu);

        this.add(panelBoutons, BorderLayout.SOUTH);
    }




    private void setScore(){

        JPanel allScore = new JPanel();
        allScore.setLayout(new BoxLayout(allScore, BoxLayout.X_AXIS));

        for(int i = 0; i < jeu.getListeJoueur().size();i++) {

            BufferedImage pingouin = getImage(i+1);

            ImagePanel jlP = new ImagePanel(GameConstants.poisson);
            ImagePanel jlH = new ImagePanel(GameConstants.hexagone);
            ImagePanel jlPing = new ImagePanel(pingouin);

            //Panel de base pour chaque score
            JPanel mainP = new JPanel();
            mainP.setLayout(new BorderLayout());
            mainP.setBorder(new LineBorder(Color.BLACK));

            //Panel pour les deux scores
            JPanel imageP = new JPanel();
            imageP.setLayout(new BoxLayout(imageP, BoxLayout.X_AXIS));


            //Panel pour le score poisson
            JPanel poissonP = new JPanel();
            poissonP.setLayout(new BoxLayout(poissonP, BoxLayout.Y_AXIS));

            //Panel pour le score hexagone
            JPanel hexaP = new JPanel();
            hexaP.setLayout(new BoxLayout(hexaP, BoxLayout.Y_AXIS));

            imageP.add(poissonP);
            imageP.add(hexaP);

            //Texte pour le numéro du joueur
            JLabel numJoueur = new JLabel("Joueur " + (i + 1));
            numJoueur.setForeground(Color.BLACK);

            mainP.add(numJoueur, BorderLayout.NORTH);
            mainP.add(jlPing, BorderLayout.CENTER);
            mainP.add(imageP, BorderLayout.SOUTH);

            JLabel scoreP = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getScore()));
            poissonP.add(jlP);
            poissonP.add(scoreP);

            JLabel scoreH = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
            hexaP.add(jlH);
            hexaP.add(scoreH);


            allScore.add(mainP);
        }
        this.add(allScore, BorderLayout.CENTER);
    }

    private BufferedImage getImage(int i) {
        if(i == 1){
            return (gagnant.contains(1)) ? GameConstants.pingouinBleuC : GameConstants.pingouinBleu;
        }else if(i == 2){
            return (gagnant.contains(2)) ? GameConstants.pingouinRougeC : GameConstants.pingouinRouge;
        }else if(i == 3){
            return (gagnant.contains(3)) ? GameConstants.pingouinVertC : GameConstants.pingouinVert;
        }else{
            return (gagnant.contains(4)) ? GameConstants.pingouinJauneC : GameConstants.pingouinJaune;
        }
    }

}
