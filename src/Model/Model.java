/**
 * Created by xpathz on 2017. 5. 19..
 */

package Model;

import View.View;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model implements ModelInterface {

    // 상수 : 주로 패널의 왼쪽 오른쪽 구분을 위해 사용될 것임.
    public final static String LEFT = "Left";
    public final static String RIGHT = "Right";

    public FileModel leftFileModel, rightFileModel;
    private ArrayList<DiffBlock> leftDiffBlocks = new ArrayList<DiffBlock>(); // 왼쪽 파일 모델 (혹은 파일 내용)을 비교한 결과를 저장함.
    private ArrayList<DiffBlock> rightDiffBlocks = new ArrayList<DiffBlock>(); // 오른쪽 파일 모델 (혹은 파일 내용)을 비교한 결과를 저장함.

    public Model() {
        leftFileModel = new FileModel(LEFT);
        rightFileModel = new FileModel(RIGHT);
    }

    public FileModel getFileModel(String s) {
        if (s.equals(LEFT)) {
            return leftFileModel;
        }
        else if (s.equals(RIGHT)) {
            return rightFileModel;
        }
        return null; //TODO Exception
    }

    public int setFileContent(View v, String s) {
        int returnVal = -1;
        if (s.equals(LEFT)) {
            returnVal = leftFileModel.setFileContent(v);
        }
        else if (s.equals(RIGHT)) {
            returnVal = rightFileModel.setFileContent(v);
        }
        return returnVal;
    }

    public void compareLines() {
        // 둘다 파일 경로가 올바른지?
        if ( leftFileModel.isFileLoaded() && rightFileModel.isFileLoaded() ) {
            if ( leftFileModel.getLines().size() != rightFileModel.getLines().size() ) {
                CalcDiff.addFakeLine(leftFileModel.getLines(), rightFileModel.getLines());
            }
            CalcDiff.compareLines(leftFileModel.getLines(), rightFileModel.getLines(), leftDiffBlocks, rightDiffBlocks);
        }
    }

    //TODO: 해당 텍스트판에 위치한 커서가 Merge 작업이 유효한 곳인지 체크. (TODO : 추가되었다는 의미)
    // 리턴형은 해당하는 커서 포인트 어드레스를 가지고 있는 LINE의 ROW.
    public int checkMergable(String orgs, int pos) {
        // 해당 위치가 Diff 캐릭터셋에 포함되어있는지?
        if ( orgs.equals(Model.LEFT) ) {
            for (int i = 0; i < leftDiffBlocks.size(); i++) {
                if (leftDiffBlocks.get(i).getFirst() <= pos && leftDiffBlocks.get(i).getLast() >= pos) return i;
            }
        } else if ( orgs.equals(Model.RIGHT) ) {
            for(int i = 0; i < rightDiffBlocks.size(); i++) {
                if ( rightDiffBlocks.get(i).getFirst() <= pos && rightDiffBlocks.get(i).getLast() >= pos ) return i;
            }
        }
        return -1;
    }

    //TODO: 전체 머지
    //TODO: 버그 사항 = 한 줄씩 채워나가다보니까 그 글자가 변동되면 전체 어드레스가 바뀌어 일부 블록이 제대로 반영 안되는 경우가 있음
    //TODO: 이를 위한 해결책? : 레프트블록과 라이트블록의 어드레스 길이 차이를 이용해서 보정해주면 가능할 것 같은데 이는 내가 연구해보겠음.
    public void mergeAll(String orgs, JTextPane left, JTextPane right) {
        if ( orgs.equals(Model.LEFT) ) {
            for(int x = leftDiffBlocks.size()-1; x >= 0; x--) {
                int startDestPos = this.rightDiffBlocks.get(x).getFirst();
                int finishDestPos = this.rightDiffBlocks.get(x).getLast();

                int startOrgPos = this.leftDiffBlocks.get(x).getFirst();
                int finishOrgPos = this.leftDiffBlocks.get(x).getLast();

                for (int i = 0, address = 0; i < rightFileModel.getLines().size(); i++) {
                    if ( startDestPos <= address + rightFileModel.getLines().get(i).toString().length()) {
                        String newLine = this.rightFileModel.getLines().get(i).toString().substring(0, startDestPos - address);
                        newLine += left.getText().substring(startOrgPos, finishOrgPos + 1);
                        if (finishDestPos - address + 1 < this.rightFileModel.getLines().get(i).toString().length() + 1) {
                            newLine += this.rightFileModel.getLines().get(i).toString().substring(finishDestPos - address + 1);
                        }
                        rightFileModel.getLines().get(i).setString(newLine);
                        break;
                    }

                    address += (rightFileModel.getLines().get(i).toString().length() + 1);
                }
            }
        } else if ( orgs.equals(Model.RIGHT) ) {
            for(int x = leftDiffBlocks.size()-1; x >= 0; x--) {
                int startDestPos = this.leftDiffBlocks.get(x).getFirst();
                int finishDestPos = this.leftDiffBlocks.get(x).getLast();

                int startOrgPos = this.rightDiffBlocks.get(x).getFirst();
                int finishOrgPos = this.rightDiffBlocks.get(x).getLast();

                for (int i = 0, address = 0; i < leftFileModel.getLines().size(); i++) {
                    if ( startDestPos <= address + leftFileModel.getLines().get(i).toString().length()) {
                        String newLine = this.leftFileModel.getLines().get(i).toString().substring(0, startDestPos - address);
                        newLine += right.getText().substring(startOrgPos, finishOrgPos + 1);
                        if (finishDestPos - address + 1 < this.leftFileModel.getLines().get(i).toString().length() + 1) {
                            newLine += this.leftFileModel.getLines().get(i).toString().substring(finishDestPos - address + 1);
                        }
                        leftFileModel.getLines().get(i).setString(newLine);
                        break;
                    }

                    address += (leftFileModel.getLines().get(i).toString().length() + 1);
                }
            }
        }
    }

    //TODO: Merge 작업에 사용된 DiffBlock을 삭제함. (TODO : 추가되었다는 의미)
    public void deleteDiffBlocks() {
        leftDiffBlocks.clear();
        rightDiffBlocks.clear();
    }

    //TODO: DiffBlock 어레이리스트를 가져온다. (TODO : 추가되었다는 의미)
    public ArrayList<DiffBlock> getDiffBlocks(String orgs) {
        if ( orgs.equals(Model.LEFT) ) return leftDiffBlocks;
        else if ( orgs.equals(Model.RIGHT) ) return rightDiffBlocks;
        return null;
    }

    //TODO: 머지의 결과를 텍스트판에 반영.  (TODO : 추가되었다는 의미)
    // 근원지와 대상지, 그리고 블록의 시작, 마지막 인덱스를 각각 가져오고,
    // 이를 바탕으로 substring과 concat을 적절히 이용하여 머지를 실시한다.
    public void mergeResult(String dest, int index, JTextPane orgP) {
        if ( dest.equals(Model.RIGHT) ) {
            int startDestPos = this.rightDiffBlocks.get(index).getFirst();
            int finishDestPos = this.rightDiffBlocks.get(index).getLast();

            int startOrgPos = this.leftDiffBlocks.get(index).getFirst();
            int finishOrgPos = this.leftDiffBlocks.get(index).getLast();

            int row = -1;
            int address = 0;
            for(int i = 0; i < rightFileModel.getLines().size(); address += rightFileModel.getLines().get(i).toString().length() + 1, i++) {
                if ( startDestPos <= address + rightFileModel.getLines().get(i).toString().length()) {
                    row = i;
                    break;
                }
            }

            if ( row != -1 ) {
                String newLine = this.rightFileModel.getLines().get(row).toString().substring(0, startDestPos - address);
                newLine += orgP.getText().substring(startOrgPos, finishOrgPos+1);
                if ( finishDestPos - address + 1 < this.rightFileModel.getLines().get(row).toString().length() + 1 ) {
                    newLine += this.rightFileModel.getLines().get(row).toString().substring(finishDestPos - address + 1);
                }
                rightFileModel.getLines().get(row).setString(newLine);
                //System.out.println(newLine);
            }

        } else if ( dest.equals(Model.LEFT) ) {
            int startDestPos = this.leftDiffBlocks.get(index).getFirst();
            int finishDestPos = this.leftDiffBlocks.get(index).getLast();

            int startOrgPos = this.rightDiffBlocks.get(index).getFirst();
            int finishOrgPos = this.rightDiffBlocks.get(index).getLast();

            int row = -1;
            int address = 0;
            for(int i = 0; i < leftFileModel.getLines().size(); i++) {
                if ( startDestPos <= address && finishDestPos >= address ) {
                    row = i;
                    break;
                }
                address += leftFileModel.getLines().get(i).toString().length() + 1;
            }

            if ( row != -1 ) {
                String newLine = this.leftFileModel.getLines().get(row).toString().substring(0, startDestPos - address);
                newLine += orgP.getText().substring(startOrgPos, finishOrgPos+1);
                if ( finishDestPos - address + 1 < this.leftFileModel.getLines().get(row).toString().length() + 1 ) {
                    newLine += this.leftFileModel.getLines().get(row).toString().substring(finishDestPos - address + 1);
                }
                leftFileModel.getLines().get(row).setString(newLine);
                //System.out.println(newLine);
            }

        }
        /*
        String copy = this.getScrollTextPane(orgs).getJTextPane().getText().substring(startOrgPos, endOrgPos+1);
        String copied = this.getScrollTextPane(dest).getJTextPane().getText().substring(0, startDstPos);
        copied += copy;
        copied += this.getScrollTextPane(dest).getJTextPane().getText().substring(endDstPos+1);

        getScrollTextPane(dest).getJTextPane().setText(copied);
        */
    }

}
