package gui.repository;


import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.team.ExceptionHandler;

public class Repository extends JPanel implements ChangeListener
{
		Vector trees;
		JTabbedPane tabbedPane;
		OpdProject project = null;
		BaseView currentView;
		TreeSelectionListener  opdSelectionEvent = null ;
		TreeSelectionListener  thingSelectionEvent = null ; 
		

		//OPCATeam
		protected TeamMember myTeamMember = null;
		protected org.objectprocess.team.Admin admin = null;
		protected JPanel opcaTeamPanel = new JPanel();
		protected Opcat2 myOpcat2;

		// known views
	OpdThingView opdThingView;
	OpdTreeView opdTreeView;


	public Repository(OpdProject prj,Opcat2 opcat2)
	{
		project = prj;
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		setLayout(new BorderLayout());
		tabbedPane.setPreferredSize(new Dimension(180, 200));
				tabbedPane.addChangeListener(this);
		this.add(tabbedPane, BorderLayout.CENTER);
		trees = new Vector();
		myOpcat2 = opcat2;
		DefaultMutableTreeNode p = null;

		// creating opdThingView
		if(project == null)
		{
				  opdThingView = new OpdThingView("root"); // this is not visible
				  opdTreeView = new OpdTreeView("root"); // this is not visible
		}
		else
		{
				  opdThingView = new OpdThingView("root"); // this is not visible
				  DefaultTreeModel model = (DefaultTreeModel)opdThingView.getModel();
				  model.insertNodeInto(new DefaultMutableTreeNode(project), (MutableTreeNode)model.getRoot(), 0);

				  opdTreeView = new OpdTreeView("root"); // this is not visible
				  model = (DefaultTreeModel)opdTreeView.getModel();
				  p = new DefaultMutableTreeNode(project);
				  model.insertNodeInto(p, (MutableTreeNode)model.getRoot(), 0);
				  currentView = opdThingView;
		}
		
		opdThingView.addMouseListener(new RepositoryMouseListener(opdThingView, this));
		opdTreeView.addMouseListener(new RepositoryMouseListener(opdTreeView, this));
		
		
		trees.add(opdTreeView);
		trees.add(opdThingView);
		
		// end creating opdThingView
	
		JScrollPane scroller;
		for (int i = 0; i < trees.size(); i++)
		{
				  scroller = new JScrollPane((BaseView)trees.elementAt(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				  // views shold be added to tabbed pane exectly in the same order they appear in
				  // 'trees' vector. It used for switching views
				  tabbedPane.addTab(  ((BaseView)(trees.elementAt(i))).getName(),
									  ((BaseView)(trees.elementAt(i))).getIcon(),
									  //(BaseView)(trees.elementAt(i)),
									  scroller,
									  ((BaseView)(trees.elementAt(i))).getTip());
		}
		
		opdSelectionEvent = new SelectionEvent(opdTreeView, this) ;
		thingSelectionEvent = new SelectionEvent(opdThingView , this) ;
	}


	public void rebuildTrees()
	{
		if(project == null)
		{
				  for(Enumeration e = trees.elements(); e.hasMoreElements();)
				  {
					BaseView view = (BaseView)e.nextElement();
					view.clearTree();
		  }
				  return;
		}

		// rescan current view only !!!!
		currentView.rebuildTree(project.getComponentsStructure(), project);
		
		//opdTreeView.getSelectionModel().removeTreeSelectionListener(opdSelectionEvent); 
		//opdTreeView.getSelectionModel().addTreeSelectionListener(opdSelectionEvent) ;			
	}

	public void setProject(OpdProject prj)
	{
		project = prj;
		rebuildTrees();
	}

	public OpdProject getProject()
	{
		return project;
	}

	public void clearHistory()
	{
	  for(Enumeration e = trees.elements(); e.hasMoreElements();)
		{
			BaseView view = (BaseView)e.nextElement();
			view.clearHistory();
		}
	}

	public void stateChanged(ChangeEvent ce)
	{
		if(ce.getSource() instanceof JTabbedPane)
		{
		  //OPCAteam
		  if(2 == tabbedPane.getSelectedIndex()) {
			return;
		  }
		  // we suppose the order in 'trees' vector is identical to order
		  // in 'tabbedPane'
		  currentView = (BaseView)trees.elementAt(tabbedPane.getSelectedIndex());
		  rebuildTrees();
		}
		return;
	}

//OPCATeam functions
	public void addOPCATeamTab() {
	  tabbedPane.addTab("OPCATeam",OPCATeamImages.PEOPLEVerySmall,opcaTeamPanel,"Team Support Tab");
	}

	public void removeOPCATeamTab() {
	  opcaTeamPanel.remove(admin);
	  tabbedPane.remove(opcaTeamPanel);
	  setAdmin(null);
	}

	public void initiateOPCATeamTab() {
	  if (admin == null) {
		myTeamMember = myOpcat2.getTeamMember();
		admin = new org.objectprocess.team.Admin(myTeamMember,myOpcat2);
		BorderLayout bd = new BorderLayout();
		opcaTeamPanel.setLayout(bd);
		opcaTeamPanel.add(admin,bd.CENTER);
		opcaTeamPanel.setFocusable(true);
	  }
	}

	public void refreshOPCATeamControlPanel(){
	  try {
		myOpcat2.getTeamMember().initializeEnhancedUserValue();
		myOpcat2.getRepository().getAdmin().refreshTree();
	  }catch (Exception exception) {
		ExceptionHandler exceptionHandler = new  ExceptionHandler(exception);
	  }
	}

	public org.objectprocess.team.Admin getAdmin() {
		return admin;
	}

	public void setAdmin(org.objectprocess.team.Admin newAdmin) {
		admin = newAdmin;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}


	public OpdThingView getOpdThingView() {
		return opdThingView;
	}


	public OpdTreeView getOpdTreeView() {
		return opdTreeView;
	}
}

class SelectionEvent implements TreeSelectionListener {
	private BaseView myTree ; 
	private Repository repo ; 
	
	SelectionEvent(BaseView tree, Repository rep ) {
		myTree = tree; 
		repo = rep ; 
	}
	
	public void valueChanged(TreeSelectionEvent event) {
		
		TreePath newSelPath = event.getNewLeadSelectionPath() ;
		TreePath oldSelPath = event.getOldLeadSelectionPath() ;  
		
		DefaultMutableTreeNode newNode ; 
		DefaultMutableTreeNode oldNode ; 
		
		if (newSelPath != null ) {
			newNode = (DefaultMutableTreeNode)(newSelPath.getLastPathComponent());			
			Object obj = newNode.getUserObject(); 
			if(obj instanceof Opd) showOpd((Opd)obj);
		}
		
		if (oldSelPath != null ) {
			oldNode = (DefaultMutableTreeNode)(oldSelPath.getLastPathComponent());
		}
		
		
		//IconCellRenderer renderer =  myTree.getCellRenderer() ; 
		
	}
	
	private void showOpd(Opd myOpd)
	{
		myOpd.setVisible(true);
		try{
			myOpd.getOpdContainer().setSelected(true);
			myOpd.getOpdContainer().setMaximum(true);
		}catch(java.beans.PropertyVetoException pve)
		{
			pve.printStackTrace();
		}
	}	
}