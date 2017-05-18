package Controller;

import View.View;
import Model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener
{
    private JFileChooser fileChooser = new JFileChooser();
    private View view;
    private Model model;

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

        int returnVal;
        if (actionName.equals("btnLeftLoad")) {
            // 왼쪽 패널 로드 버튼일 시
            model.setLeftFileContent();
        }
        else if (actionName.equals("btnRightLoad")) {
            // 오른쪽 패널 로드 버튼일 시
            model.setRightFileContent();
        } else if (actionName.equals("btnCompare")) {
            // 비교 버튼 시
            model.textCompare();
        }
    }
}