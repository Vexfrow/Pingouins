package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class Aide extends JPanel{

    private Image aideImg;
    private Image flecheLeft;
    private Image flecheRight;

    private JLabel image;
    private JButton flecheGauche;
    private JButton flecheDroite;


    public Aide(){
        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(0xFDCF76));

        try{
            aideImg = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/Aide.png"));
            flecheLeft = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGauche.png"));
            flecheRight = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroite.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        image = new JLabel();
        flecheGauche = new JButton();
        flecheDroite = new JButton();

        setAide();
    }

    public Image reScale(Dimension w, Image img){
        return img.getScaledInstance((int)(w.width*0.7), (int)(w.height), Image.SCALE_SMOOTH) ;

    }

    private void setAide(){
        flecheGauche.setContentAreaFilled(false);
        flecheGauche.setBorderPainted(false);

        flecheDroite.setContentAreaFilled(false);
        flecheDroite.setBorderPainted(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(image, gbc);


        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(flecheGauche, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        this.add(flecheDroite, gbc);

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        removeAll();
        this.revalidate();

        Dimension d = getRootPane().getContentPane().getSize();
        int width = (int)(d.width*(0.6));
        int height = (int)(d.height*(0.7));
        setSize(new Dimension(width, height));
        setAide();
        image.setIcon(new ImageIcon(aideImg));
        flecheGauche.setIcon(new ImageIcon(flecheLeft));
        flecheDroite.setIcon(new ImageIcon(flecheRight));



    }


}
