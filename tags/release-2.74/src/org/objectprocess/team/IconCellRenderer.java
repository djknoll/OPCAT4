package org.objectprocess.team;

import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.objectprocess.Client.PermissionFlags;

public class IconCellRenderer extends DefaultTreeCellRenderer
{

        public IconCellRenderer(){}
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//                setBackgroundNonSelectionColor(new Color(230, 230, 230));
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object obj = node.getUserObject();

                if (obj instanceof String) {
                  setIcon(OPCATeamImages.MEMBERSTREE);
                  return this ;
                }

                if (obj instanceof MemberTreeNode) {
                  MemberTreeNode memberTreeNode = (MemberTreeNode)obj;
                  if (memberTreeNode.getMemberInfo().getAccessControl().intValue() >=
                        PermissionFlags.LOGGEDIN.intValue())
                  setIcon(OPCATeamImages.MEMBER_CONNECTED);
                else
                  setIcon(OPCATeamImages.MEMBER_NOT_CONNECTED);
                }

//              setToolTipText(obj.toString());

                return this;
        }
}
