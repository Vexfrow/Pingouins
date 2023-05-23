package Interface.Panes;

import Interface.ChargeElm.ListeFile;
import Interface.ChargeElm.Preview;
import Interface.GameConstants;
import Model.Jeu;
import Joueur.*;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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

    public Chargement(CollecteurEvenements c){
        File d = new File(GameConstants.DOSSIER_SAVE);
        d.mkdir();

        collecteurEvenements = c;
        setLayout(new GridBagLayout());
        setBackground(GameConstants.BACKGROUND_COLOR);
        lf = new ListeFile(this);
        preview = new Preview();
        GridBagConstraints gbc = new GridBagConstraints();
        retour = new JButton();
        valider = new JButton();
        titre = new JLabel("Chargement de partie");
        selection = new JPanel(new GridLayout(1, 2));
        fichier = "";
        selection.add(lf);
        selection.add(preview);

        retour.setContentAreaFilled(false);
        retour.setBorderPainted(false);

        valider.setBorderPainted(false);
        valider.setContentAreaFilled(false);


        gbc.gridx =0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(retour, gbc);

        gbc.gridx =0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        add(titre, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 2;
        gbc.weightx = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        add(selection, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(valider, gbc);
        retour.setIcon(new ImageIcon(GameConstants.flecheRetour));
        valider.setIcon(new ImageIcon(GameConstants.valider));
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
                collecteurEvenements.activateGameBoard();
            }
        });


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                retour.setIcon(new ImageIcon(reScale(GameConstants.flecheRetour, 0.05, 0.04)));
                valider.setIcon(new ImageIcon(reScale(GameConstants.valider, 0.3, 0.12)));
                titre.setFont(new Font("Arial", Font.BOLD, (int)(getHeight()*0.06)));
            }
        });

    }

    public Image reScale(Image img, double coefX, double coefY){
        return img.getScaledInstance((int)(getWidth()*coefX), (int)(getHeight()*coefY), Image.SCALE_FAST);
    }

    public void changePreview(String s){
        fichier = GameConstants.DOSSIER_SAVE + s+ ".txt";
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
                        ari.add(new IAFacile(j));
                        break;
                    case 2:
                        ari.add(new IAMoyen(j));
                        break;
                    case 3:
                        ari.add(new IADifficile(j));
                        break;
                    case 4:
                        ari.add(new IAExpert(j));
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
        return ari;
    }


}
