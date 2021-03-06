package gui.metaLibraries.logic;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXRole;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXSystemStructure;
import exportedAPI.opcatAPIx.IXThingEntry;
import gui.metaDataProject.MetaDataProject;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.projectStructure.ThingEntry;
import gui.util.OpcatLogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.JDesktopPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * Manages a collection of {@link MetaLibrary} objects, serving as a repository
 * and a factory.
 * 
 * @author Eran Toch Created: 30/04/2004
 */
public class MetaManager implements Cloneable {

	/**
	 * An empty constructor.
	 */
	public MetaManager() {
	}

	/**
	 * A list of meta-libraries that are imported, or need to be imported, into
	 * the project.
	 * 
	 * @author Eran Toch
	 */
	private Vector metaLibraries = new Vector();

	protected JProgressBar pBar;

	/**
	 * Returns the ontologies which are imported by this project.
	 * 
	 * @return Enumeration Contains MetaLibrary object, each of them represent a
	 *         model of a domain-specific ontology.
	 * @author Eran Toch
	 */
	public Enumeration getMetaLibraries() {
		return this.metaLibraries.elements();
	}

	public Enumeration getMetaLibraries(int type) {
		Vector vec = new Vector();
		Enumeration locEnum = this.metaLibraries.elements();

		MetaLibrary meta;

		while (locEnum.hasMoreElements()) {
			meta = (MetaLibrary) locEnum.nextElement();
			if (meta.getType() == type) {
				vec.add(meta);
			}
		}

		return vec.elements();
	}

