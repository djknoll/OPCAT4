package extensionTools.validator.algorithm;

import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import extensionTools.validator.ValidationException;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaLibrary;

/**
 * An implementation of a simple validation algorithm that validates a given 
 * model against its imported meta-libraries.
 * The algorithm recieves as input an OPM system model and a set of meta-libraries
 * (represented as OPM models), and outputs a list of vaidation warnings.
 * @author Eran Toch
 * Created: 06/05/2004
 * TODO: zoom-in
 * TODO: check that objects have the correct states.
 */
public class VAlgorithm {
	private ModelReflection systemReality;
	private HashMap metaLibraries = null;
	private HashMap metas = null;
	
	public Vector validate(ISystem sys) throws ValidationException {
		ModelReflection systemReality = new ModelReflection(sys);
		try	{
			systemReality.compile();
		}
		catch (ValidationException ve)	{
			throw new ValidationException("Model analysis had failed"); 
		}
		metas = new HashMap();
		metaLibraries = new HashMap();
		Enumeration metaLibs = sys.getMetaLibraries();
		HashSet flagedMetas = new HashSet();
		while (metaLibs.hasMoreElements()) {
			MetaLibrary lib = (MetaLibrary) metaLibs.nextElement();
			try	{
				flagedMetas.add(new Long(lib.getReferenceID()));
					metaLibraries.put(new Long(lib.getReferenceID()), lib);
					MetaReflection laws =
						new MetaReflection(lib.getISystem(), lib.getReferenceID());
					laws.compile();
					metas.put(new Long(lib.getReferenceID()), laws);
			}
			catch (ValidationException ve)	{
				throw new ValidationException("Meta-Library analysis had failed for library "+ lib.getName() +"("+ lib.getPath() +")"); 
			}
		}

		//Cleaning the metas list from staled library reflections
		//cleanOldLawSets(flagedMetas);
		
		//Finding the offences
		Vector warnings = null;
		try	{
			Vector offences = systemReality.justice(metas);
		
			//Creating the warnings
			warnings = createWarnings(offences, sys);
		}
		catch (ValidationException vex)	{
			throw vex;
		}
		return warnings;
	}
	
	/**
	 * Cleans the <code>metas</code> list of "staled" meta-reflections - meta-libraries
	 * which are not currently in the libraries list.
	 * @param currentList
	 */
	protected void cleanOldLawSets(HashSet currentList)	{
		Set keys = metas.keySet();
		Iterator lawIt = keys.iterator();
		try	{
			while (lawIt.hasNext())	{
				Long metaID = (Long)lawIt.next();
				MetaReflection meta = (MetaReflection)metas.get(metaID);
				if (!currentList.contains(metaID))	{
					metas.remove(metaID);
					metaLibraries.remove(metaID);
				}
			}
			
		}
		catch (ConcurrentModificationException ex)	{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Creates the warnings data set from a given <code>Offence</code> objects <code>Vector</code>.
	 * @param offences
	 * @param sys
	 * @return
	 */
	protected Vector createWarnings(Vector offences, ISystem sys) {
		Vector allWarnings = new Vector();
		for (int j = 0; j < offences.size(); j++) {
			Offence offence = (Offence)offences.get(j);
			Vector warning = new Vector();
			ImageIcon icon = StandardImages.VALIDATION;
			warning.add(icon);
			warning.add(offence.getOriginalThingName());
			VRole lawRole = offence.getLaw().getApplicant().getRole();
			if (lawRole == null)	{
				System.out.println("Error on offence"+ offence + " - no role was found");
				break;
			}
			long libID = offence.getLaw().getApplicant().getRole().getMetaLibID();
			MetaLibrary metaLib = (MetaLibrary)metaLibraries.get(new Long(libID));
			if (metaLib == null)	{
				System.out.println("MetaLib "+ libID +" is missing");
				break;
			}
			ISystemStructure structure = metaLib.getStructure();
			String text = offence.renderOffenceText(structure);
			warning.add(text);
			allWarnings.add(warning);
		}
		return allWarnings;
	}
}
