package gui.actions.svn;

import groups.GUI.addFileDialog;
import gui.Opcat2;
import gui.util.OpcatFile;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Dec 28, 2010
 * Time: 10:48:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class SVNAddToGroupAction extends SvnAction {

    private OpcatFile file;

    public SVNAddToGroupAction(OpcatFile file, String name, Icon icon) {
        super(name, icon);
        this.file = file;
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        addFileDialog dialog = new addFileDialog(file);
        dialog.setTitle("Adding " + file.getName() + " To Groups");

        int width = Opcat2.getFrame().getWidth() / 2;
        int height = Opcat2.getFrame().getHeight() / 2;

        int x = Opcat2.getFrame().getX() + width / 2;
        int y = Opcat2.getFrame().getY() + height / 2;

        dialog.setLocation(x, y);
        dialog.setSize(width, height);
        //dialog.pack();
        dialog.setVisible(true);
    }
}
