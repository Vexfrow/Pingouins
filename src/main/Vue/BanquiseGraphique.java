package Vue;

import Model.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BanquiseGraphique extends JComponent {

    BufferedImage hexagonePoisson1, hexagonePoisson2, hexagonePoisson3, hexagonePingouinR;

    Jeu j;

    int largeurCase;
    int hauteurCase;

    private List<Shape> cells = new ArrayList<>(6);

    private Shape highlighted;



    public BanquiseGraphique(Jeu jeu){
        j = jeu;
        hexagonePoisson1 = chargeImage("casePoissons1");
        hexagonePoisson2 = chargeImage("casePoissons2");
        hexagonePoisson3 = chargeImage("casePoissons3");
        hexagonePingouinR = chargeImage("pingouinRouge");
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


    public static BufferedImage getTexturedImage(BufferedImage src, Shape shp){
        Rectangle r = shp.getBounds();
        Image tmps = src.getScaledInstance((int)r.getWidth(), (int)r.getHeight(), BufferedImage.SCALE_REPLICATE);
        BufferedImage buffered = new BufferedImage((int)r.getWidth(), (int)r.getHeight(),BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(tmps, 0, 0, null);

        Graphics g = buffered.getGraphics();
        Shape c = g.getClip();
        g.setClip(shp);
        g.setClip(c);
        g.setColor(Color.BLACK);
        g.dispose();

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
        majPlateau();
    }


    @Override
    public void invalidate() {
        super.invalidate();
        majPlateau();
    }

    protected void majPlateau() {

        float largeur = getSize().width;
        float hauteur = getSize().height;
        float offsetX = (largeur /20f);
        float offsetY = (hauteur /20f);

        int colonnes = 8;
        int lignes = 8;

        float size = Math.min((largeur / colonnes), (hauteur / lignes));
        float radius = size/2f;

        cells.clear();

        Shape hexagone = new Hexagone(new Point((int) (offsetX + radius),(int) (offsetY + radius)), radius).getHexagone();

        for (int ligne = 0; ligne < lignes; ligne++) {
            float offset = size / 2f;

            if (ligne % 2 == 0) {
                offset = 0;
                colonnes = 7;
            }
            for (int colonne = 0; colonne < colonnes; colonne++) {
                AffineTransform at = AffineTransform.getTranslateInstance(offset + (colonne * size), ligne * (size * 0.8f));
                Area area = new Area(hexagone);
                area = area.createTransformedArea(at);

                cells.add(area);
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        if (highlighted != null) {
            g2d.setColor(Color.BLUE);
            g2d.fill(highlighted);
        }
        g2d.setColor(Color.BLACK);

        BufferedImage bfi = hexagonePoisson3;

        for (Shape cell : cells) {
            bfi = getTexturedImage(bfi, cell);
            g2d.drawImage(bfi, cell.getBounds().x, cell.getBounds().y, null);
        }
        g2d.dispose();
    }

}
