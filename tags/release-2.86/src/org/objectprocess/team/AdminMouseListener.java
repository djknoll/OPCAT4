package org.objectprocess.team;

import gui.Opcat2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.objectprocess.Client.TeamMember;

//class for Admin Mouse Listener
public class AdminMouseListener extends MouseAdapter
  {
    protected JTree myTree;
    protected TeamMember myTeamMember;
    protected Opcat2 myOpcat2;

    public AdminMouseListener(JTree tree, TeamMember teamMember,Opcat2 opcat2) {
      this.myTree = tree;
      this.myTeamMember = teamMember;
      this.myOpcat2 = opcat2;
    }

    public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger() || e.isMetaDown()) {
        TreePath selPath = this.myTree.getPathForLocation(e.getX(), e.getY());

        if (selPath == null) {
			return;
		}

        this.myTree.setSelectionPath(selPath);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (selPath.
            getLastPathComponent());

        if (node == null) {
			return;
		}

        Object obj = node.getUserObject();

        if (obj instanceof WorkgroupTreeNode) {
          JPopupMenu jpm = new WorkgroupPopupMenu(
              node,
              this.myTeamMember,
              ( (WorkgroupTreeNode) node.getUserObject()).
              getEnhancedWorkgroupPermissionsValue(),
              this.myOpcat2);

          jpm.show(this.myTree, e.getX(), e.getY());
        }

        if (obj instanceof OpmModelTreeNode) {
          JPopupMenu jpm = new OpmModelPopupMenu(
              node,
              this.myTeamMember,
              ( (OpmModelTreeNode) node.getUserObject()).
              getEnhancedOpmModelPermissionsValue(),
              this.myOpcat2);
          jpm.show(this.myTree, e.getX(), e.getY());
        }

        if (obj instanceof CollaborativeSessionTreeNode) {
          JPopupMenu jpm = new CollaborativeSessionPopupMenu(
              node,
              this.myTeamMember,
              ( (CollaborativeSessionTreeNode) node.getUserObject()).
              getEnhancedCollaborativeSessionPermissionsValue(),
              this.myOpcat2);
          jpm.show(this.myTree, e.getX(), e.getY());
        }

        if (obj instanceof UserTreeNode) {
          JPopupMenu jpm = new UserDataPopupMenu(this.myTeamMember,
                                                 this.myOpcat2);
          jpm.show(this.myTree, e.getX(), e.getY());
        }

      }
    }


    public void mouseClicked(MouseEvent e) {
        TreePath selPath = this.myTree.getPathForLocation(e.getX(), e.getY());
        if (selPath == null) {
			return;
		}

        this.myTree.setSelectionPath(selPath);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(selPath.getLastPathComponent());

        if(e.getClickCount() == 2) {
          if (node != null) {
            Object obj = node.getUserObject();
            if (obj instanceof CollaborativeSessionTreeNode) {
				;
			}

          }
        }
    }
  }
//end of class AdminMouseListener

