package Interface;


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



    private final int MAX = 4;
    public static final String HUMAIN = "Humain";
    public static final String IA_EASY = "IA Facile";
    public static final String IA_MEDIUM = "IA Moyenne";
    public static final String IA_DIFFICILE = "IA Difficile";

    private int rotation;
    private JButton minus;
    private JButton plus;
    private JButton gauche;
    private JButton droite;
    private JLabel type;
    private JLabel empty;
    private Image selGauche;
    private Image selDroite;
    private Image pengouin;
    private Image ajout;
    private Image supp;
    private JLabel icon;




    public IconeSelection(Color color, int size, boolean notInit){
        this.setPreferredSize(new Dimension(size*2, size*2));
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        this.setLayout(new GridBagLayout());

        this.color = color;
        final int r = GameConstants.ROUGE.getRGB();
        final int g = GameConstants.VERT.getRGB();
        int b = GameConstants.BLEU.getRGB();
        int y = GameConstants.JAUNE.getRGB();
        this.size = size;
        this.rotation = 0;

        try{
            if(this.color.getRGB() == r){
                pengouin = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/pingouinRouge.png"));
            }else if(this.color.getRGB() == g){
                pengouin = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/pingouinVert.png"));
            }else if(this.color.getRGB() == b){
                pengouin = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/pingouinBleu.png"));
            }else if(this.color.getRGB() == y){
                pengouin = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/pingouinJaune.png"));
            }
            selGauche = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheSelectGauche.png"));
            selDroite = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheSelectDroite.png"));
            ajout = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/plus.png"));
            supp = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/croix.png"));

        }catch(Exception e){
            System.out.println("une erreur " + e);
        }

        Dimension taille = getSize();
        gauche  = new JButton(new ImageIcon(selGauche));
        droite = new JButton(new ImageIcon(selDroite));
        icon = new JLabel(new ImageIcon(pengouin));
        plus = new JButton(new ImageIcon(ajout));
        minus= new JButton(new ImageIcon(supp));

        plus.setBorderPainted(false);
        minus.setBorderPainted(false);

        plus.setContentAreaFilled(false);
        minus.setContentAreaFilled(false);
        Icon ic = new ImageIcon(supp);
        minus.setPreferredSize(new Dimension(ic.getIconWidth(), ic.getIconHeight()));

        gauche.setBackground(new Color(0x2678A7));
        gauche.setForeground(Color.WHITE);
        gauche.setPreferredSize(new Dimension(50, 30));
        droite.setBackground(new Color(0x2678A7));
        droite.setForeground(Color.WHITE);
        droite.setPreferredSize(new Dimension(50, 30));

        type = new JLabel(HUMAIN, SwingConstants.CENTER);
        type.setPreferredSize(new Dimension(80, 30));
        type.setBackground(Color.WHITE);
        type.setOpaque(true);

        empty = new JLabel(new ImageIcon(supp));
        empty.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(size, 0, size, 0);

        //add(empty,gbc);

        gbc.gridy = 0;
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        this.add(minus, gbc);
        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(plus, gbc);


        gbc.insets = new Insets(size, 0, size, 0);
        gbc.gridy = 3;
        gbc.gridx = 1;
        this.add(gauche, gbc);

        gbc.gridx = 3;
        this.add(droite, gbc);

        gbc.gridx = 2;

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
        }else{
            actif = true;
            this.plus.setEnabled(false);
            this.plus.setVisible(false);
        }



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
                selection();
            }
        });

        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection();
            }
        });


    }

    public IconeSelection(Color color, int size, boolean notInit, Dimension dim){
        this(color, size,notInit);
        setPreferredSize(dim);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        type.setBackground(Color.WHITE);

        Polygon p = new Polygon();
        //Dessin du polygone
        Dimension taille = getSize();
        System.out.println(taille);
        this.coorX = taille.width/2;
        this.coorY = taille.height/2 ;

        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i +30;
            angle_rad = ((Math.PI / 180) * angle_deg);
            p.addPoint((int) (coorX + size*Math.cos(angle_rad)), (int)(coorY + size * Math.sin(angle_rad)) );
        }
        g.setColor(this.color);
        g.fillPolygon(p);

        //g.drawRect(0,0, this.getWidth(), this.getHeight());
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
        this.minus.setEnabled(!this.minus.isEnabled());
        this.minus.setVisible(!this.type.isVisible());
        this.type.setEnabled(!this.type.isEnabled());
        this.type.setVisible(!this.type.isVisible());
    }

    public boolean isActif(){
        return actif;
    }
}
