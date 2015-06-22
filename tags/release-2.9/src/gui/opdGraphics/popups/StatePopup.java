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
		//StateInstance stInstance = (StateInstance) (this.myProject.getCurrentOpd()
		//		.getSelectedItem());
		
		StateInstance stInstance = (StateInstance) inst ; 
		
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
		///////////////////////////////
//		JMenu alingMenu = new JMenu("Align");
//		alingMenu.setIcon(StandardImages.EMPTY);
//		alingMenu.add(this.alignLeftAction);
//		alingMenu.add(this.alignRightAction);
//		alingMenu.add(this.alignTopAction);
//		alingMenu.add(this.alignBottomAction);
//		alingMenu.add(this.alignCenterVerticallyAction);
//		alingMenu.add(this.alignCenterHorizontallyAction);
//		alingMenu.add(new JSeparator());
//		alingMenu.add(this.alignAsTreeAction);
//
//		this.add(alingMenu);
//		if ((this.myProject.getCurrentOpd() == null)
//				|| (this.myProject.getCurrentOpd().getSelectedItemsHash()
//						.size() < 2)) {
//			alingMenu.setEnabled(false);
//		}
//		this.add(new JSeparator());
		//////////////////////////
		
		
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
			//StatePopup.this.myProject.getCurrentOpd().getSelectedItem()
			((StateInstance) (myInstance))
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
			//StatePopup.this.myProject.getCurrentOpd().getSelectedItem()
			((StateInstance) (myInstance))
					.getState().setVisible(false);
			StatePopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};
	
	
	////////////////////////////////
	Action alignLeftAction = new AbstractAction("Left") {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5483421693041278633L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().align2Left();
		}
	};

	Action alignRightAction = new AbstractAction("Right") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 239503285663982807L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().align2Right();
		}
	};

	Action alignTopAction = new AbstractAction("Top") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2305020403623966419L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().align2Top();
		}
	};

	Action alignBottomAction = new AbstractAction("Bottom") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7814303177070480931L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().align2Bottom();
		}
	};

	Action alignCenterVerticallyAction = new AbstractAction("Center Vertically") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1316613334680687140L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().evenSpaceVertically() ; 
					//.align2CenterVertically();
			
			
		}
	};

	Action alignCenterHorizontallyAction = new AbstractAction(
			"Center Horizontally") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6662070932673168893L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager()
					.align2CenterHorizontally();
		}
	};

	Action alignAsTreeAction = new AbstractAction("As Tree") {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5193633177133700618L;

		public void actionPerformed(ActionEvent e) {
			StatePopup.this.myProject.getLayoutManager().alignAsTree();
		}
	};
	///////////////////////////

}