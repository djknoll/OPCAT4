package gui.metaLibraries.dialogs;

import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.RolesManager;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmThing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/**
 * A dialog for handling roles from a {@link MetaLibrary} for an <code>OpmThing</code>.
 *  
 * @author Eran Toch
 * Created: 01/05/2004
 */
public class RolesDialog extends JPanel {

	private Vector selectedRoles;
	private Vector avaliableRoles;
	private boolean okPressed = false;
	JPanel jPanel1 = new JPanel();
	Border border1;
	JButton addButton = new JButton();
	JButton removeButton = new JButton();
	JButton cancelButton = new JButton();
	JScrollPane selectedScroll = new JScrollPane();
	JScrollPane avaliableScroll = new JScrollPane();
	JList selectedList = new JList();
	JList avaliableList = new JList();
	JButton okButton = new JButton();
	private OpmThing theThing;
	private OpdProject myProject;
	private JTextField roleText, noOfInstancesText;
	private JPanel p1, p2; // tmp panels


	public RolesDialog(OpmThing theThing, OpdProject project)
		throws HeadlessException {
		try {
			this.theThing = theThing;
			myProject = project;
			build();
			initLists();
			addListeners();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void build() {
		//Handling buttons
		int buttonWidth = 60;
		//addButton.setSize(new Dimension(buttonWidth, 25));
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		addButton.setHorizontalTextPosition(SwingConstants.TRAILING);
		addButton.setText(" >> ");
		//removeButton.setSize(new Dimension(buttonWidth, 25));
		removeButton.setAlignmentX(CENTER_ALIGNMENT);
		removeButton.setText(" << ");

		//Creating button pane
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
		buttons.add(Box.createRigidArea(new Dimension(buttonWidth, 80)));
		buttons.add(addButton, BorderLayout.PAGE_START);
		buttons.add(Box.createRigidArea(new Dimension(buttonWidth, 10)));
		buttons.add(removeButton, BorderLayout.PAGE_START);
		buttons.setAlignmentY(buttons.CENTER_ALIGNMENT);

		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		//pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Roles Selection "), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		avaliableScroll.setPreferredSize(new Dimension(160, 250));
		avaliableScroll.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Avaliable Roles "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		selectedScroll.setPreferredSize(new Dimension(160, 250));
		selectedScroll.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Selected Roles "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		pane.add(avaliableScroll, BorderLayout.WEST);
		pane.add(buttons, BorderLayout.CENTER);
		pane.add(selectedScroll, BorderLayout.EAST);
		JPanel bigPanel = new JPanel(new BorderLayout());
		bigPanel.add(pane, BorderLayout.CENTER);
		bigPanel.add(_constructMasTab(), BorderLayout.SOUTH);
		
		//pane.setSize(new Dimension(250, 400));
		this.add(bigPanel, null);
		avaliableScroll.getViewport().add(avaliableList, null);
		selectedScroll.getViewport().add(selectedList, null);

		//add(cancelButton, null);
		//add(okButton, null);
		//jPanel1.add(selectedScroll, null);

		//this.add(jPanel1, null);
	}

	private JPanel _constructMasTab() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		JLabel roleLabel = new JLabel("Free Text Role: ");
		roleText = new JTextField();
		roleText.setText(theThing.getFreeTextRole());
				
		JLabel noOfInstancesLabel = new JLabel("Number of instances: ");
		noOfInstancesText =
			new JTextField(String.valueOf(theThing.getNumberOfInstances()));

		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(roleLabel);
		p2.add(Box.createVerticalStrut(15));
		p2.add(noOfInstancesLabel);
		p1.add(p2);

		p1.add(Box.createHorizontalStrut(15));

		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(roleText);
		p2.add(Box.createVerticalStrut(15));
		p2.add(noOfInstancesText);
		p1.add(p2);

		p.add(p1);
		p.add(Box.createVerticalStrut(10));
		return p;
	}

	private void initLists() {
		selectedRoles = ((RolesManager)theThing.getRolesManager().clone()).getRolesVector();
		
		selectedList.setModel(new RolesListModel(selectedRoles));
		selectedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Vector allRoles = new Vector();
		Enumeration ontologies = myProject.getMetaManager().getMetaLibraries();
		try {
			while (ontologies.hasMoreElements()) {
				MetaLibrary ont = (MetaLibrary) ontologies.nextElement();
				allRoles.addAll(ont.getRolesCollection());
			}
		} catch (MetaException E) {
			//JOptionPane.showMessageDialog(this, E.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
			//OpcatLogger.logError(E);
		}

		avaliableRoles = new Vector();
		for (int i = 0; i < allRoles.size(); i++) {
			Role role = (Role) allRoles.get(i);
			OpmThing thing = role.getThing();
			if (theThing instanceof OpmObject) {
				if (thing instanceof OpmObject) {
					avaliableRoles.add(role);
				}
			} else if (theThing instanceof OpmProcess) {
				if (thing instanceof OpmProcess) {
					avaliableRoles.add(role);
				}
			}
		}
		//avaliableRoles = Opcat2.getOntology().getRolesCollection();
		//Iterator it = avaliableRoles.iterator();
		
		
		//clean list
		avaliableList.setModel(new RolesListModel(avaliableRoles));
		selectedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Enumeration enum = avaliableRoles.elements();
		while (enum.hasMoreElements())	{
			Role runner = (Role)enum.nextElement();
			if (theThing.getRolesManager().contains(runner))	{
				avaliableRoles.remove(runner);
			}
		}
		
	}

	private void addListeners() {

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPressed();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePressed();
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okPressed = true;
				selectedRoles =
					((RolesListModel) (selectedList.getModel())).getRoles();
				done();
			}
		});

		//cancel - close Dialog without doint anything
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okPressed = false;
				done();
			}
		});

	}

	private void done() {
		this.setVisible(false);
	}

	private void addPressed() {
		RolesListModel avaliableModel = (RolesListModel) (avaliableList.getModel());
		Role theRole = (Role) (avaliableList.getSelectedValue());
		if ((avaliableList.getSelectedValue() == null)
			|| (avaliableList.getSelectedIndex() == avaliableModel.getSize())) {
			JOptionPane.showMessageDialog(
				null,
				"Please select a role to add");
			return;
		}
		if (!((RolesListModel) (selectedList.getModel())).addRole(theRole)) {
			JOptionPane.showMessageDialog(null, "Role Already Selected");
		}
		RolesListModel selectedModel = (RolesListModel) (selectedList.getModel());
		avaliableModel.removeRole(theRole);
		selectedModel.addRole(theRole);
	}

	private void removePressed() {
		RolesListModel selectedModel = (RolesListModel) (selectedList.getModel());
		if ((selectedList.getSelectedValue() == null)
			|| (selectedList.getSelectedIndex() == selectedModel.getSize())) {
			JOptionPane.showMessageDialog(
				null,
				"Please select a role to remove");
			return;
		}
		Role theRole = (Role)(selectedList.getSelectedValue());
		RolesListModel avaliableModel = (RolesListModel) (avaliableList.getModel());
		avaliableModel.addRole(theRole);
		selectedModel.removeRole(theRole);
	}

	public Vector getRoles() {
		return selectedRoles;
	}
	
	public String getRoleText()	{
		return roleText.getText();
	}
	
	public String getNoOfInstances()	{
		return noOfInstancesText.getText();
	}

	public RolesDialog() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setMinimumSize(new Dimension(200, 300));
	}
}
