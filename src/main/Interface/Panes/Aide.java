package Interface.Panes;

import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;

public class Aide extends JPanel{

    private Image aideImg;
    private Image flecheLeft;
    private Image flecheRight;
    private CollecteurEvenements collecteur;
    private JLabel image;
    private JButton flecheGauche;
    private JButton flecheDroite;
    private JButton sortie;


    public Aide(CollecteurEvenements c){
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
        sortie = new JButton("X");
        collecteur = c;

        setAide();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                image.setIcon(new ImageIcon(reScale(aideImg, 0.6f, 0.5f)));
                flecheGauche.setIcon(new ImageIcon(reScale(flecheLeft, 0.08f, 0.1f)));
                flecheDroite.setIcon(new ImageIcon(reScale(flecheRight, 0.08f, 0.1f)));
            }
        });
    }

    public Image reScale(Dimension w, Image img){
        return img.getScaledInstance((int)(w.width*0.7), (int)(w.height), Image.SCALE_SMOOTH) ;

    }

    private void setAide(){
        flecheGauche.setContentAreaFilled(false);
        flecheGauche.setBorderPainted(false);

        flecheDroite.setContentAreaFilled(false);
        flecheDroite.setBorderPainted(false);

        sortie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(collecteur.getEtatBackPane() == 2){
                    System.out.println("Here");
                    collecteur.togglePause(false);

                }else{
                    collecteur.toggleHelp(true);
                }

            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(image, gbc);


        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(flecheGauche, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        this.add(flecheDroite, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(sortie, gbc);


    }

    public Image reScale(Image img, float rapportX, float rapportY){
        Dimension d = new Dimension((int)(getWidth()*rapportX), (int)(getHeight()*rapportY) );
        Image neoImg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH) ;
        return neoImg;
    }



}
