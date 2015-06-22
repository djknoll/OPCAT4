package gui.opmEntities;


/**
* The Base Class for all procedural links existing in OPM.
* For better understanding of this class you should be familiar with OPM.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*/


public abstract class OpmProceduralLink extends OpmEntity
{
//---------------------------------------------------------------------------
// The private attributes/members are located here
	private long sourceId;
	private long destinationId;
	private String minReactionTime;
	private String maxReactionTime;
	private String condition;
	private String path;

/**
* Creates an OpmProceduralLink with specified id and name. Id of created OpmProceduralLink
* must be unique in OPCAT system
*
* @param id OpmProceduralLink id
* @param name OpmProceduralLink name
*/

	public OpmProceduralLink(long linkId,String linkName,long sourceId, long destinationId)
	{
		super(linkId,linkName);
		maxReactionTime = "infinity";
		minReactionTime = "0;0;0;0;0;0;0";
		condition = "";
		path = "";
        this.sourceId = sourceId;
		this.destinationId = destinationId;
	}

    public void copyPropsFrom(OpmProceduralLink origin)
    {
        super.copyPropsFrom(origin);
		maxReactionTime = origin.getMaxReactionTime();
		minReactionTime = origin.getMinReactionTime();
		condition = origin.getCondition();
		path = origin.getPath();
    }

/**
* Returns the source id of this OpmProceduralLink.
*
*/

	public long getSourceId()
	{
	  return sourceId;
	}


/**
* Returns the destination id of this OpmProceduralLink.
*
*/
	public long getDestinationId()
	{
		return destinationId;
	}


/**
* Returns String representing minimum reaction time
* of this OpmProceduralLink. This String contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/
	public String getMinReactionTime()
	{
		return minReactionTime;
	}

/**
* Sets  minimum reaction time
* of this OpmProceduralLink. This field contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/
	public void setMinReactionTime(String minimumReactionTime)
	{
		minReactionTime=minimumReactionTime;
	}

/**
* Returns String representing maximum reaction time
* of this OpmProceduralLink. This String contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/
 	public String getMaxReactionTime()
	{
		return maxReactionTime;
	}

/**
* Sets  maximum reaction time
* of this OpmProceduralLink. This field contains non-negative integer X 7
* (msec, sec, min, hours, days, months, years) with semi-colons
* separation.
*
*/
	public void setMaxReactionTime(String maximumReactionTime)
	{
		maxReactionTime=maximumReactionTime;
	}

/**
* Returns condition of this OpmProceduralLink
*
*/
	public String getCondition()
	{
		return condition;
	}

/**
* Sets condition of this OpmProceduralLink
*
*/
	public void setCondition(String condition)
	{
		this.condition=condition;
	}

/**
* Returns path of this OpmProceduralLink
*
*/
	public String getPath()
	{
		return path;
	}

/**
* Sets path of this OpmProceduralLink
*
*/
	public void setPath(String path)
	{
		this.path=path;
	}

}