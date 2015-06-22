package org.objectprocess.team;

import gui.Opcat2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.Enhanced2CollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


public class Admin extends JPanel {
  private JFrame myFrame;
  private TeamMember myTeamMember;
  private Opcat2 myOpcat2;

  protected DefaultMutableTreeNode rootNode;
  protected DefaultTreeModel treeModel;
  protected DefaultMutableTreeNode membersRootNode;
  protected DefaultTreeModel membersTreeModel=null;

  protected JTree tree;
  protected JScrollPane treeView;

  protected JTree membersTree = new JTree(membersTreeModel);
  protected JScrollPane userView = new JScrollPane();

  protected JPanel jPanel1 = new JPanel();
  protected JPanel jPanel2 = new JPanel();
  protected JPanel jPanel3 = new JPanel();

  JLabel controlPanelLable = new JLabel();
  JLabel presenceWindowLabel = new JLabel();
  JLabel activeSessionLabel = new JLabel();
  JLabel tokenHolderLabel = new JLabel();
  JTextField activeSessionText = new JTextField();
  JTextField tokenHolderText = new JTextField();

  GridBagLayout gridBagLayout4 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  BorderLayout borderLayout = new BorderLayout();

  public Admin(TeamMember teamMember,Opcat2 opcat2) throws HeadlessException {
    super();

    myTeamMember=teamMember;
    myOpcat2=opcat2;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

   }
  private void jbInit() throws Exception {

    this.setLayout(borderLayout);
    jPanel1.setLayout(gridBagLayout1);
    jPanel2.setLayout(gridBagLayout2);

    jPanel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jPanel3.setInputVerifier(null);
    jPanel3.setLayout(gridBagLayout3);

    //Create the logical tree and his nodes.
    UserTreeNode userTreeNode = new UserTreeNode(myTeamMember);
    rootNode = new DefaultMutableTreeNode(userTreeNode);
    treeModel = new DefaultTreeModel(rootNode);
    createControlPanelTree(treeModel);

    //Create the visual tree which allows one selection at a time.
    tree = new JTree(treeModel);
    tree.setCellRenderer(new IconCellRendererControlPanel());
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    //Create the scroll pane and add the visual tree to it.
    treeView = new JScrollPane(tree);
    treeView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    treeView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    treeView.setMinimumSize(new Dimension(100, 50));

    userView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    userView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    activeSessionLabel.setFont(new java.awt.Font("SansSerif", 0, 11));
    activeSessionLabel.setText("Active Session:");
    activeSessionLabel.setToolTipText("The name of the active session" );
    tokenHolderLabel.setFont(new java.awt.Font("SansSerif", 0, 11));
    tokenHolderLabel.setText("Token Holder:");
    tokenHolderLabel.setToolTipText("The name of the token holder" );

    activeSessionText.setFont(new java.awt.Font("Dialog", 1, 12));
    activeSessionText.setEditable(false);
    activeSessionText.setText("");
    activeSessionText.setColumns(0);
    activeSessionText.setHorizontalAlignment(SwingConstants.CENTER);
    tokenHolderText.setEditable(false);
    tokenHolderText.setText("");
    tokenHolderText.setHorizontalAlignment(SwingConstants.CENTER);

    controlPanelLable.setFont(new java.awt.Font("SansSerif", 1, 12));
    controlPanelLable.setText("Control Panel:");
    controlPanelLable.setToolTipText("Local administration for the user" );

    presenceWindowLabel.setEnabled(false);
    presenceWindowLabel.setFont(new java.awt.Font("SansSerif", 1, 12));
    presenceWindowLabel.setText("Presence Window:");
    presenceWindowLabel.setToolTipText("Session's participants list" );

    jPanel1.add(treeView,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 5, 7), 142, -100));
    jPanel1.add(controlPanelLable,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 8, 0, 67), 26, 4));

    jPanel2.add(userView,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 11, 7), 161, -201));
    jPanel2.add(presenceWindowLabel,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 8, 0, 32), 33, 2));
    userView.getViewport().add(membersTree, null);

    jPanel3.add(activeSessionLabel,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(21, 8, 0, 0), 3, 4));
    jPanel3.add(activeSessionText,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(21, 0, 0, 7), 81, 0));
    jPanel3.add(tokenHolderText,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(11, 0, 6, 7), 80, 0));
    jPanel3.add(tokenHolderLabel,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 8, 6, 8), 5, 4));

    this.add(jPanel1, BorderLayout.CENTER);
    this.add(jPanel3, BorderLayout.NORTH);
    this.add(jPanel2, BorderLayout.SOUTH);

    //add listener to the tree
    tree.addMouseListener(new AdminMouseListener(tree,myTeamMember,myOpcat2));

    //Listen for when the selection changes.
    tree.addTreeSelectionListener(new TreeSelectionListener() {
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object obj=node.getUserObject();
        if (obj instanceof WorkgroupTreeNode) {}
        if (obj instanceof OpmModelTreeNode) {}
        if (obj instanceof CollaborativeSessionTreeNode) {}
        if (obj instanceof UserTreeNode) {}
       }
    });

    this.setSize(new Dimension(180, 495));
    this.setVisible(true);

  } //end of Jbinit()


