package extensionTools.opltoopd;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exportedAPI.opcatAPIx.IXSystem;

/**
 * The main controller of this application
 */
public class OPLComboBoxController
{
    boolean isChange;
    /**
     * Initializes the controller
     */
    public OPLComboBoxController() {
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
    }
    boolean getIsChange(){
        return isChange;
    }
    /**
     * Initializes the controller
     * @param system the running IXSystem
     * @param isChanged for markes system changing
     *
     */
  public OPLComboBoxController(IXSystem opcatSystem,boolean isChanged) {

    iXSystem = opcatSystem;
    curOpdId = iXSystem.getCurrentIXOpd().getOpdId();
    activateGui();
    initGui(oPLComboBox);
    isChange = isChanged;
    rescanButtonPressed();
  }

  /** Activates the dialog box controlled by this controller. */

  public void activateGui() {
    long opd_id = iXSystem.getCurrentIXOpd().getOpdId();
    String opl_sent = iXSystem.getOPL(false, (int) curOpdId);
    oPLComboBox = new OPLComboBox(this);
  }

  /**
   * Sets the input dialog as the GUI component controlled by this component
   * and initializes it.
   * @param oPLComboBox controlled by this controller.
   */
  public void initGui(OPLComboBox oPLComboBox) {
    //oPLComboBox = oPLComboBox;
  }

  /**
   * Represents the selection of the ComboBox
   */
  public void comboChused(ActionEvent e) {
    JComboBox cb = (JComboBox) e.getSource();
    String newSelection = (String) cb.getSelectedItem();
    oPLComboBox.currentPattern = newSelection;
    if (newSelection != null) {
      ref_flag = true;
      reformat();
    }
  }

  private void rescanButtonPressed() {
    curOpdId = iXSystem.getCurrentIXOpd().getOpdId();
    String opl_sent = iXSystem.getOPL(false, (int) curOpdId);
    opl_sent = opl_sent.replace('\n', ' ');
    StringTokenizer token = new StringTokenizer(opl_sent, ".");
    oPLComboBox.patternList.removeAllItems();
    oPLComboBox.patternList.addItem("(Add new sentence)");
    if (token == null)
    {
    }
    else {
      int i;
      int count_tok = token.countTokens();
      for (i = 1; i < count_tok; i++) {
        String newtok;
        newtok = new String(token.nextToken());
        oPLComboBox.patternList.addItem(newtok);
      }
    }
    oPLComboBox.result.setText("");
  }

  /**
   * Handles the "Add" button pressing.
   */
  public void addButtonPressed() {
    NewSentenceAdding addSent = new NewSentenceAdding(iXSystem);
    LinkStruct linkNew = new LinkStruct(), linkOld;
    RelationStruct relNew = new RelationStruct(), relOld;
    ObjectStruct objNew = new ObjectStruct(), objOld;
    ProcessStruct procNew = new ProcessStruct(), procOld;
    boolean isParsed = false;
    comboSent = new String(oPLComboBox.currentPattern);
    newSent = new String(oPLComboBox.result.getText());

    runParser(newSent, linkNew, relNew, objNew, procNew);
    if (linkNew.getLinkType() != null) {
      addSent.newLinkSentence(linkNew);
      isParsed = true;
    }
    if (relNew.getRelationType() != null) {
      addSent.newRelationSentence(relNew);
      isParsed = true;
    }
    if (objNew.getObjectName() != null) {
      addSent.newObjectAdding(objNew, 0,false);
     isParsed = true;
    }
    if (procNew.getProcessName() != null) {
      addSent.newProcessAdding(procNew, 0,false);
      isParsed = true;
    }
    if (isParsed){
      iXSystem.updateChanges();
      rescanButtonPressed();
      isChange = true;
      oPLComboBox.result.setText("");
    }
    return;
  }


