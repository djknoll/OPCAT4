package gui.repository;

import gui.images.repository.RepositoryImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class IconCellRenderer extends DefaultTreeCellRenderer
{

	public IconCellRenderer(){}
	
	/**
     * Returns the color to use for the background if node is selected.
     * LERA
     */
   public Color getBackgroundSelectionColor() {
	return backgroundNonSelectionColor;
   }

	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setBackgroundNonSelectionColor(new Color(230, 230, 230));
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		Object obj = node.getUserObject();

		setToolTipText(obj.toString());
//		if (obj instanceof OpdProject)
//		{
//			setIcon(ICON_PROJECT);
//			return this;
//		}

		if (obj instanceof Opd)
		{
			setIcon(RepositoryImages.OPD);
			return this;
		}

		if (obj instanceof ObjectInstance)
		{
			setIcon(RepositoryImages.OBJECT);
			return this;
		}

		if (obj instanceof ProcessInstance)
		{
			setIcon(RepositoryImages.PROCESS);
			return this;
		}

        if (obj instanceof OpdProject && tree instanceof BaseView)
        {
            setIcon(((BaseView)tree).getIcon());
        }

//		if (obj instanceof String && ((String)obj).equals("Opcat Projects"))
//		{
//			setIcon(ICON_OPCAT_PROJECTS);
//			return this;
//		}

		return this;
	}
}
