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
    private JButton quitter;
    private JButton regles;
    private ImageIcon titreS, hintS, partieRapideS, partiePersoS, chargerPartieS, quitterS;
    private SpringLayout layout;
    private  JLabel menu;
    private CollecteurEvenements c;
    private boolean hover;

    public MenuP(CollecteurEvenements ctrl){
        this.c = ctrl;
        hover = false;

        partiePersonnalisee = new JButton();
        partieRapide = new JButton();
        chargerPartie = new JButton();
        quitter = new JButton();
        regles = new JButton();

        menu = new JLabel();
        this.setLayout(new GridBagLayout());

        ImageIcon c = new ImageIcon(GameConstants.regles);
        regles.setPreferredSize(new Dimension(c.getIconWidth(), c.getIconHeight() ));
        titreS = new ImageIcon(GameConstants.titre);
        hintS = new ImageIcon(GameConstants.regles);
        partieRapideS = new ImageIcon(GameConstants.partieRapide);
        partiePersoS = new ImageIcon(GameConstants.partiePerso);
        chargerPartieS = new ImageIcon(GameConstants.chargerPartie);
        quitterS = new ImageIcon(GameConstants.quitter);
        setMenu();



    }

    private void setMenu(){

        //Button
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        quitter.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        regles.setBorderPainted(false);
        regles.setContentAreaFilled(false);


        menu.setIcon(titreS);

        partieRapide.setForeground(Color.WHITE);
        partiePersonnalisee.setForeground(Color.WHITE);
        chargerPartie.setForeground(Color.WHITE);
        quitter.setForeground(Color.WHITE);

        regles.setBorderPainted(false);
        partieRapide.setBorderPainted(false);
        partiePersonnalisee.setBorderPainted(false);
        chargerPartie.setBorderPainted(false);
        quitter.setBorderPainted(false);

        regles.setContentAreaFilled(false);
        partieRapide.setContentAreaFilled(false);
        partiePersonnalisee.setContentAreaFilled(false);
        chargerPartie.setContentAreaFilled(false);
        quitter.setContentAreaFilled(false);

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
        this.add(quitter ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
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
               //c.startGame();
            }
        });

        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.toggelCharge(true);
                toggleButtons();
            }
        });

        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.close();
            }
        });


        partiePersonnalisee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, GameConstants.partiePerso, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, GameConstants.partiePerso)));
                hover = false;
            }
        });

        partieRapide.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, GameConstants.partieRapide, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, GameConstants.partieRapide)));
                hover = false;
            }
        });

        chargerPartie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, GameConstants.chargerPartie, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, GameConstants.chargerPartie)));
                hover = false;
            }
        });

        quitter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                quitter.setIcon(new ImageIcon(imageOnButton(quitter, GameConstants.quitter, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                quitter.setIcon(new ImageIcon(imageOnButton(quitter, GameConstants.quitter)));
                hover = false;
            }
        });

        regles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                regles.setIcon(new ImageIcon(imageOnButton(regles, GameConstants.regles, 0.9, 0.9)));
                hover = true;
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                regles.setIcon(new ImageIcon(imageOnButton(regles, GameConstants.regles)));
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
        quitter.setEnabled(!quitter.isEnabled());
        partiePersonnalisee.setEnabled(!partiePersonnalisee.isEnabled());
        partieRapide.setEnabled(!partieRapide.isEnabled());
        regles.setEnabled(!regles.isEnabled());
        //regles.setOpaque(!regles.isOpaque());
        hover = false;
    }

    public void activateButton(){
        chargerPartie.setEnabled(true);
        quitter.setEnabled(true);
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
        quitter.setPreferredSize(scaleButton(getWidth(), getHeight()));
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
        menu.setIcon(new ImageIcon(reScale(GameConstants.titre)));

        regles.setIcon(new ImageIcon(imageOnButton(regles, GameConstants.regles)));
        partieRapide.setIcon(new ImageIcon(imageOnButton(partieRapide, GameConstants.partieRapide)));
        partiePersonnalisee.setIcon(new ImageIcon(imageOnButton(partiePersonnalisee, GameConstants.partiePerso)));
        chargerPartie.setIcon(new ImageIcon(imageOnButton(chargerPartie, GameConstants.chargerPartie)));
        quitter.setIcon(new ImageIcon(imageOnButton(quitter, GameConstants.quitter)));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!hover){
            maj();
        }
        g.drawImage(GameConstants.fondMenu,0, 0,this.getWidth(), this.getHeight(), this);

    }



}
