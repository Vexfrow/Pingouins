package Vue;

import Model.Jeu;

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

    BufferedImage hexagonePoisson1, hexagonePoisson2, hexagonePoisson3, hexagonePingouinR, hexagoneVide;

    private Jeu jeu;


    private List<Shape> grille;



    public BanquiseGraphique(Jeu jeu){
        this.jeu = jeu;
        hexagonePoisson1 = chargeImage("casePoissons1");
        hexagonePoisson2 = chargeImage("casePoissons2");
        hexagonePoisson3 = chargeImage("casePoissons3");
        hexagonePingouinR = chargeImage("pingouinRouge");
        hexagoneVide = chargeImage("caseVide");
        grille = new ArrayList<>(60);
    }

    private BufferedImage chargeImage(String nom) {
        try {
            InputStream in = new FileInputStream("rsc/images/" + nom + ".png");
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


    public static BufferedImage getTexturedImage(BufferedImage src, Shape shp){
        Rectangle r = shp.getBounds();

        //On récupère une version redimensionnée de l'image
        Image imageTmp = src.getScaledInstance((int)r.getWidth(), (int)r.getHeight(), BufferedImage.SCALE_REPLICATE);
        //On crée une nouvelle image bufferisé
        BufferedImage buffered = new BufferedImage((int)r.getWidth(), (int)r.getHeight(),BufferedImage.TYPE_INT_ARGB);
        //On remplace la nouvelle image par la version redimensionnée de l'image que l'on souhaite mettre
        buffered.getGraphics().drawImage(imageTmp, 0, 0, null);

        //TODO : Verifier l'utilité de ce bout de code
//        Graphics g = buffered.getGraphics();
//        Shape c = g.getClip();
//        g.setClip(shp);
//        g.setClip(c);
//        g.setColor(Color.BLACK);
//        g.dispose();

        return buffered;
    }


    public void misAJour(Jeu jeu){
        this.jeu = jeu;
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
        float offsetX = (largeur /10f);
        float offsetY = (hauteur /20f);

        int colonnes = 8;
        int lignes = 8;

        float size = Math.min(((largeur-(offsetX*2)) / colonnes), ((hauteur-(offsetY*2)) / lignes / 0.9f));
        float radius = size/2f;

        grille.clear();

        Shape hexagone = new Hexagone(new Point((int) (offsetX + radius),(int) (offsetY + radius)), radius).getHexagone();

        for (int ligne = 0; ligne < lignes; ligne++) {
            float offset = 0;
            colonnes = 8;

            if (ligne % 2 == 0) {
                offset = size/2;
                colonnes = 7;
            }
            for (int colonne = 0; colonne < colonnes; colonne++) {
                AffineTransform at = AffineTransform.getTranslateInstance((colonne * size) + offset, ligne *size*0.9f);
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

        BufferedImage bfi = hexagoneVide;

        int i = 0;
        int j = 0;
        for (Shape cell : grille) {

            if(jeu.getCase(i,j).estMange()){
                bfi = hexagoneVide;
            }else if(jeu.getCase(i,j).getNbPoissons() == 1){
                bfi = hexagonePoisson1;
            }else if(jeu.getCase(i,j).getNbPoissons() == 2){
                bfi = hexagonePoisson2;
            }else if(jeu.getCase(i,j).getNbPoissons() == 3){
                bfi = hexagonePoisson3;
            }

            bfi = getTexturedImage(bfi, cell);
            g2d.drawImage(bfi, cell.getBounds().x, cell.getBounds().y, null);

            j++;

            if(i%2 == 0){
                if(j >= 7) {
                    i = i + 1;
                    j = 0;
                }
            }else{
                if(j >= 8) {
                    i = i + 1;
                    j = 0;
                }
            }
        }

        g2d.dispose();
    }

}
