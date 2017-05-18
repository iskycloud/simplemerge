/**
 * Created by xstel on 2017-05-14.
 */

package Model;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import View.View;

public class DiffModel extends Model {

    private View view;
    private JTextPane txtLeftPane, txtRightPane;

    public DiffModel() {

    }

    public DiffModel(View view) {
        this.view = view;
        this.txtLeftPane = view.getJTextPane("txtLeftTextPane");
        this.txtRightPane = view.getJTextPane("txtRightTextPane");

        leftFileModel = new FileModel("Left");
        rightFileModel = new FileModel("Right");
    }

    // LCS 알고리즘
    // 갓-위키피디아의 알고리즘과 완죤 똑같음
    public String lcs(String l, String r) {
        int m = l.length();
        int n = r.length();
        int[][] L = new int[m+1][n+1];

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if ( l.charAt(i) == r.charAt(j) ) { // 글자가 같으면 1점 더함
                    L[i+1][j+1] = L[i][j] + 1;
                } else { // 같지 않으면 1을 더하지 않음. Max값 이용하여 이전 값으로 할당.
                    L[i+1][j+1] = Math.max(L[i+1][j], L[i][j+1]);
                }
            }
        }

        System.out.println("LCS 값 : " + L[m][n]);

        // 배열을 이용하여 일치하는 값들을 스트링으로 뽑아냄
        // x = 0 y = 0으로 초기화 하고 while 조건을 x < m && y < n 으로 해두고
        // x-- 대신 x++, y-- 대신 y++으로 하면 return 할때 sb.reverse ~~ 대신 sb.toString로 해도 코드는 동일하게 작동됨.
        StringBuffer sb = new StringBuffer();
        int x = m, y = n;
        while ( x != 0 && y != 0 ) {
            if ( L[x][y] == L[x-1][y] ) { // Max 당시 기준 x-1, y 값이었을 경우 x 1 감소
                x--;
            }
            else if ( L[x][y] == L[x][y-1] )  { // Max 당시 기준 x, y-1 값이었을 경우 y 1 감소
                y--;
            }
            else {
                if (l.charAt(x-1) == r.charAt(y-1)) sb.append(l.charAt(x-1)); // 같으면 스트링버퍼에 푸쉬
                x--;
                y--;
            }
        }

        return sb.reverse().toString();
    }

    // 텍스트 비교 메소드
    public void textCompare() {
        // 둘다 파일 경로가 올바른지?
        if ( leftFileModel.getFilePath().compareTo("") != 0 && rightFileModel.getFilePath().compareTo("") != 0 ) {
            // 텍스트를 한 줄 단위로 스트링 배열에 저장한다.
            ArrayList<String> leftTexts, rightTexts;
            leftTexts = leftFileModel.getLines();
            rightTexts = rightFileModel.getLines();

            clearColor("Left");
            clearColor("Right");

            // 텍스트판에 적용시킬 스타일. 컬러 적용에 사용됨.
            StyledDocument ldoc = txtLeftPane.getStyledDocument(), rdoc = txtRightPane.getStyledDocument();
            StyleContext sc = StyleContext.getDefaultStyleContext();
            int prevLeftAddress = 0, prevRightAddress = 0;
            AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, Color.YELLOW);

            // 왼쪽 파일 텍스트를 기준점으로 잡고, 열 단위로 lcs를 수행한다.
            for(int row = 0; row < leftTexts.size(); row++ ) {
                // 왼쪽 파일 텍스트와 오른쪽 파일 텍스트 (각각 한줄 씩)에 대해 lcs를 수행한다
                // 결과값은 스트링에 저장되며, 이는 색칠 과정에 사용될 것임.
                String result = this.lcs(leftTexts.get(row), rightTexts.get(row));
                System.out.println(result);
                // 왼쪽 파일 텍스트의 한 줄과 결과 값 비교 시작!
                for (int i = 0, address = 0; i < leftTexts.get(row).length(); i++) {
                    if (result.charAt(address) != leftTexts.get(row).charAt(i)) {
                        // 글자가 일치하지 않으면, 색칠
                        ldoc.setCharacterAttributes(prevLeftAddress + i, 1, attrs, true);
                    } else { // 일치하면 다음 어드레스로 넘김
                        address++;
                    }
                }

                // 오른쪽 파일 텍스트의 한 줄과 결과 값을 비교하며
                // 과정은 위와 똑같다.
                for (int i = 0, address = 0; i < rightTexts.get(row).length(); i++) {
                    if (result.charAt(address) != rightTexts.get(row).charAt(i)) {
                        rdoc.setCharacterAttributes(prevRightAddress + i, 1, attrs, true);
                    } else {
                        address++;
                    }
                }

                // 가장 마지막 위치의 어드레스를 기준으로 다음 어드레스를 저장한다.
                // 이들을 저장하는 이유는, 색칠할 때 오프셋으로 적용시킬 것이기 때문이다.
                // 제이텍스트판에 사용될 도큐먼트의 오프셋이 첫줄부터 끝줄까지 연속되기 때문.
                prevLeftAddress += leftTexts.get(row).length() + 1;
                prevRightAddress += rightTexts.get(row).length() + 1;
            }
        }
    }

    public void clearColor(String name) {
        if ( name.equals("Left") ) {
            StyledDocument ldoc = txtLeftPane.getStyledDocument();
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, Color.WHITE);
            ldoc.setCharacterAttributes(0, txtLeftPane.getText().length(), attrs, true);
        } else if ( name.equals("Right") ) {
            StyledDocument rdoc = txtRightPane.getStyledDocument();
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, Color.WHITE);
            rdoc.setCharacterAttributes(0, txtRightPane.getText().length(), attrs, true);
        }
    }

    // 왼쪽 파일을 불러온다
    public void setFileContent(String target) {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(view.getFrame());

        if( returnVal == JFileChooser.APPROVE_OPTION) {
            //OPEN 버튼 누를 시
            if (target.equals("Left")) { // 왼쪽 패널에서 오픈 버튼 눌렀다
                File file = fileChooser.getSelectedFile();
                leftFileModel.setFilePath(file.getPath());
                view.getJTextField("txtLeftPath").setText(leftFileModel.getFilePath());

                txtLeftPane.setText("");
                clearColor("Left");
                leftFileModel.setLines();

                for (int i = 0; i < leftFileModel.getLines().size(); i++) {
                    txtLeftPane.setText(txtLeftPane.getText() + leftFileModel.getLines().get(i));
                    if (i != leftFileModel.getLines().size() - 1) txtLeftPane.setText(txtLeftPane.getText() + '\n');
                }
            } else if (target.equals("Right") ) { // 오른쪽 패널에서 오픈 버튼 눌렀다
                File file = fileChooser.getSelectedFile();
                rightFileModel.setFilePath(file.getPath());
                view.getJTextField("txtRightPath").setText(rightFileModel.getFilePath());

                txtRightPane.setText("");
                clearColor("Right");
                rightFileModel.setLines();

                for (int i = 0; i < rightFileModel.getLines().size(); i++) {
                    txtRightPane.setText(txtRightPane.getText() + rightFileModel.getLines().get(i));
                    if (i != rightFileModel.getLines().size() - 1) txtRightPane.setText(txtRightPane.getText() + '\n');
                }
            }
        }
    }

}
