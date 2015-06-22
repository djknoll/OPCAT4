package extensionTools.MsProject.MsProjectDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessInstance;
import extensionTools.MsProject.MsUtility;

public class MsResource {
	private Node myXMLResource;
	private String resourceName;
	private String UID;
	private String ID;
	private String[] Work;
	private int Type;
	private double Cost;
	private int MaxUnits;
	private int PeakUnits;
	private boolean wasUpdated = false;
	private IXObjectEntry objOPMEntry;
	static String[] props = {"UID", "ID", "Name", "Type", "MaxUnits", "PeakUnits", 
			"Work", "Cost"};
	static int numOfProps = 8;
	
	public MsResource(Node resource){
		if(resource == null)return;
		this.parseTaskProperties(resource);
		if(resourceName != null){
			MsProject.resources.put(UID, this);
			//System.out.println("Resource: "+this.resourceName);
		}
	}
	
	public void parseTaskProperties(Node resource){
		if(resource.getNodeType() == Node.ELEMENT_NODE){
			NodeList currNode;
			Element currElem;
			String currVal;		
			for(int i=0; i < numOfProps; i++){
				currNode = ((Element)resource).getElementsByTagName(props[i]);
				if(currNode != null){
					currElem = (Element)currNode.item(0);
					if(currElem != null){
						currVal = currElem.getTextContent();
						if(currVal!=null){
							switch(i){
								case 0:{ this.UID = currVal; break;}
								case 1:{this.ID = currVal;break;}
								case 2:{this.resourceName = currVal;break;}
								case 3:{this.Type = new Integer(currVal).intValue();break;}
								case 4:{this.MaxUnits = new Integer(currVal).intValue();break;}
								case 5:{this.PeakUnits = new Integer(currVal).intValue();break;}
								case 6:{this.Work = MsUtility.getDuration(currVal);break;}
								case 7:{this.Cost = new Double(currVal).doubleValue(); break;}
								}
						}
					}
				}
			}
        }
	}

	public boolean isWasUpdated() {
		return wasUpdated;
	}

	public void setWasUpdated(boolean wasUpdated) {
		this.wasUpdated = wasUpdated;
	}

	public IXObjectEntry getObjOPMEntry() {
		return objOPMEntry;
	}

	public void setObjOPMEntry(IXObjectEntry objOPMEntry) {
		this.objOPMEntry = objOPMEntry;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getUID() {
		return UID;
	}

	public String getID() {
		return ID;
	}

	public String[] getWork() {
		return Work;
	}

	public int getType() {
		return Type;
	}

	public double getCost() {
		return Cost;
	}

	public int getMaxUnits() {
		return MaxUnits;
	}

	public int getPeakUnits() {
		return PeakUnits;
	}
}
