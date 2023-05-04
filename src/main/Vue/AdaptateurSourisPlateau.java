package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSourisPlateau extends MouseAdapter {

    BanquiseGraphique banquiseGraphique;
    CollecteurEvenements collecteurEvenements;

    AdaptateurSourisPlateau(BanquiseGraphique bGraphique, CollecteurEvenements cEvenements) {
        banquiseGraphique = bGraphique;
        collecteurEvenements = cEvenements;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int coupX = e.getX();
        int coupY = e.getY();
        System.out.println("Test x = " + coupX + "; y = " + coupY);
        collecteurEvenements.clicSourisPlateau(coupX, coupY);
    }


}
