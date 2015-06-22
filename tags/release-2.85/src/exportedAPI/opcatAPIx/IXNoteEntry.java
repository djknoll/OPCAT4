package exportedAPI.opcatAPIx;


/**
 * IXNoteEntry is an interface to logical representation of Note in OPM meaning
 */

public interface IXNoteEntry extends IXThingEntry
{

        /**
        * Returns String representing type property of this OPM object.
        * @retrun String represening type of the OPM object
        */
        public String getDescription();

        /**
        * Sets type property of this OPM Object. Do not set this property directly without valididy check
        * @param new type for OPM object
        */
        public void setDescription(String type);
}
