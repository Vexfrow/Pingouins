package Interface;

import Model.Jeu;
import Vue.BanquiseGraphique;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class GameBoard extends JPanel {

    BanquiseGraphique bq;

    JPanel gamePanel;

    JPanel menuGame;

    CollecteurEvenements collecteur;

    Label messageTour;

    Jeu jeu;

    private JButton bPause;
    private JButton bSuggestion;

    private ArrayList<JTextArea> listScore;
    BufferedImage poisson, hexagone, annuler, refaire, pause, suggestion;

    private ArrayList<JLabel> listScoreP;
    private ArrayList<JLabel> listScoreH;



    public GameBoard(Jeu j, CollecteurEvenements c){
        bq = new BanquiseGraphique(j);
        gamePanel = new JPanel();
        menuGame = new JPanel();
        messageTour = new Label();

        listScoreH = new ArrayList<>();
        listScoreP = new ArrayList<>();

        jeu = j;

        collecteur = c;
        collecteur.setPlateauJeu(this);
        collecteur.setJeu(j);

        poisson = chargeImage("poisson");
        hexagone = chargeImage("hexagone");
        suggestion = chargeImage("boutonAstuce");
        pause = chargeImage("boutonPause");
        annuler = chargeImage("boutonAnnuler");
        refaire = chargeImage("boutonRefaire");


        setMenuGame();
        setGamePanel();
    }



    private BufferedImage chargeImage(String nom){
        try {
            InputStream in = new FileInputStream("resources/assets/jeu/" + nom + ".png");
            return ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("Fichier \"" + nom + "\" introuvable");
        }
        return null;
    }


    private void setMenuGame(){

        menuGame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
//        c.gridwidth = 2;
//        c.gridheight = 4 + jeu.getListeJoueur().size();

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        //----------------Boutons Pause et suggestion-------------
        bPause = new JButton(new ImageIcon(pause));
        bPause.setBorderPainted(false);
        bPause.setContentAreaFilled(false);
        bPause.addActionListener(e -> collecteur.togglePause(true));

        bSuggestion = new JButton(new ImageIcon(suggestion));
        bSuggestion.setBorderPainted(false);
        bSuggestion.setContentAreaFilled(false);


        boutonPanel.add(bPause);
        boutonPanel.add(bSuggestion);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        menuGame.add(boutonPanel, c);




        //----------------Message-------------
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        menuGame.add(messageTour, c);



        //----------------Scores-------------
        ImageIcon iiP = new ImageIcon(poisson);
        Image image = iiP.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        iiP = new ImageIcon(newimg);

        ImageIcon iiH = new ImageIcon(hexagone);
        image = iiH.getImage(); // transform it
        newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        iiH = new ImageIcon(newimg);

        for(int i = 0; i < jeu.getListeJoueur().size();i++){

            JLabel jlP = new JLabel(iiP);
            JLabel jlH = new JLabel(iiH);

            JPanel mainP = new JPanel();
            mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
            mainP.setBorder(new LineBorder(Color.BLACK));
            mainP.setPreferredSize(new Dimension(menuGame.getWidth(), menuGame.getHeight()));

            JPanel imageP = new JPanel();
            imageP.setLayout(new BoxLayout(imageP, BoxLayout.X_AXIS));

            JPanel poissonP = new JPanel();
            poissonP.setLayout(new BoxLayout(poissonP, BoxLayout.Y_AXIS));

            JPanel hexaP = new JPanel();
            hexaP.setLayout(new BoxLayout(hexaP, BoxLayout.Y_AXIS));

            imageP.add(poissonP);
            imageP.add(hexaP);
            mainP.add(imageP);

            JLabel textArea = new JLabel("Joueur "+(i+1));
            if(i == 3)
                textArea.setForeground(Color.BLACK);
            else
                textArea.setForeground(Color.WHITE);

            mainP.add(textArea);
            mainP.add(imageP);

            JLabel scoreP = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getScore()));
            poissonP.add(jlP);
            poissonP.add(scoreP);
            listScoreP.add(scoreP);

            JLabel scoreH = new JLabel(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
            hexaP.add(jlH);
            hexaP.add(scoreH);
            listScoreH.add(scoreH);


            imageP.add(poissonP);
            imageP.add(hexaP);

            c.gridx = 0;
            c.gridy = 2+i;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.CENTER;
            menuGame.add(mainP, c);
        }


        JButton bHistorique = new JButton("Historique");

        c.gridx = 0;
        c.gridy = 3+jeu.getListeJoueur().size();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        menuGame.add(bHistorique,c);



        //----------Boutons annuler et refaire --------------
        JButton bAnnuler = new JButton(new ImageIcon(annuler));
        bAnnuler.setBorderPainted(false);
        bAnnuler.setContentAreaFilled(false);

        bAnnuler.addActionListener(e -> {
            jeu.annule();
            misAJour(jeu);
        });

        JButton bRefaire = new JButton(new ImageIcon(refaire));
        bRefaire.setBorderPainted(false);
        bRefaire.setContentAreaFilled(false);

        bRefaire.addActionListener(e -> {
            jeu.refaire();
            misAJour(jeu);
        });

        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bRefaire);


        c.gridx = 0;
        c.gridy = 4+jeu.getListeJoueur().size();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        menuGame.add(boutonPanel2, c);

    }


    private void setGamePanel(){
        this.setLayout(new BorderLayout());

        this.add(bq, BorderLayout.CENTER);

        this.add(menuGame, BorderLayout.EAST);
        this.setBackground(Color.CYAN);
    }


    public void misAJour(Jeu j){
        jeu = j;
        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());
        for(int i = 0; i < jeu.getListeJoueur().size();i++){
           listScoreH.get(i).setText(String.valueOf(jeu.getListeJoueur().get(i).getNbCasesMange()));
           listScoreP.get(i).setText(String.valueOf(jeu.getListeJoueur().get(i).getScore()));

        }



        bq.misAJour(jeu);
    }


    public BanquiseGraphique getBq(){
        return bq;
    }

    public void toggleButton(){
        bPause.setEnabled(!bPause.isEnabled());
        bSuggestion.setEnabled(!bSuggestion.isEnabled());

    }



}
