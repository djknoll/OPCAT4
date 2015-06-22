package gui.projectStructure;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXSystemStructure;
import gui.opdProject.Opd;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmStructuralRelation;
/**
* <p>This class actually is a data structure that contains all information about
* all entities existing in user's project. Each Entry  in this data structure
* contains some entity and in this Entry we hold all logical information about
* this entity and information about all of its graphical representations.
* <p>This Class also contains information about all OPDs existing in user's
* project.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*/

public class MainStructure implements ISystemStructure, IXSystemStructure
{
	private Hashtable mStructure;
	private Hashtable opdsHash;
	private Hashtable graphicalRepresentations;
	private Hashtable ors;

/**
* Creates a MainStructure class and initializes its internal data structures.
*
*/

	public MainStructure()
	{
		mStructure = new Hashtable();
		opdsHash = new Hashtable();
		graphicalRepresentations = new Hashtable();
		ors = new Hashtable();
	}

/**
* Maps the specified key to the specified Entry in this MainStructure.
* Neither the key nor the Entry can be null.
* The Entry can be retrieved by calling the getElement method with a key that
* is equal to the original key.
*
* @param pKey entity id of entity which represented by Entry
* @param pEntry Entry which contains information about some entity in user's project
* @return the previous value of the specified key in this MainStruscure,
* or null if it did not have one.
*/

	public Entry put(long pKey, Entry pEntry)
	{
		return (Entry)(mStructure.put(new Long(pKey), pEntry));
	}

/**
* Maps the specified key to the specified Opd in this MainStructure.
* Neither the key nor the Opd can be null.
* The Opd can be retrieved by calling the getOpd method with a key that
* is equal to the original key.
*
* @param pKey opd id
* @param pOpd instance of OPD class that represents some OPD in user's project
* @return the previous value of the specified key in this MainStruscure,
* or null if it did not have one.
*/

	public Opd put(long pKey, Opd pOpd)
	{
		return (Opd)(opdsHash.put(new Long(pKey), pOpd));
	}

/**
* Maps the specified key to the specified GraphicalRelationRepresentationEntry in this MainStructure.
* Neither the key nor the GraphicalRelationRepresentationEntry can be null.
*
* @param pKey opd id
* @param gRepresentation GraphicalRelationRepresentationEntry - class representing graphically
* some fundamental strctural relation
* @return the previous value of the specified key in this MainStruscure,
* or null if it did not have one.
*/

	public GraphicalRelationRepresentation put(GraphicRepresentationKey pKey, GraphicalRelationRepresentation gRepresentation)
	{
		return (GraphicalRelationRepresentation)(graphicalRepresentations.put(pKey, gRepresentation));
	}

/**
* Returns the OPD to which the specified key is mapped in this MainStructure.
*
* @param pKey a key in this MainStructure
* @return the Opd to which the key is mapped in this MainStructure;
* null if the key is not mapped to any Opd in this MainStructure.
*/

	public Opd getOpd(long pKey)
	{
		if (pKey == -1)
		{
			return null;
		}

		return (Opd)(opdsHash.get(new Long(pKey)));
	}

	public IOpd getIOpd(long pKey)
	{
		return (IOpd)(opdsHash.get(new Long(pKey)));
	}

	public IXOpd getIXOpd(long pKey)
	{
		return (IXOpd)(opdsHash.get(new Long(pKey)));
	}


	public Opd deleteOpd(long pKey)
	{
		return (Opd)(opdsHash.remove(new Long(pKey)));
	}

/**
* Returns the Entry to which the specified key is mapped in this MainStructure.
*
* @param pKey a key in this MainStructure
* @return the Entry to which the key is mapped in this MainStructure;
* null if the key is not mapped to any Entry in this MainStructure.
*/

	public Entry getEntry(long pKey)
	{
		return (Entry)mStructure.get(new Long (pKey));
	}

	public IEntry getIEntry(long pKey)
	{
		return (IEntry)mStructure.get(new Long (pKey));
	}


	public IXEntry getIXEntry(long pKey)
	{
		return (IXEntry)mStructure.get(new Long (pKey));
	}

		public boolean removeEntry(long pKey)
		{
		  Object result = mStructure.remove(new Long(pKey));
		  if (result == null)
		  {
			return false;
		  }
		  else
		  {
			return true;
		  }
		}

		public boolean removeGraphicalRelationRepresentation(GraphicRepresentationKey pKey)
		{
		  GraphicalRelationRepresentation gRelR = (GraphicalRelationRepresentation)graphicalRepresentations.get(pKey);

		  if (gRelR == null)
		  {
			return false;
		  }
		  gRelR.removeFromContainer();
		  graphicalRepresentations.remove(pKey);
		  return true;
		}


/**
* Returns an enumeration of the Entries representing entities in user's project in this MainStructure.
* Use the Enumeration methods on the returned object to fetch the Entries sequentially
*
* @return an enumeration of the Entries in this MainStructure
*/
	public Enumeration getElements()
	{
		return mStructure.elements();
	}

/**
* Returns an enumeration of the OPDs in this MainStructure.
* Use the Enumeration methods on the returned object to fetch the OPDs sequentially
*
* @return an enumeration of the OPDs in this MainStructure
*/
	public Enumeration getOpds()
	{
		return opdsHash.elements();
	}

/**
* Returns an enumeration of the GraphicalRelationRepresentationEntry in this MainStructure.
* Use the Enumeration methods on the returned object to fetch the
* GraphicalRelationRepresentationEntry sequentially
*
* @return an enumeration of the GraphicalRelationRepresentationEntry in this MainStructure
*/
	public Enumeration getGraphicalRepresentations()
	{
          return graphicalRepresentations.elements();
	}

