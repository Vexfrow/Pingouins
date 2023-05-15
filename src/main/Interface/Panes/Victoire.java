package Interface.Panes;

import Interface.GameConstants;
import Model.Jeu;
import Model.Joueur;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Victoire extends JPanel{

    JButton relancerPartie;
    JButton retourMenu;
    JPanel panelBoutons;

    Jeu jeu;

    Label messageVictoire;

    CollecteurEvenements controlleur;

    BufferedImage pingouinBleuC,pingouinRougeC, pingouinVertC, pingouinJauneC;
    BufferedImage pingouinBleu,pingouinRouge, pingouinVert, pingouinJaune;


    public Victoire(CollecteurEvenements c){
        jeu = c.getJeu();
        controlleur = c;
        this.setLayout(new BorderLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);

        setEcranVictoire();
    }


    private void setEcranVictoire(){
        panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.X_AXIS));
        relancerPartie = new JButton("Relancer une partie");
        retourMenu = new JButton("Revenir au menu principal");
        retourMenu.addActionListener(e -> controlleur.switchMenu());

        messageVictoire = new Label();
        ArrayList<Joueur> arl = jeu.getListeJoueur();
        int jMax = 0;

        for(int i = 0; i < arl.size(); i++){
            if(i == 0 || arl.get(i).getScore() > arl.get(jMax-1).getScore())
                jMax = i+1;
        }

        messageVictoire.setText("Victoire du joueur " + jMax);

        this.add(messageVictoire, BorderLayout.NORTH);

        panelBoutons.add(relancerPartie);
        panelBoutons.add(retourMenu);

        this.add(panelBoutons, BorderLayout.CENTER);
    }




}
