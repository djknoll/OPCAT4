package gui.opdGraphics.dialogs;

import gui.Opcat2;
import gui.metaLibraries.dialogs.LibraryLocationDialog;
import gui.metaLibraries.dialogs.RolesDialog;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.projectStructure.Entry;
import gui.projectStructure.ObjectEntry;
import gui.undo.UndoableChangeObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.UndoableEditEvent;

/**
 * Importing ontologies package.
 * @author Eran Toch
 */

/**
 *  This class is a properties dialog box for <a href = "OpdObject.html"><code>OpdObject</code></a>.
 *  <p>Constructs and shows properties dialog box for one of the OPD Objects
 *
 *  <br>Shown when <code>callPropertiesDialog()</code> is called.
 */
public class ObjectPropertiesDialog
	extends JDialog
	implements ComponentListener {
	private JTextField indexName, initialValue, indexOrder;
	private JTextArea name, description, url, generaldescription /*, type*/;
	private JRadioButton physical, informational, environmental, system;
	private JComboBox scope;
	private JCheckBox isPersistent, isKey;
	private JButton okButton,
		cancelButton,
		applyButton,
		bgColor,
		textColor,
		borderColor, resourceButton;
	private ButtonGroup piBg, esBg;
	private JScrollPane /*typeScrollPane, */
	descriptionScrollPane, generaldescriptionScrollPane, nameScrollPane, urlScrollPane;
	private JPanel p1, p2; // tmp panels
	private OpmObject object;
	private OpdObject opdObject;
	private ObjectEntry myEntry;
	protected JList stateList;
	private StatePanel statesPanel;
	private OpdProject myProject;
	private ObjectTypePanel objTypePanel;
	private JTabbedPane tabs;
	private DirectionPanel dp;
	private OpdObject sampleObject;
	private String defaultName;
	private String defaultUrl;
	private final int numTabs = 3;
	private int showTabs;
	private boolean okPressed = false;
	private boolean isCreation;

	/**
	* A JPanel containing roles selection.
	* @author Eran Toch
	*/
	private RolesDialog rolesTab;

	/**
	 *  Constructor:
	 *  @param <code>parent</code> -- parent frame, Opcat2 application window or <code>null</code>
	 *  @param <code>OpdObject pObject</code> -- the OPD object to show properties dialog for.
	 *  @param <code>ThingEntry pEntry</code> -- the <code>projectStructure.ThingEntry</code> class, entry of this OPD Object in project dynamic structure.
	 *  @see <a href = "ThingEntry.html"><code>ThingEntry</code></a> class documentation for more information.
	 */
	public ObjectPropertiesDialog(
		OpdObject pObject,
		ObjectEntry pEntry,
		OpdProject project,
		boolean isCreation) {
		super(Opcat2.getFrame(), "OPD Object Properties", true);

		this.addComponentListener(this);
		opdObject = pObject;
		object = (OpmObject) pObject.getEntity();
		myEntry = pEntry;
		myProject = project;
		defaultName = "";
		defaultUrl = "";

		this.isCreation = isCreation;

		int showButtons;
		if (!isCreation) {
			showTabs = BaseGraphicComponent.SHOW_ALL_TABS;
			showButtons = BaseGraphicComponent.SHOW_ALL_BUTTONS;
		} else {
			showTabs =
				BaseGraphicComponent.SHOW_ALL_TABS
					- BaseGraphicComponent.SHOW_3;
			showButtons =
				BaseGraphicComponent.SHOW_OK | BaseGraphicComponent.SHOW_CANCEL;
		}

		// init all variables
		_initVariables();

		Container contPane = getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));

		tabs = new JTabbedPane();

		if ((showTabs & BaseGraphicComponent.SHOW_1) != 0) {
			tabs.add("General", _constructGeneralTab());
		}

		if ((showTabs & BaseGraphicComponent.SHOW_7) != 0) {
			tabs.add("Description", _constructDescriptionTab());
		}
	
		if ((showTabs & BaseGraphicComponent.SHOW_2) != 0) {
			tabs.add("Keys & Indices", _constructKeysAndIndecesTab());
		}
		if ((showTabs & BaseGraphicComponent.SHOW_3) != 0) {
			tabs.add("States", _constructStatesTab());
		} else {
			tabs.add("States", _constructDummyStatesTab());
		}
		
		//Adding roles tab to the general tabs
		//By Eran Toch
		if ((showTabs & BaseGraphicComponent.SHOW_4) != 0) 
		{
			  tabs.add("Roles", _constructRolesTab());
		}
		
		if ((showTabs & BaseGraphicComponent.SHOW_MISC) != 0) {
			tabs.add("Misc.", _constructMiscTab());
		}

		contPane.add(tabs);

		// -----------------------------------------------------------------
		// add buttons
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridLayout layout = new GridLayout(1, 4);
		layout.setHgap(3);
		p1.setLayout(layout);
		p1.add(Box.createGlue());
		if ((showButtons & BaseGraphicComponent.SHOW_OK) != 0) {
			okButton = new JButton("OK");
			okButton.addActionListener(new OkListener());
			getRootPane().setDefaultButton(okButton);
			p1.add(okButton);
		}
		if ((showButtons & BaseGraphicComponent.SHOW_CANCEL) != 0) {
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelListener());
			p1.add(cancelButton);
		}
		if ((showButtons & BaseGraphicComponent.SHOW_APPLY) != 0) {
			applyButton = new JButton("Apply");
			applyButton.addActionListener(new ApplyListener());
			p1.add(applyButton);
		}
		contPane.add(p1);
		// -----------------------------------------------------------------

		// Escape & Enter listener
		KeyListener escapeAndEnterListener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					(ObjectPropertiesDialog.this).dispose();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER
					&& !description.hasFocus() /* && !type.hasFocus()*/
					) {
					if ((ObjectPropertiesDialog.this)._updateObjectData())
						 (ObjectPropertiesDialog.this).dispose();
					return;
				}
			}
		};

		addKeyListener(escapeAndEnterListener);

		this.pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		int fX = Opcat2.getFrame().getX();
		int fY = Opcat2.getFrame().getY();
		int pWidth = Opcat2.getFrame().getWidth();
		int pHeight = Opcat2.getFrame().getHeight();

		setLocation(
			fX + Math.abs(pWidth / 2 - getWidth() / 2),
			fY + Math.abs(pHeight / 2 - getHeight() / 2));
		this.setResizable(false);
		//reuseComment
		if (pEntry.isLocked())
			commitLockForEdit();
		//endReuseComment

	}

	private void _initVariables() {
		try {
			if ((showTabs & BaseGraphicComponent.SHOW_1) != 0) {
				//general tab
				if (object.getName().trim().equals("")) {
					name = new JTextArea(defaultName, 3, 15);
				} else {
					name = new JTextArea(object.getName(), 3, 15);
				}
				name.setRequestFocusEnabled(true);
				name.setLineWrap(true);
				name.setWrapStyleWord(true);

				
				//set the url to be presented 
				if(object.getUrl().trim().equals(""))
				{
					url = new JTextArea(defaultUrl, 4, 24);
				}
				else
				{
					url = new JTextArea(object.getUrl(), 4, 24);
				}
				url.setLineWrap(true);
				url.setWrapStyleWord(true);
				urlScrollPane = new JScrollPane(url, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
								
				
				
				initialValue = new JTextField(object.getInitialValue());

				physical = new JRadioButton("Physical");
				physical.addActionListener(positionAction);
				informational = new JRadioButton("Informatical");
				informational.addActionListener(positionAction);
				piBg = new ButtonGroup();
				piBg.add(physical);
				piBg.add(informational);
				if (object.isPhysical()) {
					physical.setSelected(true);
				} else {
					informational.setSelected(true);
				}
				//				type = new JTextArea(object.getType(), 3, 30);
				environmental = new JRadioButton("Environmental");
				environmental.addActionListener(positionAction);
				system = new JRadioButton("Systemic");
				system.addActionListener(positionAction);
				esBg = new ButtonGroup();
				esBg.add(environmental);
				esBg.add(system);
				if (object.isEnviromental()) {
					environmental.setSelected(true);
				} else {
					system.setSelected(true);
				}

				scope = new JComboBox();
				scope.addItem("Public");
				scope.addItem("Protected");
				scope.addItem("Private");
				scope.setSelectedIndex(
					Character.digit((object.getScope()).charAt(0), 3));
				// end general tab
			}

			if ((showTabs & BaseGraphicComponent.SHOW_2) != 0) {
				//keys & indices
				isPersistent = new JCheckBox("Persistent");
				if (object.isPersistent()) {
					isPersistent.setSelected(true);
				}

				isKey = new JCheckBox("Key");
				if (object.isKey()) {
					isKey.setSelected(true);
				}

				indexOrder =
					new JTextField(
						(new Integer(object.getIndexOrder())).toString());
				indexName = new JTextField(object.getIndexName());

				/*
				 * FIXME: the idiots used the description generic attribute of the opmEntity for
				 * the keys description and now when i want to add a real description 
				 * i am fucked. need to replace this by a KeysDescription attribute of an object 
				 * class  and add this to th esave proc.
				 * save it and use this description as it was intended. 
				 * raanan
				 */
				description = new JTextArea(object.getDescription(), 8, 30);
				descriptionScrollPane =
					new JScrollPane(
						description,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				description.setLineWrap(true);
				description.setWrapStyleWord(true);
				//end keys & indices
				
				//description
				//generaldescription = new JTextArea(object.getGeneralDescription(), 8, 30);
				generaldescription = new JTextArea("NOT IMPLEMENTED YET, do not enter anything here", 8, 30);
				generaldescriptionScrollPane =
					new JScrollPane(
						generaldescription,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				generaldescription.setLineWrap(true);
				generaldescription.setWrapStyleWord(true);
			}

			stateList = new JList();

		} catch (Exception e) {
			System.out.println("Problem in ObjectPropertiesDialog!!");
		}

		//		typeScrollPane = new JScrollPane(type, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//		type.setBorder(BorderFactory.createEtchedBorder());
		//		type.setLineWrap(true);
		//		type.setWrapStyleWord(true);

		if ((showTabs & BaseGraphicComponent.SHOW_MISC) != 0) {
			bgColor = new JButton("          ");
			bgColor.setBackground(opdObject.getBackgroundColor());
			textColor = new JButton("          ");
			textColor.setBackground(opdObject.getTextColor());
			borderColor = new JButton("          ");
			borderColor.setBackground(opdObject.getBorderColor());
		}
	}

	private JPanel _constructGeneralTab() {
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Object Name / Init Value
		p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2));
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Object Name "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

		nameScrollPane =
			new JScrollPane(
				name,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p1.add(nameScrollPane);
		p2.add(p1);

		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Initial Value "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		//p1.add(Box.createVerticalStrut(30));
		p1.add(initialValue);
		p1.add(Box.createVerticalStrut(30));
		p2.add(p1);
		tab.add(p2);

		// type
		objTypePanel = new ObjectTypePanel(myEntry, myProject);
		tab.add(objTypePanel);

		// Radio buttons panel group
		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

		// Essence group
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Essence "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p2.add(physical);
		p2.add(Box.createVerticalStrut(5));
		p2.add(informational);
		p1.add(p2);

		p1.add(Box.createHorizontalStrut(10));

		// Origin group
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Origin "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p2.add(environmental);
		p2.add(Box.createVerticalStrut(5));
		p2.add(system);
		p1.add(p2);

		tab.add(p1);

		// scope ComboBox
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		JLabel l2 = new JLabel("Scope:   ");
		p1.add(l2);
		p1.add(Box.createHorizontalStrut(10));
		p1.add(scope);
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(p1);

		tab.add(p2);
		return tab;
	}
	
	private JPanel _constructDescriptionTab() {
		JPanel tab = new JPanel();
		JPanel p1 ; 
		
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		

		// Process Description
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Description "));
		p1.add(generaldescriptionScrollPane);
		p1.setVisible(false) ;
		tab.add(p1);
		
		// URL
		// Process Body
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " URL "));
		resourceButton = new JButton("URL");
		resourceButton.addActionListener(new resourceListner()) ;  
		p1.add(urlScrollPane); 
		p1.add( resourceButton);		
		tab.add(p1);		

		return tab;
	}

	private JPanel _constructKeysAndIndecesTab() {
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// pesist/key checkboxes
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		p1.add(isPersistent);
		p1.add(Box.createHorizontalStrut(40));
		p1.add(isKey);

		tab.add(p1);

		// index name
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Index Name "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(indexName);
		tab.add(p1);

		// index order
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Index Order "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(indexOrder);
		tab.add(p1);

		//tab.add(Box.createVerticalStrut(160));

		//description
		p1 = new JPanel();
		p1.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Description "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		JScrollPane sp =
			new JScrollPane(
				description,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p1.add(sp);
		tab.add(p1);
		tab.add(Box.createVerticalStrut(30));

		return tab;
	}

	private JPanel _constructStatesTab() {
		statesPanel = new StatePanel(opdObject, myProject);
		statesPanel.add(Box.createVerticalStrut(45));
		return statesPanel;
	}

	private JPanel _constructDummyStatesTab() {
		JPanel p = new JPanel();
		//p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel l =
			new JLabel(
				"You have to confirm the Object addition before adding States.",
				gui.images.misc.MiscImages.LOGO_BIG_ICON,
				JLabel.CENTER);
		p.add(Box.createVerticalStrut(320));
		p.add(l);
		return p;
	}

	private JPanel _constructMiscTab() {
		JPanel retTab = new JPanel();
		retTab.setLayout(new BoxLayout(retTab, BoxLayout.Y_AXIS));

		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.X_AXIS));
		tab.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Colors "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		JLabel bgColorLabel = new JLabel("Background Color:");
		JLabel textColorLabel = new JLabel("Text Color:");
		JLabel borderColorLabel = new JLabel("Border Color:");
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		p1.add(bgColorLabel);
		p1.add(Box.createVerticalStrut(20));
		p1.add(textColorLabel);
		p1.add(Box.createVerticalStrut(20));
		p1.add(borderColorLabel);
		//p1.add(Box.createVerticalStrut(112));
		bgColor.addActionListener(new BgColorButtonListener());
		textColor.addActionListener(new TextColorButtonListener());
		borderColor.addActionListener(new BorderColorButtonListener());
		p2.add(bgColor);
		p2.add(Box.createVerticalStrut(8));
		p2.add(textColor);
		p2.add(Box.createVerticalStrut(8));
		p2.add(borderColor);
		//p2.add(Box.createVerticalStrut(112));
		tab.add(p1);
		tab.add(p2);
		//tab.add(Box.createHorizontalStrut(140));
		retTab.add(tab);

		JPanel textPositionPanel = new JPanel();
		textPositionPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Text Position "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		textPositionPanel.setLayout(new BorderLayout());
		dp = new DirectionPanel(true, "try str", positionAction);
		dp.setSelection(opdObject.getTextPosition());
		textPositionPanel.add(dp, BorderLayout.CENTER);
		textPositionPanel.add(Box.createVerticalStrut(40), BorderLayout.NORTH);
		textPositionPanel.add(Box.createVerticalStrut(40), BorderLayout.SOUTH);

		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					" Preview "),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		ObjectEntry sampleEntry =
			new ObjectEntry(new OpmObject(0, "Sample"), myProject);
		previewPanel.setLayout(new BorderLayout());
		//        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.X_AXIS));
		sampleObject = new OpdObject(sampleEntry, myProject, 0, 0);
		sampleObject.setTextPosition(opdObject.getTextPosition());
		sampleObject.setBackgroundColor(opdObject.getBackgroundColor());
		sampleObject.setBorderColor(opdObject.getBorderColor());
		sampleObject.setTextColor(opdObject.getTextColor());
		if (opdObject.isDashed()) {
			sampleObject.setDashed(true);
		}
		if (opdObject.isShadow()) {
			sampleObject.setShadow(true);
		}

		previewPanel.add(sampleObject, BorderLayout.CENTER);
		previewPanel.add(Box.createVerticalStrut(40), BorderLayout.NORTH);
		previewPanel.add(Box.createVerticalStrut(40), BorderLayout.SOUTH);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.add(textPositionPanel);
		bottomPanel.add(previewPanel);

		retTab.add(bottomPanel);
		return retTab;
	}

	/**
		 * Construct a roles tab, using the RolesDialog class.
		 * @return The roles JPanel class.
		 * @author Eran Toch
		 */
	private JPanel _constructRolesTab() {
		rolesTab = new RolesDialog(object, myProject);
		return rolesTab;
	}

	private boolean _updateObjectData() {
		OpmObject oldObject = new OpmObject(-1, "");
		oldObject.copyPropsFrom(object);

		//general
		if ((showTabs & BaseGraphicComponent.SHOW_1) != 0) {
			if (name.getText().equals(defaultName)
				|| name.getText().trim().equals("")) {
				tabs.setSelectedIndex(0);
				name.requestFocus();
				JOptionPane.showMessageDialog(
					Opcat2.getFrame(),
					"You should provide a name for the Object",
					"Opcat2 - Error",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (name.getText().trim().toLowerCase().endsWith("ing")) {
				String[] options = { "OK", "Edit Name" };
				JOptionPane pane =
					new JOptionPane(
						"If the object name ends with \"ing\" the suffix \"Object\"\n"
							+ "will be added to it in the OPL, so it will be called \""
							+ GraphicsUtils.capitalizeFirstLetters(
								name.getText().trim())
							+ " Object\"",
						JOptionPane.WARNING_MESSAGE,
						JOptionPane.YES_NO_OPTION,
						null,
						options,
						options[0]);

				pane.createDialog(Opcat2.getFrame(), "Opcat2 - Warning").setVisible(true);

				if (!options[0].equals(pane.getValue())) {
					tabs.setSelectedIndex(0);
					name.requestFocus();
					return false;
				}
			}

			try {
				Integer.decode(indexOrder.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					this,
					"Index Order is not an integer",
					"Opcat 2 - Error",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (!object.equals(name.getText())) {
				object.setName(
					GraphicsUtils.capitalizeFirstLetters(
						name.getText().trim()));
				_updateObjectsTypes(name.getText());
			}

			if (!object.equals(url.getText())) {
				object.setUrl(
					GraphicsUtils.capitalizeFirstLetters(
						url.getText().trim()));
			}
			
			object.setInitialValue(initialValue.getText());
			//object.setType(type.getText());
			object.setType(objTypePanel.getType());

			object.setTypeOriginId(objTypePanel.getTypeObjectId());
			object.setPhysical(physical.isSelected());
			object.setEnviromental(environmental.isSelected());
			object.setScope(String.valueOf(scope.getSelectedIndex()));
		}
		//end general

		//keys & indicies
		if ((showTabs & BaseGraphicComponent.SHOW_2) != 0) {
			object.setKey(isKey.isSelected());
			object.setPersistent(isPersistent.isSelected());
			object.setIndexName(indexName.getText());
			try {
				object.setIndexOrder(
					(Integer.decode(indexOrder.getText())).intValue());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					this,
					"Index Order is not an integer",
					"Opcat 2 - Error",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}
			object.setDescription(description.getText());
		}
		//end keys & indicies

		//misc
		if ((showTabs & BaseGraphicComponent.SHOW_MISC) != 0) {
			opdObject.setBackgroundColor(bgColor.getBackground());
			opdObject.setTextColor(textColor.getBackground());
			opdObject.setBorderColor(borderColor.getBackground());
		}
		//end misc

		//states
		if ((showTabs & BaseGraphicComponent.SHOW_3) != 0) {
			opdObject.setStatesAutoarrange(statesPanel.isAutoarrange());
			opdObject.setStatesDrawingStyle(statesPanel.getStatesView());
			opdObject.setTextPosition(dp.getSelection());

			DefaultListModel listModel = statesPanel.getListModel();
			StateListCell cell;
			for (Enumeration e = listModel.elements(); e.hasMoreElements();) {
				cell = (StateListCell) (e.nextElement());
				if (cell.isSelected() && !(cell.getOpdState().isVisible())) {
					cell.getOpdState().setVisible(true);
				}

				if (!(cell.isSelected()) && cell.getOpdState().isVisible()) {
					cell.getOpdState().setVisible(false);
				}
			}
		}
		//ens states

		if ((showTabs & BaseGraphicComponent.SHOW_4) != 0) {
			object.setRole(rolesTab.getRoleText());
			int noi = 0;
			try {
				noi = Integer.parseInt(rolesTab.getNoOfInstances());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(
					Opcat2.getFrame(),
					"Number Of Instances must be a positive integer",
					"Opcat2 - Error",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if (noi < 1) {
				JOptionPane.showMessageDialog(
					Opcat2.getFrame(),
					"Number Of Instances must be a positive integer",
					"Opcat2 - Error",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}
			object.setNumberOfInstances(noi);
		}

		/********* Handling Roles **************/
		//Handling roles selection, when the OK button is pressed.
		//by Eran Toch
		object.getRolesManager().setRoles(rolesTab.getRoles());

		myEntry.updateInstances();

		if (!isCreation && !object.hasSameProps(oldObject)) {
			Opcat2.getUndoManager().undoableEditHappened(
				new UndoableEditEvent(
					myProject,
					new UndoableChangeObject(
						myProject,
						myEntry,
						oldObject,
						object)));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
		return true;
	}

	private void _updateObjectsTypes(String newName) {
		for (Enumeration e = myProject.getComponentsStructure().getElements();
			e.hasMoreElements();
			) {
			Entry currEntry = (Entry) e.nextElement();

			if (currEntry instanceof ObjectEntry) {
				OpmObject currObject = (OpmObject) currEntry.getLogicalEntity();
				if (currObject.getTypeOriginId() == object.getId()) {
					currObject.setType(newName);
					currEntry.updateInstances();
				}
			}
		}

		return;
	}

	public boolean showDialog() {
		this.setVisible(true);
		return okPressed;
	}

	public void componentShown(ComponentEvent e) {
		name.requestFocus();
	}
	public void componentHidden(ComponentEvent e) {
	}
	public void componentMoved(ComponentEvent e) {
	}
	public void componentResized(ComponentEvent e) {
	}
	
	//reuseComment
	public void commitLockForEdit() {

		borderColor.setEnabled(false);
		indexName.setEditable(false);
		initialValue.setEditable(false);
		indexOrder.setEditable(false);
		name.setEditable(false);
		url.setEditable(false);
		generaldescription.setEditable(false);
		description.setEditable(false);
		physical.setEnabled(false);
		informational.setEnabled(false);
		environmental.setEnabled(false);
		system.setEnabled(false);
		scope.setEditable(false);
		scope.setEnabled(false);
		isKey.setEnabled(false);
		isPersistent.setEnabled(false);
		objTypePanel.setEnabled(false);
		statesPanel.setEnabled(false);
		//

		Component[] array = statesPanel.getComponents();
		int i = 0;
		while (i < array.length) {
			array[i].setEnabled(false);
			i++;
		}
	}
	//reuseComment


	class BgColorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Color newColor =
				JColorChooser.showDialog(
					ObjectPropertiesDialog.this,
					"Choose Background Color",
					bgColor.getBackground());
			if (newColor != null) {
				(ObjectPropertiesDialog.this).bgColor.setBackground(newColor);
				(ObjectPropertiesDialog.this).sampleObject.setBackgroundColor(
					newColor);
				(ObjectPropertiesDialog.this).sampleObject.repaint();
			}
		}
	}

	class TextColorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Color newColor =
				JColorChooser.showDialog(
					ObjectPropertiesDialog.this,
					"Choose Text Color",
					textColor.getBackground());
			if (newColor != null) {
				(ObjectPropertiesDialog.this).textColor.setBackground(newColor);
				(ObjectPropertiesDialog.this).sampleObject.setTextColor(
					newColor);
				(ObjectPropertiesDialog.this).sampleObject.repaint();
			}

		}
	}

	class BorderColorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Color newColor =
				JColorChooser.showDialog(
					ObjectPropertiesDialog.this,
					"Choose Border Color",
					borderColor.getBackground());
			if (newColor != null) {
				(ObjectPropertiesDialog.this).borderColor.setBackground(
					newColor);
				(ObjectPropertiesDialog.this).sampleObject.setBorderColor(
					newColor);
				(ObjectPropertiesDialog.this).sampleObject.repaint();
			}
		}
	}

	class OkListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ((ObjectPropertiesDialog.this)._updateObjectData()) {
				okPressed = true;
				(ObjectPropertiesDialog.this).dispose();
			}
			return;
		}
	}

	class ApplyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			okPressed = true;
			(ObjectPropertiesDialog.this)._updateObjectData();
		}
	}

	class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			okPressed = false;
			(ObjectPropertiesDialog.this).dispose();
		}
	}

	Action positionAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if ((showTabs & BaseGraphicComponent.SHOW_MISC) == 0) {
				return;
			}
			String position = (ObjectPropertiesDialog.this).dp.getSelection();
			(ObjectPropertiesDialog.this).sampleObject.setTextPosition(
				position);
			(ObjectPropertiesDialog.this).sampleObject.setDashed(
				(ObjectPropertiesDialog.this).environmental.isSelected());
			(ObjectPropertiesDialog.this).sampleObject.setShadow(
				(ObjectPropertiesDialog.this).physical.isSelected());
			(ObjectPropertiesDialog.this).sampleObject.repaint();
		}
	};

	class resourceListner implements ActionListener{
		public void actionPerformed(ActionEvent e){
			LibraryLocationDialog addLocation =
				new LibraryLocationDialog(Opcat2.getFrame(), "", MetaLibrary.TYPE_FILE);
			addLocation.setTitle("URL"); 
			addLocation.setFileChooserTitle("Choose File");
			addLocation.setResourceLabel("URL Or File Location :"); 			
			HashMap newRef = addLocation.showDialog();
			if (newRef != null) {
				String path = (String)newRef.get("path");
				int type = ((Integer)newRef.get("type")).intValue();
				if (type == MetaLibrary.TYPE_FILE ) {
					url.setText("file:///".concat(path));
				}
				else {
					url.setText(path);
				}
			}
			return;
		}
	}	
}

