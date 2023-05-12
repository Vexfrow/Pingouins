package Interface.Panes;

import Interface.GameConstants;
import Interface.SaveElm.SaveList;
import Vue.CollecteurEvenements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class Sauvegarde extends JPanel {

    private SaveList listeSave;
    private JLabel titre;
    private JButton valider;
    private JButton retour;
    private CollecteurEvenements collecteur;
    private GridBagLayout layout;
    private Image flecheRetour;
    public Sauvegarde(CollecteurEvenements c){
        //setBorder(new EmptyBorder(50, 50, 50, 50));
        setBackground(GameConstants.BACKGROUND_COLOR);

        try{
            flecheRetour = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheRetour.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }
        collecteur = c;
        listeSave = new SaveList();
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
        //gbc.insets = new Insets(50,30, 0, 30);
        add(retour, gbc);


        //
        gbc.gridx = 0;
        gbc.gridy =1;
        gbc.gridwidth =  5;
        gbc.weightx = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        //gbc.insets = new Insets(0,0, 0, 0);
        add(titre, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 3;
        //gbc.insets = new Insets(0,50, 0, 150);
        add(listeSave, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.NONE;
        add(valider, gbc);


        //valider.setPreferredSize(new Dimension(200, 80));

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String res = listeSave.getText();
                c.save(res);
                collecteur.togglePause(false);
            }
        });

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecteur.togglePause(false);
            }
        });

    }

    public Image reScale(float percent, Image img){
        return img.getScaledInstance((int)(img.getWidth(null)*percent), (int)(img.getHeight(null)*percent), Image.SCALE_FAST  );
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        retour.setIcon(new ImageIcon(reScale(0.5f, flecheRetour )));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 3;
        gbc.insets = new Insets(0, (int)(getWidth()*0.2), 0 , (int)(getWidth()*0.2 ));
        layout.setConstraints(listeSave, gbc);
    }
}
