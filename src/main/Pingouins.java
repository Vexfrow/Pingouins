import Interface.Fenetre;
import Controleur.Controleur;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Pingouins {
    public static void main (String args[]){
        Controleur c = new Controleur();
        Fenetre window = new Fenetre(c);

        SwingUtilities.invokeLater(window);

    }


}