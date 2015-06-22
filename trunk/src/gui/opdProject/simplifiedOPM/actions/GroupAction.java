package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 12:28
 */
public class GroupAction extends AbstractAction {

    GroupUtils utils;

    public GroupAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        utils.groupSelection(utils.getView().getX(), utils.getView().getY());

    }
}
