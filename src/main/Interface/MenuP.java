package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class MenuP extends JComponent {
    private JButton partieRapide;
    private JButton partiePersonnalisee;
    private JButton chargerPartie;
    private JButton tutoriel;
    private JButton regles;
    private Image img;
    private SpringLayout layout;
    private  JLabel menu;

    public MenuP(){
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
        layout = new SpringLayout();
        menu = new JLabel(new ImageIcon(img));


        //Ajouts


    }

    private Image reScale(Image source, int x, int y){
        return source.getScaledInstance(x, y, java.awt.Image.SCALE_AREA_AVERAGING);
    }



    @Override
    public void paintComponent(Graphics g){
        Container panel = getRootPane().getContentPane();
        panel.setBackground(new Color(62, 230, 220));
        Color darkBlue = new Color(50, 128, 162);
        //Peinture
        menu.setIcon(new ImageIcon(reScale(img, (int)(panel.getSize().width/2), (int)(panel.getSize().height/(3)))));
        regles.setBackground(darkBlue);
        partieRapide.setBackground(darkBlue);
        partiePersonnalisee.setBackground(darkBlue);
        chargerPartie.setBackground(darkBlue);
        tutoriel.setBackground(darkBlue);

        regles.setForeground(Color.WHITE);
        partieRapide.setForeground(Color.WHITE);
        partiePersonnalisee.setForeground(Color.WHITE);
        chargerPartie.setForeground(Color.WHITE);
        tutoriel.setForeground(Color.WHITE);




        panel.setLayout(layout);
        panel.add(menu);
        panel.add(partieRapide);


        panel.add(partiePersonnalisee);
        panel.add(chargerPartie);
        panel.add(tutoriel);
        panel.add(regles);



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
