package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import Controleur.Controleur;
import Model.JeuAvance;
import Vue.CollecteurEvenements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;


public class Selection extends JPanel {
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;
    private JButton retour;
    private JButton sauvegarde;
    private JButton valide;
    private Image flecheRetour;
    private IconeSelection listJoueur[];

    public Selection(CollecteurEvenements ctrl){

        this.c = ctrl;
        retour = new JButton();
        sauvegarde = new JButton("<html>Sauvegarder comme<br>option par défaut<br>(Partie rapide)</html>");
        valide = new JButton("<html> Lancer la Partie</html>");
        try{
            flecheRetour = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRetour.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        listJoueur = new IconeSelection[4];
        setSelection();

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

        this.setLayout(new GridBagLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        //Dessin des hexagones
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        this.add(retour, gbc );


        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 2;
        JLabel j = new JLabel("Selection des Joueurs", SwingConstants.CENTER);
        j.setFont(new Font("Helvetica", Font.BOLD, 50));
        this.add(j, gbc);



        gbc.gridheight = 1;
        gbc.gridwidth =1;

        gbc.gridx = 0;
        gbc.gridy = 4;


        gbc.weightx = 1;
        gbc.weighty = 3;

        IconeSelection p1 = new IconeSelection(GameConstants.ROUGE, 100, false);
        this.add(p1, gbc);

        gbc.gridx = 1;
        IconeSelection p2 = new IconeSelection(GameConstants.BLEU, 100, false);
        this.add(p2, gbc);

        gbc.gridx = 2;
        IconeSelection p3 = new IconeSelection(GameConstants.VERT, 100, true);
        this.add(p3, gbc);

        gbc.gridx = 3;
        IconeSelection p4 = new IconeSelection(GameConstants.JAUNE, 100, true);
        this.add(p4, gbc);


        listJoueur[0] = p1;
        listJoueur[1] = p2;
        listJoueur[2] = p3;
        listJoueur[3] = p4;



        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.gridy = 5;
        gbc.gridx = 1;
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
                c.newGame(numberOfPlayer());
                c.switchGameBoard();
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
}