  /**
   *  Handles the "Update" button pressing.
  */
  public void updateButtonPressed() {

    NewSentenceAdding addSent = new NewSentenceAdding(iXSystem);
    LinkStruct linkNew = new LinkStruct(), linkOld;
    RelationStruct relNew = new RelationStruct(), relOld;
    ObjectStruct objNew = new ObjectStruct(), objOld;
    ProcessStruct procNew = new ProcessStruct(), procOld;
    boolean isParsed = false;
    comboSent = new String(oPLComboBox.currentPattern + ".");
    newSent = new String(oPLComboBox.result.getText());

    SentenceErasing sentErase = new SentenceErasing(iXSystem);
    linkOld = new LinkStruct();
    relOld = new RelationStruct();
    objOld = new ObjectStruct();
    procOld = new ProcessStruct();
    oPLComboBox.patternList.removeItem(comboSent);
    oPLComboBox.patternList.addItem(newSent);
    runParser(comboSent, linkOld, relOld, objOld, procOld);
    if (newSent.compareTo("") == 0) {
      if (linkOld.getLinkType() != null) {
        sentErase.linkSentErase(linkOld);
      }
      if (relOld.getRelationType() != null) {
        sentErase.relationSentErase(relOld);
      }
      if (objOld.getObjectName() != null) {
        sentErase.objectSentErase(objOld);
      }
      if (procOld.getProcessName() != null) {
        sentErase.processSentErase(procOld);
      }
    }
    else {

      runParser(newSent, linkNew, relNew, objNew, procNew);

      if (linkOld.getLinkType() != null) {
        if (linkNew.getLinkType() != null) {
          addSent.newLinkSentence(sentErase.linkLinkErase(linkNew, linkOld));
          isParsed = true;
        }
        if (relNew.getRelationType() != null) {
          addSent.newRelationSentence(sentErase.relationLinkErase(relNew,
              linkOld));
          isParsed = true;
        }
        if (objNew.getObjectName() != null) {
          addSent.newObjectAdding(sentErase.objectLinkErase(objNew, linkOld), 0,false);
          isParsed = true;
        }
        if (procNew.getProcessName() != null) {
          addSent.newProcessAdding(sentErase.processLinkErase(procNew, linkOld),
                                   0, false);
          isParsed = true;
        }
      }
      if (relOld.getRelationType() != null) {
        if (linkNew.getLinkType() != null) {
          addSent.newLinkSentence(sentErase.linkRelationErase(linkNew, relOld));
          isParsed = true;
        }
        if (relNew.getRelationType() != null) {
          addSent.newRelationSentence(sentErase.relationRelationErase(relNew,
              relOld));
          isParsed = true;
        }
        if (objNew.getObjectName() != null) {
          addSent.newObjectAdding(sentErase.objectRelationErase(objNew, relOld),
                                  0,false);
          isParsed = true;
        }
        if (procNew.getProcessName() != null) {
          addSent.newProcessAdding(sentErase.processRelationErase(procNew,
              relOld), 0, false);
          isParsed = true;
        }
      }
      if (objOld.getObjectName() != null) {
        if (linkNew.getLinkType() != null) {
          addSent.newLinkSentence(sentErase.linkObjectErase(linkNew, objOld));
          isParsed = true;
        }
        if (relNew.getRelationType() != null) {
          addSent.newRelationSentence(sentErase.relationObjectErase(relNew,
              objOld));
          isParsed = true;
        }
        if (objNew.getObjectName() != null) {
          addSent.newObjectAdding(sentErase.objectObjectErase(objNew, objOld),
                                  0, false);
          isParsed = true;
        }
        if (procNew.getProcessName() != null) {
          addSent.newProcessAdding(sentErase.processObjectErase(procNew, objOld),
                                   0,false);
          isParsed = true;
        }
      }
      if (procOld.getProcessName() != null) {
        if (linkNew.getLinkType() != null) {
          addSent.newLinkSentence(sentErase.linkProcessErase(linkNew, procOld));
          isParsed = true;
        }
        if (relNew.getRelationType() != null) {
          addSent.newRelationSentence(sentErase.relationProcessErase(relNew,
              procOld));
          isParsed = true;
        }
        if (objNew.getObjectName() != null) {
          addSent.newObjectAdding(sentErase.objectProcessErase(objNew, procOld),
                                  0, false);
          isParsed = true;
        }
        if (procNew.getProcessName() != null) {
          addSent.newProcessAdding(sentErase.processProcessErase(procNew,
              procOld), 0, false);
          isParsed = true;
        }
      }
      if (isParsed){
        iXSystem.updateChanges();
        rescanButtonPressed();
        isChange = true;
        oPLComboBox.result.setText("");
      }
      return;
    }
  }

