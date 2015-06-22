package extensionTools.validator.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * Represent the possible applicants (the roles on the other side of a constraint),
 * that each of them is applicable for constraint satisfaction. 
 * 
 * @author Eran Toch
 * Created: 06/05/2004
 */
public class MultipleApplicant extends Applicant {
	private Set participants;

	public MultipleApplicant(VRole role) {
		this();
		addApplicant(role);
	}

	public MultipleApplicant() {
		participants = new HashSet();
	}

	public void addApplicant(VRole role) {
		if (participants == null)	{
			return;
		}
		participants.add(role);	
	}
	
	public void addAllApplicants(Set set)	{
		if (participants == null)	{
			return;
		}
		participants.addAll(set);
	}

	/**
	 * Returns the first role of the law. 
	 * @return	<code>VRole</code> or <code>null</code> if it does not exists.
	 */
	public VRole getRole()	{
		Iterator it = participants.iterator();
		if (it.hasNext())	{
			VRole v = (VRole)it.next();
			return v;
		}
		return null;
	}
	
	public boolean contains(VRole role) {
		Iterator it = participants.iterator();
		while (it.hasNext())	{
			VRole v = (VRole)it.next();
			if (v.equals(role))	{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the current <code>MultipleApplicant</code> contains one of the
	 * given roles in a given <code>MultipleApplicant</code>.
	 * @param multiple
	 * @return
	 */
	public boolean contains(MultipleApplicant multiple) {
		Iterator it = participants.iterator();
		while (it.hasNext())	{
			VRole v = (VRole)it.next();
			if (multiple.contains(v))	{
				return true;
			}
		}
		return false;
	}
	
	public String toString()	{
		Iterator it = participants.iterator();
		String output = "";
		while (it.hasNext())	{
			output += (VRole)it.next() + ",";
		}
		return output;
	}

}
