package Interface;

import Model.Jeu;
import Model.Joueur;
import Vue.CollecteurEvenements;
import Joueur.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MenuP extends JPanel {
    private JButton partieRapide;
    private JButton partiePersonnalisee;
    private JButton chargerPartie;
    private JButton tutoriel;
    private JButton regles;
    private Image titre;
    private Image hint;
    private ImageIcon titreS;
    private ImageIcon hintS;
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;

    public MenuP(CollecteurEvenements ctrl){
        super();
        this.c = ctrl;
        //Création des éléments
        try{
            titre = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/Titre.png"));
            hint = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/boutonRegles.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        partiePersonnalisee = new JButton("Partie Personnalisée");
        partieRapide = new JButton("Partie Rapide");
        chargerPartie = new JButton("Charger Partie");
        tutoriel = new JButton("Tutoriel");
        regles = new JButton();

        layout = new SpringLayout();
        menu = new JLabel();
        this.setLayout(layout);

        ImageIcon c = new ImageIcon(hint);
        System.out.println(c.getIconWidth() + " h -> " + c.getIconHeight());
        regles.setPreferredSize(new Dimension(c.getIconWidth(), c.getIconHeight() ));
        titreS = new ImageIcon(titre);
        hintS = new ImageIcon(hint);
        setMenu();



    }




    private Image reScale(Image source, int x, int y){
        return source.getScaledInstance(x, y, java.awt.Image.SCALE_AREA_AVERAGING);
    }

    private Image reScale(Image source, float percent){
        ImageIcon ic = new ImageIcon(source);

        return source.getScaledInstance((int)(ic.getIconWidth()*percent), (int)(ic.getIconHeight()*percent), java.awt.Image.SCALE_AREA_AVERAGING);
    }

    private void setMenu(){
        //Button
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        tutoriel.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        regles.setBorderPainted(false);
        regles.setContentAreaFilled(false);


        //Colorization
        Color reglesColor = new Color(0xFDCF76);
        Color partieRapideColor = new Color(0x155D85);
        Color partiPersonnaliseeColor = new Color(0x2678A7);
        Color chargerPartieColor = new Color(0x4D88A9);
        Color tutorielColor = new Color(0x7292A4);

        this.setBackground(GameConstants.BACKGROUND_COLOR);
        menu.setIcon(titreS);
        regles.setIcon(hintS);
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
                c.toggleHelp(true);
                toggleButtons();
            }
        });

        partiePersonnalisee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.switchSel();
            }
        });

        partieRapide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Joueur> ar = new ArrayList<Joueur>();
                ar.add(new Joueur(1,0,0,false));
                ar.add(new Joueur(2,0,0,true));
                Jeu j = new Jeu(ar);
                ArrayList<IAJoueur> arj = new ArrayList<IAJoueur>();
                arj.add(null);
                arj.add(new IAAleatoire(j));

                c.newGame(j, arj, ar);
                c.switchGameBoard();
            }
        });

        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.toggelCharge(true);
                toggleButtons();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                titreS = new ImageIcon(reScale(titre));
                menu.setIcon(titreS);
            }
        });
    }


    public void toggleButtons() {
        chargerPartie.setEnabled(!chargerPartie.isEnabled());
        tutoriel.setEnabled(!tutoriel.isEnabled());
        partiePersonnalisee.setEnabled(!partiePersonnalisee.isEnabled());
        partieRapide.setEnabled(!partieRapide.isEnabled());
        regles.setEnabled(!regles.isEnabled());
        //regles.setOpaque(!regles.isOpaque());
    }

    public void activateButton(){
        chargerPartie.setEnabled(true);
        tutoriel.setEnabled(true);
        partiePersonnalisee.setEnabled(true);
        partieRapide.setEnabled(true);
        regles.setEnabled(true);
    }

    public Image reScale(Image img){
        Dimension d = new Dimension((int)(getWidth()*0.45), (int)(getHeight()*0.3) );
        Image neoImg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH) ;
        return neoImg;
    }

    public Dimension scaleButton(int x,int y){
        return new Dimension((int)(x*0.2), (int)(y * 0.12));
    }

    public void allScale(){
        partieRapide.setPreferredSize(scaleButton(getWidth(), getHeight()));
        partiePersonnalisee.setPreferredSize(scaleButton(getWidth(), getHeight()));
        chargerPartie.setPreferredSize(scaleButton(getWidth(), getHeight()));
        tutoriel.setPreferredSize(scaleButton(getWidth(), getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        allScale();
        System.out.println("Here Menu");
    }

}
