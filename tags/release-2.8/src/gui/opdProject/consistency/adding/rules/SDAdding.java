package gui.opdProject.consistency.adding.rules;

import java.util.Enumeration;
import java.util.Hashtable;
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
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;

/**
 * this rule is for adding a thing inside the root SD.
 * @author raanan
 *
 */
public class SDAdding extends ConsistencyAbstractChecker implements ConsistencyCheckerInterface {
		
		private boolean deploy = true ; 
		
		public SDAdding (ConsistencyOptions options, ConsistencyResult results) {
			super(options, results) ; 
		}

		public void check() {
			
			

			OpdProject project =  getMyOptions().getProject() ; 
			
			Enumeration addedEnum = getMyOptions().getInstences() ; 		
			while (addedEnum.hasMoreElements()) {
				Instance addedIns = (Instance) addedEnum.nextElement() ;
				//now search project 
				Entry entry = project.getCurrentOpd().getMainEntry() ;
				if (entry == null ) {
					project.getCurrentOpd().selectAll(); 
					Enumeration mainEnum = project.getCurrentOpd().getSelectedItems() ;  
					while (mainEnum.hasMoreElements()) {
						Object obj = mainEnum.nextElement()  ; 
						if (obj instanceof ThingInstance) {
							ThingInstance thingInstance =(ThingInstance) obj ; 
							entry = thingInstance.getEntry() ; 
							Enumeration entryEnum = entry.getInstances() ; 
							while (entryEnum.hasMoreElements()) {
								Instance main = (Instance) entryEnum.nextElement() ; 
								if((main.getOpd().getOpdId() != addedIns.getOpd().getOpdId()) ) {
									Instance[] insArray = new Instance[1] ; 
									insArray[0] = main ; 
									ConsistencyAction action = new ConsistencyAction(ConsistencyAction.THING_SD_ADDITION, 
																	main.getOpd(), 
																	addedIns, 
																	insArray) ;   
									getResults().setAction(action) ; 
								}
							}
						}
					}
					project.getCurrentOpd().removeSelection();
				}
				
			}
		}

		public void deploy(ConsistencyResult checkResult) {
			OpdProject project = getMyOptions().getProject() ; 
			Hashtable OPDS = new Hashtable() ; 
			
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
				if (action.getMyType() == ConsistencyAction.THING_SD_ADDITION) { 
					if(!deploy.isCancelPressed() && !deploy.isWindowClosing() ) {
						Opd opd = action.getMyOpd() ; 
						if (OPDS.get(new Long(opd.getOpdId())) == null ) {
							OPDS.put(new Long(opd.getOpdId()), opd) ; 
							project.showOPD(opd.getOpdId()) ;
							opd.removeSelection() ;
							action.getMySourceInstance().setSelected(true);
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
						}
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
