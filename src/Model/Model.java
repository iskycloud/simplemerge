package Model;

import View.*;
/**
 * Created by xpathz on 2017. 5. 19..
 */
public class Model {
    public FileModel leftFileModel, rightFileModel;
    public DiffModel diffModel;

    public Model() {
        diffModel = new DiffModel();
    }

    public Model(View view) {
        diffModel = new DiffModel(view);
    }
}
