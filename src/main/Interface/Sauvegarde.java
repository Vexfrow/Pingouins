package Interface;

import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class Sauvegarde extends JPanel {

    private JComboBox listeSave;
    private JLabel titre;
    private JButton valider;
    private JButton retour;
    private CollecteurEvenements collecteur;
    private GridBagLayout layout;

    public Sauvegarde(CollecteurEvenements c){
        setBackground(GameConstants.BACKGROUND_COLOR);
        Image flecheRetour = null;
        try{
            flecheRetour = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRetour.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        collecteur = c;
        listeSave = new JComboBox();
        titre = new JLabel("Sauvegarde de la partie actuelle");
        valider = new JButton("Valider");
        retour = new JButton(new ImageIcon(flecheRetour));

        retour.setContentAreaFilled(false);
        retour.setBorderPainted(false);

        valider.setBorderPainted(false);

        layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(retour, gbc);

        gbc.gridx = 0;
        gbc.gridy =1;
        gbc.gridwidth =  5;
        gbc.weightx = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        add(titre, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.weightx = 3;
        add(listeSave, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.NONE;
        add(valider, gbc);


        valider.setPreferredSize(new Dimension(200, 80));

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.togglePause(false);
            }
        });

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
