package gui.projectStructure;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.util.BrowserLauncher2;

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
		this.logicalEntity = pEntity;
		this.instances = new Hashtable();
                this.myProject = project;
                //reuseComment
                this.isLocked=false;
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
		return this.logicalEntity;
	}

/**
* Tests if the specified pKey is a key in data structure containing graphical instances
* of this Entry's entity.
*/
	public boolean containsKey(OpdKey pKey)
	{
		return this.instances.containsKey(pKey);
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

		if (this.containsKey(key))
		{
			return false;
		}

		this.instances.put(key, pInstance);
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
          if (!this.containsKey(pKey))
          {
            return false;
          }

          Instance tempInstance = (Instance)this.instances.get(pKey);
          tempInstance.removeFromContainer();
          this.instances.remove(pKey);
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
		return this.instances.elements();
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
		return (Instance)this.instances.get(pKey);
	}

	public IXInstance getIXInstance(OpdKey pKey)
	{
		return (IXInstance)this.instances.get(pKey);
	}

    public IInstance getIInstance(OpdKey pKey)
    {
        return (IInstance)this.instances.get(pKey);
    }

/**
* Returns the number of graphical instances of this Entry's entity.
*
*/
	public int getInstancesNumber()
	{
		return this.instances.size();
	}

    public boolean hasInstanceInOpd(long opdNumber)
    {
        for (Enumeration e = this.getInstances(); e.hasMoreElements();)
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
        return this.instances.isEmpty();
    }

    public long getId()
    {
        return this.logicalEntity.getId();
    }


  	public String getUrl()
    {
        return this.logicalEntity.getUrl();
    }

/**
* Sets the string to be entity url
*
* @param	url entity url
*/
 	public void setUrl(String url)
    {
        this.logicalEntity.setUrl(url);
    }
    
    
  	public String getName()
    {
        return this.logicalEntity.getName();
    }

/**
* Sets the string to be entity name.
*
* @param	name entity name
*/
 	public void setName(String name)
    {
        this.logicalEntity.setName(name);
    }

/**
* Returns entity description.
*
* @return a String with the entity description
*/
 	public String getDescription()
    {
        return this.logicalEntity.getDescription();
    }

/**
* Sets the string to be entity description.
*
* @param description description of the entity
**/
 	public void setDescription(String description)
    {
        this.logicalEntity.setDescription(description);
    }


/**
* Sets the enviromental/system property of OpmEntity. If value of
* enviromental is true it's a enviromental thing, otherwise system
*
*/

	public void setEnvironmental(boolean environmental)
    {
        this.logicalEntity.setEnviromental(environmental);
    }


/**
* Returns true if this OpmEntity is enviromental. If it's system
* returns false
*
*/

	public boolean isEnvironmental()
    {
        return this.logicalEntity.isEnviromental();
    }


    public abstract void updateInstances();
    // ReuseComment
    public void commitLock()
    {
      this.isLocked=true;
    }
    public void releaseLock()
    {
      this.isLocked=false;
    }
    public boolean isLocked()
    {
      return  this.isLocked;
    }
    public void setOpenReused(boolean aBooleanVal)
    {
      this.isOpenReused=aBooleanVal;
    }
    public boolean isOpenReused()
    {
      return this.isOpenReused;
    }
    //endReuseCommnet
    
	
	/**
	* Shows the thing URL using the defualt browser
	*/
	public void ShowUrl()
	{
		if (!(this.getUrl().equalsIgnoreCase("none") || this.getUrl().equalsIgnoreCase(""))) {
			BrowserLauncher2.openURL(this.getUrl());
		}
	}
	    
	public boolean hasURL() {
		return (!(this.getUrl().equalsIgnoreCase("none")) && !(this.getUrl().equals(""))) ; 
	}
	
	/**
	 * return all the Thinginstances inside the zoomed in entry. 
	 * without the links
	 * @param opd
	 * @return
	 */
	public Vector GetInZoomedIncludedInstances(Opd opd) {
		Enumeration insEnum  = this.myProject.getSystemStructure().getInstancesInOpd(opd.getOpdId()) ; 
		Vector vec = new Vector(); 
		
		while (insEnum.hasMoreElements() ) {
			Object obj = insEnum.nextElement() ; 
			if (obj instanceof ThingInstance) {
				ThingInstance thingInstance = (ThingInstance) obj ; 
				if((thingInstance.getParent() != null) && (thingInstance.getParent().getEntry().getId() == this.getId()) ) {
					vec.add(thingInstance) ; 
				}
			}
		}
		
		return vec ; 
	}

}
