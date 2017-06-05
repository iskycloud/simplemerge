package Model;

import View.View;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by xpathz on 2017. 6. 5..
 */
public interface FileModelInterface {

    // 파일 설정.
    public void setFilePath(String filePath);

    // 파일로부터 줄 읽어오는 작업.
    // 어레이리스트에 저장된다!
    public void loadLines();

    // 편집 후의 텍스트판을 가져와
    // 새로운 어레이리스트로 읽음.
    public void setLines(JTextPane jp);

    public void editLine(int row, String text);

    // 파일이 로드되었는지?
    public boolean isFileLoaded();

    void saveLines();

    void setTarget(String t);

    String getTarget();

    String getFilePath();

    // 어레이리스트 가져오기.
    ArrayList<Line> getLines();

    // 파일을 불러온다
    int setFileContent(View v);
}
