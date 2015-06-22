package gui.projectStructure;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.util.BrowserLauncher;

/**
* <p>The base class for all kinds of Entries in MainStructure class. Each Entry
* represents some entity in user's project. Each Entry holds logical information
* about this entity (OpmEntity object). Also each Entry has a data structure that
* holds all graphical instances of this entity in special data structure.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public abstract class Entry implements IXEntry, IEntry
{
  protected OpmEntity logicalEntity;
  private Hashtable instances;
  protected OpdProject myProject;
  //reuseComment
  private boolean isLocked;
  private boolean isOpenReused;
  //endReuseComment



/**
* Creates Entry that holds all information about specified pEntity.
*
* @param pEntity object of OpmEntity class
*/
	public Entry(OpmEntity pEntity, OpdProject project)
	{
		logicalEntity = pEntity;
		instances = new Hashtable();
                myProject = project;
                //reuseComment
                isLocked=false;
                //endReuseComment
	}

/**
* Returns OpmEntity that represents logically this Entry's entity.
* We can retrieve all logical information about this entity
* from returned OpmEntity.
*
*/

	public OpmEntity getLogicalEntity()
	{
		return logicalEntity;
	}

/**
* Tests if the specified pKey is a key in data structure containing graphical instances
* of this Entry's entity.
*/
	public boolean containsKey(OpdKey pKey)
	{
		return instances.containsKey(pKey);
	}


/**
* Adds the specified pInstance with the specified key to the data structure containing graphical instances
* of this Entry's entity.
*
* @param key OpdKey - key of graphical instance of some entity.
* @param pInstance graphical Instance of some entity.
* @return true if specified key is a new key in data structure containing graphical instances
* of this Entry's entity. Returns false and does nothing if specified key already exist in the data structure.
*/
	public boolean addInstance(OpdKey key, Instance pInstance)
	{

		if (containsKey(key))
		{
			return false;
		}

		instances.put(key, pInstance);
		return true;
	}

/**
* Removes from the data structure containing graphical instances of this
* Entry's entity Instance with the specified pKey
*
* @param key OpdKey - key of graphical instance of some entity.
* @return true operation was successful . False if specified key doesn't exist
* in the data structure.
*/
	public boolean removeInstance(OpdKey pKey)
	{
          if (!containsKey(pKey))
          {
            return false;
          }

          Instance tempInstance = (Instance)instances.get(pKey);
          tempInstance.removeFromContainer();
          instances.remove(pKey);
          return true;
	}


/**
* Returns an enumeration of the Instances in this Entry.
* Use the Enumeration methods on the returned object to fetch the
* Instances sequentially
*
* @return an enumeration of the Instances in this MainStructure
*/

	public Enumeration getInstances()
	{
		return instances.elements();
	}

/**
* Returns the Instance to which the specified key is mapped in data structure
* containing graphical instances of this Entry's entity.
*
* @param key OpdKey - key of graphical instance of some entity.
* @return the Instance to which the key is mapped in data structure
* containing graphical instances of this Entry's entity;
* null if the key is not mapped to any Entry in this MainStructure.
*/
	public Instance getInstance(OpdKey pKey)
	{
		return (Instance)instances.get(pKey);
	}

	public IXInstance getIXInstance(OpdKey pKey)
	{
		return (IXInstance)instances.get(pKey);
	}

    public IInstance getIInstance(OpdKey pKey)
    {
        return (IInstance)instances.get(pKey);
    }

/**
* Returns the number of graphical instances of this Entry's entity.
*
*/
	public int getInstancesNumber()
	{
		return instances.size();
	}

    public boolean hasInstanceInOpd(long opdNumber)
    {
        for (Enumeration e = getInstances(); e.hasMoreElements();)
		{
            Instance currInstance = (Instance)e.nextElement();
            if (currInstance.getKey().getOpdId() == opdNumber)
            {
                return true;
            }
        }

        return false;

    }

    public boolean isEmpty()
    {
        return instances.isEmpty();
    }

    public long getId()
    {
        return logicalEntity.getId();
    }


  	public String getUrl()
    {
        return logicalEntity.getUrl();
    }

/**
* Sets the string to be entity url
*
* @param	url entity url
*/
 	public void setUrl(String url)
    {
        logicalEntity.setUrl(url);
    }
    
    
  	public String getName()
    {
        return logicalEntity.getName();
    }

/**
* Sets the string to be entity name.
*
* @param	name entity name
*/
 	public void setName(String name)
    {
        logicalEntity.setName(name);
    }

/**
* Returns entity description.
*
* @return a String with the entity description
*/
 	public String getDescription()
    {
        return logicalEntity.getDescription();
    }

/**
* Sets the string to be entity description.
*
* @param description description of the entity
**/
 	public void setDescription(String description)
    {
        logicalEntity.setDescription(description);
    }


/**
* Sets the enviromental/system property of OpmEntity. If value of
* enviromental is true it's a enviromental thing, otherwise system
*
*/

	public void setEnvironmental(boolean environmental)
    {
        logicalEntity.setEnviromental(environmental);
    }


/**
* Returns true if this OpmEntity is enviromental. If it's system
* returns false
*
*/

	public boolean isEnvironmental()
    {
        return logicalEntity.isEnviromental();
    }


    public abstract void updateInstances();
    // ReuseComment
    public void commitLock()
    {
      isLocked=true;
    }
    public void releaseLock()
    {
      isLocked=false;
    }
    public boolean isLocked()
    {
      return  isLocked;
    }
    public void setOpenReused(boolean aBooleanVal)
    {
      isOpenReused=aBooleanVal;
    }
    public boolean isOpenReused()
    {
      return isOpenReused;
    }
    //endReuseCommnet
    
	
	/**
	* Shows the thing URL using the defualt browser
	*/
	public void ShowUrl()
	{
		try {
			if (!(getUrl().equalsIgnoreCase("none") || getUrl().equalsIgnoreCase("")))  
			BrowserLauncher.openURL(getUrl());
		} catch (IOException e ) {
			
		}
	}
	    
	public boolean hasURL() {
		return (!(getUrl().equalsIgnoreCase("none")) && !(getUrl().equals(""))) ; 
	}

}
