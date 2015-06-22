package gui.repository.rpopups;

import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.repository.BaseView;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;

public class REntryPopup extends RDefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	OpmThing myOpmThing;

	ThingEntry myEntry;

	public REntryPopup(BaseView view, OpdProject prj) {
		super(view, prj);
		this.myOpmThing = (OpmThing) this.userObject;
		this.add(this.showAction);
		this.add(new JSeparator());
		this.add(this.deleteAction);
		this.add(new JSeparator());
		this.add(this.zoomInAction);
		this.add(this.unfoldingAction);
		this.add(new JSeparator());
		this.add(this.propertiesAction);
		this.add(new JSeparator());
		this.add(this.copyAction);
		this.addCollapseExpand();
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
			ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing).ShowInstances();
		}
	};

	Action copyAction = new AbstractAction("Copy", StandardImages.COPY) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -2130296981968671447L;

		public void actionPerformed(ActionEvent e) {
			ThingEntry entry;
			Opd curOpd = REntryPopup.this.myProject.getCurrentOpd();
			entry = ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing);
			if (entry != null) {
				Enumeration insEnum = entry.getInstances();
				ThingInstance local = (ThingInstance) insEnum.nextElement();
				REntryPopup.this.myProject.showOPD(local.getOpd().getOpdId());
				REntryPopup.this.myProject.removeSelection();
				REntryPopup.this.myProject.addSelection(local, true);
				local.setSelected(true);
				REntryPopup.this.myProject.copy();
				REntryPopup.this.myProject.showOPD(curOpd.getOpdId());
				REntryPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
			}
		}
	};

	Action deleteAction = new AbstractAction("Delete", StandardImages.DELETE) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = -1586427737686549765L;

		public void actionPerformed(ActionEvent e) {
			ThingEntry entry;
			entry = ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing);
			if (entry != null) {
				Enumeration insEnum = entry.getInstances();
				ThingInstance local = (ThingInstance) insEnum.nextElement();
				REntryPopup.this.myProject.showOPD(local.getOpd().getOpdId());
				REntryPopup.this.myProject.removeSelection();
				REntryPopup.this.myProject.addSelection(local, true);
				local.setSelected(true);
				REntryPopup.this.myProject.delete();
			}

		}
	};

	Action zoomInAction = new AbstractAction("In-Zooming", StandardImages.EMPTY) {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 4601416826417432940L;

		public void actionPerformed(ActionEvent e) {
			ThingEntry entry;
			entry = ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing);
			if ((entry != null) && (entry instanceof ProcessEntry)) {
				Enumeration insEnum = entry.getInstances();
				ThingInstance local = (ThingInstance) insEnum.nextElement();
				REntryPopup.this.myProject.showOPD(local.getOpd().getOpdId());
				REntryPopup.this.myProject.removeSelection();
				REntryPopup.this.myProject.addSelection(local, true);
				local.setSelected(true);
				REntryPopup.this.myProject.zoomIn();
			}
		}
	};

	Action unfoldingAction = new AbstractAction("Unfolding",
			StandardImages.EMPTY) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -5811445809081408803L;

		public void actionPerformed(ActionEvent e) {
			ThingEntry entry;
			entry = ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing);
			if ((entry != null) && (entry instanceof ObjectEntry)) {
				Enumeration insEnum = entry.getInstances();
				ThingInstance local = (ThingInstance) insEnum.nextElement();
				REntryPopup.this.myProject.showOPD(local.getOpd().getOpdId());
				REntryPopup.this.myProject.removeSelection();
				REntryPopup.this.myProject.addSelection(local, true);
				local.setSelected(true);
				REntryPopup.this.myProject.unfolding();
			}
		}
	};

	Action propertiesAction = new AbstractAction("Properties",
			StandardImages.PROPERTIES) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -7956680214857803796L;

		public void actionPerformed(ActionEvent e) {
			ThingEntry entry;
			entry = ThingEntry.getEntryFromOpmThing(REntryPopup.this.myOpmThing);
			if ((entry != null)) {
				Enumeration insEnum = entry.getInstances();
				ThingInstance local = (ThingInstance) insEnum.nextElement();
				REntryPopup.this.myProject.showOPD(local.getOpd().getOpdId());
				REntryPopup.this.myProject.removeSelection();
				REntryPopup.this.myProject.addSelection(local, true);
				local.setSelected(true);
				local.getThing().callPropertiesDialog(
						BaseGraphicComponent.SHOW_ALL_TABS,
						BaseGraphicComponent.SHOW_ALL_BUTTONS);
				REntryPopup.this.myProject.getComponentsStructure().getOpd(
						local.getThing().getOpdId()).getDrawingArea().repaint();
			}
		}
	};

}