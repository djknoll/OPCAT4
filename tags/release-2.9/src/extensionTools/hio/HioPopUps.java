package extensionTools.hio;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.images.opm.OPMImages;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;

/**
 * <p>
 * Title: class hioPopUps
 * </p>
 * <p>
 * Description: opens pop up toolbars for object or state in zoomIn or
 * fullRelation popup
 * </p>
 */

public class HioPopUps extends JPopupMenu {
	 

	/**
	 * 
	 */
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * private OpdProject myProject holds the current project
	 */
	private OpdProject myProject = extensionTools.hio.OpenDrawInOpcat.myProject;// holds

	// the
	// current
	// project

	/**
	 * private IXConnectionEdgeInstance openIn indecates where to open the draw
	 */
	private IXConnectionEdgeInstance openIn = OpenDrawInOpcat.openIn;// indecates

	// where
	// to
	// open
	// the
	// draw

	/**
	 * private Point openAt indecates where to open the draw
	 */
	private Point openAt = OpenDrawInOpcat.openAt;// indecates where to open

	// the draw

	/**
	 * private int relationType indecates what kind of relation was recognized
	 */
	private int relationType;// indecates what kind of relation was

	// recognized

	/**
	 * constructor of hioPopUps
	 * 
	 * @param menuKind
	 *            if 0 fullRelation popup, else state/object popup
	 */
	public HioPopUps(int menuKind) {
		// popup for relation
		if (menuKind == 0) {
			// add buttons for the following relations
			this.add(this.apAction);
			this.add(this.fcAction);
			this.add(this.ciAction);
		}

		// popup for state/object
		if (menuKind == 1) {
			// add buttons for object and state
			this.add(this.objAction);
			this.add(this.stateAction);
		}

	}

	/**
	 * the function openFundamentalRelation opens a chosen relation and enables
	 * undo/redo and delete
	 */
	private void openFundamentalRelation() {
		FundamentalRelationInstance relation = (FundamentalRelationInstance) // the
		// type
		// of
		// relation
		// is
		// FundamentalRelation
		OpenDrawInOpcat.openRelation(this.relationType);
		OpenDrawInOpcat.enableFundamentalRelation(relation);
	}

	// the action performed when pressig on the popup buttons
	Action apAction = new AbstractAction("Agregation-Particulation",
			OPMImages.AGGREGATION) {

		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

		/**
				 * 
				 */
				 

		public void actionPerformed(ActionEvent e) {
			HioPopUps.this.relationType = 204;
			HioPopUps.this.openFundamentalRelation();
		}

	};

	Action fcAction = new AbstractAction("Featuring-Characterization",
			OPMImages.EXHIBITION) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -6578654403274947155L;

		public void actionPerformed(ActionEvent e) {
			HioPopUps.this.relationType = 202;
			HioPopUps.this.openFundamentalRelation();
		}
	};

	Action ciAction = new AbstractAction("Classification-Instantiation",
			OPMImages.INSTANTIATION) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -183754983440898904L;

		public void actionPerformed(ActionEvent e) {
			HioPopUps.this.relationType = 203;
			HioPopUps.this.openFundamentalRelation();
		}
	};

	// action performed for an object
	Action objAction = new AbstractAction("Object", OPMImages.OBJECT) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 7937864731663790474L;

		public void actionPerformed(ActionEvent e) {
			OpmObject sampleObjOpm = new OpmObject(0, "");
			ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm,
					HioPopUps.this.myProject);
			ObjectInstance sampleObjInstance = new ObjectInstance(
					sampleObjEntry, new OpdKey(0, 0), null, HioPopUps.this.myProject, false);
			OpdObject sampleObjOpd = (OpdObject) sampleObjInstance
					.getConnectionEdge();
			ObjectPropertiesDialog od = new ObjectPropertiesDialog(
					sampleObjOpd, sampleObjEntry, HioPopUps.this.myProject, true); // creates
			// properties
			// dialog

			if (od.showDialog()) {
				ObjectInstance currObject = (ObjectInstance) HioPopUps.this.myProject
						.addObject(od.getName(), (int) HioPopUps.this.openAt.getX(),
								(int) HioPopUps.this.openAt.getY(), (IXThingInstance) HioPopUps.this.openIn);
				currObject
						.setLocation((int) HioPopUps.this.openAt.getX(), (int) HioPopUps.this.openAt.getY());
				OpenDrawInOpcat.enableObject(currObject, sampleObjOpm,
						sampleObjInstance);
			}

		}
	};

	// action performed for a state
	Action stateAction = new AbstractAction("State", OPMImages.STATE) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -2750319495206407582L;

		public void actionPerformed(ActionEvent e) {
			OpenDrawInOpcat.openState();
		}
	};

}