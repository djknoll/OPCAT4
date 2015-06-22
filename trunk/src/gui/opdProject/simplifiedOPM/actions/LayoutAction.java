package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 10/07/11
 * Time: 13:27
 */
public class LayoutAction extends AbstractAction {

    OpdSimplifiedView view;

    public LayoutAction(OpdSimplifiedView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.doGraphLayout();
        view.repaint();
    }
}
