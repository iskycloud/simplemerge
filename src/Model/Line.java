package Model;

import java.util.ArrayList;

public class Line {
    private String line;   /* 한 줄의 내용 저장 */
    private ArrayList<Integer> diffCharSet;
    private int state;     /* 0 : 정상, 1 : 비교 후 다른 상태, -1 : Fake line */

    Line() {
        this.line = "";
        this.diffCharSet = new ArrayList<Integer>();
        this.state = 0;
    }

    public Line(String s, int i) {
        this.line = s;
        this.diffCharSet = new ArrayList<Integer>();
        this.state = i;
    }

    public void initDiffCharSet() {
        this.diffCharSet.clear();
        if (state == 1) {
            state = 0;
        }
    }

    public void setString(String text) { this.line = text; }

    @Override
    public String toString() {
        return this.line;
    }

    public ArrayList<Integer> getDiffCharSet() {
        return this.diffCharSet;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        if (this.state >= 0) {
            this.state = i;
        }
    }
}
