package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BackingPane extends JPanel {
    private Aide aide;
    public BackingPane() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(120, 300, 120, 300));
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
