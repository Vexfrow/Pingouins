package Interface;

import Vue.CollecteurEvenements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BackingPane extends JPanel {
    private CollecteurEvenements collecteur;
    private JPanel context;
    private int etat;
    private int previousState;

    public BackingPane(CollecteurEvenements c) {
        this.collecteur = c;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(120, 300, 120, 300));
        context = new Aide(collecteur);
        add(context, BorderLayout.CENTER);
        etat = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(GameConstants.BACKGROUND_GRISEE);
        g.fillRect(0, 0, getWidth(), getHeight());

    }

    public void setPanelLayer(int j){
        this.remove(context);
        if(j == 1){
            setBorder(new EmptyBorder(120, 300, 120, 300));
            this.context = new Aide(collecteur);
            previousState = etat;
            this.etat = 1;
        }else if(j == 2){
            setBorder(new EmptyBorder(100, 600, 100, 600));
            this.context = new Pause(this.collecteur);
            previousState = etat;
            this.etat = 2;
        }else if(j == 3){
            setBorder(new EmptyBorder(120, 300, 120, 300));
            this.context = new Sauvegarde(collecteur);
            previousState = etat;
            this.etat = 3;
        }
        this.add(context);
        this.revalidate();
        this.repaint();

    }

    public int getPreviousState(){
        return previousState;
    }

    public void reset(){
        etat = 0;
        previousState = 0;
    }

}
