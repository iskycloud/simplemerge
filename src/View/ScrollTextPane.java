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
    private final static Color COLOR_DIFF_STRING = Color.ORANGE;
    private final static Color COLOR_DIFF_LINE = Color.PINK;
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
        this.repaint();
    }

    // DiffBlock의 인덱스 어레이리스트를 가져오고, 인덱스 객체들을 이용하여 색 표현.
    public void printCompare(ArrayList<Line> lines) {
        this.repaint();
        Graphics g = this.getGraphics();
        /*
        g.setColor(Color.WHITE);
        g.fillRect(1, 2, getWidth()-2, getHeight()-2);
        */

        int h = 0;
        for(int i = 0; i < lines.size(); i++) {
            if ( lines.get(i).getState() == 1 ) {
                g.setColor(ScrollTextPane.COLOR_DIFF_LINE);
                g.fillRect(1,2+ lineHeight * i, getWidth()-2, lineHeight);
            } else if ( lines.get(i).getState() == -1 ) {
                g.setColor(ScrollTextPane.COLOR_DIFF_FAKE_LINE);
                g.fillRect(1,2+ lineHeight * i, getWidth()-2, lineHeight);
            } else {
                // do nothing.
            }

            for(int x = 0; x < lines.get(i).getDiffCharSet().size(); x++) {
                int diffPos = lines.get(i).getDiffCharSet().get(x) - h;
                int startPos = txtPane.getFontMetrics(txtPane.getFont()).charsWidth(lines.get(i).toString().toCharArray(), 0, diffPos);
                int width = txtPane.getFontMetrics(txtPane.getFont()).charWidth(startPos);
                g.setColor(ScrollTextPane.COLOR_DIFF_STRING);
                g.fillRect(startPos,2+ i * lineHeight , width, lineHeight);
            }

            h += lines.get(i).toString().length() + 1;
        }

        //txtPane.setText("");
        //for(int i = 0; i < lines.size(); i++) {
        //    txtPane.setText(txtPane.getText() + lines.get(i) + '\n');
        //}
        /*




        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        StyledDocument sDoc = txtPane.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attrs;
        int address = 0;

        // 텍스트판에 적용시킬 스타일. 컬러 적용에 사용됨.
        for(int i = 0; i < lines.size(); i++) {
            if ( lines.get(i).getState() == 1 ) {
                attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_DIFF_LINE);
                sDoc.setCharacterAttributes(address, lines.get(i).toString().length(), attrs, true);
            } else if ( lines.get(i).getState() == -1 ) {
                attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_DIFF_FAKE_LINE);
                sDoc.setCharacterAttributes(address, lines.get(i).toString().length(), attrs, true);
            }
            address += lines.get(i).toString().length() + 1;
        }

        attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, COLOR_DIFF_STRING);
        // 인덱스 객체들을 하나씩 불러와 시작 인덱스로부터 끝 인덱스까지 색칠.
        for(int i = 0; i < indexes.size(); i++) {
            sDoc.setCharacterAttributes(indexes.get(i).First(), indexes.get(i).Last() - indexes.get(i).First() + 1, attrs, true);
        }
        */
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