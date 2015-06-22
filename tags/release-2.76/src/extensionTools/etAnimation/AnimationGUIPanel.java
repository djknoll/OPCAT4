package extensionTools.etAnimation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPIx.IXSystem;
import gui.Opcat2;
import gui.util.HtmlPanel;
import gui.util.OpcatException;

class AnimationGUIPanel extends JPanel
{
  IXSystem mySys;
  AnimationSystem aSys = null;
  private final static String fileSeparator =
	System.getProperty("file.separator");

  //JSplitPane jSplitPane1 = new JSplitPane();
  //BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanelButtons = new JPanel();
  //JPanel jPanel2 = new JPanel();
  JButton runButton = new JButton();
  JButton stopButton = new JButton();
  JButton pauseButton = new JButton();
  JButton continueButton = new JButton();
  JButton forwardButton = new JButton();
  JButton backwardButton = new JButton();
  JButton activateButton = new JButton();
  JButton deactivateButton = new JButton();
  JButton settingsButton = new JButton();
  JButton help = new JButton();
  JPanel jPanelRun = new JPanel();
  JPanel jPanelPause = new JPanel();
  JPanel jPanelManual = new JPanel();
  JPanel jPanelStep = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  Border border1;
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JTextField jTextFieldForwardSteps = new JTextField();
  JTextField jTextFieldBackward = new JTextField();
  JSplitPane jSplitPane1 = new JSplitPane();
  BoxLayout boxLayout1 = new BoxLayout(jPanelRun, BoxLayout.Y_AXIS);
  BoxLayout boxLayout2 = new BoxLayout(jPanelPause, BoxLayout.Y_AXIS);
  BoxLayout boxLayout3 = new BoxLayout(jPanelManual, BoxLayout.Y_AXIS);
  BoxLayout boxLayout4 = new BoxLayout(jPanelStep, BoxLayout.Y_AXIS);
  JPanel jPanel1 = new JPanel();
  AnimationStatusBar jLabelAnimationStatus = new AnimationStatusBar();
  GridLayout gridLayout3 = new GridLayout();
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanel2 = new JPanel();
  //ImageIcon AgentIcon = new ImageIcon(getResource("agent.gif"));
  //this.setIconImage(AgentIcon.getImage());

