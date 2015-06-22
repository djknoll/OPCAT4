package org.objectprocess.team;

import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertiesDialog extends JDialog {
  JFrame myParent;
  private final static String fileSeparator = System.getProperty("file.separator");

  JPanel jPanel = new JPanel();

  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();

  JTextField loginName = new JTextField();
  JPasswordField password = new JPasswordField();
  JTextField serverPort = new JTextField();
  JTextField serverAddr = new JTextField();

  JButton CancelButton = new JButton();
  JButton SaveButton = new JButton();



public PropertiesDialog(JFrame parent) throws HeadlessException {
    super(parent, true);

    myParent= parent;

    //read preference file - temporary file
    try {
      ReadPreferenceFile();
    } catch (Exception e) {}

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Properties");
    this.addKeyListener(new PropertiesDialog_this_keyAdapter(this));
    jPanel.setBounds(new Rectangle(0, 0, 372, 283));

    jPanel.setLayout(null);

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Login Name:");
    jLabel1.setBounds(new Rectangle(112, 38, 86, 31));
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Password:");
    jLabel2.setBounds(new Rectangle(112, 81, 82, 31));

    loginName.setBounds(new Rectangle(227, 40, 111, 26));
    loginName.setFont(new java.awt.Font("SansSerif", 0, 12));

    password.setBounds(new Rectangle(227, 83, 111, 26));
    password.setFont(new java.awt.Font("SansSerif", 0, 12));

    serverPort.setFont(new java.awt.Font("SansSerif", 0, 12));
    serverPort.setBounds(new Rectangle(227, 180, 111, 26));

    serverAddr.setBounds(new Rectangle(227, 134, 111, 26));
    serverAddr.setFont(new java.awt.Font("SansSerif", 0, 12));

    CancelButton.setBounds(new Rectangle(250, 237, 88, 25));
    CancelButton.setText("Cancel");
    CancelButton.addActionListener(new PropertiesDialog_CancelButton_actionAdapter(this));
    SaveButton.setBounds(new Rectangle(19, 237, 88, 25));
    SaveButton.setText("Save");
    SaveButton.addActionListener(new PropertiesDialog_SaveButton_actionAdapter(this));

    logoLabel.setBounds(new Rectangle(19, 40, 50, 50));

    jLabel4.setToolTipText("Insert a full description of the server's URL");
    jLabel4.setBounds(new Rectangle(108, 178, 109, 31));
    jLabel4.setText("Server Port:");
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("Server Addres:");
    jLabel5.setBounds(new Rectangle(108, 132, 109, 31));
    jLabel5.setToolTipText("Insert a full description of the server's URL");
    jPanel.add(logoLabel, null);
    jPanel.add(loginName, null);
    jPanel.add(password, null);
    jPanel.add(jLabel1, null);
    jPanel.add(jLabel2, null);
    jPanel.add(CancelButton, null);
    jPanel.add(jLabel5, null);
    jPanel.add(serverAddr, null);
    jPanel.add(jLabel4, null);
    jPanel.add(serverPort, null);
    jPanel.add(SaveButton, null);
    this.getContentPane().add(jPanel, null);


    this.setBounds(0, 0, 372, 313);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

}//end of JBinit


void CancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
}

void SaveButton_actionPerformed(ActionEvent e) {
        String fileName = "OPCATeam" + fileSeparator + "OPCATeam.txt";
        File outputFile = new File(fileName);
        try {
          FileWriter out = new FileWriter(outputFile);
          out.write(loginName.getText()+'\r'+'\n');
          out.write(serverAddr.getText()+'\r'+'\n');
          out.write(serverPort.getText()+'\r'+'\n');
          out.close();
        }catch (Exception eFile) {
          System.out.print(eFile.toString());
        }
        finally {
          this.dispose();
        }
      }

  public void ReadPreferenceFile()  throws Exception {
    String fileName = "OPCATeam" + fileSeparator + "OPCATeam.txt";
    BufferedReader in = new BufferedReader(new FileReader(fileName));

    String line = null;
    loginName.setText("");
    serverPort.setText("");
    serverAddr.setText("");

    try {
      line = in.readLine();
      loginName.setText(line);
      line = in.readLine();
      serverAddr.setText(line);
      line = in.readLine();
      serverPort.setText(line);
      } catch (Exception e) {}
}

  void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER);
   }
} //end of class Connect




class PropertiesDialog_CancelButton_actionAdapter implements java.awt.event.ActionListener {
  PropertiesDialog adaptee;

  PropertiesDialog_CancelButton_actionAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.CancelButton_actionPerformed(e);
  }
}

class PropertiesDialog_SaveButton_actionAdapter implements java.awt.event.ActionListener {
  PropertiesDialog adaptee;

  PropertiesDialog_SaveButton_actionAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.SaveButton_actionPerformed(e);
  }
}

class PropertiesDialog_this_keyAdapter extends java.awt.event.KeyAdapter {
  PropertiesDialog adaptee;

  PropertiesDialog_this_keyAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void keyPressed(KeyEvent e) {
    adaptee.this_keyPressed(e);
  }
}



