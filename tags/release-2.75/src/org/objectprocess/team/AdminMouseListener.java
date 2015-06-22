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
      myTree = tree;
      myTeamMember = teamMember;
      myOpcat2 = opcat2;
    }

    public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {
        TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());

        if (selPath == null)
          return;

        myTree.setSelectionPath(selPath);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (selPath.
            getLastPathComponent());

        if (node == null)
          return;

        Object obj = node.getUserObject();

        if (obj instanceof WorkgroupTreeNode) {
          JPopupMenu jpm = new WorkgroupPopupMenu(
              node,
              myTeamMember,
              ( (WorkgroupTreeNode) node.getUserObject()).
              getEnhancedWorkgroupPermissionsValue(),
              myOpcat2);

          jpm.show(myTree, e.getX(), e.getY());
        }

        if (obj instanceof OpmModelTreeNode) {
          JPopupMenu jpm = new OpmModelPopupMenu(
              node,
              myTeamMember,
              ( (OpmModelTreeNode) node.getUserObject()).
              getEnhancedOpmModelPermissionsValue(),
              myOpcat2);
          jpm.show(myTree, e.getX(), e.getY());
        }

        if (obj instanceof CollaborativeSessionTreeNode) {
          JPopupMenu jpm = new CollaborativeSessionPopupMenu(
              node,
              myTeamMember,
              ( (CollaborativeSessionTreeNode) node.getUserObject()).
              getEnhancedCollaborativeSessionPermissionsValue(),
              myOpcat2);
          jpm.show(myTree, e.getX(), e.getY());
        }

        if (obj instanceof UserTreeNode) {
          JPopupMenu jpm = new UserDataPopupMenu(myTeamMember,
                                                 myOpcat2);
          jpm.show(myTree, e.getX(), e.getY());
        }

      }
    }


    public void mouseClicked(MouseEvent e) {
        TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
        if (selPath == null)
          return;

        myTree.setSelectionPath(selPath);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(selPath.getLastPathComponent());

        if(e.getClickCount() == 2) {
          if (node != null) {
            Object obj = node.getUserObject();
            if (obj instanceof CollaborativeSessionTreeNode);

          }
        }
    }
  }
//end of class AdminMouseListener

