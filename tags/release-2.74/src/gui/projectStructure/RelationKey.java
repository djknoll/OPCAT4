package gui.projectStructure;

/**
* <p>This class defines a key for relations/links.
* Actually this key is compound from two elements - entity id  of this relation
* and relation's type (as it specified in Interface Constants in opmEntities package).
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class RelationKey
{
	private int relationType;
	private long entityId;
    private String relationName;

/**
* Creates RelationKey with specified pRelationType and pEntityId.
*
* @param pRelationType int representing relation's type
* (as it specified in Interface Constants in opmEntities package).
* @pEntityId entity id of this relation.
*/
	public RelationKey(int pRelationType, long pEntityId)
	{
		relationType = pRelationType;
		entityId = pEntityId;
	}


/**
* Returns entity id of this RelationKey
*
*/
	public long getEntityId()
	{
		return entityId;
	}

/**
* Returns relation type of this RelationKey
*
* @return int reprenting relation type (as it specified in Interface
* Constants in opmEntities package).
*/

	public int getRelationType()
	{
		return relationType;
	}

/**
* Returns a hash code value for the RelationKey.
* This method is supported for the benefit of hashtables such as
* those provided by java.util.Hashtable.
*/

	public int hashCode()
	{
		return (int)relationType;
	}

/**
* Indicates whether some other object is "equal to" this one.
*/
	public boolean equals(Object obj)
	{

		RelationKey tempKey;

		if (!(obj instanceof RelationKey))
		{
			return false;
		}

		tempKey = (RelationKey)obj;

		if (tempKey.getRelationType() == relationType && tempKey.getEntityId() == entityId)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
