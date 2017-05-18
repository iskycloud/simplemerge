/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import javax.swing.*;
import java.awt.*;

class ControlPane extends JPanel {
    private JButton btnCompare, btnMergeLeftToRightAll, btnMergeLeftToRight, btnMergeRightToLeft, btnMergeRightToLeftAll;

    ControlPane() {
        initialize();
    }

    private void initialize() {
        btnCompare = new JButton(new ImageIcon("./img/compare.png"));
        btnMergeLeftToRightAll = new JButton(new ImageIcon("./img/all_ltor.png"));
        btnMergeLeftToRight = new JButton(new ImageIcon("./img/ltor.png"));
        btnMergeRightToLeft = new JButton(new ImageIcon("./img/rtol.png"));
        btnMergeRightToLeftAll = new JButton(new ImageIcon("./img/all_rtol.png"));

        super.setLayout(new GridLayout(5, 1));

        super.add(btnCompare, 0);
        super.add(btnMergeLeftToRightAll, 1);
        super.add(btnMergeLeftToRight, 2);
        super.add(btnMergeRightToLeft, 3);
        super.add(btnMergeRightToLeftAll, 4);
    }
    
}
