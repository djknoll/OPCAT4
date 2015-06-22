package gui.metaLibraries.dialogs;

import gui.metaLibraries.logic.MetaManager;
import gui.opdProject.OpdProject;

import java.util.Vector;

import javax.swing.JFrame;

/**
 * Represent a Meta-Libraries configuration window which is presented as part of the 
 * loading  process. The window enable users to change the path of a library or
 * to remove one.
 * @author Eran Toch
 */
public class LibrariesFix extends LibrariesImportsDialog {

	public LibrariesFix(MetaManager meta, OpdProject _project, JFrame parent) throws Exception {
		super(meta, _project, parent);
	}
	
	protected boolean displayAddButton() {
		return false;
	}
	
	protected Vector getLibReferences() {
		return metaManager.getFaliedLibs();
	}
	
	protected String getTitleText() {
		return "The following meta-libraries were not found:";
	}
}
