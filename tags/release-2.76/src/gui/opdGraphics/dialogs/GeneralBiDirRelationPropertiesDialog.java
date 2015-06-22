package gui.opdGraphics.dialogs;


import exportedAPI.OpdKey;
import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.MainStructure;

import java.awt.BorderLayout;
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;



/**
 *  <p>Constructs and shows properties dialog box for General Bi-directional Relation
 *  represented by class <a href = "OpdBiDirectionalRelation.html"><code>OpdBiDirectionalRelation</code></a>.
 *  Shown when <code>callPropertiesDialog()</code> is called.
 */

public class GeneralBiDirRelationPropertiesDialog extends JDialog implements ComponentListener
{
//	private JTextArea description;
	private JTextField sourceThing, destinationThing;
	private JComboBox sourceCardinality, destinationCardinality;
	private JTextField customSourceCardinality,customDestinationCardinality;
	private JTextField forwardRelationMeaning;
	private JTextField backwardRelationMeaning;

	private OpmBiDirectionalRelation biDirGeneralRelation;
//	private OpdBiDirectionalRelation opdBiDirGeneral;

	private JPanel p1, p2; //tmp panels
	private JRadioButton environmental, system;
	private ButtonGroup esBg;

	private JButton okButton, cancelButton, applyButton;
	private JButton bgColor, textColor, borderColor;
	private String sourceName, destinationName;

	private MainStructure myStructure;
	private GeneralRelationEntry myEntry;
	private GeneralRelationInstance myInstance;

        //reuseCommnet

        // this file has been changed by the reused project team
        // the changes aim to support locking of items
        // the change disbles all the GUI changing for elements which are locked
        private JCheckBox lockStatus;
        private boolean isLocked;
        //endReuseCommnet


	//===============================================================

	/**
	 *  Constructor:
	 *  @param <code>parent</code> -- parent frame, Opcat2 application window
	 *  @param <code>biDirGeneral</code> -- the <code>OpdBiDirectionalRelation</code> to show properies for
	 *  @param <code>sName</code> -- The name of the source OPD component, OPD Object
	 *  @param <code>dName</code> -- The name of the destination OPD component, OPD Object
	 */

	public GeneralBiDirRelationPropertiesDialog(OpmBiDirectionalRelation relation,OpdKey key, OpdProject myProject)
		{
		super(Opcat2.getFrame(), "Bi-Directional General Relation Properties", true);

        this.addComponentListener(this);
		this.biDirGeneralRelation = (OpmBiDirectionalRelation)relation;
                //reuseCommnet
               //  isLocked=myInstance.getEntry().getLocked();
                //endReuseCommnet
		myStructure = myProject.getComponentsStructure();
		myEntry = (GeneralRelationEntry)myStructure.getEntry(biDirGeneralRelation.getId());
		myInstance = (GeneralRelationInstance)myEntry.getInstance(key);

		long sId, dId;

		sId = biDirGeneralRelation.getSourceId();
		dId = biDirGeneralRelation.getDestinationId();

		sourceName = myStructure.getEntry(sId).getLogicalEntity().getName().replace('\n',' ');
		destinationName = myStructure.getEntry(dId).getLogicalEntity().getName().replace('\n',' ');

		//init all variables

		_initVariables();

		Container contPane = getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));

		JTabbedPane tabs = new JTabbedPane();

		tabs.add("General", _constructGeneralTab());
		tabs.add("Web", _constructWebTab());
		tabs.add("Misc.", _constructMiscTab());

		contPane.add(tabs);

		// -----------------------------------------------------------------
		// add buttons
		okButton = new JButton("OK");
		okButton.addActionListener(new OkListener());
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelListener());
		applyButton = new JButton("Apply");
