package gui.repository;

import gui.controls.FileControl;
import gui.images.standard.StandardImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class OpdView extends BaseView {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    OpdProject myProject = null;
    private Opd hilighted;

    public OpdView(String invisibleRoot) {
        super(invisibleRoot);
        this.tip = new String("OPD Hierarchy");
        this.viewName = "OPD Hierarchy";
        this.setType(Repository.OPDVIEW);
        this.icon = StandardImages.System_Icon;
        setCellRenderer(new MyCellRenderer((DefaultTreeCellRenderer) getCellRenderer()));
    }

    public void rebuildTree(MainStructure s, OpdProject rootComponent) {
        if (FileControl.getInstance().isGUIOFF()) return;


        myProject = rootComponent;

        getExpandedDescendants(rootComponent);

        super.rebuildTree(s, rootComponent);

        DefaultTreeModel model = (DefaultTreeModel) this.getModel();

        DefaultTreeModel opdTree = s.getOpdTree();

        DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) model
                .getRoot()).getChildAt(0);


        prjRoot.add((DefaultMutableTreeNode) opdTree.getRoot());

        setExpandedDescendants();


    }

    public void select(Opd opd) {
        //search for the opd
        hilighted = opd;
        repaint();
        //DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        //DefaultMutableTreeNode node = getNodeFromObject((DefaultMutableTreeNode) model.getRoot(), opd);
        //if (node != null) {
        //    JOptionPane.showMessageDialog(Opcat2.getFrame(), node);
        //}
        //DefaultTreeCellRenderer renderer =
        //        (DefaultTreeCellRenderer) getCellRenderer();
        //
        ////renderer.getla
        //hilighted = node;


    }

    private DefaultMutableTreeNode getNodeFromObject(DefaultMutableTreeNode root, Opd opd) {
        DefaultMutableTreeNode node = null;
        if ((root.getUserObject() instanceof Opd) && (((Opd) root.getUserObject()).getOpdId() == opd.getOpdId())) {
            node = root;
        } else {
            for (int i = 0; i < root.getChildCount(); i++) {
                node = getNodeFromObject((DefaultMutableTreeNode) root.getChildAt(i), opd);
                if (node != null) break;
            }
        }
        return node;
    }

    class MyCellRenderer extends DefaultTreeCellRenderer {

        DefaultTreeCellRenderer opdRenderer;

        public MyCellRenderer(DefaultTreeCellRenderer opdRenderer) {
            super();
            this.opdRenderer = opdRenderer;
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            Object nodevalue = null;
            nodevalue = ((DefaultMutableTreeNode) value).getUserObject();

            if (nodevalue == null) {
                return opdRenderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                        row, hasFocus);
            }
            if (hilighted == null) {
                return opdRenderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                        row, hasFocus);
            }
            if ((nodevalue instanceof Opd) && (((Opd) nodevalue).getOpdId() == hilighted.getOpdId())) {
                opdRenderer.setTextNonSelectionColor(Color.BLUE);
                opdRenderer.setTextSelectionColor(Color.BLUE);
                opdRenderer.setFont(opdRenderer.getFont().deriveFont(Font.BOLD));
//                TreePath path = new TreePath(((DefaultMutableTreeNode) value).getPath());
//                if (!tree.isExpanded(path)) {
//                    tree.expandPath(path);
//                }
            } else {
                opdRenderer.setTextNonSelectionColor(Color.BLACK);
                opdRenderer.setTextSelectionColor(Color.BLACK);
                opdRenderer.setFont(opdRenderer.getFont().deriveFont(Font.PLAIN));
            }

            return opdRenderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                    row, hasFocus);
        }
    }

}


