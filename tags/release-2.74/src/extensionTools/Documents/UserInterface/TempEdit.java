package extensionTools.Documents.UserInterface;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPI.ISystem;
import extensionTools.Documents.Doc.Document;

/**
 * TempEdit Class extends the standard JDialog. Allows user to edit a template
 * or create a new one, by selecting the contents he is interested in.
 * All the choices of the user regarding the fields of the General Info, OPD,
 * OPL and Elements Dictionary are done in this screen.
 * Parallel to the DocGen class (for Documents).
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */

public class TempEdit extends JDialog {
  ISystem mySys;
  String filename;
  JPanel contentPane;
  JTabbedPane TTPMain = new JTabbedPane();
  JPanel TGI = new JPanel();
  JPanel TOPDOPL = new JPanel();
  JPanel TDD = new JPanel();
  TitledBorder titledBorder1;
  JTabbedPane TTPDD = new JTabbedPane();
  JPanel TDDObjects = new JPanel();
  JPanel TDDProcesses = new JPanel();
  JPanel TDDRelations = new JPanel();
  JPanel TDDLinks = new JPanel();
  JPanel TButtonsPanel = new JPanel();
  JButton TCancel = new JButton();
  JPanel TGILeft = new JPanel();
  JCheckBox TGIClient = new JCheckBox();
  JCheckBox TGIOverview = new JCheckBox();
  JCheckBox TGIFuture = new JCheckBox();
  JCheckBox TGIUsers = new JCheckBox();
  JCheckBox TGIGoals = new JCheckBox();
  JCheckBox TGICurrent = new JCheckBox();
  JPanel TOPL = new JPanel();
  JPanel TDDObjMain = new JPanel();
  JCheckBox TDDObjOrigin = new JCheckBox();
  JCheckBox TDDObjDesc = new JCheckBox();
  JCheckBox TDDObjType = new JCheckBox();
  JCheckBox TDDObjStates = new JCheckBox();
  JCheckBox TDDObjScope = new JCheckBox();
  JCheckBox TDDObjEssence = new JCheckBox();
  JPanel TOPD = new JPanel();
  JRadioButton TOPDByName = new JRadioButton();
  JRadioButton TOPDUntil = new JRadioButton();
  JRadioButton TOPDAll = new JRadioButton();
  JRadioButton TOPDNone = new JRadioButton();
  ButtonGroup BG_TOPD = new ButtonGroup();
  ButtonGroup BG_TOPL = new ButtonGroup();
  JCheckBox TDDObjIndex = new JCheckBox();
  JCheckBox TDDObjInitVal = new JCheckBox();
  JPanel TDDObjState = new JPanel();
  JCheckBox TDDObjStateDescr = new JCheckBox();
  JCheckBox TDDObjStateInitial = new JCheckBox();
  Border border1;
  TitledBorder titledBorder2;
  Border border2;
  TitledBorder titledBorder3;
  Border border3;
  TitledBorder titledBorder4;
  Border border4;
  TitledBorder titledBorder5;
  JPanel TDDProcMain = new JPanel();
  Border border5;
  TitledBorder titledBorder6;
  JCheckBox TDDProcEssence = new JCheckBox();
  JCheckBox TDDProcOrigin = new JCheckBox();
  JCheckBox TDDProcDescr = new JCheckBox();
  JCheckBox TDDProcActTime = new JCheckBox();
  JCheckBox TDDProcScope = new JCheckBox();
  JCheckBox TDDProcBody = new JCheckBox();
  Border border6;
  TitledBorder titledBorder7;
  JPanel TDDRelMain = new JPanel();
  Border border7;
  TitledBorder titledBorder8;
  JCheckBox TDDRelAggreg = new JCheckBox();
  JCheckBox TDDRelFeature = new JCheckBox();
  JCheckBox TDDRelGeneral = new JCheckBox();
  JCheckBox TDDRelClassif = new JCheckBox();
  JCheckBox TDDRelUni = new JCheckBox();
  JCheckBox TDDRelBi = new JCheckBox();
  Border border8;
  TitledBorder titledBorder9;
  JPanel TDDLinkMain = new JPanel();
  JCheckBox TDDLinkInvoc = new JCheckBox();
  JCheckBox TDDLinkInsrEv = new JCheckBox();
  JCheckBox TDDLinkCondition = new JCheckBox();
  JCheckBox TDDLinkInstr = new JCheckBox();
  JCheckBox TDDLinkEvent = new JCheckBox();
  JCheckBox TDDLinkRes = new JCheckBox();
  JCheckBox TDDLinkEffect = new JCheckBox();
  JCheckBox TDDLinkAgent = new JCheckBox();
  JCheckBox TDDLinkExeption = new JCheckBox();
  Border border9;
  TitledBorder titledBorder10;
  JCheckBox TGIIssues = new JCheckBox();
  JCheckBox TGIInputs = new JCheckBox();
  JCheckBox TGIHard = new JCheckBox();
  JCheckBox TGIProblems = new JCheckBox();
  JCheckBox TGIBusiness = new JCheckBox();
  JCheckBox TGIOper = new JCheckBox();
  Border border10;
  TitledBorder titledBorder11;
  JTextField TOPLUntilText = new JTextField();
  JTextField TOPDUntilText = new JTextField();
  JRadioButton TOPLUntil = new JRadioButton();
  JRadioButton TOPLAll = new JRadioButton();
  JRadioButton TOPLNone = new JRadioButton();
  JRadioButton TOPLByName = new JRadioButton();
  JRadioButton TOPLandOPD = new JRadioButton();
  JCheckBox TDDLinkPropCond = new JCheckBox();
  JPanel TDDLinkProp = new JPanel();
  JCheckBox TDDLinkPropPath = new JCheckBox();
  JCheckBox TDDLinkPropReacTime = new JCheckBox();
  JButton TSaveAs = new JButton();
  JCheckBox TDDObjStateTime = new JCheckBox();
  JPanel TNamePanel = new JPanel();
  JLabel TNameLable = new JLabel();
  JTextField TNameText = new JTextField();
  JDialog dad;
  JButton SelAll = new JButton();
  JButton UnSelAll = new JButton();
  JButton ObjSelAll = new JButton();
  JButton ObjUnSelAll = new JButton();
  JButton ProcUnSelAll = new JButton();
  JButton ProcSelAll = new JButton();
  JButton LinkUnSelAll = new JButton();
  JButton LinkSelAll = new JButton();
  JButton RelSelAll = new JButton();
  JButton RelUnSelAll = new JButton();

