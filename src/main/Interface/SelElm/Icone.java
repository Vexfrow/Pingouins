package Interface.SelElm;
import javax.swing.*;
import java.awt.*;

public class Icone extends JPanel{


    public Icone(){

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Polygon p = new Polygon();
        //Dessin du polygone
        Dimension taille = getSize();
        System.out.println(taille);
        int coorX = taille.width/2;
        int coorY = taille.height/2 ;
        int size = getRootPane().getHeight();
        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i +30;
            angle_rad = ((Math.PI / 180) * angle_deg);
            p.addPoint((int) (coorX + size*Math.cos(angle_rad)), (int)(coorY + size * Math.sin(angle_rad)) );
        }
        g.setColor(Color.RED);
        g.fillPolygon(p);
    }
}
