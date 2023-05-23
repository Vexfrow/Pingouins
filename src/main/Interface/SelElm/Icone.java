package Interface.SelElm;
import Interface.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class Icone extends JPanel{
    private int size;
    private boolean actif;
    private Color col;
    private Image ping;
    private JLabel pingouin;
    private int indice;


    public Icone(int i){
        setOpaque(true);
        setLayout(new GridLayout(1,1));
        actif = true;
        indice = i;
        pingouin = new JLabel();
        pingouin.setHorizontalAlignment(SwingConstants.CENTER);
        pingouin.setVerticalAlignment(SwingConstants.CENTER);
        add(pingouin);
        this.setBackground(GameConstants.BACKGROUND_COLOR);
    }

    public void setSize(int s){
        this.size =s;
    }


    public void setColor(Color c){
        this.col = c;

    }

    public void setActif(boolean t){
        actif = t;
    }

    public void refresh(){
        if(actif){
            pingouin.setVisible(true);
            switch (indice){
                case 0:
                    pingouin.setIcon(new ImageIcon(GameConstants.pingouinBleu.getScaledInstance(size*2, size*2, Image.SCALE_SMOOTH)));
                    break;
                case 1:
                    pingouin.setIcon(new ImageIcon(GameConstants.pingouinRouge.getScaledInstance(size*2, size*2, Image.SCALE_SMOOTH)));
                    break;
                case 2:
                    pingouin.setIcon(new ImageIcon(GameConstants.pingouinVert.getScaledInstance(size*2, size*2, Image.SCALE_SMOOTH)));
                    break;
                case 3:
                    pingouin.setIcon(new ImageIcon(GameConstants.pingouinJaune.getScaledInstance(size*2, size*2, Image.SCALE_SMOOTH)));
                    break;

            }

        }else{
            pingouin.setVisible(false);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        if(actif){
            refresh();
        }else{
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
        }


    }
}
