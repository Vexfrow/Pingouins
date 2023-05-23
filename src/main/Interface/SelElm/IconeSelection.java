package Interface.SelElm;


import Interface.GameConstants;
import Interface.Selection;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;

public class IconeSelection extends JPanel {
    public Color color;
    public final Color basiqueVide = new Color(128, 128, 128, 240);
    public int coorX;
    public int coorY;
    public int size;
    private boolean actif;



    private final int MAX = 5;
    public static final String HUMAIN = "Humain";
    public static final String IA_EASY = "IA Facile";
    public static final String IA_MEDIUM = "IA Moyenne";
    public static final String IA_DIFFICILE = "IA Difficile";
    public static final String IA_EXPERTE = "IA Experte";

    private int rotation;
    private JButton minus;
    private JButton plus;
    private JButton gauche;
    private JButton droite;
    private JLabel type;
    private JLabel empty;
    private Image pengouin;
    private JLabel icon;
    private Icone centre;
    private boolean fixe;
    private int indice;
    private Selection sel;




    public IconeSelection(Color color, int size, boolean notInit, Selection s, int i){
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        this.setLayout(new GridBagLayout());
        this.sel = s;
        this.color = color;
        final int r = GameConstants.ROUGE.getRGB();
        final int g = GameConstants.VERT.getRGB();
        int b = GameConstants.BLEU.getRGB();
        int y = GameConstants.JAUNE.getRGB();
        this.size = size;
        this.rotation = 1;

        this.indice = i;
        if(this.color.getRGB() == r){
            pengouin = GameConstants.pingouinBleu;
        }else if(this.color.getRGB() == g){
            pengouin = GameConstants.pingouinRouge;
        }else if(this.color.getRGB() == b){
            pengouin = GameConstants.pingouinVert;
        }else if(this.color.getRGB() == y){
            pengouin = GameConstants.pingouinJaune;
        }

        this.centre = new Icone(indice);

        gauche  = new JButton(new ImageIcon(GameConstants.flecheGaucheSelection));
        droite = new JButton(new ImageIcon(GameConstants.flecheDroiteSelection));
        icon = new JLabel(new ImageIcon(pengouin));
        plus = new JButton(new ImageIcon(GameConstants.plus));
        minus= new JButton(new ImageIcon(GameConstants.croix));

        plus.setBorderPainted(false);
        minus.setBorderPainted(false);

        plus.setContentAreaFilled(false);
        minus.setContentAreaFilled(false);
        Icon ic = new ImageIcon(GameConstants.croix);
        minus.setPreferredSize(new Dimension(ic.getIconWidth(), ic.getIconHeight()));

        gauche.setBackground(new Color(0x2678A7));
        gauche.setForeground(Color.WHITE);
        droite.setBackground(new Color(0x2678A7));
        droite.setForeground(Color.WHITE);

        type = new JLabel(HUMAIN, SwingConstants.CENTER);
        type.setBackground(Color.WHITE);
        type.setOpaque(true);

        empty = new JLabel(new ImageIcon(GameConstants.croix));
        empty.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.3;
        this.add(minus, gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        this.add(plus, gbc);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        this.add(centre,gbc);



        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 20, 0, 0);
        this.add(gauche, gbc);
        gbc.insets = new Insets(0, 0, 0, 20);
        gbc.gridx = 2;
        this.add(droite, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.weighty = 1;
        gbc.weightx = 0.4;
        this.add(type, gbc);


        if(notInit){

            actif =false;
            this.droite.setEnabled(false);
            this.droite.setVisible(false);
            this.gauche.setEnabled(false);
            this.gauche.setVisible(false);
            this.minus.setEnabled(false);
            this.minus.setVisible(false);
            this.type.setEnabled(false);
            this.type.setVisible(false);
            if(indice == 3){
                this.plus.setEnabled(false);
                //this.plus.setVisible(false);
            }else{
                this.plus.setEnabled(true);
                //this.plus.setVisible(true);
            }


        }else{
            actif = true;
            this.plus.setEnabled(false);
            this.plus.setVisible(false);
        }

        centre.setActif(actif);


        gauche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotationNom(-1);
            }
        });

        droite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotationNom(1);
            }
        });

        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.ajout();
                selection();
                centre.setActif(actif);
                s.refresh();
                centre.refresh();


            }
        });

        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection();
                s.del();
                centre.setActif(actif);
                s.refresh();
                centre.refresh();

            }
        });
    }



    public void paintComponent(Graphics g){

        type.setPreferredSize(new Dimension((int)(getWidth()*0.20), (int)(getHeight()*0.09)));
        type.revalidate();
        gauche.setPreferredSize(new Dimension((int)(getWidth()*0.1),(int)(getHeight()*0.09)));
        droite.setPreferredSize(new Dimension((int)(getWidth()*0.1), (int)(getHeight()*0.09)));
        type.setBackground(Color.WHITE);
        Polygon p = new Polygon();
        Dimension taille = getSize();
        this.coorX = taille.width/2;
        this.coorY = taille.height/2 ;
        this.size = getHeight()/6;
        centre.setSize(size);
        centre.setColor(color);
        super.paintComponent(g);
    }

    public void rotationNom(int i){
        rotation += i;
        if(rotation > MAX ){
            rotation = 1;
        }else if(rotation < 1){
            rotation = MAX;
        }
        String res = "";
        switch(rotation){
            case 1:
                res = HUMAIN;
                break;
            case 2:
                res = IA_EASY;
                break;
            case 3:
                res = IA_MEDIUM;
                break;
            case 4:
                res = IA_DIFFICILE;
                break;
            case 5:
                res = IA_EXPERTE;
                break;
            default:
                res= "Erreur";
                break;
        }
        System.err.println(res);
        type.setText(res);
    }

    public String getName(){
        String res = "";
        res = type.getText();
        return res;
    }

    public void selection(){
        actif = !actif;
        this.plus.setEnabled(!this.plus.isEnabled());
        this.plus.setVisible(!this.plus.isVisible());
        this.droite.setEnabled(!this.droite.isEnabled());
        this.droite.setVisible(!this.type.isVisible());
        this.gauche.setEnabled(!this.gauche.isEnabled());
        this.gauche.setVisible(!this.type.isVisible());
        this.type.setEnabled(!this.type.isEnabled());
        this.type.setVisible(!this.type.isVisible());
    }

    public boolean isActif(){
        return actif;
    }

    public void maj(){
        if(indice < 2){
            minus.setEnabled(false);
        }else{
            if(actif ){
                if(indice == sel.getLast()){
                    minus.setEnabled(true);
                    minus.setVisible(true);
                }else{
                    minus.setEnabled(false);
                    //minus.setVisible(false);
                }
            }else{
                if(indice == (sel.getLast()+1)){
                    plus.setEnabled(true);
                    plus.setVisible(true);
                    minus.setVisible(false);
                }else{
                    plus.setEnabled(false);
                    plus.setVisible(false);
                    minus.setVisible(false);
                }
            }
        }


    }

}
