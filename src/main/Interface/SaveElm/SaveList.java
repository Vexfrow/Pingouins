package Interface.SaveElm;

import Interface.GameConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveList extends JPanel {
    private JTextField listeSave;
    private JLabel echec;
    private JButton develop;
    private JButton clear;
    private JList<String> box;
    private JScrollPane listScroller;
    private boolean open;
    private String[] problem = {"\\", "/", "<", ">", ":", "\"", "|", "*", "?"};
    public SaveList(){
        setLayout(new GridBagLayout());
        echec =new JLabel("Les caractères: \\, /, <, >, :, \", |, *, ? sont interdits");
        echec.setForeground(Color.RED);
        listeSave = new JTextField("");
        develop = new JButton(">");
        clear = new JButton("X");
        box = new JList<String>();
        open = false;


        String s[] = getAllSaves();
        if(s.length == 0){
            s = new String[1];
            s[0] = "Aucun fichier";
            box.setListData(s);
            box.setEnabled(false);
            box.setVisibleRowCount(1);

        }else{
            box.setListData(s);
            box.setEnabled(true);
            if((s.length/2) < 5) {
                box.setVisibleRowCount(s.length/2);
            }
            else{
                box.setVisibleRowCount(5);
            }
        }
        box.setVisibleRowCount(5);



        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight =2;
        gbc.weighty =2;
        gbc.fill = GridBagConstraints.BOTH;
        box.setBorder(new EmptyBorder(0, 10, 0, 0));
        JScrollPane jp = new JScrollPane(box);

        jp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(jp, gbc);

        gbc.gridheight =1;
        gbc.weightx = 0;
        gbc.weighty =0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(clear, gbc);

        gbc.gridx = 0;

        gbc.fill = GridBagConstraints.BOTH;
        add(listeSave, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(echec, gbc);

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeSave.setText("");
            }
        });


        develop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(open){

                    box.setVisibleRowCount(0);
                    revalidate();
                    repaint();
                    open = false;

                }else{
                    box.setVisibleRowCount(5);
                    revalidate();
                    repaint();
                    open = true;
                }

            }
        });


        box.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setSaveText();
            }
        });
    }

    public boolean isValideName(String s){
        int i =0;
        while( i < problem.length && s.contains(problem[i])){
            i++;
        }
        return (s.length() == 0 && i >= problem.length);
    }

    public String removeSpace(String s){
        int i =0;
        String res = null;

        while(i < s.length() && s.charAt(i)==' '){
            i++;
        }
        res = s.substring(i);
        int j = res.length()-1;
        while( j>=0 && res.charAt(j)==' '){
            j--;
        }
        res = res.substring(i, j);

        return res;
    }

    public String getText(){
        String res = removeSpace(listeSave.getText());
        if(!isValideName(res)){
            System.err.println("Le nom du fichier est impossible");
            return "";
        }
        return res;
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
                s[i] = fileName.substring(0, index);
                i++;
            }
        }
        return s;
    }

    public void setSaveText(){
        listeSave.setText(box.getSelectedValue());
    }

}
