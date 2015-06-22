package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 12:30
 */
public class DownAction extends AbstractAction {

    GroupUtils utils;

    public DownAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (utils.getView().getGraph2D().selectedNodes() != null)
                if (utils.getView().getGraph2D().selectedNodes().ok()) {
                    utils.down(utils.getView().getGraph2D().selectedNodes().node());
                    utils.getView().getGraph2D().fitGraph2DView();
                    utils.getView().doGraphLayout();
                    utils.getView().getGraph2D().updateViews();
                }
        } catch (Exception ex) {

        }
    }
}
