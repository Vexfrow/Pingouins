package Interface.Panes;

import Interface.ChargeElm.ListeFile;
import Interface.ChargeElm.Preview;
import Interface.GameConstants;
import Model.Jeu;
import Joueur.*;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.ArrayList;


public class Chargement extends JPanel {

    private CollecteurEvenements collecteurEvenements;
    private ListeFile lf;
    private Preview preview;
    private JButton retour;
    private JButton valider;
    private JLabel titre;
    private JPanel selection;
    private String fichier;
    private Image img;

    public Chargement(CollecteurEvenements c){
        File d = new File("resources/sauvegarde");
        d.mkdir();
        try{
            img = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRetour.png"));
        }catch(Exception e){
            System.err.println(e);
        }
        collecteurEvenements = c;
        setLayout(new GridBagLayout());
        setBackground(GameConstants.BACKGROUND_COLOR);
        lf = new ListeFile(this);
        preview = new Preview();
        GridBagConstraints gbc = new GridBagConstraints();
        retour = new JButton();
        valider = new JButton("Valider");
        titre = new JLabel("<html><p align=\"center\">Chargement de partie</p></html>");
        selection = new JPanel(new GridLayout(1, 2));

        selection.add(lf);
        selection.add(preview);

        retour.setContentAreaFilled(false);
        retour.setBorderPainted(false);

        gbc.gridx =0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(retour, gbc);

        gbc.gridx =1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        add(titre, gbc);


        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 2;
        gbc.weightx = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        add(selection, gbc);


        gbc.weighty = 0;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(valider, gbc);
        retour.setIcon(new ImageIcon(img));
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(collecteurEvenements.getEtatBackPane() == 2){
                    collecteurEvenements.togglePause(false);

                }else{
                    collecteurEvenements.toggelCharge(true);
                }
            }
        });

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteurEvenements.toggelCharge(true);
                genererPartie();
            }
        });
        fichier = "";

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                retour.setIcon(new ImageIcon(reScale(img, 0.06, 0.05)));
            }
        });

    }

    public Image reScale(Image img, double coefX, double coefY){
        return img.getScaledInstance((int)(getWidth()*coefX), (int)(getHeight()*coefY), Image.SCALE_FAST);
    }

    public void changePreview(String s){
        fichier = "resources/sauvegarde/"+ s+ ".txt";
        preview.setPreview(s);
    }

    public void genererPartie(){
        Jeu j = new Jeu(fichier);
        ArrayList<IAJoueur> ari = getIA(j, fichier);
        collecteurEvenements.setJeu(j, ari);
        collecteurEvenements.switchGameBoard();
    }

    public ArrayList<IAJoueur> getIA(Jeu j, String s){
        ArrayList<IAJoueur> ari = new ArrayList<IAJoueur>();
        FileReader reader = null;
        try {
            reader = new FileReader(s);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String line = bufferedReader.readLine();
            int nbJoueur = Integer.parseInt(line);
            bufferedReader.readLine(); //irrelevant
            bufferedReader.readLine(); //irrelevant
            int[] types = new int[nbJoueur];



            for(int i =0; i < nbJoueur*2; i++){
                bufferedReader.readLine();
            }
            for(int i =0; i < nbJoueur; i++){
                types[i] = Integer.parseInt(bufferedReader.readLine());
            }

            for(int i =0; i < nbJoueur; i++){
                switch(types[i]){
                    case 0:
                        ari.add(null);
                        break;
                    case 1:
                        ari.add(new IAAleatoire(j));
                        break;
                    case 2:
                        ari.add(new IATroisPoissons(j));
                        break;
                    case 3:
                        ari.add(new IAMinimax(j));
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Chargement : NB J" + ari.size());
        return ari;
    }

}
