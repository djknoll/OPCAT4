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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPI.ISystem;
import extensionTools.Documents.Doc.Document;

/**
 * DocGen Class extends the standard JDialog, allows user to select
 * the contents of the document he is interested in.
 * All the choices of the user regarding the fields of the General Info, OPD,
 * OPL and Elements Dictionary are done in this screen.
 * Also allows opening one of the templates avaliable or saving the selection
 * as a template.
 * Parallel to the TempEdit class (for templates).
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */
public class DocGen extends JDialog {
  ISystem mySys;
  File myDir = new File("Templates");
  JPanel contentPane;
  JTabbedPane TPMain = new JTabbedPane();
  JPanel GI = new JPanel();
  JPanel OPDOPL = new JPanel();
  JPanel DD = new JPanel();
  TitledBorder titledBorder1;
  JTabbedPane TPDD = new JTabbedPane();
  JPanel DDObjects = new JPanel();
  JPanel DDProcesses = new JPanel();
  JPanel DDRelations = new JPanel();
  JPanel DDLinks = new JPanel();
  JPanel ButtonsPanel = new JPanel();
  JButton Cancel = new JButton();
  JButton Generate = new JButton();
  JPanel GILeft = new JPanel();
  JCheckBox GIClient = new JCheckBox();
  JCheckBox GIOverview = new JCheckBox();
  JCheckBox GIFuture = new JCheckBox();
  JCheckBox GIUsers = new JCheckBox();
  JCheckBox GIGoals = new JCheckBox();
  JCheckBox GICurrent = new JCheckBox();
  JPanel OPL = new JPanel();
  JPanel DDObjMain = new JPanel();
  JCheckBox DDObjOrigin = new JCheckBox();
  JCheckBox DDObjDesc = new JCheckBox();
  JCheckBox DDObjType = new JCheckBox();
  JCheckBox DDObjStates = new JCheckBox();
  JCheckBox DDObjScope = new JCheckBox();
  JCheckBox DDObjEssence = new JCheckBox();
  JPanel OPD = new JPanel();
  JRadioButton OPDByName = new JRadioButton();
  JRadioButton OPDUntil = new JRadioButton();
  JRadioButton OPDAll = new JRadioButton();
  JRadioButton OPDNone = new JRadioButton();
  ButtonGroup BG_OPD = new ButtonGroup();
  ButtonGroup BG_OPL = new ButtonGroup();
  JCheckBox DDObjIndex = new JCheckBox();
  JCheckBox DDObjInitVal = new JCheckBox();
  JPanel DDObjState = new JPanel();
  JCheckBox DDObjStateDescr = new JCheckBox();
  JCheckBox DDObjStateInitial = new JCheckBox();
  Border border1;
  TitledBorder titledBorder2;
  Border border2;
  TitledBorder titledBorder3;
  Border border3;
  TitledBorder titledBorder4;
  Border border4;
  TitledBorder titledBorder5;
  JPanel DDProcMain = new JPanel();
  Border border5;
  TitledBorder titledBorder6;
  JCheckBox DDProcEssence = new JCheckBox();
  JCheckBox DDProcOrigin = new JCheckBox();
  JCheckBox DDProcDescr = new JCheckBox();
  JCheckBox DDProcActTime = new JCheckBox();
  JCheckBox DDProcScope = new JCheckBox();
  JCheckBox DDProcBody = new JCheckBox();
  Border border6;
  TitledBorder titledBorder7;
  JPanel DDRelMain = new JPanel();
  Border border7;
  TitledBorder titledBorder8;
  JCheckBox DDRelAggreg = new JCheckBox();
  JCheckBox DDRelFeature = new JCheckBox();
  JCheckBox DDRelGeneral = new JCheckBox();
  JCheckBox DDRelClassif = new JCheckBox();
  JCheckBox DDRelUni = new JCheckBox();
  JCheckBox DDRelBi = new JCheckBox();
  Border border8;
  TitledBorder titledBorder9;
  JPanel DDLinkMain = new JPanel();
  JCheckBox DDLinkInvoc = new JCheckBox();
  JCheckBox DDLinkInstrEv = new JCheckBox();
  JCheckBox DDLinkCondition = new JCheckBox();
  JCheckBox DDLinkInstr = new JCheckBox();
  JCheckBox DDLinkEvent = new JCheckBox();
  JCheckBox DDLinkRes = new JCheckBox();
  JCheckBox DDLinkEffect = new JCheckBox();
  JCheckBox DDLinkAgent = new JCheckBox();
  JCheckBox DDLinkException = new JCheckBox();
  Border border9;
  TitledBorder titledBorder10;
  JCheckBox GIIssues = new JCheckBox();
  JCheckBox GIInputs = new JCheckBox();
  JCheckBox GIHard = new JCheckBox();
  JCheckBox GIProblems = new JCheckBox();
  JCheckBox GIBusiness = new JCheckBox();
  JCheckBox GIOper = new JCheckBox();
  Border border10;
  TitledBorder titledBorder11;
  JTextField OPLUntilText = new JTextField();
  JTextField OPDUntilText = new JTextField();
  JRadioButton OPLUntil = new JRadioButton();
  JRadioButton OPLAll = new JRadioButton();
  JRadioButton OPLNone = new JRadioButton();
  JRadioButton OPLByName = new JRadioButton();
  JRadioButton OPLandOPD = new JRadioButton();
  JCheckBox DDLinkPropCond = new JCheckBox();
  JPanel DDLinkProp = new JPanel();
  JCheckBox DDLinkPropPath = new JCheckBox();
  JCheckBox DDLinkPropReacTime = new JCheckBox();
  JCheckBox DDObjStateTime = new JCheckBox();
  JPanel DFromTempPanel = new JPanel();
  JRadioButton DTempRadio = new JRadioButton();
  JComboBox DTCombo = new JComboBox();
  JPanel DTSavePanel = new JPanel();
  JButton MakeTemp = new JButton();
  JTextField DTSaveText = new JTextField();
  Border border11;
  TitledBorder titledBorder12;
  Border border12;
  TitledBorder titledBorder13;
  Border border13;
  TitledBorder titledBorder14;
  JButton DTSaveOK = new JButton();
  JButton DTSaveCancel = new JButton();
  Border border14;
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
  JLabel DTSaveL = new JLabel();

/**
 * Construct the frame
 * @param sys of type ISystem
 */
  public DocGen(ISystem sys) {
    super(sys.getMainFrame(), true);
    this.setTitle("Generate a new Document");
    mySys = sys;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }//of try
    catch(Exception e) {
      e.printStackTrace();
    }
    //Esc and Enter Keys active
    KeyListener kListener = new KeyAdapter(){
      public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
          Cancel.doClick();
          return;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
          Generate.doClick();
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
 * Component initialization
 */
  private void jbInit(){
    int counter=0;
    DTCombo.addItem("None");
    if (myDir.exists()) {
      File[] contents = myDir.listFiles();
      while (counter < contents.length) {
        DTCombo.addItem(contents[counter].getName());
        counter++;
      }
    }

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
    border11 = BorderFactory.createLineBorder(Color.white,1);
    titledBorder12 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Save as template");
    border12 = BorderFactory.createEmptyBorder();
    titledBorder13 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Generate Document");
    border13 = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(134, 134, 134),new Color(93, 93, 93));
    titledBorder14 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"From Template");
    border14 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    contentPane.setLayout(null);
    this.setResizable(false);
    this.setSize(new Dimension(580, 477));
    this.setTitle("New Document Generation");
    GI.setLayout(null);
    OPDOPL.setLayout(null);
    DD.setLayout(null);
    TPDD.setBackground(new Color(204, 204, 204));
    TPDD.setBounds(new Rectangle(-5, 0, 566, 264));
    DDObjects.setLayout(null);
    DDProcesses.setLayout(null);
    DDRelations.setLayout(null);
    DDLinks.setLayout(null);
    TPMain.setBackground(new Color(204, 204, 204));
    TPMain.setBounds(new Rectangle(5, 51, 563, 284));
    ButtonsPanel.setBorder(titledBorder13);
    ButtonsPanel.setBounds(new Rectangle(4, 339, 567, 52));
    ButtonsPanel.setLayout(null);
    Cancel.setBounds(new Rectangle(425, 17, 124, 27));
    Cancel.setBorder(BorderFactory.createEtchedBorder());
    Cancel.setToolTipText("");
    Cancel.setText("Cancel");
    Cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Cancel_actionPerformed(e);
      }
    });
    Generate.setBounds(new Rectangle(147, 17, 124, 27));
    Generate.setBorder(BorderFactory.createEtchedBorder());
    Generate.setText("Generate");
    Generate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Generate_actionPerformed(e);

      }
    });
    GILeft.setBorder(titledBorder2);
    GILeft.setBounds(new Rectangle(12, 17, 535, 232));
    GILeft.setLayout(null);
    GIClient.setBorder(BorderFactory.createEtchedBorder());
    GIClient.setText("Client");
    GIClient.setBounds(new Rectangle(9, 30, 90, 25));
    GIOverview.setBorder(null);
    GIOverview.setText("System Overview");
    GIOverview.setBounds(new Rectangle(9, 58, 117, 25));
    GIFuture.setBorder(BorderFactory.createEtchedBorder());
    GIFuture.setText("Future Goals");
    GIFuture.setBounds(new Rectangle(9, 115, 100, 25));
    GIUsers.setBorder(BorderFactory.createEtchedBorder());
    GIUsers.setText("Possible Users for the System");
    GIUsers.setBounds(new Rectangle(9, 171, 199, 25));
    GIGoals.setBorder(BorderFactory.createEtchedBorder());
    GIGoals.setText("Goals and Objectives of the Project");
    GIGoals.setBounds(new Rectangle(9, 143, 219, 25));
    GICurrent.setBorder(BorderFactory.createEtchedBorder());
    GICurrent.setText("The Current State");
    GICurrent.setBounds(new Rectangle(9, 86, 136, 25));
    OPL.setBorder(titledBorder3);
    OPL.setBounds(new Rectangle(298, 21, 209, 200));
    OPL.setLayout(null);
    DDObjMain.setBorder(titledBorder5);
    DDObjMain.setBounds(new Rectangle(19, 17, 531, 205));
    DDObjMain.setLayout(null);
    DDObjOrigin.setText("Origin");
    DDObjOrigin.setBounds(new Rectangle(14, 100, 58, 25));
    DDObjDesc.setText("Description ");
    DDObjDesc.setBounds(new Rectangle(14, 64, 91, 25));
    DDObjType.setText("Object Type ");
    DDObjType.setBounds(new Rectangle(14, 27, 92, 25));
    DDObjStates.setText("States");
    DDObjStates.setBounds(new Rectangle(175, 134, 60, 25));
    DDObjStates.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDObjStates_actionPerformed(e);
      }
    });
    DDObjScope.setText("Scope");
    DDObjScope.setBounds(new Rectangle(14, 134, 60, 25));
    DDObjEssence.setText("Essence");
    DDObjEssence.setBounds(new Rectangle(175, 27, 74, 25));
    OPD.setBorder(titledBorder11);
    OPD.setBounds(new Rectangle(48, 21, 209, 200));
    OPD.setLayout(null);
    OPDByName.setText("By Name");
    OPDByName.setBounds(new Rectangle(13, 97, 89, 25));
    OPDByName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPDByName_actionPerformed(e);
      }
    });
    OPDUntil.setText("Until Level");
    OPDUntil.setBounds(new Rectangle(13, 65, 89, 25));
    OPDUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPDUntil_actionPerformed(e);
      }
    });
    OPDAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPDAll_actionPerformed(e);
      }
    });
    OPDAll.setActionCommand("");
    OPDAll.setText("All Levels");
    OPDAll.setBounds(new Rectangle(13, 33, 89, 25));
    OPDNone.setText("None");
    OPDNone.setBounds(new Rectangle(13, 129, 89, 25));
    OPDNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPDNone_actionPerformed(e);
      }
    });
    DDObjIndex.setText("Index");
    DDObjIndex.setBounds(new Rectangle(175, 64, 54, 25));
    DDObjInitVal.setText("Initial Value");
    DDObjInitVal.setBounds(new Rectangle(175, 100, 88, 25));
    DDObjState.setBorder(titledBorder4);
    DDObjState.setBounds(new Rectangle(368, 31, 134, 127));
    DDObjState.setLayout(null);
    DDObjStateDescr.setEnabled(true);
    DDObjStateDescr.setText("Description");
    DDObjStateDescr.setBounds(new Rectangle(16, 64, 88, 25));
    DDObjStateInitial.setEnabled(true);
    DDObjStateInitial.setText("Initial/Final");
    DDObjStateInitial.setBounds(new Rectangle(16, 34, 88, 25));
    DDProcMain.setBorder(titledBorder7);
    DDProcMain.setBounds(new Rectangle(19, 17, 531, 206));
    DDProcMain.setLayout(null);
    DDProcEssence.setText("Essence");
    DDProcEssence.setBounds(new Rectangle(14, 63, 92, 25));
    DDProcOrigin.setText("Origin");
    DDProcOrigin.setBounds(new Rectangle(14, 134, 92, 25));
    DDProcDescr.setText("Description");
    DDProcDescr.setBounds(new Rectangle(14, 27, 92, 25));
    DDProcActTime.setText("Activation time");
    DDProcActTime.setBounds(new Rectangle(176, 27, 103, 25));
    DDProcScope.setText("Scope");
    DDProcScope.setBounds(new Rectangle(14, 98, 92, 25));
    DDProcBody.setText("Body");
    DDProcBody.setBounds(new Rectangle(178, 63, 52, 25));
    DDRelMain.setBorder(titledBorder8);
    DDRelMain.setBounds(new Rectangle(19, 17, 531, 205));
    DDRelMain.setLayout(null);
    DDRelAggreg.setText("Aggregation-Particulation Relations");
    DDRelAggreg.setBounds(new Rectangle(14, 27, 235, 25));
    DDRelFeature.setText("Featuring-Characterization Relations");
    DDRelFeature.setBounds(new Rectangle(14, 63, 235, 25));
    DDRelGeneral.setText("Generalization-Specification Relations");
    DDRelGeneral.setBounds(new Rectangle(14, 98, 235, 25));
    DDRelClassif.setText("Classification-Instantiation Relations");
    DDRelClassif.setBounds(new Rectangle(14, 134, 235, 25));
    DDRelUni.setText("Uni-Directional General Relations");
    DDRelUni.setBounds(new Rectangle(284, 27, 230, 25));
    DDRelBi.setText("Bi-directional General Relations");
    DDRelBi.setBounds(new Rectangle(284, 63, 230, 25));
    DDLinkMain.setBorder(titledBorder10);
    DDLinkMain.setBounds(new Rectangle(19, 17, 532, 205));
    DDLinkMain.setLayout(null);
    DDLinkInvoc.setText("Invocation Links");
    DDLinkInvoc.setBounds(new Rectangle(194, 59, 125, 25));
    DDLinkInvoc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkInvoc_actionPerformed(e);
      }
    });
    DDLinkInstrEv.setText("Instrument Event Links");
    DDLinkInstrEv.setBounds(new Rectangle(194, 91, 151, 25));
    DDLinkInstrEv.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkInstrEv_actionPerformed(e);
      }
    });
    DDLinkCondition.setText("Condition Links");
    DDLinkCondition.setBounds(new Rectangle(194, 123, 132, 25));
    DDLinkCondition.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkCondition_actionPerformed(e);
      }
    });
    DDLinkInstr.setText("Instrument Links");
    DDLinkInstr.setBounds(new Rectangle(14, 59, 173, 25));
    DDLinkInstr.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkInstr_actionPerformed(e);
      }
    });
    DDLinkEvent.setText("Event Links");
    DDLinkEvent.setBounds(new Rectangle(14, 155, 173, 25));
    DDLinkEvent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkEvent_actionPerformed(e);
      }
    });
    DDLinkRes.setText("Result/Consumption Links");
    DDLinkRes.setBounds(new Rectangle(14, 91, 173, 25));
    DDLinkRes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkRes_actionPerformed(e);
      }
    });
    DDLinkEffect.setText("Effect Links");
    DDLinkEffect.setBounds(new Rectangle(14, 123, 173, 25));
    DDLinkEffect.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkEffect_actionPerformed(e);
      }
    });
    DDLinkAgent.setText("Agent Links");
    DDLinkAgent.setBounds(new Rectangle(14, 27, 173, 25));
    DDLinkAgent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkAgent_actionPerformed(e);
      }
    });
    DDLinkException.setText("Exception Links");
    DDLinkException.setBounds(new Rectangle(194, 27, 116, 25));
    DDLinkException.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DDLinkException_actionPerformed(e);
      }
    });

    //General info fields
    GIIssues.setBorder(BorderFactory.createEtchedBorder());
    GIIssues.setText("Open Issues");
    GIIssues.setBounds(new Rectangle(249, 115, 96, 25));
    GIInputs.setBorder(BorderFactory.createEtchedBorder());
    GIInputs.setText("Inputs, Processing functionality and Outputs");
    GIInputs.setBounds(new Rectangle(249, 86, 269, 25));
    GIHard.setBorder(BorderFactory.createEtchedBorder());
    GIHard.setText("Hardware and Software Requirements");
    GIHard.setBounds(new Rectangle(249, 171, 236, 25));
    GIProblems.setBorder(BorderFactory.createEtchedBorder());
    GIProblems.setText("Problems");
    GIProblems.setBounds(new Rectangle(249, 143, 79, 25));
    GIBusiness.setBorder(BorderFactory.createEtchedBorder());
    GIBusiness.setText("Business or Program Constraints");
    GIBusiness.setBounds(new Rectangle(249, 58, 210, 25));
    GIOper.setBorder(BorderFactory.createEtchedBorder());
    GIOper.setText("Operation and Maintenance");
    GIOper.setBounds(new Rectangle(249, 30, 176, 25));
    //OPD OPL fields
    OPLUntilText.setBounds(new Rectangle(131, 58, 39, 25));
    OPLUntilText.setVisible(false);
    OPDUntilText.setVisible(false);
    OPDUntilText.setBounds(new Rectangle(110, 65, 38, 25));
    OPLUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLUntil_actionPerformed(e);
      }
    });
    OPLUntil.setBounds(new Rectangle(14, 58, 89, 25));
    OPLUntil.setText("Until Level");
    OPLAll.setBounds(new Rectangle(14, 27, 89, 25));
    OPLAll.setText("All Levels");
    OPLAll.setActionCommand("");
    OPLAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLAll_actionPerformed(e);
      }
    });
    OPLNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLNone_actionPerformed(e);
      }
    });
    OPLNone.setBounds(new Rectangle(14, 121, 89, 25));
    OPLNone.setText("None");
    OPLByName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLByName_actionPerformed(e);
      }
    });
    OPLByName.setBounds(new Rectangle(14, 90, 89, 25));
    OPLByName.setText("By Name");
    OPLandOPD.setText("According to OPD");
    OPLandOPD.setBounds(new Rectangle(14, 152, 128, 25));
    OPLandOPD.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OPLandOPD_actionPerformed(e);
      }
    });
    //Link properties fields
    DDLinkPropCond.setText("Condition");
    DDLinkPropCond.setBounds(new Rectangle(20, 27, 78, 25));
    DDLinkProp.setBorder(titledBorder9);
    DDLinkProp.setBounds(new Rectangle(377, 23, 130, 137));
    DDLinkProp.setLayout(null);
    DDLinkProp.setVisible(false);
    DDLinkPropPath.setText("Path");
    DDLinkPropPath.setBounds(new Rectangle(20, 59, 50, 25));
    DDLinkPropReacTime.setText("Reaction time");
    DDLinkPropReacTime.setBounds(new Rectangle(20, 91, 101, 25));
    DDObjStateTime.setText("Activation time");
    DDObjStateTime.setBounds(new Rectangle(16, 94, 109, 25));

    DFromTempPanel.setBorder(titledBorder14);
    DFromTempPanel.setBounds(new Rectangle(4, 4, 566, 47));
    DFromTempPanel.setLayout(null);
    DTempRadio.setText("From Template");
    DTempRadio.setBounds(new Rectangle(68, 15, 142, 25));
    DTempRadio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DTempRadio_actionPerformed(e);
      }
    });
    DTCombo.setBounds(new Rectangle(212, 17, 125, 21));
    DTCombo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DTCombo_actionPerformed(e);
      }
    });
    DTSavePanel.setBorder(titledBorder12);
    DTSavePanel.setBounds(new Rectangle(4, 391, 567, 54));
    DTSavePanel.setLayout(null);
    MakeTemp.setBounds(new Rectangle(286, 17, 124, 27));
    MakeTemp.setBorder(BorderFactory.createEtchedBorder());
    MakeTemp.setText("Save as Template");
    MakeTemp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MakeTemp_actionPerformed(e);
      }
    });
    DTSaveText.setBounds(new Rectangle(178, 20, 187, 26));
    DTSaveOK.setBounds(new Rectangle(382, 19, 79, 27));
    DTSaveOK.setBorder(border14);
    DTSaveOK.setText("OK");
    DTSaveOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DTSaveOK_actionPerformed(e);
      }
    });
    DTSaveCancel.setBounds(new Rectangle(469, 19, 79, 27));
    DTSaveCancel.setBorder(border14);
    DTSaveCancel.setText("Cancel");
    DTSaveCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DTSaveCancel_actionPerformed(e);
      }
    });
    SelAll.setBounds(new Rectangle(293, 197, 114, 27));
    SelAll.setText("Select All");
    SelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SelAll_actionPerformed(e);
      }
    });
    UnSelAll.setBounds(new Rectangle(413, 197, 114, 27));
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
    ObjSelAll.setBounds(new Rectangle(289, 169, 114, 27));
    ObjUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ObjUnSelAll_actionPerformed(e);
      }
    });
    ObjUnSelAll.setText("Unselect All");
    ObjUnSelAll.setBounds(new Rectangle(409, 169, 114, 27));
    ProcUnSelAll.setBounds(new Rectangle(407, 169, 114, 27));
    ProcUnSelAll.setText("Unselect All");
    ProcUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ProcUnSelAll_actionPerformed(e);
      }
    });
    ProcSelAll.setBounds(new Rectangle(287, 169, 114, 27));
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
    LinkUnSelAll.setBounds(new Rectangle(410, 171, 114, 27));
    LinkSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LinkSelAll_actionPerformed(e);
      }
    });
    LinkSelAll.setText("Select All");
    LinkSelAll.setBounds(new Rectangle(290, 171, 114, 27));
    RelSelAll.setBounds(new Rectangle(289, 170, 114, 27));
    RelSelAll.setText("Select All");
    RelSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        RelSelAll_actionPerformed(e);
      }
    });
    RelUnSelAll.setBounds(new Rectangle(409, 170, 114, 27));
    RelUnSelAll.setText("Unselect All");
    RelUnSelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        RelUnSelAll_actionPerformed(e);
      }
    });
    DTSaveL.setToolTipText("");
    DTSaveL.setText("Template name");
    DTSaveL.setBounds(new Rectangle(57, 19, 119, 26));
    GILeft.add(GICurrent, null);
    GILeft.add(GIOper, null);
    GILeft.add(GIClient, null);
    GILeft.add(GIOverview, null);
    GILeft.add(GIUsers, null);
    GILeft.add(GIGoals, null);
    GILeft.add(GIFuture, null);
    GILeft.add(GIBusiness, null);
    GILeft.add(GIInputs, null);
    GILeft.add(GIIssues, null);
    GILeft.add(GIProblems, null);
    GILeft.add(GIHard, null);
    GILeft.add(UnSelAll, null);
    GILeft.add(SelAll, null);
    contentPane.add(ButtonsPanel, null);
    DDProcMain.add(DDProcDescr, null);
    DDProcMain.add(DDProcEssence, null);
    DDProcMain.add(DDProcScope, null);
    DDProcMain.add(DDProcOrigin, null);
    DDProcMain.add(DDProcActTime, null);
    DDProcMain.add(DDProcBody, null);
    DDProcMain.add(ProcUnSelAll, null);
    DDProcMain.add(ProcSelAll, null);
    TPDD.add(DDObjects, "Objects");
    DDObjects.add(DDObjMain, null);
    TPDD.add(DDProcesses, "Processes");
    DDProcesses.add(DDProcMain, null);
    DDLinkMain.add(DDLinkAgent, null);
    DDLinkMain.add(DDLinkInstr, null);
    DDLinkMain.add(DDLinkRes, null);
    DDLinkMain.add(DDLinkEffect, null);
    DDLinkMain.add(DDLinkEvent, null);
    DDLinkMain.add(DDLinkException, null);
    DDLinkMain.add(DDLinkInvoc, null);
    DDLinkMain.add(DDLinkInstrEv, null);
    DDLinkMain.add(DDLinkCondition, null);
    DDLinkMain.add(DDLinkProp, null);
    DDLinkProp.add(DDLinkPropCond, null);
    DDLinkProp.add(DDLinkPropPath, null);
    DDLinkProp.add(DDLinkPropReacTime, null);
    DDLinkMain.add(LinkUnSelAll, null);
    DDLinkMain.add(LinkSelAll, null);
    TPDD.add(DDLinks, "Links");
    DDLinks.add(DDLinkMain, null);
    DDRelMain.add(DDRelUni, null);
    DDRelMain.add(DDRelBi, null);
    DDRelMain.add(DDRelAggreg, null);
    DDRelMain.add(DDRelFeature, null);
    DDRelMain.add(DDRelGeneral, null);
    DDRelMain.add(DDRelClassif, null);
    DDRelMain.add(RelUnSelAll, null);
    DDRelMain.add(RelSelAll, null);
    TPDD.add(DDRelations, "Relations");
    DDRelations.add(DDRelMain, null);
    contentPane.add(DFromTempPanel, null);
    OPL.add(OPLUntilText, null);
    OPL.add(OPLAll, null);
    OPL.add(OPLUntil, null);
    OPL.add(OPLByName, null);
    OPL.add(OPLNone, null);
    OPL.add(OPLandOPD, null);
    DD.add(TPDD, null);
    OPDOPL.add(OPD, null);
    DDObjMain.add(DDObjDesc, null);
    DDObjMain.add(DDObjOrigin, null);
    DDObjMain.add(DDObjScope, null);
    DDObjMain.add(DDObjEssence, null);
    DDObjMain.add(DDObjIndex, null);
    DDObjMain.add(DDObjInitVal, null);
    DDObjMain.add(DDObjType, null);
    DDObjMain.add(DDObjStates, null);
    DDObjMain.add(DDObjState, null);
    DDObjState.add(DDObjStateInitial, null);
    DDObjState.add(DDObjStateDescr, null);
    DDObjState.add(DDObjStateTime, null);
    DDObjMain.add(ObjUnSelAll, null);
    DDObjMain.add(ObjSelAll, null);
     GI.add(GILeft, null);
    BG_OPD.add(OPDAll);
    BG_OPD.add(OPDUntil);
    BG_OPD.add(OPDByName);
    BG_OPD.add(OPDNone);
    ButtonsPanel.add(Generate, null);
    ButtonsPanel.add(Cancel, null);
    ButtonsPanel.add(MakeTemp, null);
    contentPane.add(DTSavePanel, null);
    DTSavePanel.add(DTSaveText, null);
    DTSavePanel.add(DTSaveCancel, null);
    DTSavePanel.add(DTSaveOK, null);
    DTSavePanel.add(DTSaveL, null);
    OPD.add(OPDUntil, null);
    OPD.add(OPDAll, null);
    OPD.add(OPDByName, null);
    OPD.add(OPDNone, null);
    OPD.add(OPDUntilText, null);
    OPDOPL.add(OPL, null);
    BG_OPL.add(OPLAll);
    BG_OPL.add(OPLUntil);
    BG_OPL.add(OPLByName);
    BG_OPL.add(OPLNone);
    BG_OPL.add(OPLandOPD);
    TPMain.add(GI, "General Info");
    TPMain.add(OPDOPL, "OPD & OPL");
    TPMain.add(DD,  "Element Dictionary");
    DFromTempPanel.add(DTempRadio, null);
    DFromTempPanel.add(DTCombo, null);
    contentPane.add(TPMain, null);
    DDObjState.setVisible(false);
    DTCombo.setVisible(false);
    DTSavePanel.setVisible(false);
    OPDAll.setSelected(true);
    OPLandOPD.setSelected(true);
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      this.dispose();
      //System.exit(0);
    }
  }

