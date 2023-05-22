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
        liste.initFile();
        liste.removeAll();
        liste.setPosition();
        liste.resetBorder();
        liste.majFleche();
    }
}
