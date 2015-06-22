package gui.projectStructure;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPIx.IXLinkEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.Constants;
import gui.opmEntities.OpmProceduralLink;

import java.util.Enumeration;


/**
* <p>This class represents entry of OPM procedural link.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class LinkEntry extends Entry implements IXLinkEntry, ILinkEntry
{

/**
* Creates LinkEntry that holds all information about specified pLink.
*
* @param pLink object of OpmProceduralLink class.
*/

	public LinkEntry(OpmProceduralLink pLink, OpdProject project)
	{
		super(pLink, project);
	}

    public void updateInstances()
    {
        for (Enumeration e = getInstances() ; e.hasMoreElements();)
        {
            LinkInstance tempInstance = (LinkInstance)(e.nextElement());
            tempInstance.update();
        }
    }


    public int getLinkType()
    {
        return Constants.getType4Link((OpmProceduralLink)logicalEntity);
    }


/**
* Returns the source id of this OpmProceduralLink.
*
*/

	public long getSourceId()
    {
        return ((OpmProceduralLink)logicalEntity).getSourceId();
    }

/**
* Returns the destination id of this OpmProceduralLink.
*
*/
	public long getDestinationId()
    {
        return ((OpmProceduralLink)logicalEntity).getDestinationId();
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
        return ((OpmProceduralLink)logicalEntity).getMinReactionTime();
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
        ((OpmProceduralLink)logicalEntity).setMinReactionTime(minimumReactionTime);
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
        return ((OpmProceduralLink)logicalEntity).getMaxReactionTime();
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
        ((OpmProceduralLink)logicalEntity).setMaxReactionTime(maximumReactionTime);
    }

/**
* Returns condition of this OpmProceduralLink
*
*/
	public String getCondition()
    {
        return ((OpmProceduralLink)logicalEntity).getCondition();
    }

/**
* Sets condition of this OpmProceduralLink
*
*/
	public void setCondition(String condition)
    {
        ((OpmProceduralLink)logicalEntity).setCondition(condition);
    }

/**
* Returns path of this OpmProceduralLink
*
*/
	public String getPath()
    {
        return ((OpmProceduralLink)logicalEntity).getPath();
    }

/**
* Sets path of this OpmProceduralLink
*
*/
	public void setPath(String path)
    {
        ((OpmProceduralLink)logicalEntity).setPath(path);
    }


}
