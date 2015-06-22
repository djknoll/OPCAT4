package gui.opdGraphics.popups;

import gui.Opcat2;
import gui.actions.edit.MetaColoringAction;
import gui.actions.edit.MetaHideAction;
import gui.actions.edit.MetaInsertAction;
import gui.controls.GuiControl;
import gui.images.standard.StandardImages;
import gui.metaDataProject.MetaDataProject;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.LibraryTypes;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.opmEntities.OpmThing;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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
		add(new JSeparator());

		JMenu levelColor = new JMenu("Meta Coloring");
		levelColor
				.setToolTipText("Color things according to Meta Data associations");
		add(levelColor);

		JMenu implemMenu = new JMenu("Insert Roles");
		implemMenu
				.setToolTipText("Insert Things connected to an External Thing");

		JMenu hideMenu = new JMenu("Meta Hide");
		hideMenu.setToolTipText("Hide Things connected to External Data");
		add(hideMenu);

		JMenu showMenu = new JMenu("Meta Show");
		showMenu.setToolTipText("Show Things connected to External Data");
		add(showMenu);

		JMenuItem colorItem = new JMenuItem(new MetaColoringAction("Default",
				null));
		levelColor.add(colorItem);

		JMenuItem showItem = new JMenuItem(new MetaHideAction("Default", null,
				true));
		showMenu.add(showItem);

		JMenuItem hideItem = new JMenuItem(new MetaHideAction("Default", null,
				false));
		hideMenu.add(hideItem);

		Iterator iter = Collections.list(myProject.getMetaLibraries())
				.iterator();
		while (iter.hasNext()) {
			MetaLibrary meta = (MetaLibrary) iter.next();
			if (meta.getState() == MetaLibrary.STATE_LOADED) {
				if (meta.getProjectHolder() instanceof MetaDataProject) {
					MetaDataProject proj = (MetaDataProject) meta
							.getProjectHolder();
					if (proj.getColoringIndex() >= 0) {
						hideItem = new JMenuItem(new MetaHideAction(proj
								.getType(), meta, false));
						hideMenu.add(hideItem);

						showItem = new JMenuItem(new MetaHideAction(proj
								.getType(), meta, true));
						showMenu.add(showItem);

						colorItem = new JMenuItem(new MetaColoringAction(proj
								.getType(), meta));
						levelColor.add(colorItem);
					}
				} else {
					OpdProject proj = (OpdProject) meta.getProjectHolder();
					hideItem = new JMenuItem(new MetaHideAction(proj.getName(),
							meta, false));
					hideMenu.add(hideItem);

					showItem = new JMenuItem(new MetaHideAction(proj.getName(),
							meta, true));
					showMenu.add(showItem);

					colorItem = new JMenuItem(new MetaColoringAction(proj
							.getName(), meta));
					levelColor.add(colorItem);
				}
			}
		}

		iter = Collections.list(
				myProject.getMetaLibraries(LibraryTypes.MetaLibrary))
				.iterator();
		ArrayList<Role> insertRolesThings = new ArrayList<Role>();

		while (iter.hasNext()) {
			MetaLibrary meta = (MetaLibrary) iter.next();
			if (meta.getState() == MetaLibrary.STATE_LOADED) {
				// check for connected roles
				try {
					Iterator rolesIter = meta.getRolesCollection().iterator();
					JMenu metaMenu = new JMenu(meta.getName());
					while (rolesIter.hasNext()) {
						Role role = (Role) rolesIter.next();
						if (role.getThing() instanceof OpmThing) {
							insertRolesThings.add(role);
						}
					}
					Collections.sort(insertRolesThings);
					for (int i = 0; i < insertRolesThings.size(); i++) {
						JMenuItem roleItem = new JMenuItem(
								new MetaInsertAction(insertRolesThings.get(i)));
						metaMenu.add(roleItem);
					}
					implemMenu.add(metaMenu);
				} catch (Exception ex) {
					OpcatLogger.logError(ex);
				}
			}
		}
		// this.add(new JSeparator());
		add(implemMenu);
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
					OpdPopup.this.myProject, Opcat2.getFrame(),
					"Project Properties");
			ppd.showDialog();
			OpdPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
			GuiControl gui = GuiControl.getInstance();
			gui.getRepository().rebuildTrees(true);
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
			OpdPopup.this.myProject.deleteOpd(OpdPopup.this.myProject
					.getCurrentOpd());
			// myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

}