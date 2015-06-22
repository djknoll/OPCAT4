package extensionTools.uml.userInterface;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.ISystem;
import extensionTools.uml.umlDiagrams.Activity_d;
import extensionTools.uml.umlDiagrams.Diagrams_Creator;
import extensionTools.uml.umlDiagrams.Statechart_d;
import extensionTools.uml.umlDiagrams.sequence_d;

/**
 * Creates the screen for the UML diagrams generating.
 */
public class UMLChooser extends JDialog {
  ISystem mySys;
  JPanel UMLMain;
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  JList USeqList;
  JList UStateList;
  TitledBorder titledBorder2;
  Border border3;
  TitledBorder titledBorder3;
  ButtonGroup UUBGroup = new ButtonGroup();
  Border border4;
  TitledBorder titledBorder4;
  Border border5;
  TitledBorder titledBorder5;
  ButtonGroup UClassBGroup = new ButtonGroup();
  Border border6;
  TitledBorder titledBorder6;
  ButtonGroup UDepBGroup = new ButtonGroup();
  Border border7;
  TitledBorder titledBorder7;
  ButtonGroup UMLActGroup = new ButtonGroup();
  JPanel UmlP = new JPanel();
  JPanel UseCaseP = new JPanel();
  Border border8;
  TitledBorder titledBorder8;
  JRadioButton UUAll = new JRadioButton();
  JRadioButton UUUntil = new JRadioButton();
  JRadioButton UURoot = new JRadioButton();
  JRadioButton UUNone = new JRadioButton();
  JPanel ClassP = new JPanel();
  Border border9;
  TitledBorder titledBorder9;
  JRadioButton UClassYes = new JRadioButton();
  JRadioButton UClassNo = new JRadioButton();
  JPanel SequenceP = new JPanel();
  Border border10;
  TitledBorder titledBorder10;
  JScrollPane USeqScroll = new JScrollPane(USeqList);
  JPanel StatechartP = new JPanel();
  Border border11;
  TitledBorder titledBorder11;
  JScrollPane UStateScroll = new JScrollPane();
  JPanel DeploymentP = new JPanel();
  Border border12;
  TitledBorder titledBorder12;
  JRadioButton UDepNo = new JRadioButton();
  JRadioButton UDepYes = new JRadioButton();
  JPanel ActivityP = new JPanel();
  Border border13;
  TitledBorder titledBorder13;
  JTextField UMLActTopText = new JTextField();
  JLabel UMLActTopLable = new JLabel();
  JRadioButton UMLActTop = new JRadioButton();
  JRadioButton UMLActElse = new JRadioButton();
  JScrollPane UMLActScroll = new JScrollPane();
  JButton UMLCancel = new JButton();
  JButton UMLGenerate = new JButton();
  JTextField UUUntilText = new JTextField();
  JRadioButton UMLActNone = new JRadioButton();
  JList UMLActList = new JList();
  JRadioButton UMLActListAll = new JRadioButton();
  JRadioButton UMLActListUntil = new JRadioButton();
  JTextField UMLActListNum = new JTextField();
  JLabel UMLActListLabel = new JLabel();
  ButtonGroup UMLAct2 = new ButtonGroup();
  JRadioButton UStateYes = new JRadioButton();
  JRadioButton UStateNo = new JRadioButton();
  JRadioButton USeqYes = new JRadioButton();
  JRadioButton USeqNo = new JRadioButton();
  ButtonGroup USeqGroup = new ButtonGroup();
  ButtonGroup UStateGroup = new ButtonGroup();
  Vector v_proc=new Vector(1, 1);
  Vector v_obj=new Vector(1, 1);
  Vector v_act=new Vector(1,1);


