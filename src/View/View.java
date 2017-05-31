/**
 * Created by iskyc on 2017-05-13.
 */

package View;

import Controller.Controller;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {
    public static String TARGET_LEFT = "Left";
    public static String TARGET_RIGHT = "Right";

    private JFrame frmMain;
    private LocationPane locPane;
    private JPanel frmBodyPane, westPane, eastPane;
    private FileTextPane leftFilePane, rightFilePane;
    private ControlPane ctlPane;

/** TODO Implementation :  LocationPane */
//  LocationPane

    public View() {
        initBodyPane();
        initWestPane();
        initEastPane();
        initFrmMain();
    }

    private void initFrmMain() {
        frmMain.setTitle("SimpleMerge - Team 1"); //set the title of JFrame
        frmMain.setSize(600, 500); //set the size of JFrame
        frmMain.setLocationRelativeTo(null); //set the location of JFrame
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set the close option of JFrame
        frmMain.setVisible(true); //set visible for this window
    }

    private void initBodyPane() {
        frmMain = new JFrame();

        frmBodyPane = new JPanel();

        frmBodyPane.setLayout(new GridLayout(1, 2));

        frmMain.add(frmBodyPane);
    }

    private void initWestPane() {
        westPane = new JPanel();
        leftFilePane = new FileTextPane();
/** TODO Implementation :  LocationPane */
//
        // 로케이션 판 추가됨! 단, 현재는 임시로 텍스트에어리어들 둔 것임!!!!!
        locPane = new LocationPane();

        westPane.setLayout(new BorderLayout());

        frmBodyPane.add(westPane, 0);
/** TODO Implementation :  LocationPane */
        westPane.add(locPane, BorderLayout.WEST);
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

    public LocationPane getLocPane() { return locPane; }
    public void setLocationPaneModels(FileModel left, FileModel right) {
        locPane.setModel(left, right);
    }

    // 현재 J프레임을 리턴해주는 메소드.
    // 주로 컨트롤러에서 창을 띄울 때 사용함.
    public JFrame getFrame() {
        return this.frmMain;
    }

    public ScrollTextPane getScrollTextPane(String name) {
        if ( name.equals(View.TARGET_LEFT) ) return this.leftFilePane.getScrollTextPane();
        else if ( name.equals(View.TARGET_RIGHT) ) return this.rightFilePane.getScrollTextPane();
        return null;
    }

    public FileTextPane getFileTextPane(String name) {
        if ( name.equals(View.TARGET_LEFT) ) return this.leftFilePane;
        else if ( name.equals(View.TARGET_RIGHT) ) return this.rightFilePane;
        return null;
    }

    // JFrame에 메시지 다이어로그를 띄움
    // 인자는 이름, 내용, 메시지 타입으로 받는다.
    public void showMessage(String text, String name, int type) {
        JOptionPane.showMessageDialog(this.frmMain, text, name, type);
    }

    // 버튼들에 대한 액션리스너를 컨트롤러에 추가시킨다.
    // 그리고, 그 버튼들에 대해 이름을 설정해준다.
    // 나중에 발생하는 이벤트들을 구별하기 위함임.
    public void setActionListener(ActionListener al) {
        try {
            leftFilePane.getButton(FileTextPane.BTN_LOAD).addActionListener(al);
            leftFilePane.getButton(FileTextPane.BTN_LOAD).setName(Controller.BTN_LEFT_LOAD);
            leftFilePane.getButton(FileTextPane.BTN_SAVE).addActionListener(al);
            leftFilePane.getButton(FileTextPane.BTN_SAVE).setName(Controller.BTN_LEFT_SAVE);
            leftFilePane.getButton(FileTextPane.BTN_EDIT).addActionListener(al);
            leftFilePane.getButton(FileTextPane.BTN_EDIT).setName(Controller.BTN_LEFT_EDIT);

            rightFilePane.getButton(FileTextPane.BTN_LOAD).addActionListener(al);
            rightFilePane.getButton(FileTextPane.BTN_LOAD).setName(Controller.BTN_RIGHT_LOAD);
            rightFilePane.getButton(FileTextPane.BTN_SAVE).addActionListener(al);
            rightFilePane.getButton(FileTextPane.BTN_SAVE).setName(Controller.BTN_RIGHT_SAVE);
            rightFilePane.getButton(FileTextPane.BTN_EDIT).addActionListener(al);
            rightFilePane.getButton(FileTextPane.BTN_EDIT).setName(Controller.BTN_RIGHT_EDIT);

            ctlPane.getButton(ControlPane.BTN_COMPARE).addActionListener(al);
            ctlPane.getButton(ControlPane.BTN_COMPARE).setName(Controller.BTN_COMPARE);
            ctlPane.getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT).addActionListener(al);
            ctlPane.getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT).setName(Controller.BTN_MERGE_LEFT_TO_RIGHT);
            ctlPane.getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT_ALL).addActionListener(al);
            ctlPane.getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT_ALL).setName(Controller.BTN_MERGE_LEFT_TO_RIGHT_ALL);
            ctlPane.getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT).addActionListener(al);
            ctlPane.getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT).setName(Controller.BTN_MERGE_RIGHT_TO_LEFT);
            ctlPane.getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT_ALL).addActionListener(al);
            ctlPane.getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT_ALL).setName(Controller.BTN_MERGE_RIGHT_TO_LEFT_ALL);
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
    }

    public void clearColor() {
        leftFilePane.getScrollTextPane().clearColor();
        rightFilePane.getScrollTextPane().clearColor();
    }

    // TODO Modify : diffCharSet으로
    // 비교한 결과에 대한 글자 배경 색칠.
    public void printCompare(ArrayList<Line> leftLines, ArrayList<Line> rightLines) {
        clearColor();
        leftFilePane.getScrollTextPane().printCompare(leftLines);
        rightFilePane.getScrollTextPane().printCompare(rightLines);
    }
}
