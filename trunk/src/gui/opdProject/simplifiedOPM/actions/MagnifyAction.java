package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import y.view.MagnifierViewMode;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 14:10
 */
public class MagnifyAction extends AbstractAction {
    OpdSimplifiedView view;
    boolean on = false;
    MagnifierViewMode magnifierMode = new MagnifierViewMode();

    public MagnifyAction(OpdSimplifiedView view) {
        this.view = view;

        magnifierMode.setMagnifierRadius(100);
        magnifierMode.setMagnifierZoomFactor(2.0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        on = !on;

        if (on) {
            view.addViewMode(magnifierMode);
        } else {
            view.removeViewMode(magnifierMode);
        }
        view.repaint();

    }
}
