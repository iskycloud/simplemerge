/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import Model.FileModel;
import javax.swing.*;
import java.awt.*;

public class FileTextPane extends JPanel {
    /* 상수 :  아이콘 */
    private final static Icon ICON_LOAD = new ImageIcon("./img/load.png");
    private final static Icon ICON_SAVE = new ImageIcon("./img/save.png");
    private final static Icon ICON_EDIT = new ImageIcon("./img/edit.png");
    private final static Icon ICON_EDITING = new ImageIcon("./img/editing.png");

    /* 멤버 변수 */
    private JPanel fileNorthPane, fileMenuPane;
    private JButton btnLoad, btnSave, btnEdit;
    private JTextField txtPath, txtStatus;
    private ScrollTextPane scrTxtPane;

    /* 생성자 */
    FileTextPane() {
        initialize();
    }

    /* 초기화 메소드 */
    private void initialize() {
        /** 패널 객체 생성 */
        fileNorthPane = new JPanel();
        fileMenuPane = new JPanel();

        /** 버튼 객체 생성 */
        btnLoad = new JButton(ICON_LOAD);
        btnSave = new JButton(ICON_SAVE);
        btnEdit = new JButton(ICON_EDITING);

        /** 텍스트 상자 객체 생성 */
        txtPath = new JTextField();
        txtStatus = new JTextField();

        /** 파일 내용 텍스트상자 객체 생성 */
        scrTxtPane = new ScrollTextPane();

        /** 텍스트 상자 읽기 전용 설정*/
        txtPath.setEditable(false);
        txtPath.setBackground(Color.WHITE);
        txtPath.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        txtStatus.setEditable(false);
        txtStatus.setBackground(Color.WHITE);
        txtStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        /** 파일 내용 텍스트상자 읽기 전용 설정*/
        this.setEditable(false);

        /** 각 패널 레이아웃 설정 */
        super.setLayout(new BorderLayout());
        fileNorthPane.setLayout(new BorderLayout());
        fileMenuPane.setLayout(new GridLayout(1,4));

        /** 각 컴포넌트들 배치 */
        super.add(fileNorthPane, BorderLayout.NORTH);
        super.add(scrTxtPane, BorderLayout.CENTER);
        super.add(txtStatus, BorderLayout.SOUTH);

        /** 버튼 패널 및 경로 텍스트상자 배치 */
        fileNorthPane.add(fileMenuPane, BorderLayout.NORTH);
        fileNorthPane.add(txtPath, BorderLayout.SOUTH);

        /** 버튼 배치 */
        fileMenuPane.add(btnLoad, 0);
        fileMenuPane.add(btnSave, 1);
        fileMenuPane.add(btnEdit, 2);
    }

    public JTextField getJTextField(String name) {
        if ( name.equals("txtPath") ) return this.txtPath;
        else if ( name.equals("txtStatus") ) return this.txtStatus;
        return null;
    }

    public ScrollTextPane getScrollTextPane() {
        return this.scrTxtPane;
    }

    public void loadFile(FileModel fm) {
        //View에 구현
        txtPath.setText(fm.getFilePath());
        scrTxtPane.getJTextPane().setText("");
        scrTxtPane.clearColor();
        for (int i = 0; i < fm.getLines().size(); i++) {
            scrTxtPane.getJTextPane().setText(scrTxtPane.getJTextPane().getText() + fm.getLines().get(i));
            if (i != fm.getLines().size() - 1) {
                scrTxtPane.getJTextPane().setText(scrTxtPane.getJTextPane().getText() + '\n');
            }
        }
    }

    public boolean getEditable() {
        return scrTxtPane.getJTextPane().isEditable();
    }

    public void setEditable(boolean b) {
        scrTxtPane.getJTextPane().setEditable(b);

        if (b) {
            // 편집 가능
            btnEdit.setIcon(ICON_EDITING);
            txtStatus.setText("편집 가능");
        }
        else {
            // 읽기 전용
            btnEdit.setIcon(ICON_EDIT);
            txtStatus.setText("읽기 전용");
        }
    }
    public JButton getButton(String name) {
        if ( name.equals("Load") ) {
            return this.btnLoad;
        } else if ( name.equals("Save") ) {
            return this.btnSave;
        } else if ( name.equals("Edit") ) {
            return this.btnEdit;
        }
        return null;
    }
}
