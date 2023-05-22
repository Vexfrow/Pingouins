package Interface.SelElm;
import Interface.GameConstants;

import javax.swing.*;
import java.awt.*;

public class Icone extends JPanel{
    private int size;

    private Color col;
    public Icone(){
        setOpaque(true);
        this.setBackground(GameConstants.BACKGROUND_COLOR);
    }

    public void setSize(int s){
        this.size =s;
    }


    public void setColor(Color c){
        this.col = c;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        Polygon p = new Polygon();
        double angle_deg;
        double angle_rad;
        int coorX = getWidth()/2;
        int coorY = getHeight()/2;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i +30;
            angle_rad = ((Math.PI / 180) * angle_deg);
            p.addPoint((int) (coorX + size*Math.cos(angle_rad)), (int)(coorY + size * Math.sin(angle_rad)) );
        }
        gd.setColor(col);
        gd.fillPolygon(p);
        //gd.drawRect(0,0, getWidth()-1, getHeight()-1);
    }
}
