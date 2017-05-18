/**
 * Created by iskyc on 2017-05-13.
 */

import View.View;
import Controller.Controller;
import Model.Model;

public class SimpleMerge {
    public static void main(String[] args) {
        View view = new View();
        Model model = new Model(view);
        Controller controller = new Controller(model, view);
        // 모델 -> 컨트롤러에 연동 예정
    }
}
