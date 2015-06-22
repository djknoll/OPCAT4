package gui.opmEntities;


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
	}


//---------------------------------------------------------------------------
// The public methods are located here

    public void copyPropsFrom(OpmProcess origin)
    {
        super.copyPropsFrom(origin);
		this.maxTimeActivation = origin.getMaxTimeActivation();
		this.minTimeActivation = origin.getMinTimeActivation();
		this.processBody = origin.getProcessBody();
		//	Added by Eran Toch
//		rolesManager = origin.getRolesManager();
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

}