package gui.repository.rpopups;

import gui.controls.GuiControl;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.metaDataProject.MetaDataProject;
import gui.metaDataProject.MetaSourceType;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;
import gui.util.OpcatLogger;

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
		this.add(new JSeparator());
		this.add(this.showInGrid);
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
			// ROpdPopup.this.myOpd.setVisible(true);
			myProject.showOPD(myOpd.getOpdId());
			// try {
			// ROpdPopup.this.myOpd.getOpdContainer().setSelected(true);
			// ROpdPopup.this.myOpd.getOpdContainer().setMaximum(true);
			// } catch (java.beans.PropertyVetoException pve) {
			// pve.printStackTrace();
			// }
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
			GuiControl gui = GuiControl.getInstance();
			gui.getRepository().rebuildTrees(true);
			myProject.setCanClose(false);
		}
	};

	// Action pasteAction = new AbstractAction("Paste", StandardImages.PASTE){
	// public void actionPerformed(ActionEvent e){
	// myProject.paste(x, y,myProject.getCurrentOpd().getDrawingArea());
	// myProject.getCurrentOpd().getDrawingArea().repaint();
	// }
	// };

	Action showInGrid = new AbstractAction("Show in Grid", StandardImages.OPEN) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1774127394237777292L;

		public void actionPerformed(ActionEvent e) {
			try {
				Object[] source;
				source = new Object[2];
				source[0] = myProject;
				source[1] = new Long(myOpd.getOpdId());
				MetaDataProject metaData = new MetaDataProject(source,
						new MetaSourceType(MetaSourceType.OPCAT_OPD));
				MetaLibrary meta = new MetaLibrary(metaData);
				
				MetaLibrary oldOpdMete = myProject.getOpdMetaDataManager().findByPath(meta.getPath()) ; 
				if(oldOpdMete != null) {
					myProject.getOpdMetaDataManager().removeMetaLibrary(oldOpdMete.getPath()) ; 
				} 
				
				myProject.getOpdMetaDataManager().addMetaLibrary(meta);
				
				myProject.getOpdMetaDataManager().refreshRoles(myProject) ; 
				
				GuiControl file = GuiControl.getInstance();
				file.showMetaData(meta, metaData);
				// metadata.
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
			}
			myProject.showOPD(myOpd.getOpdId()); 
		}
	};

}