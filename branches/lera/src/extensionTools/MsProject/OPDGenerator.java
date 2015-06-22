package extensionTools.MsProject;
import java.awt.Color;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import gui.opdGraphics.opdBaseComponents.*;
import gui.opdProject.GenericTable;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.*;
import extensionTools.MsProject.MsProjectDB.MsAssignment;
import extensionTools.MsProject.MsProjectDB.MsProject;
import extensionTools.MsProject.MsProjectDB.MsResource;
import extensionTools.MsProject.MsProjectDB.MsTask;

import java.awt.Font;

//Current Algorithm:
//The hierarchy is according to the WBS hierarchy accepted from the MsProject
//Inside inZooming of each process, all the child processes are organized
//in accordance with their FS predecessor links
//The algorithm is outside of the tasks creation to make it more generic and
//Strategy Design Pattern was used.

public class OPDGenerator {
	
	public static final int BASIC_HIERARCHY = 0; 
	private OPDConstructor constructor;
	
	public OPDGenerator(int selection){
		switch(selection){
			case BASIC_HIERARCHY:{
				constructor = new BasicOPDConstructor();
			}
		}
	}
	
	public void generateOPDTree(){
		constructor.constructOPDTree();
	}
	
	public abstract class OPDConstructor{
		public abstract void constructOPDTree();
	};
	
	public class BasicOPDConstructor extends OPDConstructor{
		
		public void constructOPDTree(){
			IXOpd myOPD= (IXOpd)MsProject.opcatSystem.getIXSystemStructure().getOpds().nextElement();
			long rootOPDID = myOPD.getOpdId();
			IXProcessInstance procOPMInstance = (IXProcessInstance)MsProject.opcatSystem.addProcess(
				MsProject.firstTask.getTaskName(), 150, 150, rootOPDID);
			MsProject.firstTask.setProcOPMInstance(procOPMInstance);
			constructFirstOPD(MsProject.firstTask);
			//constructChildOPDs(MsProject.firstTask);
			/*Iterator<MsTask>firstTaskIter = MsProject.firstTasks.iterator();
			while(firstTaskIter.hasNext()){
				MsTask currTask = firstTaskIter.next();
				IXProcessInstance procOPMInstance = (IXProcessInstance)MsProject.opcatSystem.addProcess(
					currTask.getTaskName(), 100, (55 + new Integer(currTask.getWBS()).intValue()*5), 
						rootOPDID);
				currTask.setProcOPMInstance(procOPMInstance);
				constructChildOPDs(currTask);	
			}*/
			addAssignments2OPD();
			//System.out.println("Finished constructing OPD TREE");
		}
		
		private void constructFirstOPD(MsTask myTask){	
		IXOpd myOPD = myTask.getProcOPMInstance().createInzoomedOpd();
		myTask.setMyOPDInZoomedID(myOPD.getOpdId());
		Iterator<MsTask>firstTaskIter = MsProject.firstTasks.iterator();
		int i=0;
		int level = 0;
		while(firstTaskIter.hasNext()){
			if(i%3 == 0){level++;i=0;}
			MsTask currTask = firstTaskIter.next();
			String procName = MsUtility.getNormalizedThingName(currTask.getTaskName());
			IXProcessInstance procOPMInstance = (IXProcessInstance)MsProject.opcatSystem.addProcess(procName,i*100+25,25+level*55, myOPD.getMainIXInstance());
			currTask.setProcOPMInstance(procOPMInstance);
			this.constructChildOPDs(currTask);
			i++;
		}
		fixOPDMainThingSize(myOPD);
	}
		
		private void constructChildOPDs(MsTask myTask){
			if(myTask==null || myTask.getChildren().isEmpty())return;
			IXOpd myOPD = myTask.getProcOPMInstance().createInzoomedOpd();
			myTask.setMyOPDInZoomedID(myOPD.getOpdId());
			int level = 0;
			HashSet<String> prevChildren = (HashSet<String>)myTask.getChildren().clone();
			Vector<String> children = findNextLevelChildren(prevChildren, myTask);
			while(children != null && prevChildren != null){
				addNextLevelProcesses2OPD(children, level, myOPD);
				level++;
				int count = prevChildren.size();
				prevChildren.removeAll(children);
				int afterRemoveCount = prevChildren.size(); //If has circle - stop!!
				if(prevChildren.isEmpty()||count == afterRemoveCount)break;
				children = findNextLevelChildren(prevChildren, myTask);
			}
			fixOPDMainThingSize(myOPD);
		}
		
