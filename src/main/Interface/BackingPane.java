package Interface;

import javax.swing.*;
import java.awt.*;

public class BackingPane extends JPanel {
    private Aide aide;
    public BackingPane() {
        setOpaque(false);
        aide = new Aide();
        add(aide);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(128, 128, 128, 192));
        g.fillRect(0, 0, getWidth(), getHeight());
        aide.repaint();
    }
}
