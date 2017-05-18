package Model;

import View.*;
/**
 * Created by xpathz on 2017. 5. 19..
 */
public class Model {

    // 상수 : 주로 패널의 왼쪽 오른쪽 구분을 위해 사용될 것임.
    public static String LEFT = "Left";
    public static String RIGHT = "Right";

    public FileModel leftFileModel, rightFileModel;
    public DiffModel diffModel;

    public Model() {
    }

    public Model(View view) {
        diffModel = new DiffModel(view);
    }
}
