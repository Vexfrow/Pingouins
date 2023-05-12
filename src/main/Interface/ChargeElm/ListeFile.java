package Interface.ChargeElm;

import Interface.GameConstants;
import Interface.Panes.Chargement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

public class ListeFile extends JPanel {

    Chargement panel;
    String[] listeFichier;
    JButton[] affichage;
    private String current;
    private Image up;
    private Image down;
    private Image upVide;
    private Image downVide;

    private JButton flecheHaut;
    private JButton flecheBas;

    private int sommet;

    public ListeFile(Chargement c){
        panel = c;
        sommet = 0;
        listeFichier = getAllSaves();
        affichage = new JButton[5];
        flecheHaut = new JButton();
        flecheBas = new JButton();
        current = "";
        for(int i = 0; i < 5; i++){
            if(i < listeFichier.length){
                affichage[i] = new JButton(listeFichier[i]);
                affichage[i].setBackground(GameConstants.SELECTION);
                affichage[i].setBorderPainted(false);
            }else{
                affichage[i] = new JButton("");
                affichage[i].setBackground(GameConstants.SELECTION);
                affichage[i].setBorderPainted(false);
            }
        }

        try{
            up = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeHaut.png"));
            down = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeBas.png"));
            upVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeHautTransparente.png"));
            downVide = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeBasTransparente.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }


        flecheHaut.setIcon(new ImageIcon(up));
        flecheHaut.setBorderPainted(false);
        flecheHaut.setContentAreaFilled(false);
        flecheBas.setIcon(new ImageIcon(down));
        flecheBas.setBorderPainted(false);
        flecheBas.setContentAreaFilled(false);
        setLayout(new GridBagLayout());
        setBackground(GameConstants.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(flecheHaut, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(flecheBas, gbc);

        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 2;
        gbc.weightx = 2;
        for(int i = 0; i < 5; i++){
            add(affichage[i], gbc);
            gbc.gridy++;
        }
        majFleche();
        flecheHaut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBorder();
                majListe(true);
                majFleche();
            }
        });

        flecheBas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBorder();
                majListe(false);
                majFleche();
            }
        });

        for(int i =0; i < 5; i++){
            JButton b = affichage[i];
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.changePreview(b.getText());
                    resetBorder();
                    current = b.getText();
                    b.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                    b.setBorderPainted(!b.isBorderPainted());
                }
            });
        }
        affichage[0].setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        current = affichage[0].getText();
    }
    // up est vrai sur la fleche du bas, faux sinon
    public void majListe(boolean up){
        if(up){
            sommet++;
            if(sommet >= listeFichier.length){
                sommet = listeFichier.length-1;
            }
        }else{
            sommet--;
            if(sommet < 0){
                sommet = 0;
            }
        }

        int j = 0;
        int i = sommet;

        while(i < listeFichier.length &&  j < 5){
            affichage[j].setText(listeFichier[i]);
            i++;
            j++;
        }
    }

    public void majFleche(){
        if(!isTop()){
            flecheBas.setIcon(new ImageIcon(down));
        }else{
            flecheBas.setIcon(new ImageIcon(downVide));
        }
        if(!isBottom()){
            flecheHaut.setIcon(new ImageIcon(up));
        }else{
            flecheHaut.setIcon(new ImageIcon(upVide));
        }

    }


    public String[] getAllSaves(){
        File folder = new File(GameConstants.DOSSIER_SAVE);
        File[] listOfFiles = folder.listFiles();
        String s[] = new String[listOfFiles.length];
        int i = 0;
        for (File file : listOfFiles) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (file.isFile() && fileName.substring(index).equals(".txt")) {
                System.out.println(fileName);
                s[i] = fileName.substring(0, index);
                i++;
            }
        }
        return s;
    }

    public boolean isTop(){

        return sommet == 0;
    }

    public boolean isBottom(){
        return (sommet+5) >= listeFichier.length;
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i =0; i < 5; i++){
            //affichage[i].setMinimumSize(new Dimension((int)(getWidth()*0.8), (int)(getHeight()*0.15)));
        }
    }

    public void resetBorder(){
        for(int i =0; i < 5; i++){
            affichage[i].setBorderPainted(false);
        }
    }

    public String getCurrent(){
        return current;
    }


}
