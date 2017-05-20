/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import javax.swing.*;
import java.awt.*;

public class ControlPane extends JPanel {
    private JButton btnCompare, btnMergeLeftToRightAll, btnMergeLeftToRight, btnMergeRightToLeft, btnMergeRightToLeftAll;

    ControlPane() {
        initialize();
    }

    private void initialize() {
        /** 버튼 객체 생성 */
        btnCompare = new JButton(new ImageIcon("./img/compare.png"));
        btnMergeLeftToRightAll = new JButton(new ImageIcon("./img/all_ltor.png"));
        btnMergeLeftToRight = new JButton(new ImageIcon("./img/ltor.png"));
        btnMergeRightToLeft = new JButton(new ImageIcon("./img/rtol.png"));
        btnMergeRightToLeftAll = new JButton(new ImageIcon("./img/all_rtol.png"));

        /** 패널 레이아웃 설정 */
        super.setLayout(new GridLayout(5, 1));

        /** 각 패널 레이아웃 설정 */
        super.add(btnCompare, 0);
        super.add(btnMergeLeftToRightAll, 1);
        super.add(btnMergeLeftToRight, 2);
        super.add(btnMergeRightToLeft, 3);
        super.add(btnMergeRightToLeftAll, 4);
    }

    public JButton getButton(String name) {
        if ( name.equals("Compare") ) return this.btnCompare;
        else if ( name.equals("bMLTR") ) return this.btnMergeLeftToRight;
        else if ( name.equals("bMLTRA") ) return this.btnMergeLeftToRightAll;
        else if ( name.equals("bMRTLA") ) return this.btnMergeRightToLeftAll;
        else if ( name.equals("bMRTL") ) return this.btnMergeRightToLeft;
        return null;
    }
}
