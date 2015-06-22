package exportedAPI.opcatAPI;
import java.awt.Color;

import exportedAPI.OpdKey;
/**
 * IInstance is an iterface to graphical representation of IXEntry.
 * This is base class for all Instances as you can see from class hierachy.
 *
 */
public interface IInstance
{

/**
* Returns OpdKey of this Instance.
* @return <code>OpdKey of IInstance</code>
*/
	public OpdKey getKey();

	/**
	 * Retrives IEntry which IInstance belongs to, shortly returns logical part
	 * of IInstance
	 * @return IEntry which IInstance belongs to.
	 */
	public IEntry getIEntry();

	/**
	 * Retrives an ID of IEntry which IInstance belongs to.
	 * This is identical to getIEntry().getId()
	 * @retrun an ID of which IInstance belongs to.
	 */
	public long getLogicalId();

	/**
	 * Returns backgrund color of graphical representation
	 * @return background color of IInstance
	 */
	public Color getBackgroundColor();

	/**
	 * Returns border color of graphical representation
	 * @return border color of IXInstance
	 */
	public Color getBorderColor();

	/**
	 * Returns text color of graphical representation
	 * @return text color of IXInstance
	 */
	public Color getTextColor();
}