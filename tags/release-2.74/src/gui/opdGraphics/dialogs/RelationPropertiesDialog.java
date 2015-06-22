package gui.opdGraphics.dialogs;

import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.OpdAggregation;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdExhibition;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdInstantination;
import gui.opdGraphics.opdBaseComponents.OpdSpecialization;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *  This class is properties dialog box for relation.
 *  <p>It displays the properties for relation of one type and with one source thing. It's possible for <a href = "OpdFundamentalRelation.html><code>OpdFundamentalRelation</code></a>
 *  to have several destination things, in this case all destination things are displaied.</p>
 */
public class RelationPropertiesDialog extends JDialog
{
	private String dialogName;

//	private JTextArea description;
	private JTextField sourceThing, sourceCardinality;
	private DestinationsTable destinationsTable;

	private OpdFundamentalRelation opdRelation;
	private OpdConnectionEdge opdSourceThing;
	private FundamentalRelationInstance[] relationInstances;

	private JPanel p1, p2; //tmp panels
	private GraphicalRelationRepresentation myGraphicalRelation;

	private JButton okButton, cancelButton, applyButton;
	private JButton bgColor, textColor, borderColor;
	private String sourceName, tmpCardinality;
	private boolean isCardinalityEditable;


	//===============================================================

	/**
	 *  Constructor:
	 *  @param <code>graphicalRelation GraphicalRelationRepresentationEntry</code> The entry of this OpdRelation.
	 *  @see <a href = "../projectStructure/GraphicalRelationRepresentationEntry.html"><code>GraphicalRelationRepresentationEntry</code></a>.
	 */
	public RelationPropertiesDialog(GraphicalRelationRepresentation graphicalRelation, String dialogName, boolean isCardinalityEditable)
	{
		super(Opcat2.getFrame(), true);
				//super((JFrame)null, true);
		this.opdRelation = graphicalRelation.getRelation();
		this.opdSourceThing = graphicalRelation.getSource();
		myGraphicalRelation = graphicalRelation;
		this.isCardinalityEditable = isCardinalityEditable;

		this.relationInstances = new FundamentalRelationInstance[graphicalRelation.getInstancesNumber()];

		int i = 0;
		for (Enumeration e = graphicalRelation.getInstances() ; e.hasMoreElements(); i++)
		{
			relationInstances[i] = (FundamentalRelationInstance)e.nextElement();
		}

		if(opdRelation instanceof OpdAggregation) dialogName = "Aggregation-Particulation";
		if(opdRelation instanceof OpdExhibition) dialogName = "Featuring-Characterization";
		if(opdRelation instanceof OpdSpecialization) dialogName = "Generalization-Specialization";
		if(opdRelation instanceof OpdInstantination) dialogName = "Classification-Instantination";
		setTitle(dialogName + " Relation Properties");


		//init all variables
		_initVariables();

		Container contPane = getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));

		JTabbedPane tabs = new JTabbedPane();

		tabs.add("General", _constructGeneralTab());
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
		// Escape & Enter listener
		KeyListener escapeAndEnterListener = new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					if(!destinationsTable.hasFocus())
					{
						(RelationPropertiesDialog.this).dispose();
					}
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!destinationsTable.hasFocus())
					{
						if((RelationPropertiesDialog.this)._updateRelationData())
						{
							(RelationPropertiesDialog.this).dispose();
						}
					}
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
	}

	public void showDialog()
	{
		this.setVisible(true);
	}


	//===============================================================

	private void _initVariables()
	{
		try
		{
//			description = new JTextArea("Here will be description", 3, 30);
//			description.setEditable(false);

			sourceName = (opdSourceThing.getEntity()).getName().replace('\n',' ');

			sourceThing = new JTextField(sourceName);
			sourceThing.setSelectedTextColor(new Color(0,255,0));
			sourceThing.setEditable(false);
			sourceThing.setHorizontalAlignment(JTextField.CENTER);

			sourceCardinality = new JTextField("1");
			sourceCardinality.setEditable(false);
			sourceCardinality.setHorizontalAlignment(JTextField.CENTER);


			destinationsTable = new DestinationsTable(relationInstances.length, isCardinalityEditable);
			for (int j = 0 ; j < relationInstances.length; j++)
			{
				if (((relationInstances[j].getDestination()).getEntity()) instanceof OpmObject)
				{
					destinationsTable.setIsObject(true, j);
				}
				if (((relationInstances[j].getDestination()).getEntity()) instanceof OpmProcess)
				{
					destinationsTable.setIsObject(false, j);
				}
				destinationsTable.setValueAt(((relationInstances[j].getDestination()).getEntity()).getName().replace('\n',' '), j, 0);
				tmpCardinality = (relationInstances[j].getLogicalRelation()).getDestinationCardinality();
				if (tmpCardinality.compareTo("1") == 0)
								{
				  destinationsTable.setValueAt(tmpCardinality, j, 1);
				}
				else if (tmpCardinality.compareTo("m") == 0)
				{
					destinationsTable.setValueAt("many", j, 1);
				}
				else
				{
					destinationsTable.setValueAt("custom", j, 1);
					destinationsTable.setValueAt(tmpCardinality, j, 2);
				}
			}
			destinationsTable.setVisible(true);

		}catch(Exception e)
		{
			System.out.println("Problem2!!");
		}

		bgColor = new JButton("    ");
		bgColor.setBackground(opdRelation.getBackgroundColor());
		textColor = new JButton("    ");
		textColor.setBackground(opdRelation.getTextColor());
		borderColor = new JButton("    ");
		borderColor.setBackground(opdRelation.getBorderColor());
	}

	//===============================================================

	private JPanel _constructGeneralTab()
	{
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		//Relation description
//		p1 = new JPanel();
//		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Description "), BorderFactory.createEmptyBorder(5,5,5,5)));
//		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
//		description.setBackground(p1.getBackground());
//		p1.add(description);
//		tab.add(p1);


		//source and destinations pref
		p1 = new JPanel();
		p1.setLayout(new  GridLayout(1,2));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Source "));
		//source
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		JLabel source1 = new JLabel("Name: ");
		p2.add(source1);
		p2.add(Box.createHorizontalStrut(10));
		p2.add(sourceThing);
		p1.add(p2);


		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		JLabel source2 = new JLabel("Cardinality: ");
		p2.add(source2);
		p2.add(Box.createHorizontalStrut(10));
		p2.add(sourceCardinality);
		p1.add(p2);
		tab.add(p1);

		tab.add(Box.createVerticalStrut(10));

		//destination
		destinationsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), " Destinations "));
		tab.add(destinationsTable);
		return tab;
	}

	//===============================================================

	private JPanel _constructMiscTab()
	{
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


	//===============================================================

	private boolean _updateRelationData()
	{

		destinationsTable.updateTable();

		for (int k = 0 ; k < relationInstances.length; k++)
		{
			tmpCardinality = (destinationsTable.getValueAt(k, 1)).toString();
			if (tmpCardinality.compareTo("1") == 0)
			{
				(relationInstances[k].getLogicalRelation()).setDestinationCardinality(tmpCardinality);
			}
			else if (tmpCardinality.compareTo("many") == 0)
			{
				(relationInstances[k].getLogicalRelation()).setDestinationCardinality("m");
			}

			else if (tmpCardinality.compareTo("custom") == 0)
			{
				String str = (destinationsTable.getValueAt(k, 2)).toString();

				if(GraphicsUtils.checkCustomCardinalityLegality(str))
				{
					(relationInstances[k].getLogicalRelation()).setDestinationCardinality(str);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Custom cardinality should be of the form min..max or v1, v2, v3 or combination of them.", "Opcat 2 - Error" ,JOptionPane.ERROR_MESSAGE);
					JTable table = destinationsTable.getTable();
					DefaultCellEditor de = (DefaultCellEditor)table.getCellEditor(table.getSelectedRow(), 2);
					table.editCellAt(table.getSelectedRow(), 2);
					JTextField tf = (JTextField)de.getComponent();
					tf.setSelectionStart(0);
					tf.setSelectionEnd(tf.getText().length());
					tf.requestFocus();
					return false;
				}
			}

		}

		myGraphicalRelation.setBackgroundColor(bgColor.getBackground());
		myGraphicalRelation.setTextColor(textColor.getBackground());
		myGraphicalRelation.setBorderColor(borderColor.getBackground());

		myGraphicalRelation.updateInstances();
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

		return true;
	}



	//===============================================================

	class BgColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(RelationPropertiesDialog.this,
					"Choose Relation Color",
					bgColor.getBackground());
			if(newColor != null)
					(RelationPropertiesDialog.this).bgColor.setBackground(newColor);
		}
	}

	//===============================================================

	class TextColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					RelationPropertiesDialog.this,
					"Choose Text Color",
					textColor.getBackground());
			if(newColor != null)
					(RelationPropertiesDialog.this).textColor.setBackground(newColor);
		}
	}

	//===============================================================

	class BorderColorButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color newColor = JColorChooser.showDialog(
					RelationPropertiesDialog.this,
					"Choose Border Color",
					borderColor.getBackground());
			if(newColor != null)
					(RelationPropertiesDialog.this).borderColor.setBackground(newColor);
		}
	}

	//===============================================================

	class OkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((RelationPropertiesDialog.this)._updateRelationData())
				(RelationPropertiesDialog.this).dispose();
			return;
		}
	}

	//===============================================================

	class ApplyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			(RelationPropertiesDialog.this)._updateRelationData();
			opdRelation.repaint();
			opdRelation.repaintLines();
		}
	}

	//===============================================================

	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			(RelationPropertiesDialog.this).dispose();
		}
	}

}
