package Interface.Panes;

import Interface.GameConstants;
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

    private Image flecheLeft;
    private Image flecheRight;
    private Image flecheLeftVide;
    private Image flecheRightVide;
    private CollecteurEvenements collecteur;
    private JLabel image;
    private JButton flecheGauche;
    private JButton flecheDroite;
    private JButton sortie;
    private Image panels[];
    private int avancement;
    private JLabel text;
    private String indications[];
    private JLabel titre;
    private String[] titres;


    public Aide(CollecteurEvenements c){
        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(0xFDCF76));
        avancement = 0;
        panels = new Image[11];
        titres = new String[11];
        titre = new JLabel();
        text = new JLabel();
        try{
            flecheLeft = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGauche.png"));
            flecheRight = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroite.png"));
            flecheLeftVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGaucheTransparente.png"));
            flecheRightVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroiteTransparente.png"));
            for(int i =1; i <= panels.length; i++){
                panels[i-1] = (Image) ImageIO.read(new FileInputStream("resources/assets/aide/panelRegles" + i +".png"));
            }
        }catch(Exception e){
            System.err.println("une erreur " + e);
        }
        image = new JLabel();
        flecheGauche = new JButton();
        flecheDroite = new JButton();
        sortie = new JButton("X");
        collecteur = c;

        setAide();

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
        titre.setFont(new Font("Arial", Font.BOLD, 30));
        text.setFont(new Font("Arial", Font.PLAIN, 15));
        indications = new String[15];
        for(int i =0; i < indications.length; i++){
            indications[i]="here";
        }
        setTexts();
        sortie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(collecteur.getEtatBackPane() == 2){
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
                resize();
                revalidate();
            }
        });

        flecheDroite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slide(1);
                majFleche();
                resize();
                revalidate();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(titre,gbc);



        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        this.add(image, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        this.add(this.text, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        this.add(flecheGauche, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        this.add(flecheDroite, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(sortie, gbc);
        flecheGauche.setEnabled(!debut());
        flecheDroite.setEnabled(!fin());
        slide(0);
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resize();
                //revalidate();
            }
        });
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
        }else if(avancement >= panels.length ){
            avancement = panels.length-1;
        }
        this.image.setIcon(new ImageIcon(panels[avancement]));
        this.titre.setText(titres[avancement]);
        this.text.setText(indications[avancement]);
    }

    public boolean debut(){
        return avancement == 0;
    }

    public boolean fin(){
        return avancement == panels.length-1;
    }

    public void majFleche(){
        flecheGauche.setEnabled(!debut());
        flecheDroite.setEnabled(!fin());
    }

    public void resize(){
        flecheGauche.setIcon(new ImageIcon(reScale(flecheLeft, 0.06f, 0.04f)));
        flecheDroite.setIcon(new ImageIcon(reScale(flecheRight, 0.06f, 0.04f)));
        flecheGauche.setDisabledIcon(new ImageIcon(reScale(flecheLeftVide, 0.06f, 0.04f)));
        flecheDroite.setDisabledIcon(new ImageIcon(reScale(flecheRightVide, 0.06f, 0.04f)));
        image.setIcon(new ImageIcon(reScale(panels[avancement], 0.6f, 0.5f)));
    }

    public void setTexts(){
        int i = 0;
        indications[i] = "<html>Hey, that’s my fish! est un jeu de 2 à 4 joueurs.<br> Vous incarnez une équipe de pingouin et le but du jeu est de manger le plus de poissons.</html>";
        titres[i] = "But du jeu";
        i++;
        indications[i] ="<html>Un plateau est généré aléatoirement.<br> Il est composé de 60 cases hexagonales, pouvant contenir 1 (x30), 2 (x20) ou 3 (x10) poissons. </html>";
        titres[i] = "Plateau";
        i++;
        indications[i] = "<html>Chacun son tour, les joueurs vont placer leurs pingouins, en cliquant sur la banquise.<br> Vous ne pouvez les poser que sur des cases de 1 poisson.<br> Le nombre total de pingouins sur le plateau dépend du nombre de joueurs. </html>";
        titres[i] = "Placer";
        i++;
        indications[i] = "<html>Lors de la phase de jeu, cliquez sur le pingouin que vous souhaitez déplacer.<br>Les déplacements se font dans les 6 directions proposées par les tuiles hexagonales sur toute la ligne.<br> Le pingouin ne peut se déplacer qu’en ligne droite, et ne peut traverser des pingouins ou la mer.</html>";
        titres[i] = "Bouger pingouin";
        i++;
        indications[i] = "<html>Les poissons que vous avez collectés sont ajoutés à votre score quand votre pingouin quitte sa case.<br>La case disparaît et un trou non franchissable s’y trouve alors.</html>";
        titres[i] = "Collecte";
        i++;
        indications[i] = "<html>Si un pingouin est bloqué sur une tuile, il ne peut plus être déplacé et <br>disparaît en récupérant ses poissons.<br> Si tous les pingouins d’un joueur sont indisponibles, il passe ses tours.</html>";
        titres[i] = "Pingouin bloqué";
        i++;
        indications[i] = "<html>Quand plus aucun pingouin ne peut bouger, le jeu est terminé.<br> Les derniers poissons sont comptabilisés et le joueur avec le plus haut score gagne.</html>";
        titres[i] = "Fin de jeu";
        i++;
        indications[i] = "<html>En cas d’égalité, le nombre de tuiles que les pingouins ont retiré <br>est ajouté aux scores pour départager les joueurs.</html>";
        titres[i] = "Égalité";
        i++;
        indications[i] ="<html>Si vous voulez annuler ou refaire votre dernier coup, des flèches se trouvent en bas à droite de l’écran.<br> La flèche de droite refait, celle de gauche annule</html>" ;
        titres[i] = "Annuler";
        i++;
        indications[i] ="<html>Si vous ne savez pas quoi jouer, cliquez sur l’icône de suggestion, en haut à droite.<br> Un conseil de jeu s’affichera.</html>";
        titres[i] = "Indice";
        i++;
        indications[i] = "<html>Choisissez votre stratégie et soyez sans pitié!<br> C'est MON poisson!</html>";
        titres[i] = "C'est parti";
    }



}
