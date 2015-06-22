package org.objectprocess.team;

import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.objectprocess.Client.PermissionFlags;

public class IconCellRenderer extends DefaultTreeCellRenderer
{

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
	 * 
	 */
	 
		public IconCellRenderer(){}
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//                setBackgroundNonSelectionColor(new Color(230, 230, 230));
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object obj = node.getUserObject();

                if (obj instanceof String) {
                  this.setIcon(OPCATeamImages.MEMBERSTREE);
                  return this ;
                }

                if (obj instanceof MemberTreeNode) {
                  MemberTreeNode memberTreeNode = (MemberTreeNode)obj;
                  if (memberTreeNode.getMemberInfo().getAccessControl().intValue() >=
                        PermissionFlags.LOGGEDIN.intValue()) {
					this.setIcon(OPCATeamImages.MEMBER_CONNECTED);
				} else {
					this.setIcon(OPCATeamImages.MEMBER_NOT_CONNECTED);
				}
                }

//              setToolTipText(obj.toString());

                return this;
        }
}
