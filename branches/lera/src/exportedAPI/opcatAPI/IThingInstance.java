package exportedAPI.opcatAPI;
import java.util.Enumeration;

import exportedAPI.opcatAPIx.IXOpd;
/**
 * IThingInstance is an interface to graphical representation of OPM Thing
 */
public interface IThingInstance extends IConnectionEdgeInstance
{
	/**
	 * Checks if this IThingInstance is ZoomedIn
	 * @return true if IThingInstance is ZoomedIn, false if not
	 */
	public boolean isZoomedIn();

	/**
	 * Returns parent IThingInstance (IThingInstance which contains graphically
     * this IThingInstance) or null if this IThingInstance drawn directly on OPD
     * (no parent IThingInstance).
	 * @return IThingInstance which contains graphically this IThingInstance
     * or null if this IThingInstance drawn directly on OPD
     * (no parent IThingInstance).
	 */

    public IThingInstance getParentIThingInstance();

	/**
	 * Returns Enumeration of IThingInstance which this IThingInstance contains
     * graphically. Empty Enumeration is returned if this IThingInstance contains
     * nothing. Only directly contained etities are returned.
	 * @return Enumeration of IThingInstance which this IThingInstance contains
     * graphically. Empty Enumeration is returned if this IThingInstance contains
     * nothing. Only directly contained etities are returned.
	 */
    public Enumeration getChildThings();
    
    /**
     * Returns OPD unfolding this ThingInstance. null returned if there is no
     * such OPD.
     * @author Yossi Gozlan
     * @since	26/April/2004
     */
   	public IXOpd getUnfoldingIXOpd();


}