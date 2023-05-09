package Vue;

import Model.Jeu;
import Model.Cases;
import Model.Pingouin;
import Model.Position;


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
import java.util.Objects;

public class BanquiseGraphique extends JComponent {

    public final static int ETAT_INITIAL = 0; //Etat de base
    public final static int ETAT_PLACEMENTP = 1; //Highlight sur les hexagones disponibles pour placer le pingouin
    public final static int ETAT_SELECTIONP = 2; //Highlight sur les pingouins que le joueur peut utiliser
    public final static int ETAT_CHOIXC = 3;//Highlight sur les hexagones disponibles pour déplacer le pingouin choisi

    BufferedImage hPoisson1, hPoisson2, hPoisson3, hVide;
    BufferedImage hPingouinR1, hPingouinR2, hPingouinR3;
    BufferedImage hPingouinB1, hPingouinB2, hPingouinB3;
    BufferedImage hPingouinV1, hPingouinV2, hPingouinV3;
    BufferedImage hPingouinJ1, hPingouinJ2, hPingouinJ3;

    TexturePaint paintFont;

    int etat;
    int hexagone;

    private Jeu jeu;
    private ArrayList<Shape> plateau;

    public BanquiseGraphique(Jeu jeu) {
        this.jeu = jeu;
        this.etat = ETAT_INITIAL;

        Rectangle r = new Rectangle(0,0, 750, 750);
        paintFont = new TexturePaint(chargeImage("fondMer"),r);

        //Todo : trouver une meilleure manière que charger toutes les images directement
        hPoisson1 = chargeImage("casePoissons1");
        hPoisson2 = chargeImage("casePoissons2");
        hPoisson3 = chargeImage("casePoissons3");
        hVide = chargeImage("caseVide");

        hPingouinR1 = chargeImage("caseRouge1");
        hPingouinR2 = chargeImage("caseRouge2");
        hPingouinR3 = chargeImage("caseRouge3");

        hPingouinV1 = chargeImage("caseVert1");
        hPingouinV2 = chargeImage("caseVert2");
        hPingouinV3 = chargeImage("caseVert3");

        hPingouinB1 = chargeImage("caseBleu1");
        hPingouinB2 = chargeImage("caseBleu2");
        hPingouinB3 = chargeImage("caseBleu3");

        hPingouinJ1 = chargeImage("caseJaune1");
        hPingouinJ2 = chargeImage("caseJaune2");
        hPingouinJ3 = chargeImage("caseJaune3");


        plateau = new ArrayList<>(60);
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


    public static BufferedImage getTexturedImage(BufferedImage src, Shape shp, boolean redTaint) {
        Rectangle r = shp.getBounds();

        //On récupère une version redimensionnée de l'image
        Image imageTmp = src.getScaledInstance((int) r.getWidth(), (int) r.getHeight(), BufferedImage.SCALE_FAST);

        //On crée une nouvelle image bufferisé
        BufferedImage buffered = new BufferedImage((int) r.getWidth(), (int) r.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //On remplace la nouvelle image par la version redimensionnée de l'image que l'on souhaite mettre
        buffered.getGraphics().drawImage(imageTmp, 0, 0, null);

        if(redTaint){
            Color c = new Color(200,0,0,100);
            for (int x = 0; x < buffered.getWidth(); x++) {
                for (int y = 0; y < buffered.getHeight(); y++) {
                    Color pixelColor = new Color(buffered.getRGB(x, y), true);
                    int re = (pixelColor.getRed() + c.getRed()) / 2;
                    int g = (pixelColor.getGreen() + c.getGreen()) / 2;
                    int b = (pixelColor.getBlue() + c.getBlue()) / 2;
                    int a = pixelColor.getAlpha();
                    int rgba = (a << 24) | (re << 16) | (g << 8) | b;
                    buffered.setRGB(x, y, rgba);
                }
            }
        }


        src.getGraphics().dispose();
        buffered.getGraphics().dispose();

        return buffered;
    }


    public void misAJour(Jeu jeu, int etat, int info) {
        this.jeu = jeu;
        this.etat = etat;
        this.hexagone = info;
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

        plateau.clear();

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

                plateau.add(area);
            }
        }

    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(paintFont);
        g2d.fill(this.getBounds());

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        ArrayList<Position> listHexagone = null;
        ArrayList<Position> listPingouinPos = null;

        if(etat == ETAT_CHOIXC) {
            Position infoP = getCoordFromNumber(hexagone);
            listHexagone = jeu.getCaseAccessible(infoP.x, infoP.y);
        }else if(etat == ETAT_SELECTIONP){
            ArrayList<Pingouin> listPingouin = jeu.getListeJoueur().get(jeu.getJoueur()-1).listePingouin;
            listPingouinPos = new ArrayList<>();
            for(Pingouin p : listPingouin){
                listPingouinPos.add(new Position(p.getLigne(), p.getColonne()));
            }
        }

        BufferedImage bfi = hVide;

        for (int i = 0; i < plateau.size(); i++) {

            Position coordHexa = getCoordFromNumber(i);
            Cases c = jeu.getCase(coordHexa.x, coordHexa.y);
            Shape cell = plateau.get(i);

            if(etat == ETAT_PLACEMENTP && c.getNbPoissons() == 1 && c.pingouinPresent() == 0){
                bfi = getTexturedImage(getBfi(c), cell, true);
            }else if(etat == ETAT_CHOIXC && Objects.requireNonNull(listHexagone).contains(coordHexa)){
                bfi = getTexturedImage(getBfi(c), cell, true);
            }else if(etat == ETAT_SELECTIONP && Objects.requireNonNull(listPingouinPos).contains(coordHexa)){
                bfi = getTexturedImage(getBfi(c), cell, true);
            }else{
                bfi = getTexturedImage(getBfi(c), cell, false);
            }

            g2d.drawImage(bfi, cell.getBounds().x, cell.getBounds().y, null);

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
        return plateau;
    }


    public Position getCoordFromNumber(int number){
        int i = 0;
        int j =0;

        while(number > 0){
            j++;

            if(i%2 == 0){
                if(j >= 7){
                   i++;
                   j=0;
                }
            }else{
                if(j >= 8){
                    i++;
                    j=0;
                }
            }
            number--;
        }
        return new Position(i,j);
    }


}

