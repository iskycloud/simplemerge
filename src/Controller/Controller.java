package Controller;

import View.View;
import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener
{
    public static String BTN_LEFT_LOAD = "btnLeftLoad";
    public static String BTN_LEFT_SAVE = "btnLeftSave";
    public static String BTN_LEFT_EDIT = "btnLeftEdit";
    public static String BTN_RIGHT_LOAD = "btnRightLoad";
    public static String BTN_RIGHT_SAVE = "btnRightSave";
    public static String BTN_RIGHT_EDIT = "btnRightEdit";
    public static String BTN_COMPARE = "btnCompare";
    public static String BTN_MERGE_LEFT_TO_RIGHT_ALL = "bMLTRA";
    public static String BTN_MERGE_LEFT_TO_RIGHT = "bMLTR";
    public static String BTN_MERGE_RIGHT_TO_LEFT_ALL = "bMRTLA";
    public static String BTN_MERGE_RIGHT_TO_LEFT = "bMRTL";

    private Model model;
    private View view;

    // 디폴트 컨스트럭터
    public Controller() {}

    // 컨스트럭터
    // 모델과 뷰를 설정한다.
    // 모델 설정 이유 = 모델을 조작
    // 뷰 설정 이유 = 버튼 이벤트 설정을 이 쪽으로 가져오기 위함
    public Controller(Model m, View v) {
        this.model = m;
        this.view = v;
        this.view.setActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        String actionName = ((JButton)e.getSource()).getName();

        if (actionName.equals(Controller.BTN_LEFT_LOAD)) {
            // 왼쪽 패널 로드 버튼 클릭 시
            int returnVal = model.setFileContent(view, View.TARGET_LEFT);
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                view.getFileTextPane(View.TARGET_LEFT).loadFile(model.getFileModel(Model.LEFT));
                view.getFileTextPane(View.TARGET_LEFT).setStatusText("불러오기 완료 / 읽기 전용");
            }
        }
        else if (actionName.equals(Controller.BTN_RIGHT_LOAD)) {
            // 오른쪽 패널 로드 버튼 클릭 시
            int returnVal = model.setFileContent(view, View.TARGET_RIGHT);
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                view.getFileTextPane(View.TARGET_RIGHT).loadFile(model.getFileModel(Model.RIGHT));
                view.getFileTextPane(View.TARGET_RIGHT).setStatusText("불러오기 완료 / 읽기 전용");
            }
        } else if (actionName.equals(Controller.BTN_LEFT_SAVE)) {
            // 왼쪽 패널 편집 버튼 클릭 시
            if ( model.leftFileModel.isFileLoaded() ) {
                if (view.getFileTextPane(View.TARGET_LEFT).getEditable()) {
                    view.showMessage("현재 문서는 편집 상태입니다. \n편집 아이콘을 한번 더 눌러 편집을 완료하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
                } else {
                    model.leftFileModel.saveLines();
                    view.getFileTextPane(View.TARGET_LEFT).setStatusText("저장 완료");
                }
            } else {
                view.showMessage("먼저 파일을 불러오십시오.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionName.equals(Controller.BTN_RIGHT_SAVE)) {
            // 왼쪽 패널 편집 버튼 클릭 시
            if ( model.rightFileModel.isFileLoaded() ) {
                if (view.getFileTextPane(View.TARGET_RIGHT).getEditable()) {
                    view.showMessage("현재 문서는 편집 상태입니다. \n편집 아이콘을 한번 더 눌러 편집을 완료하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
                } else {
                    model.rightFileModel.saveLines();
                    view.getFileTextPane(View.TARGET_RIGHT).setStatusText("저장 완료");
                }
            } else {
                view.showMessage("먼저 파일을 불러오십시오.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionName.equals(Controller.BTN_LEFT_EDIT)) {
            // 왼쪽 패널 편집 버튼 클릭 시
            if ( model.leftFileModel.isFileLoaded() ) {
                if (view.getFileTextPane(View.TARGET_LEFT).getEditable()) {
                    view.getFileTextPane(View.TARGET_LEFT).setEditable(false);
                    model.leftFileModel.setLines(view.getFileTextPane(View.TARGET_LEFT).getScrollTextPane().getJTextPane());
                    view.getFileTextPane(View.TARGET_LEFT).setStatusText("편집 완료");
                } else {
                    view.getFileTextPane(View.TARGET_LEFT).setEditable(true);
                    view.getFileTextPane(View.TARGET_LEFT).setStatusText("편집 가능");
                }
            } else {
                view.showMessage("먼저 파일을 불러오십시오.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionName.equals(Controller.BTN_RIGHT_EDIT)) {
            // 오른쪽 패널 편집 버튼 클릭 시
            if ( model.rightFileModel.isFileLoaded() ) {
                if (view.getFileTextPane(View.TARGET_RIGHT).getEditable()) {
                    view.getFileTextPane(View.TARGET_RIGHT).setEditable(false);
                    model.rightFileModel.setLines(view.getFileTextPane(View.TARGET_RIGHT).getScrollTextPane().getJTextPane());
                    view.getFileTextPane(View.TARGET_RIGHT).setStatusText("편집 완료");
                } else {
                    view.getFileTextPane(View.TARGET_RIGHT).setEditable(true);
                    view.getFileTextPane(View.TARGET_RIGHT).setStatusText("편집 가능");
                }
            } else {
                view.showMessage("먼저 파일을 불러오십시오.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionName.equals(Controller.BTN_COMPARE)) {
            // 비교 버튼 클릭 시
            // 만약 둘 다 편집 상태가 아닐 경우 비교 수행
            if ( !view.getFileTextPane(View.TARGET_LEFT).getEditable() && !view.getFileTextPane(View.TARGET_RIGHT).getEditable() ) {
                model.textCompare();
                view.printCompare(model.getDiffModel());
            } else { // 아니면 메세지를 띄어줌.
                view.showMessage("편집 상태인 문서가 있습니다. \n편집 아이콘을 한번 더 눌러 편집을 완료하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}