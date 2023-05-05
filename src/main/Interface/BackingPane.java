package Interface;

import javax.swing.*;
import java.awt.*;

public class BackingPane extends JPanel {
    private Aide aide;
    public BackingPane() {
        setLayout(new BorderLayout());
        setOpaque(false);

        aide = new Aide();
        add(aide, BorderLayout.CENTER);



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(GameConstants.BACKGROUND_GRISEE);
        g.fillRect(0, 0, getWidth(), getHeight());

    }
}