		private void fixOPDMainThingSize(IXOpd myOPD){
			Enumeration<IXThingInstance> insts = myOPD.getMainIXInstance().getChildThings();
			Hashtable<IXThingInstance,Location> myHash= new Hashtable<IXThingInstance,Location>();
			if(!insts.hasMoreElements())return;
			IXThingInstance father = myOPD.getMainIXInstance();
			IXThingInstance inst;
			int minX = 20000;
			int minY = 20000;
			int maxX = 0;
			int maxY = 0;
			int z;
			while(insts.hasMoreElements()){
				inst = insts.nextElement();
				myHash.put(inst, new Location(inst.getX(),inst.getY()));
				minX =(minX > inst.getX())? inst.getX():minX;
				minY =(minY > inst.getY())? inst.getY():minY;
				maxX =(maxX < (z=inst.getX()+inst.getWidth()))? z:maxX;
				maxY =(maxY < (z=inst.getY()+inst.getHeight()))? z:maxY;
			}
			if(minX==20000)return;
			int AB = maxX-minX;
			int ABq = AB*AB;
			double alpha = new Double(ABq/2.0).doubleValue();
			double a = java.lang.Math.sqrt(alpha);
			int BC = maxY-minY;
			int BCq = BC*BC;
			double beta = new Double(BCq/2.0).doubleValue();
			double b = java.lang.Math.sqrt(beta);
			myOPD.getMainIXInstance().setSize(new Double(a).intValue()*2, new Double(b).intValue()*2);	
			int midX = (maxX + minX)/2;
			int midY = (maxY + minY)/2;
			double dispX = a - midX;
			double dispY = b - midY;
			Enumeration<IXThingInstance> instss = myHash.keys();
			Location l;
			while(instss.hasMoreElements()){
				inst = instss.nextElement();
				l = myHash.get(inst);
				inst.setLocation(new Double(l.getX()+dispX).intValue(), 
							new Double(l.getY()+dispY).intValue());
			}
			father.setLocation(100, 10);
		}
		
		private Vector<String> findNextLevelChildren(HashSet<String>prevChildren, MsTask myTask){
			Vector<String> nextChildren = new Vector<String>();
			//for each child check if it has any predecessors inside prevChildren, if no - add to the collection
			Iterator<String> it = prevChildren.iterator();
			while(it.hasNext()){
				String nextUID = it.next();
				MsTask currTask = MsProject.tasks.get(nextUID);
				Vector<String> myPreds = currTask.getPredecessorUIDsByType(MsTask.Predecessor.FS);
				int i=0;
				for(;i<myPreds.size();i++){
					if(prevChildren.contains(myPreds.elementAt(i)))break;
				}
				//current children list doesn't contain any predecessors
				if(i==myPreds.size())nextChildren.add(nextUID);
			}
			return nextChildren;
		}
		
		private void addNextLevelProcesses2OPD(Vector<String>children, int level, /*IXProcessInstance*/IXOpd parentInst){
			int size = children.size();
			IXProcessInstance procOPMInstance;
			String procName;
			for(int i =0; i< size;i++){
				MsTask currTask = MsProject.tasks.get(children.get(i));
				procName = MsUtility.getNormalizedThingName(currTask.getTaskName());
				procOPMInstance = (IXProcessInstance)MsProject.opcatSystem.addProcess(procName,i*100+25,25+level*55, parentInst.getMainIXInstance());
				currTask.setProcOPMInstance(procOPMInstance);
				this.constructChildOPDs(currTask);
			}
		}
		
		private void addAssignments2OPD(){
			Enumeration<MsTask>tasks = MsProject.tasks.elements();
			MsTask currTask;
			IXProcessInstance procInst;
			String assignmentUID;
			MsAssignment assign;
			MsResource currObj;
			while(tasks.hasMoreElements()){
				currTask = tasks.nextElement();
				procInst = currTask.getProcOPMInstance();
				if(procInst != null){
					Iterator<String> it = currTask.getAssignments().iterator();
					while(it.hasNext()){
						assignmentUID = it.next();
						assign = MsProject.assignments.get(assignmentUID);
						if(assign!=null){
							currObj = MsProject.resources.get(assign.getResourceUID());
							if(currObj!=null)
								linkResourceWithProcess(currObj, (IXProcessEntry)procInst.getIXEntry());
						}
						
					}
				}
				
			}
		}
		
		private void linkResourceWithProcess(MsResource rs, IXProcessEntry proc){
			IXProcessInstance tmpProc;
			Enumeration procEn = proc.getInstances();
			IXObjectEntry objEntry = rs.getObjOPMEntry();
			IXObjectInstance obj;
			while(procEn.hasMoreElements()){
				tmpProc = (IXProcessInstance)procEn.nextElement();
				OpdKey key = tmpProc.getKey();
				IXOpd currOPD = MsProject.opcatSystem.getIXSystemStructure().getIXOpd(key.getOpdId());
				if(objEntry == null || !objEntry.hasInstanceInOpd(currOPD.getOpdId())){
					//add new instance/entry to the opd
					obj = (IXObjectInstance)MsProject.opcatSystem.addObject(MsUtility.getNormalizedThingName(rs.getResourceName()), 10, 10, currOPD.getOpdId());
				}else{
					obj = findOPMObjectInstanceInOPD(objEntry, currOPD);
				}
				rs.setObjOPMEntry((IXObjectEntry)obj.getIXEntry());
				linkOPMObjectInstanceWithOPMProcInstance(obj, tmpProc);
			}
		}
		
		private IXObjectInstance findOPMObjectInstanceInOPD(IXObjectEntry objEntry, IXOpd currOPD){
			Enumeration en = objEntry.getInstances();
			while(en.hasMoreElements()){
				IXObjectInstance i = (IXObjectInstance)en.nextElement();
				if(i.getKey().getOpdId() == currOPD.getOpdId())
					return i;
			}
			return null;
		}
		
		private void linkOPMObjectInstanceWithOPMProcInstance(IXObjectInstance obj, IXProcessInstance proc){
			MsProject.opcatSystem.addLink(obj, proc, exportedAPI.OpcatConstants.INSTRUMENT_LINK);		
		}
		
		public class Location{
			int x;
			int y;
			public int getX() {
				return x;
			}
			public int getY() {
				return y;
			}
			public Location(int x_, int y_){x = x_; y=y_;}
		};
	};
		
		
}
