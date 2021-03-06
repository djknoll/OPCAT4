package gui.repository;

import gui.images.opcaTeam.OPCATeamImages;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.scenarios.Scenario;
import gui.scenarios.Scenarios;
import gui.util.OpcatFile;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class IconCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public IconCellRenderer() {
	}

	/**
	 * Returns the color to use for the background if node is selected. LERA
	 */
	public Color getBackgroundSelectionColor() {
		return this.backgroundNonSelectionColor;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();

		this.setToolTipText(obj.toString());

		if (obj instanceof Opd) {
			if (expanded) {
				this.setIcon(RepositoryImages.OPD_OPEN);
			} else {
				this.setIcon(RepositoryImages.OPD);
			}
			return this;
		}

		if (obj instanceof ObjectInstance) {
			this.setIcon(RepositoryImages.OBJECT_MID);
			return this;
		}

		if (obj instanceof OpmObject) {
			this.setIcon(RepositoryImages.OBJECT_MID);
			return this;
		}

		if (obj instanceof ObjectEntry) {
			this.setIcon(RepositoryImages.OBJECT_MID);
			return this;
		}

		if (obj instanceof ProcessInstance) {
			this.setIcon(RepositoryImages.PROCESS_MID);
			return this;
		}

		if (obj instanceof OpmProcess) {
			this.setIcon(RepositoryImages.PROCESS_MID);
			return this;
		}

		if (obj instanceof ProcessEntry) {
			this.setIcon(RepositoryImages.PROCESS_MID);
			return this;
		}

		if (obj instanceof Scenario) {
			this.setIcon(OPCATeamImages.MODEL);
			return this;
		}

		if (obj instanceof Scenarios) {
			this.setIcon(OPCATeamImages.WORKGROUP);
			return this;
		}

		if ((obj instanceof OpcatFile) && (!((OpcatFile) obj).isDirectory())) {
			this.setIcon(StandardImages.LIBRARIES);
			return this;
		}

		if (obj instanceof MetaLibrary) {
			if (((MetaLibrary) obj).isPolicyLibrary()) {
				this.setText("<html><b>" + ((MetaLibrary) obj).getName()
						+ "</b></html>");
				this.setIcon(StandardImages.Policies);
			} else {
				this.setText(((MetaLibrary) obj).getName());
				this.setIcon(StandardImages.Classification);
			}
			return this;
		}

		if ((obj instanceof OpdProject) && (tree instanceof BaseView)) {
			this.setIcon(((BaseView) tree).getIcon());
		}

		return this;
	}
}
