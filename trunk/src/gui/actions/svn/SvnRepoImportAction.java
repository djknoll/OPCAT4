package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import user.OpcatUser;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SvnRepoImportAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SVNURL entry;

    public SvnRepoImportAction(SVNURL entry, String name, Icon icon) {
        super(name, icon);
        this.entry = entry;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.startWait();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFileChooser myFileChooser = new JFileChooser();
                    myFileChooser.setSelectedFile(new File(""));
                    myFileChooser.resetChoosableFileFilters();
                    myFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int retVal = myFileChooser.showOpenDialog(Opcat2.getFrame());
                    if (retVal == JFileChooser.APPROVE_OPTION) {
                        OpcatMCManager.getInstance().doImport(
                                myFileChooser.getSelectedFile(),
                                entry,
                                "Importing from Opcat user - "
                                        + OpcatUser.getCurrentUser().getName());
                        GuiControl.getInstance().getRepository().getModelsView()
                                .repaintKeepOpen();
                    }
                   JOptionPane.showConfirmDialog(Opcat2.getFrame(), "Import Finished") ;
                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                    JOptionPane.showConfirmDialog(Opcat2.getFrame(), "Import Finished with errors") ;
                }

                Opcat2.endWait();
            }
        });


    }
}
