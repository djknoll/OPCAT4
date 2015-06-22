package gui.opdGraphics.popups;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.projectStructure.Instance;
import gui.projectStructure.ProcessEntry;

public class AnimatedProcessPopup extends DefaultPopup {
	ProcessEntry myProcessEntry;
	
	public AnimatedProcessPopup(OpdProject prj, Instance inst) {
		super(prj, inst);
		this.add(breakPointAction);
		myProcessEntry = (ProcessEntry)inst.getEntry();
	}
	
	Action breakPointAction = new AbstractAction("Toggle Breakpoint",
			StandardImages.EMPTY) {

		/**
		 * 
		 */
		//private static final long serialVersionUID = -667563739626134447L;

		public void actionPerformed(ActionEvent e) {
			try {
				myProcessEntry.setBreakpoint(!myProcessEntry.isMarkedAsBreakpoint());
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	};
	

}
