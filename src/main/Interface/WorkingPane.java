package Interface;

import javax.swing.*;
import java.awt.*;

public class WorkingPane extends JLayeredPane {
    private final BackingPane backingPane;

    private JPanel menu;

    private JPanel selection;

    private JPanel game;
    public WorkingPane(JPanel m, JPanel s){
        setLayout(new CardLayout());
        this.menu = m;
        this.selection = s;
        backingPane = new BackingPane();
        backingPane.setVisible(false);

        add(this.menu);
        add(this.selection);

        add(backingPane);


        setLayer(backingPane, highestLayer() + 1);

    }

    public void toggleBackingPane(){
        backingPane.setVisible(!backingPane.isVisible());
    }

    public void changePanel(JPanel J){

    }
}