  /**
   * Construct the frame
   * @param ISystem sys
   */
  public UMLChooser(ISystem sys) {
    super(sys.getMainFrame(), true);
    mySys = sys;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    KeyListener kListener = new KeyAdapter()
    {
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                UMLCancel.doClick();
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                UMLGenerate.doClick();
                return;
            }

        }
    };

    addKeyListener(kListener);


    int fX = sys.getMainFrame().getX();
    int fY = sys.getMainFrame().getY();
    int pWidth = sys.getMainFrame().getWidth();
    int pHeight = sys.getMainFrame().getHeight();

    setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));

  }

  /**
   *Component initialization
   */
  private void jbInit() throws Exception  {
    Enumeration e = mySys.getISystemStructure().getElements();
    int i=0;
    IEntry abstractEntry;
    String tempString;


    //-------------activity list------------------------
    Activity_d act_d=new Activity_d();
    act_d.GetAll_InZoomedProcess(mySys.getISystemStructure(), v_act);
    String data2[] = new String [v_act.size()];
    while (i<v_act.size()) {
      data2[i]=((IEntry)v_act.get(i)).getName().replace('\n',' ');
      i++;
    }
    UMLActList = new JList(data2);
    //-------------state list--------------------------
    Statechart_d state_d=new Statechart_d();
    state_d.getObjectsForList(mySys.getISystemStructure(), v_obj);
    String data1[] = new String [v_obj.size()];
    i=0;
    while (i<v_obj.size()) {
      data1[i]=((IEntry)v_obj.get(i)).getName().replace('\n',' ');
      i++;
    }
    UStateList = new JList(data1);
    //---------------------Sequence list-------------------------
    sequence_d seq_d = new sequence_d();
    seq_d.getProcForList(mySys.getISystemStructure(),v_proc);
    String data[] = new String [v_proc.size()];
    i=0;
    while (i<v_proc.size()) {
      data[i]=((IEntry)v_proc.get(i)).getName().replace('\n',' ');
      i++;
    }
    USeqList = new JList(data);



    UMLMain = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Please select Processes for Sequence Diagrams");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Please select Objects for Statechart Diagrams");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(border3,"Please select levels of OPD for UseCase Diagram");
    border4 = BorderFactory.createEmptyBorder();
    titledBorder4 = new TitledBorder(border4,"Generate Class Diagram?");
    border5 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder5 = new TitledBorder(border5,"Generate Class Diagram?");
    border6 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142));
    titledBorder6 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Generate Deployment Diagram?");
    border7 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder7 = new TitledBorder(border7,"Please select Processes and levels for Activity Diagrm");
    border8 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder8 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Use Case Diagram");
    border9 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder9 = new TitledBorder(border9,"Class Diagram");
    border10 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder10 = new TitledBorder(border10,"Sequence Diagrams");
    border11 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142));
    titledBorder11 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Statechart Diagrams");
    border12 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder12 = new TitledBorder(border12,"Deployment Diagram");
    border13 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder13 = new TitledBorder(border13,"Activity Diagrams");
    UMLMain.setLayout(null);
    this.setResizable(false);
    this.setSize(new Dimension(485, 542));
    this.setTitle("OPM to UML");

    UmlP.setBounds(new Rectangle(4, 4, 510, 488));
    UmlP.setLayout(null);
    UseCaseP.setBorder(titledBorder8);
    UseCaseP.setBounds(new Rectangle(3, 0, 212, 154));
    UseCaseP.setLayout(null);
    UUAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UUAll_actionPerformed(e);
      }
    });
    UUAll.setBounds(new Rectangle(13, 53, 104, 25));
    UUAll.setText("All OPD Levels");
    UUUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UUUntil_actionPerformed(e);
      }
    });
    UUUntil.setBounds(new Rectangle(13, 86, 104, 25));
    UUUntil.setText("Until Level");
    UURoot.setSelected(true);
    UURoot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UURoot_actionPerformed(e);
      }
    });
    UURoot.setBounds(new Rectangle(13, 21, 125, 25));
    UURoot.setText("Root Level Only");
    UUNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UUNone_actionPerformed(e);
      }
    });
    UUNone.setBounds(new Rectangle(13, 118, 125, 25));
    UUNone.setText("None");
    ClassP.setBorder(titledBorder9);
    ClassP.setBounds(new Rectangle(224, 0, 245, 82));
    ClassP.setLayout(null);
    UClassYes.setSelected(true);
    UClassYes.setBounds(new Rectangle(12, 19, 53, 25));
    UClassYes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UClassYes_actionPerformed(e);
      }
    });
    UClassYes.setText("Yes");
    UClassNo.setToolTipText("");
    UClassNo.setBounds(new Rectangle(12, 51, 57, 25));
    UClassNo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UClassNo_actionPerformed(e);
      }
    });
    UClassNo.setText("No");
    SequenceP.setBorder(titledBorder10);
    SequenceP.setBounds(new Rectangle(3, 151, 212, 152));
    SequenceP.setLayout(null);
    USeqScroll.setBounds(new Rectangle(65, 23, 124, 112));
    StatechartP.setBorder(titledBorder11);
    StatechartP.setBounds(new Rectangle(3, 301, 212, 149));
    StatechartP.setLayout(null);
    UStateScroll.setBounds(new Rectangle(65, 28, 124, 106));
    DeploymentP.setBorder(titledBorder12);
    DeploymentP.setBounds(new Rectangle(224, 81, 245, 83));
    DeploymentP.setLayout(null);
    UDepNo.setBounds(new Rectangle(15, 54, 65, 25));
    UDepNo.setText("No");
    UDepNo.setToolTipText("");
    UDepYes.setBounds(new Rectangle(15, 22, 78, 25));
    UDepYes.setText("Yes");
    UDepYes.setSelected(true);
    ActivityP.setBorder(titledBorder13);
    ActivityP.setBounds(new Rectangle(224, 163, 245, 287));
    ActivityP.setLayout(null);
    UMLActTopText.setToolTipText("");
    UMLActTopText.setText("1");
    UMLActTopText.setBounds(new Rectangle(152, 51, 31, 25));
    UMLActTopLable.setText("levels");
    UMLActTopLable.setBounds(new Rectangle(186, 52, 48, 25));
    UMLActTop.setText("From Top Level down");
    UMLActTop.setBounds(new Rectangle(10, 52, 142, 25));
    UMLActTop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActTop_actionPerformed(e);
      }
    });
    UMLActNone.setSelected(true);
    UMLActElse.setText("By Processes");
    UMLActElse.setBounds(new Rectangle(10, 84, 101, 25));
    UMLActElse.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActElse_actionPerformed(e);
      }
    });
    UMLActScroll.setBounds(new Rectangle(15, 14, 282, 141));
    UMLActScroll.setBounds(new Rectangle(53, 110, 167, 104));
    UMLActScroll.setEnabled(false);
    UMLCancel.setBounds(new Rectangle(357, 453, 112, 27));
    UMLCancel.setText("Cancel");
    UMLCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLCancel_actionPerformed(e);
      }
    });
    UMLGenerate.setBounds(new Rectangle(224, 453, 112, 27));
    UMLGenerate.setText("Generate");
    UMLGenerate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLGenerate_actionPerformed(e);
      }
    });
    UUUntilText.setBounds(new Rectangle(124, 86, 46, 25));
    UMLActNone.setText("None");
    UMLActNone.setBounds(new Rectangle(10, 23, 103, 25));
    UMLActNone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActNone_actionPerformed(e);
      }
    });
    UMLActListAll.setText("All levels down");
    UMLActListAll.setBounds(new Rectangle(55, 218, 103, 25));
    UMLActListAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActListAll_actionPerformed(e);
      }
    });
    UMLActListUntil.setBounds(new Rectangle(55, 240, 17, 25));
    UMLActListUntil.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActListUntil_actionPerformed(e);
      }
    });
    UMLActListNum.setBounds(new Rectangle(74, 243, 32, 21));
    UMLActListNum.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLActListNum_actionPerformed(e);
      }
    });
    UMLActListLabel.setText("levels down");
    UMLActListLabel.setBounds(new Rectangle(119, 245, 85, 17));
    UStateYes.setText("Yes");
    UStateYes.setBounds(new Rectangle(13, 31, 47, 25));
    UStateYes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UStateYes_actionPerformed(e);
      }
    });
    UStateNo.setSelected(true);
    UStateNo.setText("No");
    UStateNo.setBounds(new Rectangle(13, 63, 44, 25));
    UStateNo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UStateNo_actionPerformed(e);
      }
    });
    UStateNo.setToolTipText("");
    USeqNo.setSelected(true);
    USeqYes.setBounds(new Rectangle(11, 29, 47, 25));
    USeqYes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        USeqYes_actionPerformed(e);
      }
    });
    USeqYes.setText("Yes");
    USeqNo.setToolTipText("");
    USeqNo.setBounds(new Rectangle(11, 61, 44, 25));
    USeqNo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        USeqNo_actionPerformed(e);
      }
    });
    USeqNo.setText("No");
    UMLMain.add(UmlP, null);
    UseCaseP.add(UURoot, null);
    UseCaseP.add(UUNone, null);
    UseCaseP.add(UUUntil, null);
    UseCaseP.add(UUAll, null);
    UseCaseP.add(UUUntilText, null);
    ClassP.add(UClassYes, null);
    ClassP.add(UClassNo, null);
    DeploymentP.add(UDepYes, null);
    DeploymentP.add(UDepNo, null);
    UmlP.add(UMLCancel, null);
    UmlP.add(UMLGenerate, null);
    UmlP.add(ActivityP, null);
    UmlP.add(SequenceP, null);
    ActivityP.add(UMLActTop, null);
    ActivityP.add(UMLActNone, null);
    ActivityP.add(UMLActElse, null);
    ActivityP.add(UMLActTopText, null);
    ActivityP.add(UMLActTopLable, null);
    ActivityP.add(UMLActScroll, null);
    ActivityP.add(UMLActListAll, null);
    ActivityP.add(UMLActListUntil, null);
    ActivityP.add(UMLActListNum, null);
    ActivityP.add(UMLActListLabel, null);
    UmlP.add(DeploymentP, null);
    UMLActScroll.getViewport().add(UMLActList, null);
    UmlP.add(UseCaseP, null);
    UUUntilText.setVisible(false);
    UUBGroup.add(UURoot);
    UUBGroup.add(UUAll);
    UUBGroup.add(UUUntil);
    UUBGroup.add(UUNone);
    UClassBGroup.add(UClassYes);
    UClassBGroup.add(UClassNo);
    UDepBGroup.add(UDepYes);
    UDepBGroup.add(UDepNo);
    UMLActGroup.add(UMLActTop);
    UMLActGroup.add(UMLActElse);
    UMLActGroup.add(UMLActNone);
    UMLActTopText.setEnabled(false);
    UMLActTopLable.setEnabled(false);
    UMLAct2.add(UMLActListAll);
    UMLAct2.add(UMLActListUntil);
    StatechartP.add(UStateYes, null);
    StatechartP.add(UStateNo, null);
    StatechartP.add(UStateScroll, null);
    UStateScroll.getViewport().add(UStateList, null);
    SequenceP.add(USeqScroll, null);
    SequenceP.add(USeqYes, null);
    SequenceP.add(USeqNo, null);
    UmlP.add(StatechartP, null);
    USeqScroll.getViewport().add(USeqList, null);
    UmlP.add(ClassP, null);
    USeqScroll.setVisible(false);
    UStateScroll.setVisible(false);
    UMLActListAll.setEnabled(false);
    UMLActListAll.setSelected(true);
    UMLActListUntil.setEnabled(false);
    UMLActListNum.setEnabled(false);
    UMLActListLabel.setEnabled(false);
    USeqGroup.add(USeqYes);
    USeqGroup.add(USeqNo);
    UStateGroup.add(UStateYes);
    UStateGroup.add(UStateNo);
    UMLActList.setEnabled(false);
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
    this.dispose();
    }
  }


  void UMLActTop_actionPerformed(ActionEvent e) {
  if (UMLActTop.isSelected()){
    UMLActScroll.setEnabled(false);
    UMLActTopText.setEnabled(true);
    UMLActTopLable.setEnabled(true);
    UMLActListAll.setEnabled(false);
    UMLActListUntil.setEnabled(false);
    UMLActListNum.setEnabled(false);
    UMLActListLabel.setEnabled(false);
    UMLActList.setEnabled(false);
    UMLActScroll.setEnabled(false);
    }
  }

  void UMLActElse_actionPerformed(ActionEvent e) {
  if (UMLActElse.isSelected()) {
    UMLActScroll.setEnabled(true);
    UMLActListAll.setEnabled(true);
    UMLActList.setEnabled(true);
    UMLActListUntil.setEnabled(true);
    UMLActListLabel.setEnabled(true);
    UMLActTopText.setEnabled(false);
    UMLActTopLable.setEnabled(false);
  }
  }

  void UURoot_actionPerformed(ActionEvent e) {
    if (UURoot.isSelected())
      UUUntilText.setVisible(false);

  }

  void UUAll_actionPerformed(ActionEvent e) {
  if (UUAll.isSelected())
  UUUntilText.setVisible(false);

  }

  void UUUntil_actionPerformed(ActionEvent e) {
  if (UUUntil.isSelected())
  UUUntilText.setVisible(true);

  }

  void UUNone_actionPerformed(ActionEvent e) {
  if (UUNone.isSelected())
  UUUntilText.setVisible(false);

  }


