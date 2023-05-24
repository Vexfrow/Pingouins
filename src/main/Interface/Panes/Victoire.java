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
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        gagnant = jeu.gagnant();

        setEcranVictoire();
    }


    private void setEcranVictoire(){

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        //-------------message gagnant----------------
        messageVictoire = new Label();
        messageVictoire.setText(getTexteVictoire());
        messageVictoire.setFont(new Font("Serif", Font.PLAIN, 30));

        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.WEST;
        this.add(messageVictoire, c);



        setScore();



        relancerPartie = new JButton(new ImageIcon(GameConstants.relancerPartie));
        retourMenu = new JButton(new ImageIcon(GameConstants.retourMenu));


        relancerPartie.setBorderPainted(false);
        relancerPartie.setContentAreaFilled(false);
        relancerPartie.setBackground(Color.CYAN);
        relancerPartie.addActionListener(e -> controlleur.replay());

        retourMenu.setBorderPainted(false);
        retourMenu.setContentAreaFilled(false);
        retourMenu.setBackground(Color.CYAN);
        retourMenu.addActionListener(e -> controlleur.switchMenu());


        c.gridy = 2;
        c.weighty = 10;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(relancerPartie, c);
        c.gridx = 2;
        this.add(retourMenu, c);

    }




    private void setScore(){

        JPanel allScore = new JPanel();
        allScore.setLayout(new GridLayout(1,jeu.getListeJoueur().size()));

        for(int i = 0; i < jeu.getListeJoueur().size();i++) {

            BufferedImage pingouin = getImage(i+1);

            ImagePanel jlP = new ImagePanel(GameConstants.poisson);
            ImagePanel jlH = new ImagePanel(GameConstants.hexagone);
            ImagePanel jlPing = new ImagePanel(pingouin);

            //Panel de base pour chaque score
            JPanel mainP = new JPanel();
            mainP.setLayout(new BoxLayout(mainP,BoxLayout.Y_AXIS));
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

            mainP.add(numJoueur);
            mainP.add(jlPing);
            mainP.add(imageP);

            JLabel scoreP = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getScore()));
            poissonP.add(jlP);
            poissonP.add(scoreP);

            JLabel scoreH = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
            hexaP.add(jlH);
            hexaP.add(scoreH);


            allScore.add(mainP);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 50;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        this.add(allScore, c);
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


    public Image imageOnButton(JButton b, Image img){
        if(b.getWidth() != 0 && b.getHeight() != 0)
            return img.getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
        else
            return img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    }


    @Override
    public void invalidate() {
        super.invalidate();
        majSize();
    }

    private void majSize(){


        relancerPartie.setSize(new Dimension(getWidth()/4,getWidth()/8));
        relancerPartie.setPreferredSize(new Dimension(getWidth()/4,getWidth()/8));


        retourMenu.setSize(new Dimension(getWidth()/4,getWidth()/8));
        retourMenu.setPreferredSize(new Dimension(getWidth()/4,getWidth()/8));


        relancerPartie.setIcon(new ImageIcon(imageOnButton(relancerPartie, GameConstants.relancerPartie)));
        retourMenu.setIcon(new ImageIcon(imageOnButton(retourMenu, GameConstants.retourMenu)));
    }


    private String getTexteVictoire(){
        if (gagnant.size() == 1){
            return "Victoire du joueur " + gagnant.get(0) + " !";
        }else if(gagnant.size() == jeu.getNbJoueur()){
            return "Égalité parfaite entre tous les joueurs !";
        }else {
            String result = "Victoire des joueurs ";
            for(int i = 0; i < gagnant.size(); i++){
                if(i == gagnant.size()-2)
                    result += gagnant.get(i) + " et ";
                else if(i == gagnant.size()-1)
                    result += gagnant.get(i) + " !";
                else
                    result += gagnant.get(i) + ", ";
            }
            return result;
        }




    }

}
