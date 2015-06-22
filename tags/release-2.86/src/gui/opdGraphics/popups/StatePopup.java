package gui.opdGraphics.popups;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmState;
import gui.projectStructure.Instance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class StatePopup extends DefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	OpmState opmSt;

	public StatePopup(OpdProject prj, Instance inst) {
		super(prj, inst);
		JMenuItem jmi;
		StateInstance stInstance = (StateInstance) (this.myProject.getCurrentOpd()
				.getSelectedItem());
		this.opmSt = ((OpmState) stInstance.getState().getEntity());

		if (((StateEntry) stInstance.getEntry()).isRelated()) {
			this.deleteAction.setEnabled(false);
		}
		if (stInstance.isRelated()) {
			this.hideAction.setEnabled(false);
		}
		this.add(this.deleteAction);
		this.add(this.hideAction);
		this.add(new JSeparator());
		jmi = new JMenuItem(this.setInitialAction);
		if (this.opmSt.isInitial()) {
			jmi.setIcon(StandardImages.CHECKED);
		} else {
			jmi.setIcon(StandardImages.UNCHECKED);
		}
		this.add(jmi);
		jmi = new JMenuItem(this.setFinalAction);
		if (this.opmSt.isFinal()) {
			jmi.setIcon(StandardImages.CHECKED);
		} else {
			jmi.setIcon(StandardImages.UNCHECKED);
		}
		this.add(jmi);
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
			((StateInstance) (StatePopup.this.myProject.getCurrentOpd().getSelectedItem()))
					.getState().callPropertiesDialog(
							BaseGraphicComponent.SHOW_ALL_TABS,
							BaseGraphicComponent.SHOW_ALL_BUTTONS);
			StatePopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action setInitialAction = new AbstractAction("Initial") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 7188983139912268967L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.opmSt.setInitial(!StatePopup.this.opmSt.isInitial());
			StatePopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
	};

	Action setFinalAction = new AbstractAction("Final") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 270733455132914076L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.opmSt.setFinal(!StatePopup.this.opmSt.isFinal());
			StatePopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
	};

	Action hideAction = new AbstractAction("Hide state", StandardImages.EMPTY) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -9051346513385775950L;

		public void actionPerformed(ActionEvent e) {
			((StateInstance) (StatePopup.this.myProject.getCurrentOpd().getSelectedItem()))
					.getState().setVisible(false);
			StatePopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

}