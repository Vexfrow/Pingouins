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

        this.setLayout(new BorderLayout());

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
//        menuGame.setPreferredSize(new Dimension(getWidth()/4, getHeight()));
//        menuGame.setMinimumSize(new Dimension(getWidth()/4, getHeight()));
//        menuGame.setMaximumSize(new Dimension(getWidth()/4, getHeight()));
        menuGame.setLayout(new BoxLayout(menuGame, BoxLayout.Y_AXIS));

        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.X_AXIS));

        JButton bPause = new JButton(new ImageIcon(pause));
        bPause.setBorderPainted(false);
        bPause.setContentAreaFilled(false);
        bPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.togglePause(true);
            }
        });

        JButton bSuggestion = new JButton(new ImageIcon(suggestion));
        bSuggestion.setBorderPainted(false);
        bSuggestion.setContentAreaFilled(false);

        boutonPanel.add(bPause);

        boutonPanel.add(bSuggestion);

        menuGame.add(boutonPanel);

        messageTour.setText("C'est au tour du joueur " + jeu.getJoueurCourant());

        menuGame.add(messageTour);


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

            menuGame.add(mainP);
        }


        JButton bHistorique = new JButton("Historique");

        menuGame.add(bHistorique);




        JButton bAnnuler = new JButton(new ImageIcon(annuler));
        bAnnuler.setBorderPainted(false);
        bAnnuler.setContentAreaFilled(false);

        bAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.annule();
                misAJour(jeu);
            }
        });

        JButton bRefaire = new JButton(new ImageIcon(refaire));
        bRefaire.setBorderPainted(false);
        bRefaire.setContentAreaFilled(false);

        bRefaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.refaire();
                misAJour(jeu);
            }
        });
        JPanel boutonPanel2 = new JPanel();
        boutonPanel2.setLayout(new BoxLayout(boutonPanel2, BoxLayout.X_AXIS));

        boutonPanel2.add(bAnnuler);
        boutonPanel2.add(bRefaire);

        menuGame.add(boutonPanel2);

    }


    private void setGamePanel(){
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

}
