package extensionTools.etAnimation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AnimationSettingsWindow extends JDialog {
  JPanel jPanelButtons = new JPanel();
  JButton jButtonClose = new JButton();
  JButton jButtonDefault = new JButton();
  JButton jButtonSave = new JButton();
  JPanel jPanelFields = new JPanel();
  GridBagLayout fieldsPanelLayout = new GridBagLayout();
  JRadioButton jRadioButtonProcessDurationFixed = new JRadioButton();
  JLabel JLabelStepDuration = new JLabel();
  JLabel jLabelStepDurationUnit = new JLabel();
  JLabel jLabelProcessDuration = new JLabel();
  ButtonGroup buttonGroupProcessDuration = new ButtonGroup();
  JRadioButton jRadioButtonProcessDurationRandom = new JRadioButton();
  JTextField jTextFieldProcessDuration = new JTextField();
  JLabel jLabelProcessDurationUnit = new JLabel();
  JLabel jLabelReactionTime = new JLabel();
  ButtonGroup buttonGroupReactionTime = new ButtonGroup();
  JRadioButton jRadioButtonReactionTimeFixed = new JRadioButton();
  JRadioButton jRadioButtonReactionTimeRandom = new JRadioButton();
  JTextField jTextFieldReactionTime = new JTextField();
  JLabel jLabelDefaultInstances = new JLabel();
  ButtonGroup buttonGroupObjectInstances = new ButtonGroup();
  JRadioButton jRadioButtonDefaultInstancesOne = new JRadioButton();
  JRadioButton jRadioButtonDefaultInstancesMany = new JRadioButton();
  JLabel jLabelReactionTimeUnit = new JLabel();
  JLabel jLabelAutomaticInitiation = new JLabel();
  JLabel jLabelMoveBetweenOPD = new JLabel();
  JCheckBox jCheckBoxAutomaticInitiation = new JCheckBox();
  JCheckBox jCheckBoxMoveBetweenOPD = new JCheckBox();
  JLabel jLabelStepDuration = new JLabel();
  JTextField jTextFieldStepDuration = new JTextField();
  JTextField jTextFieldReactionTimeRandomStart = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldReactionTimeRandomEnd = new JTextField();
  JLabel jLabelReactionTimeRandomUnit = new JLabel();
  JTextField jTextFieldProcessDurationRandomStart = new JTextField();
  JTextField jTextFieldProcessDurationRandomEnd = new JTextField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabelProcessDurationRandomUnit = new JLabel();
  JLabel jLabel3 = new JLabel();
  ButtonGroup buttonGroupAnimationMode = new ButtonGroup();
  JRadioButton jRadioButtonAnimationModeContinuous = new JRadioButton();
  JRadioButton jRadioButtonAnimationModeStepByStep = new JRadioButton();
  JLabel jLabel4 = new JLabel();
  JCheckBox jCheckBoxRandomStateSelection = new JCheckBox();

  public AnimationSettingsWindow() {
    setSize(new Dimension(500, 480));
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setTitle("Animation Settings");
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    populate();
  }

  void populate() {
    jTextFieldStepDuration.setText(new Long(AnimationSettings.STEP_DURATION).toString());
    jTextFieldProcessDuration.setText(new Long(AnimationSettings.PROCESS_DURATION).toString());
    jRadioButtonProcessDurationFixed.setSelected(AnimationSettings.FIXED_PROCESS_DURATION);
    jRadioButtonProcessDurationRandom.setSelected(!AnimationSettings.FIXED_PROCESS_DURATION);
    jTextFieldProcessDurationRandomStart.setText(new Long(AnimationSettings.PROCESS_DURATION_RANGE_START).toString());
    jTextFieldProcessDurationRandomEnd.setText(new Long(AnimationSettings.PROCESS_DURATION_RANGE_END).toString());
    jRadioButtonReactionTimeFixed.setSelected(AnimationSettings.FIXED_REACTION_TIME);
    jRadioButtonReactionTimeRandom.setSelected(!AnimationSettings.FIXED_REACTION_TIME);
    jTextFieldReactionTime.setText(new Long(AnimationSettings.REACTION_TIME).toString());
    jTextFieldReactionTimeRandomStart.setText(new Long(AnimationSettings.REACTION_TIME_RANGE_START).toString());
    jTextFieldReactionTimeRandomEnd.setText(new Long(AnimationSettings.REACTION_TIME_RANGE_END).toString());
    jRadioButtonDefaultInstancesOne.setSelected(AnimationSettings.ONE_OBJECT_INSTANCE);
    jRadioButtonDefaultInstancesMany.setSelected(!AnimationSettings.ONE_OBJECT_INSTANCE);
    jCheckBoxAutomaticInitiation.setSelected(AnimationSettings.AUTOMATIC_INITIATION);
    jCheckBoxMoveBetweenOPD.setSelected(AnimationSettings.MOVE_BETWEEN_OPD);
    jRadioButtonAnimationModeContinuous.setSelected(!AnimationSettings.STEP_BY_STEP_MODE);
    jRadioButtonAnimationModeStepByStep.setSelected(AnimationSettings.STEP_BY_STEP_MODE);
    jCheckBoxRandomStateSelection.setSelected(AnimationSettings.RANDOM_STATE_SELECTION);
  }


  private void jbInit() throws Exception {
    // set common constraints
    jPanelFields.setLayout(fieldsPanelLayout);
    jRadioButtonProcessDurationFixed.setSelected(true);
    jRadioButtonProcessDurationFixed.setText("Fixed");
    jRadioButtonProcessDurationFixed.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        jRadioButtonProcessDurationFixed_itemStateChanged(e);
      }
    });
    jLabelProcessDuration.setText("Process Duration:");
    jRadioButtonProcessDurationRandom.setText("Random");
    jTextFieldProcessDuration.setToolTipText("");
    jTextFieldProcessDuration.setText("1");
    jLabelProcessDurationUnit.setToolTipText("");
    jLabelProcessDurationUnit.setText("steps");
    jLabelReactionTime.setToolTipText("");
    jLabelReactionTime.setText("Reaction Time:");
    jRadioButtonReactionTimeFixed.setToolTipText("");
    jRadioButtonReactionTimeFixed.setSelected(true);
    jRadioButtonReactionTimeFixed.setText("Fixed");
    jRadioButtonReactionTimeFixed.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        jRadioButtonReactionTimeFixed_itemStateChanged(e);
      }
    });
    jRadioButtonReactionTimeRandom.setToolTipText("");
    jRadioButtonReactionTimeRandom.setText("Random");
    jTextFieldReactionTime.setText("0");
    jLabelDefaultInstances.setToolTipText("");
    jLabelDefaultInstances.setText("Default Object Instances:");
    jRadioButtonDefaultInstancesOne.setToolTipText("");
    jRadioButtonDefaultInstancesOne.setMnemonic('0');
    jRadioButtonDefaultInstancesOne.setSelected(true);
    jRadioButtonDefaultInstancesOne.setText("One");
    jRadioButtonDefaultInstancesMany.setToolTipText("");
    jRadioButtonDefaultInstancesMany.setText("Many");
    jLabelReactionTimeUnit.setText("steps");
    jLabelAutomaticInitiation.setToolTipText("");
    jLabelAutomaticInitiation.setText("Use Automatic Initiation:");
    jLabelMoveBetweenOPD.setToolTipText("");
    jLabelMoveBetweenOPD.setDisplayedMnemonic('0');
    jLabelMoveBetweenOPD.setText("Automatic Move Between OPD:");
    jCheckBoxAutomaticInitiation.setToolTipText("Initiate objects automatically when animation starts");
    jCheckBoxAutomaticInitiation.setSelected(true);
    jCheckBoxMoveBetweenOPD.setToolTipText("Automatically switch to child OPD");
    jCheckBoxMoveBetweenOPD.setSelected(true);
    JLabelStepDuration.setToolTipText("");
    jLabelStepDuration.setText("Step Duration:");
    jTextFieldStepDuration.setToolTipText("Enter step duration in msec");
    jTextFieldStepDuration.setText("1000");
    jLabelStepDurationUnit.setToolTipText("");
    jLabelStepDurationUnit.setText("msec");
    jTextFieldReactionTimeRandomStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextFieldReactionTimeRandomStart_actionPerformed(e);
      }
    });
    jLabel2.setToolTipText("");
    jLabel2.setText("-");
    jLabelReactionTimeRandomUnit.setText("steps");
    jTextFieldReactionTimeRandomEnd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextFieldReactionTimeRandomEnd_actionPerformed(e);
      }
    });
    jTextFieldProcessDurationRandomEnd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextFieldProcessDurationRandomEnd_actionPerformed(e);
      }
    });
    jLabel1.setToolTipText("");
    jLabel1.setText("-");
    jTextFieldProcessDurationRandomStart.setToolTipText("");
    jTextFieldProcessDurationRandomStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextFieldProcessDurationRandomStart_actionPerformed(e);
      }
    });
    jTextFieldProcessDurationRandomEnd.setToolTipText("");
    jLabelProcessDurationRandomUnit.setToolTipText("");
    jLabelProcessDurationRandomUnit.setText("steps");
    jButtonDefault.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDefault_actionPerformed(e);
      }
    });
    jButtonClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonClose_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle("Animation Settings");
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    jLabel3.setToolTipText("");
    jLabel3.setText("Animation Mode:");
    jRadioButtonAnimationModeContinuous.setText("Continuous");
    jRadioButtonAnimationModeStepByStep.setToolTipText("");
    jRadioButtonAnimationModeStepByStep.setText("Step by Step");
    jLabel4.setToolTipText("Select a state randomly when no initial state is defined");
    jLabel4.setText("Random State Selection");
    jCheckBoxRandomStateSelection.setToolTipText("");
    jPanelButtons.add(jButtonSave, null);
    jPanelButtons.add(jButtonDefault, null);
    jPanelButtons.add(jButtonClose, null);
    this.getContentPane().add(jPanelFields, BorderLayout.CENTER);
    jButtonClose.setMnemonic('0');
    jButtonClose.setText("Close");
    jButtonDefault.setText("Default");
    jButtonSave.setText("Save");
    jButtonSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSave_actionPerformed(e);
      }
    });

    jPanelFields.add(JLabelStepDuration,             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));
    jPanelFields.add(jLabelStepDurationUnit,          new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jRadioButtonProcessDurationFixed,            new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelProcessDuration,          new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));


    this.getContentPane().add(jPanelButtons,  BorderLayout.SOUTH);
    buttonGroupProcessDuration.add(jRadioButtonProcessDurationFixed);
    buttonGroupProcessDuration.add(jRadioButtonProcessDurationRandom);
    jPanelFields.add(jRadioButtonProcessDurationRandom,           new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelProcessDurationUnit,                  new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jTextFieldProcessDuration,                new GridBagConstraints(3, 2, 4, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelReactionTime,          new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jRadioButtonReactionTimeFixed,              new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 7, 5), 0, 0));
    jPanelFields.add(jRadioButtonReactionTimeRandom,              new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(2, 5, 5, 5), 0, 0));
    jPanelFields.add(jTextFieldReactionTime,           new GridBagConstraints(3, 4, 4, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelDefaultInstances,         new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jRadioButtonDefaultInstancesOne,         new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroupReactionTime.add(jRadioButtonReactionTimeFixed);
    buttonGroupReactionTime.add(jRadioButtonReactionTimeRandom);
    jPanelFields.add(jRadioButtonDefaultInstancesMany,         new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelReactionTimeUnit,                  new GridBagConstraints(7, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelAutomaticInitiation,          new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelMoveBetweenOPD,          new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jCheckBoxAutomaticInitiation,            new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jCheckBoxMoveBetweenOPD,          new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabelStepDuration,                 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroupObjectInstances.add(jRadioButtonDefaultInstancesOne);
    buttonGroupObjectInstances.add(jRadioButtonDefaultInstancesMany);
    jPanelFields.add(jTextFieldStepDuration,         new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jTextFieldReactionTimeRandomStart,                  new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabel2,           new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jTextFieldReactionTimeRandomEnd,                                new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 26, 7));
    jPanelFields.add(jLabelReactionTimeRandomUnit,        new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelFields.add(jTextFieldProcessDurationRandomStart,          new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jTextFieldProcessDurationRandomEnd,         new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabel1,        new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelFields.add(jLabelProcessDurationRandomUnit,         new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabel3,      new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    jPanelFields.add(jRadioButtonAnimationModeContinuous,      new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jRadioButtonAnimationModeStepByStep,   new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jLabel4,  new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelFields.add(jCheckBoxRandomStateSelection,  new GridBagConstraints(2, 12, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroupAnimationMode.add(jRadioButtonAnimationModeContinuous);
    buttonGroupAnimationMode.add(jRadioButtonAnimationModeStepByStep);
  }

  void jRadioButtonProcessDurationFixed_itemStateChanged(ItemEvent e) {
    boolean mode = (e.getStateChange() == ItemEvent.SELECTED);
    jTextFieldProcessDuration.setEnabled((mode));
    jLabelProcessDurationUnit.setEnabled((mode));
    jTextFieldProcessDurationRandomStart.setEnabled(!mode);
    jTextFieldProcessDurationRandomEnd.setEnabled(!mode);
    jLabelProcessDurationRandomUnit.setEnabled(!mode);
  }

  void jRadioButtonReactionTimeFixed_itemStateChanged(ItemEvent e) {
    boolean mode = (e.getStateChange() == ItemEvent.SELECTED);
    jTextFieldReactionTime.setEnabled((mode));
    jLabelReactionTimeUnit.setEnabled((mode));
    jTextFieldReactionTimeRandomStart.setEnabled(!mode);
    jTextFieldReactionTimeRandomEnd.setEnabled(!mode);
    jLabelReactionTimeRandomUnit.setEnabled(!mode);
  }

  void jTextFieldProcessDurationRandomStart_actionPerformed(ActionEvent e) {

  }

  void jTextFieldProcessDurationRandomEnd_actionPerformed(ActionEvent e) {

  }

  void jTextFieldReactionTimeRandomStart_actionPerformed(ActionEvent e) {

  }

  void jTextFieldReactionTimeRandomEnd_actionPerformed(ActionEvent e) {

  }

  private boolean validateLongField(JTextField field, String fieldName) {
    if (!field.isEnabled()) return true;
    try {
      java.lang.Long.valueOf(field.getText());
    }
    catch (NumberFormatException e)  {
      // illegal value - show error message
      JOptionPane.showMessageDialog(this,"Illegal value in " + fieldName, "Validation", JOptionPane.ERROR_MESSAGE);
      field.requestFocus();
      return false;
    }
    return true;
  }

  private boolean validateRange(JTextField startRangeField, JTextField endRangeField) {
    int start = java.lang.Integer.valueOf(startRangeField.getText()).intValue();
    int end = java.lang.Integer.valueOf(endRangeField.getText()).intValue();
    if (start >= end) {
      JOptionPane.showMessageDialog(this,"Start range should be greater than end range", "Validation", JOptionPane.ERROR_MESSAGE);
      startRangeField.requestFocus();
      return false;
    }
    return true;
  }

  private boolean validateSettings()  {
    // check that numeric field contain numeric values
    if (!validateLongField(jTextFieldStepDuration, "Step Duration")) return false;
    if (!validateLongField(jTextFieldProcessDuration, "Fixed Process Duration")) return false;
    if (!validateLongField(jTextFieldProcessDurationRandomStart, "Process Duration Range Start")) return false;
    if (!validateLongField(jTextFieldProcessDurationRandomEnd, "Process Duration Range End")) return false;
    if (!validateLongField(jTextFieldReactionTime, "Fixed Reaction Time")) return false;
    if (!validateLongField(jTextFieldReactionTimeRandomStart, "Reaction Time Range Start")) return false;
    if (!validateLongField(jTextFieldReactionTimeRandomEnd, "Reaction Time Range End")) return false;
    // check ranges
    if (!validateRange(jTextFieldProcessDurationRandomStart, jTextFieldProcessDurationRandomEnd)) return false;
    if (!validateRange(jTextFieldReactionTimeRandomStart, jTextFieldReactionTimeRandomEnd)) return false;
    return true;
  }


  boolean jButtonSave_actionPerformed(ActionEvent e) {
    if (!this.validateSettings()) return false;

    AnimationSettings.STEP_DURATION = java.lang.Long.valueOf(jTextFieldStepDuration.getText()).longValue();
    AnimationSettings.FIXED_PROCESS_DURATION = jRadioButtonProcessDurationFixed.isSelected();
    if (AnimationSettings.FIXED_PROCESS_DURATION) {
      AnimationSettings.PROCESS_DURATION = java.lang.Integer.valueOf(jTextFieldProcessDuration.getText()).intValue();
    }
    else  {
      AnimationSettings.PROCESS_DURATION_RANGE_START = java.lang.Integer.valueOf(jTextFieldProcessDurationRandomStart.getText()).intValue();
      AnimationSettings.PROCESS_DURATION_RANGE_END = java.lang.Integer.valueOf(jTextFieldProcessDurationRandomEnd.getText()).intValue();
    }
    AnimationSettings.FIXED_REACTION_TIME = jRadioButtonReactionTimeFixed.isSelected();
    if (AnimationSettings.FIXED_REACTION_TIME) {
      AnimationSettings.REACTION_TIME = java.lang.Integer.valueOf(jTextFieldReactionTime.getText()).intValue();
    }
    else  {
      AnimationSettings.REACTION_TIME_RANGE_START = java.lang.Integer.valueOf(jTextFieldReactionTimeRandomStart.getText()).intValue();
      AnimationSettings.REACTION_TIME_RANGE_END = java.lang.Integer.valueOf(jTextFieldReactionTimeRandomEnd.getText()).intValue();
    }
    AnimationSettings.ONE_OBJECT_INSTANCE = jRadioButtonDefaultInstancesOne.isSelected();
    AnimationSettings.AUTOMATIC_INITIATION = jCheckBoxAutomaticInitiation.isSelected();
    AnimationSettings.MOVE_BETWEEN_OPD = jCheckBoxMoveBetweenOPD.isSelected();
    AnimationSettings.STEP_BY_STEP_MODE = jRadioButtonAnimationModeStepByStep.isSelected();
    AnimationSettings.RANDOM_STATE_SELECTION = jCheckBoxRandomStateSelection.isSelected();
    return true;
  }

  void jButtonDefault_actionPerformed(ActionEvent e) {
    // restore default settings
    AnimationSettings.loadDefaultSettings();
    populate();
  }

  void jButtonClose_actionPerformed(ActionEvent e) {
    this_windowClosing(null);
  }

  private boolean dataModified() {
    if (AnimationSettings.STEP_DURATION != java.lang.Long.valueOf(jTextFieldStepDuration.getText()).longValue()) return true;
    if (AnimationSettings.FIXED_PROCESS_DURATION != jRadioButtonProcessDurationFixed.isSelected()) return true;
    if (jTextFieldProcessDuration.isEnabled()) {
      if (AnimationSettings.PROCESS_DURATION != java.lang.Integer.valueOf(jTextFieldProcessDuration.getText()).intValue()) return true;
    }
    else  {
      if (AnimationSettings.PROCESS_DURATION_RANGE_START != java.lang.Integer.valueOf(jTextFieldProcessDurationRandomStart.getText()).intValue()) return true;
      if (AnimationSettings.PROCESS_DURATION_RANGE_END != java.lang.Integer.valueOf(jTextFieldProcessDurationRandomEnd.getText()).intValue()) return true;
    }
    if (AnimationSettings.FIXED_REACTION_TIME != jRadioButtonReactionTimeFixed.isSelected()) return true;
    if (jTextFieldReactionTime.isEnabled()) {
      if (AnimationSettings.REACTION_TIME != java.lang.Integer.valueOf(jTextFieldReactionTime.getText()).intValue()) return true;
    }
    else  {
      if (AnimationSettings.REACTION_TIME_RANGE_START != java.lang.Integer.valueOf(jTextFieldReactionTimeRandomStart.getText()).intValue()) return true;
      if (AnimationSettings.REACTION_TIME_RANGE_END != java.lang.Integer.valueOf(jTextFieldReactionTimeRandomEnd.getText()).intValue()) return true;
    }
    if (AnimationSettings.ONE_OBJECT_INSTANCE != jRadioButtonDefaultInstancesOne.isSelected()) return true;
    if (AnimationSettings.AUTOMATIC_INITIATION != jCheckBoxAutomaticInitiation.isSelected()) return true;
    if (AnimationSettings.MOVE_BETWEEN_OPD != jCheckBoxMoveBetweenOPD.isSelected()) return true;
    if (AnimationSettings.STEP_BY_STEP_MODE != jRadioButtonAnimationModeStepByStep.isSelected()) return true;
    if (AnimationSettings.RANDOM_STATE_SELECTION != jCheckBoxRandomStateSelection.isSelected()) return true;
    return false;
  }

  void this_windowClosing(WindowEvent e) {
    if (!this.dataModified())  {
      this.dispose();
      return;
    }
    int response = JOptionPane.showConfirmDialog(this, "Save current settings?", "Animation Settings",
      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.YES_OPTION) {
      // don't close the window if saved failed
      if (!jButtonSave_actionPerformed(null)) return;
    }
    if (response != JOptionPane.CANCEL_OPTION) {
      this.dispose();
    }
  }
}