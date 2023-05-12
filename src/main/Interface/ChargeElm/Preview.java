package Interface.ChargeElm;

import Interface.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;

public class Preview extends JPanel {
    JLabel visuel;
    JLabel[]  players;
    Image screen;


    public Preview(){
        setBackground(GameConstants.BACKGROUND_COLOR);
        setLayout(new GridBagLayout());
        visuel = new JLabel("Choisissez une partie");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                visuel.setIcon(new ImageIcon(reScale(screen)));
            }
        });


    }

    public void setPreview(String name){
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();

        int nbJoueur = 4;
        players = new JLabel[nbJoueur];
        gbc.gridy = 1;
        for(int i = 0; i < nbJoueur; i++){
            players[i] = new JLabel(""+ i);
            switch (i){
                case 0:
                    players[i].setBackground(GameConstants.ROUGE);

                    break;
                case 1:
                    players[i].setBackground(GameConstants.BLEU);
                    break;
                case 2:
                    players[i].setBackground(GameConstants.JAUNE);
                    break;
                case 3:
                    players[i].setBackground(GameConstants.VERT);
                    break;

            }
            add(players[i], gbc);
            gbc.gridy++;
        }
        screen = null;
        try{
            screen = (Image) ImageIO.read(new FileInputStream("resources/sauvegarde/"+ name + ".png"));
        }catch(Exception e){
            System.err.println("une erreur " + e);
        }
        visuel.setText("");
        visuel.setIcon(new ImageIcon(reScale(screen)));

        gbc.gridy = 0;
        gbc.weighty = 2;
        add(visuel, gbc);
        revalidate();
        repaint();


    }

    public Image reScale(Image img){
        return img.getScaledInstance((int)(getWidth()*0.7),(int)(getHeight()*0.4), Image.SCALE_SMOOTH);
    }


}
