package View;

import Model.FileModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xpathz on 2017. 5. 28..
 */
public class LocationPane extends JComponent {
    private int widthValue = 56;
    private FileModel leftModel, rightModel;
    // 의도하고 있는건 비교할때마다 setState해둔거 이용할거임!


    public LocationPane() {
        this.setPreferredSize(new Dimension(this.widthValue, getHeight()));
        this.repaint();
    }

    public void setModel(FileModel left, FileModel right) {
        this.leftModel = left;
        this.rightModel = right;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(2,2,getWidth()-4, getHeight()-4);

        g.setColor(Color.BLACK);
        g.fillRect(27, 2, 2, getHeight());

        // 고려하지 않은 사항 : 페이크라인
        // 라인 개수에 따라 높이 오차가 발생할 수도 있음. -> 오차는 대부분 마지막에서 표시가 되지만
        // 이 오차를 또 칠해버리면 되지 않을까 생각해봅
            if ( leftModel.isFileLoaded() && leftModel.getLines().size() > 0 ) {
                int eachHeight = (this.getHeight() - 4) / leftModel.getLines().size();
                for(int i = 0; i < leftModel.getLines().size(); i++) {
                    if ( leftModel.getLines().get(i).getState() == 1 ) {
                        g.setColor(Color.PINK);
                        g.fillRect(2, 2 + eachHeight * i, 25, eachHeight);
                    } else if ( leftModel.getLines().get(i).getState() == -1 ) {
                        g.setColor(Color.GRAY);
                        g.fillRect(2, 2 + eachHeight * i, 25, eachHeight);
                    }
                }
            }
            if (rightModel.isFileLoaded()  && rightModel.getLines().size() > 0) {
                int eachHeight = (this.getHeight() - 4) / rightModel.getLines().size();
                for (int i = 0; i < rightModel.getLines().size(); i++) {
                    if ( rightModel.getLines().get(i).getState() == 1 ) {
                        g.setColor(Color.PINK);
                        g.fillRect(29, 2 + eachHeight * i, 25, eachHeight);
                    } else if ( rightModel.getLines().get(i).getState() == -1 ) {
                        g.setColor(Color.GRAY);
                        g.fillRect(29, 2 + eachHeight * i, 25, eachHeight);
                    }
                }
            }
        //}
    }
}