  /**
   * Constructs the frame.
   * @param sys of type ISystem
   * @param file of type String - the name of the template
   * @param prev of type JDialog - the previous screen
   */
  public TempEdit(ISystem sys, String file, JDialog prev) {
    super(sys.getMainFrame(),true);
    dad=prev;
    mySys = sys;
    filename = file;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    //Esc and Enter Keys active
  KeyListener kListener = new KeyAdapter(){
    public void keyReleased(KeyEvent e){
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        TCancel.doClick();
        return;
      }
      if (e.getKeyCode() == KeyEvent.VK_ENTER){
        TSaveAs.doClick();
        return;
      }
    }
  };
  addKeyListener(kListener);
  //set the screen in the center
  int fX = sys.getMainFrame().getX();
  int fY = sys.getMainFrame().getY();
  int pWidth = sys.getMainFrame().getWidth();
  int pHeight = sys.getMainFrame().getHeight();
  setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));
}

  /**
   * Component initialization.
   */
  private void jbInit(){
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"General Information Fields");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"OPL Levels");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border3,"State Properties");
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder5 = new TitledBorder(border4,"Object Properties");
    border5 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    titledBorder6 = new TitledBorder(border5,"Process Properties");
    border6 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder7 = new TitledBorder(border6,"Process Properties");
    border7 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder8 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Relation Types");
    border8 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder9 = new TitledBorder(border8,"Link Properties:");
    border9 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder10 = new TitledBorder(border9,"Link Types");
    border10 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder11 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"OPD Levels");
    contentPane.setLayout(null);
    this.setResizable(false);
    this.setSize(new Dimension(580, 428));
    this.setTitle("Make/Edit Template");
    TGI.setLayout(null);
    TOPDOPL.setLayout(null);
    TDD.setLayout(null);
    TTPDD.setBackground(new Color(204, 204, 204));
    TTPDD.setBounds(new Rectangle(-5, 0, 566, 264));
    TDDObjects.setLayout(null);
    TDDProcesses.setLayout(null);
    TDDRelations.setLayout(null);
    TDDLinks.setLayout(null);
    TTPMain.setBackground(new Color(204, 204, 204));
    TTPMain.setBounds(new Rectangle(3, 42, 563, 291));
    TButtonsPanel.setBounds(new Rectangle(1, 338, 565, 50));
    TButtonsPanel.setLayout(null);
    TCancel.setBounds(new Rectangle(438, 1, 127, 27));
    TCancel.setBorder(BorderFactory.createEtchedBorder());
    TCancel.setToolTipText("");
    TCancel.setText("Cancel");
    TCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TCancel_actionPerformed(e);
      }
    });
    TGILeft.setBorder(titledBorder2);
    TGILeft.setBounds(new Rectangle(12, 17, 536, 237));
    TGILeft.setLayout(null);
    TGIClient.setBorder(BorderFactory.createEtchedBorder());
    TGIClient.setText("Client");
    TGIClient.setBounds(new Rectangle(9, 30, 90, 25));
    TGIOverview.setBorder(null);
    TGIOverview.setText("System Overview");
    TGIOverview.setBounds(new Rectangle(9, 58, 117, 25));
    TGIFuture.setBorder(BorderFactory.createEtchedBorder());
    TGIFuture.setText("Future Goals");
    TGIFuture.setBounds(new Rectangle(9, 115, 100, 25));
    TGIUsers.setBorder(BorderFactory.createEtchedBorder());
    TGIUsers.setText("Possible Users for the System");
    TGIUsers.setBounds(new Rectangle(9, 171, 199, 25));
    TGIGoals.setBorder(BorderFactory.createEtchedBorder());
    TGIGoals.setText("Goals and Objectives of the Project");
    TGIGoals.setBounds(new Rectangle(9, 143, 219, 25));
    TGICurrent.setBorder(BorderFactory.createEtchedBorder());
    TGICurrent.setText("The Current State");
    TGICurrent.setBounds(new Rectangle(9, 86, 136, 25));
    TOPL.setBorder(titledBorder3);
    TOPL.setBounds(new Rectangle(298, 21, 209, 200));
    TOPL.setLayout(null);
    TDDObjMain.setBorder(titledBorder5);
    TDDObjMain.setBounds(new Rectangle(19, 17, 531, 212));
    TDDObjMain.setLayout(null);
    TDDObjOrigin.setText("Origin");
    TDDObjOrigin.setBounds(new Rectangle(14, 100, 58, 25));
    TDDObjDesc.setText("Description ");
    TDDObjDesc.setBounds(new Rectangle(14, 64, 91, 25));
    TDDObjType.setText("Object Type ");
    TDDObjType.setBounds(new Rectangle(14, 27, 92, 25));
    TDDObjStates.setText("States");
    TDDObjStates.setBounds(new Rectangle(175, 134, 60, 25));
    TDDObjStates.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDObjStates_actionPerformed(e);
      }
    });
    TDDObjScope.setText("Scope");
    TDDObjScope.setBounds(new Rectangle(14, 134, 60, 25));
    TDDObjEssence.setText("Essence");
    TDDObjEssence.setBounds(new Rectangle(175, 27, 74, 25));
    TOPD.setBorder(titledBorder11);
    TOPD.setBounds(new Rectangle(48, 21, 209, 200));
    TOPD.setLayout(null);
    TOPDByName.setText("By Name");
    TOPDByName.setBounds(new Rectangle(13, 97, 89, 25));
    TOPDByName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPDByName_actionPerformed(e);
      }
    });
    TOPDUntil.setText("Until Level");
    TOPDUntil.setBounds(new Rectangle(13, 65, 89, 25));
    TOPDUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPDUntil_actionPerformed(e);
      }
    });
    TOPDAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPDAll_actionPerformed(e);
      }
    });
    TOPDAll.setActionCommand("");
    TOPDAll.setText("All Levels");
    TOPDAll.setBounds(new Rectangle(13, 33, 89, 25));
    TOPDNone.setText("None");
    TOPDNone.setBounds(new Rectangle(13, 129, 89, 25));
    TOPDNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPDNone_actionPerformed(e);
      }
    });
    TDDObjIndex.setText("Index");
    TDDObjIndex.setBounds(new Rectangle(175, 64, 54, 25));
    TDDObjInitVal.setText("Initial Value");
    TDDObjInitVal.setBounds(new Rectangle(175, 100, 88, 25));
    TDDObjState.setBorder(titledBorder4);
    TDDObjState.setBounds(new Rectangle(368, 31, 134, 127));
    TDDObjState.setLayout(null);
    TDDObjStateDescr.setEnabled(true);
    TDDObjStateDescr.setText("Description");
    TDDObjStateDescr.setBounds(new Rectangle(16, 65, 88, 25));
    TDDObjStateInitial.setEnabled(true);
    TDDObjStateInitial.setText("Initial/Final");
    TDDObjStateInitial.setBounds(new Rectangle(16, 34, 88, 25));
    TDDProcMain.setBorder(titledBorder7);
    TDDProcMain.setBounds(new Rectangle(19, 17, 531, 211));
    TDDProcMain.setLayout(null);
    TDDProcEssence.setText("Essence");
    TDDProcEssence.setBounds(new Rectangle(14, 63, 92, 25));
    TDDProcOrigin.setText("Origin");
    TDDProcOrigin.setBounds(new Rectangle(14, 134, 92, 25));
    TDDProcDescr.setText("Description");
    TDDProcDescr.setBounds(new Rectangle(14, 27, 92, 25));
    TDDProcActTime.setText("Activation time");
    TDDProcActTime.setBounds(new Rectangle(176, 27, 103, 25));
    TDDProcScope.setText("Scope");
    TDDProcScope.setBounds(new Rectangle(14, 98, 92, 25));
    TDDProcBody.setText("Body");
    TDDProcBody.setBounds(new Rectangle(178, 63, 52, 25));
    TDDRelMain.setBorder(titledBorder8);
    TDDRelMain.setBounds(new Rectangle(19, 17, 531, 211));
    TDDRelMain.setLayout(null);
    TDDRelAggreg.setText("Aggregation-Particulation Relations");
    TDDRelAggreg.setBounds(new Rectangle(14, 27, 235, 25));
    TDDRelFeature.setText("Featuring-Characterization Relations");
    TDDRelFeature.setBounds(new Rectangle(14, 63, 235, 25));
    TDDRelGeneral.setText("Generalization-Specification Relations");
    TDDRelGeneral.setBounds(new Rectangle(14, 98, 235, 25));
    TDDRelClassif.setText("Classification-Instantiation Relations");
    TDDRelClassif.setBounds(new Rectangle(14, 134, 235, 25));
    TDDRelUni.setText("Uni-Directional General Relations");
    TDDRelUni.setBounds(new Rectangle(284, 27, 230, 25));
    TDDRelBi.setText("Bi-directional General Relations");
    TDDRelBi.setBounds(new Rectangle(284, 63, 230, 25));
    TDDLinkMain.setBorder(titledBorder10);
    TDDLinkMain.setBounds(new Rectangle(19, 17, 530, 211));
    TDDLinkMain.setLayout(null);
    TDDLinkInvoc.setText("Invocation Links");
    TDDLinkInvoc.setBounds(new Rectangle(194, 59, 125, 25));
    TDDLinkInvoc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkInvoc_actionPerformed(e);
      }
    });
    TDDLinkInsrEv.setText("Instrument Event Links");
    TDDLinkInsrEv.setBounds(new Rectangle(194, 91, 151, 25));
    TDDLinkInsrEv.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkInsrEv_actionPerformed(e);
      }
    });
    TDDLinkCondition.setText("Condition Links");
    TDDLinkCondition.setBounds(new Rectangle(194, 123, 132, 25));
    TDDLinkCondition.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkCondition_actionPerformed(e);
      }
    });
    TDDLinkInstr.setText("Instrument Links");
    TDDLinkInstr.setBounds(new Rectangle(14, 59, 173, 25));
    TDDLinkInstr.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkInstr_actionPerformed(e);
      }
    });
    TDDLinkEvent.setText("Event Links");
    TDDLinkEvent.setBounds(new Rectangle(14, 155, 173, 25));
    TDDLinkEvent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkEvent_actionPerformed(e);
      }
    });
    TDDLinkRes.setText("Result/Consumption Links");
    TDDLinkRes.setBounds(new Rectangle(14, 91, 173, 25));
    TDDLinkRes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkRes_actionPerformed(e);
      }
    });
    TDDLinkEffect.setText("Effect Links");
    TDDLinkEffect.setBounds(new Rectangle(14, 123, 173, 25));
    TDDLinkEffect.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkEffect_actionPerformed(e);
      }
    });
    TDDLinkAgent.setText("Agent Links");
    TDDLinkAgent.setBounds(new Rectangle(14, 27, 173, 25));
    TDDLinkAgent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkAgent_actionPerformed(e);
      }
    });
    TDDLinkExeption.setText("Exception Links");
    TDDLinkExeption.setBounds(new Rectangle(194, 27, 116, 25));
    TDDLinkExeption.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TDDLinkExeption_actionPerformed(e);
      }
    });
    TGIIssues.setBorder(BorderFactory.createEtchedBorder());
    TGIIssues.setText("Open Issues");
    TGIIssues.setBounds(new Rectangle(249, 115, 96, 25));
    TGIInputs.setBorder(BorderFactory.createEtchedBorder());
    TGIInputs.setText("Inputs, Processing functionality and Outputs");
    TGIInputs.setBounds(new Rectangle(249, 86, 269, 25));
    TGIHard.setBorder(BorderFactory.createEtchedBorder());
    TGIHard.setText("Hardware and Software Requirements");
    TGIHard.setBounds(new Rectangle(249, 171, 236, 25));
    TGIProblems.setBorder(BorderFactory.createEtchedBorder());
    TGIProblems.setText("Problems");
    TGIProblems.setBounds(new Rectangle(249, 143, 79, 25));
    TGIBusiness.setBorder(BorderFactory.createEtchedBorder());
    TGIBusiness.setText("Business or Program Constraints");
    TGIBusiness.setBounds(new Rectangle(249, 58, 210, 25));
    TGIOper.setBorder(BorderFactory.createEtchedBorder());
    TGIOper.setText("Operation and Maintenance");
    TGIOper.setBounds(new Rectangle(249, 30, 176, 25));
    TOPLUntilText.setBounds(new Rectangle(131, 58, 39, 25));
    TOPLUntilText.setVisible(false);
    TOPDUntilText.setVisible(false);
    TOPDUntilText.setBounds(new Rectangle(110, 65, 38, 25));
    TOPLUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPLUntil_actionPerformed(e);
      }
    });
    TOPLUntil.setBounds(new Rectangle(14, 58, 89, 25));
    TOPLUntil.setText("Until Level");
    TOPLAll.setBounds(new Rectangle(14, 27, 89, 25));
    TOPLAll.setText("All Levels");
    TOPLAll.setActionCommand("");
    TOPLAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPLAll_actionPerformed(e);
      }
    });
    TOPLNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPLNone_actionPerformed(e);
      }
    });
    TOPLNone.setBounds(new Rectangle(14, 121, 89, 25));
    TOPLNone.setText("None");
    TOPLByName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TOPLByName_actionPerformed(e);
      }
    });
    TOPLByName.setBounds(new Rectangle(14, 90, 89, 25));
    TOPLByName.setText("By Name");
    TOPLandOPD.setText("According to OPD");
    TOPLandOPD.setBounds(new Rectangle(14, 152, 128, 25));
    TOPLandOPD.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLandTOPD_actionPerformed(e);
      }
    });
    TDDLinkPropCond.setText("Condition");
    TDDLinkPropCond.setBounds(new Rectangle(20, 27, 78, 25));
    TDDLinkProp.setBorder(titledBorder9);
    TDDLinkProp.setBounds(new Rectangle(377, 23, 130, 137));
    TDDLinkProp.setLayout(null);
    TDDLinkProp.setVisible(false);
    TDDLinkPropPath.setText("Path");
    TDDLinkPropPath.setBounds(new Rectangle(20, 59, 50, 25));
    TDDLinkPropReacTime.setText("Reaction time");
    TDDLinkPropReacTime.setBounds(new Rectangle(20, 91, 101, 25));
    TSaveAs.setBounds(new Rectangle(297, 1, 127, 27));
    TSaveAs.setBorder(BorderFactory.createEtchedBorder());
    TSaveAs.setText("Save");
    TSaveAs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TSaveAs_actionPerformed(e);
      }
    });
    TDDObjStateTime.setText("Activation Time");
    TDDObjStateTime.setBounds(new Rectangle(16, 95, 111, 25));
    TNamePanel.setBounds(new Rectangle(0, 11, 573, 29));
    TNamePanel.setLayout(null);
    TNameLable.setText("Template Name");
    TNameLable.setBounds(new Rectangle(14, 6, 113, 17));
    TNameText.setBounds(new Rectangle(124, 4, 154, 21));
    SelAll.setBounds(new Rectangle(293, 203, 114, 27));
    SelAll.setText("Select All");
    SelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SelAll_actionPerformed(e);
      }
    });
    UnSelAll.setBounds(new Rectangle(413, 203, 114, 27));
    UnSelAll.setText("Unselect All");
    UnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UnSelAll_actionPerformed(e);
      }
    });
    ObjSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ObjSelAll_actionPerformed(e);
      }
    });
    ObjSelAll.setText("Select All");
    ObjSelAll.setBounds(new Rectangle(289, 176, 114, 27));
    ObjUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ObjUnSelAll_actionPerformed(e);
      }
    });
    ObjUnSelAll.setText("Unselect All");
    ObjUnSelAll.setBounds(new Rectangle(409, 176, 114, 27));
    ProcUnSelAll.setBounds(new Rectangle(409, 175, 114, 27));
    ProcUnSelAll.setText("Unselect All");
    ProcUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ProcUnSelAll_actionPerformed(e);
      }
    });
    ProcSelAll.setBounds(new Rectangle(289, 175, 114, 27));
    ProcSelAll.setText("Select All");
    ProcSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ProcSelAll_actionPerformed(e);
      }
    });
    LinkUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LinkUnSelAll_actionPerformed(e);
      }
    });
    LinkUnSelAll.setText("Unselect All");
    LinkUnSelAll.setBounds(new Rectangle(405, 173, 114, 27));
    LinkSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LinkSelAll_actionPerformed(e);
      }
    });
    LinkSelAll.setText("Select All");
    LinkSelAll.setBounds(new Rectangle(285, 173, 114, 27));
    RelSelAll.setBounds(new Rectangle(288, 175, 114, 27));
    RelSelAll.setText("Select All");
    RelSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        RelSelAll_actionPerformed(e);
      }
    });
    RelUnSelAll.setBounds(new Rectangle(408, 175, 114, 27));
    RelUnSelAll.setText("Unselect All");
    RelUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        RelUnSelAll_actionPerformed(e);
      }
    });
    TButtonsPanel.add(TCancel, null);
    TButtonsPanel.add(TSaveAs, null);
    TDDLinkProp.add(TDDLinkPropCond, null);
    TDDLinkProp.add(TDDLinkPropPath, null);
    TDDLinkProp.add(TDDLinkPropReacTime, null);
    TDDLinkMain.add(LinkUnSelAll, null);
    TDDLinkMain.add(LinkSelAll, null);
    TDDRelations.add(TDDRelMain, null);
    TDDLinks.add(TDDLinkMain, null);
    TDDLinkMain.add(TDDLinkAgent, null);
    TDDLinkMain.add(TDDLinkInstr, null);
    TDDLinkMain.add(TDDLinkRes, null);
    TDDLinkMain.add(TDDLinkEffect, null);
    TDDLinkMain.add(TDDLinkEvent, null);
    TDDLinkMain.add(TDDLinkExeption, null);
    TDDLinkMain.add(TDDLinkInvoc, null);
    TDDLinkMain.add(TDDLinkInsrEv, null);
    TDDLinkMain.add(TDDLinkCondition, null);
    TDDLinkMain.add(TDDLinkProp, null);
    TDDRelMain.add(TDDRelUni, null);
    TDDRelMain.add(TDDRelBi, null);
    TDDRelMain.add(TDDRelAggreg, null);
    TDDRelMain.add(TDDRelFeature, null);
    TDDRelMain.add(TDDRelGeneral, null);
    TDDRelMain.add(TDDRelClassif, null);
    TDDRelMain.add(RelUnSelAll, null);
    TDDRelMain.add(RelSelAll, null);
    TDDProcesses.add(TDDProcMain, null);
    TDDProcMain.add(TDDProcDescr, null);
    TDDProcMain.add(TDDProcEssence, null);
    TDDProcMain.add(TDDProcScope, null);
    TDDProcMain.add(TDDProcOrigin, null);
    TDDProcMain.add(TDDProcActTime, null);
    TDDProcMain.add(TDDProcBody, null);
    TDDProcMain.add(ProcUnSelAll, null);
    TDDProcMain.add(ProcSelAll, null);
    TTPDD.add(TDDObjects, "Objects");
    TTPDD.add(TDDProcesses, "Processes");
    TTPDD.add(TDDLinks, "Links");
    TTPDD.add(TDDRelations, "Relations");
    TDDObjects.add(TDDObjMain, null);
    TDDObjState.add(TDDObjStateInitial, null);
    TDDObjState.add(TDDObjStateDescr, null);
    TDDObjState.add(TDDObjStateTime, null);
    TDDObjMain.add(ObjUnSelAll, null);
    TDDObjMain.add(ObjSelAll, null);
    TDDObjMain.add(TDDObjDesc, null);
    TDDObjMain.add(TDDObjOrigin, null);
    TDDObjMain.add(TDDObjScope, null);
    TDDObjMain.add(TDDObjEssence, null);
    TDDObjMain.add(TDDObjIndex, null);
    TDDObjMain.add(TDDObjInitVal, null);
    TDDObjMain.add(TDDObjType, null);
    TDDObjMain.add(TDDObjStates, null);
    TDDObjMain.add(TDDObjState, null);
    TOPL.add(TOPLUntilText, null);
    TOPL.add(TOPLAll, null);
    TOPL.add(TOPLUntil, null);
    TOPL.add(TOPLByName, null);
    TOPL.add(TOPLNone, null);
    TOPL.add(TOPLandOPD, null);
    TOPDOPL.add(TOPD, null);
    TOPDOPL.add(TOPL, null);
    TOPD.add(TOPDUntil, null);
    TOPD.add(TOPDAll, null);
    TOPD.add(TOPDByName, null);
    TOPD.add(TOPDNone, null);
    TOPD.add(TOPDUntilText, null);
   TDD.add(TTPDD, null);
    TGILeft.add(TGICurrent, null);
    TGILeft.add(TGIOper, null);
    TGILeft.add(TGIClient, null);
    TGILeft.add(TGIOverview, null);
    TGILeft.add(TGIUsers, null);
    TGILeft.add(TGIGoals, null);
    TGILeft.add(TGIFuture, null);
    TGILeft.add(TGIBusiness, null);
    TGILeft.add(TGIInputs, null);
    TGILeft.add(TGIIssues, null);
    TGILeft.add(TGIProblems, null);
    TGILeft.add(TGIHard, null);
    TGILeft.add(UnSelAll, null);
    TGILeft.add(SelAll, null);
    contentPane.add(TNamePanel, null);
    TGI.add(TGILeft, null);
    TNamePanel.add(TNameText, null);
    TNamePanel.add(TNameLable, null);
    contentPane.add(TButtonsPanel, null);
    contentPane.add(TTPMain, null);
    BG_TOPD.add(TOPDAll);
    BG_TOPD.add(TOPDUntil);
    BG_TOPD.add(TOPDByName);
    BG_TOPD.add(TOPDNone);
    BG_TOPD.add(TOPDUntil);
    BG_TOPD.add(TOPDAll);
    BG_TOPD.add(TOPDByName);
    BG_TOPD.add(TOPDNone);
    BG_TOPL.add(TOPLAll);
    BG_TOPL.add(TOPLUntil);
    BG_TOPL.add(TOPLByName);
    BG_TOPL.add(TOPLNone);
    BG_TOPL.add(TOPLandOPD);
    BG_TOPL.add(TOPLAll);
    BG_TOPL.add(TOPLUntil);
    BG_TOPL.add(TOPLByName);
    BG_TOPL.add(TOPLNone);
    BG_TOPL.add(TOPLandOPD);
    TTPMain.add(TGI, "General Info");
    TTPMain.add(TOPDOPL, "OPD & OPL");
    TTPMain.add(TDD,  "Element Dictionary");
    TDDObjState.setVisible(false);
    TOPDAll.setSelected(true);
    TOPLandOPD.setSelected(true);
    this.setModal(true);
    //insert the name of the editted template in the field
    if (filename.compareTo("-1")!=0){
      StringTokenizer st = new StringTokenizer(filename, ".", false);
      TNameText.setText(st.nextToken());
     }
    else
        TNameText.setText("");
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
    this.dispose();
    }
  }

  /**
   * Checks if the state properties panel should be visible.
   * @param e ActionEvent on TDDObjStates
   */
  void TDDObjStates_actionPerformed(ActionEvent e) {
    if (TDDObjStates.isSelected())
      TDDObjState.setVisible(true);
    if (TDDObjStates.isSelected()==false)
      TDDObjState.setVisible(false);
  }
  /**
   * If All OPD levels is selected hides the text field.
   * @param e ActionEvent on TOPDAll
   */
  void TOPDAll_actionPerformed(ActionEvent e) {
    if (TOPDAll.isSelected())
          TOPDUntilText.setVisible(false);
  }
  /**
     * If None OPD levels is selected hides the text field.
     * @param e ActionEvent on TOPDNone
     */
  void TOPDNone_actionPerformed(ActionEvent e) {
    if (TOPDNone.isSelected())
         TOPDUntilText.setVisible(false);
   }
   /**
    * If OPD until level is selected shows the text field.
    * @param e ActionEvent on TOPDUntil
    */
  void TOPDUntil_actionPerformed(ActionEvent e) {
        if (TOPDUntil.isSelected())
          TOPDUntilText.setVisible(true);
  }
  /**
   * If OPD by name is selected hides the text field.
   * @param e ActionEvent on TOPDByName
  */
  void TOPDByName_actionPerformed(ActionEvent e) {
      if (TOPDByName.isSelected())
           TOPDUntilText.setVisible(false);
  }
  /**
   * If OPL until level is selected shows the text field.
   * @param e ActionEvent on TOPLUntil
  */
 void TOPLUntil_actionPerformed(ActionEvent e) {
  if (TOPLUntil.isSelected())
      TOPLUntilText.setVisible(true);
  }
  /**
   * If All OPL levels is selected hides the text field.
   * @param e ActionEvent on TOPLAll
  */
  void TOPLAll_actionPerformed(ActionEvent e) {
  if (TOPLAll.isSelected())
    TOPLUntilText.setVisible(false);
  }
  /**
   * If None OPL levels is selected hides the text field.
   * @param e ActionEvent on TOPLNone
  */
 void TOPLNone_actionPerformed(ActionEvent e) {
  if (TOPLNone.isSelected())
      TOPLUntilText.setVisible(false);
  }
  /**
   * If OPL by name is selected hides the text field.
   * @param e ActionEvent on TOPLByName
  */

  void TOPLByName_actionPerformed(ActionEvent e) {
  if (TOPLByName.isSelected())
      TOPLUntilText.setVisible(false);
  }
  /**
   * If OPL according to OPD is selected hides the text field.
   * @param e ActionEvent on TOPLandOPD
  */

  void OPLandTOPD_actionPerformed(ActionEvent e) {
  if (TOPLandOPD.isSelected())
      TOPLUntilText.setVisible(false);
  }
  /**
   * For any change made to the Agent Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Agent Link
   */
  void TDDLinkAgent_actionPerformed(ActionEvent e) {
  if (TDDLinkAgent.isSelected())
    TDDLinkProp.setVisible(true);
  if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
    TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Instr Link,
   * checks if link properties panel should be visible.
   * and sets it respectively
   * @param e ActionEvent on Instr Link
   */
  void TDDLinkInstr_actionPerformed(ActionEvent e) {
    if (TDDLinkInstr.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Res Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Res Link
   */
  void TDDLinkRes_actionPerformed(ActionEvent e) {
    if (TDDLinkRes.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Effect Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Effect Link
   */
  void TDDLinkEffect_actionPerformed(ActionEvent e) {
    if (TDDLinkEffect.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Event Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Event Link
   */
  void TDDLinkEvent_actionPerformed(ActionEvent e) {
    if (TDDLinkEvent.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Exeption Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Exeption Link
   */
  void TDDLinkExeption_actionPerformed(ActionEvent e) {
    if (TDDLinkExeption.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Invoc Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Invoc Link
   */
  void TDDLinkInvoc_actionPerformed(ActionEvent e) {
    if (TDDLinkInvoc.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the InsrEv Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on InsrEv Link
   */
  void TDDLinkInsrEv_actionPerformed(ActionEvent e) {
    if (TDDLinkInsrEv.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }
  /**
   * For any change made to the Condition Link,
   * checks if link properties panel should be visible
   * and sets it respectively.
   * @param e ActionEvent on Condition Link
   */
 void TDDLinkCondition_actionPerformed(ActionEvent e) {
    if (TDDLinkCondition.isSelected())
      TDDLinkProp.setVisible(true);
    if ((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
  }

  /**
   * When Save button is pressed, checks the input and saves as template.
   * @param e ActionEvent on TSaveAs button
   */
  void TSaveAs_actionPerformed(ActionEvent e) {
  //check if the level is a valid integer
    try {
    if (TOPDUntil.isSelected()){
        int opd_text = Integer.parseInt(TOPDUntilText.getText());
	if (opd_text<1)
		opd_text = Integer.parseInt("jdkf");
    }
    if (TOPLUntil.isSelected()){
        int opl_text = Integer.parseInt(TOPLUntilText.getText());
	if (opl_text<1)
		opl_text = Integer.parseInt("jdkf");
    }
    try{
      String filename = TNameText.getText();
      //checks if a name for the template was inserted
      if (filename.compareTo("")==0){
        JOptionPane NoName = new JOptionPane();
        JOptionPane.showMessageDialog(this, "Please insert a name for the template.", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
      //if there is a name
      else {
      //checks if there already is a template with that name
      File myDir = new File("Templates");
      int flag = 0; //0 - no such file yet
      int i=0;
      if (myDir.exists()) {
        File[] contents = myDir.listFiles();
        while (i < contents.length) {
          if (contents[i].getName().compareTo(filename)==0)
            flag = 1;
          i++;
        }
      }
      //if there is a template with this name
      if (flag == 1){
        //ask if interested in overwriting
        JOptionPane SaveTemp = new JOptionPane();
        int retval=JOptionPane.showConfirmDialog( this, "A template with this name already exists. Do you want to overwrite it?", "Overwrite Template", JOptionPane.YES_NO_OPTION);
        //overwriting
        if (retval== JOptionPane.YES_OPTION){
          Document doc = DocCreate();//create a document
          doc.CreateTemplate(mySys,filename);//save to template file
          dad.dispose();//close previous screen
          this.dispose();//close this screen
        }
      }
      //if there is no such file yet
      else {
        Document doc = DocCreate();//create a document
        doc.CreateTemplate(mySys,filename);//save to template file
        dad.dispose();//close the previous screen
        this.dispose();//clise this screen
      }
     }
    }
    catch(IOException er){
      System.out.println("Problem saving the template");
    }
  }
  catch (NumberFormatException ne) {
    //illigal level number
    JOptionPane inputEr = new JOptionPane();
    JOptionPane.showMessageDialog(this, "Illegal input: level is not valid. Please insert an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  }
  /**
   * Takes the selected fields from the screen, creates a new document
   * and marks the user selection in the document.
   * @return doc of type Document, with the relevent choices
   */

  Document DocCreate() {
    Document doc = new Document();
    boolean[] obj_array =new boolean[11];
    boolean[] proc_array =new boolean[6];
    boolean[] rel_array =new boolean[6];
    boolean[] link_array =new boolean[12];
    boolean[] opd_array  =new boolean[7];
    boolean[] gi_array = new boolean[12];


    obj_array[0] = TDDObjType.isSelected();
    obj_array[1] = TDDObjDesc.isSelected();
    obj_array[2] = TDDObjInitVal.isSelected();
    obj_array[3] = TDDObjEssence.isSelected();
    obj_array[4] = TDDObjIndex.isSelected();
    obj_array[5] = TDDObjScope.isSelected();
    obj_array[6] = TDDObjOrigin.isSelected();
    obj_array[7] = TDDObjStates.isSelected();
    obj_array[8] = TDDObjStateDescr.isSelected();
    obj_array[9] = TDDObjStateInitial.isSelected();
    obj_array[10] = TDDObjStateTime.isSelected();

    proc_array[0] = TDDProcDescr.isSelected();
    proc_array[1] = TDDProcEssence.isSelected();
    proc_array[2] = TDDProcOrigin.isSelected();
    proc_array[3] = TDDProcScope.isSelected();
    proc_array[4] = TDDProcBody.isSelected();
    proc_array[5] = TDDProcActTime.isSelected();

    rel_array[0] = TDDRelAggreg.isSelected();
    rel_array[1] = TDDRelClassif.isSelected();
    rel_array[2] = TDDRelFeature.isSelected();
    rel_array[3] = TDDRelGeneral.isSelected();
    rel_array[4] = TDDRelBi.isSelected();
    rel_array[5] = TDDRelUni.isSelected();

    link_array[0] = TDDLinkAgent.isSelected();
    link_array[1] = TDDLinkCondition.isSelected();
    link_array[2] = TDDLinkEffect.isSelected();
    link_array[3] = TDDLinkEvent.isSelected();
    link_array[4] = TDDLinkExeption.isSelected();
    link_array[5] = TDDLinkInsrEv.isSelected();
    link_array[6] = TDDLinkInvoc.isSelected();
    link_array[7] = TDDLinkRes.isSelected();
    link_array[8] = TDDLinkInstr.isSelected();
    link_array[9] = TDDLinkPropCond.isSelected();
    link_array[10] = TDDLinkPropPath.isSelected();
    link_array[11] = TDDLinkPropReacTime.isSelected();

    opd_array[0] = TOPDAll.isSelected();
    opd_array[1] = TOPDByName.isSelected();
    opd_array[2] = TOPDNone.isSelected();
    opd_array[3] = TOPLAll.isSelected();
    opd_array[4] = TOPLByName.isSelected();
    opd_array[5] = TOPLNone.isSelected();
    opd_array[6] = TOPLandOPD.isSelected();

    gi_array[0] = TGIClient.isSelected();
    gi_array[1] = TGIOverview.isSelected();
    gi_array[2] = TGICurrent.isSelected();
    gi_array[3] = TGIGoals.isSelected();
    gi_array[4] = TGIBusiness.isSelected();
    gi_array[5] = TGIFuture.isSelected();
    gi_array[6] = TGIHard.isSelected();
    gi_array[7] = TGIInputs.isSelected();
    gi_array[8] = TGIIssues.isSelected();
    gi_array[9] = TGIProblems.isSelected();
    gi_array[10] = TGIUsers.isSelected();
    gi_array[11] = TGIOper.isSelected();

//untill OPD level
    String OPDUntilT,OPLUntilT;
    if (TOPDUntil.isSelected())
      OPDUntilT=TOPDUntilText.getText();
    else
      OPDUntilT="-1";
//untill OPL level
    if (TOPLUntil.isSelected())
      OPLUntilT=TOPLUntilText.getText();
    else
      OPLUntilT="-1";
//insert arrays to document
    doc.DocInfo.Data.Proc.ProcInit(proc_array);
    doc.DocInfo.Data.Obj.ObjInit(obj_array);
    doc.DocInfo.Data.Link.LinkInit(link_array);
    doc.DocInfo.Data.Rel.RelInit(rel_array);
    doc.DocInfo.GI.GIInit(gi_array);
    doc.DocInfo.opdopl.OPDOPLInit(opd_array,OPDUntilT,OPLUntilT);

    return doc;
  }
  /**
    * Cancels editing template.
    * @param e ActionEvent on Cancel button
    */

  void TCancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }
  /**
   * Shows the choices selected in the document d on the screen
   * @param d Object of type Document
   */

  void TempToScreen(Document d){

    Document doc=d;
    //for fields of object, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect


    TDDObjType.setSelected(doc.DocInfo.Data.Obj.getType());
    TDDObjDesc.setSelected(doc.DocInfo.Data.Obj.getDesc());
    TDDObjInitVal.setSelected(doc.DocInfo.Data.Obj.getInValue());
    TDDObjEssence.setSelected(doc.DocInfo.Data.Obj.getEssence());
    TDDObjIndex.setSelected(doc.DocInfo.Data.Obj.getIndex());
    TDDObjScope.setSelected(doc.DocInfo.Data.Obj.getScope());
    TDDObjOrigin.setSelected(doc.DocInfo.Data.Obj.getOrigin());
    TDDObjStates.setSelected(doc.DocInfo.Data.Obj.getStates());
    TDDObjStateDescr.setSelected(doc.DocInfo.Data.Obj.getStateDesc());
    TDDObjStateInitial.setSelected(doc.DocInfo.Data.Obj.getStateInitial());
    TDDObjStateTime.setSelected(doc.DocInfo.Data.Obj.getStateTime());
    //in case the states field for objects is selected ->
    //set visible the pannel with state properties
    if (TDDObjStates.isSelected())
      TDDObjState.setVisible(true);
    else
      TDDObjState.setVisible(false);
    //for fields of process, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    TDDProcDescr.setSelected(doc.DocInfo.Data.Proc.getDesc());
    TDDProcEssence.setSelected(doc.DocInfo.Data.Proc.getEssence());
    TDDProcOrigin.setSelected(doc.DocInfo.Data.Proc.getOrigin());
    TDDProcScope.setSelected(doc.DocInfo.Data.Proc.getScope());
    TDDProcBody.setSelected(doc.DocInfo.Data.Proc.getBody());
    TDDProcActTime.setSelected(doc.DocInfo.Data.Proc.getActTime());

    //for fields of relations, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    TDDRelAggreg.setSelected(doc.DocInfo.Data.Rel.getAgPar());
    TDDRelClassif.setSelected(doc.DocInfo.Data.Rel.getClassInst());
    TDDRelFeature.setSelected(doc.DocInfo.Data.Rel.getFeChar());
    TDDRelGeneral.setSelected(doc.DocInfo.Data.Rel.getGenSpec());
    TDDRelBi.setSelected(doc.DocInfo.Data.Rel.getBiDir());
    TDDRelUni.setSelected(doc.DocInfo.Data.Rel.getUniDir());

    //for fields of links, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    TDDLinkAgent.setSelected(doc.DocInfo.Data.Link.getAgent());
    TDDLinkCondition.setSelected(doc.DocInfo.Data.Link.getCondition());
    TDDLinkEffect.setSelected(doc.DocInfo.Data.Link.getEffect());
    TDDLinkEvent.setSelected(doc.DocInfo.Data.Link.getEvent());
    TDDLinkExeption.setSelected(doc.DocInfo.Data.Link.getException());
    TDDLinkInsrEv.setSelected(doc.DocInfo.Data.Link.getInstEvent());
    TDDLinkInvoc.setSelected(doc.DocInfo.Data.Link.getInvocation());
    TDDLinkRes.setSelected(doc.DocInfo.Data.Link.getResCons());
    TDDLinkInstr.setSelected(doc.DocInfo.Data.Link.getInstrument());
    TDDLinkPropCond.setSelected(doc.DocInfo.Data.Link.getCond());
    TDDLinkPropPath.setSelected(doc.DocInfo.Data.Link.getPath());
    TDDLinkPropReacTime.setSelected(doc.DocInfo.Data.Link.getReactTime());
    //if at least one of the link types is selected - >
    //set the panel with link properties visible, else hide it
    if((TDDLinkAgent.isSelected()==false)&&(TDDLinkInstr.isSelected()==false)&&(TDDLinkRes.isSelected()==false)&&(TDDLinkEffect.isSelected()==false)&&(TDDLinkEvent.isSelected()==false)&&(TDDLinkExeption.isSelected()==false)&&(TDDLinkInvoc.isSelected()==false)&&(TDDLinkInsrEv.isSelected()==false)&&(TDDLinkCondition.isSelected()==false))
      TDDLinkProp.setVisible(false);
    else
      TDDLinkProp.setVisible(true);

    //for fields of OPD and OPL, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    TOPDAll.setSelected(doc.DocInfo.opdopl.getOPDAll());
    TOPDByName.setSelected(doc.DocInfo.opdopl.getOPDByName());
    TOPDNone.setSelected(doc.DocInfo.opdopl.getOPDNone());
    TOPLAll.setSelected(doc.DocInfo.opdopl.getOPLAll());
    TOPLByName.setSelected(doc.DocInfo.opdopl.getOPLByName());
    TOPLNone.setSelected(doc.DocInfo.opdopl.getOPLNone());
    TOPLandOPD.setSelected(doc.DocInfo.opdopl.getOPLAccording());

    //for fields of General Info, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    TGIClient.setSelected(doc.DocInfo.GI.getClient());
    TGIOverview.setSelected(doc.DocInfo.GI.getOverview());
    TGICurrent.setSelected(doc.DocInfo.GI.getCurrent());
    TGIGoals.setSelected(doc.DocInfo.GI.getGoals());
    TGIBusiness.setSelected(doc.DocInfo.GI.getBusiness());
    TGIFuture.setSelected(doc.DocInfo.GI.getFuture());
    TGIHard.setSelected(doc.DocInfo.GI.getHard());
    TGIInputs.setSelected(doc.DocInfo.GI.getInputs());
    TGIIssues.setSelected(doc.DocInfo.GI.getIssues());
    TGIProblems.setSelected(doc.DocInfo.GI.getProblems());
    TGIUsers.setSelected(doc.DocInfo.GI.getUsers());
    TGIOper.setSelected(doc.DocInfo.GI.getOper());
    //if OPD untill was not selected in the document ->
    //hide text field of the level, unselect the field
    if(doc.DocInfo.opdopl.getOPDUntil().compareTo("-1")==0){
      TOPDUntil.setSelected(false);
      TOPDUntilText.setVisible(false);
    }
    //else, if OPD untill is selected ->
    //select the relevant field, show the text field for the level,
    //and put the level from the document in the text field
    else{
      TOPDUntil.setSelected(true);
      TOPDUntilText.setVisible(true);
      TOPDUntilText.setText(doc.DocInfo.opdopl.getOPDUntil());
    }
    //if OPL untill was not selected in the document ->
    //hide text field of the level, unselect the field
    if(doc.DocInfo.opdopl.getOPLUntil().compareTo("-1")==0){
      TOPLUntil.setSelected(false);
      TOPLUntilText.setVisible(false);
    }
    //else, if OPL untill is selected ->
    //select the relevant field, show the text field for the level,
    //and put the level from the document in the text field
    else{
      TOPLUntil.setSelected(true);
      TOPLUntilText.setVisible(true);
      TOPLUntilText.setText(doc.DocInfo.opdopl.getOPLUntil());
    }

  }
  /**
   * When the "Select All" button in the General Info panel is pressed
   * marks all the related fields as selected.
   * @param e ActionEvent on select all button for genInfo
   */
  void SelAll_actionPerformed(ActionEvent e) {
     TGIBusiness.setSelected(true);
      TGIClient.setSelected(true);
      TGICurrent.setSelected(true);
      TGIFuture.setSelected(true);
      TGIGoals.setSelected(true);
      TGIHard.setSelected(true);
      TGIInputs.setSelected(true);
      TGIIssues.setSelected(true);
      TGIOper.setSelected(true);
      TGIOverview.setSelected(true);
      TGIProblems.setSelected(true);
      TGIUsers.setSelected(true);
  }
  /**
   * When the "Unselect All" button in the General Info panel is pressed
   * marks all the related fields as unselected.
   * @param e ActionEvent on unselect all button for genInfo
   */
  void UnSelAll_actionPerformed(ActionEvent e) {
      TGIBusiness.setSelected(false);
      TGIClient.setSelected(false);
      TGICurrent.setSelected(false);
      TGIFuture.setSelected(false);
      TGIGoals.setSelected(false);
      TGIHard.setSelected(false);
      TGIInputs.setSelected(false);
      TGIIssues.setSelected(false);
      TGIOper.setSelected(false);
      TGIOverview.setSelected(false);
      TGIProblems.setSelected(false);
      TGIUsers.setSelected(false);
  }
  /**
   *  When the "Select All" button in the Objects panel is pressed
   *  marks all the object related fields as selected.
   * @param e ActionEvent on select all button for objects
   */
  void ObjSelAll_actionPerformed(ActionEvent e) {
     TDDObjDesc.setSelected(true);
      TDDObjEssence.setSelected(true);
      TDDObjIndex.setSelected(true);
      TDDObjInitVal.setSelected(true);
      TDDObjOrigin.setSelected(true);
      TDDObjScope.setSelected(true);
      TDDObjStates.setSelected(true);
      TDDObjStateDescr.setSelected(true);
      TDDObjStateInitial.setSelected(true);
      TDDObjStateTime.setSelected(true);
      TDDObjType.setSelected(true);
      TDDObjState.setVisible(true);
  }
  /**
   *  When the "Unselect All" button in the Objects panel is pressed
   *  marks all the object related fields as unselected.
   * @param e ActionEvent on unselect all button for objects
   */
  void ObjUnSelAll_actionPerformed(ActionEvent e) {
     TDDObjDesc.setSelected(false);
      TDDObjEssence.setSelected(false);
      TDDObjIndex.setSelected(false);
      TDDObjInitVal.setSelected(false);
      TDDObjOrigin.setSelected(false);
      TDDObjScope.setSelected(false);
      TDDObjStates.setSelected(false);
      TDDObjStateDescr.setSelected(false);
      TDDObjStateInitial.setSelected(false);
      TDDObjStateTime.setSelected(false);
      TDDObjType.setSelected(false);
      TDDObjState.setVisible(false);
  }
  /**
   *  When the "Unselect All" button in the Processes panel is pressed
   *  marks all the process related fields as unselected.
   * @param e ActionEvent on unselect all button for processes
   */
  void ProcUnSelAll_actionPerformed(ActionEvent e) {
     TDDProcActTime.setSelected(false);
      TDDProcBody.setSelected(false);
      TDDProcDescr.setSelected(false);
      TDDProcEssence.setSelected(false);
      TDDProcOrigin.setSelected(false);
      TDDProcScope.setSelected(false);
  }
  /**
   *  When the "Select All" button in the Processes panel is pressed
   *  marks all the process related fields as selected.
   * @param e ActionEvent on select all button for processes
   */
  void ProcSelAll_actionPerformed(ActionEvent e) {
      TDDProcActTime.setSelected(true);
      TDDProcBody.setSelected(true);
      TDDProcDescr.setSelected(true);
      TDDProcEssence.setSelected(true);
      TDDProcOrigin.setSelected(true);
      TDDProcScope.setSelected(true);
  }
  /**
   *  When the "Unselect All" button in the Links panel is pressed
   *  marks all the link related fields as unselected.
   * @param e ActionEvent on unselect all button for links
   */
  void LinkUnSelAll_actionPerformed(ActionEvent e) {
     TDDLinkAgent.setSelected(false);
      TDDLinkCondition.setSelected(false);
      TDDLinkEffect.setSelected(false);
      TDDLinkEvent.setSelected(false);
      TDDLinkExeption.setSelected(false);
      TDDLinkInstr.setSelected(false);
      TDDLinkInsrEv.setSelected(false);
      TDDLinkInvoc.setSelected(false);
      TDDLinkPropCond.setSelected(false);
      TDDLinkPropPath.setSelected(false);
      TDDLinkPropReacTime.setSelected(false);
      TDDLinkRes.setSelected(false);
      TDDLinkProp.setVisible(false);
  }
  /**
   *  When the "Select All" button in the Links panel is pressed
   *  marks all the link related fields as selected.
   * @param e ActionEvent on select all button for links
   */
  void LinkSelAll_actionPerformed(ActionEvent e) {
      TDDLinkAgent.setSelected(true);
      TDDLinkCondition.setSelected(true);
      TDDLinkEffect.setSelected(true);
      TDDLinkEvent.setSelected(true);
      TDDLinkExeption.setSelected(true);
      TDDLinkInstr.setSelected(true);
      TDDLinkInsrEv.setSelected(true);
      TDDLinkInvoc.setSelected(true);
      TDDLinkPropCond.setSelected(true);
      TDDLinkPropPath.setSelected(true);
      TDDLinkPropReacTime.setSelected(true);
      TDDLinkRes.setSelected(true);
      TDDLinkProp.setVisible(true);

  }
  /**
   *  When the "Select All" button in the Relations panel is pressed
   *  marks all the relation related fields as selected.
   * @param e ActionEvent on select all button for relations
   */
  void RelSelAll_actionPerformed(ActionEvent e) {
     TDDRelAggreg.setSelected(true);
      TDDRelBi.setSelected(true);
      TDDRelClassif.setSelected(true);
      TDDRelFeature.setSelected(true);
      TDDRelGeneral.setSelected(true);
      TDDRelUni.setSelected(true);
  }
  /**
   *  When the "Unselect All" button in the Relations panel is pressed
   *  marks all the relation related fields as unselected.
   * @param e ActionEvent on unselect all button for relations
   */
  void RelUnSelAll_actionPerformed(ActionEvent e) {
      TDDRelAggreg.setSelected(false);
      TDDRelBi.setSelected(false);
      TDDRelClassif.setSelected(false);
      TDDRelFeature.setSelected(false);
      TDDRelGeneral.setSelected(false);
      TDDRelUni.setSelected(false);
  }////////////end of TempToScreen

}
