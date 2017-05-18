package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xpathz on 2017. 5. 18..
 */
public class FileModel {
    private String target;
    private String filePath;
    private ArrayList<String> lines;

    // 디폴트 컨스트럭터
    // 초기화 용도에 사용되는 정도.
    public FileModel() {
        this.target = "";
        this.filePath = "";
        this.lines = new ArrayList<String>();
    }

    // 타겟 이름을 파라미터로 가져오는 컨스트럭터
    public FileModel(String t) {
        this.target = t;
        this.filePath = "";
        this.lines = new ArrayList<String>();
    }

    // 파일 설정.
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // 파일로부터 줄 읽어오는 작업.
    // 어레이리스트에 저장된다!
    public void setLines() {
        try {
            String l = "";
            BufferedReader in = new BufferedReader(new FileReader(filePath));

            while (l != null) {
                l = in.readLine();
                if (l == null) break;
                lines.add(l);
            }

            in.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    // 이 클래스의 이름을 정의.
    // 주로 Left 혹은 Right로 설정될 것.
    public void setTarget(String t) {
        this.target = t;
    }

    // 이름 가져오기.
    public String getTarget() {
        return this.target;
    }

    public String getFilePath() {
        return this.filePath;
    }

    // 어레이리스트 가져오기.
    public ArrayList<String> getLines() {
        return this.lines;
    }
}
