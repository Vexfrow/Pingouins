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
    private Image flecheLeftVide;
    private Image flecheRightVide;
    private CollecteurEvenements collecteur;
    private JLabel image;
    private JButton flecheGauche;
    private JButton flecheDroite;
    private JButton sortie;
    private JLabel slides[];
    private int avancement;


    public Aide(CollecteurEvenements c){
        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(0xFDCF76));
        slides = new JLabel[4];
        avancement = 0;
        try{
            aideImg = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/Aide.png"));
            flecheLeft = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGauche.png"));
            flecheRight = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroite.png"));
            flecheLeftVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGaucheTransparente.png"));
            flecheRightVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroiteTransparente.png"));
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



    private void setAide(){
        flecheGauche.setContentAreaFilled(false);
        flecheGauche.setBorderPainted(false);
        flecheGauche.setDisabledIcon(new ImageIcon(flecheLeftVide));
        flecheGauche.setIcon(new ImageIcon(flecheLeft));

        flecheDroite.setContentAreaFilled(false);
        flecheDroite.setBorderPainted(false);
        flecheDroite.setDisabledIcon(new ImageIcon(flecheRightVide));
        flecheDroite.setIcon(new ImageIcon(flecheRight));

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

        flecheGauche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slide(-1);
                majFleche();
            }
        });

        flecheDroite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slide(1);
                majFleche();
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
        majFleche();


    }

    public Image reScale(Image img, float rapportX, float rapportY){
        Dimension d = new Dimension((int)(getWidth()*rapportX), (int)(getHeight()*rapportY) );
        Image neoImg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH) ;
        return neoImg;
    }

    public void slide(int left){
        avancement += left;
        if(avancement < 0){
            avancement = 0;
        }
        this.image = slides[avancement];
    }

    public boolean debut(){
        return avancement == 0;
    }

    public boolean fin(){
        return avancement == slides.length-1;
    }

    public void majFleche(){
        flecheGauche.setEnabled(!debut());
        flecheDroite.setEnabled(!fin());
    }


}
