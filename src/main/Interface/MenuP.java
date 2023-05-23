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
    private Image titreI, hintI, partieRapideI, partiePersoI, chargerPartieI, tutorielI, fond;
    private ImageIcon titreS, hintS, partieRapideS, partiePersoS, chargerPartieS, tutorielS;
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;
    private boolean hover;

    public MenuP(CollecteurEvenements ctrl){
        this.c = ctrl;
        hover = false;
        //Création des éléments
        try{
            fond = ImageIO.read(new FileInputStream("resources/assets/menu/fondMenu.png"));
            titreI = ImageIO.read(new FileInputStream("resources/assets/menu/Titre.png"));
            hintI = ImageIO.read(new FileInputStream("resources/assets/menu/boutonRegles.png"));
            partieRapideI = ImageIO.read(new FileInputStream("resources/assets/menu/boutonPartieRapide.png"));
            partiePersoI = ImageIO.read(new FileInputStream("resources/assets/menu/boutonPartiePerso.png"));
            chargerPartieI = ImageIO.read(new FileInputStream("resources/assets/menu/boutonChargerPartie.png"));
            tutorielI = ImageIO.read(new FileInputStream("resources/assets/menu/boutonTuto.png"));
        }catch(Exception e){
            System.err.println("une erreur " + e);
        }
        partiePersonnalisee = new JButton();
        partieRapide = new JButton();
        chargerPartie = new JButton();
        tutoriel = new JButton();
        regles = new JButton();

        menu = new JLabel();
        this.setLayout(new GridBagLayout());

        ImageIcon c = new ImageIcon(hintI);
        regles.setPreferredSize(new Dimension(c.getIconWidth(), c.getIconHeight() ));
        titreS = new ImageIcon(titreI);
        hintS = new ImageIcon(hintI);
        partieRapideS = new ImageIcon(partieRapideI);
        partiePersoS = new ImageIcon(partiePersoI);
        chargerPartieS = new ImageIcon(chargerPartieI);
        tutorielS = new ImageIcon(tutorielI);
        setMenu();



    }

    private void setMenu(){

        //Button
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        tutoriel.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        regles.setBorderPainted(false);
        regles.setContentAreaFilled(false);


        menu.setIcon(titreS);

        partieRapide.setForeground(Color.WHITE);
        partiePersonnalisee.setForeground(Color.WHITE);
        chargerPartie.setForeground(Color.WHITE);
        tutoriel.setForeground(Color.WHITE);

        regles.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        tutoriel.setBorderPainted(false);

        regles.setContentAreaFilled(false);
        partieRapide.setContentAreaFilled(false);
        partiePersonnalisee.setContentAreaFilled(false);
        chargerPartie.setContentAreaFilled(false);
        tutoriel.setContentAreaFilled(false);

        //Ajouts
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0);
        this.add(menu, gbc);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(partieRapide,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(partiePersonnalisee, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(chargerPartie,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 0);
        this.add(tutoriel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(regles,gbc);

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
                Settings s = new Settings();
                ArrayList<Joueur> ar = s.getJoueur();
                Jeu j = new Jeu(ar);
                ArrayList<IAJoueur> arj = s.getTypes(j);
                c.newGame(j, arj, ar);
                c.switchGameBoard();
                c.startGame();
            }
        });

        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.toggelCharge(true);
                toggleButtons();
            }
        });


        partiePersonnalisee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, partiePersoI, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, partiePersoI)));
                hover = false;
            }
        });

        partieRapide.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, partieRapideI, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, partieRapideI)));
                hover = false;
            }
        });

        chargerPartie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, chargerPartieI, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, chargerPartieI)));
                hover = false;
            }
        });

        tutoriel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                tutoriel.setIcon(new ImageIcon(imageOnButton(tutoriel, tutorielI, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                tutoriel.setIcon(new ImageIcon(imageOnButton(tutoriel, tutorielI)));
                hover = false;
            }
        });

        regles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                regles.setIcon(new ImageIcon(imageOnButton(regles, hintI, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                regles.setIcon(new ImageIcon(imageOnButton(regles, hintI)));
                hover = false;
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                maj();
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
        hover = false;
    }

    public void activateButton(){
        chargerPartie.setEnabled(true);
        tutoriel.setEnabled(true);
        partiePersonnalisee.setEnabled(true);
        partieRapide.setEnabled(true);
        regles.setEnabled(true);
        hover = false;
    }

    public Image reScale(Image img){
        Dimension d = new Dimension((int)(getWidth()*0.5), (int)(getHeight()*0.35) );
        Image neoImg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH) ;
        return neoImg;
    }

    public Dimension scaleButton(int x,int y){
        return new Dimension((int)(x*0.18), (int)(y * 0.10));
    }

    public void allScale(){
        partieRapide.setPreferredSize(scaleButton(getWidth(), getHeight()));
        partiePersonnalisee.setPreferredSize(scaleButton(getWidth(), getHeight()));
        chargerPartie.setPreferredSize(scaleButton(getWidth(), getHeight()));
        tutoriel.setPreferredSize(scaleButton(getWidth(), getHeight()));
    }

    public Image imageOnButton(JButton b, Image img){
        return img.getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
    }

    public Image imageOnButton(JButton b, Image img, double x, double y){
        return img.getScaledInstance((int)(b.getWidth()*x), (int)(b.getHeight()*y), Image.SCALE_SMOOTH);
    }

    public void iconfied(){
        maj();
    }

    private void maj(){
        allScale();
        menu.setIcon(new ImageIcon(reScale(titreI)));

        regles.setIcon(new ImageIcon(imageOnButton(regles, hintI)));
        partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, partieRapideI)));
        partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, partiePersoI)));
        chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, chargerPartieI)));
        tutoriel.setIcon(new ImageIcon(imageOnButton(tutoriel, tutorielI)));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!hover){
            maj();
        }
        g.drawImage(fond,0, 0,this.getWidth(), this.getHeight(), this);

    }



}
