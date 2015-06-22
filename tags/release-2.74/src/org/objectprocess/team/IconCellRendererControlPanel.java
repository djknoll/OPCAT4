package org.objectprocess.team;

import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class IconCellRendererControlPanel extends DefaultTreeCellRenderer
{

        public IconCellRendererControlPanel(){}
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//                setBackgroundNonSelectionColor(new Color(230, 230, 230));
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object obj = node.getUserObject();

                if (obj instanceof WorkgroupTreeNode) {
                  setIcon(OPCATeamImages.WORKGROUP);
                  return this ;
                }

                if (obj instanceof OpmModelTreeNode) {
                  setIcon(OPCATeamImages.MODEL);
                  return this ;
                }
                if (obj instanceof CollaborativeSessionTreeNode) {
                  setIcon(OPCATeamImages.SESSION);
                  return this ;
                }
                if (obj instanceof UserTreeNode) {
                  setIcon(OPCATeamImages.USER);
                  return this ;
                }

//              setToolTipText(obj.toString());

                return this;
        }
}