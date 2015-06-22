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

public class ScenarioParamsPanel extends JPanel {
	SimulationRunner runner;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JPanel spacer1 = new JPanel();
	JLabel creatorLabel = new JLabel();
	JRadioButton runtimeRadioButton = new JRadioButton();
	JRadioButton fileRadioButton = new JRadioButton();
	JTextField fileNameField = new JTextField();	
	JButton browseButton = new JButton();
	JButton jsaveButton = new JButton();
	JFileChooser chooser = new JFileChooser();
	private boolean newScenarioWasLoaded;
	File newScenarioFile;
	ButtonGroup creatorGroup = new ButtonGroup();

	public ScenarioParamsPanel(SimulationRunner runner){
		try {
	  	  newScenarioWasLoaded = false;	    	
	      this.runner = runner;
	      jbInit();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	}
	

	  private void saveScenario(){
		  int userChoice = chooser.showSaveDialog(this);
		  if (userChoice == JFileChooser.APPROVE_OPTION){
			  Thread saver = new Thread(){
				  public void run(){
					  runner.saveAsXML(chooser.getSelectedFile());
				  }
			  };

			  saver.setPriority(Thread.NORM_PRIORITY);
			  saver.setName("Scenario saver");
		      saver.start();
		  }
	  }

	  private void loadScenario(){
		  newScenarioWasLoaded = true;
		  int userChoice = chooser.showOpenDialog(this);
		  if (userChoice == JFileChooser.APPROVE_OPTION) {
			  newScenarioFile = chooser.getSelectedFile();
		  }
		  fileNameField.setText(newScenarioFile.getPath());
	  }

	  public void applyParams(){
		  if (fileRadioButton.isSelected() && newScenarioWasLoaded){
			  runner.setCreatorType(SimulationRunner.CREATOR_TYPE.FILE, newScenarioFile, false);
		  }else{
			  if (runtimeRadioButton.isSelected()){
		        runner.setCreatorType(SimulationRunner.CREATOR_TYPE.RUNTIME, null, false);
			  }
		  }

	  }
	  
	  private void updateAvailability(){
		  fileNameField.setEnabled(!runner.isPlaying() && fileRadioButton.isSelected());	
		  browseButton.setEnabled(!runner.isPlaying() && fileRadioButton.isSelected());
	  }

	  public void updateParams(){
		  runtimeRadioButton.setEnabled(!runner.isPlaying());
		  fileRadioButton.setEnabled(!runner.isPlaying());
		  updateAvailability();
		  jsaveButton.setEnabled(runner.isPlaying());
		  
		  switch (runner.getCreatorType()){
		  	case SimulationRunner.CREATOR_TYPE.FILE :{
		  		fileRadioButton.setSelected(true);
		  		break;
		  	}
		  	case SimulationRunner.CREATOR_TYPE.RUNTIME :{
		  		runtimeRadioButton.setSelected(true);
		  		break;
		  	}
		  }
	  }
	  
	  private void jbInit() throws Exception {
		  setLayout(gridBagLayout1);
		  setBorder(BorderFactory.createRaisedBevelBorder());
		  creatorLabel.setText("Run simulation using:");
		  runtimeRadioButton.setText("Online generated scenario");
		  runtimeRadioButton.addChangeListener(new SelectionChangeListener());
		  fileRadioButton.setText("Previousely saved scenario");
		  fileRadioButton.addChangeListener(new SelectionChangeListener());
		  browseButton.setText("Browse...");
		  browseButton.addActionListener(new BrowseButton_ActionAdapter(this));
		  jsaveButton.setText("Save Scenario...");
		  jsaveButton.addActionListener(new SaveButton_ActionAdapter(this));
		  creatorGroup.add(runtimeRadioButton);
		  creatorGroup.add(fileRadioButton);
		  fileNameField.setPreferredSize(new Dimension(170, 20));
		  fileNameField.setMinimumSize(new Dimension(170, 20));
		  fileNameField.setMaximumSize(new Dimension(170, 20));

		  add(spacer1, new GridBagConstraints(21, 21, 1, 1, 0.1, 0.1
				  , GridBagConstraints.CENTER,
				  GridBagConstraints.BOTH,
				  new Insets(0, 0, 0, 0), 0, 0));
		  add(creatorLabel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(10, 10, 5, 5), 0, 0));
		  add(runtimeRadioButton,
				  new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
						  , GridBagConstraints.NORTHWEST,
						  GridBagConstraints.NONE,
						  new Insets(1, 15, 1, 5), 0, 0));
		  add(fileRadioButton, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
				  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				  new Insets(1, 15, 1, 5), 0, 0));

		  add(fileNameField, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
				  , GridBagConstraints.WEST, GridBagConstraints.NONE,
				  new Insets(1, 35, 1, 5), 0, 0));
		  
		  add(browseButton, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
				  , GridBagConstraints.WEST, GridBagConstraints.NONE,
				  new Insets(1, 5, 1, 5), 0, 0));
		  add(jsaveButton, new GridBagConstraints(0, 12, 3, 1, 0.0, 0.0
				  , GridBagConstraints.WEST, GridBagConstraints.NONE,
				  new Insets(30, 10, 0, 0), 0, 0));
	  }
		  
	  public void jsaveButton_actionPerformed(ActionEvent e) {
		  saveScenario();
	  }

	  public void browseButton_actionPerformed(ActionEvent e) {
		  loadScenario();
	  }

	  class SelectionChangeListener implements ChangeListener{
		  public void stateChanged(ChangeEvent event){
			  updateAvailability();
		  }
	  }
}

class BrowseButton_ActionAdapter implements ActionListener {
	private ScenarioParamsPanel adaptee;
	BrowseButton_ActionAdapter(ScenarioParamsPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.browseButton_actionPerformed(e);
	}
}

class SaveButton_ActionAdapter implements ActionListener {
	private ScenarioParamsPanel adaptee;
	SaveButton_ActionAdapter(ScenarioParamsPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jsaveButton_actionPerformed(e);
	}
}

