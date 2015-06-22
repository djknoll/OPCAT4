package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.axis.AxisFault;
import org.objectprocess.Client.TeamMember;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Connect extends JDialog {
  TeamMember myTeamMember;
  private final static String fileSeparator = System.getProperty("file.separator");

  JPanel jPanel = new JPanel();

  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  JTextField loginName = new JTextField();
  JPasswordField password = new JPasswordField();
  JTextField serverAddr = new JTextField();
  JTextField serverPort = new JTextField();

  JButton ConnectButton = new JButton();
  JButton CancelButton = new JButton();
  JCheckBox saveProperties = new JCheckBox();


public Connect(TeamMember teamMember) throws HeadlessException {
    super(Opcat2.getFrame(), true);

    myTeamMember=teamMember;

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
    this.setTitle("Connect");
    jPanel.setBounds(new Rectangle(0, 0, 355, 296));

    jPanel.setLayout(null);

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Login Name:");
    jLabel1.setBounds(new Rectangle(98, 26, 98, 31));
    jLabel1.setToolTipText("Insert your login name");
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Password:");
    jLabel2.setBounds(new Rectangle(98, 69, 98, 31));
    jLabel2.setToolTipText("Insert your password");
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Server Address:");
    jLabel3.setBounds(new Rectangle(98, 116, 109, 31));
    jLabel3.setToolTipText("Insert a full description of the server's URL");
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Server Port:");
    jLabel4.setBounds(new Rectangle(98, 172, 110, 15));

    loginName.setBounds(new Rectangle(219, 28, 111, 26));
    loginName.setFont(new java.awt.Font("SansSerif", 0, 12));

    password.setBounds(new Rectangle(219, 71, 111, 26));
    password.setFont(new java.awt.Font("SansSerif", 0, 12));

    serverAddr.setFont(new java.awt.Font("SansSerif", 0, 12));
    serverAddr.setBounds(new Rectangle(219, 118, 111, 26));

    serverPort.setFont(new java.awt.Font("SansSerif", 0, 12));
    serverPort.setBounds(new Rectangle(219, 171, 111, 26));

    ConnectButton.setBounds(new Rectangle(18, 249, 88, 25));
    ConnectButton.setText("Connect");
    ConnectButton.addActionListener(new Connect_ConnectButton_actionAdapter(this));
    CancelButton.setBounds(new Rectangle(242, 249, 88, 25));
    CancelButton.setText("Cancel");
    CancelButton.addActionListener(new Connect_CancelButton_actionAdapter(this));

    logoLabel.setBounds(new Rectangle(20, 28, 50, 50));

    saveProperties.setEnabled(true);
    saveProperties.setFont(new java.awt.Font("Dialog", 0, 12));
    saveProperties.setSelected(false);
    saveProperties.setText("Save Properties");
    saveProperties.setBounds(new Rectangle(100, 208, 116, 23));
    saveProperties.addActionListener(new Connect_saveProperties_actionAdapter(this));
    saveProperties.addActionListener(new Connect_saveProperties_actionAdapter(this));
    jPanel.add(logoLabel, null);
    jPanel.add(jLabel1, null);
    jPanel.add(loginName, null);
    jPanel.add(jLabel2, null);
    jPanel.add(password, null);
    jPanel.add(jLabel3, null);
    jPanel.add(serverAddr, null);
    jPanel.add(jLabel4, null);
    jPanel.add(saveProperties, null);
    jPanel.add(CancelButton, null);
    jPanel.add(ConnectButton, null);
    jPanel.add(serverPort, null);
    this.getContentPane().add(jPanel, null);

//catch the "enter" and perform connect to server.
    loginName.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    loginName.getActionMap().put("pressed",enterKeyPressed);
    password.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    password.getActionMap().put("pressed",enterKeyPressed);
    serverAddr.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    serverAddr.getActionMap().put("pressed",enterKeyPressed);
    serverPort.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    serverPort.getActionMap().put("pressed",enterKeyPressed);

    jPanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    jPanel.getActionMap().put("pressed",enterKeyPressed);


    this.setBounds(0, 0, 355, 326);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);
//    password.setFocusable(true);
//    password.requestFocus();
}//end of JBinit


void ConnectButton_actionPerformed() {

    //check that loginName and password were given by the user.
    if ( (loginName.getText().equals("")) || (password.getText().equals("")) ) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "Login Name or Password is missing",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    setCursor(new Cursor(Cursor.WAIT_CURSOR));

    //first, initialize all web service, if it is the first time.
    if (!myTeamMember.isConnected())
      try {
        myTeamMember.setAllServices(serverAddr.getText(),serverPort.getText());
      }
      catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }

    //try to login into server, and if succesful, get all user data.
    //first- try to connect to server, using loginname and apss- if OK return wuth userID.
    try {
      myTeamMember.loginUser(loginName.getText(), password.getText());
    }
    catch (AxisFault axisException) {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      this.dispose();
      ExceptionHandler exceptionHandler = new ExceptionHandler(axisException);
      return;
    }
    catch (Exception eLogin) {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      this.dispose();
      System.err.println("Exception caught: " + eLogin);
      return;
    }

    //get from the server all the user data.
    try {
      myTeamMember.initializeEnhancedUserValue();
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      this.dispose();
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "User successfully logged to server",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    catch (AxisFault axisException) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(axisException);
    }
    catch (Exception eInitUser) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "User Interface Initialization failed.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
    try {
      myTeamMember.logoutUser();
    }
    catch (Exception eLogout) {};
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    this.dispose();
  }


void CancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
}

void saveProperties_actionPerformed(ActionEvent e) {
    if (saveProperties.isSelected()) {
      Thread runner = new Thread() {
        public void run() {

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
        }
      };
      runner.start();
    }
  }

  public void ReadPreferenceFile()  throws Exception {

    String fileName = "OPCATeam" + fileSeparator + "OPCATeam.txt";
    BufferedReader in = new BufferedReader(new FileReader(fileName));

    String line = null;
    loginName.setText("");
    serverAddr.setText("");
    serverPort.setText("");

    try {
      line = in.readLine();
      loginName.setText(line);
      line = in.readLine();
      serverAddr.setText(line);
      line=in.readLine();
      serverPort.setText(line);
    } catch (Exception e) {}
  }


  Action enterKeyPressed = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      ConnectButton_actionPerformed();
    }
  };


} //end of class Connect


class Connect_ConnectButton_actionAdapter implements java.awt.event.ActionListener {
  Connect adaptee;

  Connect_ConnectButton_actionAdapter(Connect adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ConnectButton_actionPerformed();
  }
}


class Connect_CancelButton_actionAdapter implements java.awt.event.ActionListener {
  Connect adaptee;

  Connect_CancelButton_actionAdapter(Connect adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.CancelButton_actionPerformed(e);
  }
}

class Connect_saveProperties_actionAdapter implements java.awt.event.ActionListener {
  Connect adaptee;

  Connect_saveProperties_actionAdapter(Connect adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.saveProperties_actionPerformed(e);
  }
}




