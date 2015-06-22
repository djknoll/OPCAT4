package extensionTools.MsProject.MsProjectDB;
import java.util.*;
import org.w3c.dom.*;

import exportedAPI.opcatAPIx.*;
import extensionTools.MsProject.MsUtility;


public class MsTask {

	private String taskName;
	private Node myXMLTask;
	private String WBS;
	private String UID;
	private String ID;
	private Date Start;
	private Date Finish;
	private Date EarlyStart;
	private Date EarlyFinish;
	private Date LateStart;
	private Date LateFinish;
	private String[] Duration;
	private boolean isCritical;
	private boolean isMilestone;
	private boolean wasUpdated = false;
	private long myOPDInZoomedID = -1;
	private ArrayList<Predecessor>predessesors;
	private HashSet<String>children;
	private HashSet<String>assignments;
	private IXProcessInstance procOPMInstance;
	
	public MsTask(Node task){
		//sets the default!! (for the zero node (task with WBS = 0))
		this.taskName = MsProject.title;
		init(task);
		constructChildNodes();
		constructPredecessors();
	}
	
	private void init(Node task){
		myXMLTask = task;
		predessesors = new ArrayList<Predecessor>();
		children = new HashSet<String>();
		assignments = new HashSet<String>();
		parseTaskProperties(task);
		MsProject.tasks.put(UID, this);
	}
	
	public void constructChildNodes(){
		Document doc = myXMLTask.getOwnerDocument();
		NodeList wbsList = doc.getElementsByTagName("WBS");
		for(int i=0; i<wbsList.getLength();i++){
			Node wbsNode = wbsList.item(i);
			String wbsCode = ((Element)wbsNode).getTextContent();
			if(wbsCode.matches(this.WBS+"."+"[0-9]*")){
				MsTask child = new MsTask(wbsNode.getParentNode());
				this.children.add(child.UID);
			}
		}
	}
	
	//adding predecessor updates est lst,.. for critical path
	public void addPredecessor(MsTask predecessor, int type){
		MsTask.Predecessor pred = null;
		pred = new MsTask.Predecessor(predecessor.UID, type);
		if(type == MsTask.Predecessor.FS){
			if(EarlyStart.before(predecessor.EarlyFinish)){
				EarlyStart = predecessor.EarlyFinish;
			}
			EarlyFinish = MsUtility.getEndDate(EarlyStart, Duration);
			//now fix the predecessor
		    if(predecessor.LateFinish == null){
		    	predecessor.LateFinish = (Date)LateStart.clone();
		    }else{
		    	if(predecessor.LateFinish.after(LateStart)){
		    		predecessor.LateFinish = (Date)LateStart.clone();
		    	}
		    }
		    predecessor.LateStart = MsUtility.getStartDate(predecessor.LateFinish, predecessor.Duration);
			setIsCritical(this);
		    setIsCritical(predecessor);
		    }	
	}
	
	public void setIsCritical(MsTask task){
	    if(task.EarlyFinish.equals(task.LateFinish)&&task.EarlyStart.equals(task.LateStart)){
			task.isCritical = true;
			return;
		}task.isCritical = false;
	}
	
	public void constructPredecessors(){
		MsTask.Predecessor pred = null;
		NodeList predecessorLinks = ((Element)myXMLTask).getElementsByTagName("PredecessorLink");
		for(int i=0,currType=0; i< predecessorLinks.getLength();i++){
			NodeList currNodesUID = ((Element)predecessorLinks.item(i)).getElementsByTagName("PredecessorUID");
			NodeList currNodesType = ((Element)predecessorLinks.item(i)).getElementsByTagName("Type");
			String currUID = ((Element)currNodesUID.item(0)).getTextContent();
			currType = new Integer(((Element)currNodesType.item(0)).getTextContent()).intValue();
			pred = new MsTask.Predecessor(currUID, currType);
			this.predessesors.add(pred);
		//	System.out.println("MY Pred UID is: "+ currUID);
		}
	}
	
