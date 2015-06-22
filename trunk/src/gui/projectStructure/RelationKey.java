package gui.projectStructure;

/**
 * <p/>
 * This class defines a key for relations/links. Actually this key is compound
 * from two elements - entity id of this relation and relation's type (as it
 * specified in Interface Constants in opmEntities package).
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class RelationKey implements Comparable {
    private int relationType;

    private long entityId;

    /**
     * Creates RelationKey with specified pRelationType and pEntityId.
     *
     * @param pRelationType int representing relation's type (as it specified in Interface
     *                      Constants in opmEntities package).
     * @pEntityId entity id of this relation.
     */
    public RelationKey(int pRelationType, long pEntityId) {
        this.relationType = pRelationType;
        this.entityId = pEntityId;
    }

    /**
     * Returns entity id of this RelationKey
     */
    public long getEntityId() {
        return this.entityId;
    }

    /**
     * Returns relation type of this RelationKey
     *
     * @return int reprenting relation type (as it specified in Interface
     *         Constants in opmEntities package).
     */

    public int getRelationType() {
        return this.relationType;
    }

    /**
     * Returns a hash code value for the RelationKey. This method is supported
     * for the benefit of hashtables such as those provided by
     * java.util.Hashtable.
     */

    public int hashCode() {
        return (String.valueOf(getRelationType()) + String.valueOf(getEntityId())).hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     */
    @Override
    public boolean equals(Object obj) {

//		RelationKey tempKey;
//
//		if (!(obj instanceof RelationKey)) {
//			return false;
//		}
//
//		tempKey = (RelationKey) obj;

        if (obj instanceof RelationKey) {
            RelationKey key = (RelationKey) obj;
            if ((key.getRelationType() == this.relationType)
                    && (key.getEntityId() == this.entityId)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof RelationKey) {
            RelationKey key = (RelationKey) o;
            if ((key.getRelationType() == this.relationType)
                    && (key.getEntityId() == this.entityId)) {
                return 0;
            }
        }
        return -1;
    }
}
