package gui.actions.edit;

import gui.controls.FileControl;
import gui.util.BrowserLauncher2;
import org.w3c.dom.events.EventException;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class DataFormAction extends EditAction {

    private static final long serialVersionUID = 1L;

    /**
     *
     */

    public DataFormAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(ActionEvent arg0) {
        try {
            super.actionPerformed(arg0);
        } catch (EventException e) {
            JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
                    .toString(), "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileControl file = FileControl.getInstance();
            URL url = new URL("file", "", file.getOPCATDirectory() + FileControl.fileSeparator + "Dataform.xlt");
            BrowserLauncher2.openURL(url.toExternalForm());
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

    }

}
