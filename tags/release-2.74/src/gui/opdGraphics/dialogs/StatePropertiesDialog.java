package gui.opdGraphics.dialogs;


import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmState;
import gui.projectStructure.StateInstance;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 *  This class is a properties dialog box for <a href = "OpdState.html"><code>OpdState</code></a>.
 *  <p>Constructs and shows properties dialog box for one of the OPD States
 *
 *  <br>Shown when <code>callPropertiesDialog()</code> is called.
 */

public class StatePropertiesDialog extends JDialog implements ComponentListener
{
	private JTextArea name ;
	private JTextArea description;
	private JScrollPane nameScrollPane;
	private JScrollPane descriptionScrollPane;
	private JCheckBox isInitial, isFinal, isDefault;
	private TimeSpecifier maxTime, minTime;
	private JButton okButton, cancelButton, applyButton, bgColor, textColor, borderColor;
	private JPanel p1, p2; // tmp panels
	private OpmState opmState;
	private OpdState opdState;
	private StatePanel parentDialogBox;
	private OpdProject myProject;
	private boolean showTabs;
	private int showButtons;
	private boolean okPressed = false;
	private StateInstance myInstance;
	private JTabbedPane tabs;
        // reuseComment
        private JCheckBox lockStatus;
        private boolean isLocked;
        //endReuseComment


	/**
	 *  Constructor:
	 *  @param <code>parent</code> -- parent frame, Opcat2 application window or <code>null</code>
	 *  @param <code>pState OpdState</code> -- the OPD State to show properties dialog for.
	 */
	public StatePropertiesDialog(StateInstance instance, OpdProject project, boolean showTabs, int showButtons)
	{
		super(Opcat2.getFrame(), "State Properties", true);

                //reuseComment
                isLocked=instance.getEntry().isLocked();
                //endResueComment

                this.addComponentListener(this);

		myInstance = instance;
		this.opdState = (OpdState)myInstance.getConnectionEdge();

		this.opmState = (OpmState)opdState.getEntity();
		myProject = project;
		this.showTabs = showTabs;
		this.showButtons = showButtons;

//		parentDialogBox = (StatePanel)parent;

		// init all variables
		_initVariables();

		Container contPane = getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));

		tabs = new JTabbedPane();

		tabs.add("General", _constructGeneralTab());
		tabs.add("Preferences", _constructPreferencesTab());
		tabs.add("Misc.", _constructMiscTab());

		contPane.add(tabs);

		// -----------------------------------------------------------------
		// add buttons
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridLayout layout = new GridLayout(1, 5);
		layout.setHgap(3);
		p1.setLayout(layout);
		p1.add(Box.createGlue());
		p1.add(Box.createGlue());

		if((showButtons & BaseGraphicComponent.SHOW_OK) != 0)
		{
			okButton = new JButton("OK");
			okButton.addActionListener(new OkListener());
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


		this.pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		int fX = Opcat2.getFrame().getX();
		int fY = Opcat2.getFrame().getY();
		int pWidth = Opcat2.getFrame().getWidth();
		int pHeight = Opcat2.getFrame().getHeight();

		setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));
		this.setResizable(false);
                //reuseComment
                //here we check if the state is locked
                if (instance.getEntry().isLocked())
                  commitLockForEdit();
                //endReuseComment

	}

	private void _initVariables(){
		try{
			//general
				name = new JTextArea(opmState.getName(), 3, 30);
				nameScrollPane = new JScrollPane(name, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				name.setLineWrap(true);
				name.setWrapStyleWord(true);

				description = new JTextArea(opmState.getDescription(), 5, 30);
				descriptionScrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				description.setLineWrap(true);
				description.setWrapStyleWord(true);

			//end general

			// 2 tab
				isInitial = new JCheckBox("Initial", opmState.isInitial());
				isFinal = new JCheckBox("Final", opmState.isFinal());
                                isDefault = new JCheckBox("Default", opmState.isDefault());

				maxTime = new TimeSpecifier(TimeSpecifier.HORIZONTAL, TimeSpecifier.TOP, 3, 3);
				maxTime.setTime(opmState.getMaxTime());
				minTime = new TimeSpecifier(TimeSpecifier.HORIZONTAL, TimeSpecifier.TOP, 3, 3);
				minTime.setTime(opmState.getMinTime());

			//end 2 tab

		}catch(NullPointerException e){
			System.out.println("No object passed");
		}

		//misc
			bgColor = new JButton("          ");
			bgColor.setBackground(opdState.getBackgroundColor());
			textColor = new JButton("          ");
			textColor.setBackground(opdState.getTextColor());
			borderColor = new JButton("          ");
			borderColor.setBackground(opdState.getBorderColor());
		//end misc

	}

	private JPanel _constructGeneralTab(){
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Object Name
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "State Name"));
//		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
//		JLabel l1 = new JLabel("State Name:");
//		p1.add(l1);
//		p1.add(Box.createHorizontalStrut(10));
		p1.add(nameScrollPane);
		tab.add(p1);
		tab.add(Box.createVerticalStrut(5));

		// State Description
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Description "));
		p1.add(descriptionScrollPane);
		//tab.add(Box.createVerticalStrut(10));
		tab.add(p1);
		tab.add(Box.createVerticalStrut(12));

                p1 = new JPanel();
                p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
