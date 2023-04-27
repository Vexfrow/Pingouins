package Interface;

import Controleur.Controleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;

public class MenuP extends JComponent {
    private Controleur c;
    private JButton partieRapide;
    private JButton partiePersonnalisee;
    private JButton chargerPartie;
    private JButton tutoriel;
    private JButton regles;
    private Image img;
    private SpringLayout layout;
    private  JLabel menu;

    public MenuP(Controleur c){
        //Création des éléments
        try{
            img = (Image)ImageIO.read(new FileInputStream("resource/assets/menu/Titre.png"));
        }catch(Exception e){
            System.out.println(e);
        }
        partiePersonnalisee = new JButton("Partie Personnalisée");
        partieRapide = new JButton("Partie Rapide");
        chargerPartie = new JButton("Charger Partie");
        tutoriel = new JButton("Tutoriel");
        regles = new JButton("Règles");

        prepareBouton();
        layout = new SpringLayout();
        menu = new JLabel(new ImageIcon(img));
        this.c = c;

        //Ajouts


    }

    public void prepareBouton(){
        regles.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Je me lance");
                c.demarrerRegles();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private Image reScale(Image source, int x, int y){
        return source.getScaledInstance(x, y, java.awt.Image.SCALE_AREA_AVERAGING);
    }



    @Override
    public void paintComponent(Graphics g){
        Container panel = getRootPane().getContentPane();



        //Peinture
        Color reglesColor = new Color(0xFDCF76);
        Color partieRapideColor = new Color(0x155D85);
        Color partiPersonnaliseeColor = new Color(0x2678A7);
        Color chargerPartieColor = new Color(0x4D88A9);
        Color tutorielColor = new Color(0x7292A4);


        panel.setBackground(new Color(0x88C9D1));
        menu.setIcon(new ImageIcon(reScale(img, (int)(panel.getSize().width/2), (int)(panel.getSize().height/(3)))));
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
        panel.setLayout(layout);
        panel.add(menu);
        panel.add(partieRapide);
        panel.add(partiePersonnalisee);
        panel.add(chargerPartie);
        panel.add(tutoriel);
        panel.add(regles);

        //Fonctions sur les boutons



        //Redimensionnement
        Dimension boutonTaille = new Dimension(200, 50);
        partieRapide.setPreferredSize(boutonTaille);
        partiePersonnalisee.setPreferredSize(boutonTaille);
        chargerPartie.setPreferredSize(boutonTaille);
        tutoriel.setPreferredSize(boutonTaille);


        //Positionnement
        //Par rapport a la fenetre
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, partieRapide, 0 , SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, partiePersonnalisee, 0 , SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, chargerPartie, 0 , SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tutoriel, 0 , SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, menu, 0 , SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, menu, 20, SpringLayout.NORTH, panel);

        //Par rapport aux elements
        layout.putConstraint(SpringLayout.EAST, regles, -30, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, regles, -30, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.NORTH, partiePersonnalisee, 20,  SpringLayout.SOUTH, partieRapide);
        layout.putConstraint(SpringLayout.NORTH, chargerPartie, 20,  SpringLayout.SOUTH, partiePersonnalisee);
        layout.putConstraint(SpringLayout.NORTH, tutoriel, 20,  SpringLayout.SOUTH, chargerPartie);
        layout.putConstraint(SpringLayout.NORTH, partieRapide, 20, SpringLayout.SOUTH, menu );



    }
}
