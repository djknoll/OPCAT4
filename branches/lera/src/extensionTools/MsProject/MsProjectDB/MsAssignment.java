package extensionTools.MsProject.MsProjectDB;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import exportedAPI.opcatAPIx.*;

import exportedAPI.opcatAPIx.IXObjectInstance;
import extensionTools.MsProject.MsUtility;

public class MsAssignment {
	private Node myXMLAssignment;
	private String UID;
	private String TaskUID;
	private String ResourceUID;
	private java.util.Date Start;
	private java.util.Date Finish;
	private int Units;
	private String[] Work; 
	
	static String[] props = {"UID", "TaskUID", "ResourceUID","Start", "Finish", "Units",
		"Work"};
	static int numOfProps = 7;
	private IXLinkInstance linkOPMInstance; 
	
	public MsAssignment(Node assignment){
		if(assignment == null)return;
		this.parseAssignmentProperties(assignment);
		if(TaskUID != null && ResourceUID!=null){
			MsProject.assignments.put(this.UID,this);
			MsTask myTask = MsProject.tasks.get(this.TaskUID);
			if(myTask!=null){
				myTask.addAssignment(this.UID);
			}
			//System.out.println("Assignment: "+this.UID);
		}
	}
	
	
	public void parseAssignmentProperties(Node assignment){
		if(assignment.getNodeType() == Node.ELEMENT_NODE){
			NodeList currNode;
			Element currElem;
			String currVal;		
			for(int i=0; i < numOfProps; i++){
				currNode = ((Element)assignment).getElementsByTagName(props[i]);
				if(currNode != null){
					currElem = (Element)currNode.item(0);
					if(currElem != null){
						currVal = currElem.getTextContent();
						if(currVal!=null){
							switch(i){
								case 0:{ this.UID = currVal; break;}
								case 1:{this.TaskUID = currVal;break;}
								case 2:{this.ResourceUID = currVal;break;}
								case 3:{this.Start = MsUtility.getDate(currVal);break;}
								case 4:{this.Finish = MsUtility.getDate(currVal);break;}
								case 5:{this.Units = new Integer(currVal).intValue();break;}
								case 6:{this.Work = MsUtility.getDuration(currVal);break;}	
								}
						}
					}
				}
			}
        }
	}


	public IXLinkInstance getLinkOPMInstance() {
		return linkOPMInstance;
	}


	public void setLinkOPMInstance(IXLinkInstance linkOPMInstance) {
		this.linkOPMInstance = linkOPMInstance;
	}


	public String getUID() {
		return UID;
	}


	public String getTaskUID() {
		return TaskUID;
	}


	public String getResourceUID() {
		return ResourceUID;
	}


	public java.util.Date getStart() {
		return Start;
	}


	public java.util.Date getFinish() {
		return Finish;
	}


	public int getUnits() {
		return Units;
	}


	public String[] getWork() {
		return Work;
	}


	public static String[] getProps() {
		return props;
	}


	public static int getNumOfProps() {
		return numOfProps;
	}

	
}
