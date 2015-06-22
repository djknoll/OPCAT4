package org.objectprocess.team;

import gui.Opcat2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
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
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2001
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class Admin extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private TeamMember myTeamMember;

	private Opcat2 myOpcat2;

	protected DefaultMutableTreeNode rootNode;

	protected DefaultTreeModel treeModel;

	protected DefaultMutableTreeNode membersRootNode;

	protected DefaultTreeModel membersTreeModel = null;

	protected JTree tree;

	protected JScrollPane treeView;

	protected JTree membersTree = new JTree(this.membersTreeModel);

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

	public Admin(TeamMember teamMember, Opcat2 opcat2) throws HeadlessException {
		super();

		this.myTeamMember = teamMember;
		this.myOpcat2 = opcat2;

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {

		this.setLayout(this.borderLayout);
		this.jPanel1.setLayout(this.gridBagLayout1);
		this.jPanel2.setLayout(this.gridBagLayout2);

		this.jPanel3.setFont(new java.awt.Font("Dialog", 0, 12));
		this.jPanel3.setInputVerifier(null);
		this.jPanel3.setLayout(this.gridBagLayout3);

		// Create the logical tree and his nodes.
		UserTreeNode userTreeNode = new UserTreeNode(this.myTeamMember);
		this.rootNode = new DefaultMutableTreeNode(userTreeNode);
		this.treeModel = new DefaultTreeModel(this.rootNode);
		this.createControlPanelTree(this.treeModel);

		// Create the visual tree which allows one selection at a time.
		this.tree = new JTree(this.treeModel);
		this.tree.setCellRenderer(new IconCellRendererControlPanel());
		this.tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Create the scroll pane and add the visual tree to it.
		this.treeView = new JScrollPane(this.tree);
		this.treeView
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.treeView
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.treeView.setMinimumSize(new Dimension(100, 50));

		this.userView
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.userView
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.activeSessionLabel.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.activeSessionLabel.setText("Active Session:");
		this.activeSessionLabel
				.setToolTipText("The name of the active session");
		this.tokenHolderLabel.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.tokenHolderLabel.setText("Token Holder:");
		this.tokenHolderLabel.setToolTipText("The name of the token holder");

		this.activeSessionText.setFont(new java.awt.Font("Dialog", 1, 12));
		this.activeSessionText.setEditable(false);
		this.activeSessionText.setText("");
		this.activeSessionText.setColumns(0);
		this.activeSessionText.setHorizontalAlignment(SwingConstants.CENTER);
		this.tokenHolderText.setEditable(false);
		this.tokenHolderText.setText("");
		this.tokenHolderText.setHorizontalAlignment(SwingConstants.CENTER);

		this.controlPanelLable.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.controlPanelLable.setText("Control Panel:");
		this.controlPanelLable
				.setToolTipText("Local administration for the user");

		this.presenceWindowLabel.setEnabled(false);
		this.presenceWindowLabel.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.presenceWindowLabel.setText("Presence Window:");
		this.presenceWindowLabel.setToolTipText("Session's participants list");

		this.jPanel1.add(this.treeView, new GridBagConstraints(0, 1, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 8, 5, 7), 142, -100));
		this.jPanel1.add(this.controlPanelLable, new GridBagConstraints(0, 0,
				1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 8, 0, 67), 26, 4));

		this.jPanel2.add(this.userView, new GridBagConstraints(0, 1, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 8, 11, 7), 161, -201));
		this.jPanel2.add(this.presenceWindowLabel, new GridBagConstraints(0, 0,
				1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(9, 8, 0, 32), 33, 2));
		this.userView.getViewport().add(this.membersTree, null);

		this.jPanel3.add(this.activeSessionLabel, new GridBagConstraints(0, 0,
				1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(21, 8, 0, 0), 3, 4));
		this.jPanel3.add(this.activeSessionText, new GridBagConstraints(1, 0,
				1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(21, 0, 0, 7), 81, 0));
		this.jPanel3.add(this.tokenHolderText, new GridBagConstraints(1, 1, 1,
				1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(11, 0, 6, 7), 80, 0));
		this.jPanel3.add(this.tokenHolderLabel, new GridBagConstraints(0, 1, 1,
				1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(12, 8, 6, 8), 5, 4));

		this.add(this.jPanel1, BorderLayout.CENTER);
		this.add(this.jPanel3, BorderLayout.NORTH);
		this.add(this.jPanel2, BorderLayout.SOUTH);

		// add listener to the tree
		this.tree.addMouseListener(new AdminMouseListener(this.tree,
				this.myTeamMember, this.myOpcat2));

		// Listen for when the selection changes.
		this.tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) Admin.this.tree
						.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}

				Object obj = node.getUserObject();
				if (obj instanceof WorkgroupTreeNode) {
				}
				if (obj instanceof OpmModelTreeNode) {
				}
				if (obj instanceof CollaborativeSessionTreeNode) {
				}
				if (obj instanceof UserTreeNode) {
				}
			}
		});

		this.setSize(new Dimension(180, 495));
		this.setVisible(true);

	} // end of Jbinit()

	// The next functions create, add, or remove objects from Control Panel Tree
	public DefaultMutableTreeNode addNodeToControlPanelTree(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = this.tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = this.rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath
					.getLastPathComponent());
		}

		return this.addObject(parentNode, child, true);
	}

	private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child) {
		return this.addObject(parent, child, false);
	}

	private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = this.rootNode;
		}

		this.treeModel
				.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			this.tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	public void removeNodeFromControlPanelTree(DefaultMutableTreeNode node) {

		this.treeModel.removeNodeFromParent(node);
	}

	private void createControlPanelTree(DefaultTreeModel treeModel) {

		DefaultMutableTreeNode workgroupNode = null;
		DefaultMutableTreeNode opmModelNode = null;
		// DefaultMutableTreeNode collaborativeSessionNode = null;

		EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;
		EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;
		EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

		Integer myWorkgroupId, myOpmModelId, tempInteger;
		int sessionAccessControlIntValue;
		int i, j, x;

		// create wg's instances
		for (i = 0; i < this.myTeamMember
				.getLocalEnhancedWorkgroupsPermissions().size(); i++) {
			myEnhancedWorkgroupPermissionsValue = (EnhancedWorkgroupPermissionsValue) this.myTeamMember
					.getLocalEnhancedWorkgroupsPermissions().get(i);

			// present ONLY entities that are not disabled on the server, and
			// they have permission
			if ((myEnhancedWorkgroupPermissionsValue.getWorkgroup()
					.getEnabled().booleanValue() == true)
					&& (myEnhancedWorkgroupPermissionsValue.getAccessControl()
							.intValue() != PermissionFlags.NULL_PERMISSIONS
							.intValue())) {
				myWorkgroupId = myEnhancedWorkgroupPermissionsValue
						.getWorkgroupID();
				WorkgroupTreeNode wg = new WorkgroupTreeNode(
						myEnhancedWorkgroupPermissionsValue);
				workgroupNode = this.addObject(null, wg);

				// create opmmodel's instances and add them to the tree
				for (j = 0; j < this.myTeamMember
						.getLocalEnhancedOpmModelsPermissions().size(); j++) {
					myEnhancedOpmModelPermissionsValue = (EnhancedOpmModelPermissionsValue) this.myTeamMember
							.getLocalEnhancedOpmModelsPermissions().get(j);

					// present ONLY entities that are not disabled on the
					// server, and they have permission
					if ((myEnhancedOpmModelPermissionsValue.getOpmModel()
							.getEnabled().booleanValue() == true)
							&& (myEnhancedOpmModelPermissionsValue
									.getAccessControl().intValue() != PermissionFlags.NULL_PERMISSIONS
									.intValue())) {
						tempInteger = myEnhancedOpmModelPermissionsValue
								.getOpmModel().getWorkgroupID();
						if (tempInteger.equals(myWorkgroupId)) {
							myOpmModelId = myEnhancedOpmModelPermissionsValue
									.getOpmModelID();
							OpmModelTreeNode om = new OpmModelTreeNode(
									myEnhancedOpmModelPermissionsValue);
							opmModelNode = this.addObject(workgroupNode, om);

							// create session's instances and add them to the
							// tree
							for (x = 0; x < this.myTeamMember
									.getLocalEnhancedCollaborativeSessionsPermissions()
									.size(); x++) {
								myEnhancedCollaborativeSessionPermissionsValue = (EnhancedCollaborativeSessionPermissionsValue) this.myTeamMember
										.getLocalEnhancedCollaborativeSessionsPermissions()
										.get(x);

								// present ONLY entities that are not disabled
								// on the server, and they have permission
								if ((myEnhancedCollaborativeSessionPermissionsValue
										.getCollaborativeSession()
										.getTerminated().booleanValue() == false)
										&& (myEnhancedCollaborativeSessionPermissionsValue
												.getCollaborativeSession()
												.getEnabled().booleanValue() == true)) {
									sessionAccessControlIntValue = myEnhancedCollaborativeSessionPermissionsValue
											.getAccessControl().intValue();
									if (sessionAccessControlIntValue >= PermissionFlags.LOGGEDIN
											.intValue()) {
										sessionAccessControlIntValue -= PermissionFlags.LOGGEDIN
												.intValue();
									}
									tempInteger = myEnhancedCollaborativeSessionPermissionsValue
											.getCollaborativeSession()
											.getOpmModelID();

									if ((tempInteger.equals(myOpmModelId))
											&& (sessionAccessControlIntValue != PermissionFlags.NULL_PERMISSIONS
													.intValue())) {
										CollaborativeSessionTreeNode cs = new CollaborativeSessionTreeNode(
												myEnhancedCollaborativeSessionPermissionsValue);
										addObject(opmModelNode, cs);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	// end of createControlPanelTree function

	public void refreshTree() {

		// Create the logical tree and his nodes.
		this.treeView.getViewport().remove(this.tree);

		UserTreeNode userTreeNode = new UserTreeNode(this.myTeamMember);
		this.rootNode = new DefaultMutableTreeNode(userTreeNode);

		this.treeModel = new DefaultTreeModel(this.rootNode);
		this.createControlPanelTree(this.treeModel);
		this.tree = new JTree(this.treeModel);
		this.tree.setCellRenderer(new IconCellRendererControlPanel());

		this.tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// add listener to the tree
		this.tree.addMouseListener(new AdminMouseListener(this.tree,
				this.myTeamMember, this.myOpcat2));

		// Listen for when the selection changes.
		this.tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) Admin.this.tree
						.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}

				Object obj = node.getUserObject();
				if (obj instanceof WorkgroupTreeNode) {
				}
				if (obj instanceof OpmModelTreeNode) {
				}
				if (obj instanceof CollaborativeSessionTreeNode) {
				}
				if (obj instanceof UserTreeNode) {
				}
			}
		});

		this.treeView.getViewport().add(this.tree, null);
	}

	// The next functions create, update and remove objects from the MembersTree
	public void addNodeToMembersTree(Object child) {

		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		this.membersTreeModel.insertNodeInto(childNode, this.membersRootNode,
				this.membersRootNode.getChildCount());
		// Make sure the user can see the lovely new node.
		this.membersTree.scrollPathToVisible(new TreePath(childNode.getPath()));
	}

	public void updateMemberIcon(int memberID) {

		Object rootObj = this.membersTreeModel.getRoot();
		int childCount = this.membersTreeModel.getChildCount(rootObj);
		Object childObj = null;
		int i;
		for (i = 0; i < childCount; i++) {
			childObj = this.membersTreeModel.getChild(rootObj, i);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childObj;
			Object obj = node.getUserObject();

			if (obj instanceof MemberTreeNode) {
				MemberTreeNode memberTreeNode = (MemberTreeNode) obj;
				if (memberTreeNode.getMemberInfo().getUserID().intValue() == memberID) {
					this.membersTreeModel.nodeChanged(node);
					return;
				}
			}
		}
	}

	public void createMembersTree(Integer collaborativeSessionID) {

		int z = 0;
		EnhancedCollaborativeSessionValue enhancedCollaborativeSessionValue = null;

		try {
			enhancedCollaborativeSessionValue = this.myTeamMember
					.fatchEnhancedCollaborativeSession(collaborativeSessionID
							.intValue());

			String collaborativeSessionName = enhancedCollaborativeSessionValue
					.getCollaborativeSessionName();

			// Create the logical session users tree and his nodes.
			this.membersRootNode = new DefaultMutableTreeNode(
					collaborativeSessionName);
			this.membersTreeModel = new DefaultTreeModel(this.membersRootNode);

			for (z = 0; z < enhancedCollaborativeSessionValue
					.getCollaborativeSessionsPermissions().length; z++) {
				Enhanced2CollaborativeSessionPermissionsValue e2cspv = enhancedCollaborativeSessionValue
						.getCollaborativeSessionsPermissions()[z];
				// get the member accessControl flag
				int memberAccessControlIntValue = e2cspv.getAccessControl()
						.intValue();
				// ignore the loggedin flag
				if (memberAccessControlIntValue >= PermissionFlags.LOGGEDIN
						.intValue()) {
					memberAccessControlIntValue -= PermissionFlags.LOGGEDIN
							.intValue();
				}
				// if the user has no permission in this session- dont add him
				// to the members tree
				if (memberAccessControlIntValue != PermissionFlags.NULL_PERMISSIONS
						.intValue()) {
					MemberTreeNode memberTreeNode = new MemberTreeNode(e2cspv);
					this.addNodeToMembersTree(memberTreeNode);
				}
			}

			this.userView.getViewport().remove(this.membersTree);
			this.membersTree = new JTree(this.membersTreeModel);
			this.membersTree.setCellRenderer(new IconCellRenderer());

			// Listen for when the selection changes.
			this.membersTree
					.addTreeSelectionListener(new TreeSelectionListener() {
						public void valueChanged(TreeSelectionEvent e) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) Admin.this.membersTree
									.getLastSelectedPathComponent();

							if (node == null) {
								return;
							}

							Object obj = node.getUserObject();
							if (obj instanceof MemberTreeNode) {
								// MemberTreeNode memberTreeNode =
								// (MemberTreeNode)obj;
								// memberTreeNode.getMemberInfo().setAccessControl(PermissionFlags.
								// LOGGEDIN);
								// membersTreeModel.nodeChanged(node);
							}
						}
					});

			this.userView.getViewport().add(this.membersTree, null);
			this.presenceWindowLabel.setEnabled(true);

		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

	public void removeMembersTree() {
		this.userView.getViewport().remove(this.membersTree);
		this.membersTreeModel = null;
		this.membersTree = new JTree(this.membersTreeModel);
		this.userView.getViewport().add(this.membersTree, null);
		this.presenceWindowLabel.setEnabled(false);
	}

	// Set of functions to handle the current open session.
	public void updateActiveSessionOnPanel(String sessionName) {
		this.activeSessionText.setText(sessionName);
	}

	// The next functions update the New Token Holder on the Panel.
	// the first function get as a parameter the memberID
	public void updateTokenHolderOnPanel(int tokenHolderID) {
		String tokenHolderName = null;
		if (tokenHolderID == PermissionFlags.SERVER_USERID.intValue()) {
			this.tokenHolderText.setText("server");
			return;
		}
		tokenHolderName = this.myTeamMember.getLocalMemberName(tokenHolderID);
		if (tokenHolderName == null) {
			this.tokenHolderText.setText("");
		} else {
			this.tokenHolderText.setText(tokenHolderName);
		}
	}

	// the second function get as a parameter the name of the token holder
	public void updateTokenHolderOnPanel(String tokenHolderName) {
		if (tokenHolderName == null) {
			this.tokenHolderText.setText("");
		} else {
			this.tokenHolderText.setText(tokenHolderName);
		}
	}

}// end of class Admin

// class for workgroup node in the tree
class WorkgroupTreeNode extends Object {
	EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

	public WorkgroupTreeNode(
			EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue) {
		this.myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
	}

	public String toString() {
		return this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getWorkgroupName();
	}

	public EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsValue() {
		return this.myEnhancedWorkgroupPermissionsValue;
	}
}

// class for opmmodel node in the tree
class OpmModelTreeNode extends Object {
	EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

	public OpmModelTreeNode(
			EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue) {
		this.myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;
	}

	public String toString() {
		return this.myEnhancedOpmModelPermissionsValue.getOpmModel()
				.getOpmModelName();
	}

	public EnhancedOpmModelPermissionsValue getEnhancedOpmModelPermissionsValue() {
		return this.myEnhancedOpmModelPermissionsValue;
	}
}

// class for session node in the tree
class CollaborativeSessionTreeNode extends Object {
	EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

	public CollaborativeSessionTreeNode(
			EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue) {
		this.myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;
	}

	public String toString() {
		return this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSession().getCollaborativeSessionName();
	}

	public EnhancedCollaborativeSessionPermissionsValue getEnhancedCollaborativeSessionPermissionsValue() {
		return this.myEnhancedCollaborativeSessionPermissionsValue;
	}
}

// class for user data in the tree
class UserTreeNode extends Object {
	String userName;

	public UserTreeNode(TeamMember teamMember) {
		this.userName = teamMember.getLocalEnhancedUser().getFirstName() + " "
				+ teamMember.getLocalEnhancedUser().getLastName();
	}

	public String toString() {
		return this.userName;
	}
}

// class for member data in the tree
class MemberTreeNode extends Object {
	Enhanced2CollaborativeSessionPermissionsValue myMemberInfo;

	public MemberTreeNode(
			Enhanced2CollaborativeSessionPermissionsValue memberInfo) {
		this.myMemberInfo = memberInfo;
	}

	public String toString() {
		return (this.myMemberInfo.getUser().getFirstName() + " " + this.myMemberInfo
				.getUser().getLastName());
	}

	public Enhanced2CollaborativeSessionPermissionsValue getMemberInfo() {
		return this.myMemberInfo;
	}
}
