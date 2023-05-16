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
        slides = new JLabel[4];
        avancement = 0;
        panels = new Image[14];
        titres = new String[14];
        titre = new JLabel();
        try{
            flecheLeft = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGauche.png"));
            flecheRight = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroite.png"));
            flecheLeftVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleGaucheTransparente.png"));
            flecheRightVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRegleDroiteTransparente.png"));
            for(int i =1; i <= 15; i++){
                panels[i-1] = (Image) ImageIO.read(new FileInputStream("resources/assets/aide/panelRegles" + i +".png"));
            }
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
                super.componentResized(e);
                //resize();
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

        text = new JLabel();
        indications = new String[15];
        for(int i =0; i < 15; i++){
            indications[i]="here";
        }
        setTexts();
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

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(titre,gbc);



        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(image, gbc);

        gbc.gridy = 2;
        this.add(this.text, gbc);


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
        slide(0);
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
        flecheGauche.setIcon(new ImageIcon(reScale(flecheLeft, 0.08f, 0.1f)));
        flecheDroite.setIcon(new ImageIcon(reScale(flecheRight, 0.08f, 0.1f)));
        image.setIcon(new ImageIcon(reScale(panels[avancement], 0.6f, 0.5f)));

    }

    public void setTexts(){
        indications[0] = "<html>Hey, that’s my fish! est un jeu de 2 à 4 joueurs.<br> Vous incarnez des pingouins et le but du jeu est de manger le plus de poissons.</html>";
        titres[0] = "1. But du jeu:";

        indications[1] = "<html>Ce menu vous permet de choisir le nombre et le type de joueur (Humain, IA facile/medium/difficile). </html>";
        titres[1] = "2. Partie perso :";

        indications[2] = "<html>Vous pouvez reprendre une partie que vous avez sauvegardé au préalable. </html>";
        titres[2] =  "3. Charger :";

        indications[3] = "<html>La sauvegarde se fait en jeu, depuis le menu de pause.<br> Les noms des autres parties sauvegardées sont affichées. </html>";
        titres[3] = "4. Sauvegarder :";

        indications[4] ="<html>Un plateau est généré aléatoirement.<br> Il est composé de 60 cases hexagonales, elles peuvent contenir 1 (x30), 2 (x20) ou 3 (x10) poissons. </html>";
        titres[4] = "5. Plateau :";

        indications[5] = "<html>Chacun son tour, les joueurs vont placer leurs pingouins sur la banquise.<br> Vous ne pouvez les poser que sur des cases de 1 poisson.<br> Le nombre total de pingouins sur le plateau dépend du nombre de joueurs. </html>";
        titres[5] = "6. Placer :";

        indications[6] = "<html>Lors de la phase de jeu, cliquez sur le pingouin que vous souhaiter déplacer.<br> Vous ne pouvez le faire que dans une des 6 directions proposées par les tuiles hexagonales.<br> Le pingouin ne peut se déplacer qu’en ligne droite, et ne peut traverser des pingouins ou la mer.</html>";
        titres[6] = "7. Bouger pingouin :";

        indications[7] = "<html>Les poissons que vous avez collectés sont ajoutés à votre score quand votre pingouin quitte sa case.<br>La case disparaît et un trou non franchissable s’y trouve alors.</html>";
        titres[7] = "8. Collecte :";

        indications[8] = "<html>Si un pingouins est bloqué sur une tuile, il ne peut plus être déplacé et disparaît en récupérant ses poissons.<br> Si tous les pingouins d’un joueur sont indisponibles, il passe ses tours.</html>";
        titres[8] = "9. Pingouin bloqué :";

        indications[9] = "<html>Quand plus aucun pingouin ne peut bouger, le jeu est terminé.<br> Les derniers poissons sont comptabilisés et le joueur avec le plus haut score gagne.</html>";
        titres[9] = "10. Fin de jeu:";

        indications[10] = "<html>En cas d’égalité, le nombre de tuiles que les pingouins ont retiré est ajouté aux scores pour départager les joueurs.</html>";
        titres[10] = "11. Égalité :";

        indications[11] ="<html>Si vous voulez annuler ou refaire le placement de votre pingouin ou votre dernier coup,<br> des flèches se trouvent en bas à droite de l’écran.<br Un historique (bord droit de l’écran) permet de revenir plus loin dans la partie.</html>" ;
        titres[11] = "12. Annuler :";

        indications[12] ="<html>Si vous ne savez pas quoi jouer, cliquez sur l’icône de suggestion, en haut à droite.<br> Un conseil de jeu s’affichera.</html>";
        titres[12] = "13. Indice :";

        indications[13] = "<html>Choisissez votre stratégie et soyez sans pitié!<br> C'est MON poisson!</html>";
        titres[13] = "14. C'est parti :";






    }



}