  /**
   * Handles the "Cancel" button pressing.
   */
  public void cancelButtonPressed() {
    oPLComboBox.result.setText("");
  }

  private void reformat() {
    if (ref_flag) {
      String sent = new String(oPLComboBox.currentPattern);
      if (sent.equals("(Add new sentence)")) {
        oPLComboBox.result.setText("");
        oPLComboBox.result.setForeground(Color.black);
      }
      else {
        oPLComboBox.result.setText(sent);
        oPLComboBox.result.setForeground(Color.black);
      }
      ref_flag = false;
    }
  }

  /**
   * The parser activation. This function returns just one packed struct,
   * depends on the new sentence.The rest 3 structs are null.
   * @param str a new sentence for parsing
   * @param LS the structure for new Link Sentence
   * @param RS the structure for new Relation Sentence
   * @param OS the structure for new Object Sentence
   * @param PS the structure for new Process Sentence
   */
  public void runParser(String str, LinkStruct LS, RelationStruct RS,
                        ObjectStruct OS, ProcessStruct PS) {
    StringReader sr = new StringReader(str);
    Reader r = new BufferedReader(sr);

    OplParser parser = new OplParser(r);

    try {
      int i = parser.CompilationUnit(PS, OS, LS, RS);
      if (i == 1) {
        OS = null;
        LS = null;
        RS = null;
      }
      if (i == 2) {
        PS = null;
        LS = null;
        RS = null;
      }
      if (i == 3) {
        OS = null;
        PS = null;
        RS = null;
      }
      if (i == 4) {
        OS = null;
        LS = null;
        PS = null;
      }
      if (i == 5) {
        OS = null;
        LS = null;
        PS = null;
        RS = null;
      }
      parser = null;
    }
    catch (ParseException e) {


      JOptionPane.showMessageDialog(iXSystem.getMainFrame(),
      "Cannot parse the sentence.\nPlease consult the help and change the sentence.","Syntax Error",
      JOptionPane.ERROR_MESSAGE);
      //  System.out.println("DemoParser: Encountered errors during parse.");
    }
    catch(TokenMgrError er){
      JOptionPane.showMessageDialog(iXSystem.getMainFrame(),
      er.getMessage(),"Syntax Error",
      JOptionPane.ERROR_MESSAGE);

    }
      catch (MyException e) {

        JOptionPane.showMessageDialog(iXSystem.getMainFrame(),e.getMessage(),
                                      "ERROR",JOptionPane.ERROR_MESSAGE);
     /* JOptionPane.showMessageDialog(oPLComboBox,
                   "Cannot parse the sentence.\nPlease consult the help and change the sentence.","Syntax Error",
                   JOptionPane.ERROR_MESSAGE);*/
      OS = null;
      LS = null;
      PS = null;
      RS = null;

      }
    }

    /**
     * Return the main panel of this application
     * @return the main panel of this application
     */
    public JPanel getCombo() {
      return oPLComboBox.getPanel();
    }
    /**
     * Returns the dialog of this controller
     * @return OPLComboBox
     */
    public JDialog getComboDialog (){
        return oPLComboBox;
    }

    /**
     * Reterns the running IXSystem
     * @return running IXSystem
     */
    public IXSystem getSystem()
    {
        return iXSystem;
    }

    private String comboSent;
    private String newSent;
    public OPLComboBox oPLComboBox;
    private IXSystem iXSystem;
    private long curOpdId;
    private boolean ref_flag;
    private JList jList1 = new JList();
    private JList jList2 = new JList();


}
