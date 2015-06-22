package gui.opdGraphics.popups;

import extensionTools.validator.recommender.Recommender;
import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Instance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JSeparator;

public class ThingPopup extends DefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private ThingInstance ti;

	public ThingPopup(OpdProject prj, Instance inst, int x, int y) {
		super(prj, inst);
		this.ti = (ThingInstance) inst;// (ThingInstance)myProject.getCurrentOpd().getSelectedItem();
		this.add(this.showAction);
		this.add(new JSeparator());

		this.add(this.unfoldingAction);
		this.add(this.zoomInAction);
		this.add(new JSeparator());

		this.add(this.copyAction);

		this.add(this.cutAction);
		// System.err.println(ti);

		if (!(this.ti.getThing().isZoomedIn())) {
			this.pasteAction.setEnabled(false);
		}
		// add(pasteAction);
		this.add(this.pasteAction);

		this.add(this.deleteAction);
		this.add(new JSeparator());

		this.add(this.bringToFrontAction);
		this.add(this.sendToBackAction);
		this.add(this.fitToContent);

		JMenu alingMenu = new JMenu("Align");
		alingMenu.setIcon(StandardImages.EMPTY);
		alingMenu.add(this.alignLeftAction);
		alingMenu.add(this.alignRightAction);
		alingMenu.add(this.alignTopAction);
		alingMenu.add(this.alignBottomAction);
		alingMenu.add(this.alignCenterVerticallyAction);
		alingMenu.add(this.alignCenterHorizontallyAction);
		alingMenu.add(new JSeparator());
		alingMenu.add(this.alignAsTreeAction);

		this.add(alingMenu);
		if ((this.myProject.getCurrentOpd() == null)
				|| (this.myProject.getCurrentOpd().getSelectedItemsHash().size() < 2)) {
			alingMenu.setEnabled(false);
		}
		this.add(new JSeparator());
		this.add(this.propertiesAction);

		// Meta-Libraries based advisor - by Eran Toch
		this.add(new JSeparator());
		Recommender rec = new Recommender(this.ti, this.myProject);
		this.add(rec.getAdvices());
	}

	Action showAction = new AbstractAction("Show Instances",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

		/**
				 * 
				 */
				 

		public void actionPerformed(ActionEvent e) {
			ThingEntry.getEntryFromOpmThing(
					(OpmThing) ThingPopup.this.ti.getEntry().getLogicalEntity())
					.ShowInstances();
		}
	};

	Action zoomInAction = new AbstractAction("In-Zooming", StandardImages.EMPTY) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 7921410206025464287L;

		public void actionPerformed(ActionEvent e) {
			try {

				ThingPopup.this.myProject.zoomIn();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	};

	Action unfoldingAction = new AbstractAction("Unfolding",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -667563739626134447L;

		public void actionPerformed(ActionEvent e) {
			try {
				ThingPopup.this.myProject.unfolding();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	};

	Action alignLeftAction = new AbstractAction("Left") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -5483421693041278633L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2Left();
		}
	};

	Action alignRightAction = new AbstractAction("Right") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 239503285663982807L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2Right();
		}
	};

	Action alignTopAction = new AbstractAction("Top") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 2305020403623966419L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2Top();
		}
	};

	Action alignBottomAction = new AbstractAction("Bottom") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 7814303177070480931L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2Bottom();
		}
	};

	Action alignCenterVerticallyAction = new AbstractAction("Center Vertically") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1316613334680687140L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2CenterVertically();
		}
	};

	Action alignCenterHorizontallyAction = new AbstractAction(
			"Center Horizontally") {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 6662070932673168893L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().align2CenterHorizontally();
		}
	};

	Action alignAsTreeAction = new AbstractAction("As Tree") {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -5193633177133700618L;

		public void actionPerformed(ActionEvent e) {
			ThingPopup.this.myProject.getLayoutManager().alignAsTree();
		}
	};

	Action propertiesAction = new AbstractAction("Properties",
			StandardImages.PROPERTIES) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1764163162957061291L;

		public void actionPerformed(ActionEvent e) {
			((ThingInstance) (ThingPopup.this.myProject.getCurrentOpd().getSelectedItem()))
					.getThing().callPropertiesDialog(
							BaseGraphicComponent.SHOW_ALL_TABS,
							BaseGraphicComponent.SHOW_ALL_BUTTONS);
			ThingPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();

		}
	};

	Action bringToFrontAction = new AbstractAction("Bring To Front",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -7919648834938175001L;

		public void actionPerformed(ActionEvent e) {
			OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
			obc.bringToFront();
		}
	};

	Action sendToBackAction = new AbstractAction("Send To Back",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -381895123736075568L;

		public void actionPerformed(ActionEvent e) {
			OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
			obc.sendToBack();
		}
	};

	Action fitToContent = new AbstractAction("Fit To Content",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 879338001685397770L;

		public void actionPerformed(ActionEvent e) {
			OpdConnectionEdge oce = ThingPopup.this.ti.getConnectionEdge();
			oce.fitToContent();

		}
	};

}