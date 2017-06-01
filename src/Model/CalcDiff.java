/**
 * Created by xstel on 2017-05-14.
 */

package Model;

import java.util.ArrayList;

public class CalcDiff {
    // LCS 알고리즘
    // 갓-위키피디아의 알고리즘과 완죤 똑같음
    public static String lcs(String l, String r) {
        int m = l.length();
        int n = r.length();
        int[][] L = new int[m+1][n+1];

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if ( l.charAt(i) == r.charAt(j) ) { // 글자가 같으면 1점 더함
                    L[i+1][j+1] = L[i][j] + 1;
                } else { // 같지 않으면 1을 더하지 않음. Max값 이용하여 이전 값으로 할당.
                    L[i+1][j+1] = Math.max(L[i+1][j], L[i][j+1]);
                }
            }
        }

        System.out.println("LCS 값 : " + L[m][n]);

        // 배열을 이용하여 일치하는 값들을 스트링으로 뽑아냄
        // x = 0 y = 0으로 초기화 하고 while 조건을 x < m && y < n 으로 해두고
        // x-- 대신 x++, y-- 대신 y++으로 하면 return 할때 sb.reverse ~~ 대신 sb.toString로 해도 코드는 동일하게 작동됨.
        StringBuffer sb = new StringBuffer();
        int x = m, y = n;
        while ( x != 0 && y != 0 ) {
            if ( L[x][y] == L[x-1][y] ) { // Max 당시 기준 x-1, y 값이었을 경우 x 1 감소
                x--;
            }
            else if ( L[x][y] == L[x][y-1] )  { // Max 당시 기준 x, y-1 값이었을 경우 y 1 감소
                y--;
            }
            else {
                if (l.charAt(x-1) == r.charAt(y-1)) sb.append(l.charAt(x-1)); // 같으면 스트링버퍼에 푸쉬
                x--;
                y--;
            }
        }

        return sb.reverse().toString();
    }


    // Fake Line
    public static void addFakeLine(ArrayList<Line> leftLines, ArrayList<Line> rightLines) {
        // TODO Modify : Fake Line String
        Line fakeLine = new Line("", -1);
        ArrayList<Line> minLines;
        int row, addLeftRow, addRightRow, diffLineSize;

        for (row = 0; row < leftLines.size() && row < rightLines.size(); row++) {
            if (leftLines.get(row).getState() >= 0 && rightLines.get(row).getState() >= 0) {
                // 왼쪽 = 공백라인 O, 오른쪽 = 공백라인 X
                if (leftLines.get(row).toString().trim().length() == 0 && rightLines.get(row).toString().trim().length() != 0) {
                    rightLines.add(row, fakeLine);
                }
                // 왼쪽 = 공백라인 X, 오른쪽 = 공백라인 O
                else if (leftLines.get(row).toString().trim().length() != 0 && rightLines.get(row).toString().trim().length() == 0) {
                    leftLines.add(row, fakeLine);
                }
                // 왼쪽 = 공백라인 O, 오른쪽 = 공백라인 O -> continue
                // 왼쪽 = 공백라인 X, 오른쪽 = 공백라인 X
                else if (leftLines.get(row).toString().trim().length() != 0 && rightLines.get(row).toString().trim().length() != 0) {
                    addLeftRow = -1;
                    addRightRow = -1;

                    for (int i = row; i < rightLines.size(); i++) {
                        //if (leftLines.get(row).toString().equals(rightLines.get(i).toString())) {
                        if (lcs(leftLines.get(row).toString(), rightLines.get(i).toString()).length() > 0) {
                            addLeftRow = i;
                            break;
                        }
                    }

                    for (int i = row; i < leftLines.size(); i++) {
                        //if (rightLines.get(row).toString().equals(leftLines.get(i).toString())) {
                        if (lcs(leftLines.get(i).toString(), rightLines.get(row).toString()).length() > 0) {
                            addRightRow = i;
                            break;
                        }
                    }

                    if (addRightRow >= 0 && (addLeftRow > addRightRow || addLeftRow < 0)) {
                        for (int i = 0; i < (addRightRow - row); i++) {
                            rightLines.add(row, fakeLine);
                        }
                        row = addRightRow;
                    }
                    else if (addLeftRow >= 0 && (addLeftRow < addRightRow || addRightRow < 0)) {
                        for (int i = 0; i < (addLeftRow - row); i++) {
                            leftLines.add(row, fakeLine);
                        }
                        row = addLeftRow;
                    }
                }
            }
        }

        diffLineSize = Math.max(leftLines.size(), rightLines.size()) - Math.min(leftLines.size(), rightLines.size());
        System.out.println("Diff Line Size: " + diffLineSize);
        if (diffLineSize > 0) {
            if (leftLines.size() >= rightLines.size()) {
                minLines = rightLines;
            }
            else {
                minLines = leftLines;
            }

            for (int i = 0; i < diffLineSize; i++) {
                minLines.add(fakeLine);
            }
        }

        // TODO Remove : Print console for debug
        System.out.println("Left:");
        for (int i = 0; i < leftLines.size(); i++) {
            System.out.println(leftLines.get(i).getState() + " : " + leftLines.get(i).toString());
        }
        System.out.println("Right:");
        for (int i = 0; i < rightLines.size(); i++) {
            System.out.println(rightLines.get(i).getState() + " : " + rightLines.get(i).toString());
        }
    }

    // 텍스트 비교 메소드
    public static void compareLines(ArrayList<Line> leftLines, ArrayList<Line> rightLines, ArrayList<DiffBlock> leftDiffBlocks, ArrayList<DiffBlock> rightDiffBlocks) {
        int prevLeftAddress = 0, prevRightAddress = 0;

        for(int i = 0; i < leftLines.size(); i++) {
            leftLines.get(i).getDiffCharSet().clear();
        }

        for(int i = 0; i < rightLines.size(); i++) {
            rightLines.get(i).getDiffCharSet().clear();
        }
        leftDiffBlocks.clear();
        rightDiffBlocks.clear();

        // 왼쪽 파일 텍스트를 기준점으로 잡고, 열 단위로 lcs를 수행한다.
        for(int row = 0; row < leftLines.size(); row++ ) {
            // 왼쪽 파일 텍스트와 오른쪽 파일 텍스트 (각각 한줄 씩)에 대해 lcs를 수행한다
            // 결과값은 스트링에 저장되며, 이는 색칠 과정에 사용될 것임.
            String result = lcs(leftLines.get(row).toString(), rightLines.get(row).toString());
            int lastLeftAddress = 0, lastRightAddress = 0;
            System.out.println(result);

            // 다른 글자를 담는 ArrayList 초기화
            leftLines.get(row).initDiffCharSet();
            rightLines.get(row).initDiffCharSet();

            int firstAddress = -1, lastAddress = -1;
            // 왼쪽 파일 텍스트의 한 줄과 결과 값 비교 시작!
            for (int i = 0, address = 0; i < leftLines.get(row).toString().length() && address < result.length(); i++) {
                if (result.charAt(address) != leftLines.get(row).toString().charAt(i)) {
                    // 글자가 일치하지 않으면, 추가
                    leftLines.get(row).setState(1);
                    leftLines.get(row).getDiffCharSet().add(prevLeftAddress + i);
                    if ( firstAddress == -1 ) { firstAddress = prevLeftAddress + i; }
                } else { // 일치하면 다음 어드레스로 넘김
                    if ( firstAddress != -1 && lastAddress == -1 ) {
                        lastAddress = prevLeftAddress + i - 1;
                        leftDiffBlocks.add(new DiffBlock(firstAddress, lastAddress));//TODO: 왼쪽 텍스트에서 차이가 나는 부분을 DiffBlock으로 저장.  (TODO : 추가되었다는 의미)
                        firstAddress = -1; lastAddress = -1;
                    }
                    lastLeftAddress = i;
                    address++;
                }
            }

            // 오른쪽 파일 텍스트의 한 줄과 결과 값을 비교하며
            // 과정은 위와 똑같다.
            for (int i = 0, address = 0; i < rightLines.get(row).toString().length() && address < result.length(); i++) {
                if (result.charAt(address) != rightLines.get(row).toString().charAt(i)) {
                    // 글자가 일치하지 않으면, 추가
                    rightLines.get(row).setState(1);
                    rightLines.get(row).getDiffCharSet().add(prevRightAddress + i);
                    if ( firstAddress == -1 ) { firstAddress = prevRightAddress + i; }
                } else { // 일치하면 다음 어드레스로 넘김
                    if ( firstAddress != -1 && lastAddress == -1 ) {
                        lastAddress = prevRightAddress + i - 1;
                        rightDiffBlocks.add(new DiffBlock(firstAddress, lastAddress));//TODO: 오른쪽 텍스트에서 차이가 나는 부분을 DiffBlock으로 저장.  (TODO : 추가되었다는 의미)
                        firstAddress = -1; lastAddress = -1;
                    }
                    lastRightAddress = i;
                    address++;
                }
            }

            // 비교 결과 후, 이후의 텍스트들은 차이점임을 의미하므로, 이후의 텍스트들을 모두 결과에 포함시킴.
            if ( leftLines.get(row).toString().length()-1 > lastLeftAddress ) {
                for (int i = lastLeftAddress+1; i < leftLines.get(row).toString().length(); i++ ) {
                    leftLines.get(row).setState(1);
                    leftLines.get(row).getDiffCharSet().add(prevLeftAddress +i);
                }

                int startAddress = prevLeftAddress+lastLeftAddress+1;
                int finishAddress = prevLeftAddress+leftLines.get(row).toString().length()-1;
                //TODO: 왼쪽 부분은 가장 끝 인덱스 두개를 저장합니다. (TODO : 추가되었다는 의미)
                rightDiffBlocks.add(new DiffBlock(prevRightAddress+rightLines.get(row).toString().length(),prevRightAddress+rightLines.get(row).toString().length()));
                leftDiffBlocks.add(new DiffBlock(startAddress, finishAddress)); //TODO: 왼쪽 텍스트에서 차이가 나는 부분을 DiffBlock으로 저장.  (TODO : 추가되었다는 의미)
            }

            if ( rightLines.get(row).toString().length()-1 > lastRightAddress ) {
                for (int i = lastRightAddress+1; i < rightLines.get(row).toString().length(); i++ ) {
                    rightLines.get(row).setState(1);
                    rightLines.get(row).getDiffCharSet().add(prevRightAddress + i);
                }

                int startAddress = prevRightAddress+lastRightAddress+1;
                int finishAddress = prevRightAddress+rightLines.get(row).toString().length()-1;

                //TODO: 왼쪽 부분은 가장 끝 인덱스 두개를 저장합니다. (TODO : 추가되었다는 의미)
                leftDiffBlocks.add(new DiffBlock(prevLeftAddress+leftLines.get(row).toString().length(),prevLeftAddress+leftLines.get(row).toString().length()));
                rightDiffBlocks.add(new DiffBlock(startAddress, finishAddress)); //TODO: 오른쪽 텍스트에서 차이가 나는 부분을 DiffBlock으로 저장.  (TODO : 추가되었다는 의미)
            }

            if ( leftLines.get(row).toString().length() == 0 && rightLines.get(row).toString().length() != 0 ) {
                if ( leftLines.get(row).getState() != -1 ) {
                    leftLines.get(row).setState(1);
                }
                if ( rightLines.get(row).getState() != -1 ) {
                    rightLines.get(row).setState(1);
                }
                for (int i = prevRightAddress; i < prevRightAddress + rightLines.get(row).toString().length(); i++ ) {
                    rightLines.get(row).getDiffCharSet().add(i);
                }
            } else if ( rightLines.get(row).toString().length() == 0 && leftLines.get(row).toString().length() != 0 ) {
                if ( leftLines.get(row).getState() != -1 ) {
                    leftLines.get(row).setState(1);
                }
                if ( rightLines.get(row).getState() != -1 ) {
                    rightLines.get(row).setState(1);
                }
                for (int i = prevLeftAddress; i < prevLeftAddress + leftLines.get(row).toString().length(); i++ ) {
                    leftLines.get(row).getDiffCharSet().add(i);
                }
            }

            // 가장 마지막 위치의 어드레스를 기준으로 다음 어드레스를 저장한다.
            // 이들을 저장하는 이유는, 색칠할 때 오프셋으로 적용시킬 것이기 때문이다.
            // 제이텍스트판에 사용될 도큐먼트의 오프셋이 첫줄부터 끝줄까지 연속되기 때문.
            prevLeftAddress += leftLines.get(row).toString().length() + 1;
            prevRightAddress += rightLines.get(row).toString().length() + 1;
        }

        // 다른 글자가 포함된 라인의 시작과 끝 인덱스를 ArrayList에 저장한다.
        /*
        int firstDiffLine = -1, lastDiffLine = -1;
        for (int row = 0; row < leftLines.size(); row++) {
            if (leftLines.get(row).getState() != 0 && firstDiffLine < 0) {
                firstDiffLine = row;
            }
            else if (leftLines.get(row).getState() != 0 && firstDiffLine >= 0) {
                lastDiffLine = row;
            }
            else if (leftLines.get(row).getState() == 0 && firstDiffLine >= 0) {
                System.out.println(firstDiffLine + "/" + lastDiffLine);
                diffBlocks.add(new DiffBlock(firstDiffLine, lastDiffLine));
                firstDiffLine = -1;
                lastDiffLine = -1;
            }
        }
        */
    }
}
