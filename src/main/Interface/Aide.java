package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class Aide extends JComponent{

    private Image aideImg;
    private JLabel image;
    public Aide(){
        setLayout(new BorderLayout());
        image = new JLabel();
        try{
            aideImg = (Image) ImageIO.read(new FileInputStream("resources/assets/menus/Aide.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }

        image.setIcon(new ImageIcon(aideImg));
        this.add(image);

    }

    @Override
    protected void paintComponent(Graphics g){

        Dimension d = getRootPane().getContentPane().getSize();
        int width = (int)(d.width * (0.7));
        int height = (int)(d.height* (0.7));
        setLocation((int)(d.width*(0.15)), (int)(d.height*(0.15)));
        setSize(width, height);
        image.setLocation(width/2 - (image.getSize().width/2), height/2 - (image.getSize().height/2));
    }


}
