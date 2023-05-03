package Interface;

import javax.swing.*;
import java.awt.*;

public class Aide extends JPanel{

    public Aide(JFrame jf){
        setLayout(new BorderLayout());
        JLabel b = new JLabel("Oui");
        this.add(b, BorderLayout.SOUTH);

    }


}
