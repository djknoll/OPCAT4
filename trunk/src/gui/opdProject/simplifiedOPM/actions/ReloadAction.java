package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opx.ver3.LoaderVer3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by raanan.
 * Date: 12/09/11
 * Time: 18:12
 */
public class ReloadAction extends AbstractAction {

    OpdSimplifiedView view;


    public ReloadAction(OpdSimplifiedView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            LoaderVer3.loadExtraData(view.getOpd().getMyProject());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
