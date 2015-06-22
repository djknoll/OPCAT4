package gui.opdGraphics.dialogs;

import gui.Opcat2;
import gui.metaLibraries.dialogs.LibraryLocationDialog;
import gui.metaLibraries.dialogs.RolesDialog;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.ProcessEntry;
import gui.undo.UndoableChangeProcess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;

/**
 * Importing ontologies package.
 * @author Eran Toch
 */


/**
 *  This class is a properties dialog box for <a href = "OpdProcess.html"><code>OpdProcess</code></a>.
 *  <p>Constructs and shows properties dialog box for one of the OPD Processes
 *
 *  <br>Shown when <code>callPropertiesDialog()</code> is called.
 */
public class ProcessPropertiesDialog extends JDialog implements ComponentListener
{

	private JTextArea name, url;
	private JTextArea description, body;
	private TimeSpecifier maxTimeActivation, minTimeActivation;
	private JRadioButton physical, informational, environmental, system;
	private JComboBox scope;
	private JButton okButton, cancelButton, applyButton, bgColor, textColor, borderColor, resourceButton ;
	private ButtonGroup piBg, esBg;
	private JScrollPane bodyScrollPane, descriptionScrollPane, nameScrollPane, urlScrollPane;
	private JPanel p1, p2; // tmp panels
	private OpmProcess process;
	private OpdProcess opdProcess;
	private OpdProject myProject;
	private ProcessEntry myEntry;
	private DirectionPanel dp;
	private OpdProcess sampleProcess;
	private String defaultName = "";
	private String defaultUrl = "";
	private int showTabs;
	private JTabbedPane tabs;
	private boolean okPressed = false;
	private boolean isCreation;
	private JFrame parentFrame ; 

	/**
	 * A JPanel containing roles selection.
	 * @author Eran Toch
	 */
	private RolesDialog rolesTab;

