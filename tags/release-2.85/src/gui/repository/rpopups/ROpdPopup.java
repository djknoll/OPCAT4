package gui.repository.rpopups;

import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;

public class ROpdPopup extends RDefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	Opd myOpd;

	public ROpdPopup(BaseView view, OpdProject prj) {
		super(view, prj);
		this.myOpd = (Opd) this.userObject;
		this.add(this.showAction);
		this.add(new JSeparator());
		this.add(this.deleteAction);
		// add(new JSeparator());
		// add(generateOPLAction);
		this.addCollapseExpand();
	}

	Action showAction = new AbstractAction("Show OPD", RepositoryImages.OPD) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		 

		public void actionPerformed(ActionEvent e) {
			ROpdPopup.this.myOpd.setVisible(true);
			try {
				ROpdPopup.this.myOpd.getOpdContainer().setSelected(true);
				ROpdPopup.this.myOpd.getOpdContainer().setMaximum(true);
			} catch (java.beans.PropertyVetoException pve) {
				pve.printStackTrace();
			}
		}
	};

	Action deleteAction = new AbstractAction("Delete OPD",
			StandardImages.DELETE) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 8446841991771412186L;

		public void actionPerformed(ActionEvent e) {
			// JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option not
			// impemented yet", "Opcat2 - tmp message",
			// JOptionPane.INFORMATION_MESSAGE);
			ROpdPopup.this.myProject.deleteOpd(ROpdPopup.this.myOpd);
		}
	};

	// Action pasteAction = new AbstractAction("Paste", StandardImages.PASTE){
	// public void actionPerformed(ActionEvent e){
	// myProject.paste(x, y,myProject.getCurrentOpd().getDrawingArea());
	// myProject.getCurrentOpd().getDrawingArea().repaint();
	// }
	// };

}