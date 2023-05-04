package Controleur;

import Interface.Aide;
import Interface.Fenetre;

import javax.swing.*;

public class Controleur{

    private Fenetre window;
    public Controleur(){

    }

    public void ajouteInterface(Fenetre window){
        this.window = window;
    }

    public void toggleHelp(){
        //this.window.workingPane.toggleBackingPane();

    }

    public void switchSel(){
        window.switchPanel(2);
    }
    public void switchMenu(){
        window.switchPanel(1);
    }

}
