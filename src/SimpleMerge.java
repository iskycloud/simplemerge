import View.*;
import Controller.*;
import Model.*;

public class SimpleMerge {

    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(model, view);
    }
}
