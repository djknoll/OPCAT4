package gui.opdGraphics.popups;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;

public class OpdPopup extends DefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public OpdPopup(OpdProject prj, int x, int y) {
		super(prj, null);
		this.add(this.pasteAction);
		this.add(this.deleteAction);
		// add(new JSeparator());
		// add(objectAction);
		// add(processAction);
		// add(new JSeparator());
		// generateOPLAction.setEnabled(false);
		// add(generateOPLAction);
		this.add(new JSeparator());
		this.add(this.propertiesAction);
	}

	Action propertiesAction = new AbstractAction("Properties",
			StandardImages.PROPERTIES) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

		/**
				 * 
				 */
				 

		public void actionPerformed(ActionEvent e) {
			ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(
					OpdPopup.this.myProject, Opcat2.getFrame(), "Project Properties");
			ppd.showDialog();
			OpdPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action deleteAction = new AbstractAction("Delete OPD",
			StandardImages.DELETE) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -1284971987046026420L;

		public void actionPerformed(ActionEvent e) {
			// JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option not
			// impemented yet", "Opcat2 - tmp message",
			// JOptionPane.INFORMATION_MESSAGE);
			OpdPopup.this.myProject.deleteOpd(OpdPopup.this.myProject.getCurrentOpd());
			// myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

}