package main.Vue;

import main.Model.Jeu;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BanquiseGraphique extends JComponent {

    BufferedImage hexagonePoisson1, hexagonePoisson2, hexagonePoisson3, hexagonePingouinR;

    Jeu j;

    int largeurCase;
    int hauteurCase;



    public BanquiseGraphique(Jeu jeu){
        j = jeu;
        hexagonePoisson1 = chargeImage("hexagone1");
        hexagonePoisson2 = chargeImage("hexagone2");
        hexagonePoisson3 = chargeImage("hexagone3");
        hexagonePingouinR = chargeImage("test");
    }

    private BufferedImage chargeImage(String nom) {
        InputStream in = null;
        try {
            in = new FileInputStream("rsc/images/" + nom + ".png");
        } catch (FileNotFoundException e) {
            System.out.println("Fichier \"" + nom + "\" introuvable");
        }

        try {
            // Chargement d'une image utilisable dans Swing
            return ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("Chargement du fichier \""+nom + "\" impossible");
        }
        return null;
    }


    private void tracer(Graphics2D g, Image i, int x, int y, int l, int h) {
        g.drawImage(i, x, y, l, h, null);
    }


    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        drawable.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawable.setRenderingHint(
                RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        int largeur = getSize().width;
        int hauteur = getSize().height;
        int offsetX = (largeur /20);
        int offsetY = (hauteur /20);
        int colonnes = 8;
        int lignes = 8;
        int radius = Math.min(largeur / colonnes, (hauteur / lignes)) / 2;

        for (int ligne = 0; ligne < lignes; ligne++)
            for (int colonne = 0; colonne < (colonnes+(ligne%2)); colonne++) {
                int x = (int) (colonne*(Math.sqrt(3)*radius) + offsetX);
                if(ligne%2 == 0){
                    x += Math.sqrt((radius*radius) - Math.pow(radius/2, 2));
                }
                int y = (int) (ligne*(((double)3 /2) * radius) + offsetY);
                Polygon hexagon = new Hexagone(new Point((int)x,(int)y), radius).getHexagone();

                BufferedImage bfi = hexagonePoisson3;
//                if(j.getCase(ligne, colonne).estMange()){
//                    bfi = hexagonePingouinR;
//                }else if(j.getCase(ligne, colonne).getNbPoissons() == 1){
//                    bfi = hexagonePoisson1;
//                }else if(j.getCase(ligne, colonne).getNbPoissons() == 2){
//                    bfi = hexagonePoisson2;
//                }else if(j.getCase(ligne, colonne).getNbPoissons() == 3){
//                    bfi = hexagonePoisson3;
//                }
                bfi = getTexturedImage(bfi, hexagon,x, y, radius);

                drawable.drawImage(bfi, x, y, null);
            }
    }


    public static BufferedImage getTexturedImage(BufferedImage src, Polygon shp, int x, int y, int radius){
        Image tmps = src.getScaledInstance((int) (Math.sqrt((radius*radius) - Math.pow(radius/2, 2))*2), (int)radius*2, BufferedImage.SCALE_SMOOTH);
        BufferedImage buffered = new BufferedImage((int) (Math.sqrt((radius*radius) - Math.pow(radius/2, 2))*2), (int)radius*2,BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(tmps, 0, 0, null);

//        Graphics g = buffered.getGraphics();
//        Shape c = g.getClip();
//        g.setClip(shp);
//        g.setClip(c);
//        g.setColor(Color.BLACK);
//        g.dispose();
//
        return buffered;
    }


    int hauteurCase() {
        return hauteurCase;
    }

    int largeurCase() {
        return largeurCase;
    }


    public void misAJour(Jeu jeu){
        j = jeu;
        repaint();
    }






}