//The next functions create, add, or remove objects from Control Panel Tree
  public DefaultMutableTreeNode addNodeToControlPanelTree(Object child) {
      DefaultMutableTreeNode parentNode = null;
      TreePath parentPath = tree.getSelectionPath();

      if (parentPath == null) {
          parentNode = rootNode;
      } else {
          parentNode = (DefaultMutableTreeNode)
                       (parentPath.getLastPathComponent());
      }

      return addObject(parentNode, child, true);
  }
  private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                          Object child) {
      return addObject(parent, child, false);
  }
  private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                          Object child,
                                          boolean shouldBeVisible) {
      DefaultMutableTreeNode childNode =
              new DefaultMutableTreeNode(child);

      if (parent == null) {
          parent = rootNode;
      }

      treeModel.insertNodeInto(childNode, parent,
                               parent.getChildCount());

      // Make sure the user can see the lovely new node.
      if (shouldBeVisible) {
          tree.scrollPathToVisible(new TreePath(childNode.getPath()));
      }
      return childNode;
  }

  public void removeNodeFromControlPanelTree(DefaultMutableTreeNode node) {

    treeModel.removeNodeFromParent(node);
  }


  private void createControlPanelTree(DefaultTreeModel treeModel) {

    DefaultMutableTreeNode workgroupNode = null;
    DefaultMutableTreeNode opmModelNode = null;
    DefaultMutableTreeNode collaborativeSessionNode=null;

    EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;
    EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;
    EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

    Integer    myWorkgroupId,myOpmModelId,myCollaborativeSessionID,tempInteger;
    int sessionAccessControlIntValue;
    int i,j,x,z;

    //create wg's instances
    for (i=0; i< myTeamMember.getLocalEnhancedWorkgroupsPermissions().size(); i++)
    {
      myEnhancedWorkgroupPermissionsValue = (EnhancedWorkgroupPermissionsValue)
          myTeamMember.getLocalEnhancedWorkgroupsPermissions().get(i);

      //present ONLY entities that are not disabled on the server, and they have permission
      if  ( (myEnhancedWorkgroupPermissionsValue.getWorkgroup().getEnabled().booleanValue()== true) &&
             (myEnhancedWorkgroupPermissionsValue.getAccessControl().intValue() != PermissionFlags.NULL_PERMISSIONS.intValue()) )
      {
        myWorkgroupId = myEnhancedWorkgroupPermissionsValue.getWorkgroupID();
        WorkgroupTreeNode wg = new WorkgroupTreeNode(myEnhancedWorkgroupPermissionsValue);
        workgroupNode = addObject(null, wg);

        //create opmmodel's instances and add them to the tree
        for (j=0; j< myTeamMember.getLocalEnhancedOpmModelsPermissions().size(); j++) {
          myEnhancedOpmModelPermissionsValue = (EnhancedOpmModelPermissionsValue)
              myTeamMember.getLocalEnhancedOpmModelsPermissions().get(j);

          //present ONLY entities that are not disabled on the server, and they have permission
          if ( (myEnhancedOpmModelPermissionsValue.getOpmModel().getEnabled().booleanValue()==true) &&
               myEnhancedOpmModelPermissionsValue.getAccessControl().intValue() !=
               PermissionFlags.NULL_PERMISSIONS.intValue())
          {
            tempInteger = myEnhancedOpmModelPermissionsValue.getOpmModel().getWorkgroupID();
            if  (tempInteger.equals(myWorkgroupId))
            {
              myOpmModelId = myEnhancedOpmModelPermissionsValue.getOpmModelID();
              OpmModelTreeNode om = new OpmModelTreeNode(myEnhancedOpmModelPermissionsValue);
              opmModelNode = addObject(workgroupNode,om);

              //create session's instances and add them to the tree
              for (x=0; x< myTeamMember.getLocalEnhancedCollaborativeSessionsPermissions().size(); x++) {
                myEnhancedCollaborativeSessionPermissionsValue =(EnhancedCollaborativeSessionPermissionsValue)
                                                                 myTeamMember.getLocalEnhancedCollaborativeSessionsPermissions().get(x);

                //present ONLY entities that are not disabled on the server, and they have permission
                if ( (myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession()
                      .getTerminated().booleanValue()==false) &&
                     (myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().
                      getEnabled().booleanValue() == true) )
                {
                  sessionAccessControlIntValue = myEnhancedCollaborativeSessionPermissionsValue.getAccessControl().intValue();
                  if (sessionAccessControlIntValue >= PermissionFlags.LOGGEDIN.intValue())
                    sessionAccessControlIntValue -= PermissionFlags.LOGGEDIN.intValue();
                  tempInteger = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getOpmModelID();

                  if ( (tempInteger.equals(myOpmModelId)) &&
                       (sessionAccessControlIntValue != PermissionFlags.NULL_PERMISSIONS.intValue())  )
                  {
                    myCollaborativeSessionID= myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID();
                    CollaborativeSessionTreeNode cs = new CollaborativeSessionTreeNode(myEnhancedCollaborativeSessionPermissionsValue);
                    collaborativeSessionNode = addObject(opmModelNode,cs);
                  }
                }
              }
            }
          }
        }
      }
    }
  }