/**
 * Checks if the state properties panel should be visible.
 * @param e ActionEvent on ObjStates
 */
  void DDObjStates_actionPerformed(ActionEvent e) {
    if (DDObjStates.isSelected()) {
      DDObjState.setVisible(true);
    }
    if (DDObjStates.isSelected()==false) {
      DDObjState.setVisible(false);
    }
  }
/**
 * In case that All OPD levels is selected
 * makes the text field for OPD level invisible.
 * @param e ActionEvent on OPDAll
 */
  void OPDAll_actionPerformed(ActionEvent e) {
    if (OPDAll.isSelected()) {
      OPDUntilText.setVisible(false);
    }
  }
/**
 * In case that No OPD levels is selected
 * makes the text field for OPD level invisible.
 * @param e ActionEvent on OPDNone
 */
  void OPDNone_actionPerformed(ActionEvent e) {
    if (OPDNone.isSelected()) {
      OPDUntilText.setVisible(false);
    }
  }
/**
 * In case that OPD Until is selected
 * makes the text field for OPD level visible.
 * @param e ActionEvent on OPDUntil
 */
 void OPDUntil_actionPerformed(ActionEvent e) {
    if (OPDUntil.isSelected()) {
      OPDUntilText.setVisible(true);
    }
 }
/**
 * In case that OPD By name is selected
 * makes the text field for OPD level invisible.
 * @param e ActionEvent on OPD by Name
 */
  void OPDByName_actionPerformed(ActionEvent e) {
    if (OPDByName.isSelected()) {
      OPDUntilText.setVisible(false);
    }
  }
