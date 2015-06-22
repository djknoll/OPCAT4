package extensionTools.opltoopd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents the GUI of OPLtoOPD window
 */
public class OPLComboBox extends JDialog
{
    /**
     * Creates the main dialog  and assigns it with controller.
     * @param aOPLComboBoxController The main controller of the application.
     */
  public OPLComboBox(OPLComboBoxController aOPLComboBoxController)
  {
      super(aOPLComboBoxController.getSystem().getMainFrame(), true);
      oPLComboBoxController = aOPLComboBoxController;
      setContentPane(createMainPanel());
      addListeners();
      pack();

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      int fX = aOPLComboBoxController.getSystem().getMainFrame().getX();
      int fY = aOPLComboBoxController.getSystem().getMainFrame().getY();
      int pWidth = aOPLComboBoxController.getSystem().getMainFrame().getWidth();
      int pHeight = aOPLComboBoxController.getSystem().getMainFrame().getHeight();

      setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));

      setResizable(false);
      setTitle("Add new OPL sentence");
  }

  private JPanel ComboBoxPanel()
  {

    JLabel patternLabel2 = new JLabel();
    JLabel patternLabel1 = new JLabel();
    patternLabel1.setText("<html><center><font color='navy' face='Tahoma' size='4'><b>&nbsp;&nbsp;&nbsp; -----Add new OPL sentence----- &nbsp;&nbsp;&nbsp;</b></font></center></html>");
    patternLabel2.setText("<html><center><font color='navy' face='Tahoma' size='4'><b>&nbsp;&nbsp;&nbsp; ------------------------------------- &nbsp;&nbsp;&nbsp;</b></font></center></html>");
    patternList = new JComboBox(patternExamples);

      currentPattern = patternExamples[0];
    patternList.setEditable(false);
    patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
    JPanel patternPanel = new JPanel();
    patternPanel.setLayout(new BoxLayout(patternPanel, BoxLayout.Y_AXIS));
    patternPanel.add(patternList);
    patternPanel.setAlignmentX(Component. CENTER_ALIGNMENT);

    return patternPanel;
  }

  private JPanel ResultPanel()
  {
    result.setForeground(Color.black);
    result.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.black),
        BorderFactory.createEmptyBorder(5,5,5,5)
        ));
    JPanel resultPanel = new JPanel();
    resultPanel.setLayout(new GridLayout(0, 1));
    resultPanel.add(result);
    return resultPanel;
  }

  private JPanel createButtonsPanel()
  {
    buttonsPanel = new UpdateCancelButtonsPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
    buttonsPanel.setBorder
    (BorderFactory.createCompoundBorder());
    return buttonsPanel;
  }

  private JPanel createMainPanel()
  {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
    mainPanel.add(ComboBoxPanel());

    mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    mainPanel.add(ResultPanel());
    mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    mainPanel.add(createButtonsPanel());

    return mainPanel;

  }
  /**
  * Returns the <code>OPLtoOPD window</code> of this system.
   */
  public JPanel getPanel(){
    return mainPanel;
  }

  private void addListeners()
  {
      KeyListener kListener = new KeyAdapter()
      {
          public void keyReleased(KeyEvent e)
          {
              if (e.getKeyCode() == KeyEvent.VK_ENTER)
              {
                  oPLComboBoxController.addButtonPressed();
                  return;
              }

              if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
              {
                  OPLComboBox.this.dispose();
                  return;
              }

          }
      };
      addKeyListener(kListener);

      ComponentAdapter cListener = new ComponentAdapter()
      {
        public void  componentShown(ComponentEvent e)
        {
            result.requestFocus();
        }
      };

      this.addComponentListener(cListener);


    patternList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        oPLComboBoxController.comboChused(e);
    }});
    buttonsPanel.getAddButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        oPLComboBoxController.addButtonPressed();
    }});
    buttonsPanel.getExitButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLComboBox.this.dispose();
        return;
    }});

    buttonsPanel.getCancelButton().
        addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        oPLComboBoxController.cancelButtonPressed();
    }});
        buttonsPanel.getUpdateButton().
           addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           oPLComboBoxController.updateButtonPressed();
    }});
  }

  JComboBox patternList;
  JPanel mainPanel ;
  public JTextField result = new JTextField();
  public UpdateCancelButtonsPanel buttonsPanel;
  private OPLComboBoxController oPLComboBoxController;
  String currentPattern;
  public String[] patternExamples = {
       "(Add new sentence)"
   };
}






