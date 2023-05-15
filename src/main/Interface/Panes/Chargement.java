package Interface.Panes;

import Interface.ChargeElm.ListeFile;
import Interface.ChargeElm.Preview;
import Interface.GameConstants;
import Model.Jeu;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class Chargement extends JPanel {

    private CollecteurEvenements collecteurEvenements;
    private ListeFile lf;
    private Preview preview;
    private JButton retour;
    private JButton valider;
    private JLabel titre;
    private JPanel selection;

    public Chargement(CollecteurEvenements c){
        collecteurEvenements = c;
        setLayout(new GridBagLayout());
        setBackground(GameConstants.BACKGROUND_COLOR);
        lf = new ListeFile(this);
        preview = new Preview();
        GridBagConstraints gbc = new GridBagConstraints();
        retour = new JButton();
        valider = new JButton("Valider");
        titre = new JLabel("Chargement de partie");
        selection = new JPanel(new GridLayout(1, 2));

        selection.add(lf);
        selection.add(preview);

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

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(collecteurEvenements.getEtatBackPane() == 2){
                    System.out.println("Here");
                    collecteurEvenements.togglePause(false);

                }else{
                    collecteurEvenements.toggelCharge(true);
                }
            }
        });

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteurEvenements.setJeu(new Jeu("resources/sauvegarde/" + lf.getCurrent() + ".txt"));
                collecteurEvenements.switchGameBoard();
            }
        });


    }

    public void changePreview(String s){
        preview.setPreview(s);
    }

}
