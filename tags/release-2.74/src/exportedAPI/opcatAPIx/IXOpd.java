package exportedAPI.opcatAPIx;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.util.Enumeration;

/**
 * IXOpd - is an interface for OPD.
 */
public interface IXOpd extends Printable
{

	/**
	 * @return The unique ID of the Opd.
	 */
	public long getOpdId();

	/**
	 * @return The name of the Opd
	 */
	public String getName();

	/**
	 * Sets the name for the OPD that is displaid on OPD frame and in repository manager.
	 * @param name - new name for OPD
	 */
	public void setName(String name);

	/**
	 * If OPD is not ROOT OPD it always has parent OPD, from which it was unfolded or inzoomed.
	 * This method returns IXOpd interface to parent OPD.
	 * @return IXOpd interface to parent OPD.
	 */
	public IXOpd getParentIXOpd();

	/**
	 * Return an Enumeration of all items selected in this OPD.
	 * @return an Enumeration of selected items
	 */
	public Enumeration getSelectedItems();

	/**
	 * If the OPD is inzoomed or unfolded returns an IXThingEntry of OPM thing
	 * that is inzoomed or unfolded by this OPD
	 * @retrun IXThingEntry of OPM thing that is inzoomed or unfolded
	 */
	public IXThingEntry getMainIXEntry();

	/**
	 * If the OPD is inzoomed or unfolded returns an IXThingInstance (interface to graphical
	 * representation) of inzoomed or unfolded OPM thing in this OPD
	 * @retrun IXThingInstance of inzoomed or unfolded OPM thing in this OPD
	 */
	public IXThingInstance getMainIXInstance();


	/**
	 * Return an BufferedImage representaion of this OPD.
	 */

    public BufferedImage getImageRepresentation();

}