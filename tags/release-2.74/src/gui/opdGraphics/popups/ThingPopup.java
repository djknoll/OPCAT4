package gui.opdGraphics.popups;

import extensionTools.validator.recommender.Recommender;
import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpdProject;
import gui.projectStructure.Instance;
import gui.projectStructure.ThingInstance;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JSeparator;

public class ThingPopup extends DefaultPopup {
	private int x;

	private int y;

	private ThingInstance ti;

	public ThingPopup(OpdProject prj, Instance inst, int x, int y) {
		super(prj, inst);
		ti = (ThingInstance) inst;//(ThingInstance)myProject.getCurrentOpd().getSelectedItem();
		this.x = x;
		this.y = y;

		add(unfoldingAction);
		add(zoomInAction);
		add(new JSeparator());

		add(copyAction);
		add(cutAction);
		//		System.err.println(ti);

		if (!(ti.getThing().isZoomedIn())) {
			pasteAction.setEnabled(false);
		}

		add(pasteAction);
		add(deleteAction);
		add(new JSeparator());

		add(bringToFrontAction);
		add(sendToBackAction);
		add(fitToContent);

		JMenu alingMenu = new JMenu("Align");
		alingMenu.setIcon(StandardImages.EMPTY);
		alingMenu.add(alignLeftAction);
		alingMenu.add(alignRightAction);
		alingMenu.add(alignTopAction);
		alingMenu.add(alignBottomAction);
		alingMenu.add(alignCenterVerticallyAction);
		alingMenu.add(alignCenterHorizontallyAction);
		alingMenu.add(new JSeparator());
		alingMenu.add(alignAsTreeAction);

		add(alingMenu);
		if (myProject.getCurrentOpd() == null
				|| myProject.getCurrentOpd().getSelectedItemsHash().size() < 2) {
			alingMenu.setEnabled(false);
		}
		add(new JSeparator());
		add(propertiesAction);

		//Meta-Libraries based advisor - by Eran Toch
		add(new JSeparator());
		Recommender rec = new Recommender(ti, myProject);
		add(rec.getAdvices());
	}

	Action zoomInAction = new AbstractAction("In-Zooming", StandardImages.EMPTY) {
		public void actionPerformed(ActionEvent e) {
			try {

				myProject.zoomIn();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	};

	Action unfoldingAction = new AbstractAction("Unfolding",
			StandardImages.EMPTY) {
		public void actionPerformed(ActionEvent e) {
			try {
				myProject.unfolding();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	};

	Action alignLeftAction = new AbstractAction("Left") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2Left();
		}
	};

	Action alignRightAction = new AbstractAction("Right") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2Right();
		}
	};

	Action alignTopAction = new AbstractAction("Top") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2Top();
		}
	};

	Action alignBottomAction = new AbstractAction("Bottom") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2Bottom();
		}
	};

	Action alignCenterVerticallyAction = new AbstractAction("Center Vertically") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2CenterVertically();
		}
	};

	Action alignCenterHorizontallyAction = new AbstractAction(
			"Center Horizontally") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().align2CenterHorizontally();
		}
	};

	Action alignAsTreeAction = new AbstractAction("As Tree") {
		public void actionPerformed(ActionEvent e) {
			myProject.getLayoutManager().alignAsTree();
		}
	};

	Action propertiesAction = new AbstractAction("Properties",
			StandardImages.PROPERTIES) {
		public void actionPerformed(ActionEvent e) {
			((ThingInstance) (myProject.getCurrentOpd().getSelectedItem()))
					.getThing().callPropertiesDialog(
							BaseGraphicComponent.SHOW_ALL_TABS,
							BaseGraphicComponent.SHOW_ALL_BUTTONS);
			myProject.getCurrentOpd().getDrawingArea().repaint();

		}
	};

	Action pasteAction = new AbstractAction("Paste", StandardImages.PASTE) {
		public void actionPerformed(ActionEvent e) {
			myProject.paste(x, y, ti.getThing());
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action bringToFrontAction = new AbstractAction("Bring To Front",
			StandardImages.EMPTY) {
		public void actionPerformed(ActionEvent e) {
			OpdBaseComponent obc = ti.getConnectionEdge();
			obc.bringToFront();
		}
	};

	Action sendToBackAction = new AbstractAction("Send To Back",
			StandardImages.EMPTY) {
		public void actionPerformed(ActionEvent e) {
			OpdBaseComponent obc = ti.getConnectionEdge();
			obc.sendToBack();
		}
	};

	Action fitToContent = new AbstractAction("Fit To Content",
			StandardImages.EMPTY) {
		public void actionPerformed(ActionEvent e) {
			OpdConnectionEdge oce = ti.getConnectionEdge();
			Point initLocation = new Point((int) oce.getX(), (int) oce.getY());
			Dimension initSize = new Dimension(oce.getWidth(), oce.getHeight());

			oce.fitToContent();

		}
	};

}