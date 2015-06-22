package gui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.w3c.dom.events.EventException;

import gui.metaDataProject.MetaDataItem;
import gui.metaDataProject.MetaDataProject;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Entry;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatLogger;

public class ColorLevelAction extends EditAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Object metaData;

	MetaLibrary meta;

	public ColorLevelAction(String name, MetaLibrary meta) {
		super(name, null);
		this.meta = meta;
		if (meta != null) {
			this.metaData = meta.getProjectHolder();
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			super.actionPerformed(arg0);
		} catch (EventException e) {
			JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
					.toString(), "Message", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (meta == null) {
			returnToDefault();
		} else {
			returnToDefault(); 
			colorToLevel();
		}
		if ((edit.getCurrentProject().getCurrentOpd() != null)
				&& (edit.getCurrentProject().getCurrentOpd().getDrawingArea() != null))
			edit.getCurrentProject().getCurrentOpd().getDrawingArea().repaint();
	}

	private void returnToDefault() {
		Iterator iter = Collections.list(
				edit.getCurrentProject().getSystemStructure().getElements())
				.iterator();
		while (iter.hasNext()) {
			Entry ent = (Entry) iter.next();
			if ((ent instanceof ObjectEntry) || (ent instanceof ProcessEntry)) {
				Iterator insIter = Collections.list(ent.getInstances())
						.iterator();
				while (insIter.hasNext()) {
					Object ins = insIter.next();
					if (ins instanceof ObjectInstance) {
						((ObjectInstance) ins).getConnectionEdge()
								.restoreFromLevelColor();
					}
					if (ins instanceof ProcessInstance) {
						((ProcessInstance) ins).getConnectionEdge()
								.restoreFromLevelColor();
					}
				}
			}
		}
	}

	private void colorToLevel() {
		Iterator iter = Collections.list(
				edit.getCurrentProject().getSystemStructure().getElements())
				.iterator();
		HashMap colored = new HashMap() ; 
		while (iter.hasNext()) {
			Entry ent = (Entry) iter.next();
			if ((ent instanceof ObjectEntry) || (ent instanceof ProcessEntry)) {
				int level = -1;
				try {
					Iterator roleIter = ((OpmThing) ent.getLogicalEntity())
							.getRolesManager().getRolesVector(meta.getType(),
									meta.getGlobalID()).iterator();
					while (roleIter.hasNext()) {
						Role role = (Role) roleIter.next();
						if (role.getThing().getDataInstance() instanceof MetaDataItem) {
							MetaDataItem metaItem = (MetaDataItem) role
									.getThing().getDataInstance();
							if (level < metaItem.getLevel()) {
								level = metaItem.getLevel();
							}
						} else {
							Long id =new Long(role.getThingId()); 
							if(colored.containsKey(id)) {
								level = ((Long) colored.get(id)).intValue()  ; 
							} else {
								level = colored.values().size() + 1 ;
								colored.put(id, new Long(level)) ; 
							}
						}
					}
				} catch (Exception ex) {
					OpcatLogger.logError(ex);
				}
				if (level > -1) {
					if (ent instanceof ThingEntry) {
						Iterator insIter = Collections.list(ent.getInstances())
								.iterator();
						while (insIter.hasNext()) {
							ThingInstance ins = (ThingInstance) insIter.next();
							int maxLevel = 0;
							if (metaData instanceof MetaDataProject) {
								maxLevel = ((MetaDataProject) metaData)
										.getMaxLevelValue();
							} else {
								maxLevel = ((OpdProject) metaData)
										.getNumOfEntries();
							}
							ins.getConnectionEdge().changeToLevelColor(level,
									maxLevel);
						}
					}
				}
			}
		}
	}

}
