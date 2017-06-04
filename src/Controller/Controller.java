package Controller;

import View.View;
import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private boolean isCompared;
    private Model model;
    private View view;

    // 디폴트 컨스트럭터
    public Controller() {}

    // 컨스트럭터
    // 모델과 뷰를 설정한다.
    // 모델 설정 이유 = 모델을 조작
    // 뷰 설정 이유 = 버튼 이벤트 설정을 이 쪽으로 가져오기 위함
    public Controller(Model m, View v) {
        this.isCompared = false;
        this.model = m;
        this.view = v;
        // 로케이션 판에 모델을 물려줌.
        this.view.setLocationPaneModels(m.leftFileModel, m.rightFileModel);
        this.view.setActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        String actionName = ((JButton)e.getSource()).getName();

        if (actionName.equals(Controller.BTN_LEFT_LOAD)) {
            // 왼쪽 패널 로드 버튼 클릭 시
            int returnVal = model.setFileContent(view, View.TARGET_LEFT);
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                isCompared = false;
                view.getFileTextPane(View.TARGET_LEFT).loadFile(model.getFileModel(Model.LEFT));
                view.clearColor();
                view.getFileTextPane(View.TARGET_LEFT).setStatusText("불러오기 완료 / 읽기 상태");
            }
        }
        else if (actionName.equals(Controller.BTN_RIGHT_LOAD)) {
            // 오른쪽 패널 로드 버튼 클릭 시
            int returnVal = model.setFileContent(view, View.TARGET_RIGHT);
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                isCompared = false;
                view.getFileTextPane(View.TARGET_RIGHT).loadFile(model.getFileModel(Model.RIGHT));
                view.clearColor();
                view.getFileTextPane(View.TARGET_RIGHT).setStatusText("불러오기 완료 / 읽기 상태");
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
                    isCompared = false;
                    view.getFileTextPane(View.TARGET_LEFT).setEditable(false);
                    model.leftFileModel.setLines(view.getFileTextPane(View.TARGET_LEFT).getScrollTextPane().getJTextPane());
                    view.getFileTextPane(View.TARGET_LEFT).getScrollTextPane().clearColor(); //TODO: 편집 시 색칠 초기화!! (TODO : 추가되었다는 의미)
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
                    isCompared = false;
                    view.getFileTextPane(View.TARGET_RIGHT).setEditable(false);
                    model.rightFileModel.setLines(view.getFileTextPane(View.TARGET_RIGHT).getScrollTextPane().getJTextPane());
                    view.getFileTextPane(View.TARGET_RIGHT).getScrollTextPane().clearColor(); //TODO: 편집 시 색칠 초기화!! (TODO : 추가되었다는 의미)
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
            if ( !model.leftFileModel.isFileLoaded() || !model.rightFileModel.isFileLoaded() ) { // 아님 파일이 아직 안불러온 상태?
                view.showMessage("비교할 파일을 모두 불러오지 않았습니다. \n파일을 모두 불러오십시오.", "WARNING", JOptionPane.WARNING_MESSAGE);
            } else {
                if (!view.getFileTextPane(View.TARGET_LEFT).getEditable() && !view.getFileTextPane(View.TARGET_RIGHT).getEditable()) {
                    isCompared = true;
                    model.compareLines();
                    view.printCompare(model.getFileModel(Model.LEFT).getLines(), model.getFileModel(Model.RIGHT).getLines());
                    view.setLines(Model.LEFT, model.getFileModel(Model.LEFT).getLines());
                    view.setLines(Model.RIGHT, model.getFileModel(Model.RIGHT).getLines());
                    view.getLocPane().repaint(); // DO NOT REMOVE!! 컴포넌트 다시 그려줄 것을 호출
                } else { // 아니면 메세지를 띄어줌.
                    view.showMessage("편집 상태인 문서가 있습니다. \n편집 아이콘을 한번 더 눌러 편집을 완료하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
            //TODO: 왼쪽 -> 오른쪽 Merge. 단, CaretListener로 설정된 좌표를 기준으로! (TODO : 추가되었다는 의미)
        } else if (actionName.equals(Controller.BTN_MERGE_LEFT_TO_RIGHT)) {
            if ( isCompared ) {
                int index = model.checkMergable(Model.LEFT, view.getScrollTextPane(Model.LEFT).getDotPosition());
                if (index != -1) {
                    model.mergeResult(Model.RIGHT, index, view.getScrollTextPane(Model.LEFT).getJTextPane());

                    //TODO: 머지했으면 다시 비교해야합니다. (TODO : 추가되었다는 의미)
                    CalcDiff.compareLines(model.leftFileModel.getLines(), model.rightFileModel.getLines(), model.getDiffBlocks(Model.LEFT), model.getDiffBlocks(Model.RIGHT));
                    view.printCompare(model.getFileModel(Model.LEFT).getLines(), model.getFileModel(Model.RIGHT).getLines());
                    view.setLines(Model.LEFT, model.getFileModel(Model.LEFT).getLines());
                    view.setLines(Model.RIGHT, model.getFileModel(Model.RIGHT).getLines());
                    view.getLocPane().repaint(); // DO NOT REMOVE!! 컴포넌트 다시 그려줄 것을 호출
                }
            } else {
                view.showMessage("Merge 기능을 위해서 \n이전에 Compare을 수행하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            //TODO: 오른쪽 -> 왼쪽 Merge. 단, CaretListener로 설정된 좌표를 기준으로! (TODO : 추가되었다는 의미)
        } else if (actionName.equals(Controller.BTN_MERGE_RIGHT_TO_LEFT)) {
            if ( isCompared ) {
                int index = model.checkMergable(Model.RIGHT, view.getScrollTextPane(Model.RIGHT).getDotPosition());
                if ( index != -1 ) {
                    model.mergeResult(Model.LEFT, index, view.getScrollTextPane(Model.RIGHT).getJTextPane());
                    //TODO: 머지했으면 다시 비교해야합니다. (TODO : 추가되었다는 의미)
                    CalcDiff.compareLines(model.leftFileModel.getLines(), model.rightFileModel.getLines(), model.getDiffBlocks(Model.LEFT), model.getDiffBlocks(Model.RIGHT));
                    view.printCompare(model.getFileModel(Model.LEFT).getLines(), model.getFileModel(Model.RIGHT).getLines());
                    view.setLines(Model.LEFT, model.getFileModel(Model.LEFT).getLines());
                    view.setLines(Model.RIGHT, model.getFileModel(Model.RIGHT).getLines());
                    view.getLocPane().repaint(); // DO NOT REMOVE!! 컴포넌트 다시 그려줄 것을 호출
                }
            } else {
                view.showMessage("Merge 기능을 위해서 \n이전에 Compare을 수행하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionName.equals(Controller.BTN_MERGE_LEFT_TO_RIGHT_ALL)) {
            if ( isCompared ) {
                model.mergeAll(Model.LEFT, view.getScrollTextPane(Model.LEFT).getJTextPane(), view.getScrollTextPane(Model.RIGHT).getJTextPane());
                CalcDiff.compareLines(model.leftFileModel.getLines(), model.rightFileModel.getLines(), model.getDiffBlocks(Model.LEFT), model.getDiffBlocks(Model.RIGHT));
                view.printCompare(model.getFileModel(Model.LEFT).getLines(), model.getFileModel(Model.RIGHT).getLines());
                view.setLines(Model.LEFT, model.getFileModel(Model.LEFT).getLines());
                view.setLines(Model.RIGHT, model.getFileModel(Model.RIGHT).getLines());
                view.getLocPane().repaint(); // DO NOT REMOVE!! 컴포넌트 다시 그려줄 것을 호출
            } else {
                view.showMessage("Merge 기능을 위해서 \n이전에 Compare을 수행하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            //TODO: 오른쪽 -> 왼쪽 Merge. 단, CaretListener로 설정된 좌표를 기준으로! (TODO : 추가되었다는 의미)
        } else if (actionName.equals(Controller.BTN_MERGE_RIGHT_TO_LEFT_ALL)) {
            if ( isCompared ) {
                model.mergeAll(Model.RIGHT, view.getScrollTextPane(Model.LEFT).getJTextPane(), view.getScrollTextPane(Model.RIGHT).getJTextPane());
                CalcDiff.compareLines(model.leftFileModel.getLines(), model.rightFileModel.getLines(), model.getDiffBlocks(Model.LEFT), model.getDiffBlocks(Model.RIGHT));
                view.printCompare(model.getFileModel(Model.LEFT).getLines(), model.getFileModel(Model.RIGHT).getLines());
                view.setLines(Model.LEFT, model.getFileModel(Model.LEFT).getLines());
                view.setLines(Model.RIGHT, model.getFileModel(Model.RIGHT).getLines());
                view.getLocPane().repaint(); // DO NOT REMOVE!! 컴포넌트 다시 그려줄 것을 호출
            } else {
                view.showMessage("Merge 기능을 위해서 \n이전에 Compare을 수행하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            //TODO: 오른쪽 -> 왼쪽 Merge. 단, CaretListener로 설정된 좌표를 기준으로! (TODO : 추가되었다는 의미)
        }

        //TODO: 버튼 이벤트 후에 리페인트 요청. (TODO : 추가되었다는 의미)
        view.getScrollTextPane(Model.LEFT).repaint();
        view.getScrollTextPane(Model.RIGHT).repaint();
    }
}