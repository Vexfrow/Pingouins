package Interface.ChargeElm;

import Interface.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class ListeFile extends JPanel {

    String[] listeFichier;
    JLabel[] affichage;

    private Image up;
    private Image down;
    private Image upVide;
    private Image downVide;

    private JButton flecheHaut;
    private JButton flecheBas;

    private int sommet;

    public ListeFile(){
        sommet = 0;
        listeFichier = getAllSaves();
        affichage = new JLabel[5];
        flecheHaut = new JButton();
        flecheBas = new JButton();
        for(int i = 0; i < 5; i++){
            if(i < listeFichier.length){
                affichage[i] = new JLabel(listeFichier[i]);
            }else{
                affichage[i] = new JLabel("");
            }
        }

        try{
            up = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeBas.png"));
            down = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeHaut.png"));
            upVide = (Image) ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeBasTransparente.png"));
            downVide = (Image)ImageIO.read(new FileInputStream("resources/assets/menu/flecheChargeHautTransparente.png"));
        }catch(Exception e){
            System.out.println("une erreur " + e);
        }

        setLayout(new GridBagLayout());
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
        for(int i = 0; i < 5; i++){
            add(affichage[i], gbc);
            gbc.gridy++;
        }

    }
    // up est vrai sur la fleche du bas, faux sinon
    public void majListe(boolean up){
        if(up){
            sommet++;
        }else{
            sommet--;
        }

        int j = 0;
        int i = sommet;

        while(i < listeFichier.length &&  j < 5){
            affichage[j].setText(listeFichier[i]);
            i++;
            j++;
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
        return true;
    }

    public boolean isBottom(){
        return true;
    }
}
