package Interface;

import Model.Jeu;

import javax.swing.*;
import java.util.ArrayList;

public class ScorePanel extends JPanel {

    public JPanel panelScore;

    public JLabel scorePoisson;
    public JLabel scoreHexagone;

    public JPanel panelScorePoisson;
    public JPanel panelScoreHexagone;

    private Jeu jeu;

    public ScorePanel(Jeu j) {
        scorePoisson = new JLabel();
        scoreHexagone = new JLabel();
        panelScorePoisson = new JPanel();
        panelScoreHexagone = new JPanel();
        jeu = j;
    }






}
