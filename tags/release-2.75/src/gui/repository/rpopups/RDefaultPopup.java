package gui.repository.rpopups;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class RDefaultPopup extends JPopupMenu
{
	protected OpdProject myProject;
	protected BaseView view;
	protected TreePath selectedPath;
	protected DefaultTreeModel treeModel;
	protected Object userObject;

	public RDefaultPopup(BaseView view, OpdProject prj)
	{
		//super(((ThingInstance)(prj.getCurrentOpd().getSelectedItem())).getThing().getEntity().getEntityName());
		//setSelectionModel(new MySingleSelectionModel());
		myProject = prj;
		this.view = view;
		selectedPath = view.getSelectionPath();
		treeModel = (DefaultTreeModel)view.getModel();
		userObject = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent()).getUserObject();
	}

	Action expandAction = new AbstractAction("Expand", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
		   view.expandPath(selectedPath);
		}
	};

	Action collapseAction = new AbstractAction("Collapse", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
		   view.collapsePath(selectedPath);
		}
	};

	protected void addCollapseExpand()
	{
		if(!treeModel.isLeaf(selectedPath.getLastPathComponent()))
		{
            add(new JSeparator());
			if(view.isExpanded(selectedPath))
			{
				add(collapseAction);
			}
			else // collapsed
			{
				add(expandAction);
			}
		}
	}

}