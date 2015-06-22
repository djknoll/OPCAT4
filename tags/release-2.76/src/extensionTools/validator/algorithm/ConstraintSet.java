package extensionTools.validator.algorithm;

import java.util.HashSet;
import java.util.Iterator;

/**
 * A set of constraints that represent all the strctural constrains a thing has.
 * @author Eran Toch
 * Created: 07/05/2004
 */
public class ConstraintSet extends HashSet {
	private VRole role = null;
	
	public void setRole(VRole _role)	{
		role = _role;
	}
	
	public String toString()	{
		Iterator it = iterator();
		String output = "Constraints: ";
		while (it.hasNext())	{
			output += (Constraint)it.next() +";\n";
		}
		return output;
	}
}
