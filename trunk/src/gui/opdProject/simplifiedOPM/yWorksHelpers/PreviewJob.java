package gui.opdProject.simplifiedOPM.yWorksHelpers;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 13/06/11
 * Time: 15:28
 */
public class PreviewJob extends AbstractAction {
    OpdSimplifiedView view;

    public PreviewJob(OpdSimplifiedView view) {
        this.view = view;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrintPreview preview = new gui.opdProject.simplifiedOPM.yWorksHelpers.PrintPreview(view);
                preview.actionPerformed(null);
            }
        });

        //To change body of implemented methods use File | Settings | File Templates.
    }
}
