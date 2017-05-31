/**
 * Created by xpathz on 2017. 5. 19..
 */

package Model;

import View.View;

public class Model {

    // 상수 : 주로 패널의 왼쪽 오른쪽 구분을 위해 사용될 것임.
    public final static String LEFT = "Left";
    public final static String RIGHT = "Right";

    public FileModel leftFileModel, rightFileModel;
    public DiffModel diffModel;

    public Model() {
        leftFileModel = new FileModel(LEFT);
        rightFileModel = new FileModel(RIGHT);
        diffModel = new DiffModel(leftFileModel, rightFileModel);
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

    public DiffModel getDiffModel() {
        return diffModel;
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

    public void textCompare() {
        diffModel.addFakeLine();
        //diffModel.textCompare();
    }
}
