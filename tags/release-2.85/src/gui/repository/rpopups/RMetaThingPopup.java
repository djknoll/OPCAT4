package gui.repository.rpopups;

import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmState;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.repository.BaseView;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;

public class RMetaThingPopup extends RDefaultPopup {

	private static final long serialVersionUID = 1L;

	ThingEntry myEntry;

	public RMetaThingPopup(BaseView view, OpdProject prj) {
		super(view, prj);
		this.myEntry = (ThingEntry) this.userObject;
		this.add(this.propertiesAction);
		this.add(new JSeparator());
		this.add(this.copyAction);
		this.addCollapseExpand();
	}

	Action copyAction = new AbstractAction("Insert", StandardImages.COPY) {

		private static final long serialVersionUID = -9011119202463736832L;

		public void actionPerformed(ActionEvent e) {
			
			myProject.clearClipBoard() ; 
			
			if (myEntry instanceof ProcessEntry) {
				ProcessEntry process = (ProcessEntry) myEntry ; 
				
				ProcessInstance addedProcessInstance = myProject.addProcess(100,100, myProject.getCurrentOpd().getDrawingArea(),-1, -1);
				((OpmProcess) addedProcessInstance.getEntry().getLogicalEntity()).copyPropsFrom((OpmProcess) process.getLogicalEntity()) ; 
				Iterator iter = Collections.list(process.getInstances()).iterator(); 
				ProcessInstance instance = (ProcessInstance)  iter.next() ;
				
				if (iter.hasNext()) {
					instance = (ProcessInstance) iter.next() ;
				}
				
				addedProcessInstance.setLocation(instance.getX(), instance.getY()) ;
				addedProcessInstance.setBackgroundColor(instance.getBackgroundColor()) ; 
				addedProcessInstance.setBorderColor(instance.getBorderColor()) ; 
				addedProcessInstance.setSize(instance.getWidth(), instance.getHeight()) ; 
				addedProcessInstance.setTextColor(instance.getTextColor()) ; 
				addedProcessInstance.setTextPosition(instance.getTextPosition()) ; 
				 
				//addedProcessInstance.copyPropsFrom(instance) ; 
				 

			}
			if (myEntry instanceof ObjectEntry) {
				ObjectEntry object = (ObjectEntry) myEntry ; 
				
				ObjectInstance addedObjectInstance = myProject.addObject(100,100, myProject.getCurrentOpd().getDrawingArea(),-1, -1, true);				
				((OpmObject) addedObjectInstance.getEntry().getLogicalEntity()).copyPropsFrom((OpmObject) object.getLogicalEntity()) ; 
				Iterator iter = Collections.list(object.getStateEnum()).iterator() ; 
				while(iter.hasNext()) {
					OpmState state = (OpmState) iter.next();
					myProject.addState(state.getName(), addedObjectInstance) ;
				}
				
				Iterator objIter = Collections.list(object.getInstances()).iterator(); 
				
				ObjectInstance instance = (ObjectInstance)  objIter.next() ;
				
				if (objIter.hasNext()) {
					instance = (ObjectInstance) objIter.next() ;
				}
				
				addedObjectInstance.setLocation(instance.getX(), instance.getY()) ;
				addedObjectInstance.setBackgroundColor(instance.getBackgroundColor()) ; 
				addedObjectInstance.setBorderColor(instance.getBorderColor()) ; 
				addedObjectInstance.setSize(instance.getWidth(), instance.getHeight()) ; 
				addedObjectInstance.setTextColor(instance.getTextColor()) ; 
				addedObjectInstance.setTextPosition(instance.getTextPosition()) ; 				

			}			

		}
	};

	Action propertiesAction = new AbstractAction("Properties",
			StandardImages.PROPERTIES) {
		
		private static final long serialVersionUID = 4806047327580173904L;

		public void actionPerformed(ActionEvent e) {

		}
	};

}