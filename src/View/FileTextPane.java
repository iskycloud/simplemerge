/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import javax.swing.*;
import java.awt.*;

public class FileTextPane extends JPanel {
    private JPanel pnlFileNorth, pnlFileMenu;
    private JButton btnLoad, btnSave, btnEdit;
    private JTextField txtPath, txtStatus;
    private ScrollTextPane scrTxtPane;

    FileTextPane() {
        initialize();
    }

    private void initialize() {
        /** 패널 객체 생성 */
        pnlFileNorth = new JPanel();
        pnlFileMenu = new JPanel();

        /** 버튼 객체 생성 */
        btnLoad = new JButton(new ImageIcon("./img/load.png"));
        btnSave = new JButton(new ImageIcon("./img/save.png"));
        btnEdit = new JButton(new ImageIcon("./img/edit.png"));

        /** 텍스트 상자 객체 생성 */
        txtPath = new JTextField();
        txtStatus = new JTextField();

        /** 파일 내용 텍스트상자 객체 생성 */
        scrTxtPane = new ScrollTextPane();

        /** 각 패널 레이아웃 설정 */
        super.setLayout(new BorderLayout());
        pnlFileNorth.setLayout(new BorderLayout());
        pnlFileMenu.setLayout(new GridLayout(1,4));

        /** 각 컴포넌트들 배치 */
        super.add(pnlFileNorth, BorderLayout.NORTH);
        super.add(scrTxtPane, BorderLayout.CENTER);
        super.add(txtStatus, BorderLayout.SOUTH);

        /** 버튼 패널 및 경로 텍스트상자 배치 */
        pnlFileNorth.add(pnlFileMenu, BorderLayout.NORTH);
        pnlFileNorth.add(txtPath, BorderLayout.SOUTH);

        /** 버튼 배치 */
        pnlFileMenu.add(btnLoad, 0);
        pnlFileMenu.add(btnSave, 1);
        pnlFileMenu.add(btnEdit, 2);
    }

    public JTextField getJTextField(String name) {
        if ( name.equals("txtPath") ) return this.txtPath;
        else if ( name.equals("txtStatus") ) return this.txtStatus;
        return null;
    }

    public ScrollTextPane getScrollTextPane() {
        return this.scrTxtPane;
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
