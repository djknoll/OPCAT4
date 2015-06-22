package gui.actions.edit;

import exportedAPI.OpdKey;
import gui.controls.EditControl;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.RolesManager;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.w3c.dom.events.EventException;

public class MetaInsertAction extends EditAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Role myRole;

	public MetaInsertAction(String name, Icon icon, Role role) {
		super(name, icon);
		myRole = role;
	}

	public MetaInsertAction(Role role) {
		super(role.getThing().getName(), null);
		myRole = role;
	}

	public void actionPerformed(ActionEvent arg0) throws EventException {
		try {
			super.actionPerformed(arg0);
		} catch (EventException e) {
			JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
					.toString(), "Message", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// get a list of things connected to the role. and paste in the current
		// location
		HashMap instances = new HashMap();
		Iterator iter = Collections.list(
				edit.getCurrentProject().getSystemStructure().getElements())
				.iterator();
		while (iter.hasNext()) {
			Entry ent = (Entry) iter.next();
			if ((ent instanceof ProcessEntry) || (ent instanceof ObjectEntry)) {
				OpmThing opmThing = (OpmThing) ent.getLogicalEntity();
				RolesManager rolesManger = opmThing.getRolesManager();

				ArrayList doInsert = new ArrayList();
				if (rolesManger.contains(myRole)) {
					doInsert.add(ent);
				}
				if (myRole.getThing() instanceof OpmThing) {
					OpmThing opm = (OpmThing) myRole.getThing();

					ArrayList sons = new ArrayList();

					OpdProject project = (OpdProject) myRole.getOntology()
							.getProjectHolder();
					Enumeration entries = project.getSystemStructure()
							.getElements();
					while (entries.hasMoreElements()) {
						Entry remoteEntry = (Entry) entries.nextElement();
						if (remoteEntry instanceof ThingEntry) {
							if (remoteEntry.getLogicalEntity().getId() == opm
									.getId()) {
								sons = ((ThingEntry) remoteEntry).getAllSons();
							}
						}
					}

					for (int i = 0; i < sons.size(); i++) {
						Entry entry = (Entry) sons.get(i);
						try {
							Role newRole = new Role(entry.getLogicalEntity()
									.getId(), myRole.getOntology());
							if (rolesManger.contains(newRole)) {
								doInsert.add(ent);
							}
						} catch (Exception ex) {
							OpcatLogger.logError(ex);
						}
					}
				}

				if (doInsert.size() > 0) {
					for (int i = 0; i < doInsert.size(); i++) {
						Entry locEntry = (Entry) doInsert.get(i);
						Instance ins = (Instance) locEntry.getInstances()
								.nextElement();
						if (!instances.containsKey(ins.getKey())) {
							instances.put(ins.getKey(), ins);
						}
					}
				}
			}
		}

		edit.getCurrentProject().deselectAll();
		edit.getCurrentProject().clearClipBoard();
		Iterator keys = instances.keySet().iterator();
		int i = 100;
		while (keys.hasNext()) {
			OpdKey key = (OpdKey) keys.next();
			Instance inst = (Instance) instances.get(key);
			inst.getOpd().getSelection().addSelection(inst, true);
			edit.getCurrentProject()._copy(inst.getOpd(),
					edit.getCurrentProject().getCurrentOpd(), i, i,
					edit.getCurrentProject().getCurrentOpd().getDrawingArea(),
					OpdProject.COPY);
			Enumeration insEnum = inst.getEntry().getInstances();
			while (insEnum.hasMoreElements()) {
				Instance locIns = (Instance) insEnum.nextElement();
				if (locIns.getOpd().getOpdId() == edit.getCurrentProject()
						.getCurrentOpd().getOpdId()) {
					if (locIns instanceof ThingInstance) {
						((ThingInstance) locIns).getConnectionEdge()
								.fitToContent();
					}
				}
			}
			i += 50;
		}

		EditControl.getInstance().getCurrentProject().getCurrentOpd().refit();

	}

}