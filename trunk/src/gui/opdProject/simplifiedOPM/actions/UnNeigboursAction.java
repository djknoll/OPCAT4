package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 12/07/11
 * Time: 15:24
 */
public class UnNeigboursAction extends AbstractAction {

    GroupUtils utils;


    public UnNeigboursAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        utils.up();
        utils.ungroupSelection();
        utils.getView().doGraphLayout();
        utils.getView().getGraph2D().updateViews();
    }
}
