package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 12:23
 */
public class UpAction extends AbstractAction {

    GroupUtils utils;

    public UpAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        utils.up();
        utils.getView().doGraphLayout();
        utils.getView().getGraph2D().updateViews();
    }
}
