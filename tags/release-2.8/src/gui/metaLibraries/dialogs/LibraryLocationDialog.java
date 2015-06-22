package gui.metaLibraries.dialogs;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.metaLibraries.logic.MetaLibrary;
import gui.util.CustomFileFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * A dialog enabling to choose a meta-library to be imported into the current project model. The dialog allow to
* choose the type of the library reference (URL or local file), and the path to the meta-library.
* <p>
* The method <code>showDialog</code> displays the dialog and returns the input given by
* the user after OK was pressed. The return value is a {@link java.util.HashMap} object
* that contains the path and type of the reference. The code below present a scenario
* of using the dialog:<p>
* <code>
* HashMap newRef = addLocation.showDialog();<br>
* String path = (String)newRef.get("path");<br>
* int type = ((Integer)newRef.get("type")).intValue();<br>
* </code>
* 
* @author Eran Toch
 * @version 1.0
 */

/*
 * FIXME: this should realy be in the generic dialog section of OPCAT
 * I'll use it here but i need to move it and call it from the generic part.
 * 
 *  changes - 
 *  	1) remove the Metalibrary dialog title and let the caller set it. 
 *  	2) file dialog title
 *  	3) forgot
 * raanan
 */

public class LibraryLocationDialog extends JDialog {
	private JPanel mainPanel = new JPanel();
	public JRadioButton chooseFile = new JRadioButton();
	private JRadioButton chooseURL = new JRadioButton();
	private ButtonGroup chooseButtonGroup = new ButtonGroup();
	private JLabel choosingLabel = new JLabel();
	private JTextField locationName = new JTextField();
	private JButton cancelButton = new JButton();
	private JButton okButton = new JButton();
	private JButton broswButton = new JButton();
	private JLabel typeLabel = new JLabel();

	/**
	 * The path to the meta-library.
	 */
	private String libraryLocation = "";

	/**
	 * Contains the path to the meta-library. The default is File.
	 */
	private int libraryResourceType = MetaLibrary.TYPE_FILE;
	
	private boolean okPressed = false;
	//Layout manager
	private BorderLayout borderLayout1 = new BorderLayout();
	
	//strings
	private  String ResourceLabel = "Meta-Library Location:" ;
	private String FileChooserTitle = "Add Library" ;

	/**
	 * Base constructor - refer all inputs to super.
	 */
	protected LibraryLocationDialog(
		Frame frame,
		String title,
		boolean modal) {
		super(frame, title, modal);
	}

	/**
	 * Creates a dialog for an existing meta-library reference, allowing the user to modify the
	 * reference.
	 * @param _location String    The meta-library path.
	 * @param _resourceType int   The reference type.
	 * @see   LibraryReference#TYPE_FILE
	 * @see   LibraryReference#TYPE_URL
	 */
	public LibraryLocationDialog(JFrame parent, String _location, int _resourceType) {
		this(parent, "" , false); 
		libraryLocation = _location;
		libraryResourceType = _resourceType;
		init();
	}

	/**
	 * Construct an empty dialog without any existing library reference.
	 */
	public LibraryLocationDialog(JFrame parent) {
		this(parent, "Location of Imported Library", false);
		init();
	}

