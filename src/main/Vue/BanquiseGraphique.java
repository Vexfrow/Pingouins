package Vue;

import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BanquiseGraphique extends JComponent {

    BufferedImage hPoisson1, hPoisson2, hPoisson3, hVide;
    BufferedImage hPingouinR1, hPingouinR2, hPingouinR3;
    BufferedImage hPingouinB1, hPingouinB2, hPingouinB3;
    BufferedImage hPingouinV1, hPingouinV2, hPingouinV3;
    BufferedImage hPingouinJ1, hPingouinJ2, hPingouinJ3;

    BufferedImage hPoisson1hR, hPoisson2hR, hPoisson3hR;
    BufferedImage hPoisson1hB, hPoisson2hB, hPoisson3hB;
    BufferedImage hPoisson1hV, hPoisson2hV, hPoisson3hV;
    BufferedImage hPoisson1hJ, hPoisson2hJ, hPoisson3hJ;
    BufferedImage hPingouinR1h, hPingouinR2h, hPingouinR3h;
    BufferedImage hPingouinB1h, hPingouinB2h, hPingouinB3h;
    BufferedImage hPingouinV1h, hPingouinV2h, hPingouinV3h;
    BufferedImage hPingouinJ1h, hPingouinJ2h, hPingouinJ3h;

    TexturePaint paintFont;

    int etat;

    private Jeu jeu;
    private ArrayList<Shape> plateau;

    public BanquiseGraphique(Jeu jeu) {
        this.jeu = jeu;
        this.etat = jeu.getEtat();

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

        hPoisson1hR = chargeImage("brilleRouge1");
        hPoisson2hR = chargeImage("brilleRouge2");
        hPoisson3hR = chargeImage("brilleRouge3");

        hPoisson1hB = chargeImage("brilleBleu1");
        hPoisson2hB = chargeImage("brilleBleu2");
        hPoisson3hB = chargeImage("brilleBleu3");

        hPoisson1hV = chargeImage("brilleVert1");
        hPoisson2hV = chargeImage("brilleVert2");
        hPoisson3hV = chargeImage("brilleVert3");

        hPoisson1hJ = chargeImage("brilleJaune1");
        hPoisson2hJ = chargeImage("brilleJaune2");
        hPoisson3hJ = chargeImage("brilleJaune3");

        hPingouinR1h = chargeImage("contourRouge1");
        hPingouinR2h = chargeImage("contourRouge2");
        hPingouinR3h = chargeImage("contourRouge3");

        hPingouinV1h = chargeImage("contourVert1");
        hPingouinV2h = chargeImage("contourVert2");
        hPingouinV3h = chargeImage("contourVert3");

        hPingouinB1h = chargeImage("contourBleu1");
        hPingouinB2h = chargeImage("contourBleu2");
        hPingouinB3h = chargeImage("contourBleu3");

        hPingouinJ1h = chargeImage("contourJaune1");
        hPingouinJ2h = chargeImage("contourJaune2");
        hPingouinJ3h = chargeImage("contourJaune3");


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


    public void misAJour(Jeu jeu) {
        this.jeu = jeu;
        this.etat = jeu.getEtat();
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
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(paintFont);
        g2d.fill(this.getBounds());

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        ArrayList<Position> listHexagone = null;
        ArrayList<Position> listPingouinPos = null;
        Position infoP = null;

        if(etat == Jeu.ETAT_CHOIXC) {
            infoP = jeu.getSelectionP();
            listHexagone = jeu.getCaseAccessible(infoP.x, infoP.y);
        }else if(etat == Jeu.ETAT_SELECTIONP){
            ArrayList<Pingouin> listPingouin = jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).listePingouin;
            listPingouinPos = new ArrayList<>();
            for(Pingouin p : listPingouin){
                listPingouinPos.add(new Position(p.getLigne(), p.getColonne()));
            }
        }

        BufferedImage bfi = hVide;

        for (int i = 0; i < plateau.size(); i++) {

            Position coordHexa = getPosFromNumber(i);
            Cases c = jeu.getCase(coordHexa.x, coordHexa.y);
            Shape cell = plateau.get(i);

            if(etat == Jeu.ETAT_PLACEMENTP && c.getNbPoissons() == 1 && c.pingouinPresent() == 0){
               if(jeu.getJoueurCourant() == 1)
                   bfi = hPoisson1hR;
               else if(jeu.getJoueurCourant() == 2)
                   bfi = hPoisson1hB;
               else if(jeu.getJoueurCourant() == 3)
                   bfi = hPoisson1hV;
               else if(jeu.getJoueurCourant() == 4)
                   bfi = hPoisson1hJ;

            }else if(etat == Jeu.ETAT_CHOIXC && Objects.requireNonNull(listHexagone).contains(coordHexa)){
                if(jeu.getJoueurCourant() == 1){
                    if (c.getNbPoissons() == 1)
                        bfi = hPoisson1hR;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPoisson2hR;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPoisson3hR;
                }else if(jeu.getJoueurCourant() == 2){
                    if (c.getNbPoissons() == 1)
                        bfi = hPoisson1hB;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPoisson2hB;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPoisson3hB;
                }else if(jeu.getJoueurCourant() == 3){
                    if (c.getNbPoissons() == 1)
                        bfi = hPoisson1hV;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPoisson2hV;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPoisson3hV;
                }else{
                    if (c.getNbPoissons() == 1)
                        bfi = hPoisson1hJ;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPoisson2hJ;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPoisson3hJ;
                }

            }else if ((etat == Jeu.ETAT_CHOIXC && coordHexa.equals(infoP)) || (etat == Jeu.ETAT_SELECTIONP && Objects.requireNonNull(listPingouinPos).contains(coordHexa))){
                if(jeu.getJoueurCourant() == 1){
                    if (c.getNbPoissons() == 1)
                        bfi = hPingouinR1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPingouinR2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPingouinR3h;
                }else if(jeu.getJoueurCourant() == 2){
                    if (c.getNbPoissons() == 1)
                        bfi = hPingouinB1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPingouinB2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPingouinB3h;
                }else if(jeu.getJoueurCourant() == 3){
                    if (c.getNbPoissons() == 1)
                        bfi = hPingouinV1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPingouinV2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPingouinV3h;
                }else{
                    if (c.getNbPoissons() == 1)
                        bfi = hPingouinJ1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = hPingouinJ2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = hPingouinJ3h;
                }
            }else{
                bfi = getBfi(c);
            }

            g2d.drawImage(bfi, cell.getBounds().x, cell.getBounds().y, cell.getBounds().width, cell.getBounds().height, null);

        }

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
        } else if (c.pingouinPresent() == 3) {
            if (c.getNbPoissons() == 1)
                bfi = hPingouinV1;
            else if (c.getNbPoissons() == 2)
                bfi = hPingouinV2;
            else if (c.getNbPoissons() == 3)
                bfi = hPingouinV3;
        } else if (c.pingouinPresent() == 4) {
            if (c.getNbPoissons() == 1)
                bfi = hPingouinJ1;
            else if (c.getNbPoissons() == 2)
                bfi = hPingouinJ2;
            else if (c.getNbPoissons() == 3)
                bfi = hPingouinJ3;
        }
        return bfi;
    }


    public List<Shape> getPlateauJeu(){
        return plateau;
    }


    public Position getPosFromNumber(int number){
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


    public void sauvegardeBanquise(String nomFichier){
        BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        this.paint(g);  //this == JComponent
        g.dispose();
        try{ImageIO.write(bi,"png",new File("resources/sauvegarde/"+nomFichier+".png"));}catch (Exception ignored) {}
    }


}