/**
 * In case that OPL Until is selected
 * makes the text field for OPL level visible.
 * @param e ActionEvent on OPL Until
 */
  void OPLUntil_actionPerformed(ActionEvent e) {
    if (OPLUntil.isSelected())
      OPLUntilText.setVisible(true);
  }
/**
 * In case that All OPL Levels is selected
 * makes the text field for OPL level invisible.
 * @param e ActionEvent on All OPL Level
 */
  void OPLAll_actionPerformed(ActionEvent e) {
    if (OPLAll.isSelected())
      OPLUntilText.setVisible(false);
  }
/**
 * In case that No OPL is selected
 * makes the text field for OPL level invisible.
 * @param e ActionEvent on No OPL
 */
  void OPLNone_actionPerformed(ActionEvent e) {
    if (OPLNone.isSelected())
      OPLUntilText.setVisible(false);
  }
/**
 * In case that OPL By name is selected
 * makes the text field for OPL level invisible.
 * @param e ActionEvent on OPL By name
 */
  void OPLByName_actionPerformed(ActionEvent e) {
    if (OPLByName.isSelected())
      OPLUntilText.setVisible(false);
  }
/**
 * In case that OPL according to OPD is selected
 * makes the text field for OPL level invisible.
 * @param e ActionEvent on OPL according to OPD
 */
  void OPLandOPD_actionPerformed(ActionEvent e) {
  if (OPLandOPD.isSelected())
      OPLUntilText.setVisible(false);
  }
