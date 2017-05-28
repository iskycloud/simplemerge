package View;

import Model.FileModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by xpathz on 2017. 5. 28..
 */
public class LocationPane extends JPanel {

    // 그냥 임시로 둔 것임!!!
    // 아직 제이컴포넌트 안만듬!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public LocationPane() {
        JTextArea l = new JTextArea();
        JTextArea r = new JTextArea();

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        l.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        r.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        l.setColumns(2);
        r.setColumns(2);
        //l.setSize(new Dimension(3, 5));
        this.setLayout(new GridLayout(1,2));
        this.add(l);
        this.add(r);

        l.setEditable(false);
        r.setEditable(false);
        // do nothing.
    }

    public void refresh() {

    }
}
