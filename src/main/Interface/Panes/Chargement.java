package Interface.Panes;

import Interface.ChargeElm.ListeFile;
import Interface.ChargeElm.Preview;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;


public class Chargement extends JPanel {

    private CollecteurEvenements collecteurEvenements;
    private ListeFile lf;
    private Preview preview;

    public Chargement(CollecteurEvenements c){
        collecteurEvenements = c;
        setLayout(new GridLayout(1, 2));
        lf = new ListeFile(this);
        preview = new Preview();
        add(lf);
        add(preview);


    }

    public void changePreview(String s){
        preview.setPreview(s);
    }
}
