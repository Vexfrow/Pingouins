package Interface.ChargeElm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cross implements ActionListener {

    public int indice;
    public ListeFile liste;
    public Cross(int x, ListeFile lf){
        indice = x;
        liste = lf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        liste.supprime(indice);
        liste.removeAll();
        liste.initFile();
        liste.setPosition();
        liste.majFleche();
        liste.setListeFichier();

        liste.revalidate();
        liste.repaint();
    }
}