/**
 * For any change made to the Agent Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Agent Link
 */
  void DDLinkAgent_actionPerformed(ActionEvent e) {
    if (DDLinkAgent.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }
/**
 * For any change made to the Instrument Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Instrument Link
 */
  void DDLinkInstr_actionPerformed(ActionEvent e) {
    if (DDLinkInstr.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }
/**
 * For any change made to the Result Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Result Link
 */
  void DDLinkRes_actionPerformed(ActionEvent e) {
    if (DDLinkRes.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Effect Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Effect Link
 */
  void DDLinkEffect_actionPerformed(ActionEvent e) {
    if (DDLinkEffect.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Event Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Event Link
 */
  void DDLinkEvent_actionPerformed(ActionEvent e) {
    if (DDLinkEvent.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Exception Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Exception Link
 */
  void DDLinkException_actionPerformed(ActionEvent e) {
    if (DDLinkException.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Invocation Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Invocation Link
 */
  void DDLinkInvoc_actionPerformed(ActionEvent e) {
    if (DDLinkInvoc.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Instrument Event Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Instrument Event Link
 */
  void DDLinkInstrEv_actionPerformed(ActionEvent e) {
    if (DDLinkInstrEv.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * For any change made to the Condition Link,
 * checks if link properties panel should be visible
 * and sets it respectively.
 * @param e ActionEvent on Condition Link
 */
  void DDLinkCondition_actionPerformed(ActionEvent e) {
    if (DDLinkCondition.isSelected())
      DDLinkProp.setVisible(true);
    if ((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
  }

/**
 * Checks the input to be valid.<BR>
 * In Case the input is valid, creates a new document by DocCreate Funciton.
 * Checks if OPD and/or OPL are to be chosen by name.<BR>
 * If they are: opens the choosing window, else
 * opens the save window and calls for print function in "doc".
 * @param e ActionEvent on Generate button
 */
  void Generate_actionPerformed(ActionEvent e) {
    try {

      //if OPD untill is selected -> check that there is a valid level number
      if (OPDUntil.isSelected()) {
        int opd_text = Integer.parseInt(OPDUntilText.getText());
        if (opd_text <1)
          opd_text = Integer.parseInt("jdkfh");
      }//of if
      //if OPL untill is selected -> check that there is a valid level number
      if (OPLUntil.isSelected()) {
        int opl_text = Integer.parseInt(OPLUntilText.getText());
        if (opl_text <1)
          opl_text = Integer.parseInt("jdkfh");
      }//of if

      Document doc = DocCreate();//create a new document
      //in case the user wants to choose the diagrams or OPL by name
      //show the chooser window
      if ( (OPDByName.isSelected())||(OPLByName.isSelected())){
        int type = 0;
        if (OPDByName.isSelected())
          type = 0;//only OPDlist
        if (OPLByName.isSelected())
          type = 1;//only OPLlist
        if ((OPLByName.isSelected())&&(OPDByName.isSelected()))
          type =2;//both OPD and OPL lists
        DocOPDByName opdlist = new DocOPDByName(mySys, type, doc, this);
        opdlist.setVisible(true);
      }// of if
      //if the user is not interested in choosing OPD or OPL by name
      else {
        String filename="";
        DocSave doc_save = new DocSave(this); //show the window for choosing
                                              //a name for the document
        int returnVal = doc_save.SDFileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          String name = doc_save.SDFileChooser.getSelectedFile().getName();
          filename = doc_save.SDFileChooser.getSelectedFile().getPath();
          StringTokenizer st = new StringTokenizer(filename, ".", false);
          filename = st.nextToken()+".htm";
          File myDir = doc_save.SDFileChooser.getCurrentDirectory();
          int flag = 0; //0 - no such file yet
          int i=0;
          //checks if a file with this name already exists
          if (myDir.exists()) {
            File[] contents = myDir.listFiles();
            while (i < contents.length) {
              if (contents[i].getName().compareTo(name)==0)
                flag = 1;
              i++;
            }//of while
          }//of if
          //if there is a file with this name
          //ask if the user is interested in overwriting the file
          //if YES -> print the document
          if (flag==1){
            int retval = JOptionPane.showConfirmDialog( this, "A document with this name already exists. Do you want to overwrite it?", "Overwrite Document", JOptionPane.YES_NO_OPTION);
            if (retval== JOptionPane.YES_OPTION){
              try{
                //make the file
                doc.PrintDocument(mySys,filename);
                //close this window
                this.dispose();
              }// of try
              catch(IOException er){
                System.out.println("There was a problem printing the file.");
              }// of catch
            }//of if
          }//of if
          //if there is no file with this name
          else {
            try{
              //make the file
              doc.PrintDocument(mySys,filename);
              //close this window
              this.dispose();
            }// of try
            catch(IOException er){
              System.out.println("There was a problem printing the file.");
            }// of catch
          }//of else
        }//of if
      }//of else
    }//of try
    //if there was an invalid level number
    catch (NumberFormatException ne) {
      JOptionPane.showMessageDialog(this, "Illegal input: level is not valid. Please insert an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
    }//of catch
  }

/**
  * Cancel creating a document
  * @param e ActionEvent on Cancel button
  */
  void Cancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }

/**
 * Checks for legal input and sets the "save as template" panel visible.
 * @param e ActionEvent on MakeTemp button
 */
  void MakeTemp_actionPerformed(ActionEvent e) {
    try {
      //if OPD untill is selected -> check that the level is a legal number
      if (OPDUntil.isSelected()){
        int opd_text = Integer.parseInt(OPDUntilText.getText());
	  if (opd_text<1)
		opd_text=Integer.parseInt("jsak");
      }
      //if OPL untill is selected -> check that the level is a legal number
      if (OPLUntil.isSelected()) {
        int opl_text = Integer.parseInt(OPLUntilText.getText());
	  if (opl_text<1)
		opl_text=Integer.parseInt("jsak");
      }
      DTSavePanel.setVisible(true);
    }//of try
    catch (NumberFormatException ne) {
      JOptionPane.showMessageDialog(this, "Illegal input: level is not valid. Please insert an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
    }//of catch
  }

/**
 * Enables choosing a template from a list of avaliable templates.
 * @param e ActionEvent on From Template radio box
 */
  void DTempRadio_actionPerformed(ActionEvent e) {
    if (DTempRadio.isSelected())
        DTCombo.setVisible(true);
    if (!DTempRadio.isSelected())
        DTCombo.setVisible(false);
  }

/**
 * Cancel saving as template. Hides the "Save as template" panel.
 * @param e ActionEvent on save button in save as template paenl
 */
  void DTSaveCancel_actionPerformed(ActionEvent e) {
    DTSavePanel.setVisible(false); //hide the "save as template" panel
  }

/**
 * Saves the selection as a template and hides the "Save as template" panel.
 * @param e ActionEvent on save button on main panel
 */
  void DTSaveOK_actionPerformed(ActionEvent e) {

    try{
     String temp=DTSaveText.getText(); //the name of the template
     //if a name for the template was not inserted
     if (temp.compareTo("")==0) {
       JOptionPane.showMessageDialog(this, "Please insert a name for the template.", "ERROR", JOptionPane.ERROR_MESSAGE);
     }//of if
     //if a name for the template was inserted
     else{
      int flag = 0; //a flag indicating if there is a template with this name
                    //0 - no such file yet
      int i=0;
      //if there is a directory for templates already
      if (myDir.exists()) {
        File[] contents = myDir.listFiles();
        //check all the files in the templates directory
        //if there is a template with this name
        //set flag to 1
        while (i < contents.length) {
          if (contents[i].getName().compareTo(temp)==0)
            flag = 1;
          i++;
        }//of while
      }//of if
      //if there is a template with this name already
      //ask if the user wants to overwrite that template
      //if YES -> overwrite, else do nothing
      if (flag == 1){
        int retval=JOptionPane.showConfirmDialog( this, "A template with this name already exists. Do you want to overwrite it?", "Overwrite Template", JOptionPane.YES_NO_OPTION);
        if (retval== JOptionPane.YES_OPTION){
          Document doc = DocCreate();//create a new document
          doc.CreateTemplate(mySys, temp);//save the document as template
          DTSavePanel.setVisible(false);//hide the "save as template" panel
        }//of if
      }//of if
      //if there isn't a template with this name already
      //save the selection as a template
      else {
        Document doc = DocCreate();//create a new document
        doc.CreateTemplate(mySys, temp);//save it as template
        DTSavePanel.setVisible(false);//hide the "save as template" panel
        DTCombo.addItem(temp);//add the name of the new template to the combo
                              //with the list of available templates
      }//of else
     }//of else
    DTSaveText.setText("");
    }//of try
    catch(IOException er){
       System.out.println("Wasn't able to save the template");
    }//of catch
  }//of DTSaveOK_actionPerformed

/**
 * Takes the selected fields from the screen, creates a new document
 * and marks the user selection in the document.
 * @return doc of type Document, with the relevent choices
 */
  Document DocCreate (){

    Document doc = new Document();
    boolean[] obj_array =new boolean[11];//an array for object fields
    boolean[] proc_array =new boolean[6];//an array for process fields
    boolean[] rel_array =new boolean[6];//an array for relation fields
    boolean[] link_array =new boolean[12];//an array for link fields
    boolean[] opd_array  =new boolean[7];//an array for opdopl fields
    boolean[] gi_array = new boolean[12];//an array for general info fields

    //for fields of object, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    obj_array[0] = DDObjType.isSelected();
    obj_array[1] = DDObjDesc.isSelected();
    obj_array[2] = DDObjInitVal.isSelected();
    obj_array[3] = DDObjEssence.isSelected();
    obj_array[4] = DDObjIndex.isSelected();
    obj_array[5] = DDObjScope.isSelected();
    obj_array[6] = DDObjOrigin.isSelected();
    obj_array[7] = DDObjStates.isSelected();
    obj_array[8] = DDObjStateDescr.isSelected();
    obj_array[9] = DDObjStateInitial.isSelected();
    obj_array[10] = DDObjStateTime.isSelected();
    //for fields of process, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    proc_array[0] = DDProcDescr.isSelected();
    proc_array[1] = DDProcEssence.isSelected();
    proc_array[2] = DDProcOrigin.isSelected();
    proc_array[3] = DDProcScope.isSelected();
    proc_array[4] = DDProcBody.isSelected();
    proc_array[5] = DDProcActTime.isSelected();
    //for fields of relation, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    rel_array[0] = DDRelAggreg.isSelected();
    rel_array[1] = DDRelClassif.isSelected();
    rel_array[2] = DDRelFeature.isSelected();
    rel_array[3] = DDRelGeneral.isSelected();
    rel_array[4] = DDRelBi.isSelected();
    rel_array[5] = DDRelUni.isSelected();
    //for fields of link, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    link_array[0] = DDLinkAgent.isSelected();
    link_array[1] = DDLinkCondition.isSelected();
    link_array[2] = DDLinkEffect.isSelected();
    link_array[3] = DDLinkEvent.isSelected();
    link_array[4] = DDLinkException.isSelected();
    link_array[5] = DDLinkInstrEv.isSelected();
    link_array[6] = DDLinkInvoc.isSelected();
    link_array[7] = DDLinkRes.isSelected();
    link_array[8] = DDLinkInstr.isSelected();
    link_array[9] = DDLinkPropCond.isSelected();
    link_array[10] = DDLinkPropPath.isSelected();
    link_array[11] = DDLinkPropReacTime.isSelected();
    //for fields of opd and opl, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    opd_array[0] = OPDAll.isSelected();
    opd_array[1] = OPDByName.isSelected();
    opd_array[2] = OPDNone.isSelected();
    opd_array[3] = OPLAll.isSelected();
    opd_array[4] = OPLByName.isSelected();
    opd_array[5] = OPLNone.isSelected();
    opd_array[6] = OPLandOPD.isSelected();
    //for fields of general info, check if the field is selected on the screen -
    //if selected -> set TRUE in the array, else FALSE
    gi_array[0] = GIClient.isSelected();
    gi_array[1] = GIOverview.isSelected();
    gi_array[2] = GICurrent.isSelected();
    gi_array[3] = GIGoals.isSelected();
    gi_array[4] = GIBusiness.isSelected();
    gi_array[5] = GIFuture.isSelected();
    gi_array[6] = GIHard.isSelected();
    gi_array[7] = GIInputs.isSelected();
    gi_array[8] = GIIssues.isSelected();
    gi_array[9] = GIProblems.isSelected();
    gi_array[10] = GIUsers.isSelected();
    gi_array[11] = GIOper.isSelected();

    String OPDUntilT,OPLUntilT; //strings for the untill level
    //if OPD until is selected -> put level chosen in the string
    if (OPDUntil.isSelected())
      OPDUntilT=OPDUntilText.getText();
    //else -> put -1
    else
      OPDUntilT="-1";
    //if OPL until is selected -> put level chosen in the string
    if (OPLUntil.isSelected())
      OPLUntilT=OPLUntilText.getText();
    //else -> put -1
    else
      OPLUntilT="-1";
    //save the selection in the document created by init functions
    doc.DocInfo.Data.Proc.ProcInit(proc_array);
    doc.DocInfo.Data.Obj.ObjInit(obj_array);
    doc.DocInfo.Data.Link.LinkInit(link_array);
    doc.DocInfo.Data.Rel.RelInit(rel_array);
    doc.DocInfo.GI.GIInit(gi_array);
    doc.DocInfo.opdopl.OPDOPLInit(opd_array,OPDUntilT,OPLUntilT);
    return doc;//return the document created
  }

/**
 * The function is showing the choices selected in the document d on the screen
 * @param d Object of type Document
 */
  void TempToScreen(Document d){

    Document doc=d;
    //for fields of object, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    DDObjType.setSelected(doc.DocInfo.Data.Obj.getType());
    DDObjDesc.setSelected(doc.DocInfo.Data.Obj.getDesc());
    DDObjInitVal.setSelected(doc.DocInfo.Data.Obj.getInValue());
    DDObjEssence.setSelected(doc.DocInfo.Data.Obj.getEssence());
    DDObjIndex.setSelected(doc.DocInfo.Data.Obj.getIndex());
    DDObjScope.setSelected(doc.DocInfo.Data.Obj.getScope());
    DDObjOrigin.setSelected(doc.DocInfo.Data.Obj.getOrigin());
    DDObjStates.setSelected(doc.DocInfo.Data.Obj.getStates());
    DDObjStateDescr.setSelected(doc.DocInfo.Data.Obj.getStateDesc());
    DDObjStateInitial.setSelected(doc.DocInfo.Data.Obj.getStateInitial());
    DDObjStateTime.setSelected(doc.DocInfo.Data.Obj.getStateTime());
    //in case the states field for objects is selected ->
    //set visible the pannel with state properties
    if (DDObjStates.isSelected())
      DDObjState.setVisible(true);
    else
      DDObjState.setVisible(false);

    //for fields of process, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    DDProcDescr.setSelected(doc.DocInfo.Data.Proc.getDesc());
    DDProcEssence.setSelected(doc.DocInfo.Data.Proc.getEssence());
    DDProcOrigin.setSelected(doc.DocInfo.Data.Proc.getOrigin());
    DDProcScope.setSelected(doc.DocInfo.Data.Proc.getScope());
    DDProcBody.setSelected(doc.DocInfo.Data.Proc.getBody());
    DDProcActTime.setSelected(doc.DocInfo.Data.Proc.getActTime());

    //for fields of relations, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    DDRelAggreg.setSelected(doc.DocInfo.Data.Rel.getAgPar());
    DDRelClassif.setSelected(doc.DocInfo.Data.Rel.getClassInst());
    DDRelFeature.setSelected(doc.DocInfo.Data.Rel.getFeChar());
    DDRelGeneral.setSelected(doc.DocInfo.Data.Rel.getGenSpec());
    DDRelBi.setSelected(doc.DocInfo.Data.Rel.getBiDir());
    DDRelUni.setSelected(doc.DocInfo.Data.Rel.getUniDir());

    //for fields of links, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    DDLinkAgent.setSelected(doc.DocInfo.Data.Link.getAgent());
    DDLinkCondition.setSelected(doc.DocInfo.Data.Link.getCondition());
    DDLinkEffect.setSelected(doc.DocInfo.Data.Link.getEffect());
    DDLinkEvent.setSelected(doc.DocInfo.Data.Link.getEvent());
    DDLinkException.setSelected(doc.DocInfo.Data.Link.getException());
    DDLinkInstrEv.setSelected(doc.DocInfo.Data.Link.getInstEvent());
    DDLinkInvoc.setSelected(doc.DocInfo.Data.Link.getInvocation());
    DDLinkRes.setSelected(doc.DocInfo.Data.Link.getResCons());
    DDLinkInstr.setSelected(doc.DocInfo.Data.Link.getInstrument());
    DDLinkPropCond.setSelected(doc.DocInfo.Data.Link.getCond());
    DDLinkPropPath.setSelected(doc.DocInfo.Data.Link.getPath());
    DDLinkPropReacTime.setSelected(doc.DocInfo.Data.Link.getReactTime());
    //if at least one of the link types is selected - >
    //set the panel with link properties visible, else hide it
    if((DDLinkAgent.isSelected()==false)&&(DDLinkInstr.isSelected()==false)&&(DDLinkRes.isSelected()==false)&&(DDLinkEffect.isSelected()==false)&&(DDLinkEvent.isSelected()==false)&&(DDLinkException.isSelected()==false)&&(DDLinkInvoc.isSelected()==false)&&(DDLinkInstrEv.isSelected()==false)&&(DDLinkCondition.isSelected()==false))
      DDLinkProp.setVisible(false);
    else
      DDLinkProp.setVisible(true);

    //for fields of OPD and OPL, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    OPDAll.setSelected(doc.DocInfo.opdopl.getOPDAll());
    OPDByName.setSelected(doc.DocInfo.opdopl.getOPDByName());
    OPDNone.setSelected(doc.DocInfo.opdopl.getOPDNone());
    OPLAll.setSelected(doc.DocInfo.opdopl.getOPLAll());
    OPLByName.setSelected(doc.DocInfo.opdopl.getOPLByName());
    OPLNone.setSelected(doc.DocInfo.opdopl.getOPLNone());
    OPLandOPD.setSelected(doc.DocInfo.opdopl.getOPLAccording());

    //for fields of General Info, check if the field is selected in document -
    //if selected -> set selected on screen, else unselect
    GIClient.setSelected(doc.DocInfo.GI.getClient());
    GIOverview.setSelected(doc.DocInfo.GI.getOverview());
    GICurrent.setSelected(doc.DocInfo.GI.getCurrent());
    GIGoals.setSelected(doc.DocInfo.GI.getGoals());
    GIBusiness.setSelected(doc.DocInfo.GI.getBusiness());
    GIFuture.setSelected(doc.DocInfo.GI.getFuture());
    GIHard.setSelected(doc.DocInfo.GI.getHard());
    GIInputs.setSelected(doc.DocInfo.GI.getInputs());
    GIIssues.setSelected(doc.DocInfo.GI.getIssues());
    GIProblems.setSelected(doc.DocInfo.GI.getProblems());
    GIUsers.setSelected(doc.DocInfo.GI.getUsers());
    GIOper.setSelected(doc.DocInfo.GI.getOper());

    //if OPD untill was not selected in the document ->
    //hide text field of the level, unselect the field
    if(doc.DocInfo.opdopl.getOPDUntil().compareTo("-1")==0){
      OPDUntil.setSelected(false);
      OPDUntilText.setVisible(false);
    }
    //else, if OPD untill is selected ->
    //select the relevant field, show the text field for the level,
    //and put the level from the document in the text field
    else{
      OPDUntil.setSelected(true);
      OPDUntilText.setVisible(true);
      OPDUntilText.setText(doc.DocInfo.opdopl.getOPDUntil());
    }
    //if OPL untill was not selected in the document ->
    //hide text field of the level, unselect the field
    if(doc.DocInfo.opdopl.getOPLUntil().compareTo("-1")==0){
      OPLUntil.setSelected(false);
      OPLUntilText.setVisible(false);
    }
    //else, if OPL untill is selected ->
    //select the relevant field, show the text field for the level,
    //and put the level from the document in the text field
    else{
      OPLUntil.setSelected(true);
      OPLUntilText.setVisible(true);
      OPLUntilText.setText(doc.DocInfo.opdopl.getOPLUntil());
    }
  }

/**
 * When the user chooses the template to be opened from the list of
 *  templates in the combo box - findes the template and shows  the
 *  selected fields on the screen.
 * @param e ActionEvent on combo box with list of templates
 */
  void DTCombo_actionPerformed(ActionEvent e) {
    try {
      //if None template selected -> unselect all
      if (((String)DTCombo.getSelectedItem()).compareTo("None")==0){
        DDLinkAgent.setSelected(false);
        DDLinkCondition.setSelected(false);
        DDLinkEffect.setSelected(false);
        DDLinkEvent.setSelected(false);
        DDLinkException.setSelected(false);
        DDLinkInstr.setSelected(false);
        DDLinkInstrEv.setSelected(false);
        DDLinkInvoc.setSelected(false);
        DDLinkPropCond.setSelected(false);
        DDLinkPropPath.setSelected(false);
        DDLinkPropReacTime.setSelected(false);
        DDLinkRes.setSelected(false);
        DDLinkProp.setVisible(false);

        DDObjDesc.setSelected(false);
        DDObjEssence.setSelected(false);
        DDObjIndex.setSelected(false);
        DDObjInitVal.setSelected(false);
        DDObjOrigin.setSelected(false);
        DDObjScope.setSelected(false);
        DDObjStates.setSelected(false);
        DDObjStateDescr.setSelected(false);
        DDObjStateInitial.setSelected(false);
        DDObjStateTime.setSelected(false);
        DDObjType.setSelected(false);
        DDObjState.setVisible(false);

        DDProcActTime.setSelected(false);
        DDProcBody.setSelected(false);
        DDProcDescr.setSelected(false);
        DDProcEssence.setSelected(false);
        DDProcOrigin.setSelected(false);
        DDProcScope.setSelected(false);

        DDRelAggreg.setSelected(false);
        DDRelBi.setSelected(false);
        DDRelClassif.setSelected(false);
        DDRelFeature.setSelected(false);
        DDRelGeneral.setSelected(false);
        DDRelUni.setSelected(false);

        GIBusiness.setSelected(false);
        GIClient.setSelected(false);
        GICurrent.setSelected(false);
        GIFuture.setSelected(false);
        GIGoals.setSelected(false);
        GIHard.setSelected(false);
        GIInputs.setSelected(false);
        GIIssues.setSelected(false);
        GIOper.setSelected(false);
        GIOverview.setSelected(false);
        GIProblems.setSelected(false);
        GIUsers.setSelected(false);

        OPDAll.setSelected(true);
        OPDUntilText.setVisible(false);
        OPLandOPD.setSelected(true);
        OPLUntilText.setVisible(false);
      }
      else {
        Document doc = new Document(); //create a new document
        String filename = (String) DTCombo.getSelectedItem(); //get the template name
        FileInputStream file = new FileInputStream("Templates/" + filename); //find the file of the template
        doc.FromTemp(file); //load the selection from file to document
        TempToScreen(doc); //show the template on the screen
        file.close(); //close the file
      }
    }//of try
    catch(IOException er){
       System.out.println("There was a problem opening template");
    }//of catch
  }

/**
 * When the "Select All" button in the General Info panel is pressed
 * marks all the related fields as selected
 * @param e ActionEvent on select all button for genInfo
 */
  void SelAll_actionPerformed(ActionEvent e) {
      GIBusiness.setSelected(true);
      GIClient.setSelected(true);
      GICurrent.setSelected(true);
      GIFuture.setSelected(true);
      GIGoals.setSelected(true);
      GIHard.setSelected(true);
      GIInputs.setSelected(true);
      GIIssues.setSelected(true);
      GIOper.setSelected(true);
      GIOverview.setSelected(true);
      GIProblems.setSelected(true);
      GIUsers.setSelected(true);

  }

/**
 * When the "Unselect All" button in the General Info panel is pressed
 * marks all the related fields as unselected.
 * @param e ActionEvent on unselect all button for genInfo
 */
  void UnSelAll_actionPerformed(ActionEvent e) {
      GIBusiness.setSelected(false);
      GIClient.setSelected(false);
      GICurrent.setSelected(false);
      GIFuture.setSelected(false);
      GIGoals.setSelected(false);
      GIHard.setSelected(false);
      GIInputs.setSelected(false);
      GIIssues.setSelected(false);
      GIOper.setSelected(false);
      GIOverview.setSelected(false);
      GIProblems.setSelected(false);
      GIUsers.setSelected(false);
  }

/**
 *  When the "Select All" button in the Objects panel is pressed
 *  marks all the object related fields as selected.
 * @param e ActionEvent on select all button for objects
 */
  void ObjSelAll_actionPerformed(ActionEvent e) {
      DDObjDesc.setSelected(true);
      DDObjEssence.setSelected(true);
      DDObjIndex.setSelected(true);
      DDObjInitVal.setSelected(true);
      DDObjOrigin.setSelected(true);
      DDObjScope.setSelected(true);
      DDObjStates.setSelected(true);
      DDObjStateDescr.setSelected(true);
      DDObjStateInitial.setSelected(true);
      DDObjStateTime.setSelected(true);
      DDObjType.setSelected(true);
      DDObjState.setVisible(true);

  }

/**
 *  When the "Unselect All" button in the Objects panel is pressed
 *  marks all the object related fields as unselected.
 * @param e ActionEvent on unselect all button for objects
 */
  void ObjUnSelAll_actionPerformed(ActionEvent e) {
      DDObjDesc.setSelected(false);
      DDObjEssence.setSelected(false);
      DDObjIndex.setSelected(false);
      DDObjInitVal.setSelected(false);
      DDObjOrigin.setSelected(false);
      DDObjScope.setSelected(false);
      DDObjStates.setSelected(false);
      DDObjStateDescr.setSelected(false);
      DDObjStateInitial.setSelected(false);
      DDObjStateTime.setSelected(false);
      DDObjType.setSelected(false);
      DDObjState.setVisible(false);

  }

/**
 *  When the "Unselect All" button in the Processes panel is pressed
 *  marks all the process related fields as unselected.
 * @param e ActionEvent on unselect all button for processes
 */
  void ProcUnSelAll_actionPerformed(ActionEvent e) {
      DDProcActTime.setSelected(false);
      DDProcBody.setSelected(false);
      DDProcDescr.setSelected(false);
      DDProcEssence.setSelected(false);
      DDProcOrigin.setSelected(false);
      DDProcScope.setSelected(false);
  }

/**
 *  When the "Select All" button in the Processes panel is pressed
 *  marks all the process related fields as selected.
 * @param e ActionEvent on select all button for processes
 */
  void ProcSelAll_actionPerformed(ActionEvent e) {
      DDProcActTime.setSelected(true);
      DDProcBody.setSelected(true);
      DDProcDescr.setSelected(true);
      DDProcEssence.setSelected(true);
      DDProcOrigin.setSelected(true);
      DDProcScope.setSelected(true);
  }

/**
 *  When the "Unselect All" button in the Links panel is pressed
 *  marks all the link related fields as unselected.
 * @param e ActionEvent on unselect all button for links
 */
  void LinkUnSelAll_actionPerformed(ActionEvent e) {
      DDLinkAgent.setSelected(false);
      DDLinkCondition.setSelected(false);
      DDLinkEffect.setSelected(false);
      DDLinkEvent.setSelected(false);
      DDLinkException.setSelected(false);
      DDLinkInstr.setSelected(false);
      DDLinkInstrEv.setSelected(false);
      DDLinkInvoc.setSelected(false);
      DDLinkPropCond.setSelected(false);
      DDLinkPropPath.setSelected(false);
      DDLinkPropReacTime.setSelected(false);
      DDLinkRes.setSelected(false);
      DDLinkProp.setVisible(false);

  }

/**
 *  When the "Select All" button in the Links panel is pressed
 *  marks all the link related fields as selected.
 * @param e ActionEvent on select all button for links
 */
  void LinkSelAll_actionPerformed(ActionEvent e) {
      DDLinkAgent.setSelected(true);
      DDLinkCondition.setSelected(true);
      DDLinkEffect.setSelected(true);
      DDLinkEvent.setSelected(true);
      DDLinkException.setSelected(true);
      DDLinkInstr.setSelected(true);
      DDLinkInstrEv.setSelected(true);
      DDLinkInvoc.setSelected(true);
      DDLinkPropCond.setSelected(true);
      DDLinkPropPath.setSelected(true);
      DDLinkPropReacTime.setSelected(true);
      DDLinkRes.setSelected(true);
      DDLinkProp.setVisible(true);

  }

/**
 *  When the "Select All" button in the Relations panel is pressed
 *  marks all the relation related fields as selected.
 * @param e ActionEvent on select all button for relations
 */
  void RelSelAll_actionPerformed(ActionEvent e) {
    DDRelAggreg.setSelected(true);
      DDRelBi.setSelected(true);
      DDRelClassif.setSelected(true);
      DDRelFeature.setSelected(true);
      DDRelGeneral.setSelected(true);
      DDRelUni.setSelected(true);
  }

/**
 *  When the "Unselect All" button in the Relations panel is pressed
 *  marks all the relation related fields as unselected.
 * @param e ActionEvent on unselect all button for relations
 */
  void RelUnSelAll_actionPerformed(ActionEvent e) {
      DDRelAggreg.setSelected(false);
      DDRelBi.setSelected(false);
      DDRelClassif.setSelected(false);
      DDRelFeature.setSelected(false);
      DDRelGeneral.setSelected(false);
      DDRelUni.setSelected(false);

  }
}
