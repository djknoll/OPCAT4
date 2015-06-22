package gui.metaLibraries.dialogs;

import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.opdProject.OpdProject;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A dialog for library reference import selection. The dialog enable users to manage their referene library.
 * @author Eran Toch
 * @version 1.0
 */

public class LibrariesImportsDialog extends JPanel {
	JList importedList = new JList();
	JLabel jLabel1 = new JLabel();
	JButton add = new JButton();
	JButton edit = new JButton();
	JButton remove = new JButton();
	JScrollPane importedScroll = new JScrollPane();
	protected MetaManager metaManager = null;
	protected OpdProject currentProject = null; 
	protected JFrame parentFrame = null;

	public LibrariesImportsDialog(MetaManager meta, OpdProject _project, JFrame parent) throws Exception {
		metaManager = (MetaManager)meta.clone();
		currentProject = _project;
		parentFrame = parent;
		if (metaManager == null) {
			throw new Exception("Meta Manager is null");
		}
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void jbInit() throws Exception {
		this.setLayout(null);
		
		Rectangle firstButtonPosition = new Rectangle(313, 54, 100, 29);
		Rectangle secondButtonPosition = new Rectangle(313, 97, 100, 29);
		Rectangle thirdButtonPosition = new Rectangle(313, 140, 100, 28);
		importedList.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setPreferredSize(new Dimension(124, 15));
		jLabel1.setText(getTitleText());
		jLabel1.setBounds(new Rectangle(24, 26, 350, 19));
		add.setBounds(firstButtonPosition);
		add.setPreferredSize(new Dimension(70, 23));
		add.setRequestFocusEnabled(true);
		add.setText("Add...");
		add.addActionListener(
			new OntologyImportsDialog_add_actionAdapter(this));
		edit.setBounds(secondButtonPosition);
		edit.setPreferredSize(new Dimension(60, 23));
		edit.setText("Edit...");
		edit.addActionListener(
			new OntologyImportsDialog_edit_actionAdapter(this));
		remove.setBounds(thirdButtonPosition);
		remove.setMaximumSize(new Dimension(60, 23));
		remove.setMinimumSize(new Dimension(60, 23));
		remove.setPreferredSize(new Dimension(60, 23));
		remove.setMnemonic('0');
		remove.setText("Remove");
		remove.addActionListener(
			new OntologyImportsDialog_remove_actionAdapter(this));
		this.setOpaque(true);
		importedScroll.setBounds(new Rectangle(24, 54, 276, 157));
		this.add(jLabel1, null);
		if (displayAddButton())	{
			this.add(add, null);
		}
		else	{
			edit.setBounds(firstButtonPosition);
			remove.setBounds(secondButtonPosition);
		}
		this.add(edit, null);
		this.add(remove, null);
		this.add(importedScroll, null);
		importedScroll.getViewport().add(importedList, null);

		//Adding current ontologies to the list
		PathObject[] references = getPathsArray(getLibReferences());
		importedList.setListData(references);
	}
	
	
	/**
	 * Returns a Vector containing the Libraries to be displayed in the list.
	 */
	protected Vector getLibReferences()	{
		return metaManager.getDisplayLibraries();
	}
	
	/**
	 * The method decides if to display an "add" button.
	 * @return <code>true</code> if the button should be displayed. <code>false</code>
	 * if not.
	 */
	protected boolean displayAddButton()	{
		return true;
	}
	
	/**
	 * Returns the text that would be displayed to the user, above the libraries list.
	 * @return
	 */
	protected String getTitleText()	{
		return "Imported Meta-Libraries:";
	}

	/**
	 * Builds an array containing the paths.
	 * @param libsList	The Vector of <code>MetaLibrary</code> objects
	 * @return
	 */
	private PathObject[] getPathsArray(Vector libsList) {
		PathObject[] references = new PathObject[libsList.size()];
		MetaLibrary runner = null;
		for (int i = 0; i < libsList.size(); i++) {
			runner = (MetaLibrary) libsList.get(i);
			if (runner != null) {
				PathObject pathObj = new PathObject(runner.getPath());
				references[i] = pathObj;
			}
		}
		return references;
	}

	/**
	 * Retruns the {@link MetaManager) of the current dialog. The dialog creates a 
	 * copy of the project's <code>MetaManager</code> and changes the copy. When closing
	 * the dialog, the <code>MetaManager</code> can be returned in order to change
	 * the original one.
	 * @return	The local copy of the <code>MetaManager</code>.
	 */
	public MetaManager getMetaManager() {
		return metaManager;
	}

	/**
	 * Adding a new library to the system. The new library is added using the
	 * <code>LibraryLocationDialog</code> which allows users to browse for libraries
	 * using a file chooser or a URL.
	 * @param e ActionEvent
	 */
	public void add_actionPerformed(ActionEvent e) {
		LibraryLocationDialog addLocation =
			new LibraryLocationDialog(parentFrame, "", MetaLibrary.TYPE_FILE);
		addLocation.setTitle( "Location of Imported Library"); 
		HashMap newRef = addLocation.showDialog();
		if (newRef != null) {
			String path = (String)newRef.get("path");
			int type = ((Integer)newRef.get("type")).intValue();
			MetaLibrary newLib = metaManager.createNewMetaLibraryReference(path, type);
			try	{
				metaManager.addMetaLibrary(newLib);
			}
			catch (MetaException E)	{
				JOptionPane.showMessageDialog(this, E.getMessage(), "Message",
							                                      JOptionPane.ERROR_MESSAGE);
			}
			PathObject[] references = getPathsArray(metaManager.getDisplayLibraries());
			importedList.setListData(references);
			importedList.repaint();
		}
	}

	/**
	 * Edits a selected imported library reference.	The selected library is replaced
	 * with the one defined by the user if the new values are different from existing
	 * ones.  
	 * @param e
	 */
	public void edit_actionPerformed(ActionEvent e) {
		if (importedList.getSelectedValue() != null) {
			PathObject selectedPathValue = (PathObject)importedList.getSelectedValue();
			MetaLibrary edited = metaManager.findByPath(selectedPathValue.getFullPath());		
			if (edited == null)	{
				return;
			}
			LibraryLocationDialog addLocation =
				new LibraryLocationDialog(parentFrame,
					edited.getPath(),
					edited.getReferenceType());
			addLocation.setTitle("Location of Imported Library"); 
			HashMap ref = addLocation.showDialog();
			if (ref != null) {
				String path = (String)ref.get("path");
				int type = ((Integer)ref.get("type")).intValue();
				//If the values are different, the library state is changed 
				if ((!edited.getPath().equals(path)) || (edited.getReferenceType() != type))	{
					try	{
						metaManager.changeLibraryState(edited.getPath(), path, type);
					}
					catch (MetaException E)	{
						JOptionPane.showMessageDialog(this, E.getMessage(), "Message",
																		  JOptionPane.ERROR_MESSAGE);
					}
				}
				PathObject[] references = getPathsArray(metaManager.getDisplayLibraries());
				importedList.setListData(references);
				importedList.repaint();
			}
		}
	}
	
	/**
	 * Removes an ontology reference from the list.
	 */
	public void remove_actionPerformed(ActionEvent e) {
		if (importedList.getSelectedValue() != null) {
			PathObject selectedPathValue = (PathObject)importedList.getSelectedValue();
			String selectedValue = selectedPathValue.getFullPath();
			MetaLibrary candidate = metaManager.findByPath(selectedValue);
//			String selectedValue = (String)importedList.getSelectedValue();
			try	{
				if (metaManager.projectHasRolesOfLib(candidate, currentProject))	{
					int retValue = 0;
					retValue =
						JOptionPane.showConfirmDialog(
							parentFrame,
							"The model contains roles of the meta-library that was removed\n" +
							"Are you sure you want to remove the meta-library?",
							"Meta-Library remove",
							JOptionPane.YES_NO_OPTION);
					if (retValue == JOptionPane.NO_OPTION)	{
						return;
					}
				}
				metaManager.markForRemoval(selectedValue);
			}
			catch (MetaException E)	{
				JOptionPane.showMessageDialog(this, E.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
			}
			PathObject[] references = getPathsArray(metaManager.getDisplayLibraries());
			importedList.setListData(references);
			importedList.repaint();
		}
	}
}	//End of class

class OntologyImportsDialog_add_actionAdapter
	implements java.awt.event.ActionListener {
	LibrariesImportsDialog adaptee;

	OntologyImportsDialog_add_actionAdapter(LibrariesImportsDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.add_actionPerformed(e);
	}
}

class OntologyImportsDialog_remove_actionAdapter
	implements java.awt.event.ActionListener {
	LibrariesImportsDialog adaptee;

	OntologyImportsDialog_remove_actionAdapter(LibrariesImportsDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.remove_actionPerformed(e);
	}
}

class OntologyImportsDialog_edit_actionAdapter
	implements java.awt.event.ActionListener {
	LibrariesImportsDialog adaptee;

	OntologyImportsDialog_edit_actionAdapter(LibrariesImportsDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.edit_actionPerformed(e);
	}
}