//		applyButton.setEnabled(false);
		applyButton.addActionListener(new ApplyListener());
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridLayout layout = new GridLayout(1, 5);
		layout.setHgap(3);
		p1.setLayout(layout);
		p1.add(Box.createGlue());
		p1.add(Box.createGlue());
		p1.add(okButton);
		p1.add(cancelButton);
		p1.add(applyButton);
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
                 if (myInstance.getEntry().isLocked())
                              commitLockForEdit();
                 //endReuseComment


  }

	//===============================================================


    public void componentShown(ComponentEvent e)
    {
        forwardRelationMeaning.requestFocus();
    }
    public void componentHidden(ComponentEvent e){}
    public void componentMoved(ComponentEvent e){}
    public void componentResized(ComponentEvent e){}


	private void _initVariables()
	{
		try
		{
//			description = new JTextArea("Here will be description", 3, 30);
//			description.setEditable(false);

			forwardRelationMeaning = new JTextField(biDirGeneralRelation.getForwardRelationMeaning());
			backwardRelationMeaning = new JTextField(biDirGeneralRelation.getBackwardRelationMeaning());

			sourceThing = new JTextField(sourceName);
			sourceThing.setEditable(false);
			destinationThing = new JTextField(destinationName);
			destinationThing.setEditable(false);

			sourceCardinality = new JComboBox();
			sourceCardinality.addItem("1");
			sourceCardinality.addItem("many");
			sourceCardinality.addItem("custom");
			customSourceCardinality = new JTextField();
			customSourceCardinality.setEditable(false);

			if (biDirGeneralRelation.getSourceCardinality().compareTo("1") == 0)
				{
					sourceCardinality.setSelectedItem("1");
				}
			else
						{
							if (biDirGeneralRelation.getSourceCardinality().compareTo("m") == 0)
					{
					sourceCardinality.setSelectedItem("many");
				}
								else
				{
				 sourceCardinality.setSelectedItem("custom");
				 customSourceCardinality.setEditable(true);
				 customSourceCardinality.setText(biDirGeneralRelation.getSourceCardinality());
				}
						}

			destinationCardinality = new JComboBox();
			destinationCardinality.addItem("1");
			destinationCardinality.addItem("many");
			destinationCardinality.addItem("custom");
			customDestinationCardinality = new JTextField();
			customDestinationCardinality.setEditable(false);

			if ( biDirGeneralRelation.getDestinationCardinality().compareTo("1") == 0)
				{
					destinationCardinality.setSelectedItem("1");
				}
			else
						{
						  if (biDirGeneralRelation.getDestinationCardinality().compareTo("m") == 0)
							{
							  destinationCardinality.setSelectedItem("many");
							}
						  else
							{
					destinationCardinality.setSelectedItem("custom");
					customDestinationCardinality.setEditable(true);
					customDestinationCardinality.setText(biDirGeneralRelation.getDestinationCardinality());
				}
						}


			sourceCardinality.addActionListener(new SourceCardinalityListener());
			destinationCardinality.addActionListener(new DestinationCardinalityListener());
		}catch(Exception e)
		{
			System.out.println("Problem2!!");
		}

		environmental = new JRadioButton("Environmental");
		system = new JRadioButton("Systemic");
		esBg = new ButtonGroup();
		esBg.add(environmental);
		esBg.add(system);

		if(myEntry.getLogicalEntity().isEnviromental()){
			environmental.setSelected(true);
		}
		else{
			system.setSelected(true);
		}


		bgColor = new JButton("    ");
		bgColor.setBackground(myInstance.getBackgroundColor());
		textColor = new JButton("    ");
		textColor.setBackground(myInstance.getTextColor());
		borderColor = new JButton("    ");
		borderColor.setBackground(myInstance.getBorderColor());




  }

	//===============================================================

	private JPanel _constructGeneralTab()
	{
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		//Relation description
//		p1 = new JPanel();
//		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Description"), BorderFactory.createEmptyBorder(5,5,5,5)));
//		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
//		description.setBackground(p1.getBackground());
//		p1.add(description);
//		tab.add(p1);

		// forward relation meaning
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "  Forward Tag  "));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(forwardRelationMeaning);
		tab.add(p1);

		tab.add(Box.createVerticalStrut(10));

		// backward relation meaning
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "  Backward Tag  "));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(backwardRelationMeaning);
		tab.add(p1);

		tab.add(Box.createVerticalStrut(10));


		//source and destination pref
		//source
		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 3, 3, 0));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "  Source  "));
		JLabel source1 = new JLabel("           Name");
		JLabel source2 = new JLabel("Participation Constraint");
		JLabel source3 = new JLabel("           Custom");
		p2.add(source1);
		p2.add(source2);
		p2.add(source3);
		sourceThing.setBorder(BorderFactory.createEtchedBorder());
		customSourceCardinality.setBorder(BorderFactory.createEtchedBorder());
		p2.add(sourceThing);
		p2.add(sourceCardinality);
		p2.add(customSourceCardinality);
		tab.add(p2);

		tab.add(Box.createVerticalStrut(5));

		//destination
		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 3, 3, 0));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "  Destination  "));
		JLabel destination1 = new JLabel("           Name");
		JLabel destination2 = new JLabel("Participation Constraint");
		JLabel destination3 = new JLabel("           Custom");
		p2.add(destination1);
		p2.add(destination2);
		p2.add(destination3);
		destinationThing.setBorder(BorderFactory.createEtchedBorder());
		customDestinationCardinality.setBorder(BorderFactory.createEtchedBorder());
		p2.add(destinationThing);
		p2.add(destinationCardinality);
		p2.add(customDestinationCardinality);

		tab.add(p2);
		return tab;
	}

	private JPanel _constructWebTab()
	{
		JPanel wp = new JPanel();
		wp.setLayout(new BorderLayout());
		JPanel p2 = new JPanel();

		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Origin "));
		p2.add(environmental);

		p2.add(system);
		wp.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		wp.add(p2);
		wp.add(Box.createVerticalStrut(200), BorderLayout.SOUTH);
		wp.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		wp.add(Box.createHorizontalStrut(10), BorderLayout.WEST);

		return wp;
	}


	//===============================================================

	private JPanel _constructMiscTab()
	{
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.X_AXIS));
		tab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), BorderFactory.createEtchedBorder()));
		JLabel bgColorLabel = new JLabel("Background Color:");
		JLabel textColorLabel = new JLabel("Text Color:");
		JLabel borderColorLabel = new JLabel("Line Color:");
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


	//===============================================================

	private boolean _updateRelationData()
	{

		if((String.valueOf(sourceCardinality.getSelectedItem())).compareTo("custom") == 0)
		{
			if(GraphicsUtils.checkCustomCardinalityLegality(customSourceCardinality.getText()))
			{
				biDirGeneralRelation.setSourceCardinality(customSourceCardinality.getText());
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Custom cardinality should be of the form min..max or v1, v2, v3 or combination of them.", "Opcat 2 - Error" ,JOptionPane.ERROR_MESSAGE);
				customSourceCardinality.setSelectionStart(0);
				customSourceCardinality.setSelectionEnd(customSourceCardinality.getText().length());
				customSourceCardinality.requestFocus();
				return false;
			}
		}
		else
		{
			if ((String.valueOf(sourceCardinality.getSelectedItem())).compareTo("many") == 0)
			{
				biDirGeneralRelation.setSourceCardinality("m");
			}
			else
			{
				biDirGeneralRelation.setSourceCardinality("1");
			}
		}


		if((String.valueOf(destinationCardinality.getSelectedItem())).compareTo("custom") == 0)
		{
			if(GraphicsUtils.checkCustomCardinalityLegality(customDestinationCardinality.getText()))
			{
				biDirGeneralRelation.setDestinationCardinality(customDestinationCardinality.getText());
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Custom cardinalit,y should be of the form min..max or v1, v2, v3 or combination of them.", "Opcat 2 - Error" ,JOptionPane.ERROR_MESSAGE);
				customDestinationCardinality.setSelectionStart(0);
				customDestinationCardinality.setSelectionEnd(customDestinationCardinality.getText().length());
				customDestinationCardinality.requestFocus();
				return false;
			}

		}

		else
		{
			if((String.valueOf(destinationCardinality.getSelectedItem())).compareTo("many") == 0)
			{
				biDirGeneralRelation.setDestinationCardinality("m");
			}
			else
			{
				biDirGeneralRelation.setDestinationCardinality("1");
			}
		}

		biDirGeneralRelation.setForwardRelationMeaning(forwardRelationMeaning.getText());
		biDirGeneralRelation.setBackwardRelationMeaning(backwardRelationMeaning.getText());
		biDirGeneralRelation.setEnviromental(environmental.isSelected());

		myEntry.updateInstances();

		myInstance.setBackgroundColor(bgColor.getBackground());
		myInstance.setTextColor(textColor.getBackground());
		myInstance.setBorderColor(borderColor.getBackground());
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

		return true;
	}

	//===============================================================

	class BgColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(GeneralBiDirRelationPropertiesDialog.this,
					"Choose Relation Color",
					bgColor.getBackground());
			if(newColor != null)
					(GeneralBiDirRelationPropertiesDialog.this).bgColor.setBackground(newColor);
		}
	}

	//===============================================================

	class TextColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					GeneralBiDirRelationPropertiesDialog.this,
					"Choose Text Color",
					textColor.getBackground());
			if(newColor != null)
					(GeneralBiDirRelationPropertiesDialog.this).textColor.setBackground(newColor);
		}
	}

	//===============================================================

	class BorderColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					GeneralBiDirRelationPropertiesDialog.this,
					"Choose Border Color",
					borderColor.getBackground());
			if(newColor != null)
					(GeneralBiDirRelationPropertiesDialog.this).borderColor.setBackground(newColor);
		}
	}

	//===============================================================

	class SourceCardinalityListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (((String.valueOf(sourceCardinality.getSelectedItem())).compareTo("custom")) == 0)
			{
				customSourceCardinality.setEditable(true);
				customSourceCardinality.requestFocus();
			}
			else
			{
				customSourceCardinality.setEditable(false);
				customSourceCardinality.setText("");
			}
			return;
		}
	}


	//===============================================================

	class DestinationCardinalityListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (((String.valueOf(destinationCardinality.getSelectedItem())).compareTo("custom")) == 0)
			{
				customDestinationCardinality.setEditable(true);
				customDestinationCardinality.requestFocus();
			}
			else
			{
				customDestinationCardinality.setEditable(false);
				customDestinationCardinality.setText("");
			}
			return;
		}
	}

	//===============================================================

	class OkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((GeneralBiDirRelationPropertiesDialog.this)._updateRelationData())
				(GeneralBiDirRelationPropertiesDialog.this).dispose();
			return;
		}
	}

	//===============================================================

	class ApplyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			(GeneralBiDirRelationPropertiesDialog.this)._updateRelationData();
		}
	}

	//===============================================================

	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			(GeneralBiDirRelationPropertiesDialog.this).dispose();
		}
	}

//reuseComment

public void commitLockForEdit()
 {
   sourceThing.setEditable(false);
   destinationThing.setEditable(false);
   sourceCardinality.setEditable(false);
   destinationCardinality.setEditable(false);
   customSourceCardinality.setEditable(false);
   customDestinationCardinality.setEditable(false);
   forwardRelationMeaning.setEditable(false);
   backwardRelationMeaning.setEditable(false);
   environmental.setEnabled(false);
   system.setEnabled(false);
   borderColor.setEnabled(false);


 }

//endReuseComment

}
