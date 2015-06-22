package gui.repository;

import gui.images.repository.RepositoryImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.LibraryTypes;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.util.debug.Debug;
import gui.util.opcatGrid.GridPanel;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class RolesView extends BaseView  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	OpdProject myProject;

	Object[] row = { "", "", "" };

	Object[] tag = { "", "" };

	GridPanel results = null;

	public RolesView(String invisibleRoot) {
		super(invisibleRoot);
		this.tip = new String("Roles View");
		this.viewName = "Roles View";
		this.icon = RepositoryImages.TREE;
		this.setType(Repository.MetaVIEW) ; 

		Vector colNames = new Vector();
		colNames.add("Name");
		colNames.add("Description");
		colNames.add("URL");
		this.results = new GridPanel(3, colNames);
		this.results.getGrid().setDuplicateRows(false);
		this.results.getButtonPane().add(new JLabel(""));
		this.results.getButtonPane().add(new JLabel(""));
		this.results.getButtonPane().add(new JLabel(""));
		this.results.getButtonPane().add(new JLabel(""));
		this.results.getButtonPane().add(new JLabel(""));
	}

	public void rebuildTree(MainStructure s, OpdProject rootComponent) {

		super.rebuildTree(rootComponent);
		this.myProject = rootComponent;
		
		myProject.getMetaManager().setAllLibstoReferenced() ; 
		myProject.getMetaManager().refresh(myProject, new JProgressBar()) ;		

		DefaultTreeModel model = (DefaultTreeModel) this.getModel();

		DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) model
				.getRoot()).getChildAt(0);

		Iterator libreries = Collections.list(
				rootComponent.getMetaLibraries(LibraryTypes.MetaLibrary)).iterator();
		while (libreries.hasNext()) {
			
			TreeMap sorterHt = new TreeMap();
			
			MetaLibrary meta = (MetaLibrary) libreries.next();
			DefaultMutableTreeNode tmpOpdNode;
			tmpOpdNode = new DefaultMutableTreeNode(meta); ; 
			model.insertNodeInto(tmpOpdNode, prjRoot, prjRoot.getChildCount());

			
			// LERA
			TreePath nodes = new TreePath(tmpOpdNode.getPath());
			this.addSelectionPath(nodes);
			// END
			
			
			this.nodesHash.put(new HashKey(tmpOpdNode.getUserObject()),
					tmpOpdNode);

			// sort things
			try {
				Iterator e = meta.getRolesCollection().iterator();
				while (e.hasNext()) {
					Role role = (Role) e.next();
					//ThingEntry entry = role.getThingEntry() ; 
					
					Object ent =  role.getThing().getAdditionalData() ; 
					//System.out.println(ent.getClass().getCanonicalName() ) ; 
					//if (ent instanceof ThingEntry) {
						if (ent instanceof ProcessEntry) {
							ProcessEntry process = (ProcessEntry) ent ; 
							sorterHt.put("P" + process.getName(),ent);
						} else {
							ObjectEntry object = (ObjectEntry) ent ; 
							sorterHt.put("O" + object.getName(),ent);
						}
						
						//OpdProject pr = (OpdProject) meta.getProjectHolder();
						//pr.getMetaManager().get

					//}
				}				
			} catch (Exception ex) {
				Debug debug = Debug.getInstance() ; 
				debug.Print(meta, ex.getMessage(), "1") ; 
			}


			// put things into tree
			DefaultMutableTreeNode currFather;

			currFather = tmpOpdNode;

			for (Iterator i = sorterHt.values().iterator(); i.hasNext();) {
				model.insertNodeInto(new DefaultMutableTreeNode(i.next()),
						currFather, currFather.getChildCount());
			}
		}
		this.setExpandedDescendants();
	}

}
