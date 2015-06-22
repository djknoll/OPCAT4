package gui.repository;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.images.svn.SvnImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmThing;
import gui.projectStructure.*;
import gui.scenarios.Scenario;
import gui.scenarios.Scenarios;
import gui.util.OpcatFile;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import user.OpcatUser;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class IconCellRenderer extends DefaultTreeCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */

    public IconCellRenderer() {
    }

    /**
     * Returns the color to use for the background if node is selected. LERA
     */
    public Color getBackgroundSelectionColor() {
        return this.backgroundNonSelectionColor;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();

        this.setToolTipText(obj.toString());

        if (obj instanceof Opd) {
            if (expanded) {
                this.setIcon(RepositoryImages.OPD_OPEN);
            } else {
                this.setIcon(RepositoryImages.OPD);
            }
            return this;
        }

        if (obj instanceof ObjectInstance) {
            ImageIcon icon = new ImageIcon(((ThingInstance) obj).getEntry().getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.OBJECT_MID;
            }
            this.setIcon(icon);
            return this;
        }

        if (obj instanceof OpmObject) {
            ImageIcon icon = new ImageIcon(((ThingEntry) Opcat2.getCurrentProject().getSystemStructure().getEntry(((OpmThing) obj).getId())).getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.OBJECT_MID;
            }
            this.setIcon(icon);

            return this;
        }

        if (obj instanceof ObjectEntry) {

            ImageIcon icon = new ImageIcon(((ThingEntry) obj).getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.OBJECT_MID;
            }
            this.setIcon(icon);
            return this;
        }

        if (obj instanceof ProcessInstance) {
            ImageIcon icon = new ImageIcon(((ThingInstance) obj).getEntry().getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.PROCESS_MID;
            }
            this.setIcon(icon);
            return this;
        }

        if (obj instanceof OpmProcess) {
            ImageIcon icon = new ImageIcon(((ThingEntry) Opcat2.getCurrentProject().getSystemStructure().getEntry(((OpmThing) obj).getId())).getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.PROCESS_MID;
            }
            this.setIcon(icon);
            return this;
        }

        if (obj instanceof ProcessEntry) {
            ImageIcon icon = new ImageIcon(((ThingEntry) obj).getIconPath());
            if (icon.getIconWidth() == -1) {
                icon = RepositoryImages.PROCESS_MID;
            }
            this.setIcon(icon);
            return this;
        }

        if (obj instanceof Scenario) {
            this.setIcon(OPCATeamImages.MODEL);
            return this;
        }

        if (obj instanceof Scenarios) {
            this.setIcon(OPCATeamImages.WORKGROUP);
            return this;
        }

        if (obj instanceof SVNURL) {
            this.setText(((SVNURL) obj).getHost());
            return this;
        }

        if ((obj instanceof OpcatFile)) {
            OpcatFile file = (OpcatFile) obj;

            if (file.isFile()) {
                this.setIcon(SvnImages.FILE_NORMAL);
            } else {
            }

            if (file.isWorkinCopyFile()) {
                try {

                    if (file.getPath().equalsIgnoreCase(
                            Configuration.getInstance().getProperty(
                                    "models_directory"))) {
                        return this;
                    }

                    boolean allowRemote = GuiControl.getInstance()
                            .getRepository().getModelsView()
                            .isAllowServerAccess();

                    OpcatMCManager svn;
                    svn = OpcatMCManager.getInstance(true);
                    SVNStatus status = null;

                    boolean checkStatus = Boolean.valueOf(Configuration.getInstance().getProperty("expose.check.files.status"));
                    if (checkStatus) {
                        status = svn.getStatus(file, allowRemote);
                    }


                    if (status != null) {
                        if (file.isFile()) {
                            getFileStatus(file, status, allowRemote);
                            return this;
                        } else {
                            getLocalDirectoryStatus(status);
                            return this;
                        }
                    }
                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                }

            }
            return this;
        }

        if (obj instanceof MetaLibrary) {
            if (((MetaLibrary) obj).isPolicy()) {
                this.setText("<html><b>" + ((MetaLibrary) obj).getName()
                        + "</b></html>");
                this.setIcon(StandardImages.Policies);
            } else {
                this.setText(((MetaLibrary) obj).getName());
                this.setIcon(StandardImages.Classification);
            }
            return this;
        }

        if ((obj instanceof OpdProject) && (tree instanceof BaseView)) {
            this.setIcon(((BaseView) tree).getIcon());
        }

        return this;
    }

    private void getFileStatus(OpcatFile file, SVNStatus status, boolean remote) {
        try {
            if (status.getContentsStatus() != SVNStatusType.STATUS_UNVERSIONED) {
                if (remote) {
                    OpcatMCManager svn = OpcatMCManager.getInstance(false);
                    SVNLock lock = svn.getRemoteLock(file);
                    if (lock != null) {
                        if ((lock.getOwner() != null)
                                && lock.getOwner().equalsIgnoreCase(
                                OpcatUser.getCurrentUser().getName())) {
                            this.setIcon(SvnImages.FILE_LOCKED_BYME);
                            return;
                        } else {
                            this.setIcon(SvnImages.FILE_LOCKED);
                            return;
                        }
                    }
                } else {
                    boolean locked = !file.canWrite();
                    if (locked) {
                        this.setIcon(SvnImages.FILE_LOCKED);
                        return;
                    }

                }
            }

            if (status != null) {
                if (status.getContentsStatus() == SVNStatusType.STATUS_UNVERSIONED) {
                    this.setIcon(SvnImages.FILE_NEW);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_ADDED) {
                    this.setIcon(SvnImages.FILE_ADDED);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_MODIFIED) {
                    this.setIcon(SvnImages.FILE_CHANGED);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_DELETED) {
                    this.setIcon(SvnImages.FILE_DELETED);
                    return;
                }
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

    }

    private void getLocalDirectoryStatus(SVNStatus status) {
        try {
            if (status != null) {
                if (status.getContentsStatus() == SVNStatusType.STATUS_UNVERSIONED) {
                    this.setIcon(SvnImages.FOLDER_NEW);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_ADDED) {
                    this.setIcon(SvnImages.FOLDER_ADDED);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_MODIFIED) {
                    this.setIcon(SvnImages.FOLDER_CHANGED);
                    return;
                }

                if (status.getContentsStatus() == SVNStatusType.STATUS_DELETED) {
                    this.setIcon(SvnImages.FOLDER_DELETED);
                    return;
                }
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }
}
