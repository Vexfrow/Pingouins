package Interface;

import Controleur.Controleur;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;

public class MenuP extends JPanel {
    private JButton partieRapide;
    private JButton partiePersonnalisee;
    private JButton chargerPartie;
    private JButton tutoriel;
    private JButton regles;
    private Image img;
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;

    public MenuP(CollecteurEvenements ctrl){
        super();
        this.c = ctrl;
        //Création des éléments
        try{
            img = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/Titre.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        partiePersonnalisee = new JButton("Partie Personnalisée");
        partieRapide = new JButton("Partie Rapide");
        chargerPartie = new JButton("Charger Partie");
        tutoriel = new JButton("Tutoriel");
        regles = new JButton("Règles");

        layout = new SpringLayout();
        menu = new JLabel();
        this.setLayout(layout);

        setMenu();



    }




    private Image reScale(Image source, int x, int y){
        return source.getScaledInstance(x, y, java.awt.Image.SCALE_AREA_AVERAGING);
    }

    private void setMenu(){
        //Button
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        tutoriel.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        regles.setBorderPainted(false);



        //Colorization
        Color reglesColor = new Color(0xFDCF76);
        Color partieRapideColor = new Color(0x155D85);
        Color partiPersonnaliseeColor = new Color(0x2678A7);
        Color chargerPartieColor = new Color(0x4D88A9);
        Color tutorielColor = new Color(0x7292A4);

        this.setBackground(GameConstants.BACKGROUND_COLOR);
        menu.setIcon(new ImageIcon(img));
        regles.setBackground(reglesColor);
        partieRapide.setBackground(partieRapideColor);
        partiePersonnalisee.setBackground(partiPersonnaliseeColor);
        chargerPartie.setBackground(chargerPartieColor);
        tutoriel.setBackground(tutorielColor);

        partieRapide.setForeground(Color.WHITE);
        partiePersonnalisee.setForeground(Color.WHITE);
        chargerPartie.setForeground(Color.WHITE);
        tutoriel.setForeground(Color.WHITE);


        //Ajouts
        this.add(menu);
        this.add(partieRapide);
        this.add(partiePersonnalisee);
        this.add(chargerPartie);
        this.add(tutoriel);
        this.add(regles);


        partieRapide.setPreferredSize(GameConstants.boutonTaille);
        partiePersonnalisee.setPreferredSize(GameConstants.boutonTaille);
        chargerPartie.setPreferredSize(GameConstants.boutonTaille);
        tutoriel.setPreferredSize(GameConstants.boutonTaille);
        regles.setPreferredSize(new Dimension(150, 50));

        //Placement
        //Par rapport à la fenetre
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, partieRapide, 0 , SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, partiePersonnalisee, 0 , SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, chargerPartie, 0 , SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tutoriel, 0 , SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, menu, 0 , SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, menu, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, regles, -30, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, regles, -30, SpringLayout.SOUTH, this);

        //Par rapport aux elements
        layout.putConstraint(SpringLayout.NORTH, partiePersonnalisee, 20,  SpringLayout.SOUTH, partieRapide);
        layout.putConstraint(SpringLayout.NORTH, chargerPartie, 20,  SpringLayout.SOUTH, partiePersonnalisee);
        layout.putConstraint(SpringLayout.NORTH, tutoriel, 20,  SpringLayout.SOUTH, chargerPartie);
        layout.putConstraint(SpringLayout.NORTH, partieRapide, 20, SpringLayout.SOUTH, menu );

        //Commande
        regles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleButtons();
                c.toggleHelp();
            }
        });

        partiePersonnalisee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.switchSel();
            }
        });
    }


    public void toggleButtons() {
        chargerPartie.setEnabled(!chargerPartie.isEnabled());
        tutoriel.setEnabled(!tutoriel.isEnabled());
        partiePersonnalisee.setEnabled(!partiePersonnalisee.isEnabled());
        partieRapide.setEnabled(!partieRapide.isEnabled());

    }

}
