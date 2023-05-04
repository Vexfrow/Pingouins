package Interface;

import javax.swing.*;
import java.awt.*;

public class WorkingPane extends JLayeredPane {
    public final int CURRENT_LEVEL = 10;
    private final BackingPane backingPane;
    private JPanel actuel;
    private JPanel game;
    public WorkingPane(JPanel m){
        setLayout(new CardLayout());
        this.actuel = m;
        backingPane = new BackingPane();
        backingPane.setVisible(false);
        this.add(actuel);
        this.add(backingPane);
        this.setLayer(actuel, CURRENT_LEVEL);
        this.setLayer(backingPane, POPUP_LAYER);


    }

    public void toggleBackingPane(){
        backingPane.setVisible(!backingPane.isVisible());
    }

    public void changePanel(JPanel j){
        this.remove(actuel);
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

}
