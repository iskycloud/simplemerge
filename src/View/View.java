/**
 * Created by iskyc on 2017-05-13.
 */

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frmMain;
    private JPanel frmBodyPane, westPane, eastPane;
    private FileTextPane leftFilePane, rightFilePane;
    private ControlPane ctlPane;

/** Modification Required : LocationPane */
//  LocationPane

    public View() {
        initFrmMain();
        initWestPane();
        initEastPane();
    }

    private void initFrmMain() {
        frmMain = new JFrame();

        frmBodyPane = new JPanel();

        frmMain.setTitle("SimpleMerge - Team 1"); //set the title of JFrame
        frmMain.setSize(600, 400); //set the size of JFrame
        frmMain.setLocationRelativeTo(null); //set the location of JFrame
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set the close option of JFrame
        frmMain.setVisible(true); //set visible for this window
        
        frmBodyPane.setLayout(new GridLayout(1, 2));

        frmMain.add(frmBodyPane);
    }

    private void initWestPane() {
        westPane = new JPanel();
        leftFilePane = new FileTextPane();
/** Modification Required : LocationPane */
//        LocationPane locPane = new JPanel();

        westPane.setLayout(new BorderLayout());

        frmBodyPane.add(westPane, 0);
/** Modification Required : LocationPane */
//        westPane.add(locPane, BorderLayout.WEST);
        westPane.add(leftFilePane, BorderLayout.CENTER);
    }

    private void initEastPane() {
        eastPane = new JPanel();
        rightFilePane = new FileTextPane();
        ctlPane = new ControlPane();

        eastPane.setLayout(new BorderLayout());

        frmBodyPane.add(eastPane, 1);
        eastPane.add(ctlPane, BorderLayout.WEST);
        eastPane.add(rightFilePane, BorderLayout.CENTER);
    }

    // 현재 J프레임을 리턴해주는 메소드.
    // 주로 컨트롤러에서 창을 띄울 때 사용함.
    public JFrame getFrame() {
        return this.frmMain;
    }


    public ScrollTextPane getScrollTextPane(String name) {
        if ( name.equals("Left") ) return this.leftFilePane.getScrollTextPane();
        else if ( name.equals("Right") ) return this.rightFilePane.getScrollTextPane();
        return null;
    }

    public FileTextPane getFileTextPane(String name) {
        if ( name.equals("Left") ) return this.leftFilePane;
        else if ( name.equals("Right") ) return this.rightFilePane;
        return null;
    }


    // 버튼들에 대한 액션리스너를 컨트롤러에 추가시킨다.
    // 그리고, 그 버튼들에 대해 이름을 설정해준다.
    // 나중에 발생하는 이벤트들을 구별하기 위함임.
    public void setActionListener(ActionListener al) {
        try {
            leftFilePane.getButton("Load").addActionListener(al);
            leftFilePane.getButton("Load").setName("btnLeftLoad");
            leftFilePane.getButton("Save").addActionListener(al);
            leftFilePane.getButton("Save").setName("btnLeftSave");
            leftFilePane.getButton("Edit").addActionListener(al);
            leftFilePane.getButton("Edit").setName("btnLeftEdit");

            rightFilePane.getButton("Load").addActionListener(al);
            rightFilePane.getButton("Load").setName("btnRightLoad");
            rightFilePane.getButton("Save").addActionListener(al);
            rightFilePane.getButton("Save").setName("btnRightSave");
            rightFilePane.getButton("Edit").addActionListener(al);
            rightFilePane.getButton("Edit").setName("btnRightEdit");

            ctlPane.getButton("Compare").addActionListener(al);
            ctlPane.getButton("Compare").setName("btnCompare");
            ctlPane.getButton("bMLTR").addActionListener(al);
            ctlPane.getButton("bMLTR").setName("btnMergeLeftToRight");
            ctlPane.getButton("bMLTRA").addActionListener(al);
            ctlPane.getButton("bMLTRA").setName("btnMergeLeftToRightAll");
            ctlPane.getButton("bMRTL").addActionListener(al);
            ctlPane.getButton("bMRTL").setName("btnMergeRightToLeft");
            ctlPane.getButton("bMRTLA").addActionListener(al);
            ctlPane.getButton("bMRTLA").setName("btnMergeRightToLeftAll");
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
    }
}
