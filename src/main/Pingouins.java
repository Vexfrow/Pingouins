
import Interface.Fenetre;
import Controleur.Controleur;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Pingouins {
    public static void main (String args[]){
        Fenetre window = new Fenetre();

        Controleur c = new Controleur(window);
        SwingUtilities.invokeLater(window);

    }


}