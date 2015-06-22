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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPI.ISystem;
import extensionTools.Documents.Doc.Document;
/**
 * HandleTemp Class extends the standard JDialog. Allows user to work with
 * templates: create new template, delete or edit templates.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */


public class HandleTemp extends JDialog {
  ISystem mySys;
  JPanel contentPane;
  String data[];
  JPanel HTPanel = new JPanel();
  JButton HTNew = new JButton();
  JButton HTEdit = new JButton();
  JButton HTDel = new JButton();
  JButton HTCancel = new JButton();
  JScrollPane HTScroll = new JScrollPane();
  JList HTList;
  DefaultListModel list_model = new DefaultListModel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  File myDir = new File("Templates");


  /**
   * Constructs the frame.
   * @param sys of type ISystem
   */
  public HandleTemp(ISystem sys) {
    super(sys.getMainFrame(), true);
    mySys = sys;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    //setting the location in the center
    int fX = sys.getMainFrame().getX();
    int fY = sys.getMainFrame().getY();
    int pWidth = sys.getMainFrame().getWidth();
    int pHeight = sys.getMainFrame().getHeight();
    setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));
    //Esc key listener
    KeyListener kListener = new KeyAdapter(){
        public void keyReleased(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                HTCancel.doClick();
                return;
            }
        }
    };
    addKeyListener(kListener);

  }
  /**
   * Component initialization.
   */
  private void jbInit(){
    this.setResizable(false);//disable changing the size of the dialog
    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(border1,"Handle Template");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    contentPane.setLayout(null);
    this.setSize(new Dimension(378, 240));
    this.setTitle("Handle Template");
    HTPanel.setBorder(titledBorder1);
    HTPanel.setBounds(new Rectangle(6, 5, 351, 198));
    HTPanel.setLayout(null);
    HTNew.setBounds(new Rectangle(216, 28, 79, 27));
    HTNew.setBorder(border2);
    HTNew.setText("New");
    HTNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        HTNew_actionPerformed(e);
      }
    });
    HTEdit.setBounds(new Rectangle(216, 59, 79, 27));
    HTEdit.setBorder(border2);
    HTEdit.setText("Edit");
    HTEdit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        HTEdit_actionPerformed(e);
      }
    });
    HTDel.setBounds(new Rectangle(216, 89, 79, 27));
    HTDel.setBorder(border2);
    HTDel.setText("Delete");
    HTDel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        HTDel_actionPerformed(e);
      }
    });
    HTCancel.setBounds(new Rectangle(216, 148, 79, 27));
    HTCancel.setBorder(border2);
    HTCancel.setText("Cancel");
    HTCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        HTCancel_actionPerformed(e);
      }
    });
    HTScroll.setBounds(new Rectangle(29, 28, 137, 147));

    contentPane.add(HTPanel, null);
    HTPanel.add(HTScroll, null);
    HTPanel.add(HTNew, null);
    HTPanel.add(HTEdit, null);
    HTPanel.add(HTDel, null);
    HTPanel.add(HTCancel, null);
    //creating the list of available templates
    int counter=0;
    if (myDir.exists()) {
      File[] contents = myDir.listFiles();
      while (counter < contents.length) {
        list_model.addElement(contents[counter].getName());
        counter++;
      }
    }
    HTList = new JList(list_model);
    HTList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    HTScroll.getViewport().add(HTList, null);
  }
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        this.dispose();
    }
  }
  /**
   * Opens a TempEdit screen in order to make selections for a new template.
   * @param e ActionEvent on New button
   */
  void HTNew_actionPerformed(ActionEvent e) {
    //creates and shows a screen for selection of fields for template
    TempEdit te = new TempEdit(mySys, "-1", this);
    te.setVisible(true);

  }
  /**
   * Openes a TempEdit screen, with the selected fields of the template marked, in
   * order to allow the user editing.
   * @param e ActionEvent on Edit button
   */
  void HTEdit_actionPerformed(ActionEvent e) {
    try {
      //check if a template for editing was selected
      if (!HTList.isSelectionEmpty()) {
        Document doc =new Document();//create a document
        String filename = (String)HTList.getModel().getElementAt(HTList.getSelectedIndex());
        FileInputStream file = new FileInputStream("Templates/"+filename);
        doc.FromTemp(file);//insert the info from the template to document
        //create an editing screen and show the selected fields
        TempEdit te = new TempEdit(mySys, filename, this);
        te.TempToScreen(doc);
        te.setVisible(true);
        file.close();
      }
      //if a template was not chosen -> error
      else {
        JOptionPane.showMessageDialog(this, "Please select a Template.", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    catch(IOException er){
       System.out.println("Couldn't open a template");
    }
  }
  /**
   * Cancels working with templates.
   * @param e ActionEvent on cancel button
   */
  void HTCancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }
  /**
   * Deletes the chosen template.
   * @param e ActionEvent on Delete button
   */
  void HTDel_actionPerformed(ActionEvent e) {
    //if a template was selected
    if (!HTList.isSelectionEmpty()){
      String filename = (String)HTList.getModel().getElementAt(HTList.getSelectedIndex());
      //makes sure the user wants to delete
      int retval=JOptionPane.showConfirmDialog( this, "Are you sure you want to delete '"+filename+"' template?", "Delete Template", JOptionPane.YES_NO_OPTION);
      if (retval== JOptionPane.YES_OPTION){
        File aFile = new File ("Templates/"+filename);
        aFile.delete();//remove file
        list_model.removeElement(filename);//remove from the list on the screen
      }
     }
     //if template was not chosen -> error
     else {
        JOptionPane.showMessageDialog(this, "Please select a Template.", "ERROR", JOptionPane.ERROR_MESSAGE);
      }

   }
}