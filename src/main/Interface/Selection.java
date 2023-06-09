package Interface;

import javax.swing.*;

import Interface.SelElm.IconeSelection;
import Model.Jeu;
import Model.Joueur;
import Joueur.*;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;


public class Selection extends JPanel {
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;
    private JButton retour;
    private JButton sauvegarde;
    private JButton valide;

    private IconeSelection listJoueur[];
    // 0 - 3
    private int last;

    public Selection(CollecteurEvenements ctrl){
        last = 1;
        this.c = ctrl;
        retour = new JButton();

        sauvegarde = new JButton();
        valide = new JButton();

        listJoueur = new IconeSelection[4];
        setSelection();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                sauvegarde.setIcon(new ImageIcon(reScale(sauvegarde.getSize(), GameConstants.defaut)));
                valide.setIcon(new ImageIcon(reScale(valide.getSize(), GameConstants.lancerPartie)));
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

        int k =0;
        IconeSelection p1 = new IconeSelection(GameConstants.BLEU, 100, false, this,k);
        k++;
        IconeSelection p2 = new IconeSelection(GameConstants.ROUGE, 100, false, this,k);
        k++;
        IconeSelection p3 = new IconeSelection(GameConstants.VERT, 100, true, this,k);
        k++;
        IconeSelection p4 = new IconeSelection(GameConstants.JAUNE, 100, true, this, k);


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

        for(int i =0; i < 4; i++){
            listJoueur[i].repaint();
        }


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
                ArrayList<Joueur> ar = getJoueur();
                if(ar.size() >= 2){
                    Jeu j = new Jeu(ar);
                    ArrayList<IAJoueur> arj = getIA(j);
                    c.newGame(j, arj, ar);
                    c.switchGameBoard();
                }

            }
        });

        sauvegarde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings s = new Settings();
                ArrayList<Joueur> ar = getJoueur();
                int t = ar.size();
                int[] tj = new int[t];
                for(int i = 0; i < t; i++){
                    tj[i] = ar.get(i).estIA();
                }
                s.writeSettings(t, tj);
            }
        });

        refresh();


    }

    public void changeIcon(){
            retour.setIcon(new ImageIcon(GameConstants.flecheRetour));
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
                if(listJoueur[i].getName().equals(IconeSelection.IA_EASY)){
                    ar.add(new Joueur((i+1), 0, 0, 1));
                }else if(listJoueur[i].getName().equals(IconeSelection.IA_MEDIUM)){
                    ar.add(new Joueur((i+1), 0, 0, 2));
                }else if(listJoueur[i].getName().equals(IconeSelection.IA_DIFFICILE)){
                    ar.add(new Joueur((i+1), 0, 0, 3));
                }else if(listJoueur[i].getName().equals(IconeSelection.IA_EXPERTE)){
                    ar.add(new Joueur((i+1), 0, 0, 4));
                }else{
                    ar.add(new Joueur((i+1), 0, 0, 0));
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
                        arj.add(new IAFacile(j));
                    }else if(listJoueur[i].getName().equals(IconeSelection.IA_MEDIUM)){
                        arj.add(new IAMoyen(j));
                    }else if(listJoueur[i].getName().equals(IconeSelection.IA_DIFFICILE)){
                        arj.add(new IADifficile(j));
                    }else if(listJoueur[i].getName().equals(IconeSelection.IA_EXPERTE)){
                        arj.add(new IAExpert(j));
                    }
                }

            }
        }
        return arj;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < 4; i++){
            listJoueur[i].revalidate();
            listJoueur[i].repaint();
        }
    }

    public void ajout(){

        last++;
        if(last > 3){
            last = 3;
        }
    }

    public void del(){
        last--;
        if(last < 1){
            last =2;
        }
    }

    public int getLast(){
        return last;
    }

    public void refresh(){
        for(int i =0; i < 4; i++){
            listJoueur[i].maj();
        }
    }
}



