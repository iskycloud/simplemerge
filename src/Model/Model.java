package Model;

import View.View;
import java.util.ArrayList;

public class Model {

    // 상수 : 주로 패널의 왼쪽 오른쪽 구분을 위해 사용될 것임.
    public final static String LEFT = "Left";
    public final static String RIGHT = "Right";

    public FileModel leftFileModel, rightFileModel;
    private ArrayList<DiffBlock> leftDiffBlocks = new ArrayList<DiffBlock>(); // 왼쪽 파일 모델 (혹은 파일 내용)을 비교한 결과를 저장함.
    private ArrayList<DiffBlock> rightDiffBlocks = new ArrayList<DiffBlock>(); // 오른쪽 파일 모델 (혹은 파일 내용)을 비교한 결과를 저장함.

    public Model() {
        leftFileModel = new FileModel(LEFT);
        rightFileModel = new FileModel(RIGHT);
    }

    public FileModel getFileModel(String s) {
        if (s.equals(LEFT)) {
            return leftFileModel;
        }
        else if (s.equals(RIGHT)) {
            return rightFileModel;
        }
        return null;
    }

    public int setFileContent(View v, String s) {
        int returnVal = -1;
        if (s.equals(LEFT)) {
            returnVal = leftFileModel.setFileContent(v);
        }
        else if (s.equals(RIGHT)) {
            returnVal = rightFileModel.setFileContent(v);
        }
        return returnVal;
    }

    public void compareLines() {
        // 둘다 파일 경로가 올바른지?
        if ( leftFileModel.isFileLoaded() && rightFileModel.isFileLoaded() ) {
            if ( leftFileModel.getLines().size() != rightFileModel.getLines().size() ) {
                CalcDiff.addFakeLine(leftFileModel.getLines(), rightFileModel.getLines());
            }
            CalcDiff.compareLines(leftFileModel.getLines(), rightFileModel.getLines(), leftDiffBlocks, rightDiffBlocks);
        }
    }

    public void mergeAll(String dest) {
        if ( dest.equals(Model.LEFT) ) {
            for(int x = leftDiffBlocks.size()-1; x >= 0; x--) {
                int i = leftDiffBlocks.get(x).getFirst();
                if ( leftFileModel.getLines().get(i).getState() == -1 ) {
                    leftFileModel.getLines().remove(i);
                    if ( i > leftFileModel.getLines().size() ) {
                        if ( rightFileModel.getLines().get(i).toString().length() != 0 ) {
                            leftFileModel.getLines().add(new Line(rightFileModel.getLines().get(i).toString(), 0));
                        } else {
                            leftFileModel.getLines().add(new Line("", 0));
                        }
                    } else {
                        if ( rightFileModel.getLines().get(i).toString().length() != 0 ) {
                            leftFileModel.getLines().add(i, new Line(rightFileModel.getLines().get(i).toString(), 0));
                        } else {
                            leftFileModel.getLines().add(i, new Line("", 0));
                        }
                    }
                    leftFileModel.getLines().get(i).setState(0);
                    rightFileModel.getLines().get(i).setState(0);
                } else if ( rightFileModel.getLines().get(i).getState() == -1 ) {
                    leftFileModel.getLines().remove(i);
                    rightFileModel.getLines().remove(i);
                } else {
                    leftFileModel.getLines().get(i).setString(rightFileModel.getLines().get(i).toString());
                    leftFileModel.getLines().get(i).setState(0);
                    rightFileModel.getLines().get(i).setState(0);
                }
            }
        } else if ( dest.equals(Model.RIGHT) ) {
            for(int x = rightDiffBlocks.size()-1; x >= 0; x--) {
                int i = leftDiffBlocks.get(x).getFirst();
                if ( rightFileModel.getLines().get(i).getState() == -1 ) {
                    rightFileModel.getLines().remove(i);
                    if ( i > rightFileModel.getLines().size() ) {
                        if ( leftFileModel.getLines().get(i).toString().length() != 0 ) {
                            rightFileModel.getLines().add(new Line(leftFileModel.getLines().get(i).toString(), 0));
                        } else {
                            rightFileModel.getLines().add(new Line("", 0));
                        }
                    } else {
                        if ( leftFileModel.getLines().get(i).toString().length() != 0 ) {
                            rightFileModel.getLines().add(i, new Line(leftFileModel.getLines().get(i).toString(), 0));
                        } else {
                            rightFileModel.getLines().add(i, new Line("", 0));
                        }
                    }
                    leftFileModel.getLines().get(i).setState(0);
                    rightFileModel.getLines().get(i).setState(0);
                } else if ( leftFileModel.getLines().get(i).getState() == -1 ) {
                    leftFileModel.getLines().remove(i);
                    rightFileModel.getLines().remove(i);
                } else {
                    rightFileModel.getLines().get(i).setString(leftFileModel.getLines().get(i).toString());
                    leftFileModel.getLines().get(i).setState(0);
                    rightFileModel.getLines().get(i).setState(0);
                }
            }
        }
    }


