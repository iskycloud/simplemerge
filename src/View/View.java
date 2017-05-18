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

    // 원하는 텍스트필드를 리턴해주는 메소드.
    // 주로 모델에서 이 것을 사용할 것이다.
    public JTextField getJTextField(String name) {
        if (name.equals("txtLeftPath")) {
            return this.txtLeftPath;
        }
        else if (name.equals("txtRightPath")) {
            return this.txtRightPath;
        }

        return null;
    }

    // ---------------------------- 수정점!!
    // 원하는 텍스트판를 리턴해주는 메소드.
    // 주로 모델에서 이 것을 사용할 것이다.
    public JTextPane getJTextPane(String name) {
        if (name.equals("txtLeftTextPane")) {
            return this.txtLeftTextPane;
        }
        else if (name.equals("txtRightTextPane")) {
            return this.txtRightTextPane;
        }

        return null;
    }

    // 버튼들에 대한 액션리스너를 컨트롤러에 추가시킨다.
    // 그리고, 그 버튼들에 대해 이름을 설정해준다.
    // 나중에 발생하는 이벤트들을 구별하기 위함임.
    public void setActionListener(ActionListener al) {
        btnLeftLoad.addActionListener(al);
        btnLeftLoad.setName("btnLeftLoad");
        btnLeftSave.addActionListener(al);
        btnLeftSave.setName("btnLeftSave");
        btnLeftEdit.addActionListener(al);
        btnLeftEdit.setName("btnLeftEdit");

        btnRightLoad.addActionListener(al);
        btnRightLoad.setName("btnRightLoad");
        btnRightSave.addActionListener(al);
        btnRightSave.setName("btnRightSave");
        btnRightEdit.addActionListener(al);
        btnRightEdit.setName("btnRightEdit");

        btnCompare.addActionListener(al);
        btnCompare.setName("btnCompare");
        btnMergeLeftToRight.addActionListener(al);
        btnMergeLeftToRight.setName("btnMergeLeftToRight");
        btnMergeLeftToRightAll.addActionListener(al);
        btnMergeLeftToRightAll.setName("btnMergeLeftToRightAll");
        btnMergeRightToLeft.addActionListener(al);
        btnMergeRightToLeft.setName("btnMergeRightToLeft");
        btnMergeRightToLeftAll.addActionListener(al);
        btnMergeRightToLeftAll.setName("btnMergeRightToLeftAll");
    }
}
