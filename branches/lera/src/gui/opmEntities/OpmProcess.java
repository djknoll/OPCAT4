package gui.opmEntities;

import exportedAPI.opcatAPIx.IXProcessEntry;
import gui.Opcat2;

import java.util.ArrayList;



/**
* This class represents Process in OPM.
* For better understanding of this class you should be familiar with OPM.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*/

public class OpmProcess extends OpmThing
{
//---------------------------------------------------------------------------
// The private attributes/members are located here
	private String processBody;
	private String maxTimeActivation;
	private String minTimeActivation;
	private ArrayList<Predecessor>predessesors;
	private boolean isCritical = false;

/**
* Creates an OpmProcess with specified id and name. Id of created OpmProcess
* must be unique in OPCAT system. Other data members of OpmProcess get default values.
*
* @param id OpmProcess id
* @param name OpmProcess name
*/
	public OpmProcess(long processId,String processName)
	{
		super(processId,processName);
		this.maxTimeActivation = "infinity";
		this.minTimeActivation = new String("0;0;0;0;0;0;0");
		this.processBody = "none";
		this.predessesors = new ArrayList<Predecessor>();
	}


//---------------------------------------------------------------------------
// The public methods are located here

    public void copyPropsFrom(OpmProcess origin)
    {
        super.copyPropsFrom(origin);
		this.maxTimeActivation = origin.getMaxTimeActivation();
		this.minTimeActivation = origin.getMinTimeActivation();
		this.processBody = origin.getProcessBody();
		this.predessesors = origin.predessesors;
		//	Added by Eran Toch
//		rolesManager = origin.getRolesManager();
    }
    
    public void addPredecessors(ArrayList<Predecessor> preds){
    	this.predessesors = new ArrayList<Predecessor>();
    	for(int i=0;i<preds.size();i++){
    		this.addPredecessor(preds.get(i));
    	} 	
    }

    public boolean hasSameProps(OpmProcess pProcess)
    {
        return (super.hasSameProps(pProcess) &&
                this.maxTimeActivation.equals(pProcess.getMaxTimeActivation()) &&
                this.minTimeActivation.equals(pProcess.getMinTimeActivation()) &&
                this.processBody.equals(pProcess.getProcessBody()));
    }


/**
* Returns process body of this OpmProcess
*
*/
	public String getProcessBody()
	{
		return this.processBody;
	}

/**
* Sets process body of this OpmProcess
*
*/
	public void setProcessBody(String processBody)
	{
		this.processBody = processBody;
	}

/**
* Returns String representing maximum activation time
* of this OpmProcess. This String contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/

	public String getMaxTimeActivation()
	{
		return this.maxTimeActivation;
	}

/**
* Sets  maximum activation time
* of this OpmProcess. This field contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/

	public void setMaxTimeActivation(String time)
	{
		 this.maxTimeActivation = time;
	}

/**
* Returns String representing minimum activation time
* of this OpmProcess. This String contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/

	public String getMinTimeActivation()
	{
		return this.minTimeActivation;
	}


/**
* Sets  minimum activation time
* of this OpmProcess. This field contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/

	public void setMinTimeActivation(String time)
	{
		 this.minTimeActivation = time;
	}
	
	public java.util.Vector<IXProcessEntry> getProcessPredecessorsByType(int type){
		java.util.Vector<IXProcessEntry> nbrs = new java.util.Vector<IXProcessEntry>();
		for(int i=0; i<predessesors.size(); i++){
			Predecessor p = this.predessesors.get(i);
			if(p.getType() == type){
				nbrs.add((IXProcessEntry)Opcat2.getCurrentProject().getIXSystemStructure().getIXEntry(p.getProcessID()));
			}
		}
		return nbrs;
	}
	
	public void addPredecessor(long predecessorID, int type){
		addPredecessor(new Predecessor(predecessorID, type));
	}
	public void addPredecessor(Predecessor pred){
		if(pred.getProcessID() == this.getId())return;
		for(int i=0; i<predessesors.size();i++){
			Predecessor p = this.predessesors.get(i);
			if(p.getProcessID() == pred.getProcessID() && !predecessorsCompatible(p,pred))
				return;
		}
		this.predessesors.add(pred);
	}
	
	public boolean predecessorsCompatible(Predecessor p1, Predecessor p2){
		int type1 = p1.getType();
		int type2 = p2.getType();
		if(type1 == type2) return false;
		if(type1 == Predecessor.FF & type2 == Predecessor.SS)return true;
		if(type1 == Predecessor.SS & type2 == Predecessor.FF)return true;
		return false;
	}
	
	public ArrayList<Predecessor> getPredecessors(){
		return this.predessesors;
	}
	
	public void removePredecessor(long processID){
		for(int i=0; i<this.predessesors.size();i++){
			Predecessor curr = this.predessesors.get(i);
			if(curr.getProcessID() == processID){
				this.predessesors.remove(i); 
				return;
			}
		}
	};

}