        public Enumeration getGraphicalRepresentationsInOpd(long opdId)
        {
          Vector resultVector = new Vector();
          GraphicalRelationRepresentation tempIns;

          for (Enumeration e = getGraphicalRepresentations(); e.hasMoreElements();)
          {
            tempIns = (GraphicalRelationRepresentation) e.nextElement();
            if (tempIns.getSource().getOpdId() == opdId) {
              resultVector.add(tempIns);
            }
          }

          return resultVector.elements();

        }

        public GraphicalRelationRepresentation getGraphicalRepresentation(GraphicRepresentationKey pKey)
	{
          return (GraphicalRelationRepresentation)graphicalRepresentations.get(pKey);
	}


		public ConnectionEdgeEntry getSourceEntry(OpmStructuralRelation relation)
		{
		  return (ConnectionEdgeEntry)mStructure.get(new Long(relation.getSourceId()));
		}

		public ConnectionEdgeEntry getSourceEntry(OpmProceduralLink link)
		{
		  return (ConnectionEdgeEntry)(mStructure.get(new Long(link.getSourceId())));
		}

		public ConnectionEdgeEntry getDestinationEntry(OpmStructuralRelation relation)
		{
		  return (ConnectionEdgeEntry)(mStructure.get(new Long(relation.getDestinationId())));
		}

		public ConnectionEdgeEntry getDestinationEntry(OpmProceduralLink link)
		{
		  return (ConnectionEdgeEntry)(mStructure.get(new Long(link.getDestinationId())));
		}

	/**
	 * This function returns all instances in the given OPD
	 */
	public Enumeration getInstancesInOpd(long opdId)
	{
		Vector resultVector = new Vector();
		Entry tempEnt;
		Instance tempIns;
		for(Enumeration ee = getElements(); ee.hasMoreElements();)
		{
			tempEnt = (Entry)ee.nextElement();
			for(Enumeration ie = tempEnt.getInstances(); ie.hasMoreElements();)
			{
				tempIns = (Instance)ie.nextElement();
				if(tempIns.getKey().getOpdId() == opdId)
				{
					resultVector.add(tempIns);
				}
			}
		}
		return resultVector.elements();
	}

	/**
	 * This function returns all instances in the given OPD
	 */
	public Enumeration getInstancesInOpd(Opd opd)
	{
		return getInstancesInOpd(opd.getOpdId());
	}


	/**
	 * This function returns all link instances in the given OPD
	 */
	public Enumeration getLinksInOpd(long opdId)
	{
		Vector resultVector = new Vector();
		Instance tempIns;
		for(Enumeration e = getInstancesInOpd(opdId); e.hasMoreElements();)
		{
			tempIns = (Instance)e.nextElement();
			if(tempIns instanceof LinkInstance)
			{
				resultVector.add(tempIns);
			}
		}

		return resultVector.elements();
	}

	/**
	 * This function returns all link instances in the given OPD
	 */
	public Enumeration getLinksInOpd(Opd opd)
	{
		return getLinksInOpd(opd.getOpdId());
	}


	/**
	 * This function returns all General Relations instances in the given OPD
	 */
	public Enumeration getGeneralRelationsInOpd(long opdId)
	{
		Vector resultVector = new Vector();
		Instance tempIns;
		for(Enumeration e = getInstancesInOpd(opdId); e.hasMoreElements();)
		{
			tempIns = (Instance)e.nextElement();
			if(tempIns instanceof GeneralRelationInstance)
			{
				resultVector.add(tempIns);
			}
		}

		return resultVector.elements();
	}

	/**
	 * This function returns all General Relations instances in the given OPD
	 */
	public Enumeration getGeneralRelationsInOpd(Opd opd)
	{
		return getGeneralRelationsInOpd(opd.getOpdId());
	}

	/**
	 * This function returns all Fundamental Relations instances in the given OPD
	 */
	public Enumeration getFundamentalRelationsInOpd(long opdId)
	{
		Vector resultVector = new Vector();
		Instance tempIns;
		for(Enumeration e = getInstancesInOpd(opdId); e.hasMoreElements();)
		{
			tempIns = (Instance)e.nextElement();
			if(tempIns instanceof FundamentalRelationInstance)
			{
				resultVector.add(tempIns);
			}
		}

		return resultVector.elements();
	}

	/**
	 * This function returns all Fundamental Relations instances in the given OPD
	 */
	public Enumeration getFundamentalRelationsInOpd(Opd opd)
	{
		return getFundamentalRelationsInOpd(opd.getOpdId());
	}

	/**
	 * This function returns all Things instances in the given OPD
	 */
	public Enumeration getThingsInOpd(long opdId)
	{
		Vector resultVector = new Vector();
		Instance tempIns;
		for(Enumeration e = getInstancesInOpd(opdId); e.hasMoreElements();)
		{
			tempIns = (Instance)e.nextElement();
			if(tempIns instanceof ThingInstance)
			{
				resultVector.add(tempIns);
			}
		}

		return resultVector.elements();
	}

	/**
	 * This function returns all Things instances in the given OPD
	 */
	public Enumeration getThingsInOpd(Opd opd)
	{
		return getThingsInOpd(opd.getOpdId());
	}

	public Enumeration getInstanceInOpd(Opd opd, long entityId)
	{
		Vector v = new Vector();
		Instance inst = null;
		for (Enumeration e = getInstancesInOpd(opd); e.hasMoreElements(); )
		{
			inst = (Instance)e.nextElement();
			if(inst.getEntry().getId() == entityId)
			{
				v.add(inst);
			}
		}
		if(v.size() > 0)
		{
			return v.elements();
		}
		return null;
	}
	
	/**
	 * Returns the number of entries that reside in the model.
	 * @return	Number of entries.
	 */
	public int getNumOfEntries()	{
		return mStructure.size();
	}
}
