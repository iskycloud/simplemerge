package Model;

import java.util.ArrayList;

/**
 * Created by xpathz on 2017. 5. 28..
 */

// DiffBlock 클래스.
// 역할 : 왼쪽, 오른쪽 파일 모델 (혹은 파일 내용)을 비교한 결과를 저장함.
public class DiffBlock {
    private ArrayList<Index> indexes;

    public DiffBlock() {
        this.indexes = new ArrayList<Index>();
    }

    public ArrayList<Index> getIndexes() {
        return this.indexes;
    }
}