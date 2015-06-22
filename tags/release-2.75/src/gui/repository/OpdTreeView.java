package gui.repository;

import gui.images.repository.RepositoryImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class OpdTreeView extends BaseView
{
	public OpdTreeView(String invisibleRoot)
	{
		super(invisibleRoot);
		tip = new String("OPD Tree view");
		viewName = "OPD Tree";

//		icon = RepositoryImages.OPD;
        icon = RepositoryImages.TREE;
	}

	public void rebuildTree(MainStructure s, OpdProject rootComponent)
	{
		super.rebuildTree(s, rootComponent);
		DefaultTreeModel model = (DefaultTreeModel)getModel();
		DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode)((DefaultMutableTreeNode)model.getRoot()).getChildAt(0);

        _scanOpdTreeDFS(s, model, -1, prjRoot);

		setExpandedDescendants();
	}

    private void _scanOpdTreeDFS(MainStructure s,
                                 DefaultTreeModel model,
                                 long rootOpdId,
                                 DefaultMutableTreeNode parentNode)
    {
        DefaultMutableTreeNode tmpOpdNode;
   		TreeMap opdSorter = new TreeMap();
        Opd tmpParentOpd;
        Object tmpObj;
        long parentOpdId;

        // get all opds and select those that have OPD identified by rootOpdId
		for (Enumeration e = s.getOpds(); e.hasMoreElements();)
		{
			Object o = e.nextElement();
            tmpParentOpd = ((Opd)o).getParentOpd();

            // handle rootOpd case
            if(tmpParentOpd == null)
            {
                parentOpdId = -1;
            }
            else
            {
                parentOpdId = tmpParentOpd.getOpdId();
            }

            if( parentOpdId == rootOpdId)
            {
                opdSorter.put(o.toString(), o);
            }
        }

        // for each child: add to node
        //                 recursivly scan it's subtree
        for (Iterator i = opdSorter.values().iterator(); i.hasNext();)
		{
            // the next line is for 'setExpandedDescendants()'
            // we update parent when we know that is has children
            // see 'nodesHash' in 'BaseView'
            nodesHash.put( new HashKey(parentNode.getUserObject()), parentNode);

            tmpObj = i.next();
			tmpOpdNode = new DefaultMutableTreeNode(tmpObj);
        	model.insertNodeInto(tmpOpdNode, parentNode, parentNode.getChildCount());
        	
        	//Opening all the OPD folders in the tree - Lera
    		TreePath nodes = new TreePath(tmpOpdNode.getPath());
    		this.addSelectionPath(nodes);
    		//END
        	
            _scanOpdTreeDFS(s, model, ((Opd)tmpObj).getOpdId(),tmpOpdNode );
        }

        return;
    }
}


