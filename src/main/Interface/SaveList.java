package Interface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveList extends JPanel {
    private JTextField listeSave;
    private JButton develop;
    private JButton clear;
    private JList<String> box;
    private JScrollPane listScroller;
    private boolean open;
    public SaveList(){
        setLayout(new GridBagLayout());
        listeSave = new JTextField("");
        develop = new JButton(">");
        clear = new JButton("X");
        box = new JList();
        open = false;


        String s[] = getAllSaves();
        if(s.length == 0){
            s = new String[1];
            s[0] = "Aucun fichier";
            box.setListData(s);

        }else{
            box.setListData(s);
        }

        box.setVisibleRowCount(1);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(box, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 0;
        gbc.gridx = 0;


        gbc.gridx = 2;
        add(clear, gbc);

        gbc.gridx = 1;
        gbc.weightx = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(listeSave, gbc);

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeSave.setText("");
            }
        });


        develop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(open);
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

    public String getText(){
        return listeSave.getText();
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

    public void setSaveText(){
        listeSave.setText(box.getSelectedValue());
    }

}
