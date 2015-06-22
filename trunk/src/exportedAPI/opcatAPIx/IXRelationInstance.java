package exportedAPI.opcatAPIx;

import gui.projectStructure.Instance;

/**
 * IXRelationInstance is an interface to graphical representation of OPM Relation
 */

public interface IXRelationInstance extends IXInstance, IXLine {

    public IXConnectionEdgeInstance getSourceIXInstance();

    /**
     * Gets IXConnectionEdgeInstance which is destination of this IXRelationInstance.
     */
    public IXConnectionEdgeInstance getDestinationIXInstance();

    public Instance getDestinationInstance();

    public Instance getSourceInstance();
}