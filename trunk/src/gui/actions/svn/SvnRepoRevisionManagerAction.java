package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import modelControl.OpcatMCManager;
import modelControl.gui.OpcatSvnGridReporter;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

public class SvnRepoRevisionManagerAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SVNURL url;
    private String entryName;

    public SvnRepoRevisionManagerAction(SVNURL url, String entryName,
                                        String name, Icon icon) {
        super(name, icon);
        this.url = url;
        this.entryName = entryName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.startWait();

        try {

            Collection<SVNLogEntry> logs = OpcatMCManager.getInstance().getVersions(
                    url);

            if (logs == null) {
                JOptionPane.showMessageDialog(Opcat2.getFrame(),
                        "No revisions", "Revisions", JOptionPane.YES_OPTION,
                        null);
                Opcat2.endWait();
                return;
            }

            Collection<String> data = new LinkedList<String>();
            data.add("Number;Name;Revision;Author;Date;Message");
            int i = 1;
            SVNLogEntry lll ;

            for (SVNLogEntry log : logs) {
                SVNProperties revisionProperties = log.getRevisionProperties();
                data.add(i + ";"
                        //+ OpcatMCManager.getInstance().getNodeKind(url) + ";"
                        + entryName + ";"
                        + log.getRevision() + ";"
                        + log.getAuthor() + ";" 
                        + log.getDate() + ";"
                        + revisionProperties.getStringValue("svn:log"));
                i++;
            }

            OpcatSvnGridReporter report = new OpcatSvnGridReporter(true, ";",
                    entryName + " Revisions");
            report.setData(data);
            report.show(true);

            GuiControl.getInstance().getRepository().getModelsView()
                    .repaintKeepOpen();

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

        Opcat2.endWait();
    }
}
