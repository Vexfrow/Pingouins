package Interface.Panes;

import Interface.GameConstants;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;

public class Pause extends JPanel {

    private CollecteurEvenements collecteur;
    private final JLabel titrePause;
    private final JButton reprendre;
    private final JButton sauvegarder;
    private final JButton chargerPartie;
    private final JButton regles;
    private final JButton quitter;
    private final JButton recommencer;


    public Pause(CollecteurEvenements c){
        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        this.collecteur = c;
        this.titrePause = new JLabel("Pause", JLabel.CENTER);
        this.reprendre = new JButton();
        this.recommencer = new JButton();
        this.sauvegarder = new JButton();
        this.chargerPartie = new JButton();
        this.regles = new JButton();
        this.quitter = new JButton();
        setPause();
    }

    private void setPause(){
        titrePause.setFont(new Font("Arial", Font.BOLD, 15));
        reprendre.setContentAreaFilled(false);
        recommencer.setContentAreaFilled(false);
        sauvegarder.setContentAreaFilled(false);
        regles.setContentAreaFilled(false);
        quitter.setContentAreaFilled(false);
        chargerPartie.setContentAreaFilled(false);

        reprendre.setBorderPainted(false);
        recommencer.setBorderPainted(false);
        sauvegarder.setBorderPainted(false);
        regles.setBorderPainted(false);
        quitter.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;


        gbc.anchor = GridBagConstraints.NORTH;
        add(titrePause, gbc);
        //gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTH;
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

        recommencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.replay();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                reprendre.setIcon(new ImageIcon(reSize(reprendre, GameConstants.reprendre)));
                recommencer.setIcon(new ImageIcon(reSize(recommencer, GameConstants.relancerPartie)));
                chargerPartie.setIcon(new ImageIcon(reSize(chargerPartie, GameConstants.chargerPartie)));
                regles.setIcon(new ImageIcon(reSize(regles, GameConstants.reglesPause)));
                sauvegarder.setIcon(new ImageIcon(reSize(sauvegarder, GameConstants.sauvegarder)));
                quitter.setIcon(new ImageIcon(reSize(quitter, GameConstants.abandonner)));
            }
        });


    }

    public Image reSize(JButton b, Image img){
        return img.getScaledInstance((int)(getWidth()*0.8), (int)(getHeight()*0.12) ,Image.SCALE_SMOOTH);
    }


}
