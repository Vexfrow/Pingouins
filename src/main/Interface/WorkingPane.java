package Interface;

import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;

public class WorkingPane extends JLayeredPane {
    public final int CURRENT_LEVEL = 10;
    private final BackingPane backingPane;
    private CollecteurEvenements collecteur;
    public JPanel actuel;

    public WorkingPane(JPanel m, CollecteurEvenements c){
        setLayout(new CardLayout());
        this.collecteur = c;
        this.actuel = m;
        backingPane = new BackingPane(this.collecteur);
        backingPane.setVisible(false);
        this.add(actuel);

        this.add(backingPane);
        this.setLayer(actuel, CURRENT_LEVEL);
        this.setLayer(backingPane, POPUP_LAYER);


    }

    public void toggleBackingPane(int i){
        backingPane.setVisible(!backingPane.isVisible());
    }

    public void changePanel(JPanel j){
        setPanelLayer(j);
    }


    public void setPanelLayer(JPanel j){
        this.remove(actuel);
        this.remove(backingPane);
        this.actuel = j;
        this.add(actuel);
        this.add(backingPane);
        this.setLayer(actuel, CURRENT_LEVEL);
        this.setLayer(backingPane, POPUP_LAYER);
        this.revalidate();
        this.repaint();

    }

    public void switchBackPane(int j){
        backingPane.setPanelLayer(j);
    }


    public int getEtatofBackPane(){
        return backingPane.getEtat();
    }
}
