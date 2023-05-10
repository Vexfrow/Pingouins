package Interface;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Interface.GameConstants;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import java.io.FileInputStream;

public class IconeSelection extends JPanel {
    public Color color;
    public final Color basiqueVide = new Color(128, 128, 128, 240);
    public int coorX;
    public int coorY;
    public int size;


    private final int MAX = 3;
    public static final String VIDE = "Vide";
    public static final String HUMAIN = "Humain";
    public static final String IA_EASY = "IA Facile";
    public static final String IA_MEDIUM = "IA Moyenne";

    private int rotation;

    private JButton gauche;
    private JButton droite;
    private JLabel type;
    private Image pengouin;
    private JLabel icon;



    public IconeSelection(Color color, int size){

        this.setBackground(GameConstants.BACKGROUND_COLOR);
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

        }catch(Exception e){
            System.out.println("une erreur " + e);
        }

        Dimension taille = getSize();
        gauche  = new JButton("<--");
        droite = new JButton("-->");
        icon = new JLabel(new ImageIcon(pengouin));
        gauche.setBackground(new Color(0x2678A7));
        gauche.setForeground(Color.WHITE);
        gauche.setPreferredSize(new Dimension(50, 30));
        droite.setBackground(new Color(0x2678A7));
        droite.setForeground(Color.WHITE);
        droite.setPreferredSize(new Dimension(50, 30));

        type = new JLabel(VIDE, SwingConstants.CENTER);
        type.setPreferredSize(new Dimension(80, 30));
        type.setBackground(Color.WHITE);
        type.setOpaque(true);

        //this.add(icon);
        this.add(gauche);
        this.add(droite);
        this.add(type);


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


    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        type.setBackground(Color.WHITE);

        //Dessin du polygone
        Polygon p = new Polygon();
        Dimension taille = getSize();
        this.coorX = taille.width/2;
        this.coorY = taille.height/2 - 100;
/*
        Icon icon = new ImageIcon(pengouin);
        JLabel j = new JLabel(icon);
        add(j);
        j.setLocation(coorX, coorY);

 */



        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i +30;
            angle_rad = ((Math.PI / 180) * angle_deg);
            p.addPoint((int) (coorX + size*Math.cos(angle_rad)), (int)(coorY + size * Math.sin(angle_rad)) );
        }
        g.setColor(this.color);
        g.fillPolygon(p);

        //Bouton a choix
        int ecart = 60;
        int y = coorY + size*2;
        int x1 = coorX - ecart -30;
        int x2 = coorX + 40;
        gauche.setLocation(x1, y);
        droite.setLocation(x2, y);

        type.setLocation(x1+gauche.getSize().width, y);
        if(type.getText().equals(VIDE)){
            survole(g);
        }
    }



    public void rotationNom(int i){
        rotation += i;
        if(rotation > MAX ){
            rotation = 0;
        }else if(rotation < 0){
            rotation = MAX;
        }
        String res = "";
        switch(rotation){
            case 0:
                res= VIDE;
                break;
            case 1:
                res = HUMAIN;
                break;
            case 2:
                res = IA_EASY;
                break;
            case 3:
                res = IA_MEDIUM;

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
        switch(rotation) {
            case 0:
                res = VIDE;
                break;
            case 1:
                res = HUMAIN;
                break;
            case 2:
                res = IA_EASY;
                break;
            case 3:
                res = IA_MEDIUM;
                break;
            default:
                res = "Erreur";
                break;
        }
        return res;
    }


    public void survole(Graphics g){
        Polygon p = new Polygon();
        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i +30;
            angle_rad = ((Math.PI / 180) * angle_deg);
            p.addPoint((int) (coorX + size*Math.cos(angle_rad)), (int)(coorY + size * Math.sin(angle_rad)) );
        }
        g.setColor(this.basiqueVide);
        g.fillPolygon(p);
    }
}
