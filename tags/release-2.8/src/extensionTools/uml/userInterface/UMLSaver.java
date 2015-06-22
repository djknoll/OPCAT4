package extensionTools.uml.userInterface;

import gui.util.CustomFileFilter;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Saves the created XML file.
 */
public class UMLSaver extends JFrame {
  JPanel UMLSaveMain;
  JFileChooser UMLSaver = new JFileChooser();


  /**
   * Construct the frame
   */
  public UMLSaver() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *Component initialization
   */
  private void jbInit() throws Exception  {
    UMLSaveMain = (JPanel) this.getContentPane();
    UMLSaveMain.setLayout(null);
    this.setResizable(false);
    this.setSize(new Dimension(493, 325));
    this.setTitle("Save XML Document");
    UMLSaver.setBackground(new Color(204, 204, 204));
    UMLSaver.setFont(new java.awt.Font("Dialog", 0, 12));
    UMLSaver.setBounds(new Rectangle(10, 5, 466, 283));
        UMLSaver.setFileFilter(new CustomFileFilter("xml", "XML Files"));
    UMLSaver.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UMLSaver_actionPerformed(e);
      }
    });
    UMLSaveMain.setBackground(new Color(204, 204, 204));
    UMLSaveMain.add(UMLSaver, null);
    UMLSaver.setApproveButtonText("Save");



  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      this.dispose();
    }
  }

  void UMLSaver_actionPerformed(ActionEvent e) {
  this.dispose();
  }
}