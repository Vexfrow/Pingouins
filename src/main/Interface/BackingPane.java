package Interface;

import javax.swing.*;
import java.awt.*;

public class BackingPane extends JPanel {
    public BackingPane() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(128, 128, 128, 192));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
