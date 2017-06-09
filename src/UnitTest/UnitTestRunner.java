package UnitTest;

import Model.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.easymock.EasyMock.*;
import java.util.ArrayList;

public class UnitTestRunner extends TestCase {

    private FileModelInterface mock;
    private Model model;


    public UnitTestRunner() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {

    }

    @Before
    public void setUp() {
        mock = createMock(FileModelInterface.class);
        model = new Model();
    }

    @After
    public void tearDown() {

    }

    // 파일을 정상적으로 불러오는지에 대한 테스트.
    // 성공 : 파일모델에 저장된 경로와, 사전에 설정해둔 Mock의 경로가 같다.
    // 실패 : 파일이 존재하지 않거나, 위의 조건을 만족하지 않는다.
    @Test
    public void testLoad() {
        ArrayList<String> result = new ArrayList<String>();
        String testPath = "./example/left.txt"; // 파일 경로 설정

        model.leftFileModel.setFilePath(testPath);
        model.leftFileModel.loadLines();
        assertTrue(model.leftFileModel.isFileLoaded());

        //Expectations
        expect(mock.getFilePath()).andReturn(testPath);
        expect(mock.isFileLoaded()).andReturn(true);
        replay(mock);

        assertTrue(mock.isFileLoaded());
        assertEquals(mock.getFilePath(), model.leftFileModel.getFilePath());
        verify(mock);
    }

    // 파일을 정상적으로 저장하고, 이를 다시 불러와 내용이 제대로 저장되었는지 검증하는 유닛 테스트
    // 성공 : rightFileModel 파일모델 클래스 내의 라인 어레이리스트의 내용과 leftFileModel 파일모델 클래스 내의 라인 어레이리스트 내용이 같다.
    // 실패 : 파일이 존재하지 않거나, 위의 조건을 만족하지 않는다.
    @Test
    public void testSave() {
        ArrayList<String> result = new ArrayList<String>();
        String testPath = "./example/left.txt"; // 파일 경로 설정
        String testDestPath = "./example/right.txt"; // 파일 경로 설정

        model.leftFileModel.setFilePath(testPath);
        model.leftFileModel.loadLines();

        model.leftFileModel.setFilePath(testDestPath);
        model.leftFileModel.saveLines();

        model.rightFileModel.setFilePath(testDestPath);
        model.rightFileModel.loadLines();

        for(int i = 0; i < model.leftFileModel.getLines().size(); i++) {
            assertEquals(model.leftFileModel.getLines().get(i).toString(), model.rightFileModel.getLines().get(i).toString());
        }
    }


    // 두 스트링을 가지고 LCS 알고리즘의 결과가 올바른지 검증하는 유닛 테스트.
    // 성공 : 사전에 정의해둔 스트링들과의 내용과, LCS 알고리즘 결과의 값이 같다.
    // 실패 : 위의 조건을 만족하지 않는다.
    @Test
    public void testLCSAlgorithm() {
        String left = "I believe I can fly";
        String right = "I belpeve I can touch the sky";
        String expectedResult = "I beleve I can y";
        String result = CalcDiff.lcs(left, right);

    assertEquals(expectedResult, result);
}

    // 두 파일 모델들의 라인 어레이리스트간 비교를 실시하며, 이러한 비교와 DiffCharSet 어레이리스트의 결과가 유효한지 검증하는 유닛 테스트.
    // 성공 : 각 파일모델 및 라인 별 LCS 결과 + 해당 라인의 DiffCharSet 사이즈 = 원래 라인의 Length.
    // 실패 : 파일이 존재하지 않거나, 위의 조건을 만족하지 않는다.
    @Test
    public void testCompare() {

        ArrayList<Character> diffChars = new ArrayList<Character>();
        String testPath = "./example/left.txt"; // 파일 경로 설정
        String testDestPath = "./example/right.txt"; // 파일 경로 설정

        model.leftFileModel.setFilePath(testPath);
        model.leftFileModel.loadLines();

        model.rightFileModel.setFilePath(testDestPath);
        model.rightFileModel.loadLines();

        model.compareLines();

        // 줄 수가 일치하는가? 페이크라인 검사임
        assertEquals(model.leftFileModel.getLines().size(), model.rightFileModel.getLines().size());

        // LCS 결과 + 해당 라인의 DiffCharSet 사이즈 = 원래 라인의 Length.
        for(int i = 0; i < model.leftFileModel.getLines().size(); i++) {
            int lcsLength = CalcDiff.lcs(model.leftFileModel.getLines().get(i).toString(), model.rightFileModel.getLines().get(i).toString()).length();
            int diffCharSize = model.leftFileModel.getLines().get(i).getDiffCharSet().size();
            assertEquals(model.leftFileModel.getLines().get(i).toString().length(), lcsLength + diffCharSize);
        }
    }
}