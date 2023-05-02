
package main.Interface;

import javax.swing.*;

import main.Model.Jeu;

import main.Vue.BanquiseGraphique;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Fenetre implements Runnable {

    Jeu j;


    public Fenetre(Jeu jeu){
        j = jeu;
    }


    public static void demarrer(Jeu j){
        try{
            SwingUtilities.invokeAndWait(new Fenetre(j));
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void run() {
        // Creation d'une fenêtre
        JFrame mainFrame = new JFrame("La gaufre empoisonnée");
        mainFrame.setBackground(Color.blue);

        // On fixe la taille et on démarre
        mainFrame.setSize(530, 300);
        mainFrame.setMinimumSize(new Dimension(530, 300));


        JPanel panelGaufre = new JPanel(new CardLayout());
        panelGaufre.setBackground(new Color(155, 216, 248));
        BanquiseGraphique bg = new BanquiseGraphique(j);
        panelGaufre.add(bg);
        panelGaufre.setVisible(true);
        mainFrame.add(panelGaufre);


        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }



}
