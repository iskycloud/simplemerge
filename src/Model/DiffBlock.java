package Model;

import java.util.ArrayList;

/**
 * Created by xpathz on 2017. 5. 28..
 */

// DiffBlock 클래스.
// 역할 : 차이가 나는 부분의 시작 인덱스와 마지막 인덱스를 저장함.
public class DiffBlock {
    private int first;

    public DiffBlock() {

    }

    public DiffBlock(int first) {
        this.first = first;
    }

    public int getFirst() {
        return this.first;
    }
}