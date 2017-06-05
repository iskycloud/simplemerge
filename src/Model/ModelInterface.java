package Model;

import View.View;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by xpathz on 2017. 6. 5..
 */
public interface ModelInterface {

    FileModel getFileModel(String s);

    int setFileContent(View v, String s);

    void compareLines();

    //TODO: 해당 텍스트판에 위치한 커서가 Merge 작업이 유효한 곳인지 체크.
    int checkMergable(String orgs, int pos);

    //TODO: 전체 머지
    void mergeAll(String orgs, JTextPane left, JTextPane right);

    //TODO: Merge 작업에 사용된 DiffBlock을 삭제함.
    void deleteDiffBlocks();

    //TODO: DiffBlock 어레이리스트를 가져온다.
    ArrayList<DiffBlock> getDiffBlocks(String orgs);

    //TODO: 머지의 결과를 텍스트판에 반영.
    // 근원지와 대상지, 그리고 블록의 시작, 마지막 인덱스를 각각 가져오고,
    // 이를 바탕으로 substring과 concat을 적절히 이용하여 머지를 실시한다.
    void mergeResult(String dest, int index, JTextPane orgP);

}
