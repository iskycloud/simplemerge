/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import Model.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrollTextPane extends JScrollPane {
    private final static Color COLOR_NORMAL = Color.WHITE;
    private final static Color COLOR_DIFF_STRING = Color.PINK;
    private final static Color COLOR_DIFF_LINE = Color.YELLOW;
    private final static Color COLOR_DIFF_FAKE_LINE = Color.GRAY;

    private JTextPane txtPane;
    private int lineHeight;

    ScrollTextPane() {
        initialize();
    }

    private void initialize() {
        txtPane = new JTextPane();
        super.setViewportView(txtPane);

        lineHeight = txtPane.getFontMetrics(txtPane.getFont()).getHeight();
    }

    public JTextPane getJTextPane() {
        return this.txtPane;
    }

    // 모든 색을 제거해줌 = 초기화
    public void clearColor() {
        StyledDocument ldoc = txtPane.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_NORMAL);
        ldoc.setCharacterAttributes(0, txtPane.getText().length(), attrs, true);
    }

    public void printCompare(ArrayList<Line> lines) {
        clearColor();

        // 텍스트판에 적용시킬 스타일. 컬러 적용에 사용됨.
        StyledDocument sDoc = txtPane.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        int prevLeftAddress = 0, prevRightAddress = 0;
        AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_DIFF_STRING);

        for (int row = 0; row < lines.size(); row++) {
            if (lines.get(row).getState() == 1) {
                // 줄 색칠
                // 구현 필요
                // 글자 색칠
                for (int i = 0; i < lines.get(row).getDiffCharSet().size(); i++) {
                    sDoc.setCharacterAttributes(lines.get(row).getDiffCharSet().get(i), 1, attrs, true);
                }
            }
        }
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