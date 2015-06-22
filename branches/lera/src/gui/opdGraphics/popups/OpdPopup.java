package gui.opdGraphics.popups;

import gui.Opcat2;
import gui.actions.edit.ColorLevelAction;
import gui.actions.edit.RoleInsertAction;
import gui.images.standard.StandardImages;
import gui.metaDataProject.MetaDataProject;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.LibraryTypes;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;
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
		implemMenu.setToolTipText("Insert Things connected to a Role");

		JMenuItem colorItem = new JMenuItem(new ColorLevelAction("Default",
				null));
		levelColor.add(colorItem);

		Iterator iter = Collections.list(myProject.getMetaLibraries())
				.iterator();
		while (iter.hasNext()) {
			MetaLibrary meta = (MetaLibrary) iter.next();
			if (meta.getState() == MetaLibrary.STATE_LOADED) {			
				if (meta.getProjectHolder() instanceof MetaDataProject) {
					MetaDataProject proj = (MetaDataProject) meta
					.getProjectHolder();						
					if (proj.getLevelIndex() >= 0) {
						colorItem = new JMenuItem(new ColorLevelAction(proj
								.getType(), meta));
						levelColor.add(colorItem);
					}
				} else {	
					OpdProject proj = (OpdProject) meta
					.getProjectHolder();						
					colorItem = new JMenuItem(new ColorLevelAction(proj.getName(), meta));
					levelColor.add(colorItem);					
				}
			}
		}

		iter = Collections.list(
				myProject.getMetaLibraries(LibraryTypes.MetaLibrary))
				.iterator();
		while (iter.hasNext()) {
			MetaLibrary meta = (MetaLibrary) iter.next();
			if (meta.getState() == MetaLibrary.STATE_LOADED) {
				// check for connected roles
				try {
					Iterator rolesIter = meta.getRolesCollection().iterator();
					JMenu metaMenu = new JMenu(meta.getName());
					while (rolesIter.hasNext()) {
						Role role = (Role) rolesIter.next();
						JMenuItem roleItem = new JMenuItem(
								new RoleInsertAction(role));
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