  public AnimationGUIPanel(IXSystem sys)
  {
    mySys = sys;
    aSys = new AnimationSystem(mySys, jLabelAnimationStatus);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    AnimationSettings.loadDefaultSettings();
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    titledBorder4 = new TitledBorder("");

    // run button
    runButton.setMaximumSize(new Dimension(50, 27));
    runButton.setMinimumSize(new Dimension(30, 27));
    runButton.setPreferredSize(new Dimension(30, 27));
    runButton.setToolTipText("Run");
    runButton.setMargin(new Insets(2, 2, 2, 2));
    runButton.setText("Run");
    runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    runButton_actionPerformed(e);
            }
    });

    // stop button
    stopButton.setEnabled(false);
    stopButton.setMaximumSize(new Dimension(91, 27));
    stopButton.setMinimumSize(new Dimension(30, 27));
    stopButton.setPreferredSize(new Dimension(30, 27));
    stopButton.setToolTipText("Stop");
    stopButton.setMargin(new Insets(2, 2, 2, 2));
    stopButton.setText("Stop");
    stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    stopButton_actionPerformed(e);
            }
    });

    // pause button
    pauseButton.setEnabled(false);
    pauseButton.setMaximumSize(new Dimension(91, 27));
    pauseButton.setMinimumSize(new Dimension(91, 27));
    pauseButton.setPreferredSize(new Dimension(30, 27));
    pauseButton.setToolTipText("Pause");
    pauseButton.setMargin(new Insets(2, 2, 2, 2));
    pauseButton.setText("Pause");
    pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    pauseButton_actionPerformed(e);
            }
    });

    // continue button
    continueButton.setEnabled(false);
    continueButton.setMaximumSize(new Dimension(91, 27));
    continueButton.setMinimumSize(new Dimension(91, 27));
    continueButton.setPreferredSize(new Dimension(30, 27));
    continueButton.setToolTipText("Continue");
    continueButton.setMargin(new Insets(2, 2, 2, 2));
    continueButton.setText("Continue");
    continueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    continueButton_actionPerformed(e);
            }
    });

    // forward button
    forwardButton.setEnabled(false);
    forwardButton.setMinimumSize(new Dimension(30, 27));
    forwardButton.setPreferredSize(new Dimension(30, 27));
    forwardButton.setToolTipText("Forward");
    forwardButton.setMargin(new Insets(0, 0, 0, 0));
    forwardButton.setMnemonic('0');
    forwardButton.setText("Forward");
    forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    forwardButton_actionPerformed(e);
            }
    });

    // backward button
    backwardButton.setEnabled(false);
    backwardButton.setMinimumSize(new Dimension(65, 27));
    backwardButton.setPreferredSize(new Dimension(30, 27));
    backwardButton.setToolTipText("Backward");
    backwardButton.setMargin(new Insets(2, 2, 2, 2));
    backwardButton.setMnemonic('0');
    backwardButton.setText("Backward");
    backwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    backwardButton_actionPerformed(e);
            }
    });

    // activate button
    activateButton.setEnabled(false);
    activateButton.setMaximumSize(new Dimension(91, 27));
    activateButton.setMinimumSize(new Dimension(91, 27));
    activateButton.setPreferredSize(new Dimension(30, 27));
    activateButton.setToolTipText("Activate");
    activateButton.setMargin(new Insets(2, 2, 2, 2));
    activateButton.setText("Activate");
    activateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    activateButton_actionPerformed(e);
            }
    });

    // deactivate button
    deactivateButton.setEnabled(false);
    deactivateButton.setPreferredSize(new Dimension(30, 27));
    deactivateButton.setToolTipText("Deactivate");
    deactivateButton.setMargin(new Insets(2, 2, 2, 2));
    deactivateButton.setText("Deactivate");
    deactivateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    deactivateButton_actionPerformed(e);
            }
    });

    // settings button
    settingsButton.setBorder(null);
    settingsButton.setMaximumSize(new Dimension(30, 27));
    settingsButton.setMinimumSize(new Dimension(91, 27));
    settingsButton.setPreferredSize(new Dimension(60, 27));
    settingsButton.setToolTipText("Settings");
    settingsButton.setMargin(new Insets(2, 2, 2, 2));
    settingsButton.setText("Settings");
    settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    settingsButton_actionPerformed(e);
            }
    });

    help.setBorder(null);
    help.setMaximumSize(new Dimension(30, 27));
    help.setMinimumSize(new Dimension(91, 27));
    help.setPreferredSize(new Dimension(60, 27));
    help.setToolTipText("Help");
    help.setMargin(new Insets(2, 2, 2, 2));
    help.setText("Help");
    help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    help_actionPerformed(e);
            }
    });

    jPanelRun.setBorder(titledBorder1);
    jPanelRun.setPreferredSize(new Dimension(100, 30));
    jPanelPause.setBorder(titledBorder2);
    jPanelPause.setPreferredSize(new Dimension(100, 30));
    jPanelManual.setBorder(titledBorder3);
    jPanelManual.setPreferredSize(new Dimension(152, 30));
    jPanelStep.setBorder(titledBorder4);
    jPanelStep.setPreferredSize(new Dimension(340, 30));
    jTextFieldForwardSteps.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextFieldForwardSteps.setPreferredSize(new Dimension(18, 21));
    jTextFieldForwardSteps.setToolTipText("");
    jTextFieldForwardSteps.setText("1");
    jTextFieldForwardSteps.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextFieldForwardSteps_actionPerformed(e);
      }
    });
    jTextFieldBackward.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextFieldBackward.setPreferredSize(new Dimension(18, 21));
    jTextFieldBackward.setToolTipText("");
    jTextFieldBackward.setText("1");
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jPanelButtons.setLayout(gridLayout1);
    this.setLayout(gridLayout2);
    jLabelAnimationStatus.setPreferredSize(new Dimension(41, 17));
    jPanel1.setLayout(gridLayout3);

    // run
    jPanelButtons.setPreferredSize(new Dimension(1700, 30));
    jPanel2.setPreferredSize(new Dimension(304, 30));
    jPanelRun.add(runButton, null);
    jPanelRun.add(stopButton, null);
    jPanelButtons.add(jPanelRun, null);
    jPanelButtons.add(jPanelPause, null);

    // pause
    jPanelPause.add(pauseButton, null);
    jPanelPause.add(continueButton, null);
    jPanelButtons.add(jPanelStep, null);

    // step
    jPanelStep.add(forwardButton, null);
    jPanelStep.add(jTextFieldForwardSteps, null);
    jPanelStep.add(backwardButton, null);
    jPanelStep.add(jTextFieldBackward, null);
    jPanelButtons.add(jPanelManual, null);

    // manual
    jPanelManual.add(activateButton, null);
    jPanelManual.add(deactivateButton, null);
    jPanelButtons.add(jPanel2, null);
    jPanel2.add(settingsButton, null);
    jPanelButtons
	.add(help, null);

    jSplitPane1.add(jPanel1, JSplitPane.BOTTOM);
    jPanel1.add(jLabelAnimationStatus, null);
    jSplitPane1.add(jPanelButtons, JSplitPane.TOP);
    this.add(jSplitPane1, null);

  }


  void stopButton_actionPerformed(ActionEvent e)
  {
    aSys.animationStop();
    // disable all options except run and settings
    this.forwardButton.setEnabled(false);
    this.backwardButton.setEnabled(false);
    this.activateButton.setEnabled(false);
    this.deactivateButton.setEnabled(false);
    this.stopButton.setEnabled(false);
    this.pauseButton.setEnabled(false);
    this.continueButton.setEnabled(false);
    this.settingsButton.setEnabled(true);
    this.help.setEnabled(true);
    this.runButton.setEnabled(true);
  }

  void runButton_actionPerformed(ActionEvent e)
  {
    aSys.animationStart();
    // enable all options except run and settings
    // enable forward and backward only in step by step mode
    if (AnimationSettings.STEP_BY_STEP_MODE)  {
      this.forwardButton.setEnabled(true);
      this.backwardButton.setEnabled(true);
    }
    this.activateButton.setEnabled(true);
    this.deactivateButton.setEnabled(true);
    this.stopButton.setEnabled(true);
    this.pauseButton.setEnabled(true);
    this.continueButton.setEnabled(true);
    this.settingsButton.setEnabled(false);
    this.runButton.setEnabled(false);

  }

  void pauseButton_actionPerformed(ActionEvent e)
  {
    aSys.animationPause();
    this.forwardButton.setEnabled(true);
    this.backwardButton.setEnabled(true);
  }

  void continueButton_actionPerformed(ActionEvent e)
  {
    aSys.animationContinue();
  }

  void forwardButton_actionPerformed(ActionEvent e) {
    long numberOfSteps = 1;
    try {
      numberOfSteps = java.lang.Long.valueOf(jTextFieldForwardSteps.getText()).longValue();
    }
    catch (NumberFormatException exc)  {
      JOptionPane.showMessageDialog(this,"Illegal number of steps for forward", "Forward Failed", JOptionPane.ERROR_MESSAGE);
      jTextFieldForwardSteps.requestFocus();
      return ;
    }
    aSys.animationForward(numberOfSteps);
  }

  void backwardButton_actionPerformed(ActionEvent e) {
    long numberOfSteps = 1;
    try {
      numberOfSteps = java.lang.Long.valueOf(jTextFieldBackward.getText()).longValue();
    }
    catch (NumberFormatException exc)  {
      JOptionPane.showMessageDialog(this,"Illegal number of steps for backward", "Backward Failed", JOptionPane.ERROR_MESSAGE);
      jTextFieldBackward.requestFocus();
      return ;
    }
    aSys.animationBackward(numberOfSteps);
  }

  void activateButton_actionPerformed(ActionEvent e) {
    aSys.animationActivate();
  }

  void deactivateButton_actionPerformed(ActionEvent e) {
    aSys.animationDeactivate();
  }

  void help_actionPerformed(ActionEvent e) {


	Thread runner = new Thread() {
		public void run() {
			JFrame helpWindow;

			helpWindow = new JFrame("Opcat II Animation Help");
			try {
                //helpWindow.setIconImage(logoIcon.getImage());
                helpWindow.getContentPane().add(
                	new HtmlPanel(
                		"file:"
                			+ System.getProperty("user.dir")
                			+ fileSeparator
                			+ "help"
                			+ fileSeparator
                			+ "help"+fileSeparator+"index.html"));
            } catch (OpcatException e) {
                JOptionPane.showMessageDialog(
    					Opcat2.getFrame(),
    					"Help files were not found",
    					"Help error",
    					JOptionPane.ERROR_MESSAGE);    
            }

			Dimension screenSize =
				Toolkit.getDefaultToolkit().getScreenSize();

			helpWindow.setBounds(
				0,
				0,
				(int) (screenSize.getWidth() * 7 / 8),
				(int) (screenSize.getHeight() * 7 / 8));
			helpWindow.setDefaultCloseOperation(
				WindowConstants.DISPOSE_ON_CLOSE);
			helpWindow.setVisible(true);

		}
	};
	runner.start();

  }
  void settingsButton_actionPerformed(ActionEvent e) {

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    AnimationSettingsWindow settingsWindow = new AnimationSettingsWindow();
    boolean packFrame = false;
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      settingsWindow.pack();
    }
    else {
      settingsWindow.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = settingsWindow.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    settingsWindow.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    settingsWindow.setVisible(true);
  }

  void jTextFieldForwardSteps_actionPerformed(ActionEvent e) {

  }

}