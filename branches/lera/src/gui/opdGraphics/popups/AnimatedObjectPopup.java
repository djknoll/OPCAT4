package gui.opdGraphics.popups;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import gui.images.standard.StandardImages;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdProject.OpdProject;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.opdGraphics.opdBaseComponents.*;

public class AnimatedObjectPopup extends DefaultPopup {
	ObjectEntry myEntry;
	ObjectInstance myObjInstance;
	
	public AnimatedObjectPopup(OpdProject prj, Instance inst) {
		super(prj, inst);
		myEntry = (ObjectEntry)inst.getEntry();
		myObjInstance = (ObjectInstance)inst;
		if (!myEntry.isInstance()){
			this.add(showInstancesAction);
		}
	}
	
	Action showInstancesAction = new AbstractAction("Show Instances",
			StandardImages.EMPTY) {

		/**
		 * 
		 */
		//private static final long serialVersionUID = -667563739626134447L;

		public void actionPerformed(ActionEvent e) {
			ObjectPropertiesDialog od = new ObjectPropertiesDialog((OpdObject)myObjInstance.getConnectionEdge(),
					myEntry, myProject, false);
			od.showDialog();
		}
	};
	

}
