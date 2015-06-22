package gui.repository;


import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * This is the base class for all possible views
 */
public abstract class BaseView extends JTree
{
	DefaultTreeModel treeModel;
	protected String viewName;
	protected String tip;
	protected Icon icon;
	protected final String fileSeparator = System.getProperty("file.separator");
	protected final String javaImagesPath = "images" + fileSeparator + "javaimages" + fileSeparator;
	protected boolean isSameProject = false;
	protected Enumeration expandedDescendants;
//	private JTree tmpTree; // for saving tree
	protected Hashtable nodesHash; // another representation of nodes in tree
								   // holds only non-leaf nodes

	public BaseView(String invisibleRoot)
	{
		super(new DefaultTreeModel(new DefaultMutableTreeNode(invisibleRoot)));
		setRootVisible(false);
		setBackground(new Color(230, 230, 230));
		ToolTipManager.sharedInstance().registerComponent(this);
		putClientProperty("JTree.lineStyle", "Angled");
		setShowsRootHandles(true);
		treeModel = (DefaultTreeModel)getModel();
		this.setCellRenderer(new IconCellRenderer());
	
		nodesHash = new Hashtable();
		//tmpTree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode(invisibleRoot)));
		toggleClickCount = 0;
	}

	public String getName()
	{
		return viewName;
	}

	public String getTip()
	{
		return tip;
	}

	public Icon getIcon()
	{
		return icon;
	}

    void clearHistory()
    {
        nodesHash.clear();
    }


	public void clearTree()
	{
		//treeModel = (DefaultTreeModel)this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
		if(!root.isLeaf() && root.getChildCount() > 0)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)root.getChildAt(0);
			int childIndexes[] = {0};
			Object removedChildren[] = {node.getUserObject()};
			node.removeFromParent();
			treeModel.nodesWereRemoved(root, childIndexes, removedChildren);
		}
	}

	public void setRootComponent(OpdProject c)
	{
		treeModel = (DefaultTreeModel)this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
		DefaultMutableTreeNode subRoot = new DefaultMutableTreeNode(c);
		treeModel.insertNodeInto(subRoot, root, 0);
		nodesHash.put(new HashKey(c), subRoot);
	}

	public void rebuildTree(MainStructure s, OpdProject rootComponent)
	{
		getExpandedDescendants(rootComponent);
		clearTree();
		setRootComponent(rootComponent);
	}

	/**
	 * Saves the expanded state in tmp tree
	 */
	protected void getExpandedDescendants(OpdProject rootComponent)
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();

		if(!root.isLeaf())
		{
			OpdProject prevProject = (OpdProject)((DefaultMutableTreeNode)root.getChildAt(0)).getUserObject();
			if(prevProject.getProjectId() == rootComponent.getProjectId())
			{
				isSameProject = true;
				expandedDescendants = super.getExpandedDescendants(new TreePath(root));
				clearToggledPaths();
			}
		}
	}

	protected void expandRoot()
	{
		TreePath rootPath = new TreePath(treeModel.getRoot());
		expandPath(rootPath);
	}

	// call this method if you want tree to be expanded as before rescaning
	protected void setExpandedDescendants()
	{
		expandRoot();
		if(isSameProject)
		{
			isSameProject = false;
			DefaultMutableTreeNode tmpNode, newNode;
			for(;expandedDescendants.hasMoreElements();)
			{
				tmpNode = (DefaultMutableTreeNode)((TreePath)expandedDescendants.nextElement()).getLastPathComponent();
				newNode = (DefaultMutableTreeNode)nodesHash.get(new HashKey(tmpNode.getUserObject()));
				if(newNode != null && !isExpanded(new TreePath(newNode.getPath())))
				{
                    //System.err.println("New node " + newNode);
					expandPath(new TreePath(newNode.getPath()));
				}
			}
		}
	}


//	public static DefaultMutableTreeNode findNodeInTree(JTree tree, DefaultMutableTreeNode n, Comparator c)
//	{
//		TreeModel model = tree.getModel();
//		return localFindNodeInTree((DefaultMutableTreeNode)model.getRoot(), n, c, model);
//	}
//
//	private static DefaultMutableTreeNode localFindNodeInTree(DefaultMutableTreeNode tree, DefaultMutableTreeNode n, Comparator c, TreeModel model)
//	{
//		DefaultMutableTreeNode tn = null;
//		for(Enumeration e = tree.children(); e.hasMoreElements();)
//		{
//			tn = (DefaultMutableTreeNode)e.nextElement();
//			if(c.compare(n, tn) == 0)
//			{
//				return tn;
//			}
//			if(!model.isLeaf(tn))
//			{
//				return localFindNodeInTree(tn, n, c, model);
//			}
//		}
//		return null;
//	}
//
//	private class NodesByNameComparator implements Comparator
//	{
//		public int compare(Object o1, Object o2)
//		{
//			DefaultMutableTreeNode n1 = (DefaultMutableTreeNode)o1;
//			DefaultMutableTreeNode n2 = (DefaultMutableTreeNode)o2;
//			if(n1.getUserObject().toString().equals(n2.getUserObject().toString()))
//			{
//				return 0;
//			}
//			return -1;
//		}
//	}
}
