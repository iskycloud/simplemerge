package View;

import Model.FileModel;
import javax.swing.*;
import java.awt.*;

public class LocationPane extends JComponent {
    private int widthValue = 56;
    private FileModel leftModel, rightModel;


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

        if ( leftModel != null && rightModel != null ) {
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
        }
    }
}
