package View;

import Model.FileModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by xpathz on 2017. 5. 28..
 */
public class LocationPane extends JComponent {
    private int leftLineSize, rightLineSize; // 필요 있을지는 의문임
    // 의도하고 있는건 비교할때마다 setState해둔거 이용할거임!

    public LocationPane() {
        this.setPreferredSize(new Dimension(56, getHeight()));
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(2,2,getWidth()-4, getHeight()-4);

        g.setColor(Color.BLACK);
        g.fillRect(27, 2, 2, getHeight());
    }
}
