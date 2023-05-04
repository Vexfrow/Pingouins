package Vue;

import Model.Jeu;
import Model.Cases;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BanquiseGraphique extends JComponent {

    BufferedImage hPoisson1, hPoisson2, hPoisson3, hVide;
    BufferedImage hPingouinR, hPingouinR1, hPingouinR2, hPingouinR3;
    BufferedImage hPingouinB, hPingouinB1, hPingouinB2, hPingouinB3;
    BufferedImage hPingouinV, hPingouinV1, hPingouinV2, hPingouinV3;
    BufferedImage hPingouinJ, hPingouinJ1, hPingouinJ2, hPingouinJ3;

    private Jeu jeu;
    private List<Shape> grille;

    public BanquiseGraphique(Jeu jeu) {
        this.jeu = jeu;

        //Todo : trouver une meilleure manière que charger toutes les images directement
        hPoisson1 = chargeImage("casePoissons1");
        hPoisson2 = chargeImage("casePoissons2");
        hPoisson3 = chargeImage("casePoissons3");
        hVide = chargeImage("caseVide");

        hPingouinR = chargeImage("caseRouge");
        hPingouinR1 = chargeImage("caseRouge1");
        hPingouinR2 = chargeImage("caseRouge2");
        hPingouinR3 = chargeImage("caseRouge3");

        hPingouinV = chargeImage("caseVert");
        hPingouinV1 = chargeImage("caseVert1");
        hPingouinV2 = chargeImage("caseVert2");
        hPingouinV3 = chargeImage("caseVert3");

        hPingouinB = chargeImage("caseBleu");
        hPingouinB1 = chargeImage("caseBleu1");
        hPingouinB2 = chargeImage("caseBleu2");
        hPingouinB3 = chargeImage("caseBleu3");

        hPingouinJ = chargeImage("caseJaune");
        hPingouinJ1 = chargeImage("caseJaune1");
        hPingouinJ2 = chargeImage("caseJaune2");
        hPingouinJ3 = chargeImage("caseJaune3");


        grille = new ArrayList<>(60);
    }

    private BufferedImage chargeImage(String nom) {
        try {
            InputStream in = new FileInputStream("resources/assets/plateau/" + nom + ".png");
            return ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("Fichier \"" + nom + "\" introuvable");
        }
        return null;
    }


    //Todo : Verifier l'utilité de cette fonction
    private void tracer(Graphics2D g, Image i, int x, int y, int l, int h) {
        g.drawImage(i, x, y, l, h, null);
    }


    public static BufferedImage getTexturedImage(BufferedImage src, Shape shp) {
        Rectangle r = shp.getBounds();

        //On récupère une version redimensionnée de l'image
        Image imageTmp = src.getScaledInstance((int) r.getWidth(), (int) r.getHeight(), BufferedImage.SCALE_REPLICATE);
        //On crée une nouvelle image bufferisé
        BufferedImage buffered = new BufferedImage((int) r.getWidth(), (int) r.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //On remplace la nouvelle image par la version redimensionnée de l'image que l'on souhaite mettre
        buffered.getGraphics().drawImage(imageTmp, 0, 0, null);
        src.getGraphics().dispose();

        //TODO : Verifier l'utilité de ce bout de code
//        Graphics g = buffered.getGraphics();
//        Shape c = g.getClip();
//        g.setClip(shp);
//        g.setClip(c);
//        g.setColor(Color.BLACK);
//        g.dispose();

        return buffered;
    }


    public void misAJour(Jeu jeu) {
        this.jeu = jeu;
        //majPlateau();
        repaint();
    }


    @Override
    public void invalidate() {
        super.invalidate();
        majPlateau();
    }

    protected void majPlateau() {

        float largeur = getSize().width;
        float hauteur = getSize().height;
        float offsetX = (largeur / 10f);
        float offsetY = (hauteur / 20f);

        int colonnes = 8;
        int lignes = 8;

        float size = Math.min(((largeur - (offsetX * 2)) / colonnes), ((hauteur - (offsetY * 2)) / lignes / 0.9f));
        float radius = size / 2f;

        grille.clear();

        Shape hexagone = new Hexagone(new Point((int) (offsetX + radius), (int) (offsetY + radius)), radius).getHexagone();

        for (int ligne = 0; ligne < lignes; ligne++) {
            float offset = 0;
            colonnes = 8;

            if (ligne % 2 == 0) {
                offset = size / 2;
                colonnes = 7;
            }
            for (int colonne = 0; colonne < colonnes; colonne++) {
                AffineTransform at = AffineTransform.getTranslateInstance((colonne * size) + offset, ligne * size * 0.9f);
                Area area = new Area(hexagone);
                area = area.createTransformedArea(at);

                grille.add(area);
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        BufferedImage bfi = hVide;

        int i = 0;
        int j = 0;
        for (Shape cell : grille) {
//            g2d.setColor(getColor(jeu.getCase(i, j)));
//            g2d.fill(cell);
//
//            g2d.draw(cell);

            bfi = getTexturedImage(getBfi(jeu.getCase(i, j)), cell);
            g2d.drawImage(bfi, cell.getBounds().x, cell.getBounds().y, null);


            j++;

            if (i % 2 == 0) {
                if (j >= 7) {
                    i = i + 1;
                    j = 0;
                }
            } else {
                if (j >= 8) {
                    i = i + 1;
                    j = 0;
                }
            }
        }

        g2d.dispose();
    }


    private BufferedImage getBfi(Cases c) {
        BufferedImage bfi = null;
        if (c.estMange()) {
            bfi = hVide;
        } else if (c.pingouinPresent() == 0) {
            if (c.getNbPoissons() == 1)
                bfi = hPoisson1;
            else if (c.getNbPoissons() == 2)
                bfi = hPoisson2;
            else if (c.getNbPoissons() == 3)
                bfi = hPoisson3;
        } else if (c.pingouinPresent() == 1) {
            if (c.getNbPoissons() == 1)
                bfi = hPingouinR1;
            else if (c.getNbPoissons() == 2)
                bfi = hPingouinR2;
            else if (c.getNbPoissons() == 3)
                bfi = hPingouinR3;
        } else if (c.pingouinPresent() == 2) {
            if (c.getNbPoissons() == 1)
                bfi = hPingouinB1;
            else if (c.getNbPoissons() == 2)
                bfi = hPingouinB2;
            else if (c.getNbPoissons() == 3)
                bfi = hPingouinB3;
        }
        return bfi;
    }


    public List<Shape> getPlateauJeu(){
        return grille;
    }

    private Color getColor(Cases c){
        Color couleur;
        if (c.estMange()) {
            couleur = Color.BLACK;
        } else if (c.pingouinPresent() == 0) {
            if (c.getNbPoissons() == 1)
                couleur = Color.GREEN;
            else if (c.getNbPoissons() == 2)
                couleur = Color.MAGENTA;
            else if (c.getNbPoissons() == 3)
                couleur = Color.orange;
        } else if (c.pingouinPresent() == 1) {
                couleur = Color.red;
        } else if (c.pingouinPresent() == 2) {
                couleur = Color.BLUE;
        }

        return null;
    }
}
