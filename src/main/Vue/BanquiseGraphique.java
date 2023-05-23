package Vue;

import Interface.GameConstants;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BanquiseGraphique extends JComponent {

    TexturePaint paintFont;

    private float size;
    private float offsetX;
    private float offsetY;

    int etat;

    boolean drawPing;
    Position posPing;

    private Jeu jeu;

    private Coup suggestionCoup;
    private Position suggestionPos;

    private final ArrayList<Shape> plateau;

    public BanquiseGraphique(Jeu jeu) {
        this.jeu = jeu;
        this.etat = jeu.getEtat();
        drawPing = false;
        posPing = null;

        Rectangle r = new Rectangle(0,0, 750, 750);
        paintFont = new TexturePaint(GameConstants.fondMer,r);

        plateau = new ArrayList<>(60);

        suggestionCoup = null;
        suggestionPos = null;
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
        offsetX = (largeur / 6f);
        offsetY = (hauteur / 20f);

        int colonnes = 8;
        int lignes = 8;

        size = Math.min(((largeur - (offsetX *2)) / colonnes), ((hauteur - (offsetY * 2)) / lignes / 0.9f));

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

        g2d.drawImage(GameConstants.banquise, 0, 0, getWidth(), getHeight(), null);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        ArrayList<Position> listHexagone = null;
        ArrayList<Position> listPingouinPos = null;
        Position infoP = null;

        if(etat == Jeu.ETAT_CHOIXC) {
            infoP = jeu.getSelectionP();
            listHexagone = jeu.getCaseAccessible(infoP.x, infoP.y);
        }else if(etat == Jeu.ETAT_SELECTIONP && jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() == 0){
            ArrayList<Pingouin> listPingouin = jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).listePingouin;
            listPingouinPos = new ArrayList<>();
            for(Pingouin p : listPingouin){
                listPingouinPos.add(new Position(p.getLigne(), p.getColonne()));
            }
        }

        Coup lc = jeu.getLastCoup();

        Position lastPos = null;
        Position newPos = null;
        if(lc != null){
            newPos = new Position(lc.getLigne(), lc.getColonne());
            lastPos = new Position(lc.getPingouin().getLigne(), lc.getPingouin().getColonne());
        }


        if(suggestionCoup != null)
            System.out.println("scoup = " + suggestionCoup.getLigne() + " " + suggestionCoup.getColonne());

        if(suggestionPos != null)
            System.out.println("spos = " + suggestionPos.x + " " + suggestionPos.y);

        BufferedImage bfi = GameConstants.hVide;

        for (int i = 0; i < plateau.size(); i++) {

            Position coordHexa = getPosFromNumber(i);
            Cases c = jeu.getCase(coordHexa.x, coordHexa.y);
            Shape cell = plateau.get(i);


            if(coordHexa.equals(newPos)){
                Cases c2 = jeu.getCase(newPos.x,newPos.y);
                if (c2.pingouinPresent() == 1) {
                    if (c2.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinBC1;
                    else if (c2.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinBC2;
                    else if (c2.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinBC3;
                } else if (c2.pingouinPresent() == 2) {
                    if (c2.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinRC1;
                    else if (c2.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinRC2;
                    else if (c2.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinRC3;
                } else if (c2.pingouinPresent() == 3) {
                    if (c2.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinVC1;
                    else if (c2.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinVC2;
                    else if (c2.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinVC3;
                } else if (c2.pingouinPresent() == 4) {
                    if (c2.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinJC1;
                    else if (c2.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinJC2;
                    else if (c2.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinJC3;
                }
            }else if(coordHexa.equals(lastPos) && etat != Jeu.ETAT_PLACEMENTP){
                int lastJoueur = jeu.getLastPlayer();
                if(lastJoueur == 1)
                    bfi = GameConstants.hVideB;
                else if(lastJoueur == 2)
                    bfi = GameConstants.hVideR;
                else if (lastJoueur == 3)
                    bfi = GameConstants.hVideV;
                else
                    bfi = GameConstants.hVideJ;
            }else if(etat == Jeu.ETAT_PLACEMENTP && c.getNbPoissons() == 1 && c.pingouinPresent() == 0 && jeu.getListeJoueur().get(jeu.getJoueurCourant()-1).estIA() == 0) {
                if (jeu.getJoueurCourant() == 1)
                    bfi = GameConstants.hPoisson1hB;
                else if (jeu.getJoueurCourant() == 2)
                    bfi = GameConstants.hPoisson1hR;
                else if (jeu.getJoueurCourant() == 3)
                    bfi = GameConstants.hPoisson1hV;
                else if (jeu.getJoueurCourant() == 4)
                    bfi = GameConstants.hPoisson1hJ;
//            }else if(etat == Jeu.ETAT_PLACEMENTP && (c.getNbPoissons() != 1 || c.pingouinPresent() != 0)){
//                if(c.getNbPoissons() == 1)
//                    bfi = GameConstants.hPoissonG1;
//                else if(c.getNbPoissons() == 2)
//                    bfi = GameConstants.hPoissonG2;
//                else if(c.getNbPoissons() == 3)
//                    bfi = GameConstants.hPoissonG3;

            }else if(etat == Jeu.ETAT_CHOIXC && listHexagone != null && listHexagone.contains(coordHexa)){
                if(jeu.getJoueurCourant() == 1){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPoisson1hB;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPoisson2hB;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPoisson3hB;
                }else if(jeu.getJoueurCourant() == 2){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPoisson1hR;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPoisson2hR;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPoisson3hR;
                }else if(jeu.getJoueurCourant() == 3){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPoisson1hV;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPoisson2hV;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPoisson3hV;
                }else{
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPoisson1hJ;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPoisson2hJ;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPoisson3hJ;
                }

            }else if ((etat == Jeu.ETAT_CHOIXC && coordHexa.equals(infoP)) || (etat == Jeu.ETAT_SELECTIONP && listPingouinPos != null && listPingouinPos.contains(coordHexa))){
                if(jeu.getJoueurCourant() == 1){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinB1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinB2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinB3h;
                }else if(jeu.getJoueurCourant() == 2){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinR1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinR2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinR3h;
                }else if(jeu.getJoueurCourant() == 3){
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinV1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinV2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinV3h;
                }else{
                    if (c.getNbPoissons() == 1)
                        bfi = GameConstants.hPingouinJ1h;
                    else if (c.getNbPoissons() == 2)
                        bfi = GameConstants.hPingouinJ2h;
                    else if (c.getNbPoissons() == 3)
                        bfi = GameConstants.hPingouinJ3h;
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
            bfi = GameConstants.hVide;
        } else if (c.pingouinPresent() == 0) {
            if (c.getNbPoissons() == 1)
                bfi = GameConstants.hPoisson1;
            else if (c.getNbPoissons() == 2)
                bfi = GameConstants.hPoisson2;
            else if (c.getNbPoissons() == 3)
                bfi = GameConstants.hPoisson3;
        } else if (c.pingouinPresent() == 1) {
            if (c.getNbPoissons() == 1)
                bfi = GameConstants.hPingouinB1;
            else if (c.getNbPoissons() == 2)
                bfi = GameConstants.hPingouinB2;
            else if (c.getNbPoissons() == 3)
                bfi = GameConstants.hPingouinB3;
        } else if (c.pingouinPresent() == 2) {
            if (c.getNbPoissons() == 1)
                bfi = GameConstants.hPingouinR1;
            else if (c.getNbPoissons() == 2)
                bfi = GameConstants.hPingouinR2;
            else if (c.getNbPoissons() == 3)
                bfi = GameConstants.hPingouinR3;
        } else if (c.pingouinPresent() == 3) {
            if (c.getNbPoissons() == 1)
                bfi = GameConstants.hPingouinV1;
            else if (c.getNbPoissons() == 2)
                bfi = GameConstants.hPingouinV2;
            else if (c.getNbPoissons() == 3)
                bfi = GameConstants.hPingouinV3;
        } else if (c.pingouinPresent() == 4) {
            if (c.getNbPoissons() == 1)
                bfi = GameConstants.hPingouinJ1;
            else if (c.getNbPoissons() == 2)
                bfi = GameConstants.hPingouinJ2;
            else if (c.getNbPoissons() == 3)
                bfi = GameConstants.hPingouinJ3;
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
        BufferedImage bi = new BufferedImage((int) (size*8 + 2*offsetX), (int) (size*8 + offsetY), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        this.paint(g);
        g.dispose();
        try{ImageIO.write(bi,"png",new File("resources/sauvegarde/"+nomFichier+".png"));}catch (Exception ignored) {}
    }


    public void misAJourSuggestionPlacement(Jeu jeu, Position position) {
        suggestionPos = position;
        misAJour(jeu);
    }

    public void misAJourSuggestionCoup(Jeu jeu, Coup coup) {
        suggestionCoup = coup;
        misAJour(jeu);
    }

}

