package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import Controleur.Controleur;
import Model.Jeu;
import Model.Joueur;
import Joueur.*;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.awt.event.*;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.util.ArrayList;


public class Selection extends JPanel {
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;
    private JButton retour;
    private JButton sauvegarde;
    private JButton valide;
    private Image flecheRetour;
    private Image lancer;
    private Image defaut;
    private IconeSelection listJoueur[];

    public Selection(CollecteurEvenements ctrl){

        this.c = ctrl;
        retour = new JButton();

        sauvegarde = new JButton();
        valide = new JButton();


        try{
            flecheRetour = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRetour.png"));
            lancer = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonLancerPartie.png"));
            defaut = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/boutonChoixDefaut.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        listJoueur = new IconeSelection[4];
        setSelection();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                sauvegarde.setIcon(new ImageIcon(reScale(sauvegarde.getSize(), defaut)));
                valide.setIcon(new ImageIcon(reScale(valide.getSize(), lancer)));
            }
        });

    }

    public Image reScale(Dimension d, Image img){
        Image neoImg = img.getScaledInstance(d.width, d.height, Image.SCALE_AREA_AVERAGING) ;
        return neoImg;
    }


    public void setSelection(){
        valide.setPreferredSize(new Dimension(200, 80));
        valide.setFont(new Font("Arial", Font.PLAIN, 20));
        valide.setBackground(new Color(60, 60, 100));
        sauvegarde.setPreferredSize(new Dimension(200, 80));
        retour.setBorderPainted(false);
        retour.setContentAreaFilled(false);
        retour.setPreferredSize(new Dimension(100, 52));

        valide.setBorderPainted(false);
        valide.setContentAreaFilled(false);

        sauvegarde.setBorderPainted(false);
        sauvegarde.setContentAreaFilled(false);

        this.setLayout(new GridBagLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        //Dessin des hexagones
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        this.add(retour, gbc );


        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 2;
        JLabel j = new JLabel("Selection des Joueurs", SwingConstants.CENTER);
        j.setFont(new Font("Helvetica", Font.BOLD, 50));
        this.add(j, gbc);



        gbc.gridheight = 1;
        gbc.gridwidth =3;

        gbc.gridx = 0;
        gbc.gridy = 4;


        gbc.weightx = 2;
        gbc.weighty = 3;

        IconeSelection p1 = new IconeSelection(GameConstants.BLEU, 100, false);

        IconeSelection p2 = new IconeSelection(GameConstants.ROUGE, 100, false);

        IconeSelection p3 = new IconeSelection(GameConstants.VERT, 100, true);

        IconeSelection p4 = new IconeSelection(GameConstants.JAUNE, 100, true);


        listJoueur[0] = p1;
        listJoueur[1] = p2;
        listJoueur[2] = p3;
        listJoueur[3] = p4;
        JPanel pane = new JPanel(new GridLayout(1, 4));
        for(int i =0; i < 4; i++){
            pane.add(listJoueur[i]);
        }
        add(pane, gbc);



        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.weighty = 1;
        this.add(sauvegarde, gbc);
        sauvegarde.setBackground(new Color(0x7292A4));
        sauvegarde.setForeground(Color.WHITE);
        gbc.gridx = 2;
        this.add(valide, gbc);
        valide.setBackground(new Color(0x155D85));
        valide.setForeground(Color.WHITE);


        //Action sur les boutons
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.switchMenu();
            }
        });

        valide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("nb P " + numberOfPlayer());
                ArrayList<Joueur> ar = getJoueur();
                Jeu j = new Jeu(ar);
                ArrayList<IAJoueur> arj = getIA(j);
                c.newGame(j, arj, ar);
                c.switchGameBoard();
                c.startGame();
            }
        });
    }

    public void changeIcon(){
            retour.setIcon(new ImageIcon(flecheRetour));
    }


    public int numberOfPlayer(){
        int j = 0;
        for(int i =0; i < 4; i++){
            if(listJoueur[i].isActif()){
                j++;
            }
        }
        return j;
    }


    public  ArrayList<Joueur> getJoueur(){
        ArrayList<Joueur> ar = new ArrayList<>();
        for(int i =0; i < 4; i++){
            if(listJoueur[i].isActif()){
                if(listJoueur[i].getName().equals(IconeSelection.HUMAIN)) {
                    ar.add(new Joueur(1, 0, 0, 0));
                }else{
                    ar.add(new Joueur(1, 0, 0, 1));
                }

            }
        }
        return ar;
    }

    public  ArrayList<IAJoueur> getIA(Jeu j){
        ArrayList<IAJoueur> arj = new ArrayList<>();
        for(int i =0; i < 4; i++){
            if(listJoueur[i].isActif()){
                if(listJoueur[i].getName().equals(IconeSelection.HUMAIN)) {
                    arj.add(null);
                }else{
                    if(listJoueur[i].getName().equals(IconeSelection.IA_EASY)){
                        arj.add(new IAAleatoire(j));
                    }else if(listJoueur[i].getName().equals(IconeSelection.IA_MEDIUM)){
                        arj.add(new IATroisPoissons(j));
                    }else if(listJoueur[i].getName().equals(IconeSelection.IA_DIFFICILE)){
                        arj.add(new IAMinimax(j));
                    }
                }

            }
        }
        return arj;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < listJoueur.length; i++){
            if(!listJoueur[i].isActif()){

            }
        }
    }
}



