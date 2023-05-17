package Interface.ChargeElm;

import Interface.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;

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
                super.componentResized(e);
                if(screen != null){
                    visuel.setIcon(new ImageIcon(reScale(screen)));
                }

            }
        });


    }

    public void setPreview(String name){
        removeAll();

        FileReader reader = null;
        try {
            reader = new FileReader("resources/sauvegarde/"+ name+".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "0";
        int nbJoueur = 0;
        int scores[];
        int cases[];
        int types[];
        try {
            line = bufferedReader.readLine();
            nbJoueur = Integer.parseInt(line);
            bufferedReader.readLine(); //irrelevant
            bufferedReader.readLine(); //irrelevant
            scores = new int[nbJoueur];
            types = new int[nbJoueur];
            cases = new int[nbJoueur];

            for(int i =0; i < nbJoueur; i++){
                scores[i] = Integer.parseInt(bufferedReader.readLine());
            }
            for(int i =0; i < nbJoueur; i++){
                cases[i] = Integer.parseInt(bufferedReader.readLine());
            }
            for(int i =0; i < nbJoueur; i++){
                types[i] = Integer.parseInt(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        players = new JLabel[nbJoueur];
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 50);

        for(int i = 0; i < nbJoueur; i++){
            players[i] = new JLabel(""+ i);
            players[i].setOpaque(true);
            players[i].setFont(new Font("Arial", Font.BOLD, 20 ));
            switch (i){
                case 0:
                    players[i].setBackground(GameConstants.BLEU_CLAIR);
                    break;
                case 1:
                    players[i].setBackground(GameConstants.ROUGE_CLAIR);
                    break;
                case 2:
                    players[i].setBackground(GameConstants.JAUNE_CLAIR);
                    break;
                case 3:
                    players[i].setBackground(GameConstants.VERT_CLAIR);
                    break;

            }
            String categories = "";
            switch(types[i]){
                case 0:
                    categories = "Humain :";
                    break;
                case 1:
                    categories = "IA Facile :";
                    break;
                case 2:
                    categories = "IA Moyenne :";
                    break;
                case 3:
                    categories = "IA Difficile :";
                    break;
            }
            categories += " Score = " + scores[i] + " Cases = " + cases[i];
            players[i].setText(categories);
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
        gbc.gridx = 0;
        gbc.weighty = 2;
        gbc.weightx = 1;
        add(visuel, gbc);


    }

    public Image reScale(Image img){
        return img.getScaledInstance((int)(getWidth()*0.8),(int)(getHeight()*0.6), Image.SCALE_AREA_AVERAGING);
    }


}
