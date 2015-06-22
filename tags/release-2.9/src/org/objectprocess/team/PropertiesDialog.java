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
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	 
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

    this.myParent= parent;

    //read preference file - temporary file
    try {
      this.ReadPreferenceFile();
    } catch (Exception e) {}

    try {
      this.jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Properties");
    this.addKeyListener(new PropertiesDialog_this_keyAdapter(this));
    this.jPanel.setBounds(new Rectangle(0, 0, 372, 283));

    this.jPanel.setLayout(null);

    this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel1.setText("Login Name:");
    this.jLabel1.setBounds(new Rectangle(112, 38, 86, 31));
    this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel2.setText("Password:");
    this.jLabel2.setBounds(new Rectangle(112, 81, 82, 31));

    this.loginName.setBounds(new Rectangle(227, 40, 111, 26));
    this.loginName.setFont(new java.awt.Font("SansSerif", 0, 12));

    this.password.setBounds(new Rectangle(227, 83, 111, 26));
    this.password.setFont(new java.awt.Font("SansSerif", 0, 12));

    this.serverPort.setFont(new java.awt.Font("SansSerif", 0, 12));
    this.serverPort.setBounds(new Rectangle(227, 180, 111, 26));

    this.serverAddr.setBounds(new Rectangle(227, 134, 111, 26));
    this.serverAddr.setFont(new java.awt.Font("SansSerif", 0, 12));

    this.CancelButton.setBounds(new Rectangle(250, 237, 88, 25));
    this.CancelButton.setText("Cancel");
    this.CancelButton.addActionListener(new PropertiesDialog_CancelButton_actionAdapter(this));
    this.SaveButton.setBounds(new Rectangle(19, 237, 88, 25));
    this.SaveButton.setText("Save");
    this.SaveButton.addActionListener(new PropertiesDialog_SaveButton_actionAdapter(this));

    this.logoLabel.setBounds(new Rectangle(19, 40, 50, 50));

    this.jLabel4.setToolTipText("Insert a full description of the server's URL");
    this.jLabel4.setBounds(new Rectangle(108, 178, 109, 31));
    this.jLabel4.setText("Server Port:");
    this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel5.setText("Server Addres:");
    this.jLabel5.setBounds(new Rectangle(108, 132, 109, 31));
    this.jLabel5.setToolTipText("Insert a full description of the server's URL");
    this.jPanel.add(this.logoLabel, null);
    this.jPanel.add(this.loginName, null);
    this.jPanel.add(this.password, null);
    this.jPanel.add(this.jLabel1, null);
    this.jPanel.add(this.jLabel2, null);
    this.jPanel.add(this.CancelButton, null);
    this.jPanel.add(this.jLabel5, null);
    this.jPanel.add(this.serverAddr, null);
    this.jPanel.add(this.jLabel4, null);
    this.jPanel.add(this.serverPort, null);
    this.jPanel.add(this.SaveButton, null);
    this.getContentPane().add(this.jPanel, null);


    this.setBounds(0, 0, 372, 313);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);

}//end of JBinit


void CancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
}

void SaveButton_actionPerformed(ActionEvent e) {
        String fileName = "OPCATeam" + fileSeparator + "OPCATeam.txt";
        File outputFile = new File(fileName);
        try {
          FileWriter out = new FileWriter(outputFile);
          out.write(this.loginName.getText()+'\r'+'\n');
          out.write(this.serverAddr.getText()+'\r'+'\n');
          out.write(this.serverPort.getText()+'\r'+'\n');
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
    this.loginName.setText("");
    this.serverPort.setText("");
    this.serverAddr.setText("");

    try {
      line = in.readLine();
      this.loginName.setText(line);
      line = in.readLine();
      this.serverAddr.setText(line);
      line = in.readLine();
      this.serverPort.setText(line);
      } catch (Exception e) {}
}

  void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
		;
	}
   }
} //end of class Connect




class PropertiesDialog_CancelButton_actionAdapter implements java.awt.event.ActionListener {
  PropertiesDialog adaptee;

  PropertiesDialog_CancelButton_actionAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    this.adaptee.CancelButton_actionPerformed(e);
  }
}

class PropertiesDialog_SaveButton_actionAdapter implements java.awt.event.ActionListener {
  PropertiesDialog adaptee;

  PropertiesDialog_SaveButton_actionAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    this.adaptee.SaveButton_actionPerformed(e);
  }
}

class PropertiesDialog_this_keyAdapter extends java.awt.event.KeyAdapter {
  PropertiesDialog adaptee;

  PropertiesDialog_this_keyAdapter(PropertiesDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void keyPressed(KeyEvent e) {
    this.adaptee.this_keyPressed(e);
  }
}



