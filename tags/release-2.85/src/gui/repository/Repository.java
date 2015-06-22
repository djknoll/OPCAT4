package gui.repository;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.util.OpcatLogger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import org.objectprocess.Client.TeamMember;

public class Repository extends JPanel implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	Vector trees;

	JTabbedPane tabbedPane;

	OpdProject project = null;

	BaseView currentView;

	TreeSelectionListener opdSelectionEvent = null;

	TreeSelectionListener thingSelectionEvent = null;

	public final static int OPDVIEW = 1;

	public final static int OPDThingsVIEW = 2;

	public final static int ThingsVIEW = 3;
	
	public final static int MetaVIEW = 4;	

	// OPCATeam
	protected TeamMember myTeamMember = null;

	protected org.objectprocess.team.Admin admin = null;

	protected JPanel opcaTeamPanel = new JPanel();

	protected Opcat2 myOpcat2;

	// known views
	OpdThingsView opdThingsView;

	OpdView opdView;

	ThingView thingView;

	MetaView metaView;

	public Repository(OpdProject prj, Opcat2 opcat2) {
		this.project = prj;
		this.tabbedPane = new JTabbedPane(SwingConstants.BOTTOM,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		this.setLayout(new BorderLayout());
		this.tabbedPane.setPreferredSize(new Dimension(180, 200));
		this.tabbedPane.addChangeListener(this);
		this.add(this.tabbedPane, BorderLayout.CENTER);
		this.trees = new Vector();
		this.myOpcat2 = opcat2;
		DefaultMutableTreeNode p = null;

		// creating opdThingView
		if (this.project == null) {
			this.opdThingsView = new OpdThingsView("root"); // this is not
															// visible
			this.opdView = new OpdView("root"); // this is not visible
			this.thingView = new ThingView("root"); // this is not visible
			this.metaView = new MetaView("root"); // this is not visible
		} else {
			this.opdThingsView = new OpdThingsView("root"); // this is not
															// visible
			DefaultTreeModel model = (DefaultTreeModel) this.opdThingsView
					.getModel();
			model.insertNodeInto(new DefaultMutableTreeNode(this.project),
					(MutableTreeNode) model.getRoot(), 0);

			this.thingView = new ThingView("root"); // this is not visible
			model = (DefaultTreeModel) this.thingView.getModel();
			model.insertNodeInto(new DefaultMutableTreeNode(this.project),
					(MutableTreeNode) model.getRoot(), 0);

			this.opdView = new OpdView("root"); // this is not visible
			this.metaView = new MetaView("root");
			model = (DefaultTreeModel) this.opdView.getModel();
			p = new DefaultMutableTreeNode(this.project);
			model.insertNodeInto(p, (MutableTreeNode) model.getRoot(), 0);
			this.currentView = this.opdThingsView;

		}

		this.opdThingsView.addMouseListener(new RepositoryMouseListener(
				this.opdThingsView, this));
		this.opdView.addMouseListener(new RepositoryMouseListener(this.opdView,
				this));
		this.thingView.addMouseListener(new RepositoryMouseListener(
				this.thingView, this));
		this.metaView.addMouseListener(new RepositoryMouseListener(
				this.metaView, this));
		this.trees.add(this.opdView);
		this.trees.add(this.opdThingsView);
		this.trees.add(this.thingView);
		this.trees.add(this.metaView);

		// end creating opdThingView

		JScrollPane scroller;
		int i = 0;
		// for (int i = 0; i < trees.size(); i++)
		// {
		scroller = new JScrollPane((BaseView) this.trees.elementAt(i),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// views shold be added to tabbed pane exectly in the same order they
		// appear in
		// 'trees' vector. It used for switching views
		this.tabbedPane.addTab(
				((BaseView) (this.trees.elementAt(i))).getName(),
				((BaseView) (this.trees.elementAt(i))).getIcon(),
				// (BaseView)(trees.elementAt(i)),
				scroller, ((BaseView) (this.trees.elementAt(i))).getTip());
		// }

		// opdSelectionEvent = new SelectionEvent(opdView, this) ;
		// thingSelectionEvent = new SelectionEvent(opdThingsView , this) ;
	}

	public void changeView(int i) {
		JScrollPane scroller;

		// index is the order inwhich Views where added to the trees vector.
		// need to refactor this.

		int index = 0;

		this.tabbedPane.removeAll();
		switch (i) {
		case OPDVIEW:
			index = 0;
			break;
		case OPDThingsVIEW:
			index = 1;
			break;
		case ThingsVIEW:
			index = 2;
			break;
		case MetaVIEW:
			index = 3;
			break;			
		}

		this.currentView = (BaseView) this.trees.elementAt(index);

		scroller = new JScrollPane(this.currentView,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// views shold be added to tabbed pane exectly in the same order they
		// appear in
		// 'trees' vector. It used for switching views
		this.tabbedPane.addTab(this.currentView.getName(), this.currentView
				.getIcon(), scroller, this.currentView.getTip());
		this.rebuildTrees();
		this.tabbedPane.updateUI();

	}

	public void rebuildTrees() {
		if (this.project == null) {
			for (Enumeration e = this.trees.elements(); e.hasMoreElements();) {
				BaseView view = (BaseView) e.nextElement();
				view.clearTree();
			}
			return;
		}

		// rescan current view only !!!!
		if (this.currentView == null) {
			this.currentView = this.opdView;
		}
		this.currentView.rebuildTree(this.project.getComponentsStructure(),
				this.project);

		// opdTreeView.getSelectionModel().removeTreeSelectionListener(opdSelectionEvent);
		// opdTreeView.getSelectionModel().addTreeSelectionListener(opdSelectionEvent)
		// ;
	}

	public void setProject(OpdProject prj) {
		this.project = prj;
		this.rebuildTrees();
	}

	public OpdProject getProject() {
		return this.project;
	}

	public void clearHistory() {
		for (Enumeration e = this.trees.elements(); e.hasMoreElements();) {
			BaseView view = (BaseView) e.nextElement();
			view.clearHistory();
		}
	}

	public void stateChanged(ChangeEvent ce) {
	}

	// OPCATeam functions
	public void addOPCATeamTab() {
		this.tabbedPane.addTab("OPCATeam", OPCATeamImages.PEOPLEVerySmall,
				this.opcaTeamPanel, "Team Support Tab");
	}

	public void removeOPCATeamTab() {
		this.opcaTeamPanel.remove(this.admin);
		this.tabbedPane.remove(this.opcaTeamPanel);
		this.setAdmin(null);
	}

	public void initiateOPCATeamTab() {
		if (this.admin == null) {
			this.myTeamMember = this.myOpcat2.getTeamMember();
			this.admin = new org.objectprocess.team.Admin(this.myTeamMember,
					this.myOpcat2);
			BorderLayout bd = new BorderLayout();
			this.opcaTeamPanel.setLayout(bd);
			this.opcaTeamPanel.add(this.admin, BorderLayout.CENTER);
			this.opcaTeamPanel.setFocusable(true);
		}
	}

	public void refreshOPCATeamControlPanel() {
		try {
			this.myOpcat2.getTeamMember().initializeEnhancedUserValue();
			this.myOpcat2.getRepository().getAdmin().refreshTree();
		} catch (Exception exception) {
			OpcatLogger.logError(exception);
		}
	}

	public org.objectprocess.team.Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(org.objectprocess.team.Admin newAdmin) {
		this.admin = newAdmin;
	}

	public JTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}

	public OpdThingsView getOpdThingView() {
		return this.opdThingsView;
	}

	public OpdView getOpdTreeView() {
		return this.opdView;
	}
	
	public MetaView getMetaView() {
		return this.metaView;
	}	
}

class SelectionEvent implements TreeSelectionListener {

	SelectionEvent(BaseView tree, Repository rep) {
	}

	public void valueChanged(TreeSelectionEvent event) {

		TreePath newSelPath = event.getNewLeadSelectionPath();

		DefaultMutableTreeNode newNode;

		if (newSelPath != null) {
			newNode = (DefaultMutableTreeNode) (newSelPath
					.getLastPathComponent());
			Object obj = newNode.getUserObject();
			if (obj instanceof Opd) {
				this.showOpd((Opd) obj);
			}
		}

		// IconCellRenderer renderer = myTree.getCellRenderer() ;

	}

	private void showOpd(Opd myOpd) {
		myOpd.setVisible(true);
		try {
			myOpd.getOpdContainer().setSelected(true);
			myOpd.getOpdContainer().setMaximum(true);
		} catch (java.beans.PropertyVetoException pve) {
			pve.printStackTrace();
		}
	}
}