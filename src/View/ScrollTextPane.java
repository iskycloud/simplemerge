/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import Model.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrollTextPane extends JScrollPane implements CaretListener {
    private final static Color COLOR_NORMAL = new Color(255, 255, 255, 128);       // WHITE
    private final static Color COLOR_DIFF_STRING = new Color(255, 200, 0, 128);	// ORANGE
    private final static Color COLOR_DIFF_LINE = new Color(255, 175, 175, 128);	// PINK
    private final static Color COLOR_DIFF_FAKE_LINE = new Color(128, 128, 128, 128);	// GRAY

    private JTextPane txtPane;
    private int dotPosition;
    private int lineHeight;
    private ArrayList<Line> lines;

    ScrollTextPane() {
        initialize();
    }

    private void initialize() {
        txtPane = new JTextPane();
        super.setViewportView(txtPane);
        lineHeight = txtPane.getFontMetrics(txtPane.getFont()).getHeight();
        txtPane.addCaretListener(this);
    }

    public JTextPane getJTextPane() {
        return this.txtPane;
    }

    //TODO: 텍스트 칠한 것 초기화 (TODO : 추가되었다는 의미)
    // 모든 색을 제거해줌 = 초기화
    public void clearColor() {
        StyledDocument sDoc = txtPane.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_NORMAL);
        sDoc.setCharacterAttributes(0, txtPane.getText().length(), attrs, true);
    }

    //TODO: 잘 살펴보셈  (TODO : 추가되었다는 의미)
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        super.paintBorder(g);

        if ( lines != null )  printMark(g);

    }

    public int getDotPosition() { return this.dotPosition; }

    public void caretUpdate(CaretEvent e) {
        dotPosition = e.getDot();
        this.repaint();
    }

    // DiffBlock의 인덱스 어레이리스트를 가져오고, 인덱스 객체들을 이용하여 색 표현.
    public void printCompare(ArrayList<Line> lines) {
        this.lines = lines;
        this.repaint();
    }

    public void printMark(Graphics g) {
        this.clearColor(); //TODO : 색칠하기 전 텍스트 색 초기화

        StyledDocument sDoc = txtPane.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_DIFF_STRING);

        for(int i = 0; i < lines.size(); i++) {
            if ( lines.get(i).getState() == 1 ) {
                for (int x = 0; x < lines.get(i).getDiffCharSet().size(); x++) {
                    sDoc.setCharacterAttributes(lines.get(i).getDiffCharSet().get(x), 1, attrs, true);
                }

                g.setColor(ScrollTextPane.COLOR_DIFF_LINE);
                g.fillRect(1,2+ lineHeight * i, getWidth()-2, lineHeight);
            } else if ( lines.get(i).getState() == -1 ) {
                g.setColor(ScrollTextPane.COLOR_DIFF_FAKE_LINE);
                g.fillRect(1,2+ lineHeight * i, getWidth()-2, lineHeight);
            }
        }

        this.repaint();
    }
}
/*
class LineHighlightTextPaneUI extends BasicTextPaneUI {

    JTextPane tc;

    LineHighlightTextPaneUI (JTextPane t) {

        tc = t;
        tc.addCaretListener (new CaretListener() {
            public void caretUpdate (CaretEvent e) {

                tc.repaint ();
            }
        });
    }

    @Override
    public void paintBackground (Graphics g) {

        super.paintBackground (g);

        try {
            Rectangle rect = modelToView(tc, tc.getCaretPosition ());
            int y = rect.y;
            int h = rect.height;
            g.setColor (Color.YELLOW);
            g.fillRect (0, y, tc.getWidth (), h);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
*/