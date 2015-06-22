package gui.actions.edit;

import exportedAPI.OpdKey;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.RolesManager;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.w3c.dom.events.EventException;

public class RoleInsertAction extends EditAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Role myRole;

	public RoleInsertAction(String name, Icon icon, Role role) {
		super(name, icon);
		myRole = role;
	}

	public RoleInsertAction(Role role) {
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
				if (rolesManger.contains(myRole)) {
					Instance ins = (Instance) ent.getInstances().nextElement();
					if (!instances.containsKey(ins.getKey())) {
						instances.put(ins.getKey(), ins);
					}
				}
			}
		}

		edit.getCurrentProject().deselectAll();
		edit.getCurrentProject().clearClipBoard(); 
		Iterator keys = instances.keySet().iterator();
		int i = 100 ; 
		while (keys.hasNext()) {
			OpdKey key = (OpdKey) keys.next();
			Instance inst = (Instance) instances.get(key);
			inst.getOpd().getSelection().addSelection(inst, true);			
			edit.getCurrentProject()._copy(inst.getOpd(),
					edit.getCurrentProject().getCurrentOpd(), i , i,
					edit.getCurrentProject().getCurrentOpd().getDrawingArea(),
					OpdProject.COPY);
			i +=  50 ;  
		}

	}

}