package gui.opdProject.consistency.adding.rules;

import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.OpdKey;
import gui.images.standard.StandardImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.consistency.ConsistencyAction;
import gui.opdProject.consistency.ConsistencyAbstractChecker;
import gui.opdProject.consistency.ConsistencyCheckerInterface;
import gui.opdProject.consistency.ConsistencyDeployDialog;
import gui.opdProject.consistency.ConsistencyOptions;
import gui.opdProject.consistency.ConsistencyResult;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
/**
 * this rule is for adding a thing inside a zoomed in OPD but outside the main entity of the OPD.
 * @author raanan
 *
 */
public class InZommedOutsideAddition extends ConsistencyAbstractChecker implements ConsistencyCheckerInterface{
	
	private boolean deploy = true ; 
	
	public InZommedOutsideAddition (ConsistencyOptions options, ConsistencyResult results) {
		super(options, results) ; 
	}

	public void check() {
		
		OpdProject project =  getMyOptions().getProject() ; 
		
		Enumeration addedEnum = getMyOptions().getInstences() ; 		
		while (addedEnum.hasMoreElements()) {
			Instance addedIns = (Instance) addedEnum.nextElement() ; 
			//now search project 
			Entry entry = project.getCurrentOpd().getMainEntry() ;
			if ((entry != null) && (entry instanceof ProcessEntry)) {
				Enumeration mainEnum = entry.getInstances() ; 
				while (mainEnum.hasMoreElements()) {
					Instance main = (Instance) mainEnum.nextElement() ; 
					if((main.getOpd().getOpdId() != addedIns.getOpd().getOpdId()) && (addedIns.getParent() == null)) {
						Instance[] insArray = new Instance[1] ; 
						insArray[0] = main ; 
						ConsistencyAction action = new ConsistencyAction(ConsistencyAction.THING_INZOOMED_ADDITION, 
														main.getOpd(), 
														addedIns, 
														insArray) ;   
						getResults().setAction(action) ; 
					}
				}
			}
			
		}
	}

	public void deploy(ConsistencyResult checkResult) {
		OpdProject project = getMyOptions().getProject() ; 
		
		//run here the deletion deploy GUI and actions 
		ConsistencyDeployDialog deploy = new ConsistencyDeployDialog(); 
		deploy.setAlwaysOnTop(true); 
		deploy.setModal(true) ; 
		deploy.setLocationRelativeTo(project.getMainFrame()); 		
		deploy.setText("Add in this OPD ?"); 
		deploy.setTextIcon(StandardImages.NEW);

		
		//now loop on all the actions in the results
		Vector results = checkResult.getActions() ;
		for(int i = 0 ; i < results.size(); i++) {
			ConsistencyAction action = (ConsistencyAction) results.get(i) ;
			if(action.getMyType() == ConsistencyAction.THING_INZOOMED_ADDITION) {
				if(!deploy.isCancelPressed() && !deploy.isWindowClosing() ) { 
					Opd opd = action.getMyOpd() ; 
					project.showOPD(opd.getOpdId()) ;
					opd.removeSelection() ;
					action.getMySourceInstance().setSelected(true);
					Vector v = new Vector() ; 
					v.add(action.getMySourceInstance()) ; 
					if (!deploy.isAutoPressed()) deploy.setVisible(true) ;
					if (deploy.isOkPressed() || deploy.isAutoPressed()) {
						OpdKey key = new OpdKey(opd.getOpdId(), action.getMySourceInstance().getEntry().getId());
						if (!getResults().isDeployed(key)) {
							Instance newIns = null ; 
							if(action.getMySourceInstance() instanceof ProcessInstance ) {
								newIns = project.addProcessInstanceToContainer(action.getMySourceInstance().getEntry().getName(), ((ThingInstance) action.getMySourceInstance()).getWidth(), 100, ( ThingEntry) action.getMySourceInstance().getIXEntry(), opd.getDrawingArea()) ;
							} 
							if(action.getMySourceInstance() instanceof ObjectInstance ) {
								newIns = project.addObjectInstanceToContainer(action.getMySourceInstance().getEntry().getName(), ((ThingInstance) action.getMySourceInstance()).getWidth(), 100, ( ThingEntry) action.getMySourceInstance().getIXEntry(), opd.getDrawingArea());
							} 	
							getResults().setDeployed(newIns); 
						}
					}
					opd.removeSelection(); 
					deploy.setOkPressed(false); 
					deploy.setNextPresed(false); 
				} else {
					break ; 
				}
			}
		}
		deploy.setAutoPressed(false);
		deploy.setCancelPressed(false) ; 
		deploy.setWindowClosing(false) ; 
		
	}

	public boolean isDeploy() {
		return deploy;
	}

}
