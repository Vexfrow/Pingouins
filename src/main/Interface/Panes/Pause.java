package Interface.Panes;

import Interface.GameConstants;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class Pause extends JPanel {

    private CollecteurEvenements collecteur;
    private JLabel titrePause;
    private JButton reprendre;
    private JButton sauvegarder;
    private JButton chargerPartie;
    private JButton regles;
    private JButton quitter;
    private JButton recommencer;

    public Pause(CollecteurEvenements c){
        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        this.collecteur = c;
        this.titrePause = new JLabel("Pause", JLabel.CENTER);
        this.reprendre = new JButton("Reprendre");
        this.recommencer = new JButton("Recommencer");
        this.sauvegarder = new JButton("Sauvegarder");
        this.chargerPartie = new JButton("ChargerPartie");
        this.regles = new JButton("Regles");
        this.quitter = new JButton("Abandonner");
        setPause();
    }

    private void setPause(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        add(titrePause, gbc);
        gbc.gridy = 1;
        add(reprendre, gbc);
        gbc.gridy = 2;
        add(recommencer, gbc);
        gbc.gridy = 3;
        add(sauvegarder, gbc);
        gbc.gridy = 4;
        add(chargerPartie, gbc);
        gbc.gridy = 5;
        add(regles, gbc);
        gbc.gridy = 6;
        add(quitter, gbc);

        reprendre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.togglePause(true);
            }
        });

        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.toggelCharge(false);
            }
        });

        regles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.toggleHelp(false);
            }
        });

        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.toggleSave();
            }
        });

        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.switchMenu();
            }
        });


    }

}
