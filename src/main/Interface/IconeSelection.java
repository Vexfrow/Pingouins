package Interface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IconeSelection extends JPanel {
    public Color color;
    public final Color basiqueVide = new Color(128, 128, 128, 240);
    public int coorX;
    public int coorY;
    public int size;

    private final int MAX = 3;
    private final String VIDE = "Vide";
    private final String HUMAIN = "Humain";
    private final String IA_EASY = "IA Facile";
    private final String IA_MEDIUM = "IA Moyenne";

    private int rotation;

    private JButton gauche;
    private JButton droite;
    private JLabel type;



    public IconeSelection(Color color, int size){

        this.setBackground(GameConstants.BACKGROUND_COLOR);
        this.color = color;
        this.size = size;
        this.rotation = 0;

        Dimension taille = getSize();
        gauche  = new JButton("<--");
        droite = new JButton("-->");
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