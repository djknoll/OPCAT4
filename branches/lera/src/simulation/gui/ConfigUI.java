package simulation.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import simulation.creator.FileCreator;
import simulation.creator.CreationConfig;
import simulation.SimulationConfig;
import simulation.SimulationRunner;
import simulation.creator.CreationConfig;

import java.io.File;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import simulation.SimulationConfig;
import simulation.SimulationRunner;
import simulation.creator.CreationConfig;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ConfigUI extends JDialog {
  SimulationRunner runner;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  JButton applyButton = new JButton();
  JButton okButton = new JButton();
  JTabbedPane myTabs = new JTabbedPane();
  JPanel buttonsPanel = new JPanel();
  ScenarioParamsPanel scenariousPanel;
  GeneralParamsPanel genParamsPanel;
  AdvancedParamsPanel advancedParamsPanel;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel spacer1 = new JPanel();

  public ConfigUI(SimulationRunner runner, JFrame parent) {
	super(parent);
    try {
      this.runner = runner;
      jbInit();
      this.pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setVisible(boolean isVisible){
    updateParams();
    super.setVisible(isVisible);   
  }


  private void applyParams(){
	  genParamsPanel.applyParams();
	  scenariousPanel.applyParams();
	  advancedParamsPanel.applyParams();
  }

  private void updateParams(){
	  genParamsPanel.updateParams();
	  scenariousPanel.updateParams();
	  advancedParamsPanel.updateParams();	  
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(gridBagLayout1);
    applyButton.setText("Apply");
    applyButton.addActionListener(new ConfigUI_applyButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new ConfigUI_okButton_actionAdapter(this));
    buttonsPanel.setLayout(gridBagLayout2);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Simulation Configuration");
    genParamsPanel = new GeneralParamsPanel(runner);
    scenariousPanel = new ScenarioParamsPanel(runner);
    advancedParamsPanel = new AdvancedParamsPanel(runner);
    
    buttonsPanel.add(applyButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(5, 5, 5, 5), 0, 0));
    buttonsPanel.add(okButton, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(buttonsPanel,
                              new GridBagConstraints(0, 1, 1, 1, 0.1, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    
    myTabs.add("General", genParamsPanel);
    myTabs.add("Scenario", scenariousPanel);
    myTabs.add("Advanced", advancedParamsPanel);
    
    this.getContentPane().add(myTabs,
                              new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1
        , GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
        new Insets(2, 2, 2, 2), 0, 0));
  }
  
  public void okButton_actionPerformed(ActionEvent e) {
    applyParams();
    this.setVisible(false);
  }

  public void applyButton_actionPerformed(ActionEvent e) {
    applyParams();
  }

}

class ConfigUI_applyButton_actionAdapter
    implements ActionListener {
  private ConfigUI adaptee;
  ConfigUI_applyButton_actionAdapter(ConfigUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.applyButton_actionPerformed(e);
  }
}

class ConfigUI_okButton_actionAdapter
    implements ActionListener {
  private ConfigUI adaptee;
  ConfigUI_okButton_actionAdapter(ConfigUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}
