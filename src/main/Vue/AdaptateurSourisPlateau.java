package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class AdaptateurSourisPlateau extends MouseAdapter implements MouseMotionListener {

    BanquiseGraphique banquiseGraphique;
    CollecteurEvenements collecteurEvenements;

    public AdaptateurSourisPlateau(BanquiseGraphique bGraphique, CollecteurEvenements cEvenements) {
        banquiseGraphique = bGraphique;
        collecteurEvenements = cEvenements;

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int coupX = e.getX();
        int coupY = e.getY();
        collecteurEvenements.clicSourisPlateau(coupX, coupY);
    }


//    public void mouseMoved(MouseEvent e) {
//        int coupX = e.getX();
//        int coupY = e.getY();
//        System.out.println("zzzz");
//        collecteurEvenements.mouseOnBanquise(coupX, coupY);
//    }


}
