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
    private JLabel titrePause;
    private JButton reprendre;
    private JButton sauvegarder;
    private JButton chargerPartie;
    private JButton regles;
    private JButton quitter;
    private JButton recommencer;

    private Image resume;
    private Image rePlay;
    private Image save;
    private Image load;
    private Image rule;
    private Image quit;
    //private Image rule;

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
        titrePause.setFont(new Font("Arial", Font.BOLD, 20));
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






        try{
            resume = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonReprendre.png"));
            rePlay = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonRelancerPartie.png"));
            save = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonSauvegarder.png"));
            load = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonCharger.png"));
            rule = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonReglesPause.png"));
            quit = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonAbandonner.png"));
        }catch(Exception e){
            System.err.println("Pause : Impossible de charger les images");
        }




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
                reprendre.setIcon(new ImageIcon(reSize(reprendre, resume)));
                recommencer.setIcon(new ImageIcon(reSize(recommencer, rePlay)));
                chargerPartie.setIcon(new ImageIcon(reSize(chargerPartie, load)));
                regles.setIcon(new ImageIcon(reSize(regles, rule)));
                sauvegarder.setIcon(new ImageIcon(reSize(sauvegarder, save)));
                quitter.setIcon(new ImageIcon(reSize(quitter, quit)));
            }
        });


    }

    public Image reSize(JButton b, Image img){
        return img.getScaledInstance((int)(getWidth()*0.8), (int)(getHeight()*0.12) ,Image.SCALE_SMOOTH);
    }


}
