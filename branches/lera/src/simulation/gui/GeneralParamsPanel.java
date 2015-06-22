package simulation.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import simulation.SimulationConfig;
import simulation.SimulationRunner;
import simulation.creator.CreationConfig;

public class GeneralParamsPanel extends JPanel {
	SimulationRunner runner;
	JCheckBox isContinuous = new JCheckBox();
	JCheckBox isRunOnCopy = new JCheckBox();
	JSpinner stepDurationField = new JSpinner();
	JLabel stepDurationLabel = new JLabel();
	JSpinner processDurationField = new JSpinner();
	JLabel processDurationLabel = new JLabel();
	JLabel msecLabel1 = new JLabel("mSec");
	JLabel msecLabel2 = new JLabel("mSec");
	
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JPanel spacer1 = new JPanel();
	private CreationConfig creationConfig;
	JCheckBox agentActivatedBox = new JCheckBox();
	JCheckBox quickRunBox = new JCheckBox();
	JCheckBox autoInitiationBox = new JCheckBox();
	SimulationConfig config = SimulationConfig.getInstance();
	
	public GeneralParamsPanel(SimulationRunner runner){
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
	    creationConfig.setStopOnAgents(agentActivatedBox.isSelected());
	    creationConfig.setAutoInitiated(autoInitiationBox.isSelected());
	    creationConfig.setDefaultProcessDuration(((Integer)processDurationField.getValue()).intValue());
	    runner.setContinousPlay(isContinuous.isSelected());
	    runner.setQuickRunMode(quickRunBox.isSelected());
	    runner.setRunOnCopy(isRunOnCopy.isSelected());
	    int stepDuration =  ((Integer)stepDurationField.getValue()).intValue();
	    SimulationConfig.getInstance().atomicsInStep = stepDuration / config.atomicStepDuration;
	  }

	  public void updateParams(){
    	isRunOnCopy.setEnabled(!(runner.isPlaying()));
    	autoInitiationBox.setEnabled(!(runner.isPlaying()));
    	agentActivatedBox.setSelected(creationConfig.isStopOnAgents());
    	autoInitiationBox.setSelected(creationConfig.isAutoInitiated());
    	processDurationField.setValue(new Integer(creationConfig.getDefaultProcessDuration()));
    	
    	quickRunBox.setSelected(runner.isQuickRunMode());
	    isContinuous.setSelected(runner.isContinouosPlay());
	    isRunOnCopy.setSelected(runner.isRunOnCopy());
	    stepDurationField.setValue(new Integer(
	    		config.atomicsInStep * config.atomicStepDuration));
	  }

	  private void jbInit() throws Exception {
		  isContinuous.setText("Run contiounisely");
		  isRunOnCopy.setText("Run simulation on copied system");
		  autoInitiationBox.setText("Perform automatic initiation");
		  quickRunBox.setText("Quick run mode");
		  stepDurationField.setModel(new SpinnerNumberModel(config.atomicStepDuration * config.atomicsInStep,
				  config.atomicStepDuration, config.atomicStepDuration * 100, config.atomicStepDuration));
		  stepDurationField.setMinimumSize(new Dimension(60, 20));
		  stepDurationField.setPreferredSize(new Dimension(60, 20));
		  stepDurationLabel.setText("Single step duration : ");
		    
		  processDurationField.setMinimumSize(new Dimension(60, 20));
		  processDurationField.setPreferredSize(new Dimension(60, 20));
		  processDurationField.setModel(new SpinnerNumberModel(10,10, 10000, 250));
		  processDurationLabel.setText("Default process duration: ");
		    
		  setLayout(gridBagLayout1);
		  setBorder(BorderFactory.createRaisedBevelBorder());
		  agentActivatedBox.setText("Stop on agents");

		  add(isRunOnCopy, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(10, 5, 2, 5), 0, 0));
		  add(autoInitiationBox, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(2, 5, 2, 5), 0, 0));
		  
		  add(isContinuous, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(2, 5, 2, 5), 0, 0));
		  add(agentActivatedBox,
				  new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
						  , GridBagConstraints.NORTHWEST,
						  GridBagConstraints.NONE,
						  new Insets(2, 5, 2, 5), 0, 0));
		  add(quickRunBox,
				  new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
						  , GridBagConstraints.NORTHWEST,
						  GridBagConstraints.NONE,
						  new Insets(2, 5, 2, 5), 0, 0));
		    
		    
		  add(stepDurationLabel,
				  new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
						  , GridBagConstraints.WEST,
						  GridBagConstraints.NONE,
						  new Insets(15, 10, 5, 5), 0, 0));
		  add(stepDurationField,
				  new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
						  , GridBagConstraints.SOUTHWEST,
						  GridBagConstraints.HORIZONTAL,
						  new Insets(15, 5, 5, 5), 0, 0));

		  add(msecLabel1,
				  new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0
						  , GridBagConstraints.WEST,
						  GridBagConstraints.NONE,
						  new Insets(15, 2, 5, 5), 0, 0));
		    
		  add(processDurationLabel,
				  new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
						  , GridBagConstraints.WEST,
						  GridBagConstraints.NONE,
						  new Insets(5, 10, 5, 5), 0, 0));

		  add(processDurationField,
				  new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
						  , GridBagConstraints.SOUTHWEST,
						  GridBagConstraints.HORIZONTAL,
						  new Insets(5, 5, 5, 5), 0, 0));

		  add(msecLabel2,
				  new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0
						  , GridBagConstraints.WEST,
						  GridBagConstraints.NONE,
						  new Insets(5, 2, 5, 5), 0, 0));
		    
		  add(spacer1, new GridBagConstraints(21, 21, 1, 1, 0.1, 0.1
				  , GridBagConstraints.CENTER,
				  GridBagConstraints.BOTH,
				  new Insets(10, 10, 10, 10), 0, 0));
		    
	  }
		  
	
}
