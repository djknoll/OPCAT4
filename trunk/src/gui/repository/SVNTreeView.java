package gui.repository;

import gui.Opcat2;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.util.OpcatFileNameFilter;
import modelControl.OpcatMCDirEntry;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNNodeKind;
import util.OpcatLogger;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SVNTreeView extends BaseView implements TreeWillExpandListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private SvnIconCellRenderer myCellRenderer;

    Comparator<DefaultMutableTreeNode> nodeComparator = new Comparator<DefaultMutableTreeNode>() {

        public int compare(DefaultMutableTreeNode o1, DefaultMutableTreeNode o2) {

            if (o1.getUserObject() instanceof OpcatMCDirEntry) {
                OpcatMCDirEntry entry1 = (OpcatMCDirEntry) o1.getUserObject();
                OpcatMCDirEntry entry2 = (OpcatMCDirEntry) o2.getUserObject();
                if ((entry1.getKind() == SVNNodeKind.DIR)
                        && (entry2.getKind() == SVNNodeKind.DIR)) {
                    return entry1.getName().toLowerCase().compareTo(
                            entry2.getName().toLowerCase());
                } else if ((entry1.getKind() == SVNNodeKind.DIR)
                        && (entry2.getKind() == SVNNodeKind.FILE)) {
                    return -1;
                } else if ((entry1.getKind() == SVNNodeKind.FILE)
                        && (entry2.getKind() == SVNNodeKind.DIR)) {
                    return 1;
                } else {
                    return entry1.getName().toLowerCase().compareTo(
                            entry2.getName().toLowerCase());
                }
            } else {
                return o1.toString().toLowerCase().compareTo(
                        o2.toString().toLowerCase());
            }

        }
    };

    OpcatFileNameFilter filter = new OpcatFileNameFilter(true, true);

    public SVNTreeView(String invisibleRoot) {
        super(invisibleRoot);

        this.tip = new String("Repository Browser");
        this.viewName = "Repository Browser";
        this.setType(Repository.SVNView);
        setCanViewWitoutProject(true);

        setRootVisible(true);
        // icon = RepositoryImages.OPD;
        this.icon = StandardImages.System_Icon;

        addTreeWillExpandListener(this);

        myCellRenderer = new SvnIconCellRenderer();
        this.setCellRenderer(myCellRenderer);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) this
                .getCellRenderer();
        renderer.setLeafIcon(RepositoryImages.FOLDER_EMPTY);
        renderer.setClosedIcon(RepositoryImages.FOLDER_CLOSE);
        renderer.setOpenIcon(RepositoryImages.FOLDER_OPEN);
        renderer.setBackgroundNonSelectionColor(new Color(230, 230, 230));
    }

    public void rebuildTree(MainStructure s, OpdProject opdProject) {

        // SwingUtilities.invokeLater(new GuiControl.glassPaneStarter());
        Opcat2.startWait();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Opcat2.getFrame().requestFocus();

                    if (!OpcatMCManager.isOnline()) {
                        Opcat2.endWait();
                        return;
                    }

                    DefaultTreeModel model = (DefaultTreeModel) getModel();

                    // start from root (null) entry
                    DefaultMutableTreeNode root = OpcatMCManager.getInstance()
                            .getOneLevelRepositoryTree(null, filter);

                    sort(root, false);

                    model.setRoot(root);

                    setModel(model);

                    expandRoot();
                    // SwingUtilities.invokeLater(new GuiControl.glassPaneStopper());

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                } finally {
                    Opcat2.endWait();
                }
            }
        });

    }

    public void dblClicked(MouseEvent event) {
        expandPath(getPathForLocation(event.getX(), event.getY()));
    }

    private void sort(DefaultMutableTreeNode root, boolean reverse) {

        if (root == null) {
            return;
        }

        ArrayList<DefaultMutableTreeNode> entry = Collections.list(root
                .children());
        Collections.sort(entry, nodeComparator);
        root.removeAllChildren();

        if (reverse) {
            for (int i = entry.size() - 1; i >= 0; i--)
                root.add(entry.get(i));
        } else {
            for (int i = 0; i < entry.size(); i++)
                root.add(entry.get(i));
        }
    }

    public void treeWillCollapse(TreeExpansionEvent event)
            throws ExpandVetoException {
        // TODO Auto-generated method stub

    }

    public void treeWillExpand(TreeExpansionEvent event)
            throws ExpandVetoException {

        final TreeExpansionEvent myEvent = event;
        Opcat2.startWait();

        try {
            OpcatMCDirEntry entry = null;

            if (((DefaultMutableTreeNode) myEvent.getPath().getLastPathComponent())
                    .getUserObject() instanceof OpcatMCDirEntry) {
                entry = (OpcatMCDirEntry) ((DefaultMutableTreeNode) myEvent.getPath()
                        .getLastPathComponent()).getUserObject();
            }
            DefaultMutableTreeNode node = OpcatMCManager.getInstance()
                    .getOneLevelRepositoryTree(entry, filter);

            sort(node, true);

            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) myEvent
                    .getPath().getLastPathComponent();

            int childrenCount = node.getChildCount();
            for (int i = childrenCount - 1; i >= 0; i--) {
                DefaultMutableTreeNode cur = (DefaultMutableTreeNode) node
                        .getChildAt(i);
                currentNode.add((MutableTreeNode) cur);
            }

            DefaultMutableTreeNode empty = (DefaultMutableTreeNode) currentNode
                    .getChildAt(0);
            if (empty.getUserObject().toString().equalsIgnoreCase("emptyopcatnode"))
                currentNode.remove(0);

            //currentNode.();
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        } finally {
            Opcat2.endWait();
        }
    }
}
