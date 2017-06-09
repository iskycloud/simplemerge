package View;

import javax.swing.*;
import java.awt.*;

public class ControlPane extends JPanel {
    public static String BTN_COMPARE = "Compare";
    public static String BTN_MERGE_LEFT_TO_RIGHT_ALL = "bMLTRA";
    public static String BTN_MERGE_LEFT_TO_RIGHT = "bMLTR";
    public static String BTN_MERGE_RIGHT_TO_LEFT_ALL = "bMRTLA";
    public static String BTN_MERGE_RIGHT_TO_LEFT = "bMRTL";

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
        if ( name.equals(ControlPane.BTN_COMPARE) ) return this.btnCompare;
        else if ( name.equals(ControlPane.BTN_MERGE_LEFT_TO_RIGHT) ) return this.btnMergeLeftToRight;
        else if ( name.equals(ControlPane.BTN_MERGE_LEFT_TO_RIGHT_ALL) ) return this.btnMergeLeftToRightAll;
        else if ( name.equals(ControlPane.BTN_MERGE_RIGHT_TO_LEFT_ALL) ) return this.btnMergeRightToLeftAll;
        else if ( name.equals(ControlPane.BTN_MERGE_RIGHT_TO_LEFT) ) return this.btnMergeRightToLeft;
        return null;
    }
}
