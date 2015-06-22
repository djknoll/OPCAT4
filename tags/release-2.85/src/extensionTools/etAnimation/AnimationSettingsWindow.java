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
 * <p>
 * Title: Extension Tools
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class AnimationSettingsWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9095874374262628160L;

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
		this.setSize(new Dimension(500, 480));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Animation Settings");
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.populate();
	}

	void populate() {
		this.jTextFieldStepDuration
				.setText(new Long(AnimationSettings.STEP_DURATION).toString());
		this.jTextFieldProcessDuration.setText(new Long(
				AnimationSettings.PROCESS_DURATION).toString());
		this.jRadioButtonProcessDurationFixed
				.setSelected(AnimationSettings.FIXED_PROCESS_DURATION);
		this.jRadioButtonProcessDurationRandom
				.setSelected(!AnimationSettings.FIXED_PROCESS_DURATION);
		this.jTextFieldProcessDurationRandomStart.setText(new Long(
				AnimationSettings.PROCESS_DURATION_RANGE_START).toString());
		this.jTextFieldProcessDurationRandomEnd.setText(new Long(
				AnimationSettings.PROCESS_DURATION_RANGE_END).toString());
		this.jRadioButtonReactionTimeFixed
				.setSelected(AnimationSettings.FIXED_REACTION_TIME);
		this.jRadioButtonReactionTimeRandom
				.setSelected(!AnimationSettings.FIXED_REACTION_TIME);
		this.jTextFieldReactionTime
				.setText(new Long(AnimationSettings.REACTION_TIME).toString());
		this.jTextFieldReactionTimeRandomStart.setText(new Long(
				AnimationSettings.REACTION_TIME_RANGE_START).toString());
		this.jTextFieldReactionTimeRandomEnd.setText(new Long(
				AnimationSettings.REACTION_TIME_RANGE_END).toString());
		this.jRadioButtonDefaultInstancesOne
				.setSelected(AnimationSettings.ONE_OBJECT_INSTANCE);
		this.jRadioButtonDefaultInstancesMany
				.setSelected(!AnimationSettings.ONE_OBJECT_INSTANCE);
		this.jCheckBoxAutomaticInitiation
				.setSelected(AnimationSettings.AUTOMATIC_INITIATION);
		this.jCheckBoxMoveBetweenOPD.setSelected(AnimationSettings.MOVE_BETWEEN_OPD);
		this.jRadioButtonAnimationModeContinuous
				.setSelected(!AnimationSettings.STEP_BY_STEP_MODE);
		this.jRadioButtonAnimationModeStepByStep
				.setSelected(AnimationSettings.STEP_BY_STEP_MODE);
		this.jCheckBoxRandomStateSelection
				.setSelected(AnimationSettings.RANDOM_STATE_SELECTION);
	}

	private void jbInit() throws Exception {
		// set common constraints
		this.jPanelFields.setLayout(this.fieldsPanelLayout);
		this.jRadioButtonProcessDurationFixed.setSelected(true);
		this.jRadioButtonProcessDurationFixed.setText("Fixed");
		this.jRadioButtonProcessDurationFixed
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						AnimationSettingsWindow.this.jRadioButtonProcessDurationFixed_itemStateChanged(e);
					}
				});
		this.jLabelProcessDuration.setText("Process Duration:");
		this.jRadioButtonProcessDurationRandom.setText("Random");
		this.jTextFieldProcessDuration.setToolTipText("");
		this.jTextFieldProcessDuration.setText("1");
		this.jLabelProcessDurationUnit.setToolTipText("");
		this.jLabelProcessDurationUnit.setText("steps");
		this.jLabelReactionTime.setToolTipText("");
		this.jLabelReactionTime.setText("Reaction Time:");
		this.jRadioButtonReactionTimeFixed.setToolTipText("");
		this.jRadioButtonReactionTimeFixed.setSelected(true);
		this.jRadioButtonReactionTimeFixed.setText("Fixed");
		this.jRadioButtonReactionTimeFixed
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						AnimationSettingsWindow.this.jRadioButtonReactionTimeFixed_itemStateChanged(e);
					}
				});
		this.jRadioButtonReactionTimeRandom.setToolTipText("");
		this.jRadioButtonReactionTimeRandom.setText("Random");
		this.jTextFieldReactionTime.setText("0");
		this.jLabelDefaultInstances.setToolTipText("");
		this.jLabelDefaultInstances.setText("Default Object Instances:");
		this.jRadioButtonDefaultInstancesOne.setToolTipText("");
		this.jRadioButtonDefaultInstancesOne.setMnemonic('0');
		this.jRadioButtonDefaultInstancesOne.setSelected(true);
		this.jRadioButtonDefaultInstancesOne.setText("One");
		this.jRadioButtonDefaultInstancesMany.setToolTipText("");
		this.jRadioButtonDefaultInstancesMany.setText("Many");
		this.jLabelReactionTimeUnit.setText("steps");
		this.jLabelAutomaticInitiation.setToolTipText("");
		this.jLabelAutomaticInitiation.setText("Use Automatic Initiation:");
		this.jLabelMoveBetweenOPD.setToolTipText("");
		this.jLabelMoveBetweenOPD.setDisplayedMnemonic('0');
		this.jLabelMoveBetweenOPD.setText("Automatic Move Between OPD:");
		this.jCheckBoxAutomaticInitiation
				.setToolTipText("Initiate objects automatically when animation starts");
		this.jCheckBoxAutomaticInitiation.setSelected(true);
		this.jCheckBoxMoveBetweenOPD
				.setToolTipText("Automatically switch to child OPD");
		this.jCheckBoxMoveBetweenOPD.setSelected(true);
		this.JLabelStepDuration.setToolTipText("");
		this.jLabelStepDuration.setText("Step Duration:");
		this.jTextFieldStepDuration.setToolTipText("Enter step duration in msec");
		this.jTextFieldStepDuration.setText("1000");
		this.jLabelStepDurationUnit.setToolTipText("");
		this.jLabelStepDurationUnit.setText("msec");
		this.jTextFieldReactionTimeRandomStart
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimationSettingsWindow.this.jTextFieldReactionTimeRandomStart_actionPerformed(e);
					}
				});
		this.jLabel2.setToolTipText("");
		this.jLabel2.setText("-");
		this.jLabelReactionTimeRandomUnit.setText("steps");
		this.jTextFieldReactionTimeRandomEnd
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimationSettingsWindow.this.jTextFieldReactionTimeRandomEnd_actionPerformed(e);
					}
				});
		this.jTextFieldProcessDurationRandomEnd
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimationSettingsWindow.this.jTextFieldProcessDurationRandomEnd_actionPerformed(e);
					}
				});
		this.jLabel1.setToolTipText("");
		this.jLabel1.setText("-");
		this.jTextFieldProcessDurationRandomStart.setToolTipText("");
		this.jTextFieldProcessDurationRandomStart
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimationSettingsWindow.this.jTextFieldProcessDurationRandomStart_actionPerformed(e);
					}
				});
		this.jTextFieldProcessDurationRandomEnd.setToolTipText("");
		this.jLabelProcessDurationRandomUnit.setToolTipText("");
		this.jLabelProcessDurationRandomUnit.setText("steps");
		this.jButtonDefault.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationSettingsWindow.this.jButtonDefault_actionPerformed(e);
			}
		});
		this.jButtonClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationSettingsWindow.this.jButtonClose_actionPerformed(e);
			}
		});
		this.setResizable(false);
		this.setTitle("Animation Settings");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AnimationSettingsWindow.this.this_windowClosing(e);
			}
		});
		this.jLabel3.setToolTipText("");
		this.jLabel3.setText("Animation Mode:");
		this.jRadioButtonAnimationModeContinuous.setText("Continuous");
		this.jRadioButtonAnimationModeStepByStep.setToolTipText("");
		this.jRadioButtonAnimationModeStepByStep.setText("Step by Step");
		this.jLabel4
				.setToolTipText("Select a state randomly when no initial state is defined");
		this.jLabel4.setText("Random State Selection");
		this.jCheckBoxRandomStateSelection.setToolTipText("");
		this.jPanelButtons.add(this.jButtonSave, null);
		this.jPanelButtons.add(this.jButtonDefault, null);
		this.jPanelButtons.add(this.jButtonClose, null);
		this.getContentPane().add(this.jPanelFields, BorderLayout.CENTER);
		this.jButtonClose.setMnemonic('0');
		this.jButtonClose.setText("Close");
		this.jButtonDefault.setText("Default");
		this.jButtonSave.setText("Save");
		this.jButtonSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationSettingsWindow.this.jButtonSave_actionPerformed(e);
			}
		});

		this.jPanelFields.add(this.JLabelStepDuration, new GridBagConstraints(0, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));
		this.jPanelFields.add(this.jLabelStepDurationUnit, new GridBagConstraints(4, 0,
				1, 2, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonProcessDurationFixed,
				new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelProcessDuration, new GridBagConstraints(0, 2, 2,
				1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		this.getContentPane().add(this.jPanelButtons, BorderLayout.SOUTH);
		this.buttonGroupProcessDuration.add(this.jRadioButtonProcessDurationFixed);
		this.buttonGroupProcessDuration.add(this.jRadioButtonProcessDurationRandom);
		this.jPanelFields.add(this.jRadioButtonProcessDurationRandom,
				new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelProcessDurationUnit, new GridBagConstraints(7,
				2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jTextFieldProcessDuration, new GridBagConstraints(3,
				2, 4, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelReactionTime, new GridBagConstraints(0, 4, 2, 1,
				0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonReactionTimeFixed, new GridBagConstraints(
				2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 7, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonReactionTimeRandom,
				new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(2, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jTextFieldReactionTime, new GridBagConstraints(3, 4,
				4, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelDefaultInstances, new GridBagConstraints(0, 6,
				2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonDefaultInstancesOne,
				new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.buttonGroupReactionTime.add(this.jRadioButtonReactionTimeFixed);
		this.buttonGroupReactionTime.add(this.jRadioButtonReactionTimeRandom);
		this.jPanelFields.add(this.jRadioButtonDefaultInstancesMany,
				new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelReactionTimeUnit, new GridBagConstraints(7, 4,
				1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelAutomaticInitiation, new GridBagConstraints(0,
				8, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelMoveBetweenOPD, new GridBagConstraints(0, 9, 2,
				1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jCheckBoxAutomaticInitiation, new GridBagConstraints(
				2, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jCheckBoxMoveBetweenOPD, new GridBagConstraints(2, 9,
				1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabelStepDuration, new GridBagConstraints(0, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.buttonGroupObjectInstances.add(this.jRadioButtonDefaultInstancesOne);
		this.buttonGroupObjectInstances.add(this.jRadioButtonDefaultInstancesMany);
		this.jPanelFields.add(this.jTextFieldStepDuration, new GridBagConstraints(2, 1,
				2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jTextFieldReactionTimeRandomStart,
				new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabel2, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jTextFieldReactionTimeRandomEnd,
				new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 26, 7));
		this.jPanelFields.add(this.jLabelReactionTimeRandomUnit, new GridBagConstraints(
				7, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.jPanelFields.add(this.jTextFieldProcessDurationRandomStart,
				new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jTextFieldProcessDurationRandomEnd,
				new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabel1, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0));
		this.jPanelFields.add(this.jLabelProcessDurationRandomUnit,
				new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabel3, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonAnimationModeContinuous,
				new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jRadioButtonAnimationModeStepByStep,
				new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jLabel4, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		this.jPanelFields.add(this.jCheckBoxRandomStateSelection, new GridBagConstraints(
				2, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.buttonGroupAnimationMode.add(this.jRadioButtonAnimationModeContinuous);
		this.buttonGroupAnimationMode.add(this.jRadioButtonAnimationModeStepByStep);
	}

	void jRadioButtonProcessDurationFixed_itemStateChanged(ItemEvent e) {
		boolean mode = (e.getStateChange() == ItemEvent.SELECTED);
		this.jTextFieldProcessDuration.setEnabled((mode));
		this.jLabelProcessDurationUnit.setEnabled((mode));
		this.jTextFieldProcessDurationRandomStart.setEnabled(!mode);
		this.jTextFieldProcessDurationRandomEnd.setEnabled(!mode);
		this.jLabelProcessDurationRandomUnit.setEnabled(!mode);
	}

	void jRadioButtonReactionTimeFixed_itemStateChanged(ItemEvent e) {
		boolean mode = (e.getStateChange() == ItemEvent.SELECTED);
		this.jTextFieldReactionTime.setEnabled((mode));
		this.jLabelReactionTimeUnit.setEnabled((mode));
		this.jTextFieldReactionTimeRandomStart.setEnabled(!mode);
		this.jTextFieldReactionTimeRandomEnd.setEnabled(!mode);
		this.jLabelReactionTimeRandomUnit.setEnabled(!mode);
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
		if (!field.isEnabled()) {
			return true;
		}
		try {
			java.lang.Long.valueOf(field.getText());
		} catch (NumberFormatException e) {
			// illegal value - show error message
			JOptionPane.showMessageDialog(this,
					"Illegal value in " + fieldName, "Validation",
					JOptionPane.ERROR_MESSAGE);
			field.requestFocus();
			return false;
		}
		return true;
	}

	private boolean validateRange(JTextField startRangeField,
			JTextField endRangeField) {
		int start = java.lang.Integer.valueOf(startRangeField.getText())
				.intValue();
		int end = java.lang.Integer.valueOf(endRangeField.getText()).intValue();
		if (start >= end) {
			JOptionPane.showMessageDialog(this,
					"Start range should be greater than end range",
					"Validation", JOptionPane.ERROR_MESSAGE);
			startRangeField.requestFocus();
			return false;
		}
		return true;
	}

	private boolean validateSettings() {
		// check that numeric field contain numeric values
		if (!this.validateLongField(this.jTextFieldStepDuration, "Step Duration")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldProcessDuration,
				"Fixed Process Duration")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldProcessDurationRandomStart,
				"Process Duration Range Start")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldProcessDurationRandomEnd,
				"Process Duration Range End")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldReactionTime, "Fixed Reaction Time")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldReactionTimeRandomStart,
				"Reaction Time Range Start")) {
			return false;
		}
		if (!this.validateLongField(this.jTextFieldReactionTimeRandomEnd,
				"Reaction Time Range End")) {
			return false;
		}
		// check ranges
		if (!this.validateRange(this.jTextFieldProcessDurationRandomStart,
				this.jTextFieldProcessDurationRandomEnd)) {
			return false;
		}
		if (!this.validateRange(this.jTextFieldReactionTimeRandomStart,
				this.jTextFieldReactionTimeRandomEnd)) {
			return false;
		}
		return true;
	}

	boolean jButtonSave_actionPerformed(ActionEvent e) {
		if (!this.validateSettings()) {
			return false;
		}

		AnimationSettings.STEP_DURATION = java.lang.Long.valueOf(
				this.jTextFieldStepDuration.getText()).longValue();
		AnimationSettings.FIXED_PROCESS_DURATION = this.jRadioButtonProcessDurationFixed
				.isSelected();
		if (AnimationSettings.FIXED_PROCESS_DURATION) {
			AnimationSettings.PROCESS_DURATION = java.lang.Integer.valueOf(
					this.jTextFieldProcessDuration.getText()).intValue();
		} else {
			AnimationSettings.PROCESS_DURATION_RANGE_START = java.lang.Integer
					.valueOf(this.jTextFieldProcessDurationRandomStart.getText())
					.intValue();
			AnimationSettings.PROCESS_DURATION_RANGE_END = java.lang.Integer
					.valueOf(this.jTextFieldProcessDurationRandomEnd.getText())
					.intValue();
		}
		AnimationSettings.FIXED_REACTION_TIME = this.jRadioButtonReactionTimeFixed
				.isSelected();
		if (AnimationSettings.FIXED_REACTION_TIME) {
			AnimationSettings.REACTION_TIME = java.lang.Integer.valueOf(
					this.jTextFieldReactionTime.getText()).intValue();
		} else {
			AnimationSettings.REACTION_TIME_RANGE_START = java.lang.Integer
					.valueOf(this.jTextFieldReactionTimeRandomStart.getText())
					.intValue();
			AnimationSettings.REACTION_TIME_RANGE_END = java.lang.Integer
					.valueOf(this.jTextFieldReactionTimeRandomEnd.getText())
					.intValue();
		}
		AnimationSettings.ONE_OBJECT_INSTANCE = this.jRadioButtonDefaultInstancesOne
				.isSelected();
		AnimationSettings.AUTOMATIC_INITIATION = this.jCheckBoxAutomaticInitiation
				.isSelected();
		AnimationSettings.MOVE_BETWEEN_OPD = this.jCheckBoxMoveBetweenOPD
				.isSelected();
		AnimationSettings.STEP_BY_STEP_MODE = this.jRadioButtonAnimationModeStepByStep
				.isSelected();
		AnimationSettings.RANDOM_STATE_SELECTION = this.jCheckBoxRandomStateSelection
				.isSelected();
		return true;
	}

	void jButtonDefault_actionPerformed(ActionEvent e) {
		// restore default settings
		AnimationSettings.loadDefaultSettings();
		this.populate();
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		this.this_windowClosing(null);
	}

	private boolean dataModified() {
		if (AnimationSettings.STEP_DURATION != java.lang.Long.valueOf(
				this.jTextFieldStepDuration.getText()).longValue()) {
			return true;
		}
		if (AnimationSettings.FIXED_PROCESS_DURATION != this.jRadioButtonProcessDurationFixed
				.isSelected()) {
			return true;
		}
		if (this.jTextFieldProcessDuration.isEnabled()) {
			if (AnimationSettings.PROCESS_DURATION != java.lang.Integer
					.valueOf(this.jTextFieldProcessDuration.getText()).intValue()) {
				return true;
			}
		} else {
			if (AnimationSettings.PROCESS_DURATION_RANGE_START != java.lang.Integer
					.valueOf(this.jTextFieldProcessDurationRandomStart.getText())
					.intValue()) {
				return true;
			}
			if (AnimationSettings.PROCESS_DURATION_RANGE_END != java.lang.Integer
					.valueOf(this.jTextFieldProcessDurationRandomEnd.getText())
					.intValue()) {
				return true;
			}
		}
		if (AnimationSettings.FIXED_REACTION_TIME != this.jRadioButtonReactionTimeFixed
				.isSelected()) {
			return true;
		}
		if (this.jTextFieldReactionTime.isEnabled()) {
			if (AnimationSettings.REACTION_TIME != java.lang.Integer.valueOf(
					this.jTextFieldReactionTime.getText()).intValue()) {
				return true;
			}
		} else {
			if (AnimationSettings.REACTION_TIME_RANGE_START != java.lang.Integer
					.valueOf(this.jTextFieldReactionTimeRandomStart.getText())
					.intValue()) {
				return true;
			}
			if (AnimationSettings.REACTION_TIME_RANGE_END != java.lang.Integer
					.valueOf(this.jTextFieldReactionTimeRandomEnd.getText())
					.intValue()) {
				return true;
			}
		}
		if (AnimationSettings.ONE_OBJECT_INSTANCE != this.jRadioButtonDefaultInstancesOne
				.isSelected()) {
			return true;
		}
		if (AnimationSettings.AUTOMATIC_INITIATION != this.jCheckBoxAutomaticInitiation
				.isSelected()) {
			return true;
		}
		if (AnimationSettings.MOVE_BETWEEN_OPD != this.jCheckBoxMoveBetweenOPD
				.isSelected()) {
			return true;
		}
		if (AnimationSettings.STEP_BY_STEP_MODE != this.jRadioButtonAnimationModeStepByStep
				.isSelected()) {
			return true;
		}
		if (AnimationSettings.RANDOM_STATE_SELECTION != this.jCheckBoxRandomStateSelection
				.isSelected()) {
			return true;
		}
		return false;
	}

	void this_windowClosing(WindowEvent e) {
		if (!this.dataModified()) {
			this.dispose();
			return;
		}
		int response = JOptionPane.showConfirmDialog(this,
				"Save current settings?", "Animation Settings",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			// don't close the window if saved failed
			if (!this.jButtonSave_actionPerformed(null)) {
				return;
			}
		}
		if (response != JOptionPane.CANCEL_OPTION) {
			this.dispose();
		}
	}
}