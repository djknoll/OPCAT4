package gui.projectStructure;

import exportedAPI.opcatAPI.IConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.Constants;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmFundamentalRelation;
import gui.opmEntities.OpmGeneralRelation;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmStructuralRelation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
* <p>This class is super class for ThingEntry and StateEntry,
* both of them  have common property - can be connected
* by links or relations, therefore they should have a common superclass.
* <p>In this class we hold information (in special data structures) about links
* and relations in which this  ConnectionEdgeEntry participates as source or
* destination. This information is very useful for Unfolding and Zooming in operations.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class ConnectionEdgeEntry extends Entry implements IConnectionEdgeEntry, IXConnectionEdgeEntry
{
	private Hashtable linkSources;
	private Hashtable linkDestinations;
	private Hashtable relationSources;
	private Hashtable relationDestinations;
	private LinkedList generalRelationSources;
	private LinkedList generalRelationDestinations;

/**
* Creates ConnectionEdgeEntry that holds all information about specified pEdge.
*
* @param pEdge object of OpmConnectionEdge class.
*/
	public ConnectionEdgeEntry(OpmConnectionEdge pEdge, OpdProject project)
	{
		super(pEdge, project);
		linkSources = new Hashtable();
		linkDestinations = new Hashtable();
		relationSources = new Hashtable();
		relationDestinations = new Hashtable();
		generalRelationSources = new LinkedList();
		generalRelationDestinations = new LinkedList();
	}

/**
* This method adds pRelation to the data structure holding all structural relations
* in which this ConnectionEdgeEntry participates as source.
*
*/

	public void addRelationSource(OpmFundamentalRelation pRelation)
	{
		relationSources.put(new RelationKey(Constants.getType4Relation(pRelation), pRelation.getDestinationId()), pRelation);
		return;
	}

	public void removeRelationSource(OpmFundamentalRelation pRelation)
	{
		relationSources.remove(new RelationKey(Constants.getType4Relation(pRelation), pRelation.getDestinationId()));
		return;
	}

	public void addGeneralRelationSource(OpmGeneralRelation pRelation)
	{
		if (!generalRelationSources.contains(pRelation))
		{
			generalRelationSources.add(pRelation);
		}
		return;
	}

	public void removeGeneralRelationSource(OpmGeneralRelation pRelation)
	{
		generalRelationSources.remove(pRelation);
		return;
	}


/**
* Returns OpmStructuralRelation of pRelationType(as it specified in Interface Constants in opmEntities package)
* in which entity with pEntityId participates as destination and this
* ConnectionEdgeEntry participates as source.
* null is returned if there is no such OpmStructuralRelation in data structure.
*/

	public OpmStructuralRelation getRelationByDestination(long pEntityId, int pRelationType)
	{
		return (OpmStructuralRelation)(relationSources.get(new RelationKey(pRelationType, pEntityId)));
	}

/**
* This method adds pRelation to the data structure holding all structural relations
* in which this ConnectionEdgeEntry participates as destination.
*
*/
	public void addRelationDestination(OpmFundamentalRelation pRelation)
	{
		relationDestinations.put(new RelationKey(Constants.getType4Relation(pRelation), pRelation.getSourceId()), pRelation);
		return;
	}

	public void removeRelationDestination(OpmFundamentalRelation pRelation)
	{
		relationDestinations.remove(new RelationKey(Constants.getType4Relation(pRelation), pRelation.getSourceId()));
		return;
	}


	public void addGeneralRelationDestination(OpmGeneralRelation pRelation)
	{
		if (!generalRelationDestinations.contains(pRelation))
		{
			generalRelationDestinations.add(pRelation);
		}
		return;
	}

	public void removeGeneralRelationDestination(OpmGeneralRelation pRelation)
	{
		generalRelationDestinations.remove(pRelation);
		return;
	}


/**
* Returns OpmStructuralRelation of pRelationType(as it specified in Interface Constants in opmEntities package)
* in which entity with pEntityId participates as source and this
* ConnectionEdgeEntry participates as destination.
* null is returned if there is no such OpmStructuralRelation in data structure.
*/

	public OpmStructuralRelation getRelationBySource(long pEntityId, int pRelationType)
	{
		return (OpmStructuralRelation)(relationDestinations.get(new RelationKey(pRelationType, pEntityId)));
	}

/**
* This method adds pLink to the data structure holding all procedural links
* in which this ConnectionEdgeEntry participates as source.
*
*/

	public void addLinkSource(OpmProceduralLink pLink)
	{
		  linkSources.put(new RelationKey(Constants.getType4Link(pLink), pLink.getDestinationId()), pLink);
		  return;
		}

		public void removeLinkSource(OpmProceduralLink pLink)
	{
		  linkSources.remove(new RelationKey(Constants.getType4Link(pLink), pLink.getDestinationId()));
		  return;
		}

/**
* Returns OpmProceduralLink of pLinkType(as it specified in Interface Constants in opmEntities package)
* in which entity with pEntityId participates as destination and this
* ConnectionEdgeEntry participates as source.
* null is returned if there is no such OpmProceduralLink in data structure.
*/

	public OpmProceduralLink getLinkByDestination(long pEntityId, int pLinkType)
	{
		return (OpmProceduralLink)(linkSources.get(new RelationKey(pLinkType, pEntityId)));
	}

/**
* This method adds pLink to the data structure holding all procedural links
* in which this ConnectionEdgeEntry participates as destination.
*
*/

	public void addLinkDestination(OpmProceduralLink pLink)
	{
		  linkDestinations.put(new RelationKey(Constants.getType4Link(pLink), pLink.getSourceId()), pLink);
		  return;
		}

	public void removeLinkDestination(OpmProceduralLink pLink)
	{
		  linkDestinations.remove(new RelationKey(Constants.getType4Link(pLink), pLink.getSourceId()));
		  return;
		}

/**
* Returns OpmProceduralLink of pLinkType(as it specified in Interface Constants in opmEntities package)
* in which entity with pEntityId participates as source and this
* ConnectionEdgeEntry participates as destination.
* null is returned if there is no such OpmProceduralLink in data structure.
*/
	public OpmProceduralLink getLinkBySource(long pEntityId, int pLinkType)
	{
		return (OpmProceduralLink)(linkDestinations.get(new RelationKey(pLinkType, pEntityId)));
	}

/**
* Returns an enumeration of the OpmStructuralRelation in which this ConnectionEdgeEntry
* paricipates as source.
* Use the Enumeration methods on the returned object to fetch the
* OpmStructuralRelation sequentially
*
*/
	public Enumeration getSourceRelations()
	{
		return relationSources.elements();
	}

	public Iterator getSourceGeneralRelations()
	{
		return generalRelationSources.iterator();
	}

	/**
	* Returns Enumeration of IRelationEntry - all srtructural relations having
    * this IConnectionEdgeEntry as source. Use the Enumeration methods on the
    * returned object to fetch the IRelationEntry sequentially
	*/

    public Enumeration getRelationBySource()
    {
        Vector tVect = new Vector();
        for (Enumeration e = getSourceRelations();e.hasMoreElements();)
        {
            OpmStructuralRelation tRel = (OpmStructuralRelation)e.nextElement();
            tVect.add(myProject.getComponentsStructure().getEntry(tRel.getId()));
        }

        for (Iterator i = getSourceGeneralRelations();i.hasNext();)
        {
            OpmStructuralRelation tRel = (OpmStructuralRelation)i.next();
            tVect.add(myProject.getComponentsStructure().getEntry(tRel.getId()));
        }

        return tVect.elements();
    }


/**
* Returns an enumeration of the OpmStructuralRelation in which this ConnectionEdgeEntry
* paricipates as destination.
* Use the Enumeration methods on the returned object to fetch the
* OpmStructuralRelation sequentially
*
*/
	public Enumeration getDestinationRelations()
	{
		return relationDestinations.elements();
	}

	public Iterator getDestinationGeneralRelations()
	{
		return generalRelationDestinations.iterator();
	}

    /**
	* Returns Enumeration of RelationEntry - all structural relations having
    * this ConnectionEdgeEntry as destination. Use the Enumeration methods on
    * the returned object to fetch the RelationEntry sequentially
	*/

    public Enumeration getRelationByDestination()
    {
        Vector tVect = new Vector();
        for (Enumeration e = getDestinationRelations();e.hasMoreElements();)
        {
            OpmStructuralRelation tRel = (OpmStructuralRelation)e.nextElement();
            tVect.add(myProject.getComponentsStructure().getEntry(tRel.getId()));
        }

        for (Iterator i = getDestinationGeneralRelations();i.hasNext();)
        {
            OpmStructuralRelation tRel = (OpmStructuralRelation)i.next();
            tVect.add(myProject.getComponentsStructure().getEntry(tRel.getId()));
        }

        return tVect.elements();
    }


/**
* Returns an enumeration of the OpmProceduralLink in which this ConnectionEdgeEntry
* paricipates as source.
* Use the Enumeration methods on the returned object to fetch the
* OpmProceduralLink sequentially
*
*/
	public Enumeration getSourceLinks()
	{
		return linkSources.elements();
	}

	/**
	* Returns Enumeration of LinkEntry - all links having this
    * ConnectionEdgeEntry as source. Use the Enumeration methods on the
    * returned object to fetch the LinkEntry sequentially
	*/

    public Enumeration getLinksBySource()
    {

        Vector tVect = new Vector();
        for (Enumeration e = getSourceLinks(); e.hasMoreElements();)
        {
            OpmProceduralLink tLink = (OpmProceduralLink)e.nextElement();
            tVect.add(myProject.getComponentsStructure().getEntry(tLink.getId()));
        }

        return tVect.elements();
    }

/**
* Returns an enumeration of the OpmProceduralLink in which this ConnectionEdgeEntry
* paricipates as destination.
* Use the Enumeration methods on the returned object to fetch the
* OpmProceduralLink sequentially
*
*/
	public Enumeration getDestinationLinks()
	{
		return linkDestinations.elements();
	}

	/**
	* Returns Enumeration of LinkEntry - all links having this
    * ConnectionEdgeEntry as destination. Use the Enumeration methods on the
    * returned object to fetch the LinkEntry sequentially
	*/

    public Enumeration getLinksByDestination()
    {

        Vector tVect = new Vector();
        for (Enumeration e = getDestinationLinks(); e.hasMoreElements();)
        {
            OpmProceduralLink tLink = (OpmProceduralLink)e.nextElement();
            tVect.add(myProject.getComponentsStructure().getEntry(tLink.getId()));
        }

        return tVect.elements();
    }


	public Enumeration getSourceRelations(int relType)
	{
		OpmStructuralRelation tmpRel;
		Vector v = new Vector();
		for(Enumeration e = getSourceRelations();e.hasMoreElements();)
		{
			tmpRel = (OpmStructuralRelation)e.nextElement();
			if(Constants.getType4Relation(tmpRel) == relType)
			{
				v.addElement(tmpRel);
			}
		}
		return v.elements();
	}

	public Enumeration getDestinationRelations(int relType)
	{
		OpmStructuralRelation tmpRel;
		Vector v = new Vector();
		for(Enumeration e = relationDestinations.elements(); e.hasMoreElements();)
		{
			tmpRel = (OpmStructuralRelation)e.nextElement();
			if(Constants.getType4Relation(tmpRel) == relType)
			{
				v.addElement(tmpRel);
			}
		}
		return v.elements();
	}

	public Enumeration getSourceLinks(int linkType)
	{
		OpmProceduralLink tmpLink;
		Vector v = new Vector();
		for(Enumeration e = linkSources.elements(); e.hasMoreElements();)
		{
			tmpLink = (OpmProceduralLink)e.nextElement();
			if(Constants.getType4Link(tmpLink) == linkType)
			{
				v.addElement(tmpLink);
			}
		}
		return v.elements();
	}


	public Enumeration getDestinationLinks(int linkType)
	{
		OpmProceduralLink tmpLink;
		Vector v = new Vector();
		for(Enumeration e = linkDestinations.elements(); e.hasMoreElements();)
		{
			tmpLink = (OpmProceduralLink)e.nextElement();
			if(Constants.getType4Link(tmpLink) == linkType)
			{
				v.addElement(tmpLink);
			}
		}
		return v.elements();
	}

/**
* Returns an enumeration of OpmEntity (all relations and links) in which
* this ConnectionEdgeEntry paricipates.
* Use the Enumeration methods on the returned object to fetch the
* OpmStructuralRelation sequentially
*
*/

	public Enumeration getAllRelatedEntities()
	{
		Vector v = new Vector();

		v.addAll(linkSources.values());
		v.addAll(linkDestinations.values());
		v.addAll(relationSources.values());
		v.addAll(relationDestinations.values());

		Object tempArray[] = generalRelationSources.toArray();

		for (int i=0; i<tempArray.length;i++)
		{
			v.add(tempArray[i]);
		}

		tempArray = generalRelationDestinations.toArray();

		for (int i=0; i<tempArray.length;i++)
		{
			v.add(tempArray[i]);
		}

		return v.elements();
	}


		public void updateInstances()
		{
		  for (Enumeration e = getInstances() ; e.hasMoreElements();)
		  {
			((ConnectionEdgeInstance)(e.nextElement())).update();
		  }
		}
}
