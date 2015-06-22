package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 12:51
 */
public class UnGroupAction extends AbstractAction {
    GroupUtils utils;

    public UnGroupAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        utils.ungroupSelection();
        utils.getView().doGraphLayout();
        utils.getView().getGraph2D().updateViews();
    }
}