	public void parseTaskProperties(Node task){
		if(task.getNodeType() == Node.ELEMENT_NODE){
			NodeList currNode;
			Element currElem;
			String currVal;
			String[] props = {"UID", "ID", "Name", "WBS", "Start", "Finish", "EarlyStart",
					"EarlyFinish", "LateStart",
					"LateFinish", "Duration", "Milestone", "Critical"};
			int numOfProps = 13;
			for(int i=0; i < numOfProps; i++){
				currNode = ((Element)task).getElementsByTagName(props[i]);
				if(currNode != null){
					currElem = (Element)currNode.item(0);
					if(currElem != null){
						currVal = currElem.getTextContent();
						if(currVal!=null){
							switch(i){
								case 0:{ this.UID = currVal; break;}
								case 1:{this.ID = currVal;break;}
								case 2:{this.taskName = currVal;break;}
								case 3:{this.WBS = currVal;break;}
								case 4:{this.Start = MsUtility.getDate(currVal);break;}
								case 5:{this.Finish = MsUtility.getDate(currVal);break;}
								case 6:{this.EarlyStart = MsUtility.getDate(currVal);break;}
								case 7:{this.EarlyFinish = MsUtility.getDate(currVal);break;}
								case 8:{this.LateStart = MsUtility.getDate(currVal);break;}
								case 9:{this.LateFinish = MsUtility.getDate(currVal);break;}
								case 10:{this.Duration = MsUtility.getDuration(currVal);break;}
								case 11:{this.isMilestone = (currVal.equals("1"))?true:false;break;}
								case 12:{this.isCritical = (currVal.equals("1"))?true:false;break;}
							}
						}
					}
				}
			}
        }
	}
	
	public Vector<String> getPredecessorUIDsByType(int type_){
		Vector<String>myPreds = new Vector<String>();
		Iterator<Predecessor>predIt = this.predessesors.iterator();
		while(predIt.hasNext()){
			Predecessor pr = predIt.next();
			if(pr.type == type_){
				myPreds.add(pr.UID);
			}
		}
		return myPreds;
	}
	
	public boolean isCritical(){
		return this.isCritical;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setMyXMLTask(Node myXMLTask) {
		this.myXMLTask = myXMLTask;
	}
	public Node getMyXMLTask() {
		return myXMLTask;
	}
	public void setWBS(String wBS) {
		WBS = wBS;
	}
	public String getWBS() {
		return WBS;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getUID() {
		return UID;
	}
	public void setOPDId(long oPDId) {
		myOPDInZoomedID = oPDId;
	}
	public long getOPDId() {
		return myOPDInZoomedID;
	}	
	public ArrayList<Predecessor> getPredessesors() {
		return predessesors;
	}
	public void setPredessesors(ArrayList<Predecessor> predessesors) {
		this.predessesors = predessesors;
	}
	public HashSet<String> getChildren() {
		return children;
	}
	public void setChildren(HashSet<String> children) {
		this.children = children;
	}
	public IXProcessInstance getProcOPMInstance() {
		return procOPMInstance;
	}
	public void setProcOPMInstance(IXProcessInstance procOPMInstance) {
		this.procOPMInstance = procOPMInstance;
		String dur = "0;"+Duration[MsUtility.SECONDS]+";"+
			Duration[MsUtility.MINUTES]+";"+Duration[MsUtility.HOURS]+";0;0;0";
		((IXProcessEntry)this.procOPMInstance.getIXEntry()).setMaxTimeActivation(dur);
	}
	public long getMyOPDInZoomedID() {
		return myOPDInZoomedID;
	}
	public void setMyOPDInZoomedID(long myOPDInZoomedID) {
		this.myOPDInZoomedID = myOPDInZoomedID;
	}
	
	public HashSet<String> getAssignments() {
		return assignments;
	}
	
	public void addAssignment(String assignmentUID){
		assignments.add(assignmentUID);
	}

	public boolean isMilestone() {
		return isMilestone;
	}

	public void setMilestone(boolean isMilestone) {
		this.isMilestone = isMilestone;
	}

	public class Predecessor{
		public static final int FF = 0;
		public static final int FS = 1;
		public static final int SS = 2;
		public static final int SF = 1;
		private String UID;
		private int type;
		
		public String getUID() {
			return UID;
		}
		public void setUID(String uid) {
			UID = uid;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public Predecessor(String UID, int type){
			this.UID = UID;
			this.type = type;
		}
	};
};