    public ArrayList<DiffBlock> getDiffBlocks(String orgs) {
        if ( orgs.equals(Model.LEFT) ) return leftDiffBlocks;
        else if ( orgs.equals(Model.RIGHT) ) return rightDiffBlocks;
        return null;
    }

    // 머지 실시
    public void mergeResult(String dest, int index) {
        if ( dest.equals(Model.RIGHT) ) {
            int row = -1;
            ArrayList<Integer> rows = new ArrayList<Integer>();
            for(int i = 0, address = 0; i < leftFileModel.getLines().size(); i++) {
                if ( address <= index && address + leftFileModel.getLines().get(i).toString().length() >= index ) {
                    for( int x = 0; x < rightDiffBlocks.size(); x++) {
                        if ( rightDiffBlocks.get(x).getFirst() == i ) {
                            row = i;
                            break;
                        }
                    }
                }
                address += (leftFileModel.getLines().get(i).toString().length() + 1);
            }

            for(int c = row-1; c >= 0 && leftFileModel.getLines().get(c).getState() != 0; c--) {
                rows.add(c);
            }

            rows.add(row);

            for(int c = row+1; c < leftFileModel.getLines().size() && leftFileModel.getLines().get(c).getState() != 0; c++) {
                rows.add(c);
            }

            for(int x = rows.size()-1; x >= 0; x--) {
                int pos = rows.get(x);
                if ( rightFileModel.getLines().get(pos).getState() == -1 ) {
                    rightFileModel.getLines().remove(pos);
                    if ( pos > rightFileModel.getLines().size() ) {
                        if ( leftFileModel.getLines().get(pos).toString().length() != 0 ) {
                            rightFileModel.getLines().add(new Line(leftFileModel.getLines().get(pos).toString(), 0));
                        } else {
                            rightFileModel.getLines().add(new Line("", 0));
                        }
                    } else {
                        if ( leftFileModel.getLines().get(pos).toString().length() != 0 ) {
                            rightFileModel.getLines().add(pos, new Line(leftFileModel.getLines().get(pos).toString(), 0));
                        } else {
                            rightFileModel.getLines().add(pos, new Line("", 0));
                        }
                    }
                    leftFileModel.getLines().get(pos).setState(0);
                    rightFileModel.getLines().get(pos).setState(0);
                } else if ( leftFileModel.getLines().get(pos).getState() == -1 ) {
                    leftFileModel.getLines().remove(pos);
                    rightFileModel.getLines().remove(pos);
                } else {
                    rightFileModel.getLines().get(pos).setString(leftFileModel.getLines().get(pos).toString());
                    leftFileModel.getLines().get(pos).setState(0);
                    rightFileModel.getLines().get(pos).setState(0);
                }
            }

        } else if ( dest.equals(Model.LEFT) ) {
            int row = -1;
            ArrayList<Integer> rows = new ArrayList<Integer>();
            for(int i = 0, address = 0; i < rightFileModel.getLines().size(); i++) {
                if ( address <= index && address + rightFileModel.getLines().get(i).toString().length() >= index ) {
                    for( int x = 0; x < leftDiffBlocks.size(); x++) {
                        if ( leftDiffBlocks.get(x).getFirst() == i ) {
                            row = i;
                            break;
                        }
                    }
                }
                address += (rightFileModel.getLines().get(i).toString().length() + 1);
            }

            for(int c = row-1; c >= 0 && rightFileModel.getLines().get(c).getState() != 0; c--) {
                rows.add(c);
            }

            rows.add(row);

            for(int c = row+1; c < rightFileModel.getLines().size() && rightFileModel.getLines().get(c).getState() != 0; c++) {
                rows.add(c);
            }

            for(int x = rows.size()-1; x >= 0; x--) {
                int pos = rows.get(x);
                if ( leftFileModel.getLines().get(pos).getState() == -1 ) {
                    leftFileModel.getLines().remove(pos);
                    if ( pos > leftFileModel.getLines().size() ) {
                        if ( rightFileModel.getLines().get(pos).toString().length() != 0 ) {
                            leftFileModel.getLines().add(new Line(rightFileModel.getLines().get(pos).toString(), 0));
                        } else {
                            leftFileModel.getLines().add(new Line("", 0));
                        }
                    } else {
                        if ( rightFileModel.getLines().get(pos).toString().length() != 0 ) {
                            leftFileModel.getLines().add(pos, new Line(rightFileModel.getLines().get(pos).toString(), 0));
                        } else {
                            leftFileModel.getLines().add(pos, new Line("", 0));
                        }
                    }
                    leftFileModel.getLines().get(pos).setState(0);
                    rightFileModel.getLines().get(pos).setState(0);
                } else if ( rightFileModel.getLines().get(pos).getState() == -1 ) {
                    leftFileModel.getLines().remove(pos);
                    rightFileModel.getLines().remove(pos);
                } else {
                    leftFileModel.getLines().get(pos).setString(rightFileModel.getLines().get(pos).toString());
                    leftFileModel.getLines().get(pos).setState(0);
                    rightFileModel.getLines().get(pos).setState(0);
                }
            }
        }
    }

}
