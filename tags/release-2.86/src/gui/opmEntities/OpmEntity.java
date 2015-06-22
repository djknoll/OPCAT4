package gui.opmEntities;

//-----------------------------------------------------------------
/**
* The base class for all OPM logical entities. This class represents
* logicaly(not graphically) all entities which exist in OPM methodology,
* and its (and its subclasses) purpose is only to store all general
* information about this entity.
* For better understanding of this class you should be familiar with OPM.
*
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/
//-----------------------------------------------------------------

public abstract class OpmEntity
 {

//---------------------------------------------------------------------------
// The private attributes/members are located here
	private long entityId;
	private String entityName;
	private String entityUrl = ""; 
	private String description;
    private boolean enviromental; // true if enviromental, false if system

/**
* Creates an OpmEntity with specified id and name. Id of created OpmEntity
* must be unique in OPCAT system
*
* @param id entity id
* @param name entity name
*/

	public OpmEntity(long id,String name)
	{
		this.entityId = id;
		this.entityName = name;
		this.description = "none";
        this.enviromental=false;
        this.entityUrl="none";
	}
	
	public OpmEntity(long id,String name, String url)
	{
		this.entityId = id;
		this.entityName = name;
		this.description = "none";
        this.enviromental=false;
        this.entityUrl = url ; 
	}

    protected void copyPropsFrom(OpmEntity origin)
    {
        this.entityName = origin.getName();
        this.description = origin.getDescription();
		this.enviromental=origin.isEnviromental();
		this.entityUrl = origin.getUrl();
    }

    protected boolean hasSameProps(OpmEntity pEntity)
    {
        return (this.entityName.equals(pEntity.getName()) &&
                this.description.equals(pEntity.getDescription()) &&
                this.entityUrl.equals(pEntity.getUrl()) && 
                (this.enviromental == pEntity.isEnviromental()));
    }

//---------------------------------------------------------------------------
// The public methods are located here

//--------------------------------------------------------------------------
/**
* Returns the id of the entity.
*
* @return a long number represents id of the entity.
*/

	public long getId()
	{
		return this.entityId;
	}


 	/*************************************************************************
 	 * No setter function for entityId -  you set the ID in the constructor  *
 	 *                     and you can't change it                           *
 	 *************************************************************************/

//--------------------------------------------------------------------------
/**
* Returns the name of the entity.
*
* @return	a String represnts entity name
*/

  	public String getName()
 	{
 		return this.entityName;
 	}

//--------------------------------------------------------------------------
/**
* Sets the string to be entity name.
*
* @param	name entity name
*/

 	public void setName(String name)
 	{
 		this.entityName = name;
 	}

//--------------------------------------------------------------------------
/**
* Returns entity description.
*
* @return a String with the entity description
*/


 	public String getDescription()
 	{
 		return  this.description;
 	}

//--------------------------------------------------------------------------
/**
* Sets the string to be entity description.
*
* @param description description of the entity
**/

 	public void setDescription(String description)
 	{
 		 this.description = description;
 	}

/**
* Sets the enviromental/system property of OpmEntity. If value of
* enviromental is true it's a enviromental thing, otherwise system
*
*/

	public void setEnviromental(boolean enviromental)
	{
		this.enviromental = enviromental;
	}


/**
* Returns true if this OpmEntity is enviromental. If it's system
* returns false
*
*/

	public boolean isEnviromental()
	{
		return this.enviromental;
	}


//--------------------------------------------------------------------------
/**
* Returns a string representation of the entity.
*
* @return a string representation of the entity
**/

	public String toString()
	{
		return this.entityName.replace('\n',' ');
	}

    public boolean equals(Object obj)
	{

		OpmEntity tempEntity;
		if (!(obj instanceof OpmEntity))
		{
			return false;
		}

		tempEntity = (OpmEntity)obj;

		if (tempEntity.getId() == this.getId())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * @return Returns the entityUrl.
	 */
	public String getUrl() {
		return this.entityUrl;
	}

	/**
	 * @param entityUrl The entityUrl to set.
	 */
	public void setUrl(String entityUrl) {
		this.entityUrl = entityUrl;
	}


 }