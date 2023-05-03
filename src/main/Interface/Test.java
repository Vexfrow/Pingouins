package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        //      private Shape hexagon;
        private List<Shape> cells = new ArrayList<>(6);

        private Shape highlighted;

        public TestPane() {
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    highlighted = null;
                    for (Shape cell : cells) {
                        if (cell.contains(e.getPoint())) {
                            highlighted = cell;
                            break;
                        }
                    }
                    repaint();
                }
            });
        }

        @Override
        public void invalidate() {
            super.invalidate();
            updateHoneyComb();
        }

        protected void updateHoneyComb() {
            GeneralPath path = new GeneralPath();

            float rowHeight = ((getHeight() * 1.14f) / 3f);
            float colWidth = getWidth() / 3f;

            float size = Math.min(rowHeight, colWidth);

            float centerX = size / 2f;
            float centerY = size / 2f;
            for (float i = 0; i < 6; i++) {
                float angleDegrees = (60f * i) - 30f;
                float angleRad = ((float) Math.PI / 180.0f) * angleDegrees;

                float x = centerX + ((size / 2f) * (float) Math.cos(angleRad));
                float y = centerY + ((size / 2f) * (float) Math.sin(angleRad));

                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.closePath();

            cells.clear();
            for (int row = 0; row < 3; row++) {
                float offset = size / 2f;
                int colCount = 2;
                if (row % 2 == 0) {
                    offset = 0;
                    colCount = 3;
                }
                for (int col = 0; col < colCount; col++) {
                    AffineTransform at = AffineTransform.getTranslateInstance(offset + (col * size), row * (size * 0.8f));
                    Area area = new Area(path);
                    area = area.createTransformedArea(at);
                    cells.add(area);
                }
            }

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (highlighted != null) {
                g2d.setColor(Color.BLUE);
                g2d.fill(highlighted);
            }
            g2d.setColor(Color.BLACK);
            for (Shape cell : cells) {
                g2d.draw(cell);
            }
            g2d.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

    }
}