	/**
	 * JBuilder's init method.
	 */
	public void init() {
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Shows the dialog and return the path of the library that whose choosen.
	 * The path might be a local file path or an URL.
	 * @return String The path of the library location.
	 */
	public HashMap showDialog() {
		this.show();
		if (okPressed) {
			HashMap ref = new HashMap();
			ref.put("path", libraryLocation);
			ref.put("type", new Integer(libraryResourceType));
			return ref;
		} else {
			return null;
		}
	}

	/**
	 * Main dialog creation method.
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		chooseURL.setActionCommand("URL");
		mainPanel.setLayout(null);
		chooseFile.setToolTipText(
			"The imported library is located in the local file system");
		if (libraryResourceType == MetaLibrary.TYPE_FILE) {
			changeToFile();
		} else {
			changeToURL();
		}
		chooseFile.setText("Local File");
		chooseFile.setBounds(new Rectangle(141, 13, 104, 23));
		chooseFile.addActionListener(
			new OntologyLocationDialog_chooseFile_actionAdapter(this));
		chooseURL.setText("URL");
		chooseURL.setBounds(new Rectangle(246, 14, 127, 23));
		chooseURL.addActionListener(
			new OntologyLocationDialog_chooseURL_actionAdapter(this));
		choosingLabel.setToolTipText("");
		choosingLabel.setText(ResourceLabel);
		choosingLabel.setBounds(new Rectangle(11, 49, 294, 15));
		locationName.setBounds(new Rectangle(9, 72, 390, 23));
		locationName.setText(libraryLocation);
		cancelButton.setMinimumSize(new Dimension(71, 23));
		cancelButton.setText("Cancel");
		cancelButton.setBounds(new Rectangle(461, 126, 100, 29));
		cancelButton.setAlignmentX((float) 1.0);
		okButton.setText("OK");
		okButton.setBounds(new Rectangle(351, 126, 100, 29));
		okButton.setAlignmentX((float) 1.0);
		okButton.setPreferredSize(new Dimension(65, 23));
		okButton.addActionListener(
			new OntologyLocationDialog_okButton_actionAdapter(this));
		broswButton.setBounds(new Rectangle(400, 71, 100, 24));
		broswButton.setText("Browse...");
		broswButton.addActionListener(
			new OntologyLocationDialog_broswButton_actionAdapter(this));
		typeLabel.setText("Resource Type:");
		typeLabel.setBounds(new Rectangle(11, 17, 125, 15));
		this.setModal(true);
		this.setTitle("Add Library");
		this.getContentPane().setLayout(borderLayout1);

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setMinimumSize(new Dimension(400, 175));
		mainPanel.setPreferredSize(new Dimension(570, 175));
		mainPanel.add(typeLabel, null);
		mainPanel.add(chooseFile, null);
		mainPanel.add(chooseURL, null);
		mainPanel.add(choosingLabel, null);
		mainPanel.add(cancelButton, null);
		mainPanel.add(okButton, null);
		mainPanel.add(locationName, null);
		mainPanel.add(broswButton, null);

		chooseButtonGroup.add(chooseFile);
		chooseButtonGroup.add(chooseURL);
		cancelButton.addActionListener(
			new OntologyLocationDialog_cancelButton_actionAdapter(this));

		//Setting the icon
		//ImageIcon logoIcon = MiscImages.LOGO_SMALL_ICON;
		//this.setIconImage(logoIcon.getImage());

		this.pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Locating the dialog
		int fX = Opcat2.getFrame().getX();
		int fY = Opcat2.getFrame().getY();
		int pWidth = Opcat2.getFrame().getWidth();
		int pHeight = Opcat2.getFrame().getHeight();

		setLocation(
			fX + Math.abs(pWidth / 2 - getWidth() / 2),
			fY + Math.abs(pHeight / 2 - getHeight() / 2));

		this.setResizable(true);

	}

	/**
	 * Choosing to use file. The method displays the "browse" button.
	 */
	private void changeToFile() {
		if (libraryResourceType != MetaLibrary.TYPE_FILE)	{
			locationName.setText("");
		}
		chooseFile.setSelected(true);
		chooseURL.setSelected(false);
		broswButton.setEnabled(true);
		broswButton.repaint();
		libraryResourceType = MetaLibrary.TYPE_FILE;
	}

	/**
	 * Choosing to use a URL.
	 */
	private void changeToURL() {
		if (libraryResourceType != MetaLibrary.TYPE_URL)	{
			locationName.setText("http://");
		}
		chooseFile.setSelected(false);
		chooseURL.setSelected(true);
		broswButton.setEnabled(false);
		broswButton.repaint();
		libraryResourceType = MetaLibrary.TYPE_URL;
	}

	/**
	 * Clicking on cancel
	 * @param e ActionEvent
	 */
	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * Clicking on browse.
	 * @param e ActionEvent
	 */
	void broswButton_actionPerformed(ActionEvent e) {
		JFileChooser myFileChooser = new JFileChooser();
		myFileChooser.setSelectedFile(new File(""));
		myFileChooser.resetChoosableFileFilters();
		String[] exts = { "opx", "opz" };
		CustomFileFilter myFilter = new CustomFileFilter(exts, "Opcat2 Files");
		myFileChooser.addChoosableFileFilter(myFilter);
		String ld = FileControl.getInstance().getLastDirectory();
		if (!ld.equals("")) {
		    myFileChooser.setCurrentDirectory(new File(ld));
		}
		int retVal = myFileChooser.showDialog(this, FileChooserTitle);

		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		String fileName = myFileChooser.getSelectedFile().getPath();
		String newDirectory = myFileChooser.getSelectedFile().getParent();
        FileControl.getInstance().setLastDirectory(newDirectory);
		locationName.setText(fileName);
		locationName.repaint();
	}

	/**
	 * Clicking on OK: saving the values to the proper fields.
	 * @param e ActionEvent
	 */
	void okButton_actionPerformed(ActionEvent e) {
		libraryLocation = locationName.getText();
		if (chooseFile.isSelected())	{
			libraryResourceType = MetaLibrary.TYPE_FILE;	
		}
		else if (chooseURL.isSelected())	{
			libraryResourceType = MetaLibrary.TYPE_URL;
		}
		okPressed = true;
		this.dispose();
	}

	/**
	 * Clicking on the choose file radio button.
	 * @param e ActionEvent
	 */
	void chooseFile_actionPerformed(ActionEvent e) {
		changeToFile();
	}

	/**
	 * Clicking on the choose URL radio button.
	 * @param e ActionEvent
	 */
	void chooseURL_actionPerformed(ActionEvent e) {
		changeToURL();
	}

	/**
	 * @param resourceLabel The resourceLabel to set.
	 */
	public void setResourceLabel(String resourceLabel) {
		ResourceLabel = resourceLabel;
		choosingLabel.setText(ResourceLabel);
	}

	/**
	 * @param fileChooserTitle The fileChooserTitle to set.
	 */
	public void setFileChooserTitle(String fileChooserTitle) {
		FileChooserTitle = fileChooserTitle;
	}

}

class OntologyLocationDialog_cancelButton_actionAdapter
	implements java.awt.event.ActionListener {
	LibraryLocationDialog adaptee;

	OntologyLocationDialog_cancelButton_actionAdapter(LibraryLocationDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.cancelButton_actionPerformed(e);
	}
}

class OntologyLocationDialog_broswButton_actionAdapter
	implements java.awt.event.ActionListener {
	LibraryLocationDialog adaptee;

	OntologyLocationDialog_broswButton_actionAdapter(LibraryLocationDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.broswButton_actionPerformed(e);
	}
}

class OntologyLocationDialog_okButton_actionAdapter
	implements java.awt.event.ActionListener {
	LibraryLocationDialog adaptee;

	OntologyLocationDialog_okButton_actionAdapter(LibraryLocationDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.okButton_actionPerformed(e);
	}
}

class OntologyLocationDialog_chooseFile_actionAdapter
	implements java.awt.event.ActionListener {
	LibraryLocationDialog adaptee;

	OntologyLocationDialog_chooseFile_actionAdapter(LibraryLocationDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.chooseFile_actionPerformed(e);
	}
}

class OntologyLocationDialog_chooseURL_actionAdapter
	implements java.awt.event.ActionListener {
	LibraryLocationDialog adaptee;

	OntologyLocationDialog_chooseURL_actionAdapter(LibraryLocationDialog adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.chooseURL_actionPerformed(e);
	}
}
