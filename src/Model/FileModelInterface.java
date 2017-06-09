package Model;

import View.View;
import javax.swing.*;
import java.util.ArrayList;

public interface FileModelInterface {

    public void setFilePath(String filePath);

    public void loadLines();

    public void setLines(JTextPane jp);

    public boolean isFileLoaded();

    public void saveLines();

    public String getFilePath();

    public ArrayList<Line> getLines();

    public int setFileContent(View v);
}
