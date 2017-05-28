package Model;

/**
 * Created by xpathz on 2017. 5. 28..
 */

// Index 클래스 : 역할 - DiffBlock 클래스 내에 어레이리스트 자료형으로 할용. 차이가 나는 부분의 시작 인덱스와 마지막 인덱스를 저장함.
public class Index {
    private int first, last;

    public Index() {

    }

    public Index(int first, int last) {
        this.first = first;
        this.last = last;
    }

    public int First() {
        return this.first;
    }

    public int Last() {
        return this.last;
    }
}