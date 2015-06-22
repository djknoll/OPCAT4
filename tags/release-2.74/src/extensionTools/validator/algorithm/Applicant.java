package extensionTools.validator.algorithm;


/**
 * Represent a role which is the target part of a constraint.
 * @author Eran Toch
 * Created: 06/05/2004
 */
public class Applicant {
	private VRole theRole;
	
	public Applicant(VRole role)	{
		theRole = role;
	}
	
	public Applicant()	{}
	
	public VRole getRole()	{
		return theRole;
	}
	
	public boolean contains(VRole role)	{
		if (theRole.equals(role))	{
			return true;
		}
		return false;
	}
	
	public String toString()	{
		return theRole.toString();
	}
}
