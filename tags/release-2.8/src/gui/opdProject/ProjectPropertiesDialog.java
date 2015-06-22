package gui.opdProject;

import gui.Opcat2;
import gui.images.misc.MiscImages;
import gui.metaLibraries.dialogs.LibrariesImportsDialog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
//end of by eran's imports

/**
 * This class is a Properties Dialog for Project.
 * It displays such information as author, name, creation date of the project
     * and gives change them, excepting creation date. Furthermore, the dialog allows
 * allows authors to add requirements and implementations information in free text
 * to the system. Afterwards, the information is processed by the <code>Documents</code>
 * tool.
 * @see     extensionTools#Documents
 */
public class ProjectPropertiesDialog
    extends JDialog {
  private final int NUM_OF_ENTRIES = 11;
  JTabbedPane tabs = new JTabbedPane();
  JPanel advanced = new JPanel();
  JPanel basic = new JPanel();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();
  private JTextField name = new JTextField();
  private JTextField creator = new JTextField();
  //private JTextField client = new JTextField();
  private JTextField date = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  //Holds an aray of GenInfo entries
  private GenInfoEntry[] informations;
  //Holds the current GenInfoEntry that the focus is on
  GenInfoEntry current = null;
  OpdProject currentProject = null;
  private boolean okPressed;
  private JLabel genInfoLabels = new JLabel();
  private JLabel fieldLabel = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea textarea = new JTextArea();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JList genList = new JList();
  JLabel logoLabel = new JLabel(MiscImages.LOGO_BIG_ICON);
  private JFrame parentFrame;
  //private JScrollPane scrollPane = new JScrollPane();

  /**
   * Contains a dialog in which the user can select the libraries to import.
   * @author Eran Toch
   */
  private LibrariesImportsDialog importedLibraries;
  
  
  /**
   *  Constructor.<br>
   *  Constructs and shows <code>ProjectPropertiesDialog</code>.
   *  @param <code>currProject <a href = "OpdProject.html">OpdProject</a><code>, the project to show properties for.
   *  @param  <code>parent JFrame</code>, the <code>Frame</code> from which the dialog is displayed.
   */
  public ProjectPropertiesDialog(OpdProject currProject, JFrame parent,
                                 String title) {
    super(parent, title, true);
    currentProject = currProject;
    parentFrame = parent;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *  An empty Constructor.<br>
   */
  public ProjectPropertiesDialog() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Builds and constructs the dialog.
       * THe method initialize all the dialog variables (name, creator, date and all
   * the general informaiton variables. The general information variables are
   * hard coded into the code in this method. The code should be updated if any
   * change should be done to the list.
   * @see   GenInfoEntry
   */
  private void jbInit() throws Exception {
    //A list of all the entries.
    informations = new GenInfoEntry[NUM_OF_ENTRIES];
    informations[0] = new GenInfoEntry("System_Overview", "System Overview",
                                       currentProject.getInfoValue(
        "System_Overview"));
    informations[1] = new GenInfoEntry("Goals_and_Objectives_of_the_Project",
                                       "Goals and Objectives",
                                       currentProject.
                                       getInfoValue("Goals_and_Objectives_of_the_Project"));
    informations[2] = new GenInfoEntry("Possible_Users_for_the_System",
                                       "Possible Users",
                                       currentProject.
                                       getInfoValue("Possible_Users_for_the_System"));
    informations[3] = new GenInfoEntry("Hardware_and_Software_Requirements",
                                       "Hardware and Software",
                                       currentProject.
                                       getInfoValue("Hardware_and_Software_Requirements"));
    informations[4] = new GenInfoEntry("Inputs_Processing",
                                       "Input / Output Definition",
                                       currentProject.getInfoValue(
        "Inputs_Processing"));
    informations[5] = new GenInfoEntry("Future_Goals", "Future Goals",
                                       currentProject.getInfoValue(
        "Future_Goals"));
    informations[6] = new GenInfoEntry("Operation_and_Maintenance",
                                       "Operation and Maintenance",
                                       currentProject.getInfoValue(
        "Operation_and_Maintenance"));
    informations[7] = new GenInfoEntry("Problems", "Problems",
                                       currentProject.getInfoValue("Problems"));
    informations[8] = new GenInfoEntry("The_Current_State", "The Current State",
                                       currentProject.getInfoValue(
        "The_Current_State"));
    informations[9] = new GenInfoEntry("Open_Issues", "Open Issues",
                                       currentProject.getInfoValue(
        "Open_Issues"));
	informations[10] = new GenInfoEntry("Client", "Client",
										   currentProject.getInfoValue(
			"Client"));

    //Setting the first choice of the general information
    current = informations[0];

    //Init of the regular state variables
    //client = new JTextField(currentProject.getInfoValue("Client"));
    creator = new JTextField(currentProject.getCreator());
    name = new JTextField(currentProject.getName());
    date = new JTextField(currentProject.getCreationDate().toString());
    date.setEditable(false);

    okButton.addActionListener(new
                               ProjectPropertiesDialog_okButton_actionAdapter(this));
    cancelButton.addActionListener(new
        ProjectPropertiesDialog_cancelButton_actionAdapter(this));
    genInfoLabels.setToolTipText("");
    genInfoLabels.setText("General Information Fields:");
    genInfoLabels.setBounds(new Rectangle(16, 13, 178, 22));
    fieldLabel.setBounds(new Rectangle(223, 13, 187, 20));
    jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                              HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setBounds(new Rectangle(230, 48, 185, 156));
    textarea.setLineWrap(true);
    textarea.setText(informations[0].getValue());
    jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                              HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane2.setBounds(new Rectangle(15, 48, 194, 156));

    genList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    genList.addListSelectionListener(new
        ProjectPropertiesDialog_genList_listSelectionAdapter(this));
    logoLabel.setBounds(new Rectangle(25, 42, 39, 43));
    basic.add(creator, null);
    //basic.add(client, null);
    basic.add(date, null);
    basic.add(logoLabel, null);
    basic.add(name, null);
    basic.add(jLabel3, null);
    basic.add(jLabel1, null);
    basic.add(jLabel2, null);
    basic.add(jLabel4, null);
    tabs.add(basic, " Basic ");
    tabs.add(advanced, " Advanced ");

	//	by eran toch - Handling import tab
	importedLibraries = new LibrariesImportsDialog(currentProject.getMetaManager(), currentProject, parentFrame);
	tabs.add(importedLibraries, " Meta-Libraries ");
	  
    jLabel4.setText("Creation Date:");
    jLabel4.setBounds(new Rectangle(84, 120, 119, 25));
    //jLabel3.setText("System Client:");
    //jLabel3.setBounds(new Rectangle(84, 120, 119, 25));
    jLabel2.setText("System Creator:");
    jLabel2.setBounds(new Rectangle(84, 80, 119, 25));
    jLabel1.setText("System Name:");
    jLabel1.setBounds(new Rectangle(84, 42, 119, 25));
    this.getContentPane().setLayout(null);
    advanced.setLayout(null);
    tabs.setBounds(new Rectangle(3, 4, 435, 258));
    cancelButton.setBounds(new Rectangle(343, 278, 96, 28));
    cancelButton.setText("Cancel");
    okButton.setBounds(new Rectangle(235, 278, 96, 28));
    okButton.setText("OK");
    basic.setLayout(null);
    name.setBounds(new Rectangle(220, 42, 167, 25));
    creator.setBounds(new Rectangle(220, 80, 167, 25));
    //client.setBounds(new Rectangle(220, 120, 167, 25));
    date.setEditable(false);
    date.setBounds(new Rectangle(220, 120, 167, 25));
    this.getContentPane().add(tabs, null);
    advanced.add(genInfoLabels, null);
    advanced.add(jScrollPane1, null);
    advanced.add(fieldLabel, null);
    advanced.add(jScrollPane2, null);
    jScrollPane2.getViewport().add(genList, null);
    jScrollPane1.getViewport().add(textarea, null);
    this.getContentPane().add(cancelButton, null);
    this.getContentPane().add(okButton, null);
    this.setBounds(0, 0, 460, 360);
    this.setLocationRelativeTo(this.getParent());
    genList.setListData(informations);
    genList.setSelectedIndex(0);
  }

  /**
   * Shows the dialog.
   * @return   <code>true</code> if the OK was pressed.
   */
  public boolean showDialog() {
    this.setVisible(true);
    return isOkPressed();
  }
  
  /**
   * Shows the dialog at the meta-libraries state. 
   * @return	<code>true</code> if the OK was pressed.
   */
  public boolean showDialogAtLibraries()	{
  	tabs.setSelectedComponent(importedLibraries);
  	return showDialog();
  }

  /**
       * Changes the selection of the general information entry. The method saves the
   * last entry value in the <code>informations</code> array. Afterwards, the method
   * changes the <code>textarea</code> input field, according to the selection.
   * @see #informations
   */
  void genList_valueChanged(ListSelectionEvent e) {
    //Getting the current selection
    saveCurrentInfo();

    //changing the textarea to reflect the change
    current = (GenInfoEntry) genList.getSelectedValue();
    if (current == null)
      return;
    textarea.setText(current.getValue());
    fieldLabel.setText(current.toString() + ":");
  }

  private void saveCurrentInfo() {
    if (current != null) {
      GenInfoEntry old = searchEntry(current.getKey());
      if (old != null) {
        if (textarea.getText() != null && !textarea.getText().equals("")) {
          old.setValue(textarea.getText());
        }
      }
    }
    return;
  }

  /**
   * Returns the GenInfoEntry, according to the given key.
   */
  GenInfoEntry searchEntry(String key) {
    for (int i = 0; i < informations.length; i++) {
      if (informations[i].getKey().equals(key)) {
        return informations[i];
      }
    }
    return null;
  }

  void okButton_actionPerformed(ActionEvent e) {
    doOK();
  }

  void doOK() {
    saveCurrentInfo();
    currentProject.setName(name.getText());
    currentProject.setCreator(creator.getText());
    Opcat2.getFrame().setTitle("Opcat II - " + name.getText());
    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
    currentProject.setInfoValue("Client", "");
    GenInfoEntry entry;
    for (int i = 0; i < informations.length; i++) {
      entry = informations[i];
      if (entry != null & entry.getKey() != null && entry.getValue() != null) {
        currentProject.setInfoValue(entry.getKey(), entry.getValue());
      }
    }	  
    okPressed = true;
    this.dispose();
  }
  
  /**
   * Returns the meta
   * Copies the list of references to the local <code>importedLibs</code> variable.
   * @author Eran Toch
   */
  public Vector getMetaLibraries()	{
  	return importedLibraries.getMetaManager().getVectorClone();
  }

  public boolean isOkPressed() {
    return okPressed;
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    okPressed = false;
    this.dispose();
  }
}

class ProjectPropertiesDialog_okButton_actionAdapter
    implements java.awt.event.ActionListener {
  private ProjectPropertiesDialog adaptee;

  ProjectPropertiesDialog_okButton_actionAdapter(ProjectPropertiesDialog
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class ProjectPropertiesDialog_cancelButton_actionAdapter
    implements java.awt.event.ActionListener {
  private ProjectPropertiesDialog adaptee;

  ProjectPropertiesDialog_cancelButton_actionAdapter(ProjectPropertiesDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class ProjectPropertiesDialog_genList_listSelectionAdapter
    implements javax.swing.event.ListSelectionListener {
  ProjectPropertiesDialog adaptee;

  ProjectPropertiesDialog_genList_listSelectionAdapter(ProjectPropertiesDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void valueChanged(ListSelectionEvent e) {
    adaptee.genList_valueChanged(e);
  }
}