package Interface;

import javax.swing.*;
import Controleur.Controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Selection extends JPanel {
    private SpringLayout layout;
    private  JLabel menu;
    private Controleur c;
    private JButton retour;
    private JButton sauvegarde;
    private JButton valide;





    public Selection(Controleur ctrl){

        this.c = ctrl;
        retour = new JButton("<---");
        sauvegarde = new JButton("<html>Sauvegarder comme<br>option par défaut<br>(Partie rapide)</html>");
        valide = new JButton("<html> Lancer la Partie</html>");


        setSelection();
        this.repaint();

    }

    public void setSelection(){
        valide.setPreferredSize(new Dimension(200, 80));
        valide.setFont(new Font("Arial", Font.PLAIN, 20));
        valide.setBackground(new Color(60, 60, 100));
        sauvegarde.setPreferredSize(new Dimension(200, 80));
        retour.setBorderPainted(false);
        retour.setBackground(GameConstants.BACKGROUND_COLOR);


        this.setLayout(new GridBagLayout());
        this.setBackground(GameConstants.BACKGROUND_COLOR);
        //Dessin des hexagones
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        this.add(retour, gbc );


        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.gridwidth = 4;
        JLabel j = new JLabel("Selection des Joueurs");
        j.setFont(new Font("Arial", Font.BOLD, 50));
        j.setHorizontalTextPosition(SwingConstants.CENTER);
        j.setVerticalTextPosition(SwingConstants.CENTER);
        this.add(j, gbc);



        gbc.gridheight = 1;
        gbc.gridwidth =1;

        gbc.gridx = 0;
        gbc.gridy = 4;


        gbc.weightx = 1;
        gbc.weighty = 3;

        IconeSelection p1 = new IconeSelection(Color.BLUE, 100);
        this.add(p1, gbc);

        gbc.gridx = 1;
        IconeSelection p2 = new IconeSelection(Color.RED, 100);
        this.add(p2, gbc);

        gbc.gridx = 2;
        IconeSelection p3 = new IconeSelection(Color.YELLOW, 100);
        this.add(p3, gbc);

        gbc.gridx = 3;
        IconeSelection p4 = new IconeSelection(Color.GREEN, 100);
        this.add(p4, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.gridy = 5;
        gbc.gridx = 1;
        gbc.weighty = 1;
        this.add(sauvegarde, gbc);
        sauvegarde.setBackground(new Color(0x7292A4));
        sauvegarde.setForeground(Color.WHITE);
        gbc.gridx = 2;
        this.add(valide, gbc);
        valide.setBackground(new Color(0x155D85));
        valide.setForeground(Color.WHITE);









        //Action sur les boutons
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.switchMenu();
            }
        });
    }

}