/**
 * User chose to generate the UML diagrams.
 */
  private void UMLGenerate_actionPerformed(ActionEvent e) {
    JOptionPane inputEr = new JOptionPane();
    boolean Uflag=false;
    try{

      int flag1 =0;
      int input;
      boolean Sflag=false;
      boolean Aflag=false;
      boolean Seqflag=false;

      if (UUUntil.isSelected()) {
        Uflag=true;
        input = Integer.parseInt(UUUntilText.getText());
        if (input<1)
          input =Integer.parseInt("jdkfh");
      }
      if((UMLActListUntil.isSelected())&&(UMLActElse.isSelected())){
        input = Integer.parseInt(UMLActListNum.getText());
        if (input<1)
          input =Integer.parseInt("jdkfh");
      }
    if(UMLActTop.isSelected()){
      input = Integer.parseInt(UMLActTopText.getText());
      if (input<1)
        input =Integer.parseInt("jdkfh");
    }

    if((UStateYes.isSelected())&&(!UClassYes.isSelected())){
      JOptionPane wr = new JOptionPane();
      inputEr.showMessageDialog(this, "To create Statechart Diagram you need to choose:\n Class Diagram : Yes", "Error", JOptionPane.ERROR_MESSAGE);
      Sflag = true;
    }
    else
    if (UStateYes.isSelected()){
      int selected[] = UStateList.getSelectedIndices();
      if(selected.length<=0){
        inputEr.showMessageDialog(this, "Illegal input in statechart. Please select object.", "ERROR", JOptionPane.ERROR_MESSAGE);
        Sflag=true;
      }
    }

    if((UMLActElse.isSelected())&&(!Sflag)){
      int selected[] = UMLActList.getSelectedIndices();
      if(selected.length<=0){
        inputEr.showMessageDialog(this, "Illegal input in activity. Please select process.", "ERROR", JOptionPane.ERROR_MESSAGE);
        Aflag=true;
      }
    }

    if((USeqYes.isSelected())&&((!UUAll.isSelected())||(!UClassYes.isSelected()))&&(!Sflag)&&(!Aflag)){
      JOptionPane wr = new JOptionPane();
      inputEr.showMessageDialog(this, "To create Sequence Diagram you need to choose:\n Class Diagram : Yes\n Use Case Diagram : All OPD Levels", "Error", JOptionPane.ERROR_MESSAGE);
      Seqflag = true;
    }
    else
      if(USeqYes.isSelected() && (!Sflag) &&(!Aflag) ){
        int selected[] = USeqList.getSelectedIndices();
        if(selected.length <= 0){
          inputEr.showMessageDialog(this,"Illigal input in sequence.Please select process.","ERROR",JOptionPane.ERROR_MESSAGE);
          Seqflag = true;
        }
      }



    if(!Aflag && !Sflag && !Seqflag){
      String filename="";
      UMLSaver uml_save = new UMLSaver();
      int returnVal = uml_save.UMLSaver.showSaveDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        filename =uml_save.UMLSaver.getSelectedFile().getPath();
        String name = uml_save.UMLSaver.getSelectedFile().getName();
        StringTokenizer st = new StringTokenizer(filename, ".", false);
        filename = st.nextToken()+".xml";
        File myDir = uml_save.UMLSaver.getCurrentDirectory();
        int flag = 0; //0 - no such file yet
        int i=0;
        if (myDir.exists()) {
          File[] contents = myDir.listFiles();
          while (i < contents.length) {
            if (contents[i].getName().compareTo(name)==0)
              flag = 1;
            i++;
          }//end of while
        }//end of if
        if (flag==1){
          JOptionPane SaveTemp = new JOptionPane();
          int retval=SaveTemp.showConfirmDialog( this, "A file with this name already exists. Do you want to overwrite it?", "Overwrite Document", JOptionPane.YES_NO_OPTION);
          if (retval== JOptionPane.YES_OPTION){
           if ( (UClassYes.isSelected())||(UUNone.isSelected() == false)||(UStateYes.isSelected())
                        ||(UDepYes.isSelected())||(UMLActNone.isSelected()==false)||(USeqYes.isSelected())){
              try{
                UMLCreate(mySys, filename);
              }
              catch(IOException er){
                System.out.println("error");
              }
              this.dispose();
            }//end of if
          }//end of if
        }//end of if
        else {
          try{
            UMLCreate(mySys, filename);
            this.dispose();
          }// of try
          catch(IOException er){
            System.out.println("error");
          }// of catch
        }//end of else
      }//end of if
   }//end of if (flags)
  }//end of try
  catch (NumberFormatException ne) {
    if(Uflag)
      inputEr.showMessageDialog(this, "Illegal input in usecase: level is not valid.\n Please insert an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
    else
        inputEr.showMessageDialog(this, "Illegal input in activity: level is not valid.\n Please insert an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
  }//end of catch
}//end of func


//---------------------U M L - C R E A T E-----------------------------
/**
 * Prints the XML file.
 * @param ISystem sys
 * @param String filename
 */
private void  UMLCreate(ISystem sys, String filename) throws IOException{
    try{
      mySys=sys;
      FileOutputStream file;
      file = new FileOutputStream(filename);
      String temp="1";


      temp = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?> \n";
      file.write(temp.getBytes());
      temp = "<XMI xmi.version=\"1.1\" xmlns:UML=\"//org.omg/UML/1.3\" timestamp=\"Sun Jun 02 15:46:18 2002\"> \n";
      file.write(temp.getBytes());
      temp = "<XMI.header>\n";
      file.write(temp.getBytes());
      temp = "<XMI.documentation>\n";
      file.write(temp.getBytes());
      temp = "<XMI.exporter>Unisys.JCR.2</XMI.exporter>\n";
      file.write(temp.getBytes());
      temp = "<XMI.exporterVersion>1.3.2</XMI.exporterVersion> \n";
      file.write(temp.getBytes());
      temp = "</XMI.documentation>\n";
      file.write(temp.getBytes());
      temp = "<XMI.metamodel xmi.name=\"UML\" xmi.version=\"1.3\" />\n";
      file.write(temp.getBytes());
      temp = "</XMI.header>\n";
      file.write(temp.getBytes());
      temp = "<XMI.content>\n";
      file.write(temp.getBytes());

      //-------------------------------------------------------------------------------------
      Diagrams_Creator creat= new Diagrams_Creator();
      int opU=-1,levU=-1;
      int opC=-1;
      int opSt=-1,opSq=-1;
      Vector vecSq = new Vector(4,2);
      Vector vecSt = new Vector(4,2);
      Vector vecA = new Vector(4,2);
      int opA=-1,levA=-1;
      int opD=-1;



        //-------class-------------
        if(UClassYes.isSelected())
          opC=1;
        //-------use case-------------
        if(UURoot.isSelected()){
          opU=1;
          levU=1;
        }
        if(UUAll.isSelected()){
          opU=2;
          levU=0;
        }
        if (UUNone.isSelected()){
          opU=-1;
          levU=0;
        }
        if(UUUntil.isSelected()){
          opU=1;
          levU=Integer.parseInt(UUUntilText.getText());
        }
        //-------statechart-------------
        if (UStateYes.isSelected()){
          opSt=1;
          int selected[] = UStateList.getSelectedIndices();
          int i=0;
          while (i<selected.length){
            vecSt.addElement(v_obj.get(selected[i]));
            i++;
          }
        }
        //-------sequence-------------
        if (USeqYes.isSelected()){
          opSq=1;
          int selected[] = USeqList.getSelectedIndices();
          int i=0;
          while (i<selected.length){
            vecSq.addElement(v_proc.get(selected[i]));
            i++;
          }
        }
        //-------activity-------------
        if(UMLActTop.isSelected()){
          opA=1;
          levA=Integer.parseInt(UMLActTopText.getText());
        }
        if(UMLActElse.isSelected()){
          opA=2;// by processes
          int selected[] = UMLActList.getSelectedIndices();
          int i=0;
          while (i<selected.length){
            vecA.addElement(v_act.get(selected[i]));
            i++;
          }
          if (UMLActListAll.isSelected())
            levA=1000;
          else
            levA=Integer.parseInt(UMLActListNum.getText());
        }
        //-------deployment-------------
        if(UDepYes.isSelected())
          opD=1;




        creat.Creator(sys,file,opC,opU,levU,opSq,vecSq,opSt,vecSt,opA,levA,vecA,opD);

        //-----------------------------------------------------------------------------------------------------------------

      temp = "</XMI.content>\n";
      file.write(temp.getBytes());
      temp = "</XMI>\n";
      file.write(temp.getBytes());
      file.close();
    }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch

}//end of func
void UMLCancel_actionPerformed(ActionEvent e) {
  this.dispose();
  }

  void UMLActNone_actionPerformed(ActionEvent e) {
    if (UMLActNone.isSelected()) {
      UMLActTopText.setEnabled(false);
      UMLActTopLable.setEnabled(false);
      UMLActScroll.setEnabled(false);
      UMLActListAll.setEnabled(false);
      UMLActListUntil.setEnabled(false);
      UMLActListNum.setEnabled(false);
      UMLActListLabel.setEnabled(false);
      UMLActList.setEnabled(false);
      }
  }

  void USeqYes_actionPerformed(ActionEvent e) {
    if (USeqYes.isSelected())
      USeqScroll.setVisible(true);
    if (!USeqYes.isSelected())
      USeqScroll.setVisible(false);
  }
  void UStateYes_actionPerformed(ActionEvent e) {
    if (UStateYes.isSelected())
      UStateScroll.setVisible(true);
    if (!UStateYes.isSelected())
      UStateScroll.setVisible(false);
  }
  void USeqNo_actionPerformed(ActionEvent e) {
    if (!USeqNo.isSelected())
      USeqScroll.setVisible(true);
    if (USeqNo.isSelected())
      USeqScroll.setVisible(false);
  }
  void UStateNo_actionPerformed(ActionEvent e) {
    if (UStateNo.isSelected())
      UStateScroll.setVisible(false);
    if (!UStateNo.isSelected())
      UStateScroll.setVisible(true);
  }

  void UMLActListAll_actionPerformed(ActionEvent e) {
    if (UMLActListAll.isSelected())
      UMLActListNum.setEnabled(false);
    if (!UMLActListAll.isSelected())
      UMLActListNum.setEnabled(true);
  }

  void UMLActListUntil_actionPerformed(ActionEvent e) {
    if (UMLActListUntil.isSelected())
      UMLActListNum.setEnabled(true);
    if (!UMLActListUntil.isSelected())
      UMLActListNum.setEnabled(false);

  }

  void UMLActListNum_actionPerformed(ActionEvent e) {

  }

  void UClassNo_actionPerformed(ActionEvent e) {
  }

  void UClassYes_actionPerformed(ActionEvent e) {
    if(UClassYes.isSelected()){
        UStateYes.setEnabled(true);
        UStateNo.setEnabled(true);
        StatechartP.setEnabled(true);
        if(UStateYes.isSelected())
          UStateScroll.setVisible(true);
    }
  }

}