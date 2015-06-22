package gui.actions.svn;

import database.OpcatDatabaseConnection;
import expose.OpcatExposeError;
import expose.OpcatExposeList;
import gui.Opcat2;
import gui.actions.expose.OpcatExposeChange;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.images.svn.SvnImages;
import gui.opdProject.OpdProject;
import gui.opx.Loader;
import gui.opx.Saver;
import gui.util.OpcatFile;
import modelControl.OpcatMCManager;
import modelControl.gui.OpcatSvnCommitDialog;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import util.FileUtils;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class SvnCommitAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatFile root;

    public SvnCommitAction(OpcatFile file, String name, Icon icon) {
        super(name, icon);
        this.root = file;
    }


    class MyRuunable implements Runnable {

        boolean failed = false;
        String retMessage = "";
        boolean unLockAfter;
        String commitMessage;
        boolean showMessages;

        public String getRetMessage() {
            return retMessage;
        }

        MyRuunable(String commitMessage, boolean showMessages, boolean unLockAfter) {
            this.commitMessage = commitMessage;
            this.showMessages = showMessages;
            this.unLockAfter = unLockAfter;
        }

        @Override
        public void run() {
            try {
                retMessage = "";

                if ((FileControl.getInstance().getCurrentProject() != null)
                        && !(FileControl.getInstance().getCurrentProject()
                        .isCanClose())) {

                    if (showMessages)
                        retMessage =
                                "Please save the project before committing, Commit cancelled";
                    failed = true;
                    Opcat2.endWait();
                    return;
                }

                OpcatDatabaseConnection.getInstance();
                if (!OpcatDatabaseConnection.isOnLine()) {
                    retMessage = "Could not connect to database ";
                    failed = true;
                    Opcat2.endWait();
                    return;
                }

                if (!OpcatMCManager.getInstance().forceConnection()) {
                    retMessage = "Could not connect to MC";
                    failed = true;
                    Opcat2.endWait();
                    return;
                }


                String[] ignoreList = {".svn"};
                ArrayList<File> rawFiles = FileUtils.getFileListFlat(root, ignoreList);
                ArrayList<File> approvedForCommit = new ArrayList<File>();

                for (File raw : rawFiles) {
                    SVNStatus status = null;
                    try {
                        status = OpcatMCManager.getInstance().getStatus(raw,
                                false);
                    } catch (Exception ex) {

                    }
                    if (status != null) {
                        if (status.getContentsStatus() != SVNStatusType.STATUS_NORMAL) {
                            approvedForCommit.add(raw);
                        }
                    } else {
                        try {
                            if (OpcatMCManager.getInstance().doAdd(raw)) {
                                approvedForCommit.add(raw);
                            }
                        } catch (Exception ex) {

                        }
                    }

                }

                if (approvedForCommit.size() == 0) {
                    failed = false;
                    retMessage = "Noting to Commit";
                    Opcat2.endWait();
                    return;
                }

                for (File file : approvedForCommit) {

                    if (!(file.isFile())) {
                        retMessage = "Error : " + file.getName() + " is not a file ;" + retMessage;
                        continue;
                    }

                    //OpcatLogger.info("  Starting commit project '" + file.getName() + "'", false);
                    ArrayList<OpcatExposeChange> origChanges = new ArrayList<OpcatExposeChange>();
                    boolean autoCommit = true;

                    try {

                        OpcatDatabaseConnection.getInstance()
                                .getConnection().setAutoCommit(false);
                        autoCommit = false;

                        if (!(file.getPath().endsWith(".opx")) && !(file.getPath().endsWith(".opz"))) {
                            OpcatMCManager
                                    .getInstance().doCommit(
                                    new OpcatFile(file.getPath(), true),
                                    commitMessage,
                                    unLockAfter, false);
                            continue;
                        }

                        OpdProject project = null;
                        boolean onCurrent = false;
                        if ((FileControl.getInstance()
                                .getCurrentProject() != null) && (FileControl.getInstance()
                                .getCurrentProject().isCanClose()) && file.getPath().equalsIgnoreCase(FileControl.getInstance()
                                .getCurrentProject().getPath())) {

                            onCurrent = true;
                            project = Opcat2.getCurrentProject();

                        } else {
                            //OpcatLogger.info("      Start Loading '" + file.getName() + "'", false);
                            InputStream is = null;
                            Loader ld = new gui.opx.Loader(file.getPath());
                            if (file.getName().endsWith(".opz")) {
                                is = new GZIPInputStream(
                                        new FileInputStream(file), 4096);
                            } else {
                                is = new BufferedInputStream(
                                        new FileInputStream(file), 4096);
                            }

                            project = ld.load(new JDesktopPane(), is, null, false);
                            //OpcatLogger.info("      End Loading '" + file.getName() + "'", false);
                        }

                        if (project == null) {
                            String message = "File " + file.getName()
                                    + " : Error loading file  \n";
                            message = message
                                    + "Continue committing other files ?";

                            int ret = JOptionPane.NO_OPTION;
                            if (showMessages) {
                                ret = JOptionPane.showOptionDialog(
                                        Opcat2.getFrame(), message,
                                        "OPCAT II",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null, null, null);
                            }
                            if (ret != JOptionPane.YES_OPTION) {
                                cleanupFail();
                                retMessage = message;
                                Opcat2.endWait();
                                return;
                            } else {
                                continue;
                            }
                        }

                        ArrayList<OpcatExposeError> results = new ArrayList<OpcatExposeError>();

                        //OpcatLogger.info("      Start Commit Exposed Things '" + file.getName() + "'", false);
                        origChanges = project.getExposeManager()
                                .getLatestExposedChangeMap();

                        results = new ArrayList<OpcatExposeError>();
                        results = project.getExposeManager()
                                .commitExpose();
                        //OpcatLogger.info("      End Commit Exposed Things '" + file.getName() + "'", false);
                        if (results.size() == 0) {
                            boolean usageOK = false;
                            //OpcatLogger.info("      Start Commit Expose Usage '" + file.getName() + "'", false);

                            usageOK = project.getExposeManager()
                                    .commitUsage();

                            //OpcatLogger.info("      End Commit Expose Usage '" + file.getName() + "'", false);

                            if (usageOK) {
                                project.getExposeManager()
                                        .clearExposeLocalChanges();

                                if (FileControl.getInstance()
                                        .getCurrentProject() != null) {
                                    if (FileControl
                                            .getInstance()
                                            .getCurrentProject()
                                            .getGlobalID()
                                            .equalsIgnoreCase(
                                                    project
                                                            .getGlobalID())) {
                                        FileControl
                                                .getInstance()
                                                .getCurrentProject()
                                                .getExposeManager()
                                                .clearExposeLocalChanges(
                                                        project);
                                    }
                                }

                                if (file.exists()) {
                                    //OpcatLogger.info("      Start Save before Commit to MC '" + file.getName() + "'", false);
                                    Saver save = new gui.opx.Saver();
                                    save.save(project, null, null);
                                    //OpcatLogger.info("      End Save before Commit to MC '" + file.getName() + "'", false);
                                }


                                //OpcatLogger.info("      Start Commit to MC '" + file.getName() + "'", false);
                                boolean commitMCOK = OpcatMCManager
                                        .getInstance().doCommit(
                                                new OpcatFile(project
                                                        .getPath(),
                                                        true),
                                                commitMessage,
                                                unLockAfter, false);

                                //OpcatLogger.info("      End Commit to MC '" + file.getName() + "'", false);

                                if (commitMCOK) {
                                    OpcatDatabaseConnection
                                            .getInstance()
                                            .getConnection().commit();

                                } else {
                                    rollback(autoCommit, file, project, origChanges);

                                    String message = "File "
                                            + file.getName()
                                            + " failed while committing to MC: \n\n";
                                    message = message
                                            + "Continue committing other files ?";

                                    int ret = JOptionPane.NO_OPTION;
                                    if (showMessages) {
                                        ret = JOptionPane
                                                .showOptionDialog(
                                                        Opcat2
                                                                .getFrame(),
                                                        message,
                                                        "OPCAT II",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.WARNING_MESSAGE,
                                                        null, null,
                                                        null);
                                    }
                                    if (ret != JOptionPane.YES_OPTION) {
                                        cleanupFail();
                                        retMessage = message;
                                        Opcat2.endWait();
                                        return;
                                    }
                                }
                            } else {
                                rollback(autoCommit, file, project, origChanges);

                                String message = "File "
                                        + file.getName()
                                        + " failed while committing expose usage: \n\n";
                                message = message
                                        + "Continue committing other files ?";

                                int ret = JOptionPane.NO_OPTION;
                                if (showMessages) {
                                    ret = JOptionPane
                                            .showOptionDialog(
                                                    Opcat2.getFrame(),
                                                    message,
                                                    "OPCAT II",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.WARNING_MESSAGE,
                                                    null, null, null);
                                }
                                if (ret != JOptionPane.YES_OPTION) {
                                    cleanupFail();
                                    retMessage = message;
                                    Opcat2.endWait();
                                    return;
                                }
                            }

                            //JOptionPane.showMessageDialog(Opcat2
                            //        .getFrame(), "Finished commiting Files, see log grids for details", "OPCAT II",
                            //        JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            rollback(autoCommit, file, project, origChanges);

                            String message = "File "
                                    + file.getName()
                                    + " failed while committing expose :\n\n";
                            for (OpcatExposeError entry : results) {

                                message = message
                                        + entry.getErrorCouserName()
                                        + ": "
                                        + entry.getErrorMessage()
                                        + "\n";

                            }
                            message = message
                                    + "\nContinue committing other files ?";
                            int ret = JOptionPane.NO_OPTION;
                            if (showMessages) {
                                ret = JOptionPane.showOptionDialog(
                                        Opcat2.getFrame(), message,
                                        "OPCAT II",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null, null, null);
                            }
                            if (ret != JOptionPane.YES_OPTION) {
                                cleanupFail();
                                retMessage = message;
                                Opcat2.endWait();
                                return;
                            }
                        }
                    } catch (Exception ex) {
                        OpcatLogger.error(ex, false);
                        if (!autoCommit)
                            try {
                                OpcatDatabaseConnection.getInstance()
                                        .getConnection().rollback();
                            } catch (SQLException e) {
                            }
                        String message = "Failed committing on unknown error.\n";
                        message = message
                                + "\nContinue committing other files ?";

                        int ret = JOptionPane.NO_OPTION;
                        if (showMessages) {
                            ret = JOptionPane.showOptionDialog(Opcat2
                                    .getFrame(), message, "OPCAT II",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null,
                                    null, null);
                        }
                        if (ret != JOptionPane.YES_OPTION) {
                            cleanupFail();
                            retMessage = message;
                            Opcat2.endWait();
                            return;
                        }
                    }
                    //OpcatLogger.info("  Finished commit project '" + file.getName() + "'", false);
                }
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            } finally {
                Opcat2.endWait();

            }
        }
    }


    public String commit(final boolean unLockAfter, final String commitMessage,
                         final boolean showMessages) {

        Opcat2.startWait();
        String retMessage = "";

        MyRuunable my = new MyRuunable(commitMessage, showMessages, unLockAfter);
        SwingUtilities.invokeLater(my);
        return my.getRetMessage();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        super.actionPerformed(e);

        try {
            OpcatSvnCommitDialog commitDialog = new OpcatSvnCommitDialog(Opcat2
                    .getFrame());
            commitDialog.setIconImage(SvnImages.ACTION_COMMIT.getImage());
            commitDialog.setLocationRelativeTo(Opcat2.getFrame());
            commitDialog.setModal(true);
            commitDialog.setVisible(true);

            String msg = commitDialog.getCommitMessage();

            if (!(commitDialog.isCancled())) {
                String re = commit(commitDialog.isUnlockAfterCommit(), msg,
                        true);
                if (re != null) {
                    OpcatLogger.error(re, false);
                    OpcatLogger.info("Errors where detected during commit, see opcat-log for detailed information", false);
                }
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }

    private void cleanupFail() {
        try {

            try {
                if (FileControl.getInstance().getCurrentProject() != null) {
                    OpcatExposeList.getInstance().refresh(
                            FileControl.getInstance().getCurrentProject()
                                    .getExposeManager().getExposedList(null,
                                    true));
                }
            } catch (Exception ex) {

            }

            try {
                OpcatDatabaseConnection.getInstance().getConnection()
                        .rollback();
            } catch (SQLException e) {
                // OpcatLogger.logError(e);
            }
            try {
                OpcatDatabaseConnection.getInstance().getConnection()
                        .setAutoCommit(true);
            } catch (SQLException e) {
                // OpcatLogger.logError(e);

            }
            GuiControl.getInstance().getRepository().getModelsView()
                    .repaintKeepOpen();
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

    }

    private void rollback(boolean autoCommit, File file, OpdProject project,
                          ArrayList<OpcatExposeChange> origChanges) throws SQLException {

        if (!autoCommit) {
            OpcatDatabaseConnection.getInstance().getConnection().rollback();
        }

        project.getExposeManager().setLatestExposedChangeMap(origChanges);

        if (file.exists()) {
            Saver save = new gui.opx.Saver();
            save.save(project, null, null);
        }

        if (FileControl.getInstance().getCurrentProject() != null) {
            if (FileControl.getInstance().getCurrentProject().getGlobalID()
                    .equalsIgnoreCase(project.getGlobalID())) {
                FileControl.getInstance().getCurrentProject()
                        .getExposeManager().setLatestExposedChangeMap(
                        origChanges);
                // FileControl.getInstance().getCurrentProject()
                // .getExposeManager().clearExposeLocalChanges();
            }
        }

    }
}