	/**
	 *  Constructor:
	 *  @param <code>parent</code> -- parent frame, Opcat2 application window or <code>null</code>
	 *  @param <code>OpdProcess pProcess</code> -- the OPD object to show properties dialog for.
	 *  @param <code>ThingEntry pEntry</code> -- the <code>projectStructure.ThingEntry</code> class, entry of this OPD Process in project dynamic structure.
	 *  @see <a href = "ThingEntry.html"><code>ThingEntry</code></a> class documentation for more information.
	 */
	public ProcessPropertiesDialog(OpdProcess pProcess, ProcessEntry pEntry, OpdProject project, boolean isCreation)
	{ 
		super(Opcat2.getFrame(), "OPD Process Properties", true);

		parentFrame = Opcat2.getFrame() ;
		this.addComponentListener(this);
		myEntry = pEntry;
		opdProcess = pProcess;
		process = (OpmProcess)(myEntry.getLogicalEntity());
		myProject = project;

		this.isCreation = isCreation;
		int showButtons;
		showTabs = BaseGraphicComponent.SHOW_ALL_TABS;

		if (!isCreation)
		{
			showButtons = BaseGraphicComponent.SHOW_ALL_BUTTONS;
		}
		else
		{
			showButtons = BaseGraphicComponent.SHOW_OK | BaseGraphicComponent.SHOW_CANCEL;
		}


		// init all variables
		_initVariables();

		Container contPane = getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));

		tabs = new JTabbedPane();

		if((showTabs & BaseGraphicComponent.SHOW_1) != 0)
		{
			tabs.add("General", _constructGeneralTab());
		}

		if((showTabs & BaseGraphicComponent.SHOW_2) != 0)
		{
			tabs.add("Details", _constructPreferences1Tab());
		}

		if((showTabs & BaseGraphicComponent.SHOW_3) != 0)
		{
			tabs.add("Activation Time", _constructTimingTab());
		}
		//		Adding roles tab to the general tabs
		if((showTabs & BaseGraphicComponent.SHOW_4) != 0)	
		{
			tabs.add("Roles", _constructRolesTab());
		}
		
		if((showTabs & BaseGraphicComponent.SHOW_MISC) != 0)
		{
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
		resourceButton.addActionListener(new resourceListner()); 

		if((showButtons & BaseGraphicComponent.SHOW_OK) != 0)
		{
			okButton = new JButton("OK");
			okButton.addActionListener(new OkListener());
			getRootPane().setDefaultButton(okButton);
			p1.add(okButton);
		}

		if((showButtons & BaseGraphicComponent.SHOW_CANCEL) != 0)
		{
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelListener());
			p1.add(cancelButton);
		}

		if((showButtons & BaseGraphicComponent.SHOW_APPLY) != 0)
		{
			applyButton = new JButton("Apply");
			applyButton.addActionListener(new ApplyListener());
			p1.add(applyButton);
		}

		contPane.add(p1);
		// -----------------------------------------------------------------
		// Escape & Enter listener
		KeyListener escapeAndEnterListener = new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					(ProcessPropertiesDialog.this).dispose();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !body.hasFocus() && !description.hasFocus())
				{
					if((ProcessPropertiesDialog.this)._updateProcessData())
						(ProcessPropertiesDialog.this).dispose();
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

		setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));
		this.setResizable(false);

		//reuseComment
        if (pEntry.isLocked())
          commitLockForEdit();
       //endReuseComment

	}

	private void _initVariables(){
		try{

			//genaral
			if((showTabs & BaseGraphicComponent.SHOW_1) != 0)
			{
				if(process.getName().trim().equals(""))
				{
					name = new JTextArea(defaultName, 3, 32);
				}
				else
				{
					name = new JTextArea(process.getName(), 3, 32);
				}
				name.setLineWrap(true);
				name.setWrapStyleWord(true);
				nameScrollPane = new JScrollPane(name, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				
				/*
				 * TODO: this is ugly. the defurl and defname should be set in the 
				 * thing object load and  create. tis should be done to all those kind
				 * of properties.
				 */
				
				//set the url to be presented 
				if(process.getUrl().trim().equals(""))
				{
					url = new JTextArea(defaultUrl, 4, 24);
				}
				else
				{
					url = new JTextArea(process.getUrl(), 4, 24);
				}
				url.setLineWrap(true);
				url.setWrapStyleWord(true);
				urlScrollPane = new JScrollPane(url, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				

				scope = new JComboBox();
				scope.addItem("Public");
				scope.addItem("Protected");
				scope.addItem("Private");
				scope.setSelectedIndex(Character.digit((process.getScope()).charAt(0), 3));

				physical = new JRadioButton("Physical");
				physical.addActionListener(positionAction);
				informational = new JRadioButton("Informational");
				informational.addActionListener(positionAction);
				piBg = new ButtonGroup();
				piBg.add(physical);
				piBg.add(informational);
				if(process.isPhysical()){
					physical.setSelected(true);
				}
				else{
					informational.setSelected(true);
				}

				environmental = new JRadioButton("Environmental");
				environmental.addActionListener(positionAction);
				system = new JRadioButton("Systemic");
				system.addActionListener(positionAction);
				esBg = new ButtonGroup();
				esBg.add(environmental);
				esBg.add(system);
				if(process.isEnviromental()){
					environmental.setSelected(true);
				}
				else{
					system.setSelected(true);
				}
			}
			//end general

			//desc & body
			if((showTabs & BaseGraphicComponent.SHOW_2) != 0)
			{
				body = new JTextArea(process.getProcessBody(), 10, 30);
				bodyScrollPane = new JScrollPane(body, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				body.setLineWrap(true);
				body.setWrapStyleWord(true);

				description = new JTextArea(process.getDescription(), 4, 30);
				descriptionScrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				description.setLineWrap(true);
				description.setWrapStyleWord(true);

				
				url = new JTextArea(process.getUrl(), 4, 24);
				urlScrollPane = new JScrollPane(url, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				url.setLineWrap(true);
				url.setWrapStyleWord(true);				
			}
			//end desc & body

			//activation time
			if((showTabs & BaseGraphicComponent.SHOW_3) != 0)
			{
				maxTimeActivation = new TimeSpecifier(TimeSpecifier.HORIZONTAL, TimeSpecifier.TOP, 3, 3);
				maxTimeActivation.setTime(process.getMaxTimeActivation());
				minTimeActivation = new TimeSpecifier(TimeSpecifier.HORIZONTAL, TimeSpecifier.TOP, 3, 3);
				minTimeActivation.setTime(process.getMinTimeActivation());
			}
			//end activation time


		}catch(Exception e){
			System.out.println("Error during process processing");
		}

		//misc
		if((showTabs & BaseGraphicComponent.SHOW_MISC) != 0)
		{
			bgColor = new JButton("          ");
			bgColor.setBackground(opdProcess.getBackgroundColor());
			textColor = new JButton("          ");
			textColor.setBackground(opdProcess.getTextColor());
			borderColor = new JButton("          ");
			borderColor.setBackground(opdProcess.getBorderColor());
		}
		//end misc
	}

	private JPanel _constructGeneralTab(){
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Process Name
		p1 = new JPanel();
//		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Process Name"));
//		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
//		JLabel l1 = new JLabel("Process Name:");
//		p1.add(l1);
		//p1.add(Box.createHorizontalStrut(10));
		p1.add(nameScrollPane);
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(p1);
		tab.add(p2);

		
		// Radio buttons panel group
		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

		// Essence group
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Essence "));
		p2.add(physical);
		p2.add(Box.createVerticalStrut(10));
		p2.add(informational);
		p1.add(p2);

		p1.add(Box.createHorizontalStrut(10));

		// Origin group
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Origin "));
		p2.add(environmental);
		p2.add(Box.createVerticalStrut(10));
		p2.add(system);
		p1.add(p2);

		tab.add(Box.createVerticalStrut(8));
		tab.add(p1);

		// scope ComboBox
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		JLabel l2 = new JLabel("Scope:   ");
		p1.add(l2);
		p1.add(Box.createHorizontalStrut(10));
		p1.add(scope);
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(p1);

		tab.add(Box.createVerticalStrut(10));
		tab.add(p2);


		tab.add(Box.createVerticalStrut(40));

		return tab;
	}

	private JPanel _constructPreferences1Tab(){
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Process Description
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Description "));
		p1.add(descriptionScrollPane);
		tab.add(p1);
		
		// URL
		p1 = new JPanel();
		resourceButton = new JButton("URL"); 
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " URL "));
		p1.add(urlScrollPane);
		p1.add( resourceButton);
		tab.add(p1);
		

		// Process Body
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Body "));
		p1.add(bodyScrollPane);
		tab.add(p1);

		//tab.add(Box.createVerticalStrut(10));

		return tab;

	}

	private JPanel _constructTimingTab()
	{
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		minTimeActivation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Minimum Activation Time "));
		maxTimeActivation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Maximum Activation Time "));
		tab.add(minTimeActivation);
		tab.add(Box.createVerticalStrut(5));
		tab.add(maxTimeActivation);
		tab.add(Box.createVerticalStrut(50));
		return tab;
	}

	/**
	 * Construct a roles tab, using the RolesDialog class.
	 * @return The roles JPanel class.
	 * @author Eran Toch
	 */
	private JPanel _constructRolesTab() {
		rolesTab = new RolesDialog(process, myProject);
		return rolesTab;
	}
	
	private JPanel _constructMiscTab(){

		JPanel retTab = new JPanel();
		retTab.setLayout(new BoxLayout(retTab, BoxLayout.Y_AXIS));

		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.X_AXIS));
		tab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Colors "), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
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
		textPositionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Text Position "), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		textPositionPanel.setLayout(new BorderLayout());
		dp = new DirectionPanel(true, "try str", positionAction);
		dp.setSelection(opdProcess.getTextPosition());
		textPositionPanel.add(dp, BorderLayout.CENTER);

		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Preview "), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
		ProcessEntry sampleEntry = new ProcessEntry(new OpmProcess(0, "Sample"), myProject);
		previewPanel.setLayout(new BorderLayout());
		sampleProcess = new OpdProcess(sampleEntry,myProject,0,0);
		sampleProcess.setTextPosition(opdProcess.getTextPosition());
		sampleProcess.setBackgroundColor(opdProcess.getBackgroundColor());
		sampleProcess.setBorderColor(opdProcess.getBorderColor());
		sampleProcess.setTextColor(opdProcess.getTextColor());
		sampleProcess.setDashed(opdProcess.isDashed());
		sampleProcess.setShadow(opdProcess.isShadow());

		previewPanel.add(sampleProcess, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.add(textPositionPanel);
		bottomPanel.add(previewPanel);

		retTab.add(bottomPanel);
		return retTab;

	}

	private boolean _updateProcessData()
	{
		OpmProcess oldProcess = new OpmProcess(-1, "");
		oldProcess.copyPropsFrom(process);


		//general
		if((showTabs & BaseGraphicComponent.SHOW_1) != 0)
		{
			if(name.getText().equals(defaultName) || name.getText().trim().equals(""))
			{
				tabs.setSelectedIndex(0);
				name.requestFocus();
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "You should provide a name for the Process", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (GraphicsUtils.getMsec4TimeString(minTimeActivation.getTime()) > GraphicsUtils.getMsec4TimeString(maxTimeActivation.getTime()))
			{
				tabs.setSelectedIndex(2);
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "Min activation time can't be larger than max activation time ", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (Opcat2.getShowProcessNameMessage()) {
//				if (!name.getText().equals(process.getName()) &&
//					!name.getText().trim().toLowerCase().endsWith("ing") && !name.getText().trim().toLowerCase().endsWith("process"))
//				{
//					ProcessNameDialog doIng = new ProcessNameDialog(myProject, name.getText());
//					int doWhat = doIng.showDialog();
//					if (doWhat == doIng.CANCEL) {
//						tabs.setSelectedIndex(0);
//						name.requestFocus();
//						return false;
//					}
//					else if (doWhat == doIng.OK_AND_DONT_ASK_AGAIN) {
//						Opcat2.setShowProcessNameMessage(false);
//					}
//					else if (doWhat == doIng.OK) {
//						Opcat2.setShowProcessNameMessage(true);
//					}
//				}
			}

			process.setName(GraphicsUtils.capitalizeFirstLetters(name.getText().trim()));
			process.setUrl(url.getText().trim());
			process.setEnviromental(environmental.isSelected());
			process.setPhysical(physical.isSelected());
			process.setScope(String.valueOf(scope.getSelectedIndex()));
		}
		//end general

		//description & body
		if((showTabs & BaseGraphicComponent.SHOW_2) != 0)
		{
			process.setDescription(description.getText());
			process.setProcessBody(body.getText());
		}
		//end description & body

		//activation time
		if((showTabs & BaseGraphicComponent.SHOW_3) != 0)
		{
			process.setMinTimeActivation(minTimeActivation.getTime());
			process.setMaxTimeActivation(maxTimeActivation.getTime());
		}
		//end activation time

		//misc
		if((showTabs & BaseGraphicComponent.SHOW_MISC) != 0)
		{
			opdProcess.setBackgroundColor(bgColor.getBackground());
			opdProcess.setTextColor(textColor.getBackground());
			opdProcess.setBorderColor(borderColor.getBackground());
			opdProcess.setTextPosition(dp.getSelection());
		}
		//end misc

		if((showTabs & BaseGraphicComponent.SHOW_4) != 0)
		{
			process.setRole(rolesTab.getRoleText());
			int noi = 0;
			try{
				noi = Integer.parseInt(rolesTab.getNoOfInstances());
			}catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "Number Of Instances must be a positive integer", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if(noi < 1)
			{
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "Number Of Instances must be a positive integer", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			process.setNumberOfInstances(noi);
		}

		/********* Handling Roles **************/
		//Handling roles selection, when the OK button is pressed.
		//by Eran Toch
		process.getRolesManager().setRoles(rolesTab.getRoles());
		//end roles
		
		myEntry.updateInstances();

		if (!isCreation && !process.hasSameProps(oldProcess))
		{
			Opcat2.getUndoManager().undoableEditHappened( new UndoableEditEvent(myProject,
					new UndoableChangeProcess(myProject, myEntry, oldProcess,process)));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
		
		return true;
	}

	public boolean showDialog()
	{
		this.setVisible(true);
		return okPressed;
	}

	public void componentShown(ComponentEvent e)
	{
		name.requestFocus();
	}
	public void componentHidden(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	public void componentResized(ComponentEvent e){}

    //reuse
	public void commitLockForEdit() {
		body.setEditable(false);
		maxTimeActivation.setEnabled(false);
		maxTimeActivation.setFocusable(false);
		minTimeActivation.setEnabled(false);
		scope.setEditable(false);
		bodyScrollPane.setEnabled(false);
		descriptionScrollPane.setEnabled(false);
		nameScrollPane.setEnabled(false);
		name.setEditable(false);
		description.setEditable(false);
		physical.setEnabled(false);
		informational.setEnabled(false);
		environmental.setEnabled(false);
		system.setEnabled(false);
		scope.setEditable(false);
		scope.setEnabled(false);
		borderColor.setEnabled(false);

	}


	class BgColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					ProcessPropertiesDialog.this,
					"Choose Background Color",
					bgColor.getBackground());
			if(newColor != null)
			{
				(ProcessPropertiesDialog.this).bgColor.setBackground(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.setBackgroundColor(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.repaint();
			}

		}
	}

	class TextColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					ProcessPropertiesDialog.this,
					"Choose Text Color",
					textColor.getBackground());
			if(newColor != null)
			{
				(ProcessPropertiesDialog.this).textColor.setBackground(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.setTextColor(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.repaint();
			}

		}
	}

	class BorderColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					ProcessPropertiesDialog.this,
					"Choose Border Color",
					borderColor.getBackground());
			if(newColor != null)
			{
				(ProcessPropertiesDialog.this).borderColor.setBackground(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.setBorderColor(newColor);
				(ProcessPropertiesDialog.this).sampleProcess.repaint();
			}
		}
	}


	class resourceListner implements ActionListener{
		public void actionPerformed(ActionEvent e){
			LibraryLocationDialog addLocation =
				new LibraryLocationDialog(Opcat2.getFrame(), "", MetaLibrary.TYPE_FILE);
			addLocation.setTitle( "URL"); 
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
	
	class OkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((ProcessPropertiesDialog.this)._updateProcessData())
			{
				okPressed = true;
				(ProcessPropertiesDialog.this).dispose();
			}
			return;
		}
	}

	class ApplyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			okPressed = true;
			(ProcessPropertiesDialog.this)._updateProcessData();
		}
	}

	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			okPressed = false;
			(ProcessPropertiesDialog.this).dispose();
		}
	}

	Action positionAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
			if ((showTabs & BaseGraphicComponent.SHOW_MISC) == 0)
			{
				return;
			}
			String position = (ProcessPropertiesDialog.this).dp.getSelection();
			(ProcessPropertiesDialog.this).sampleProcess.setTextPosition(position);
			(ProcessPropertiesDialog.this).sampleProcess.setDashed((ProcessPropertiesDialog.this).environmental.isSelected());
			(ProcessPropertiesDialog.this).sampleProcess.setShadow((ProcessPropertiesDialog.this).physical.isSelected());

			(ProcessPropertiesDialog.this).sampleProcess.repaint();
		}
	};


}