package Interface.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    BufferedImage image;
    int sizeImage;


    public ImagePanel(BufferedImage bi){
        image = bi;
    }


    private void majSize() {
        float largeur = getSize().width;
        float hauteur = getSize().height;
        sizeImage = (int) Math.min(largeur, hauteur);
    }


    public void paintComponent(Graphics g) {
        g.drawImage(image, 0,0, sizeImage, sizeImage, null);
        g.dispose();
    }


    @Override
    public void invalidate() {
        super.invalidate();
        majSize();
    }



}
