package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 18/07/11
 * Time: 19:28
 */
public abstract  class  ASimplifiedOPDAction extends AbstractAction {
    GroupUtils utils;
    OpdSimplifiedView view;

    protected ASimplifiedOPDAction(OpdSimplifiedView view) {
        super();
        this.utils = view.getGroupUtils();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (view.getAllPathAction().active) {
            view.getAllPathAction().actionPerformed(null);
        }

        if (view.getPathAction().active) {
            view.getPathAction().actionPerformed(null);
        }
    }
}