//                p1.add(Box.createHorizontalStrut(12));
                p1.add(isInitial);
                p1.add(Box.createHorizontalStrut(40));
                p1.add(isFinal);
                p1.add(Box.createHorizontalStrut(40));
                p1.add(isDefault);

                tab.add(p1);

		return tab;
	}

	public JPanel _constructPreferencesTab(){
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		minTime.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Minimum Activation Time "));
		maxTime.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Maximum Activation Time "));
		tab.add(minTime);
		tab.add(Box.createVerticalStrut(5));
		tab.add(maxTime);
		tab.add(Box.createVerticalStrut(10));

		// initial/final checkboxes
//		tab.add(Box.createVerticalStrut(10));
		return tab;
	}

	private JPanel _constructMiscTab(){
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.X_AXIS));
		tab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), BorderFactory.createEtchedBorder()));
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
		p1.add(Box.createVerticalStrut(112));
		bgColor.addActionListener(new BgColorButtonListener());
		textColor.addActionListener(new TextColorButtonListener());
		borderColor.addActionListener(new BorderColorButtonListener());
		p2.add(bgColor);
		p2.add(Box.createVerticalStrut(8));
		p2.add(textColor);
		p2.add(Box.createVerticalStrut(8));
		p2.add(borderColor);
		p2.add(Box.createVerticalStrut(112));
		tab.add(p1);
		tab.add(p2);
		tab.add(Box.createHorizontalStrut(140));
		return tab;
	}

	private boolean _updateStateData(){
			if(name.getText().trim().equals(""))
			{
				tabs.setSelectedIndex(0);
				name.requestFocus();
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "You should provide a name for the State", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (GraphicsUtils.getMsec4TimeString(minTime.getTime()) > GraphicsUtils.getMsec4TimeString(maxTime.getTime()))
			{
				tabs.setSelectedIndex(1);
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "Min activation time can't be larger than max activation time ", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}


			opmState.setName(name.getText());
			opmState.setDescription(description.getText());
			opmState.setInitial(isInitial.isSelected());
			opmState.setFinal(isFinal.isSelected());
                        opmState.setDefault(isDefault.isSelected());
			opmState.setMinTime(minTime.getTime());
			opmState.setMaxTime(maxTime.getTime());

			opdState.setBackgroundColor(bgColor.getBackground());
			opdState.setTextColor(textColor.getBackground());
			opdState.setBorderColor(borderColor.getBackground());

		myInstance.getEntry().updateInstances();

		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
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


	class BgColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					StatePropertiesDialog.this,
					"Choose Background Color",
					bgColor.getBackground());
			if(newColor != null)
				(StatePropertiesDialog.this).bgColor.setBackground(newColor);
		}
	}

	class TextColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					StatePropertiesDialog.this,
					"Choose Text Color",
					textColor.getBackground());
			if(newColor != null)
				(StatePropertiesDialog.this).textColor.setBackground(newColor);
		}
	}

	class BorderColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					StatePropertiesDialog.this,
					"Choose Border Color",
					borderColor.getBackground());
			if(newColor != null)
				(StatePropertiesDialog.this).borderColor.setBackground(newColor);
		}
	}

	class OkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((StatePropertiesDialog.this)._updateStateData())
			{
				okPressed = true;
				(StatePropertiesDialog.this).dispose();
			}
			return;
		}
	}

	class ApplyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			okPressed = true;
			(StatePropertiesDialog.this)._updateStateData();
		}
	}

	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			okPressed = false;
			(StatePropertiesDialog.this).dispose();
		}
	}

      //reuseComment
      public void   commitLockForEdit()
      {
        lockStatus.setEnabled(false);
        name.setEditable(false); ;
        description.setEditable(false);
        nameScrollPane.setEnabled(false);
        descriptionScrollPane.setEnabled(false);
        isInitial.setEnabled(false);
        isFinal.setEnabled(false);
        isDefault.setEnabled(false);
        maxTime.setEnabled(false);
        minTime.setEnabled(false);
      }
//endReuseComment


//	public static void main(String[] args)
//	{
//		new StatePropertiesDialog(null, new OpdState(null, null, 23, 32));
//	}
}



