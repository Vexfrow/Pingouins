package Interface.ChargeElm;

import Interface.GameConstants;
import Interface.Panes.Chargement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;

public class ListeFile extends JPanel {

    Chargement panel;
    String[] listeFichier;
    JButton[] affichage;
    JLabel[] indices;
    private int[] positions;
    JButton[] elimine;
    private String current;

    private JButton flecheHaut;
    private JButton flecheBas;

    private int sommet;
    private int indice;

    public ListeFile(Chargement c){
        setLayout(new GridBagLayout());
        panel = c;

        initFile();
        setPosition();
        majFleche();
        setListeFichier();

        flecheHaut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBorder();
                majListe(false);
                majFleche();
                scaleAll();
            }
        });

        flecheBas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBorder();
                majListe(true);
                majFleche();
                scaleAll();
            }
        });


    }



    public void initFile(){
        sommet = 0;
        listeFichier = getAllSaves();
        affichage = new JButton[5];
        indices = new JLabel[5];
        elimine = new JButton[5];
        positions = new int[5];
        flecheHaut = new JButton();
        flecheBas = new JButton();
        current = "";
        for(int i = 0; i < 5; i++){
            if(i < listeFichier.length){
                indices[i] = new JLabel((i+1) + "/" + listeFichier.length);
                affichage[i] = new JButton(listeFichier[i]);
                affichage[i].setBackground(GameConstants.SELECTION);
                affichage[i].setBorderPainted(false);
            }else{
                indices[i] = new JLabel(" ");
                affichage[i] = new JButton("");
                affichage[i].setBackground(GameConstants.SELECTION);
                affichage[i].setBorderPainted(false);
            }
        }

    }

    public void setPosition(){


        flecheHaut.setIcon(new ImageIcon(GameConstants.flecheChargeHaut));
        flecheHaut.setBorderPainted(false);
        flecheHaut.setContentAreaFilled(false);
        flecheHaut.setDisabledIcon(new ImageIcon(GameConstants.flecheChargeHautTransparente));

        flecheBas.setIcon(new ImageIcon(GameConstants.flecheChargeBas));
        flecheBas.setBorderPainted(false);
        flecheBas.setContentAreaFilled(false);
        flecheBas.setDisabledIcon(new ImageIcon(GameConstants.flecheChargeBasTransparente));

        setBackground(GameConstants.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(flecheHaut, gbc);
        gbc.gridx = 3;
        gbc.gridy = 6;
        add(flecheBas, gbc);



        gbc.gridy = 1;

        for(int i = 0; i < 5; i++){
            gbc.insets = new Insets(10, 20, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 2;
            gbc.weightx = 1;
            gbc.gridx = 1;
            add(affichage[i], gbc);

            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.weighty = 0;
            gbc.weightx = 0;
            gbc.gridx = 0;
            add(indices[i], gbc);
            gbc.gridx = 2;
            elimine[i] = new JButton();
            elimine[i].setContentAreaFilled(false);
            elimine[i].setBorderPainted(false);
            add(elimine[i], gbc);
            if(i >= listeFichier.length){
                elimine[i].setEnabled(false);
            }
            gbc.gridy++;

        }



    }

    public void setListeFichier(){
        for(int i =0; i < affichage.length; i++){
            positions[i] = i;
            JButton b = affichage[i];
            if(i < listeFichier.length){
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.changePreview(b.getText());
                        resetBorder();
                        current = b.getText();
                        b.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                        b.setBorderPainted(!b.isBorderPainted());
                        panel.repaint();
                    }
                });
            }
            JButton j = elimine[i];
            j.addActionListener(new Cross(i, this));
        }
    }



    public void scaleAll(){
        for(int i = 0; i < 5; i++){
            elimine[i].setPreferredSize(new Dimension((int)(getWidth()*0.07) , (int)(getHeight()*0.07)));
            elimine[i].setIcon(new ImageIcon(reScale(GameConstants.croix, 0.07, 0.07)));
        }
        flecheHaut.setIcon(new ImageIcon(reScale(GameConstants.flecheChargeHaut, 0.08, 0.1)));
        flecheHaut.setDisabledIcon(new ImageIcon(reScale(GameConstants.flecheChargeHautTransparente, 0.08, 0.1)));
        flecheBas.setIcon(new ImageIcon(reScale(GameConstants.flecheChargeBas, 0.08, 0.1)));
        flecheBas.setDisabledIcon(new ImageIcon(reScale(GameConstants.flecheChargeBasTransparente, 0.08, 0.1)));
    }

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
            indices[j].setText((i+1) + "/" + listeFichier.length);
            affichage[j].setText(listeFichier[i]);
            i++;
            j++;
        }
    }

    public void majFleche(){
        if(isBottom()){
            flecheBas.setEnabled(false);
        }else{
            flecheBas.setIcon(new ImageIcon(GameConstants.flecheChargeHaut));
            flecheBas.setEnabled(true);
        }
        if(isTop()){
            flecheHaut.setEnabled(false);
        }else{
            flecheHaut.setIcon(new ImageIcon(GameConstants.flecheChargeBas));
            flecheHaut.setEnabled(true);
        }

    }

    public String[] getAllSaves(){
        File folder = new File(GameConstants.DOSSIER_SAVE);
        folder.mkdir();

        System.out.println(folder);

        File[] listOfFiles = folder.listFiles();
        String s[] = new String[listOfFiles.length];
        int i = 0;
        for (File file : listOfFiles) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (file.isFile() && fileName.substring(index).equals(".txt")) {
                s[i] = fileName.substring(0, index);
                i++;
            }
        }
        String res [] = new String[i];
        for(int j = 0; j < i; j++){
            res[j] = s[j];
        }
        return res;
    }

    public boolean isTop(){
        return sommet == 0;
    }

    public boolean isBottom(){
        return (sommet+5) >= listeFichier.length;
    }

    public Image reScale(Image img, double coefX, double coefY){
        return img.getScaledInstance((int)(getWidth()*coefX), (int)(getHeight()*coefY), Image.SCALE_REPLICATE);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        scaleAll();

    }

    public void resetBorder(){
        for(int i =0; i < 5; i++){
            affichage[i].setBorder(new EmptyBorder(1,1,1,1));
            affichage[i].setBorderPainted(false);

        }
    }

    public void supprime(int position){
        if(listeFichier[position].equals(current)){
            current = "";
        }

        File f = new File(GameConstants.DOSSIER_SAVE + listeFichier[position]+ ".txt");
        File iF = new File(GameConstants.DOSSIER_SAVE +  listeFichier[position]+ ".png");
        if(f.delete()){
            System.out.println("Fichier "+ f.getName()+ " supprimé");
        }
        if(iF.delete()){
            System.out.println("Fichier "+ iF.getName()+ " supprimé");
        }

    }

    public boolean choose(){
        return current != "";
    }


}