//end of createControlPanelTree function

public void refreshTree() {

    //Create the logical tree and his nodes.
    treeView.getViewport().remove(tree);

    UserTreeNode userTreeNode = new UserTreeNode(myTeamMember);
    rootNode = new DefaultMutableTreeNode(userTreeNode);

    treeModel = new DefaultTreeModel(rootNode);
    createControlPanelTree(treeModel);
    tree = new JTree(treeModel);
    tree.setCellRenderer(new IconCellRendererControlPanel());

    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    //add listener to the tree
    tree.addMouseListener(new AdminMouseListener(tree,myTeamMember,myOpcat2));

    //Listen for when the selection changes.
    tree.addTreeSelectionListener(new TreeSelectionListener() {
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object obj=node.getUserObject();
        if (obj instanceof WorkgroupTreeNode) {}
        if (obj instanceof OpmModelTreeNode) {}
        if (obj instanceof CollaborativeSessionTreeNode) {}
        if (obj instanceof UserTreeNode) {}
       }
    });

    treeView.getViewport().add(tree, null);
  }

//The next functions create, update and remove objects from the MembersTree
  public void addNodeToMembersTree(Object child) {

    DefaultMutableTreeNode childNode =new DefaultMutableTreeNode(child);
    membersTreeModel.insertNodeInto(childNode, membersRootNode, membersRootNode.getChildCount());
    // Make sure the user can see the lovely new node.
    membersTree.scrollPathToVisible(new TreePath(childNode.getPath()));
  }

  public void updateMemberIcon(int memberID) {

    Object rootObj = membersTreeModel.getRoot();
    int childCount = membersTreeModel.getChildCount(rootObj);
    Object childObj=null;
    int i;
    for (i=0; i<childCount; i++) {
      childObj = membersTreeModel.getChild(rootObj, i);
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) childObj;
      Object obj=node.getUserObject();

      if (obj instanceof MemberTreeNode) {
        MemberTreeNode memberTreeNode = (MemberTreeNode) obj;
        if (memberTreeNode.getMemberInfo().getUserID().intValue() == memberID) {
          membersTreeModel.nodeChanged(node);
          return;
        }
      }
    }
  }

public void createMembersTree(Integer collaborativeSessionID) {

    int z =0;
    EnhancedCollaborativeSessionValue enhancedCollaborativeSessionValue=null;

    try {
      enhancedCollaborativeSessionValue = myTeamMember.
          fatchEnhancedCollaborativeSession(collaborativeSessionID.intValue());

      String collaborativeSessionName = enhancedCollaborativeSessionValue.getCollaborativeSessionName();

      //Create the logical session users tree and his nodes.
      membersRootNode = new DefaultMutableTreeNode(collaborativeSessionName);
      membersTreeModel = new DefaultTreeModel(membersRootNode);

      for (z = 0;
           z <enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
           z++) {
        Enhanced2CollaborativeSessionPermissionsValue e2cspv =
            enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[z];
        //get the member accessControl flag
        int memberAccessControlIntValue = e2cspv.getAccessControl().intValue();
        //ignore the loggedin flag
        if (memberAccessControlIntValue >= PermissionFlags.LOGGEDIN.intValue())
          memberAccessControlIntValue-=PermissionFlags.LOGGEDIN.intValue();
        //if the user has no permission in this session- dont add him to the members tree
        if (memberAccessControlIntValue != PermissionFlags.NULL_PERMISSIONS.intValue()) {
          MemberTreeNode memberTreeNode = new MemberTreeNode(e2cspv);
          addNodeToMembersTree(memberTreeNode);
        }
      }

      userView.getViewport().remove(membersTree);
      membersTree = new JTree(membersTreeModel);
      membersTree.setCellRenderer(new IconCellRenderer());

      //Listen for when the selection changes.
      membersTree.addTreeSelectionListener(new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) membersTree.
              getLastSelectedPathComponent();

          if (node == null)
            return;

          Object obj = node.getUserObject();
          if (obj instanceof MemberTreeNode) {
//        MemberTreeNode memberTreeNode = (MemberTreeNode)obj;
//        memberTreeNode.getMemberInfo().setAccessControl(PermissionFlags. LOGGEDIN);
//        membersTreeModel.nodeChanged(node);
          }
        }
      });

      userView.getViewport().add(membersTree, null);
      presenceWindowLabel.setEnabled(true);

    }catch (Exception exception) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
    }
  }

