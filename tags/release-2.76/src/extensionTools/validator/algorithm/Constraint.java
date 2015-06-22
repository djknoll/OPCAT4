package extensionTools.validator.algorithm;

import extensionTools.validator.finder.Finder;
import gui.opmEntities.Constants;



/**
 * Represent a constraint - a relation/link pattern for a certain <code>IThingEntry</code>.
 * @author Eran Toch
 * Created: 06/05/2004
 */
public abstract class Constraint {

	protected MultipleApplicant applicant;
	private int edgeType;
	private int direction;

	public Constraint(MultipleApplicant _app, int _edgeType, int _direction)	{
		applicant = _app;
		edgeType = _edgeType;
		direction = _direction;
	}
	
	public MultipleApplicant getApplicant()	{
		return applicant;
	}
	
	/**
	 * @return
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @return
	 */
	public int getEdgeType() {
		return edgeType;
	}

	/**
	 * @param applicant
	 */
	public void setApplicant(MultipleApplicant applicant) {
		this.applicant = applicant;
	}

	/**
	 * @param i
	 */
	public void setDirection(int i) {
		direction = i;
	}

	/**
	 * @param i
	 */
	public void setEdgeType(int i) {
		edgeType = i;
	}
	
	public String toString()	{
		String output = applicant.toString() +" ("+ Constants.type2String(getEdgeType()) +")";
		if (direction == Finder.DESTINATION_DIRECTION)	{
			output += " destination";
		}
		else if (direction == Finder.SOURCE_DIRECTION)	{
			output += " source";
		}
		return output;
	}

}
