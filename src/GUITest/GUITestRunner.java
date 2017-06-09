package GUITest;

import Model.Model;
import Controller.Controller;
import View.*;
import junit.framework.TestCase;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import javax.swing.*;
import static java.lang.Thread.sleep;


public class GUITestRunner extends TestCase {
    private FrameFixture frameFixture;
    private Robot robot;
    private View view;
    private Model model;
    private Controller controller;
    private JFrame frame;

    public GUITestRunner() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {

    }

    // 테스트를 수행하기전 설정.
    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        model = new Model();
        view = new View();
        controller = new Controller(model, view);

        assertNotNull("Can't access the View.");

        view.getFileTextPane("Left").getButton(FileTextPane.BTN_LOAD).setName(Controller.BTN_LEFT_LOAD);
        view.getFileTextPane("Right").getButton(FileTextPane.BTN_LOAD).setName(Controller.BTN_RIGHT_LOAD);
        view.getFileTextPane("Left").getButton(FileTextPane.BTN_SAVE).setName(Controller.BTN_LEFT_SAVE);
        view.getFileTextPane("Right").getButton(FileTextPane.BTN_SAVE).setName(Controller.BTN_RIGHT_SAVE);
        view.getFileTextPane("Left").getButton(FileTextPane.BTN_EDIT).setName(Controller.BTN_LEFT_EDIT);
        view.getFileTextPane("Right").getButton(FileTextPane.BTN_EDIT).setName(Controller.BTN_RIGHT_EDIT);
        assertNotNull("Can't access the buttons.");

        view.getFileTextPane("Left").getTxtStatus().setName("leftTxtStatus");
        assertNotNull("Can't access the JTextField.");
        view.getFileTextPane("Left").getTxtPath().setName("leftTxtPath");
        assertNotNull("Can't access the JTextField.");

        view.getFileTextPane("Right").getTxtStatus().setName("rightTxtStatus");
        assertNotNull("Can't access the JTextField.");

        view.getFileTextPane("Right").getTxtPath().setName("rightTxtPath");
        assertNotNull("Can't access the JTextField.");

        view.getFileTextPane("Left").getScrollTextPane().getJTextPane().setName("leftJTextPane");
        assertNotNull("Can't access the JTextPane.");

        view.getFileTextPane("Right").getScrollTextPane().getJTextPane().setName("rightJTextPane");
        assertNotNull("Can't access the JTextPane.");

        view.getCtrlPane().getButton(ControlPane.BTN_COMPARE).setName(Controller.BTN_COMPARE);
        view.getCtrlPane().getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT).setName(Controller.BTN_MERGE_LEFT_TO_RIGHT);
        view.getCtrlPane().getButton(ControlPane.BTN_MERGE_LEFT_TO_RIGHT_ALL).setName(Controller.BTN_MERGE_LEFT_TO_RIGHT_ALL);
        view.getCtrlPane().getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT).setName(Controller.BTN_MERGE_RIGHT_TO_LEFT);
        view.getCtrlPane().getButton(ControlPane.BTN_MERGE_RIGHT_TO_LEFT_ALL).setName(Controller.BTN_MERGE_RIGHT_TO_LEFT_ALL);
        assertNotNull("Can't access the buttons.");

        frame = view.getFrame();
        frame.setSize(600, 500);
        frameFixture = new FrameFixture(robot, frame);
        frameFixture.show();
    }

    // 테스트를 모두 실시하면 프레임 제거.
    @After
    public void tearDown() {
        frameFixture.cleanUp();
    }

    // 버튼 이벤트 테스트. (Dialog가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnCompare() throws Exception {
        frameFixture.button(Controller.BTN_COMPARE).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (Dialog가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnMergeLeftToRight() throws Exception {
        frameFixture.button(Controller.BTN_MERGE_LEFT_TO_RIGHT).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (Dialog가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnMergeLeftToRightAll() throws Exception {
        frameFixture.button(Controller.BTN_MERGE_LEFT_TO_RIGHT_ALL).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (Dialog가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnMergeRightToLeft() throws Exception {
        frameFixture.button(Controller.BTN_MERGE_RIGHT_TO_LEFT).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (Dialog가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnMergeRightToLeftAll() throws Exception {
        frameFixture.button(Controller.BTN_MERGE_RIGHT_TO_LEFT_ALL).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnLeftLoad() throws Exception {
        frameFixture.button(Controller.BTN_LEFT_LOAD).click();
        frameFixture.fileChooser().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnRightLoad() throws Exception {
        frameFixture.button(Controller.BTN_RIGHT_LOAD).click();
        frameFixture.fileChooser().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnLeftSave() throws Exception {
        frameFixture.button(Controller.BTN_LEFT_SAVE).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnRightSave() throws Exception {
        frameFixture.button(Controller.BTN_RIGHT_SAVE).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnLeftEdit() throws Exception {
        frameFixture.button(Controller.BTN_LEFT_EDIT).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 버튼 이벤트 테스트. (FileChooser가 나와야함. 안그러면 Assert 에러 처리.)
    @Test
    public void testClickBtnRightEdit() throws Exception {
        frameFixture.button(Controller.BTN_RIGHT_EDIT).click();
        frameFixture.dialog().requireVisible();
        sleep(2000);
    }

    // 텍스트필드 테스트. (해당 내용이 텍스트필드 내에 나와야함. 안그러면 에러 처리.)
    @Test
    public void testInputTextFields() throws Exception {
        frameFixture.textBox("leftTxtStatus").setText("This box worked well.");
        frameFixture.textBox("leftTxtPath").setText("This box worked well.");
        frameFixture.textBox("rightTxtStatus").setText("This box worked well.");
        frameFixture.textBox("rightTxtPath").setText("This box worked well.");

        // 제대로 텍스트가 들어갔는지 ASSERT.
        frameFixture.textBox("leftTxtStatus").requireText("This box worked well.");
        frameFixture.textBox("leftTxtPath").requireText("This box worked well.");
        frameFixture.textBox("rightTxtStatus").requireText("This box worked well.");
        frameFixture.textBox("rightTxtPath").requireText("This box worked well.");
        sleep(2000);
    }

    // 텍스트필드 테스트. (해당 내용이 텍스트필드 내에 나와야함. 안그러면 에러 처리.)
    @Test
    public void testInputJTextAreas() throws Exception {
        frameFixture.textBox("leftJTextPane").setText("This box worked well.");
        frameFixture.textBox("rightJTextPane").setText("This box worked well.");

        // 제대로 텍스트가 들어갔는지 ASSERT.
        frameFixture.textBox("leftJTextPane").requireText("This box worked well.");
        frameFixture.textBox("rightJTextPane").requireText("This box worked well.");
        sleep(2000);
    }
}
