package simulation.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulation.SimulationConfig;
import simulation.SimulationRunner;
import simulation.creator.CreationConfig;

public class AdvancedParamsPanel extends JPanel {
	SimulationRunner runner;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JPanel spacer1 = new JPanel();
	JLabel moveOPDsLabel = new JLabel();
	JRadioButton dontMoveRadioButton = new JRadioButton();
	JRadioButton singleOPDMoveButton = new JRadioButton();
	JRadioButton parallelOPDMoveButton = new JRadioButton();
	ButtonGroup moveOPDGroup = new ButtonGroup();
	JCheckBox showLifeSpanBox = new JCheckBox();
	private CreationConfig creationConfig;
	SimulationConfig config = SimulationConfig.getInstance();

	public AdvancedParamsPanel(SimulationRunner runner){
		try {
	      this.runner = runner;
	      this.creationConfig = config.getCreationConfig();
	      jbInit();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	}
	


	  public void applyParams(){
		  config.showLifespan = showLifeSpanBox.isSelected();
		  if (dontMoveRadioButton.isSelected()){
			  config.moveBetweenOPDs = SimulationConfig.AUTO_OPD_MOVE_TYPE.DONT_MOVE;  
		  }

		  if (singleOPDMoveButton.isSelected()){
			  config.moveBetweenOPDs = SimulationConfig.AUTO_OPD_MOVE_TYPE.SINGLE_OPD_MOVE;  
		  }

		  if (parallelOPDMoveButton.isSelected()){
			  config.moveBetweenOPDs = SimulationConfig.AUTO_OPD_MOVE_TYPE.PARALLEL_OPD_MOVE;  
		  }
		  
		  runner.setLayoutType(config.moveBetweenOPDs);
	  }
	  
	  private void updateAvailability(){
	  }

	  public void updateParams(){
		  showLifeSpanBox.setSelected(config.showLifespan);
		  switch (config.moveBetweenOPDs){
		  	case SimulationConfig.AUTO_OPD_MOVE_TYPE.DONT_MOVE:{
		  		dontMoveRadioButton.setSelected(true);
		  		break;
		  	}
		  	case SimulationConfig.AUTO_OPD_MOVE_TYPE.SINGLE_OPD_MOVE:{
		  		singleOPDMoveButton.setSelected(true);
		  		break;
		  	}
		  	case SimulationConfig.AUTO_OPD_MOVE_TYPE.PARALLEL_OPD_MOVE:{
		  		parallelOPDMoveButton.setSelected(true);
		  		break;
		  	}
		  	
		  }
	  }
	  
	  private void jbInit() throws Exception {
		  setLayout(gridBagLayout1);
		  setBorder(BorderFactory.createRaisedBevelBorder());
		  moveOPDsLabel.setText("Automatically move between OPDs:");
		  dontMoveRadioButton.setText("Don't move");
		  dontMoveRadioButton.addChangeListener(new SelectionChangeListener());
		  singleOPDMoveButton.setText("Show last activated OPD");
		  singleOPDMoveButton.addChangeListener(new SelectionChangeListener());
		  parallelOPDMoveButton.setText("Show relevant OPDs simoultaneousely");
		  parallelOPDMoveButton.addChangeListener(new SelectionChangeListener());
		  
		  moveOPDGroup.add(dontMoveRadioButton);
		  moveOPDGroup.add(singleOPDMoveButton);
		  moveOPDGroup.add(parallelOPDMoveButton);		  
		  showLifeSpanBox.setText("Show Lifespan");
		  
		  add(spacer1, new GridBagConstraints(21, 21, 1, 1, 0.1, 0.1
				  , GridBagConstraints.CENTER,
				  GridBagConstraints.BOTH,
				  new Insets(0, 0, 0, 0), 0, 0));
		  add(moveOPDsLabel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(10, 10, 5, 5), 0, 0));
		  add(dontMoveRadioButton,
				  new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
						  , GridBagConstraints.NORTHWEST,
						  GridBagConstraints.NONE,
						  new Insets(1, 15, 1, 5), 0, 0));
		  add(singleOPDMoveButton, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(1, 15, 1, 5), 0, 0));
		  add(parallelOPDMoveButton, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(1, 15, 1, 5), 0, 0));
		  

		  add(showLifeSpanBox,
				  new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
						  , GridBagConstraints.NORTHWEST,
						  GridBagConstraints.NONE,
						  new Insets(15, 5, 2, 5), 0, 0));

	  }
		  

	  class SelectionChangeListener implements ChangeListener{
		  public void stateChanged(ChangeEvent event){
			  updateAvailability();
		  }
	  }
}


