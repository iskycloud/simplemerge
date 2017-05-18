/**
 * Created by iskyc on 2017-05-19.
 */

package View;

import javax.swing.*;

public class ScrollTextPane extends JScrollPane {
    private JTextPane txtPane = new JTextPane();

    ScrollTextPane() {
        initialize();
    }

    private void initialize() {
        super.setViewportView(txtPane);
    }

    public JTextPane getJTextPane() {
        return this.txtPane;
    }
}