	/**
	 * Retruns a <code>MetaLibrary</code> according to a given id.
	 * 
	 * @param id
	 *            The ID of the library that should be retrieved.
	 * @return The MetaLibrary or <code>null</code> if was not found.
	 */
	public MetaLibrary getMetaLibrary(long id) {
		MetaLibrary meta;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			meta = (MetaLibrary) this.metaLibraries.get(i);
			if (meta.getReferenceID() == id) {
				return meta;
			}
		}
		return null;
	}

	/**
	 * Returns a copy of the vector of the meta libraries. Clones
	 * <code>metaLibraries</code> and returns it.
	 * 
	 * @return A Vector containing <code>MetaLibrary</code> objects.
	 */
	public Vector getVectorClone() {
		Vector cloned = (Vector) this.metaLibraries.clone();
		return cloned;
	}

	/**
	 * Return a list of meta-libraries for display for users. The list is
	 * identical to the <code>metaLibraries</code> list, but does not contain
	 * libraries in a transient state (STATE_REMOVED, STATE_EDITED).
	 * 
	 * @return A Vector containing <code>MetaLibrary</code> objects, a subset
	 *         of the current list.
	 */
	public final Vector getDisplayLibraries() {
		Vector output = new Vector();
		MetaLibrary runner;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			runner = (MetaLibrary) this.metaLibraries.get(i);
			if ((runner.getState() == MetaLibrary.STATE_REFERENCE)
					|| (runner.getState() == MetaLibrary.STATE_LOADED)
					|| (runner.getState() == MetaLibrary.STATE_LOAD_FAILED)) {
				output.add(runner);
			}
		}
		// Creating a copy in order to avoid returning the same objects
		Vector copy = (Vector) output.clone();
		return copy;
	}

	/**
	 * Adds an ontology to the list of active ontologies for this project.
	 * 
	 * @param onto
	 *            MetaLibrary The ontology to be added.
	 * @throws MetaException
	 *             If the library already exists in the list.
	 */
	public void addMetaLibrary(MetaLibrary onto) throws MetaException {
		if (this.findByPath(onto.getPath()) != null) {
			throw new MetaException("MetaLibrary already exists", onto
					.getPath());
		}
		this.metaLibraries.add(onto);
	}

	/**
	 * Removes a <code>MetaLibrary</code> from the list, according to its
	 * path.
	 * 
	 * @param path
	 *            The path of the MetaLibrary to be removed.
	 * @throws If
	 *             no MetaLibrary exist with the given path.
	 */
	public void removeMetaLibrary(String path) throws MetaException {
		MetaLibrary lib = this.findByPath(path);
		if (lib == null) {
			throw new MetaException("MetaLibrary was not found", path);
		} else {
			this.removeMetaLibrary(lib);
		}
	}

	/**
	 * Removes a <code>MetaLibrary</code> from the list of libraries.
	 * 
	 * @param dead
	 *            The <code>MetaLibrary</code> to be removed.
	 * @throws MetaException
	 *             If the list does not contain
	 */
	private void removeMetaLibrary(MetaLibrary dead) throws MetaException {
		if (!this.metaLibraries.contains(dead)) {
			throw new MetaException("MetaLibrary was not found", dead.getPath());
		}
		this.metaLibraries.remove(dead);
	}

	/**
	 * Marks a <code>MetaLibrary</code> for removal, in the next
	 * <code>refresh()</code> call.
	 * 
	 * @param path
	 *            A path to the MetaLibrary to be marked.
	 * @throws MetaException
	 *             If no MetaLibrary exist with the given path.
	 */
	public void markForRemoval(String path) throws MetaException {
		MetaLibrary lib = this.findByPath(path);
		if (lib == null) {
			throw new MetaException("MetaLibrary was not found", path);
		} else {
			lib.setState(MetaLibrary.STATE_REMOVED);
		}
	}

	/**
	 * Changes
	 * 
	 * @param path
	 * @param newPath
	 * @param newType
	 * @throws MetaException
	 */
	public void changeLibraryState(String path, String newPath, int newType)
			throws MetaException {
		MetaLibrary theLib = this.findByPath(path);
		if (theLib == null) {
			throw new MetaException("The Meta-Library was not found", path);
		}
		theLib.setPath(newPath);
		theLib.setReferenceType(newType);
		theLib.setState(MetaLibrary.STATE_REFERENCE);
	}

	/**
	 * Removes all the roles that belong to the given <code>MetaLibrary</code>
	 * from the current <code>OpdProject</code>.
	 * 
	 * @param lib
	 *            The <code>MetaLibrary</code> that should be cleaned out the
	 *            project.
	 * @throws MetaException
	 *             if any of the given inputs are null
	 */
	private int cleanRoles(MetaLibrary lib, OpdProject myProject)
			throws MetaException {
		try {
			return this.goOverRoles(lib, myProject, true);
		} catch (MetaException me) {
			throw me;
		}
	}

	/**
	 * Returns <code>true</code> if the project contains roles of the
	 * <code>lib</code>.
	 * 
	 * @throws MetaException
	 *             if any of the given inputs are null
	 */
	public boolean projectHasRolesOfLib(MetaLibrary lib, OpdProject myProject)
			throws MetaException {
		try {
			if (this.goOverRoles(lib, myProject, false) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (MetaException me) {
			throw me;
		}
	}

	/**
	 * The method goes over all the roles of the project, and performs remove
	 * action, if the <code>doRemove</code> is set to <code>true</code>.
	 * 
	 * @param lib
	 *            The library that the roles belongs to.
	 * @param myProject
	 *            The project
	 * @param doRemove
	 *            If set to <code>true</code>, the method removes all the
	 *            found roles. Otherwise, it only goes over the roles and return
	 *            if it found any roles.
	 * @return The number of roles found.
	 * @throws MetaException
	 *             If no project was found.
	 */
	private int goOverRoles(MetaLibrary lib, OpdProject myProject,
			boolean doRemove) throws MetaException {
		if (myProject == null) {
			throw new MetaException(
					"While going over the roles, current project is null", lib
							.getPath());
		}
		if (lib == null) {
			throw new MetaException(
					"While going over the roles, MetaLibrary is null", lib
							.getPath());
		}
		int foundRolesNum = 0;
		long libID = lib.getReferenceID();
		IXSystem system = myProject;
		IXSystemStructure oStructure = system.getIXSystemStructure();
		Enumeration locenum = oStructure.getElements();
		IXEntry entry;
		IXThingEntry thing;
		Enumeration allRoles;
		while (locenum.hasMoreElements()) {
			entry = (IXEntry) locenum.nextElement();
			// OpmEntity entity = entry.getLogicalEntity();
			if (entry instanceof IXThingEntry) {
				thing = (IXThingEntry) entry;
				allRoles = thing.getRoles();
				IXRole role;
				while (allRoles.hasMoreElements()) {
					role = (IXRole) allRoles.nextElement();
					if (role.getLibraryId() == libID) {
						foundRolesNum++;
						// If the method is set for removing roles, we remove
						// the role
						if (doRemove) {
							thing.removeRole(role);
						}

					}
				}
			}
		}
		return foundRolesNum;
	}

	/**
	 * Gets a <code>MetaLibrary</code> from the list according to it's path
	 * name. If two libraries have the same path, the first one that appears on
	 * the list will be removed.
	 * 
	 * @param path
	 *            The path of the library.
	 * @return The first MetaLibrary that was found with the given path name, or
	 *         <code>null</code> if nothing was found.
	 */
	public MetaLibrary findByPath(String path) {
		if (this.metaLibraries != null) {
			MetaLibrary runner;
			for (int i = 0; i < this.metaLibraries.size(); i++) {
				runner = (MetaLibrary) this.metaLibraries.get(i);
				if (runner.getPath().equals(path)) {
					return runner;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a meta library, according to a given reference ID.
	 * 
	 * @param id
	 *            The reference ID of the library.
	 */
	public MetaLibrary findByReferenceID(long id) {
		if (this.metaLibraries != null) {
			MetaLibrary runner;
			for (int i = 0; i < this.metaLibraries.size(); i++) {
				runner = (MetaLibrary) this.metaLibraries.get(i);
				if (runner.getReferenceID() == id) {
					return runner;
				}
			}
		}
		return null;
	}

	/**
	 * Returns an avaliable unique ID for a MetaLibrary in the context of this
	 * <code>MetaManager</code>. Goes through the existing id's and return an
	 * ID which is larger in 1 than the highest.
	 */
	public long getNextAvaliableID() {
		long highest = 0;
		long libID = 0;
		MetaLibrary runner;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			runner = (MetaLibrary) this.metaLibraries.get(i);
			libID = runner.getReferenceID();
			if (libID > highest) {
				highest = libID;
			}
		}
		return (highest + 1);
	}

	/**
	 * A factory method for creating a {@link MetaLibrary}, in an unloaded
	 * state. The method automatically assigns an ID to the library.
	 * 
	 * @param _path
	 *            The path to the library.
	 * @param _refType
	 *            The reference type (<code>TYPE_FILE</code> or
	 *            <code>TYPE_URL</code>).
	 * @return A new and initialized <code>MetaLibrary</code> object.
	 */
	public MetaLibrary createNewMetaLibraryReference(String _path, int _refType) {
		return new MetaLibrary(this.getNextAvaliableID(), _path, _refType);
	}

	/**
	 * Returns a string representation of the MetaManager. Containing the path
	 * names of the MetaLibraries with line breaks between them.
	 */
	public String toString() {
		String output = "";
		MetaLibrary lib;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			lib = (MetaLibrary) this.metaLibraries.get(i);
			output += lib.getPath();
			output += " (state=" + lib.getState() + ")\n";
		}
		return output;
	}

	/**
	 * Sets the meta-libraries according to the given vector.
	 * 
	 * @param libraries
	 *            A Vector containing <code>MetaLibrary</code> objects.
	 */
	public void setLibrariesVector(Vector libraries) {
		this.metaLibraries = libraries;
	}

	/**
	 * Creates a copy of the MetaManager.
	 * 
	 */
	public Object clone() {
		MetaManager copy = new MetaManager();
		copy.setLibrariesVector(this.getVectorClone());
		return copy;
	}

	/**
	 * Refresh the library list, loading referenced libraries, removeing
	 * necessary libraries etc.
	 * <p>
	 * The method handled the following actions in this order:<br>
	 * 1. Removing libraries which are marked for removal.<br>
	 * 2. Loading libraries which are marked as reference.<br>
	 * 3. Refresh the roles view, according to the loaded libraries.<br>
	 * <p>
	 * The method manages a progress bar which is incremented according to the
	 * loading process.
	 */
	public void refresh(OpdProject myProject, JProgressBar progressBar) {
		_refresh(myProject, progressBar) ; 
	}	
	
	public void setAllLibstoReferenced() {
		Iterator iter = Collections.list(this.getMetaLibraries()).iterator() ; 
		while (iter.hasNext()) {
			MetaLibrary meta = (MetaLibrary) iter.next() ; 
			meta.setState(MetaLibrary.STATE_REFERENCE); 
		}
	}
	
	private void _refresh(OpdProject myProject, JProgressBar progressBar) {
		this.pBar = progressBar;
		this.pBar.setStringPainted(true);
		this.pBar.setMinimum(0);
		this.pBar.setMaximum(this.size() + 1);
		this.pBar.setValue(0);
		try {
			this.removeMarkedLibraries(myProject);
			this.loadLibraries();
			this.refreshRoles(myProject);
		} catch (MetaException E) {
			OpcatLogger.logError(E);
		}
	}	

	/**
	 * Loads roles in the given <code>OpdProject</code> which are not loaded
	 * from an imported meta-library. The method is used after the libraries
	 * loading procedure had ended, and assignes the identity of the unloaded
	 * roles (that <code>role.getState() == Role.UNLOADED</code>).
	 * 
	 * @param myProject
	 *            The project to be refreshed.
	 */
	public void refreshRoles(OpdProject myProject) {
		Enumeration locenum = myProject.getComponentsStructure().getElements();
		ThingEntry thing;
		Enumeration allRoles;
		Role role;
		Entry entry;
		// Runnning on the element list
		try {
			while (locenum.hasMoreElements()) {			//if (role.isLoaded()) {
				entry = (Entry) locenum.nextElement();
				// Filtering things
				if (entry instanceof ThingEntry) {
					thing = (ThingEntry) entry;
					allRoles = thing.getRoles();
					while (allRoles.hasMoreElements()) {
						role = (Role) allRoles.nextElement();
						// Handling only unloaded roles
						if (role.getState() == Role.UNLOADED) {
							role.load(this.getMetaLibrary(role.getLibraryId()));
						}
					}
				}
			}
			// Refreshing the drawing area in order to role markups to be shown
			myProject.getCurrentOpd().getDrawingArea().repaint();
			this.incrementProgressBar();
		} catch (MetaException E) {
			System.out.println(E.getMessage());
		}

	}

	/**
	 * Checks if the manager contains <code>MetaLibrary</code> objects that
	 * failed to load.
	 * 
	 * @return <code>true</code> if at least one library failed to load.
	 *         <code>false</code> otherwise.
	 */
	public boolean containFailed() {
		MetaLibrary lib;
		boolean flag = false;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			lib = (MetaLibrary) this.metaLibraries.get(i);
			if (lib.getState() == MetaLibrary.STATE_LOAD_FAILED) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * Returns a message that contains all the MetaLibraries that failed to
	 * load.
	 */
	public String getFailedMessage() {
		MetaLibrary lib;
		String output = "The following Meta-libraries had failed to load:\n\n";
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			lib = (MetaLibrary) this.metaLibraries.get(i);
			if (lib.getState() == MetaLibrary.STATE_LOAD_FAILED) {
				output += lib.getPath() + "\n";
			}
		}
		output += "\nPlease check their location and update it at the Project Properties.";
		return output;
	}

	/**
	 * Returns a list of Meta-Libraries that failed to load.
	 * 
	 * @return A Vector containing MetaLibrary objects.
	 */
	public Vector getFaliedLibs() {
		MetaLibrary lib;
		Vector failed = new Vector();
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			lib = (MetaLibrary) this.metaLibraries.get(i);
			if (lib.getState() == MetaLibrary.STATE_LOAD_FAILED) {
				failed.add(lib);
			}
		}
		return failed;
	}

	/**
	 * Removes all libraries which are marked as <code>STATE_REMOVED</code>.
	 * The method cleans the roles of a removed <code>MetaLibrary</code> and
	 * removes the library.
	 * 
	 * @throws MetaException
	 *             Whatever removeMetaLibrary or cleanRoles throws.
	 */
	private void removeMarkedLibraries(OpdProject myProject)
			throws MetaException {
		if (this.metaLibraries != null) {
			MetaLibrary runner;
			for (int i = 0; i < this.metaLibraries.size(); i++) {
				runner = (MetaLibrary) this.metaLibraries.get(i);
				if (runner.getState() == MetaLibrary.STATE_REMOVED) {
					try {
						this.cleanRoles(runner, myProject);
						this.removeMetaLibrary(runner);
					} catch (MetaException E) {
						throw E;
					}
				}
			}
		}
	}

	/**
	 * Loads all libraries which are not loaded from the Web or the local file
	 * system. The method runs through the meta-libraries list and loads each
	 * library which has an <code>STATE_REFERENCE</code> state,
	 */
	private void loadLibraries() {
		if (this.metaLibraries == null) {
			return;
		}
		MetaLibrary metaLib;
		for (int i = 0; i < this.metaLibraries.size(); i++) {
			metaLib = (MetaLibrary) this.metaLibraries.get(i);
			if (metaLib.getType() == LibraryTypes.MetaLibrary) {
				this.loadOPMLibraries(metaLib);
			}
			if (metaLib.getType() == LibraryTypes.MetaData) {
				this.loadREQLibraries(metaLib);
			}
		}
	}

	private void loadREQLibraries(MetaLibrary metaLib) {

		String fileName = metaLib.getPath();
		if (metaLib.getState() == MetaLibrary.STATE_REFERENCE) {
			InputStream is = null;
			// Handling meta libraries which are on the local file systems
			if (metaLib.getReferenceType() == MetaLibrary.TYPE_FILE) {
				File f = new File(fileName);
				if (!f.canRead()) {
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
					// System.out.println("File "+ fileName +" was not
					// found");
				}
				try {
					if (fileName.endsWith(".csv") || fileName.endsWith(".txt") || fileName.endsWith(".req")) {
						is = new BufferedInputStream(new FileInputStream(f),
								4096);
					}
				} catch (Exception E) {
					// System.out.println(E.getMessage() +" file: "+
					// fileName);
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
				}
			}
			// Handling URL's
			else if (metaLib.getReferenceType() == MetaLibrary.TYPE_URL) {
				try {
					// TODO: support OPZ files from URL's
					URL url = new URL(metaLib.getPath());
					is = url.openStream();
					if (url.getPath().endsWith(".csv") || url.getPath().endsWith(".txt") || url.getPath().endsWith(".req")) {
						is = new BufferedInputStream(is);
					}
				} catch (Exception E) {
					// System.out.println(E.getMessage());
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
				}
			}
			try {
				// Checking if the global ID is equal. We deal
				// gracefully with
				// cases where the globalID was not set
				MetaDataProject repProject = new MetaDataProject(is, metaLib
						.getPath());		
				metaLib.setModelProject(repProject);
				if (repProject.getStatus().isLoadFail()) {
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED)  ; 
				} else {
					metaLib.setState(MetaLibrary.STATE_LOADED) ; 
				}
	
			} catch (Exception e) {
				OpcatLogger.logError(e);
			}
		}
	}

	private void loadOPMLibraries(MetaLibrary metaLib) {
		// if (metaLibraries == null) {
		// return;
		// }
		// MetaLibrary metaLib;
		gui.opx.Loader ld = new gui.opx.Loader();
		// for (int i = 0; i < metaLibraries.size(); i++) {
		// metaLib = (MetaLibrary) metaLibraries.get(i);
		String fileName = metaLib.getPath();
		if ((metaLib.getState() == MetaLibrary.STATE_REFERENCE) ) {
			InputStream is = null;
			// Handling meta libraries which are on the local file systems
			if (metaLib.getReferenceType() == MetaLibrary.TYPE_FILE) {
				File f = new File(fileName);
				if (!f.canRead()) {
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
					// System.out.println("File "+ fileName +" was not
					// found");
				}
				try {
					if (fileName.endsWith(".opz")) {
						is = new GZIPInputStream(new FileInputStream(f), 4096);
					} else {
						is = new BufferedInputStream(new FileInputStream(f),
								4096);
					}
				} catch (Exception E) {
					// System.out.println(E.getMessage() +" file: "+
					// fileName);
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
				}
			}
			// Handling URL's
			else if (metaLib.getReferenceType() == MetaLibrary.TYPE_URL) {
				try {
					// TODO: support OPZ files from URL's
					URL url = new URL(metaLib.getPath());
					is = url.openStream();
					if (url.getPath().endsWith(".opz")) {
						is = new GZIPInputStream(is);
					}
				} catch (Exception E) {
					// System.out.println(E.getMessage());
					metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
				}
			}

			// Handling ontology loading
			try {
				if (is != null) {
					OpdProject pr = this._loadProject(ld, is);
					if (pr != null) {
						boolean doSetProject = true;
						// Checking if the global ID is equal. We deal
						// gracefully with
						// cases where the globalID was not set
						if ((metaLib.getGlobalID() != null)
								&& !metaLib.getGlobalID().equals("")) {
							if (!pr.getGlobalID().trim().equals(
									metaLib.getGlobalID().trim())) {
								doSetProject = false;
							}
						}
						if (doSetProject) {
							metaLib.setModelProject(pr);
						}
						pr.close();
						is.close();
						if (metaLib.projectIsNull()) {
							// System.out.println("Error - Ontology loading
							// returned a null project");
						}
						metaLib.setState(MetaLibrary.STATE_LOADED);
					} else {
						// System.out.println("metalib is null");
						metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
					}
				}
			} catch (Exception E) {
				// System.out.println("Error while loading meta-library:"+
				// E.getMessage());
				metaLib.setState(MetaLibrary.STATE_LOAD_FAILED);
			} finally {
				this.incrementProgressBar();
			}
		}
		// }
	}

	private OpdProject _loadProject(gui.opx.Loader ld, InputStream is)
			throws gui.opx.LoadException {
		OpdProject tProject = ld.load(new JDesktopPane(), is, null);
		return tProject;
	}

	/**
	 * Returns the number of meta-libraries managed by this
	 * <code>MetaManager</code>.
	 */
	public int size() {
		if (this.metaLibraries == null) {
			return 0;
		}
		return this.metaLibraries.size();
	}

	/**
	 * Checks whether the meta-manager contains any meta-libraries.
	 * 
	 * @return <code>true</code> if meta-libraries exist, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasMetaLibraries() {
		if (this.size() > 0) {
			return true;
		}
		return false;
	}

	protected void incrementProgressBar() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				synchronized (MetaManager.this.pBar) {
					MetaManager.this.pBar.setValue(MetaManager.this.pBar.getValue() + 1);
				}
			}
		});

	}

}