public void removeMembersTree() {
  userView.getViewport().remove(membersTree);
  membersTreeModel = null;
  membersTree = new JTree(membersTreeModel);
  userView.getViewport().add(membersTree, null);
  presenceWindowLabel.setEnabled(false);
}


//Set of functions  to handle the current open session.
public void updateActiveSessionOnPanel(String sessionName) {
      activeSessionText.setText(sessionName);
  }
//The next functions update the New Token Holder on the Panel.
  //the first function get as a parameter the memberID
public void updateTokenHolderOnPanel(int tokenHolderID) {
    String tokenHolderName = null;
    if (tokenHolderID == PermissionFlags.SERVER_USERID.intValue()) {
      tokenHolderText.setText("server");
      return;
    }
    tokenHolderName = myTeamMember.getLocalMemberName(tokenHolderID);
    if (tokenHolderName == null)
      tokenHolderText.setText("");
    else tokenHolderText.setText(tokenHolderName);
  }
  //the second function get as a parameter the name of the token holder
  public void updateTokenHolderOnPanel(String tokenHolderName) {
    if (tokenHolderName == null)
      tokenHolderText.setText("");
      else tokenHolderText.setText(tokenHolderName);
  }


}//end of class Admin


// class for workgroup node in the tree
class WorkgroupTreeNode extends Object {
  EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

  public WorkgroupTreeNode (EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue) {
    this.myEnhancedWorkgroupPermissionsValue=enhancedWorkgroupPermissionsValue;
    }

  public String toString() {
    return myEnhancedWorkgroupPermissionsValue.getWorkgroup().getWorkgroupName();
  }

  public EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsValue() {
    return myEnhancedWorkgroupPermissionsValue;
  }
}

//class for opmmodel node in the tree
class OpmModelTreeNode extends Object {
  EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

  public OpmModelTreeNode (EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue) {
    this.myEnhancedOpmModelPermissionsValue=enhancedOpmModelPermissionsValue;
    }

  public String toString() {
    return myEnhancedOpmModelPermissionsValue.getOpmModel().getOpmModelName();
  }

  public EnhancedOpmModelPermissionsValue getEnhancedOpmModelPermissionsValue() {
    return myEnhancedOpmModelPermissionsValue;
  }
}

//class for session node in the tree
class CollaborativeSessionTreeNode extends Object {
  EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

  public CollaborativeSessionTreeNode (EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue) {
    this.myEnhancedCollaborativeSessionPermissionsValue=enhancedCollaborativeSessionPermissionsValue;
    }

  public String toString() {
    return myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getCollaborativeSessionName();
  }

  public EnhancedCollaborativeSessionPermissionsValue getEnhancedCollaborativeSessionPermissionsValue() {
    return myEnhancedCollaborativeSessionPermissionsValue;
  }
}

//class for user data in the tree
class UserTreeNode extends Object {
  String userName;

  public UserTreeNode(TeamMember teamMember) {
    this.userName=teamMember.getLocalEnhancedUser().getFirstName() + " " + teamMember.getLocalEnhancedUser().getLastName();
    }

  public String toString() {
    return userName;
  }
}

//class for member data in the tree
class MemberTreeNode extends Object {
Enhanced2CollaborativeSessionPermissionsValue myMemberInfo;

  public MemberTreeNode(Enhanced2CollaborativeSessionPermissionsValue memberInfo) {
    this.myMemberInfo = memberInfo;
}

  public String toString() {
    return (myMemberInfo.getUser().getFirstName() + " " + myMemberInfo.getUser().getLastName());
  }
  public Enhanced2CollaborativeSessionPermissionsValue getMemberInfo() {
    return myMemberInfo;
  }
}
